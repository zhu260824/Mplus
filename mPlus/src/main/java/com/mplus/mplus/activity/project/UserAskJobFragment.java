package com.mplus.mplus.activity.project;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mplus.mplus.R;
import com.mplus.mplus.adapter.project.UserAddProjectAdapter;
import com.mplus.mplus.base.BaseFragment;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.common.Page;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.paser.project.UserAddJobListPaser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class UserAskJobFragment extends BaseFragment {
	private PullToRefreshListView mPullToRefreshListView;
	private String userid=null;
	private UserAddProjectAdapter pAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=View.inflate(mActivity, R.layout.fragment_list, null);
		mPullToRefreshListView = (PullToRefreshListView)view.findViewById(R.id.mPullToRefreshListView);
		mPullToRefreshListView.setMode(Mode.BOTH);
		View emptyView=View.inflate(mActivity, R.layout.view_empty_list, null);
		TextView list_empty_text=(TextView) emptyView.findViewById(R.id.list_empty_text);
		list_empty_text.setText("您暂时还未申请项目	");
		mPullToRefreshListView.setEmptyView(emptyView);
		new initData().execute();
		return view;
	}
	
	private class initData extends AsyncTask<Integer, Integer, Integer>{

		@Override
		protected Integer doInBackground(Integer... params) {
			userid=UserPaser.GetInstance().userid;
			return null;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			OnRefresh();
			mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

				@Override
				public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
					String label = DateUtils.formatDateTime(mActivity,System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
					OnRefresh();
				}

				@Override
				public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
					String label = DateUtils.formatDateTime(mActivity,System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
					Page page=UserAddJobListPaser.GetInstance(2).getPage();
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
						RequestManger.GetUserAskJob(mActivity, userid, currpages, 10, new Listener<RequestCall>() {

							@Override
							public void onResponse(RequestCall response) {
								if (mPullToRefreshListView!=null && mPullToRefreshListView.isRefreshing()) 
									mPullToRefreshListView.onRefreshComplete();
								if (response.getParser().getResultSuccess()){
									if (pAdapter!=null) 
										pAdapter.notifyDataSetChanged(UserAddJobListPaser.GetInstance(2).getContents());
								}
							}}, errorListener);
					}else {
						new GetDataTask(mPullToRefreshListView).execute();
					}
				}
			});
		}
	}
	
	private void OnRefresh() {
		RequestManger.GetUserAskJob(mActivity, userid, 1, 10, new Listener<RequestCall>() {

			@Override
			public void onResponse(RequestCall response) {
				if (mPullToRefreshListView!=null && mPullToRefreshListView.isRefreshing()) 
					mPullToRefreshListView.onRefreshComplete();
				if (response.getParser().getResultSuccess()){
					pAdapter=new UserAddProjectAdapter(mActivity,UserAddJobListPaser.GetInstance(2).getContents());
					mPullToRefreshListView.setAdapter(pAdapter);
				}
			}}, errorListener);
	}
	
	@Override
	public boolean onBackPressed() {
		return false;
	}

}
