package com.mplus.mplus.adapter;

import java.util.ArrayList;

import com.mplus.mplus.R;
import com.mplus.mplus.activity.temporary.ProjectNameActivity;
import com.mplus.mplus.paser.SimpleProject;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.mplus.mplus.utils.ToastTool;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 我的项目
 * @author zhaochaoyue
 *
 */
public class MyProjectAdapter extends BaseAdapter {

	private ArrayList<SimpleProject> list;
	private DisplayImageOptions options;
	private Context context;

	public MyProjectAdapter(Context context, ArrayList<SimpleProject> list) {
		super();
		this.context = context;

		this.list = list;
		options = ImageLoadUtils.getLongImageProject();
	}

	public void notifyData(ArrayList<SimpleProject> projectlist) {
		for (int i = 0; i < projectlist.size(); i++) {
			list.add(projectlist.get(i));
		}

		MyProjectAdapter.this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
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
		TextView tv_state_my_project, tv_type_my_project, tv_director, tv_date_my_project, tv_name_my_project,
				tv_position_my_project, tv_apply_for_my_project, tv_place_my_project;
		ImageView iv_my_project,iv_stay_audit;
	
		LinearLayout ll_my_project;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(parent.getContext(), R.layout.items_my_project, null);
			holder.iv_my_project = (ImageView) convertView.findViewById(R.id.iv_my_project);
			holder.iv_stay_audit = (ImageView) convertView.findViewById(R.id.iv_stay_audit);
			holder.tv_name_my_project = (TextView) convertView.findViewById(R.id.tv_name_my_project);
			holder.tv_state_my_project = (TextView) convertView.findViewById(R.id.tv_state_my_project);
			holder.tv_type_my_project = (TextView) convertView.findViewById(R.id.tv_type_my_project);
			holder.tv_director = (TextView) convertView.findViewById(R.id.tv_director);
			holder.tv_date_my_project = (TextView) convertView.findViewById(R.id.tv_date_my_project);
			holder.tv_place_my_project = (TextView) convertView.findViewById(R.id.tv_place_my_project);
			holder.tv_position_my_project = (TextView) convertView.findViewById(R.id.tv_position_my_project);
			holder.tv_apply_for_my_project = (TextView) convertView.findViewById(R.id.tv_apply_for_my_project);
			holder.ll_my_project=(LinearLayout)convertView.findViewById(R.id.ll_my_project);
			convertView.setTag(holder);
			AutoUtils.autoSize(convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final SimpleProject sProject = list.get(position);
		if (sProject != null) {
			holder.tv_name_my_project.setText(sProject.mproductname == null ? "" : sProject.mproductname);
			holder.tv_state_my_project.setText(sProject.stage == null ? "" : sProject.status);
			holder.tv_type_my_project.setText(sProject.filmtype == null ? "" : sProject.filmtype);
			holder.tv_director.setText(sProject.director == null ? "" : sProject.director);
			holder.tv_date_my_project.setText(sProject.shootingtime == null ? "": sProject.shootingtime);
			holder.tv_place_my_project.setText(sProject.location == null ? "":sProject.location);
			holder.tv_position_my_project.setText(sProject.plannum == null ? "0":sProject.plannum);
			holder.tv_apply_for_my_project.setText(sProject.applynum == null ? "0":sProject.applynum);
			ImageLoader.getInstance().displayImage(sProject.shortcut, holder.iv_my_project, options);
			if(sProject.status.equals("待审核")){
				holder.iv_stay_audit.setVisibility(View.VISIBLE);
			}else{
				holder.iv_stay_audit.setVisibility(View.GONE);
			}
			holder.ll_my_project.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (sProject.status.equals("已通过")) {
						Intent intent = new Intent(context, ProjectNameActivity.class);
						intent.putExtra("mproductname", sProject.mproductname);
						intent.putExtra("mproductid", sProject.mproductid);
						context.startActivity(intent);
					} else {
						ToastTool.showShortToast(context, "请等待项目通过后再发布角色需求");
					}
				}
			});
		}
		return convertView;
	}

}
