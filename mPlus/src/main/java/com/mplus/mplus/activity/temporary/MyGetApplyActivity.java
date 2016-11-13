package com.mplus.mplus.activity.temporary;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshSwipeListView;
import com.handmark.pulltorefresh.library.SwipeListView;
import com.mplus.mplus.R;
import com.mplus.mplus.adapter.usercenter.MyGetApplySwipeAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.MyGetApplyList;
import com.mplus.mplus.paser.MyGetApplyPaser;
import com.mplus.mplus.paser.common.Page;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.paser.project.ProjectNameListPaser;
import com.mplus.mplus.utils.ToastTool;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 收到申请
 * @author Administrator
 *
 */
public class MyGetApplyActivity extends BaseActivity {
	private PullToRefreshSwipeListView mPullRefreshListView;
	private SwipeListView mSwipeListView;
	private ArrayList<MyGetApplyList.contents> myGetApplyList;
	private MyGetApplySwipeAdapter myGetApplySwipeAdapter;
	private TextView tv_movie_my_get_apply, tv_role_my_get_apply;
	private String planId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mygetapply);
		tv_movie_my_get_apply=(TextView)findViewById(R.id.tv_movie_my_get_apply);
		tv_role_my_get_apply=(TextView)findViewById(R.id.tv_role_my_get_apply);
		initTitle("收到的简历");
		initView();
		Intent it = getIntent();
		tv_movie_my_get_apply.setText(it.getStringExtra("mproductname"));
		tv_role_my_get_apply.setText(	it.getStringExtra("planname"));
		planId=it.getStringExtra("planId");
	}

	private void initView() {
		mPullRefreshListView = (PullToRefreshSwipeListView) findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);

		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<SwipeListView>() {

			public void onPullDownToRefresh(PullToRefreshBase<SwipeListView> refreshView) {
				// 这里写下拉刷新的任务
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				RequestManger.GetMyGetApply(MyGetApplyActivity.this, planId, UserPaser.GetInstance().userid, 1, 10, new Listener<RequestCall>() {
					public void onResponse(RequestCall response) {
						dismissLoadingDialog();
						if (mPullRefreshListView!=null && mPullRefreshListView.isRefreshing()) 
							mPullRefreshListView.onRefreshComplete();
						if (response.getParser().getResultSuccess()) {
							myGetApplyList = MyGetApplyPaser.GetInstance(2).getContents();
							getMyApplyAdapter();
							mSwipeListView.setAdapter(myGetApplySwipeAdapter);
						}else{
							if (response.getParser().getResponseMsg() != null)
								Toast.makeText(MyGetApplyActivity.this, response.getParser().getResponseMsg(),
										Toast.LENGTH_SHORT).show();
						}
					}
				}, errorListener);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<SwipeListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				Page page;
				page = ProjectNameListPaser.GetInstance(2).getPage();
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

					RequestManger.GetMyGetApply(MyGetApplyActivity.this, planId, UserPaser.GetInstance().userid, currpages, 10, new Listener<RequestCall>() {
						public void onResponse(RequestCall response) {
							dismissLoadingDialog();
							if (mPullRefreshListView!=null && mPullRefreshListView.isRefreshing()) 
								mPullRefreshListView.onRefreshComplete();
							if (response.getParser().getResultSuccess()) {
								myGetApplyList = MyGetApplyPaser.GetInstance(2).getContents();
								getMyApplyAdapter();
								mSwipeListView.setAdapter(myGetApplySwipeAdapter);
							}else{
								if (response.getParser().getResponseMsg() != null)
									Toast.makeText(MyGetApplyActivity.this, response.getParser().getResponseMsg(),
											Toast.LENGTH_SHORT).show();
							}
						}
					}, errorListener);

				} else {
					new GetDataTask(mPullRefreshListView).execute();
				}
			}
		});

		mSwipeListView = mPullRefreshListView.getRefreshableView();
		// mListView = (SwipeListView)findViewById(R.id.listview);

//		mSwipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Toast.makeText(MyGetApplyActivity.this, "item onclick " + position, Toast.LENGTH_SHORT).show();
//				// startActivity(new Intent(MainActivity.this,
//				// Linestips.class));
//				// startActivity(new Intent(MyGetApplyActivity.this,
//				// FlowLayoutDemo.class));
//			}
//		});

