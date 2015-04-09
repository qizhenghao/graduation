package com.ultrawise.softwareproduct.idevplatform.adapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 
 * @author 韦国旺
 * 
 * @time 2014-4-14
 * 
 * */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ultrawise.softwareproduct.idevplatform.R;
import com.ultrawise.softwareproduct.idevplatform.utils.AsyncImageLoader;
import com.ultrawise.softwareproduct.idevplatform.utils.AsyncImageLoader.ImageCallback;
import com.ultrawise.softwareproduct.idevplatform.utils.BeanUtils;
import com.ultrawise.softwareproduct.idevplatform.utils.ViewUtils;

public class DbModelAdapter extends BaseAdapter {

	private Context context;
	protected List items;
	private int layoutId;
	private String[] keys;
	protected int[] textid;
	private View listView;
	private AsyncImageLoader imageLoader;
	private ItemViewHandler itemViewHandler;
	private int[] itemColors;
	private int defaultImgId = R.drawable.img_default;

	private int visibleIfNoImg = View.GONE; // 如果没有图片ImageView如何显示
	private boolean flowMode = false;

	public DbModelAdapter(View listView, List<?> reuslts, int layoutId,
			String[] keys, int[] txtids, ItemViewHandler itemViewHandler) {
		this.context = listView.getContext();
		this.listView = listView;
		this.items = reuslts;
		this.layoutId = layoutId;
		this.keys = keys;
		this.textid = txtids;
		this.itemViewHandler = itemViewHandler;
		this.imageLoader = ViewUtils.imageLoader;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		if (items != null) {
			return items.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			// 没有生成使用的布局
			convertView = LayoutInflater.from(context).inflate(layoutId, null);
			// 创建Holder绑定子元素
			viewHolder = new ViewHolder();
			if (textid != null && textid.length > 0) {
				viewHolder.resultViews = new View[textid.length];
				for (int i = 0; i < textid.length; i++) {
					viewHolder.resultViews[i] = convertView
							.findViewById(textid[i]);
				}
			}
			convertView.setTag(viewHolder);
		} else {
			// 直接从Holder获取绑定的对象
			viewHolder = (ViewHolder) convertView.getTag();
		}
		buildViewContext(position, convertView, viewHolder);
		if (itemColors != null) {

			convertView.setBackgroundColor(context.getResources().getColor(
					itemColors[position % itemColors.length]));
		}
		return convertView;
	}

	/** 具体的视图处理 */
	protected void buildViewContext(int position, View convertView,
			ViewHolder viewHolder) {
		try {
			// 只对有效数据进行操作
			if (items != null && position < items.size()) {
				if (textid != null && textid.length > 0 && keys != null
						&& keys.length > 0) {
					for (int i = 0; i < textid.length; i++) {
						// 获取对应的View
						View view = viewHolder.resultViews[i];
						if (view != null) {
							// 获取对应的值
							Object obj = BeanUtils.getDbFieldValue(
									getItem(position), keys[i]);
							// 将值设置对视图里
							setViewValue(view, obj, keys[i], position);
						}
					}
				}
				if (itemViewHandler != null) {
					itemViewHandler.handleView(convertView, getItem(position),
							position);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void setViewValue(View view, final Object obj, final String key,
			final int position) {
		if (view instanceof TextView && obj == null) {
		}
		if (view instanceof TextView && obj instanceof String) {
			((TextView) view).setText(Html.fromHtml((String) obj));

		} else if (view instanceof ImageView) {
			final ImageView tempImage = (ImageView) view;
			tempImage.setTag(key + position);
			if (!flowMode) {
				loadImage(obj, key, position, tempImage);
			} else {
				loadImage(null, key, position, tempImage);
			}
		}
	}

	/** 加载图片 */
	private void loadImage(Object obj, String key, final int position,
			ImageView tempImage) {
		if (obj != null) {
			if (obj instanceof Integer || obj.getClass() == int.class) {
				// 使用资源绑定图形
				tempImage.setImageResource((Integer) obj);
			} else if (obj instanceof String) {

				// 使用网络请求绑定ImageView
				String url = obj.toString();

				// 查找的KEY
				final String tagKey = key + url + position;
				tempImage.setTag(tagKey);
				// 开启网络请求获取图片
				Bitmap drawables = null;

				// 判断是否使用本地图片
				String Str = url.toString().substring(0, 1);
				if (Str.equals("/")) {
					drawables = getLoacalBitmap(url); // 从本地取图片(在Sdcard中获取)
				} else {
					if (!BeanUtils.isEmpty(url) && URLUtil.isHttpUrl(url)) {

						// 开启网络请求获取图片
						drawables = imageLoader.loadDrawable(url, key,
								new ImageCallback() {
									@Override
									public void imageLoaded(
											Bitmap imageDrawable,
											String imageUrl, String key) {
										if (listView != null) {
											ImageView imageViewByTag = (ImageView) listView
													.findViewWithTag(tagKey);
											if (imageViewByTag != null) {
												setImageValue(imageViewByTag,
														imageDrawable);
											}
										}
									}
								});
					}

				}

				// 图片展示
				setImageValue(tempImage, drawables);
			}
		} else {
			// 图片展示
			setImageValue(tempImage, null);
		}
	}

	/**
	 * 加载本地图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void setImageValue(ImageView imageView, Bitmap drawable) {
		if (imageView != null && drawable != null) {
			try {
				imageView.setImageBitmap(drawable);
				imageView.setVisibility(View.VISIBLE);
			} catch (Exception e) {
			}
		} else if (drawable == null) {
			if (defaultImgId == 0) {
				imageView.setVisibility(visibleIfNoImg);
			} else {
				imageView.setImageResource(defaultImgId);
				imageView.setVisibility(View.VISIBLE);
			}
		}
	}

	public class ViewHolder {
		public View resultViews[];
	}

	public void setItemColors(int[] itemColors) {
		this.itemColors = itemColors;
	}

	/***
	 * 动态修改ListVIiw的方位.
	 * 
	 * @param start
	 *            点击移动的position
	 * @param down
	 *            松开时候的position
	 */
	public void updateOrder(int start, int down) {
		Log.i("from", String.valueOf(start));
		Log.i("to", String.valueOf(down));
		// 获取删除的object.
		Object o = getItem(start);

		items.remove(start);// 删除该项

		items.add(down, o);// 添加删除项

		notifyDataSetChanged();// 刷新ListView
	}

	public int getDefaultImgId() {
		return defaultImgId;
	}

	public void setDefaultImgId(int defaultImgId) {
		this.defaultImgId = defaultImgId;
	}

	public int getVisibleIfNoImg() {
		return visibleIfNoImg;
	}

	public void setVisibleIfNoImg(int visibleIfNoImg) {
		this.visibleIfNoImg = visibleIfNoImg;
	}

}
