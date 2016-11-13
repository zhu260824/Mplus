package com.mplus.mplus.adapter.project;

import java.util.ArrayList;

import com.mplus.mplus.R;
import com.mplus.mplus.activity.actorcenter.ActorCenterActivity;
import com.mplus.mplus.paser.actor.ActorList;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.DialongUtils;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResultAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<ActorList> list;
	private String planid;
	private DisplayImageOptions options;
	
	public SearchResultAdapter(Context mContext, ArrayList<ActorList> list,String planid) {
		super();
		this.mContext = mContext;
		this.list = list;
		this.planid=planid;
		this.options=ImageLoadUtils.getImageOptions();
	}

	public void notifyDataSetChanged(ArrayList<ActorList> lists) {
		if (lists!=null) {
			this.list.addAll(lists);
			this.notifyDataSetChanged();
		}
	};
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

	private class ViewHolder {
		ImageView iv_ico;
		TextView tv_name,tv_height,tv_weight,tv_age;
		Button tv_delete,tv_add_option;
	}

	@Override
	public View getView(final int position, View convertView,ViewGroup parent) {
		final ViewHolder holder;
		if (convertView==null) {
			holder=new ViewHolder();
			convertView = View.inflate(mContext,R.layout.items_collect_stars, null);
			holder.iv_ico=(ImageView) convertView.findViewById(R.id.iv_ico);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_height=(TextView) convertView.findViewById(R.id.tv_height);
			holder.tv_weight=(TextView) convertView.findViewById(R.id.tv_weight);
			holder.tv_age=(TextView) convertView.findViewById(R.id.tv_age);
			holder.tv_delete=(Button) convertView.findViewById(R.id.tv_delete);
			holder.tv_add_option=(Button) convertView.findViewById(R.id.tv_add_option);
			holder.tv_delete.setVisibility(View.GONE);
			AutoUtils.auto(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		final ActorList actor=list.get(position);
		if (actor!=null) {
			ImageLoader.getInstance().displayImage(actor.getAvatar(), holder.iv_ico,options);
			holder.tv_name.setText(TextUtils.isEmpty(actor.getName())?"":actor.getName());
			holder.tv_height.setText(TextUtils.isEmpty(actor.getHeight())?"0cm":actor.getHeight()+"cm");
			holder.tv_weight.setText(TextUtils.isEmpty(actor.getWeight())?"0kg":actor.getWeight()+"kg");
			holder.tv_age.setText(TextUtils.isEmpty(actor.getAge())?"0岁":actor.getAge()+"岁");
			holder.iv_ico.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(mContext,ActorCenterActivity.class);
					intent.putExtra(ActorCenterActivity.CUSTOMERID, actor.getCustomerId());
					intent.putExtra("planid", planid);
					mContext.startActivity(intent);
				}
			});
		
			holder.tv_add_option.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					DialongUtils.showAskOptionDialog(mContext, UserPaser.GetInstance().userid, actor.getUserId(), planid);
				}
			});
		}
		return convertView;
	}


}
