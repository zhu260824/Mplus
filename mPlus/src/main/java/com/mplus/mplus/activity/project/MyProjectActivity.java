package com.mplus.mplus.activity.project;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.pushproject.FirstPushWorksActivity;
import com.mplus.mplus.adapter.MyProjectAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.Page;
import com.mplus.mplus.paser.SimpleProject;
import com.mplus.mplus.paser.SimpleProjectPaser;
import com.mplus.mplus.paser.login.UserPaser;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * 
 * 我的项目
 * items_my_project
 */
public class MyProjectActivity extends BaseActivity {
	private PullToRefreshListView mPullRefreshListView;
	private ArrayList<SimpleProject> myProjectArrayList;
	private MyProjectAdapter projectAdapter;
	private TextView tv_my_project;
	private String userid;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_project);
		initTitle("我的项目");
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.mPullToRefreshListView_my_project);
		tv_my_project = (TextView) findViewById(R.id.tv_my_project);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// 这里写下拉刷新的任务
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				RequestManger.getMyProject(MyProjectActivity.this, userid, 1, 20, new Listener<RequestCall>() {
					public void onResponse(RequestCall response) {
						mPullRefreshListView.onRefreshComplete();
						if (response.getParser().getResultSuccess()) {
							myProjectArrayList = SimpleProjectPaser.GetInstance(2);
							projectAdapter = new MyProjectAdapter(MyProjectActivity.this, myProjectArrayList);
							mPullRefreshListView.getRefreshableView().setAdapter(projectAdapter);
						} else {
							if (response.getParser().getResponseMsg() != null)
								Toast.makeText(MyProjectActivity.this, response.getParser().getResponseMsg(),Toast.LENGTH_SHORT).show();
						}

					}
				}, errorListener);
			}

			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				Page page;
				page = SimpleProjectPaser.GetPage(2);
				if (page == null) {
					page = new Page();
					page.totalcount = 1;
					page.pagenum = 1;
					page.currpage = 1;
				}
				int totalpage;
				if (page.pagenum <= 0) {
					totalpage = 0;
				} else {
					totalpage = page.totalcount / page.pagenum;
					if (page.totalcount % page.pagenum > 0)
						totalpage++;
				}
				if (page.currpage < totalpage) {
					int currpages = page.currpage + 1;
					RequestManger.getMyProject(MyProjectActivity.this, userid, currpages, 20,
							new Listener<RequestCall>() {
						@Override
						public void onResponse(RequestCall response) {
							mPullRefreshListView.onRefreshComplete();
							if (response.getParser().getResultSuccess()) {
								myProjectArrayList = SimpleProjectPaser.GetInstance(2);
								projectAdapter.notifyData(myProjectArrayList);

							} else {
								if (response.getParser().getResponseMsg() != null)
									Toast.makeText(MyProjectActivity.this, response.getParser().getResponseMsg(),Toast.LENGTH_SHORT).show();
							}
						}
					}, errorListener);

				} else {
					new GetDataTask(mPullRefreshListView).execute();
				}
			}
		});
	
		tv_my_project.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(MyProjectActivity.this, FirstPushWorksActivity.class));
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		new inintDataTask().execute();
	}
	
	private class inintDataTask extends AsyncTask<Integer, Integer, RequestCall> {

		protected RequestCall doInBackground(Integer... params) {
			if (userid==null) 
				userid = UserPaser.GetInstance().userid;
			return null;
		}

		protected void onPostExecute(RequestCall result) {
			super.onPostExecute(result);
			showLoadingDialog();
			RequestManger.getMyProject(MyProjectActivity.this, userid, 1, 20, new Listener<RequestCall>() {
				public void onResponse(RequestCall response) {
					dismissLoadingDialog();
					if (response.getParser().getResultSuccess()) {
						myProjectArrayList = SimpleProjectPaser.GetInstance(2);
						projectAdapter = new MyProjectAdapter(MyProjectActivity.this, myProjectArrayList);
						mPullRefreshListView.getRefreshableView().setAdapter(projectAdapter);
					}
				}
			}, errorListener);
		}
	}
}
