package com.mplus.mplus.adapter.pushproject;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mplus.mplus.R;
import com.mplus.mplus.paser.pushproject.RoleType;
import com.zhy.autolayout.utils.AutoUtils;

public class RoleSelcedAdapter extends BaseAdapter{
	private ArrayList<RoleType> lists;

	public RoleSelcedAdapter(ArrayList<RoleType> lists) {
		super();
		this.lists=lists;
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
		TextView tv_name;
		ImageView iv_ico;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView==null) {
			holder=new ViewHolder();
			convertView=View.inflate(parent.getContext(), R.layout.items_role_selced, null);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holder.iv_ico=(ImageView) convertView.findViewById(R.id.iv_ico);
			convertView.setTag(holder);
			AutoUtils.autoSize(convertView);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		RoleType roleType=lists.get(position);
		holder.tv_name.setText(roleType.role);
		holder.tv_name.setTag(roleType);
		if (roleType.isselced) {
			holder.iv_ico.setVisibility(View.VISIBLE);
		}else {
			holder.iv_ico.setVisibility(View.GONE);
		}
		return convertView;
	}

}
