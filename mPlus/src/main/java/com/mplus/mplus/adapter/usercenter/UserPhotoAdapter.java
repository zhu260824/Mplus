package com.mplus.mplus.adapter.usercenter;

import java.util.ArrayList;

import com.mplus.mplus.R;
import com.mplus.mplus.activity.actorcenter.PictureBigLookActivity;
import com.mplus.mplus.paser.actor.ActorPhotoList;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class UserPhotoAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<ActorPhotoList> lists;
	private DisplayImageOptions options;
	
	public UserPhotoAdapter(Context mContext, ArrayList<ActorPhotoList> lists) {
		super();
		this.mContext = mContext;
		this.lists = lists;
		this.options = ImageLoadUtils.getImageOptions();
	}

	@Override
	public int getCount() {
		return lists==null?0:lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder{
		ImageView iv_ico;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView==null) {
			holder=new ViewHolder();
			convertView=View.inflate(mContext, R.layout.items_user_gridview_photo, null);
			holder.iv_ico=(ImageView) convertView.findViewById(R.id.iv_ico);	
			AutoUtils.autoSize(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		final ActorPhotoList photo=lists.get(position);
		if (photo!=null) {
			ImageLoader.getInstance().displayImage(photo.getMaterialUrl(), holder.iv_ico, options);
			holder.iv_ico.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(mContext,PictureBigLookActivity.class);
					intent.putExtra("selcedPic", photo.getMaterialUrl());
					intent.putExtra("images", getImages());
					mContext.startActivity(intent);
				}
			});
		}
		return convertView;
	}
	
	private ArrayList<String> getImages() {
		ArrayList<String> list=new ArrayList<String>();
		for (ActorPhotoList photo : lists) {
			list.add(photo.getMaterialUrl());
		}
		return list;
	}

}
