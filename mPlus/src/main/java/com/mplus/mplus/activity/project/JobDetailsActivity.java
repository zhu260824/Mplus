package com.mplus.mplus.activity.project;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.login.LoginActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.paser.project.JobDetails;
import com.mplus.mplus.paser.project.JobDetailsPaser;
import com.mplus.mplus.utils.MultipleTextUtils;
import com.mplus.mplus.utils.StringUtils;
import com.mplus.mplus.utils.ToastTool;
import com.mplus.mplus.view.multipletext.MultipleTextViewGroup;
import com.umeng.socialize.utils.Log;

/**
 * 职位详情
 *
 */
public class JobDetailsActivity extends BaseActivity implements OnClickListener {
	private TextView tv_name, tv_needrelatedexp,tv_paycheck,tv_height,tv_weight,tv_age,tv_time,tv_roledesc;
//	tv_location, tv_worktime,tv_points, tv_roledsc, tv_remuneration_for_a_movie_actor,tv_needhight;
	private LinearLayout lin_tag;
	private Button  btnLoading;
	private JobDetails jobDetails;
	private String planid=null,userid=null;
	private MultipleTextViewGroup multipleText;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_details);
		initTitle(getString(R.string.job_details));
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_needrelatedexp = (TextView) findViewById(R.id.tv_needexp);
		tv_paycheck = (TextView) findViewById(R.id.tv_paycheck);
		tv_height = (TextView) findViewById(R.id.tv_height);
		tv_weight = (TextView) findViewById(R.id.tv_weight);
		tv_age = (TextView) findViewById(R.id.tv_age);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_roledesc = (TextView) findViewById(R.id.tv_roledesc);
		lin_tag = (LinearLayout) findViewById(R.id.lin_tag);
		multipleText = (MultipleTextViewGroup) findViewById(R.id.main_rl);
		btnLoading = (Button) findViewById(R.id.btnLoading);
		btnLoading.setOnClickListener(this);
		new inintDataTask().execute();
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLoading:
			if (MPlusApplication.isLogin) {
				showLoadingDialog();
				RequestManger.PostApplyJob(JobDetailsActivity.this, UserPaser.GetInstance().userid, jobDetails.planid, new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						dismissLoadingDialog();
						if (response.getParser().getResultSuccess()) {
							ToastTool.showShortToast(JobDetailsActivity.this, "申请角色成功！");
						}else {
							if (response.getParser().getResponseMsg()!=null) 
								ToastTool.showShortToast(JobDetailsActivity.this,response.getParser().getResponseMsg());
						}
					}
				}, errorListener);
			} else {
				startActivity(new Intent(JobDetailsActivity.this,LoginActivity.class));
			}
			break;
		default:
			break;
		}
	}

	private class inintDataTask extends AsyncTask<Integer, Integer, JobDetails> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			int IntentType=getIntent().getIntExtra("IntentType", 1);
			if (IntentType==2) 
				btnLoading.setVisibility(View.GONE);
		}

		@Override
		protected JobDetails doInBackground(Integer... params) {
			planid=getIntent().getStringExtra("planid");
			if (MPlusApplication.isLogin) 
				userid=UserPaser.GetInstance().userid;
			return null;
		}

		@Override
		protected void onPostExecute(JobDetails result) {
			super.onPostExecute(result);
			showLoadingDialog();
			RequestManger.getJobDetails(JobDetailsActivity.this, planid,userid,
					new Listener<RequestCall>() {

						@Override
						public void onResponse(RequestCall response) {
							dismissLoadingDialog();
							if (response.getParser().getResultSuccess()) {
								jobDetails = JobDetailsPaser.GetInstance();
								inintData(jobDetails);
							}
						}
					}, errorListener);
		}
	}

	private void inintData(JobDetails job) {
		if (job != null) {
			setTitle("角色详情");
			tv_name.setText(job.role);
//			String paycheckString = "面议";
//			String heightString = "无要求";
//			String weightString = "无要求";
//			String ageString = "无要求";
//			if (jobDetails.height == null||jobDetails.height.equals("无")||jobDetails.height.equals("")) {
//				
//			}else{
//				heightString=jobDetails.height;
//			}
//			if (jobDetails.paycheck == null||jobDetails.paycheck.equals("无")||jobDetails.paycheck.equals("")) {
//				
//			}else{
//				paycheckString=jobDetails.paycheck;
//			}
			
			tv_height.setText(TextUtils.isEmpty(job.height) ? "不限制" : job.height);
			tv_weight.setText(TextUtils.isEmpty(job.planWeight) ? "不限制" : job.planWeight);
			if (TextUtils.isEmpty(job.planAge)) {
				tv_age.setText("不限制");
			}else if (job.planAge.equals("不限制")) {
				tv_age.setText("不限制");
			}else {
				tv_age.setText(job.planAge+"岁");
			}
			tv_paycheck.setText(TextUtils.isEmpty(job.paycheck) ? "面议" : job.paycheck);
			String starttime = TextUtils.isEmpty(job.starttime) ? "" : job.starttime;
			String endtime = TextUtils.isEmpty(job.endtime) ? "" : job.endtime;
			String time=starttime + "~" + endtime;
			if (time.length()<=2) {
				tv_time.setText("不限");
			}else {
				tv_time.setText(time);
			}
			if (job.needrelatedexp == "1") {
				tv_needrelatedexp.setText("需要相关经验");
			} else {
				tv_needrelatedexp.setText("不需要相关经验");
			}
			String roledesc = TextUtils.isEmpty(job.roledesc) ? "" : job.roledesc;
			tv_roledesc.setText(Html.fromHtml("<font color=\"#575757\" size=\"44px\">人物小传：</font>" + roledesc));
			
			String feature = TextUtils.isEmpty(job.planFeature) ? "" : job.planFeature;
			ArrayList<String> tags = StringUtils.StringToList(feature);
			if (tags != null && tags.size() >= 1) {
				lin_tag.setVisibility(View.VISIBLE);
				MultipleTextUtils multipleTextUtils=new MultipleTextUtils(multipleText, tags);
				multipleTextUtils.AddTag();
			Log.e("planFeature="+feature);
			Log.e("tags="+tags.size());
			
			}else {
				lin_tag.setVisibility(View.GONE);
			}
		}
	}
	

}
