package com.bruce.phoneguard.android.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import com.bruce.phoneguard.android.R;

/**
 * Created by Administrator on 2015/3/16.
 */
public class DialogUtils {

    /**
     * 自定义view，并且带editText的dialog
     * @param mContext
     * @param viewId  布局id
     * @param editId  布局中editId
     * @param src     edittext的初始值
     * @param callBack
     * @return dialog
     */
    public static Dialog createDialog(Context mContext, int viewId, int editId, String src, final DialogCallBack callBack) {
        View view = View.inflate(mContext, viewId, null);
        final EditText tv = (EditText) view.findViewById(editId);
        tv.setText(src);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getResources().getString(R.string.mouthTraffic)).setView(view)
                .setCancelable(true)
                .setPositiveButton(mContext.getResources().getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        callBack.callBack(tv.getText().toString());
                    }
                })
                .setNegativeButton(mContext.getResources().getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        return alert;
    }


    public interface DialogCallBack {
        public void callBack(String str);
    }
}
