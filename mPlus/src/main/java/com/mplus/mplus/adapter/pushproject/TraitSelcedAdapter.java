package com.mplus.mplus.adapter.pushproject;

import java.util.ArrayList;

import com.mplus.mplus.R;
import com.mplus.mplus.activity.pushproject.TraitSelcedActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.paser.actor.ActorTag;
import com.mplus.mplus.utils.StringUtils;
import com.zhy.autolayout.utils.AutoUtils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TraitSelcedAdapter extends BaseAdapter{
	private boolean isAllSelced=false;
	private ArrayList<ActorTag> lists;
	private int inttype;
	private BaseActivity baseActivity;

	public TraitSelcedAdapter(BaseActivity baseActivity,ArrayList<ActorTag> lists,boolean isAllSelced,int inttype) {
		super();
		this.lists=lists;
		this.isAllSelced = isAllSelced;
		this.inttype=inttype;
		this.baseActivity=baseActivity;
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
		final ViewHolder holder;
		if (convertView==null) {
			holder=new ViewHolder();
			convertView=View.inflate(parent.getContext(), R.layout.items_role_selced, null);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holder.iv_ico=(ImageView) convertView.findViewById(R.id.iv_ico);
			if (isAllSelced) {
				holder.iv_ico.setVisibility(View.VISIBLE);
			}else {
				holder.iv_ico.setVisibility(View.GONE);
			}
			convertView.setTag(holder);
			AutoUtils.autoSize(convertView);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		final ActorTag tage=lists.get(position);
		if (tage!=null) {
			holder.tv_name.setText(TextUtils.isEmpty(tage.getName())?"":tage.getName());
			if (tage.isSelced()) {
				holder.iv_ico.setVisibility(View.VISIBLE);
			}else {
				holder.iv_ico.setVisibility(View.GONE);
			}
			
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (inttype==4) {
						Intent backdata=new Intent();
						backdata.putExtra("data", tage.getName());
						baseActivity.setResult(Activity.RESULT_OK,backdata);
						baseActivity.finish();
					}else {
						if (tage.isSelced()) {
							holder.iv_ico.setVisibility(View.GONE);
							tage.setSelced(false);
						}else {
							holder.iv_ico.setVisibility(View.VISIBLE);
							tage.setSelced(true);
						}
					}
				}
			});;
		}
		return convertView;
	}

	public String getSelcedList(){
		String slist=null;
		ArrayList<String> list=new ArrayList<String>();
		for (ActorTag actorTag : lists) {
			if (actorTag.isSelced()) 
				list.add(actorTag.getName());
		}
		slist=StringUtils.ListToString(list);
		return slist;
	}
	
	
}
