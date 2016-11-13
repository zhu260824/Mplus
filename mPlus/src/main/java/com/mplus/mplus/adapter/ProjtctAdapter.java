package com.mplus.mplus.adapter;

import java.util.ArrayList;

import com.dtd365.library.Utils.MobclickAgentUtils;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.project.ProjectDetailsActivity;
import com.mplus.mplus.paser.project.ProjectList;
import com.mplus.mplus.paser.project.ProjectList.contents;
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
import android.widget.TextView;

public class ProjtctAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<ProjectList.contents> lists;
	private DisplayImageOptions options;
	
	
	
	public ProjtctAdapter(Context mContext, ArrayList<contents> lists) {
		super();
		this.mContext = mContext;
		this.lists = lists;
		this.options=ImageLoadUtils.getImageProject();
	}
	public int getCount() {
		return lists==null?0:lists.size();
	}
	public void notifyDataSetChanged(ArrayList<ProjectList.contents> lists) {
		if (lists!=null) {
			this.lists.addAll(lists);
			this.notifyDataSetChanged();
		}
	};

	public Object getItem(int position) {
		return lists.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.items_project, null);
			holder.tv_movie_project = (TextView) convertView.findViewById(R.id.tv_movie_project);
			holder.tv_play_state_project = (TextView) convertView.findViewById(R.id.tv_play_state_project);
			holder.tv_director = (TextView) convertView.findViewById(R.id.tv_director);
			holder.tv_role_project = (TextView) convertView.findViewById(R.id.tv_role_project);
			holder.tv_date_project = (TextView) convertView.findViewById(R.id.tv_date_project);
			holder.iv_project_items = (ImageView) convertView.findViewById(R.id.iv_project_items);
			convertView.setTag(holder);
			AutoUtils.autoSize(convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ProjectList.contents project=lists.get(position);
		if (project!=null) {
			holder.tv_movie_project.setText(TextUtils.isEmpty(project.getMproductname())?"":project.getMproductname());
			holder.tv_play_state_project.setText(TextUtils.isEmpty(project.getStage())?"":project.getStage());
			holder.tv_director.setText(TextUtils.isEmpty(project.getDirector())?" 暂无":" "+project.getDirector());
			holder.tv_role_project.setText(TextUtils.isEmpty(project.getJobCount())?" 0"+mContext.getString(R.string.projectlist_role):project.getJobCount()+" "+mContext.getString(R.string.projectlist_role));
			holder.tv_date_project.setText(TextUtils.isEmpty(project.getShootingTime())?"":project.getShootingTime());
			ImageLoader.getInstance().displayImage(project.getShortcut(), holder.iv_project_items, options);
			convertView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent=new Intent(mContext,ProjectDetailsActivity.class);
					intent.putExtra(ProjectDetailsActivity.MPRODUCTID, project.getMproductid());
					MobclickAgentUtils.MobclickEventName(mContext, "0004","查看项目详情",project.getMproductname());
					mContext.startActivity(intent);
				}
			});
		}
		return convertView;
	}

	private class ViewHolder {
		TextView tv_movie_project, tv_play_state_project, tv_director, tv_role_project, tv_date_project;
		ImageView iv_project_items;
	}
}
