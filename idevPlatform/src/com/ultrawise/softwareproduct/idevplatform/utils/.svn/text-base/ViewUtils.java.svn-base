package com.ultrawise.softwareproduct.idevplatform.utils;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.ultrawise.softwareproduct.idevplatform.R;
import com.ultrawise.softwareproduct.idevplatform.utils.AsyncImageLoader.ImageCallback;



public class ViewUtils {
	public static AsyncImageLoader imageLoader = new AsyncImageLoader();
    /** 显示对应图片 */
    public static void showImage(final ImageView imageView, String imageUrl,int defaultResource) {
        imageView.setVisibility(View.VISIBLE);
        showImage(imageView, imageUrl, new ImageCallback() {
            @Override
            public void imageLoaded(Bitmap imageDrawable, String imageUrl,
                                    String key) {
                if (imageView != null && imageDrawable != null) {
                    imageView.setImageBitmap(imageDrawable);
                }
            }
        }, defaultResource);
    }

    /** 显示对应图片 */
    public static void showImage(final ImageView imageView, String imageUrl) {
        imageView.setVisibility(View.VISIBLE);
        showImage(imageView, imageUrl, new ImageCallback() {
            @Override
            public void imageLoaded(Bitmap imageDrawable, String imageUrl,
                                    String key) {
                if (imageView != null && imageDrawable != null) {
                    imageView.setImageBitmap(imageDrawable);
                }
            }
        });
    }


    /** 显示对应图片 */
    public static void showImage(final ImageView imageView, String imageUrl,
                                 AsyncImageLoader.ImageCallback callBack) {
        showImage(imageView, imageUrl, callBack, R.drawable.img_default);
    }

    /** 显示对应图片 */
    public static void showImage(final ImageView imageView, String imageUrl,
                                 ImageCallback callBack, int defaultResource) {
        if (defaultResource != 0) {
            imageView.setImageResource(defaultResource);
        }
        Bitmap drawable = imageLoader.loadDrawable(imageUrl, "", callBack);
        if (drawable != null && imageView != null) {
            imageView.setImageBitmap(drawable);
        }
    }



    public void setImageValue(ImageView imageView, Object obj) {
        if (imageView != null && obj != null) {
            try {
                if (obj instanceof Drawable) {
                    imageView.setImageDrawable((Drawable) obj);

                } else if (obj instanceof Bitmap) {
                    imageView.setImageBitmap((Bitmap) obj);

                } else if (obj instanceof Integer) {
                    imageView.setImageResource((Integer) obj);

                } else if (obj instanceof String) {
                    showImage(imageView, (String) obj);
                }
            } catch (Exception e) {
            }
        } else if (obj == null) {
            imageView.setVisibility(View.GONE);
        }
    }

    // 对于大图片回收内存
    public static void recycleImageViewResource(ImageView imageView) {
        if (imageView != null && imageView.getDrawable() != null) {
            BitmapDrawable bd = (BitmapDrawable) imageView.getDrawable();
            if (bd.getBitmap() != null && !bd.getBitmap().isRecycled()) {
                bd.getBitmap().recycle();
            }
            bd = null;
        }
        imageView = null;
    }

}
