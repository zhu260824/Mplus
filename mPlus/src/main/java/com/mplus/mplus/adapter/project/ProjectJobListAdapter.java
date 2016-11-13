package com.mplus.mplus.adapter.project;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.paser.project.JobList;
import com.mplus.mplus.utils.LoginUtils;
import com.mplus.mplus.utils.MultipleTextUtils;
import com.mplus.mplus.utils.StringUtils;
import com.mplus.mplus.utils.ToastTool;
import com.mplus.mplus.view.multipletext.MultipleTextViewGroup;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProjectJobListAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<JobList.contents> jobs;
	private String jobId;

	public ProjectJobListAdapter(Context mContext, ArrayList<JobList.contents> jobs) {
		super();
		this.mContext = mContext;
		this.jobs = jobs;
	}

	public void notifyDataSetChanged(ArrayList<JobList.contents> jobs) {
		if (jobs != null) {
			this.jobs.addAll(jobs);
			this.notifyDataSetChanged();
		}
	};

	@Override
	public int getCount() {
		return jobs == null ? 0 : jobs.size();
	}

	@Override
	public Object getItem(int position) {
		return jobs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		TextView tv_apply_job, tv_name, tv_height, tv_weight, tv_time, tv_needexp, tv_roledesc,tv_paycheck,tv_age;
		MultipleTextViewGroup multipleText;
		MultipleTextUtils multipleTextUtils;
		LinearLayout lin_tag;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(parent.getContext(), R.layout.items_project_job_list, null);
			holder.tv_apply_job = (TextView) convertView.findViewById(R.id.tv_apply_job);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_height = (TextView) convertView.findViewById(R.id.tv_height);
			holder.tv_weight = (TextView) convertView.findViewById(R.id.tv_weight);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_needexp = (TextView) convertView.findViewById(R.id.tv_needexp);
			holder.tv_paycheck=(TextView) convertView.findViewById(R.id.tv_paycheck);
			holder.tv_age=(TextView) convertView.findViewById(R.id.tv_age);
			holder.tv_roledesc = (TextView) convertView.findViewById(R.id.tv_roledesc);
			holder.lin_tag = (LinearLayout) convertView.findViewById(R.id.lin_tag);
			holder.multipleText = (MultipleTextViewGroup) convertView.findViewById(R.id.main_rl);
			holder.multipleTextUtils=new MultipleTextUtils(holder.multipleText);
			AutoUtils.auto(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final JobList.contents job = jobs.get(position);
		if (job != null) {
			String role = TextUtils.isEmpty(job.getRole()) ? "" : job.getRole();
			String nickname = TextUtils.isEmpty(job.getNickname()) ? "" : job.getNickname();
			holder.tv_name.setText(role + "   " + nickname);
			holder.tv_height.setText(TextUtils.isEmpty(job.getHeight()) ? "不限制" : job.getHeight());
			holder.tv_weight.setText(TextUtils.isEmpty(job.getWeight()) ? "不限制" : job.getWeight());
			holder.tv_age.setText(TextUtils.isEmpty(job.getAge()) ? "不限制" : job.getAge());
			holder.tv_paycheck.setText(TextUtils.isEmpty(job.getPaycheck()) ? "面议" : job.getPaycheck());
			String starttime = TextUtils.isEmpty(job.getStarttime()) ? "" : job.getStarttime();
			String endtime = TextUtils.isEmpty(job.getEndtime()) ? "" : job.getEndtime();
			String time=starttime + "至" + endtime;
			if (time.length()<=2) {
				holder.tv_time.setText("不限");
			}else {
				holder.tv_time.setText(time);
			}
			if (job.getNeedrelatedexp() == 1) {
				holder.tv_needexp.setText("需要相关经验");
			} else {
				holder.tv_needexp.setText("不需要相关经验");
			}
			String roledesc = TextUtils.isEmpty(job.getRoledesc()) ? "" : job.getRoledesc();
			holder.tv_roledesc.setText(Html.fromHtml("<font color=\"#575757\" size=\"44px\">人物小传：</font>" + roledesc));
			String feature = TextUtils.isEmpty(job.getFeature()) ? "" : job.getFeature();
			ArrayList<String> tags = StringUtils.StringToList(feature);
			if (tags != null && tags.size() >= 1) {
				holder.lin_tag.setVisibility(View.VISIBLE);
				holder.multipleText.removeAllViews();
				holder.multipleTextUtils.AddTag(tags);
			}else {
				holder.lin_tag.setVisibility(View.GONE);
			}
			
			holder.tv_apply_job.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					jobId=job.getPlanid();
					LoginUtils loginUtils=new LoginUtils() {
						
						@Override
						public void DoOnLongin() {
							showLoading();
							RequestManger.PostApplyJob(mContext, UserPaser.GetInstance().userid, jobId, new Listener<RequestCall>() {

								@Override
								public void onResponse(RequestCall response) {
									dismssLoading();
									if (response.getParser().getResultSuccess()) {
										ToastTool.showShortToast(mContext, "申请角色成功！");
									}else {
										if (response.getParser().getResponseMsg()!=null) 
											ToastTool.showShortToast(mContext,response.getParser().getResponseMsg());
									}
								}
								
							}, MHttpTools.getErrorListener(mContext));
						}
					};
					loginUtils.ToLogin((BaseActivity)mContext);
				}
			});
		}
		return convertView;
	}

	
	
	
	public String getJobId() {
		return jobId;
	}


	private void showLoading() {
		try {
			BaseActivity mActivity=(BaseActivity) mContext;
			mActivity.showLoadingDialog();
		} catch (Exception e) {
		}
		
	}
	
	private void dismssLoading() {
		try {
			BaseActivity mActivity=(BaseActivity) mContext;
			mActivity.dismissLoadingDialog();;
		} catch (Exception e) {
		}
		
	}
}
