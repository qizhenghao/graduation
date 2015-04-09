package com.ultrawise.softwareproduct.idevplatform.utils;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public class AsyncImageLoader {
	/** 通过ULR键值对保存图片信息 */
	private HashMap<String, SoftReference<Bitmap>> imageCache;
	/** 成功获取数据的URL集合 */
	private Set<String> sucessURLs = null;

	public AsyncImageLoader() {
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
		sucessURLs = new HashSet<String>();
	}

	/** 加载图片 */
	public Bitmap loadDrawable(final String imageUrl, final String key,
			final ImageCallback imageCallback) {

		Bitmap drawable = getDrawable(imageUrl);
		if (drawable != null) {
			return drawable;
		}
		// 图片下周后的接收
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				Bitmap drawable = getDrawable(imageUrl);
				// 获取图片成功但是被收回时候的处理
				if (drawable == null && sucessURLs.contains(imageUrl)) {
					startImageThread(imageUrl, this);
				} else {
					if (imageCallback != null) {
						imageCallback.imageLoaded(drawable, imageUrl, key);
					}
				}
			}
		};
		// 下载图片的后台线程
		startImageThread(imageUrl, handler);
		return null;

	}

	/** 开启获取图片的线程 */
	protected void startImageThread(final String imageUrl, final Handler handler) {
		new Thread() {
			@Override
			public void run() {
				Bitmap drawable = ImageFetchFactory.getImage2(imageUrl);
				if (drawable != null) {
					sucessURLs.add(imageUrl);
					// if(!imageCache.containsKey(imageUrl)){
					imageCache.put(imageUrl,
							new SoftReference<Bitmap>(drawable));
					// }
				}
				handler.sendEmptyMessage(111);
			}
		}.start();
	}
	

	/** 获取已经下载的图片数据 */
	protected Bitmap getDrawable(String imageUrl) {
		Bitmap drawable = null;
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Bitmap> softReference = imageCache.get(imageUrl);
			drawable = softReference.get();
			if (drawable != null) {
				return drawable;
			}
		}
		return drawable;
	}

	public Bitmap getDRawable(String imageUrl) {
		return getDrawable(imageUrl);
	}

	public interface ImageCallback {
		public void imageLoaded(Bitmap imageDrawable, String imageUrl,
				String key);
	}
	

}
