package com.mplus.mplus.activity.actorcenter;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.mplus.mplus.view.pic.ExtendedViewPager;
import com.mplus.mplus.view.pic.TouchImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PictureBigLookActivity extends BaseActivity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_big_look);
	    ExtendedViewPager mViewPager = (ExtendedViewPager) findViewById(R.id.view_pager);
	    ArrayList<String> images=getIntent().getStringArrayListExtra("images");
	    if (images!=null && images.size()>=1) {
	    	mViewPager.setAdapter(new TouchImageAdapter(images));
	    	String selcedPic=getIntent().getStringExtra("selcedPic");
	    	if (selcedPic!=null) {
				mViewPager.setCurrentItem(images.indexOf(selcedPic));
			}
	    }
	}
	 private class TouchImageAdapter extends PagerAdapter {
	        private ArrayList<String> images;
	        private DisplayImageOptions options;
	        
	        public TouchImageAdapter(ArrayList<String> images) {
				super();
				this.images = images;
				options = ImageLoadUtils.getImageOptions();
			}

			@Override
	        public int getCount() {
	        	return images==null?0:images.size();
	        }

	        @Override
	        public View instantiateItem(ViewGroup container, int position) {
	            TouchImageView img = new TouchImageView(container.getContext());
	            ImageLoader.getInstance().displayImage(images.get(position), img, options);
	            container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
	            img.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						PictureBigLookActivity.this.finish();
					}
				});
	            return img;
	        }

	        @Override
	        public void destroyItem(ViewGroup container, int position, Object object) {
	            container.removeView((View) object);
	        }

	        @Override
	        public boolean isViewFromObject(View view, Object object) {
	            return view == object;
	        }

	    }
}
