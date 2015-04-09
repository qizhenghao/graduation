package com.ultrawise.softwareproduct.idevplatform.request;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.ultrawise.softwareproduct.idevplatform.utils.BeanUtils;

public class FileUtils {


    private static final  String FILE_DAT=".dat";
    private static final  String FILE_IMG=".png";
	/**
	 * 验证文件路径是否存在，不存在则进行创建操作
	 * 
	 * @param filePath
	 */
	public static void checkFileExist(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	/** 判断文件是否存在 */
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
	 * @param
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
					+ "com.ultrawise.softproduct.fescomanage" ;
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
		path = "/data/data/" + "com.ultrawise.softproduct.fescomanage" + "/config/";
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
        path = "/data/data/" + "com.ultrawise.softproduct.fescomanage" + "/config/";
        checkFileExist(path);
        return path;
    }



    /** 保存序列对象 */
    public static synchronized void saveObject(Object obj, String path) {
        ObjectOutputStream ost = null;
        try {
            ost = new ObjectOutputStream(new FileOutputStream(path));
            ost.writeObject(obj);
            ost.flush();
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("info","保存出错");
        } finally {
            close(ost);
        }
    }

    /** 异步的方式进行存储 */
    public static void aysncSaveObject(final Object obj, final String path) {
        new Thread() {
            public void run() {
                saveObject(obj, path);
            }
        }.start();
    }

    /** 删除文件 */
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
    /** 获取对象 */
    public static Object getSaveObject(String path) {
        ObjectInputStream ost = null;
        Object obj = null;
        if (path==null||path.equals("")){
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

	/** 图片缓存目录 */
	public static String getImageCachePath() {
		return getCachePath("cache");
	}
    public static  String  getFileImgCacheName(){
//        String.valueOf(object);
        return getCachePath("img")+System.currentTimeMillis()+FILE_IMG;
    }
    public static String getFileImgPath(String str){

        return getCachePath("img")+str;
    }
    /**Save image to the SD card**/
    public static String savePhotoToSDCard( String photoName,
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
    public static  String  getFileDatCacheName(Object object){
        String.valueOf(object);
        return getCachePath("data")+md532( String.valueOf(object))+FILE_DAT;
    }
	public static boolean hasSdcard() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}
    /*** 切割字符串 */
    public static String shortString(String content, int length) {
        if (!BeanUtils.isEmpty(content) && content.length() > length) {
            content = content.substring(0, 8) + "...";
        }
        return content;
    }

    /***
     * 生成32位的MD5加密方式
     *
     * @param
     * @return
     */
    public static String md532(String source) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = source.getBytes();
            // 使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                // 将没个数(int)b进行双字节加密
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
	/**
	 * 通用资源的关闭操作
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
}
