package com.mplus.mplus.adapter.usercenter;

import java.util.ArrayList;

import com.mplus.mplus.R;
import com.mplus.mplus.paser.actor.ActorTag;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EditActorTagAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<ActorTag> list; 
	
	public EditActorTagAdapter(Context mContext, ArrayList<ActorTag> list) {
		super();
		this.mContext = mContext;
		this.list = list;
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
		TextView tv_name;
		ImageView iv_ico;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView==null) {
			holder=new ViewHolder();
			convertView=View.inflate(mContext, R.layout.items_role_selced, null);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holder.iv_ico=(ImageView) convertView.findViewById(R.id.iv_ico);
			convertView.setTag(holder);
			AutoUtils.autoSize(convertView);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		final ActorTag tag=list.get(position);
		if (tag!=null) {
			holder.tv_name.setText(tag.getName());
			if (tag.isSelced()) {
				holder.iv_ico.setVisibility(View.VISIBLE);
			}else {
				holder.iv_ico.setVisibility(View.GONE);
			}
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (tag.isSelced()) {
						holder.iv_ico.setVisibility(View.GONE);
						tag.setSelced(false);
					}else {
						holder.iv_ico.setVisibility(View.VISIBLE);
						tag.setSelced(true);
					}
				}
			});
		}
		return convertView;
	}

	public ArrayList<ActorTag> getList() {
		return list;
	}
}
