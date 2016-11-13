package com.mplus.mplus.adapter.usercenter;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.actorcenter.ActorCenterActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ProjectJobList;
import com.mplus.mplus.utils.DialongUtils;
import com.mplus.mplus.utils.ToastTool;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class SelcedProjectAdapter extends BaseExpandableListAdapter{
	private Context mContext;
	private ArrayList<ProjectJobList> list;
	private String userid=null,actoruserid=null;

	public SelcedProjectAdapter(Context mContext, ArrayList<ProjectJobList> list, String userid, String actoruserid) {
		super();
		this.mContext = mContext;
		this.list = list;
		this.userid = userid;
		this.actoruserid = actoruserid;
	}

	@Override
	public int getGroupCount() {
		return list==null?0:list.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return list==null?0:list.get(groupPosition).getPlans()==null?0:list.get(groupPosition).getPlans().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return list.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return list.get(groupPosition).getPlans().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	
	private class ViewHolder{
		TextView  tv_name;
		ImageView iv_right;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView==null) {
			holder=new ViewHolder();
			convertView=View.inflate(mContext, R.layout.items_selced_userproject, null);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holder.iv_right=(ImageView) convertView.findViewById(R.id.iv_right);
			holder.iv_right.setVisibility(View.VISIBLE);
			AutoUtils.auto(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		ProjectJobList pJobList=list.get(groupPosition);
		if (pJobList!=null) {
			holder.tv_name.setText(pJobList.getMproductName());
		}
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,ViewGroup parent) {
		ViewHolder holder;
		if (convertView==null) {
			holder=new ViewHolder();
			convertView=View.inflate(mContext, R.layout.items_selced_userproject, null);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holder.iv_right=(ImageView) convertView.findViewById(R.id.iv_right);
			LinearLayout.LayoutParams lp=(LayoutParams) holder.tv_name.getLayoutParams();
			lp.setMargins(40,0, 0, 0);
			holder.tv_name.setLayoutParams(lp);
			holder.iv_right.setVisibility(View.GONE);
			AutoUtils.auto(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		final ProjectJobList.plans plans=list.get(groupPosition).getPlans().get(childPosition);
		if (plans!=null) {
			holder.tv_name.setText(plans.getPlanName());
			holder.tv_name.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					DialongUtils.showAskOptionDialog(mContext, userid, actoruserid, plans.getPlanId());
				}
			});
		}
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
	

}