//		mSwipeListView.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				// TODO Auto-generated method stub
//				mSwipeListView.showRight(v);
//				return false;
//			}
//		});
		new inintDataTask().execute();
	}

	private class inintDataTask extends AsyncTask<Integer, Integer, RequestCall> {

		protected RequestCall doInBackground(Integer... params) {
			return null;
		}

		protected void onPostExecute(RequestCall result) {
			super.onPostExecute(result);
			showLoadingDialog();
			// 1479

			RequestManger.GetMyGetApply(MyGetApplyActivity.this, planId,UserPaser.GetInstance().userid, 1, 10, new Listener<RequestCall>() {
				public void onResponse(RequestCall response) {
					dismissLoadingDialog();
					if (response.getParser().getResultSuccess()) {
						myGetApplyList = MyGetApplyPaser.GetInstance(2).getContents();
						getMyApplyAdapter();
						mSwipeListView.setAdapter(myGetApplySwipeAdapter);
					}
				}
			}, errorListener);
		}
	}
	public void getMyApplyAdapter() {
		myGetApplySwipeAdapter = new MyGetApplySwipeAdapter(MyGetApplyActivity.this,
				mSwipeListView.getRightViewWidth(), myGetApplyList,
				new MyGetApplySwipeAdapter.IOnItemRightClickListener() {

					public void onRightClick(View v, int position) {

						// TODO Auto-generated method stub addCOllect
						mSwipeListView.resetItems();

						//添加收藏
						// 1479
						RequestManger.addCOllect(MyGetApplyActivity.this, UserPaser.GetInstance().userid,myGetApplyList.get(position).getUserId(), new Listener<RequestCall>() {
							public void onResponse(RequestCall response) {
								dismissLoadingDialog();
								if (response.getParser().getResultSuccess()) {
									Toast.makeText(MyGetApplyActivity.this,"添加成功", Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(MyGetApplyActivity.this, response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
								}
							}
						}, errorListener);

					}
				}, new MyGetApplySwipeAdapter.IOnItemRightClickListener2() {
					@Override
					public void onRightClick(View v, int position) {

						// 同意
						mSwipeListView.resetItems();

						RequestManger.agreeApplyFor(MyGetApplyActivity.this,UserPaser.GetInstance().userid,myGetApplyList.get(position).getApplyid(), new Listener<RequestCall>() {
							public void onResponse(RequestCall response) {
								dismissLoadingDialog();
								if (response.getParser().getResultSuccess()) {
									new inintDataTask().execute();
									Toast.makeText(MyGetApplyActivity.this,"同意成功", Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(MyGetApplyActivity.this, response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
								}
							}
						}, errorListener);
					}
				}, new MyGetApplySwipeAdapter.IOnItemRightClickListener3() {
					@Override
					public void onRightClick(View v, int position) {

						// 忽略 ignoreApplyFor
						mSwipeListView.resetItems();

						//UserPaser.GetInstance().userid
						RequestManger.ignoreApplyFor(MyGetApplyActivity.this,UserPaser.GetInstance().userid ,myGetApplyList.get(position).getApplyid(), new Listener<RequestCall>() {
							public void onResponse(RequestCall response) {
								dismissLoadingDialog();
								if (response.getParser().getResultSuccess()) {
									new inintDataTask().execute();
								}else{
									Toast.makeText(MyGetApplyActivity.this, response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
								}
							}
						}, errorListener);
					}
				});
	}
	protected class GetDataTask extends AsyncTask<Void, Void, String[]> {
		private PullToRefreshSwipeListView mPullRefreshListView;
		
		public GetDataTask(PullToRefreshSwipeListView mPullRefreshListView) {
			super();
			this.mPullRefreshListView = mPullRefreshListView;
		}

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			ToastTool.showShortToast(MyGetApplyActivity.this, getString(R.string.no_more_content));
			if (mPullRefreshListView!=null && mPullRefreshListView.isRefreshing()) 
				mPullRefreshListView.onRefreshComplete();
		}
	}	
}
