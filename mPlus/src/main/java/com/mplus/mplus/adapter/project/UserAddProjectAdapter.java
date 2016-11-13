package com.mplus.mplus.adapter.project;

import java.util.ArrayList;

import com.mplus.mplus.R;
import com.mplus.mplus.activity.project.JobDetailsActivity;
import com.mplus.mplus.paser.project.UserAddJobList;
import com.mplus.mplus.paser.project.UserAddJobList.contents;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserAddProjectAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<UserAddJobList.contents> list;
	private DisplayImageOptions options;

	public UserAddProjectAdapter(Context mContext, ArrayList<contents> list) {
		super();
		this.mContext = mContext;
		this.list = list;
		this.options = ImageLoadUtils.getLongImageProject();
	}

	@Override
	public int getCount() {
		return list==null?0:list.size();
	}
	
	public void notifyDataSetChanged(ArrayList<UserAddJobList.contents> lists) {
		if (lists!=null) {
			this.list.addAll(lists);
			this.notifyDataSetChanged();
		}
	};
	
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
		TextView  tv_name,tv_static,tv_filmtype,tv_director,tv_screenwriter,
		tv_shootingtime ,tv_location,tv_jobname,tv_height,tv_desc;
		LinearLayout lin_filmtype,lin_director,lin_screenwriter,lin_desc;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView==null) {
			holder=new ViewHolder();
			convertView=View.inflate(mContext, R.layout.items_user_add_project,null);
			holder.iv_ico=(ImageView) convertView.findViewById(R.id.iv_ico);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_static=(TextView) convertView.findViewById(R.id.tv_static);
			holder.tv_filmtype=(TextView) convertView.findViewById(R.id.tv_filmtype);
			holder.tv_director=(TextView) convertView.findViewById(R.id.tv_director);
			holder.tv_screenwriter=(TextView) convertView.findViewById(R.id.tv_screenwriter);
			holder.tv_shootingtime=(TextView) convertView.findViewById(R.id.tv_shootingtime);
			holder.tv_location=(TextView) convertView.findViewById(R.id.tv_location);
			holder.tv_jobname=(TextView) convertView.findViewById(R.id.tv_jobname);
//			holder.tv_height=(TextView) convertView.findViewById(R.id.tv_height);
			holder.tv_desc=(TextView) convertView.findViewById(R.id.tv_desc);
			holder.lin_desc=(LinearLayout) convertView.findViewById(R.id.lin_desc);
			holder.lin_filmtype=(LinearLayout) convertView.findViewById(R.id.lin_filmtype);
			holder.lin_director=(LinearLayout) convertView.findViewById(R.id.lin_director);
			holder.lin_screenwriter=(LinearLayout) convertView.findViewById(R.id.lin_screenwriter);
			AutoUtils.auto(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		final UserAddJobList.contents job=list.get(position);
		if (job!=null) {
			ImageLoader.getInstance().displayImage(job.getShortcut(),holder.iv_ico,options);
			holder.tv_name.setText(TextUtils.isEmpty(job.getMproductName())?"":job.getMproductName());
			holder.tv_static.setText(TextUtils.isEmpty(job.getStage())?"":job.getStage());
			if (TextUtils.isEmpty(job.getFilmType())) {
				holder.lin_filmtype.setVisibility(View.GONE);
			}else {
				holder.lin_filmtype.setVisibility(View.VISIBLE);
				holder.tv_filmtype.setText(job.getFilmType());
			}
			if (TextUtils.isEmpty(job.getDirector())) {
				holder.lin_director.setVisibility(View.GONE);
			}else {
				holder.lin_director.setVisibility(View.VISIBLE);
				holder.tv_director.setText(job.getDirector());
			}
			if (TextUtils.isEmpty(job.getScreenwriter())) {
				holder.lin_screenwriter.setVisibility(View.GONE);
			}else {
				holder.lin_screenwriter.setVisibility(View.VISIBLE);
				holder.tv_screenwriter.setText(job.getScreenwriter());
			}
			holder.tv_shootingtime.setText(TextUtils.isEmpty(job.getShootingTime())?"":job.getShootingTime());
			holder.tv_location.setText(TextUtils.isEmpty(job.getLocation())?"":job.getLocation());
			String planname=TextUtils.isEmpty(job.getPlanName())?"":job.getPlanName();
			String nickname=TextUtils.isEmpty(job.getNickname())?"":job.getNickname();
			holder.tv_jobname.setText(planname+"   "+nickname);
//			String height=TextUtils.isEmpty(job.getPlanHeight())?"":job.getPlanHeight();
//			String weight=TextUtils.isEmpty(job.getPlanWeight())?"":job.getPlanWeight();
//			holder.tv_height.setText(height+"  "+weight);
			String desc=TextUtils.isEmpty(job.getPlanDesc())?"":job.getPlanDesc();
			holder.tv_desc.setText("角色描述："+desc);
			holder.lin_desc.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(mContext,JobDetailsActivity.class);
					intent.putExtra("planid", job.getPlanId());
					intent.putExtra("IntentType", 2);
					mContext.startActivity(intent);
				}
			});
		}
		return convertView;
	}

}
