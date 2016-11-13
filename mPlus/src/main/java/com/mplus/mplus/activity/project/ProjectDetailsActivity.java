package com.mplus.mplus.activity.project;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mplus.mplus.R;
import com.mplus.mplus.adapter.project.ProjectJobListAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.SimpleProject;
import com.mplus.mplus.paser.common.Page;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.paser.project.JobList.contents;
import com.mplus.mplus.paser.project.JobListPaser;
import com.mplus.mplus.paser.project.ProjectDetailsPaser;
import com.mplus.mplus.utils.AddPlatformTool;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.mplus.mplus.utils.LoginUtils;
import com.mplus.mplus.utils.ToastTool;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ProjectDetailsActivity extends BaseActivity {
	public final static String MPRODUCTID="mproductid";//项目ID
	private ImageView iv_ico,iv_more;
	private TextView tv_name,tv_type,tv_director,tv_shootingtime,tv_location,tv_summary,tv_stage;
	private LinearLayout lin_more;
//	private MultipleTextViewGroup multipleText;
	private PullToRefreshListView mPullToRefreshListView;
	private String mproductid=null,userid=null;
	private AddPlatformTool platformTool;
	private ProjectJobListAdapter jAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_details);
		initTitle(getString(R.string.project_simaple_details));
		initShowRight(R.drawable.ico_share, null, true, false,0);
		View headerView=View.inflate(ProjectDetailsActivity.this, R.layout.heard_project_details, null);
		iv_ico=(ImageView)headerView.findViewById(R.id.iv_ico);
		iv_more=(ImageView) headerView.findViewById(R.id.iv_more);
		tv_name=(TextView) headerView.findViewById(R.id.tv_name);
		tv_type=(TextView) headerView.findViewById(R.id.tv_type);
		tv_director=(TextView) headerView.findViewById(R.id.tv_director);
		tv_shootingtime=(TextView) headerView.findViewById(R.id.tv_shootingtime);
		tv_location=(TextView) headerView.findViewById(R.id.tv_location);
		tv_stage=(TextView) headerView.findViewById(R.id.tv_stage);
		tv_summary=(TextView) headerView.findViewById(R.id.tv_summary);
		lin_more=(LinearLayout) headerView.findViewById(R.id.lin_more);
