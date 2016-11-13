package com.mplus.mplus.view.ad;

import java.util.ArrayList;

import com.mplus.mplus.R;
import com.mplus.mplus.paser.project.ProjectList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * 对自定义组件AdGallery进行了一次封装
 * 包含对图片标题和当前位置指示器(RadioGroup)的操作
 * @author wly
 *
 */
public class AdGalleryHelper {
	private AdGallery mAdGallery; //无限滚动Gallery
	private RadioGroup mRadioGroup; //滚动标记组件
	private Context mContext;
	private LayoutInflater mInflater;
	private RelativeLayout galleryLayout;
	
	@SuppressLint("InflateParams")
	public AdGalleryHelper(Context context,final ArrayList<ProjectList.top> adlist,int switchTime) {
		
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		galleryLayout = (RelativeLayout)mInflater.inflate(R.layout.adgallery_hellper, null);
		mRadioGroup = (RadioGroup)galleryLayout.findViewById(R.id.home_pop_gallery_mark);
		//添加RadioButton
//		Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point_on);
//		LayoutParams params = new LayoutParams(dpToPx(mContext, b.getWidth()), dpToPx(mContext, b.getHeight()));
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		if (adlist!=null && adlist.size()>=1) {
			for (int i = 0; i < adlist.size(); i++) {
				RadioButton _rb = new RadioButton(mContext);
				_rb.setPadding(5, 0, 5, 0);
				_rb.setId(0x1234 + i);
				_rb.setButtonDrawable(mContext.getResources().getDrawable(R.drawable.selced_point));
				mRadioGroup.addView(_rb, params);
			}
			mAdGallery = (AdGallery)galleryLayout.findViewById(R.id.gallerypop);
			mAdGallery.init(adlist, switchTime,new OnGallerySwitchListener() {
				public void onGallerySwitch(int position) {
					if (mRadioGroup != null) {
						mRadioGroup.check(mRadioGroup.getChildAt(position).getId());
					}
				}
			});
		}
	}
	
	/**
	 * 向外开放布局对象，使得可以将布局对象添加到外部的布局中去
	 * @return
	 */
	public RelativeLayout getLayout() {
		return galleryLayout;
	}
	
	/**
	 * 开始自动循环切换
	 */
	public void startAutoSwitch() {
		mAdGallery.setRunFlag(true);
		mAdGallery.startAutoScroll();
	}

	public void stopAutoSwitch() {
		mAdGallery.setRunFlag(true);
	}
	
	/**
	 * 图片切换回调接口
	 * @author wly
	 *
	 */
	interface OnGallerySwitchListener {
		public void onGallerySwitch(int position);
	}
	public static int dpToPx(Context context, int dp) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	public static int pxToDp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}

