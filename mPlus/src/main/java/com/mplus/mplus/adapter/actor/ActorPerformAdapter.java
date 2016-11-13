package com.mplus.mplus.adapter.actor;

import java.util.ArrayList;

import com.mplus.mplus.R;
import com.mplus.mplus.paser.actor.ActorPerform;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ActorPerformAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<ActorPerform> list;
	private DisplayImageOptions options;

	public ActorPerformAdapter(Context mContext, ArrayList<ActorPerform> list) {
		super();
		this.mContext = mContext;
		this.list = list;
		this.options=ImageLoadUtils.getActorHistoryOptions();
	}

	@Override
	public int getCount() {
		return list==null?0:list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	private class ViewHolder{
		ImageView iv_ico;
		TextView tv_name,tv_time,tv_desc;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView==null) {
			holder=new ViewHolder();
			convertView=View.inflate(mContext, R.layout.items_actor_perform_history,null);
			holder.iv_ico=(ImageView) convertView.findViewById(R.id.iv_ico);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_time=(TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_desc=(TextView) convertView.findViewById(R.id.tv_desc);
			AutoUtils.auto(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		ActorPerform perform=list.get(position);
		if (perform!=null) {
			if (!TextUtils.isEmpty(perform.getTitle())) {
				holder.tv_name.setVisibility(View.VISIBLE);
				holder.tv_name.setText("《"+perform.getTitle()+"》");
			}else {
				holder.tv_name.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(perform.getTime())) {
				holder.tv_time.setVisibility(View.VISIBLE);
				holder.tv_time.setText(perform.getTime()+"年");
			}else {
				holder.tv_time.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(perform.getRole())) {
				holder.tv_desc.setVisibility(View.VISIBLE);
				holder.tv_desc.setText("饰演："+perform.getRole());
			}else {
				holder.tv_desc.setVisibility(View.GONE);
			}
			ImageLoader.getInstance().displayImage(perform.getMaterialUrl(), holder.iv_ico, options);
		}
		return convertView;
	}

}
