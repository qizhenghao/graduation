package com.ultrawise.softwareproduct.idevplatform.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class ImageFetchFactory {
	private static final String CACHE_FORMAT = ".img";

	private ImageFetchFactory() {

	}

	/** 对外获取图片的方法 */
	public static Bitmap getImage2(String url) {
		Bitmap drawable = null;
		if (!BeanUtils.isEmpty(url)) {
			// md5算法比较慢放在外部统一处理
			String cacheName = BeanUtils.md532(url);
			// 尝试读取缓存
			drawable = getCacheImage2(url, cacheName);
			if (drawable == null) {
				// 使用策略进行网络下载
				drawable = download2(url, cacheName);
			}
		}
		return drawable;
	}

	/** 尝试从缓存中获取图片数据 */
	protected static Bitmap getCacheImage2(String url, String cacheName) {
		Bitmap d = null;
		String filePath = CommonUtils.getImageCachePath() + File.separator
				+ cacheName + CACHE_FORMAT;
		if (BeanUtils.isFileExist(filePath)) {
			d = getLocalDrawable2(filePath);
		}
		return d;
	}

	/** 下载图片 */
	protected static Bitmap download2(String url, String cacheName) {
		Bitmap drawable = null;
		String startS = "";
		// 分割出对应的服务器
		try {
			startS = url.substring(0, 15);
		} catch (Exception e) {
			startS = String.valueOf(url.length() % 8);
		}
		// 向同一个服务器开启的线程数 进行分割
		int poolCounts = 3;
		String poolNumber = String.valueOf(System.currentTimeMillis()
				% poolCounts);
		synchronized (startS + poolNumber) {
			drawable = getNetDrawable2(url, cacheName);
		}
		return drawable;
	}

	/** 防止多个线程操作同一个图片 */
	protected static Bitmap getNetDrawable2(String url, String cacheName) {
		synchronized (url) {
			Bitmap drawable = getCacheImage2(url, cacheName);
			if (drawable == null) {
				// 真是的下载图片
				drawable = getNetDownloadBitmap(url, cacheName);
			}
			return drawable;
		}
	}

	/** 获取网络图片 */
	public static Bitmap getNetDownloadBitmap(String url, String cacheName) {
		synchronized (url) {
			Bitmap bitmap = null;
			InputStream i = null;
			try {
				DefaultHttpClient client = buildHttpClient();

				HttpGet post = new HttpGet(url);
				HttpResponse response = client.execute(post);
				i = response.getEntity().getContent();
				if (!BeanUtils.isEmpty(cacheName) && i != null) {
					// 存放的缓存地址
					String filePath = CommonUtils.getImageCachePath()
							+ File.separator + cacheName + CACHE_FORMAT;
					// 文件进行存储
					BeanUtils.saveFile(i, filePath);
					// 再重文件中获取
					bitmap = getLocalBitmap(filePath);
				} else {
					BitmapFactory.Options opt = new BitmapFactory.Options();
					opt.inPreferredConfig = Bitmap.Config.RGB_565;
					opt.inPurgeable = true;
					opt.inInputShareable = true;

					bitmap = BitmapFactory.decodeStream(i, null, opt);

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				BeanUtils.close(i);
			}
			return bitmap;
		}
	}

	/** 将图片文件转换成具体的图片数据 */
	public static Drawable getLocalDrawable(String filePath) {
		Drawable d = null;
		synchronized (filePath) {
			InputStream i = null;
			try {
				i = new FileInputStream(filePath);

				Bitmap bitmap = BitmapFactory.decodeStream(i);

				d = Drawable.createFromStream(i, "src");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				BeanUtils.close(i);
			}
		}
		return d;
	}

	public static Bitmap getLocalDrawable2(String filePath) {
		Drawable d = null;
		Bitmap bm = null;
		synchronized (filePath) {
			InputStream i = null;
			try {
				i = new FileInputStream(filePath);
				BitmapFactory.Options opt = new BitmapFactory.Options();
				opt.inPreferredConfig = Bitmap.Config.RGB_565;
				opt.inPurgeable = true;
				opt.inInputShareable = true;
				bm = BitmapFactory.decodeStream(i, null, opt);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				BeanUtils.close(i);
			}
		}
		return bm;
	}

	/** 将图片文件转换成具体的图片数据 */
	public static Bitmap getLocalBitmap(String filePath) {
		Bitmap d = null;
		synchronized (filePath) {
			InputStream i = null;
			try {
				i = new FileInputStream(filePath);
				BitmapFactory.Options opt = new BitmapFactory.Options();
				opt.inPreferredConfig = Bitmap.Config.RGB_565;
				opt.inPurgeable = true;
				opt.inInputShareable = true;
				d = BitmapFactory.decodeStream(i, null, opt);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				BeanUtils.close(i);
			}
		}
		return d;
	}

	public static DefaultHttpClient buildHttpClient() {
		// 使用默认的HttpClient
		DefaultHttpClient client = new DefaultHttpClient();
		// 设置连接超时时间
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 60);
		// 设置读取内容连接超时时间
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				1000 * 60);
		return client;
	}

}
