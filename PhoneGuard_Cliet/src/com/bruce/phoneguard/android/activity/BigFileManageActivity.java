package com.bruce.phoneguard.android.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.model.FileInfo;
import com.bruce.phoneguard.android.model.TaskInfo;
import com.bruce.phoneguard.android.utils.FileUtils;
import com.bruce.phoneguard.android.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2015/3/4.
 */
public class BigFileManageActivity extends BaseActivity implements View.OnClickListener{

    private static final long minFileSizeByte = 5 * 1024 * 1024;
    private static final String rootPath = "/";
    private List<FileInfo> fileList;
    private BigFileManageAdapter adapter;
    private ListView listView;
    private TextView totalNumTv;
    private TextView totalSizeTv;
    private TextView delFileTv;
    private View loadingBar;

    private long selectedSize = 0;
    private long totalSize = 0;
    private int selectedNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_file_manage);
        initView();
        initData();
        bindListener();
    }

    private void bindListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ViewHolder holder = (ViewHolder) view.getTag();
                FileInfo item = fileList.get(position);
                if (item.isChecked()) {
                    selectedNum--;
                    selectedSize -= item.getLength();
                } else {
                    selectedNum++;
                    selectedSize += item.getLength();
                }
                fileList.get(position).setChecked(!item.isChecked());
                holder.cb_status.setChecked(fileList.get(position).isChecked());
                totalSizeTv.setText("选中" + FileUtils.getFileSize(selectedSize));
                if (selectedNum != 0) {
                    delFileTv.setText("删除"+ selectedNum + "个");
                    delFileTv.setVisibility(View.VISIBLE);
                } else {
                    totalSizeTv.setText("共" + FileUtils.getFileSize(totalSize));
                    delFileTv.setVisibility(View.INVISIBLE);
                }
            }
        });

        delFileTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i=0;i<fileList.size();i++) {
                            FileInfo item = fileList.get(i);
                            if (item.isChecked()) {
                                FileUtils.deleteFile(item.getAbsolutePath());
                                fileList.remove(i);
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BigFileManageActivity.this, "删除成功", Toast.LENGTH_SHORT);
                                adapter.notifyDataSetInvalidated();
                                loadingBar.setVisibility(View.INVISIBLE);
                                totalSizeTv.setText(FileUtils.getFileSize(totalSize));
                                totalNumTv.setText(fileList.size() + "个大文件");
                                delFileTv.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    @Override
    public void initView() {
        listView = (ListView) findViewById(R.id.big_file_lv);
        totalNumTv = (TextView) findViewById(R.id.total_big_file_num_tv);
        totalSizeTv = (TextView) findViewById(R.id.total_size_tv);
        delFileTv = (TextView) findViewById(R.id.big_file_del_tv);
        loadingBar = findViewById(R.id.ll_loading);
    }

    @Override
    public void initData() {
        mActionBar.setTitle("大文件管理");
        mActionBar.setIcon(R.drawable.selector_back_imagview);
        fileList = new ArrayList<FileInfo>();
        adapter = new BigFileManageAdapter();
        listView.setAdapter(adapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<File> list = FileUtils.getBiggerFiles(minFileSizeByte, "//storage//sdcard0//");
//                list.addAll(FileUtils.getBiggerFiles(minFileSizeByte, Environment.getDataDirectory().getAbsolutePath()));
//                list.addAll(FileUtils.getBiggerFiles(minFileSizeByte, Environment.DIRECTORY_DOWNLOADS));
//                list.addAll(FileUtils.getBiggerFiles(minFileSizeByte, Environment.DIRECTORY_DCIM));
//                list.addAll(FileUtils.getBiggerFiles(minFileSizeByte, Environment.DIRECTORY_DOCUMENTS));
//                list.addAll(FileUtils.getBiggerFiles(minFileSizeByte, Environment.DIRECTORY_MOVIES));
//                list.addAll(FileUtils.getBiggerFiles(minFileSizeByte, Environment.DIRECTORY_MUSIC));
//                fileList.clear();
//                for (File item : list) {
//                    FileInfo fileInfo = new FileInfo(item);
//                    fileList.add(fileInfo);
//                }
                Object[] filsArr = list.toArray();
                Arrays.sort(filsArr, new Comparator<Object>() {
                    @Override
                    public int compare(Object lhs, Object rhs) {
                        if (((File)lhs).length() > ((File)rhs).length()){
                            return 1;
                        }
                        return -1;
                    }
                });
                fileList.clear();
                totalSize = 0;
                for (int i=filsArr.length-1;i>=0;i--) {
                    totalSize += ((File)filsArr[i]).length();
                    fileList.add(new FileInfo((File) filsArr[i]));
                }
                final long finalTotalSize = totalSize;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetInvalidated();
                        totalNumTv.setText(fileList.size()+"个大文件");
                        totalSizeTv.setText("共"+ FileUtils.getFileSize(finalTotalSize));
                        loadingBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();

    }

    @Override
    public void onClick(View v) {

    }

    private class BigFileManageAdapter extends BaseAdapter {
        @Override
        public Object getItem(int position) {
            return fileList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return fileList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView != null) {
                holder = (ViewHolder) convertView.getTag();
            } else {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.item_big_file_manage, null);
                holder = new ViewHolder();
                holder.iv_icon = (ImageView) convertView
                        .findViewById(R.id.big_file_icon_iv);
                holder.tv_name = (TextView) convertView
                        .findViewById(R.id.big_file_name_tv);
                holder.tv_size = (TextView) convertView
                        .findViewById(R.id.big_file_size_tv);
                holder.cb_status = (CheckBox) convertView
                        .findViewById(R.id.big_file_status_cb);
                convertView.setTag(holder);
            }
            holder.iv_icon.setImageResource(fileList.get(position).getIcon_id());
            holder.tv_name.setText(fileList.get(position).getName());
            holder.tv_size.setText(fileList.get(position).getSize());
            holder.cb_status.setChecked(fileList.get(position).isChecked());

            return convertView;
        }
    }


    class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_size;
        CheckBox cb_status;
    }
}
