package com.bruce.phoneguard.android.utils;

import java.io.*;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import com.bruce.phoneguard.android.R;
import com.bruce.phoneguard.android.SysApplication;
import com.ultrawise.softwareproduct.idevplatform.utils.BeanUtils;

public class FileUtils {

    private final static String TAG = "FileUtils";

    private static final String FILE_DAT = ".dat";
    private static final String FILE_PNG = ".png";
    private static final String FILE_GIF = ".gif";
    private static final String FILE_JPEG = ".jpeg";
    private static final String FILE_MP3 = ".mp3";
    private static final String FILE_APK = ".apk";
    private static final String FILE_TXT = ".txt";
    private static final String FILE_AVI = ".avi";
    private static final String FILE_MP4 = ".mp4";
    private static final String FILE_3GP = ".3gp";
    private static final String FILE_WMV = ".wmv";
    private static final String FILE_RMVB = ".rmvb";
    private static final String FILE_RAR = ".rar";
    private static final String FILE_ZIP = ".zip";

    /**
     * 验证文件路径是否存在，不存在则进行创建操
     *
     * @param filePath
     * @author qizhenghao
     * time : 2015年3月5日16:47:31
     */
    public static void checkFileExist(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath
     * @return
     * @author qizhenghao
     * time ：2015年3月5日16:48:13
     */
    public static boolean isFileExist(String filePath) {
        boolean hasFile = false;
        try {
            File file = new File(filePath);
            hasFile = file.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasFile;
    }

    /**
     * 文件保存
     *
     * @param inps
     * @param filePath
     * @author qizhenghao
     * time ：2015年3月5日16:48:59
     */
    public static void saveFile(InputStream inps, String filePath) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(filePath);
            byte[] buffer = new byte[2048];
            int len = -1;
            while ((len = inps.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(inps);
            close(out);
        }
    }

    public static String getCachePath(String name) {
        String path = "";
        if (hasSdcard()) {
            path = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + "Android/data/"
                    + SysApplication.getInstance().mPackageName;
        } else {
            path = getDataPath();
        }
        checkFileExist(path);
        path = path + File.separator + name + File.separator;
        checkFileExist(path);
        return path;
    }

    public static String getDataPath() {
        String path = "";
        path = "/data/data/" + SysApplication.getInstance().mPackageName + "/config/";
        checkFileExist(path);
        return path;
    }

    public static String getImgPath() {
        String path = "";
        path = getCachePath("img");
        checkFileExist(path);
        return path;
    }

    public static String getDataPath(String fileName) {
        String path = "";
        path = "/data/data/" + SysApplication.getInstance().mPackageName + "/config/";
        checkFileExist(path);
        return path;
    }


    /**
     * 保存序列对象
     */
    public static synchronized void saveObject(Object obj, String path) {
        ObjectOutputStream ost = null;
        try {
            ost = new ObjectOutputStream(new FileOutputStream(path));
            ost.writeObject(obj);
            ost.flush();
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("info", "保存出错");
        } finally {
            close(ost);
        }
    }

    /**
     * 异步的方式进行存�?
     */
    public static void aysncSaveObject(final Object obj, final String path) {
        new Thread() {
            public void run() {
                saveObject(obj, path);
            }
        }.start();
    }

    /**
     * 删除文件
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        try {
            if (file.isFile()) {
                if (isFileExist(filePath)) {
                    file.delete();
                }
            } else if (file.isDirectory()) {
                File[] tempFiles = file.listFiles();
                if (!BeanUtils.isEmpty(tempFiles)) {
                    for (File tempFile : tempFiles) {
                        tempFile.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象
     */
    public static Object getSaveObject(String path) {
        ObjectInputStream ost = null;
        Object obj = null;
        if (path == null || path.equals("")) {
            return null;
        }
        try {
            ost = new ObjectInputStream(new FileInputStream(path));
            obj = ost.readObject();
        } catch (Exception e) {
            // System.out.println("加载用户信息出错.........");
            deleteFile(path);
        } finally {
            close(ost);
        }
        return obj;
    }

    /**
     * 图片缓存目录
     */
    public static String getImageCachePath() {
        return getCachePath("cache");
    }

    public static String getFileImgCacheName() {
//        String.valueOf(object);
        return getCachePath("img") + System.currentTimeMillis() + FILE_PNG;
    }

    public static String getFileImgPath(String str) {

        return getCachePath("img") + str;
    }

    /**
     * Save image to the SD card*
     */
    public static String savePhotoToSDCard(String photoName,
                                           Bitmap photoBitmap) {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            File photoFile = new File(photoName); //在指定路径下创建文件
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
                            fileOutputStream)) {
                        fileOutputStream.flush();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return photoName;
    }

    public static String getFileDatCacheName(Object object) {
        String.valueOf(object);
        return getCachePath("data") + md532(String.valueOf(object)) + FILE_DAT;
    }

    public static boolean hasSdcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    /**
     * 切割字符�?
     */
    public static String shortString(String content, int length) {
        if (!BeanUtils.isEmpty(content) && content.length() > length) {
            content = content.substring(0, 8) + "...";
        }
        return content;
    }

    /**
     * 生成32位的MD5加密方式
     *
     * @param source
     * @return md5字符串
     * @author qizhenghao
     * time ：2015年3月5日17:15:15
     */
    public static String md532(String source) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = source.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                // 将没个数(int)b进行双字节加�?
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 通用资源的关闭操�?
     *
     * @param closeable
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static List<File> bigFileList;
    private static long minFileSizeByte;

    /**
     * @param minFileSize
     * @param rootPath
     * @return 文件list
     * @author qizhenghao
     * time: 2015年3月5日15:30:14
     */
    public static List getBiggerFiles(long minFileSize, String rootPath) {
        LogUtils.d(TAG, "rootPath = " + rootPath);
        bigFileList = new ArrayList<File>();
        minFileSizeByte = minFileSize;
        getFiles(rootPath);
        return bigFileList;
    }

    //递归遍历文件
    private static void getFiles(String filePath) {
        File root = new File(filePath);
        File[] files = root.listFiles();
        if (files == null || files.length==0) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                getFiles(file.getAbsolutePath());
            } else {
                if (file.length() > minFileSizeByte) {
                    bigFileList.add(file);
                    LogUtils.d(TAG, file.getAbsolutePath());
                }
            }
        }
    }

    /**
     * @param file
     * @return 文件后缀名str，不带点
     * @author qizhenghao
     * time : 2015年3月5日16:45:38
     */
    public static String getFileExtension(File file) {
        return file.getName().substring(file.getName().lastIndexOf(".") + 1);
    }

    /**
     * @param file
     * @return 文件后缀名str, 带点
     * @author qizhenghao
     * time : 2015年3月5日16:45:38
     */
    public static String getFileExtensionHasSpot(File file) {
        return file.getName().substring(file.getName().lastIndexOf("."));
    }

    /**
     * 以KB单位返回、以MB单位返回、以GB单位返回
     *
     * @return 文件大小，单位已经换算
     * @author qizhenghao
     * time : 2015年3月5日16:43:03
     */
    public static String getFileSize(long size) {
        DecimalFormat df = new DecimalFormat("###.##");
        float f;
        if (size < 1024) {
            f = (float) ((float) size / (float) 1);
            return (df.format(new Float(f).doubleValue()) + "B");
        } else if (1024 < size && size < 1024 * 1024) {
            f = (float) ((float) size / (float) 1024);
            return (df.format(new Float(f).doubleValue()) + "KB");
        } else if (1024 * 1024 < size && size < 1024 * 1024 * 1024) {
            f = (float) ((float) size / (float) (1024 * 1024));
            return (df.format(new Float(f).doubleValue()) + "MB");
        } else if (size > 1024 * 1024 * 1024) {
            f = (float) ((float) size / (float) (1024 * 1024 * 1024));
            return (df.format(new Float(f).doubleValue()) + "GB");
        }
        return "未知大小";
    }


    /**
     * 根据文件后缀名取得对应的图标id
     * @param extension
     * @return 系统图片资源id
     * @author qizhenghao
     * time ：2015年3月5日20:32:42
     */
    public static int getDrawableByExtension(String extension) {
        if (extension == null || extension.equals("")) {
            return R.drawable.unknow_pic;
        }
        if (extension.equals("png") || extension.equals("jpeg") || extension.equals("gif") || extension.equals("jpg")) {
            return R.drawable.photo_pic;
        } else if (extension.equals("3gp") || extension.equals("mp4") || extension.equals("rmvb") || extension.equals("wmv") || extension.equals("avi")) {
            return R.drawable.movie_pic;
        } else if (extension.equals("mp3")) {
            return R.drawable.music_pic;
        } else if (extension.equals("apk")) {
            return R.drawable.apk_pic;
        } else if (extension.equals("txt")) {
            return R.drawable.txt_pic;
        } else if (extension.equals("dat")) {
            return R.drawable.dat_pic;
        } else if (extension.equals("rar") || extension.equals("zip")) {
            return R.drawable.rar_pic;
        }

        return R.drawable.unknow_pic;
    }
}