//		multipleText=(MultipleTextViewGroup) headerView.findViewById(R.id.main_rl);
		lin_more.setOnClickListener(new OnClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				if (tv_summary.getMaxLines()==2) {
					iv_more.setImageResource(R.drawable.close_more_content);
					tv_summary.setMaxLines(10000);
				}else {
					iv_more.setImageResource(R.drawable.open_more_content);
					tv_summary.setMaxLines(2);
				}
			}
		});
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.mPullToRefreshListView);
		mPullToRefreshListView.getRefreshableView().addHeaderView(headerView);
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(),System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				OnRefresh();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(),System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				Page page=JobListPaser.GetInstance().getPage();
				if (page==null){
					page=new Page();
					page.totalcount=1;
					page.pagenum=1;
					page.currpage=1;
				}
				int totalpage;
				if (page.pagenum<=0) {
					totalpage=0;
				}else {
					totalpage=page.totalcount/page.pagenum;
					if (page.totalcount%page.pagenum>0) 
						totalpage++;
				}
				if (page.currpage<totalpage) {
					int currpages=page.currpage+1;
					RequestManger.GetProjectJobList(ProjectDetailsActivity.this, mproductid, currpages, 10, new Listener<RequestCall>() {

						@Override
						public void onResponse(RequestCall response) {
							if (mPullToRefreshListView!=null && mPullToRefreshListView.isRefreshing()) 
								mPullToRefreshListView.onRefreshComplete();
							if (response.getParser().getResultSuccess()) {
								jAdapter.notifyDataSetChanged(JobListPaser.GetInstance().getContents());
							}
						}
					}, errorListener);
				}else {
					new GetDataTask(mPullToRefreshListView).execute();
				}
			}
		});
		new initData().execute();
	}



	private class initData extends AsyncTask<Integer, Integer, Integer>{

		@Override
		protected Integer doInBackground(Integer... params) {
			mproductid=getIntent().getStringExtra(MPRODUCTID);
			if (MPlusApplication.isLogin) 
				userid=UserPaser.GetInstance().userid;
			return null;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			RequestManger.getBaseProject(ProjectDetailsActivity.this, mproductid, userid, new Listener<RequestCall>() {
				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()) 
						initProjectData(ProjectDetailsPaser.GetInstance());
				}
			}, errorListener);
			OnRefresh();
		}
	}
	
	
	
	protected void OnRefresh() {
		RequestManger.GetProjectJobList(ProjectDetailsActivity.this, mproductid, 1, 10, new Listener<RequestCall>() {

			@Override
			public void onResponse(RequestCall response) {
				if (mPullToRefreshListView!=null && mPullToRefreshListView.isRefreshing()) 
					mPullToRefreshListView.onRefreshComplete();
				if (response.getParser().getResultSuccess()) {
					ArrayList<contents> jobs= JobListPaser.GetInstance().getContents();
					jAdapter=new ProjectJobListAdapter(ProjectDetailsActivity.this,jobs);
					mPullToRefreshListView.getRefreshableView().setAdapter(jAdapter);
//					ArrayList<String> roles=new ArrayList<String>();
//					for (contents contents : jobs) {
//						roles.add(contents.getRole());
//					}
//					if (roles!=null && roles.size()>=1) {
//						MultipleTextUtSils mu=new MultipleTextUtils(multipleText, roles);
//						mu.AddTag();
//					}
				}
			}
		}, errorListener);
	}
	
	
	private void initProjectData(final SimpleProject sProject){
		if (sProject==null) 
			return;
		ImageLoader.getInstance().displayImage(sProject.shortcut, iv_ico, ImageLoadUtils.getLongImageProject());
		tv_name.setText(TextUtils.isEmpty(sProject.mproductname)?"":sProject.mproductname);
		String filmtype=TextUtils.isEmpty(sProject.filmtype)?"":sProject.filmtype;
		String filmformat=TextUtils.isEmpty(sProject.filmformat)?"":sProject.filmformat;
		if (TextUtils.isEmpty(filmtype+filmformat)) {
			tv_type.setVisibility(View.GONE);
		}else {
			tv_type.setVisibility(View.VISIBLE);
			tv_type.setText(filmtype+filmformat);
		}
		String director=TextUtils.isEmpty(sProject.director)?"":sProject.director;
		String producer=TextUtils.isEmpty(sProject.producer)?"":sProject.producer;
		String directorstring="";
		if (director!=null && director.length()>1 ) 
			directorstring=directorstring+"导演："+director;
		if (producer!=null && producer.length()>1){
			if (directorstring!=null && directorstring.length()>1) {
				directorstring=directorstring+"    制片人："+producer;
			}else {
				directorstring=directorstring+"制片人："+producer;
			}
		}
		if (directorstring!=null && directorstring.length()>1) {
			tv_director.setText(directorstring);
		}else {
			tv_director.setVisibility(View.GONE);;
		}
		String shootingtime=TextUtils.isEmpty(sProject.shootingtime)?"":sProject.shootingtime;
		tv_shootingtime.setText("开机时间："+shootingtime);
		String location=TextUtils.isEmpty(sProject.location)?"":sProject.location;
		tv_location.setText("拍摄地点："+location);
		final String summary=TextUtils.isEmpty(sProject.summary)?"":sProject.summary;
		tv_stage.setText(TextUtils.isEmpty(sProject.stage)?"":sProject.stage);
		tv_summary.setText(Html.fromHtml("剧情简介："+summary));
		tv_summary.setMaxLines(2);
		addRightClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (platformTool==null) {
					platformTool=new AddPlatformTool(ProjectDetailsActivity.this);
				}
				platformTool.shareMsg("这个项目不错，分享给你！", sProject.mproductname, sProject.sharelink, 0, sProject.shortcut);		
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==LoginUtils.TOLOGIN && resultCode==RESULT_OK) {
			String jobId=jAdapter.getJobId();
			if (!TextUtils.isEmpty(jobId)) {
				showLoadingDialog();
				RequestManger.PostApplyJob(ProjectDetailsActivity.this, UserPaser.GetInstance().userid, jobId, new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						dismissLoadingDialog();
						if (response.getParser().getResultSuccess()) {
							ToastTool.showShortToast(ProjectDetailsActivity.this, "申请角色成功！");
						}else {
							if (response.getParser().getResponseMsg()!=null) 
								ToastTool.showShortToast(ProjectDetailsActivity.this,response.getParser().getResponseMsg());
						}
					}
					
				}, errorListener);
			}
		}
	}
}
