package com.mplus.mplus.adapter.main;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mplus.mplus.R;
import com.mplus.mplus.activity.actorcenter.ActorCenterActivity;
import com.mplus.mplus.paser.actor.ActorList;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.mplus.mplus.utils.ToastTool;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

public class starsfaceResultActivityAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<ActorList> actors;
	private DisplayImageOptions options;

	public starsfaceResultActivityAdapter(Context mContext,
			ArrayList<ActorList> actors) {
		super();
		this.mContext = mContext;
		this.actors = actors;
		this.options = ImageLoadUtils.getImageOptions();
	}

	public void notifyDataSetChanged(ArrayList<ActorList> lists) {
		if (lists!=null) {
			this.actors.addAll(lists);
			this.notifyDataSetChanged();
		}
	};
	
	@Override
	public int getCount() {
		return actors.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	private class ViewHolder {
		ImageView iv_ico;
		TextView tv_name, tv_height, tv_weight;
	}

	@Override
	public View getView(final int position, View convertView,
			ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(parent.getContext(),R.layout.items_gridview_main_person_image, null);
			holder.iv_ico = (ImageView) convertView.findViewById(R.id.iv_ico);
			// holder.iv_sex=(ImageView) convertView.findViewById(R.id.iv_sex);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_height = (TextView) convertView.findViewById(R.id.tv_height);
			holder.tv_weight = (TextView) convertView.findViewById(R.id.tv_weight);
			AutoUtils.auto(convertView);
			convertView.setTag(holder);
			AutoUtils.autoSize(convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ActorList actor = actors.get(position);
		if (actor != null) {
			ImageLoader.getInstance().displayImage(actor.getAvatar(),holder.iv_ico, options);
			holder.tv_name.setText(TextUtils.isEmpty(actor.getName()) ? "": actor.getName());
			if (!TextUtils.isEmpty(actor.getHeight()) &&  !actor.getHeight().equals("0")) {
				holder.tv_height.setVisibility(View.VISIBLE);
				holder.tv_height.setText(actor.getHeight()+"cm");
			}else {
				holder.tv_height.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(actor.getWeight()) &&  !actor.getWeight().equals("0")) {
				holder.tv_weight.setVisibility(View.VISIBLE);
				holder.tv_weight.setText(actor.getWeight()+"kg");
			}else {
				holder.tv_weight.setVisibility(View.GONE);
			}
		}
		holder.iv_ico.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, ActorCenterActivity.class);
				intent.putExtra(ActorCenterActivity.CUSTOMERID,actor.getCustomerId());
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}
	


}
