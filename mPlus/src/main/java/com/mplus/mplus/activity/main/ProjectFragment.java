package com.mplus.mplus.activity.main;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.Utils.MobclickAgentUtils;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.login.LoginActivity;
import com.mplus.mplus.activity.message.MessageCategoryActivity;
import com.mplus.mplus.adapter.ProjtctAdapter;
import com.mplus.mplus.base.BaseFragment;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.dialog.MainAddPopupWindow;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.common.Page;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.paser.message.UnReadMessage;
import com.mplus.mplus.paser.message.UnReadMessagePaser;
import com.mplus.mplus.paser.project.ProjectList;
import com.mplus.mplus.paser.project.ProjectListPaser;
import com.mplus.mplus.utils.LoginUtils;
import com.mplus.mplus.utils.ToastUtils;
import com.mplus.mplus.view.BadgeView;
import com.mplus.mplus.view.ad.AdGalleryHelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
public class ProjectFragment extends BaseFragment {
	private PullToRefreshListView mPullToRefreshListView;
	private ImageView iv_messages;
	private ImageView iv_add;
	private MainAddPopupWindow mAddPopupWindow;
	private RelativeLayout rl_ad;
	private ProjtctAdapter pAdapter;
	private LinearLayout person_select_ly;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(mActivity, R.layout.fragment_main_project,null);
		initview(view);
		initTitle(view);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (MPlusApplication.isLogin) 
			new initMessage().execute();
	}
	
	
	private void initview(View view) {
		person_select_ly = (LinearLayout) view.findViewById(R.id.person_select_ly);
		mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.mPullToRefreshListView);
		mPullToRefreshListView.setMode(Mode.BOTH);
		rl_ad = new RelativeLayout(mActivity);
		AbsListView.LayoutParams ad_lp=new AbsListView.LayoutParams(MPlusApplication.Width, MPlusApplication.Width/10*4);
		rl_ad.setLayoutParams(ad_lp);
		mPullToRefreshListView.getRefreshableView().addHeaderView(rl_ad);
		new initData().execute();
		person_select_ly.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mActivity, PersonSelectActivity.class));
			}
		});
	}

	private class initData extends AsyncTask<Integer, Integer, ProjectList>{

		@Override
		protected ProjectList doInBackground(Integer... params) {
			return ProjectListPaser.GetInstance();
		}
		
		@Override
		protected void onPostExecute(ProjectList result) {
			super.onPostExecute(result);
			initViewData(result);
			RequestManger.GetProjectList(mActivity, 1, 10, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					mPullToRefreshListView.onRefreshComplete();
					if (response.getParser().getResultSuccess()) {
						initViewData(ProjectListPaser.GetInstance());
					}
				}
			}, errorListener);
			
		}
		
	}
	
	private void initViewData(final ProjectList pList){
		if (pList==null) 
			return;
		pAdapter=new ProjtctAdapter(mActivity, pList.getContents());
		mPullToRefreshListView.getRefreshableView().setAdapter(pAdapter);
		if (pList.getTop()!=null && pList.getTop().size()>=1) {
			rl_ad.setVisibility(View.VISIBLE);
			AdGalleryHelper mGalleryHelper = new AdGalleryHelper(getActivity(),pList.getTop(), 4000);
			rl_ad.addView(mGalleryHelper.getLayout());
			// 开始自动切换
			mGalleryHelper.startAutoSwitch();
		}else {
			rl_ad.setVisibility(View.GONE);
		}
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(mActivity,System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				RequestManger.GetProjectList(mActivity, 1, 10, new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						if (mPullToRefreshListView!=null && mPullToRefreshListView.isRefreshing()) 
							mPullToRefreshListView.onRefreshComplete();
						if (response.getParser().getResultSuccess()) {
							initViewData(ProjectListPaser.GetInstance());
						}
					}
				}, errorListener);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(mActivity,System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				Page page=ProjectListPaser.GetInstance().getPage();
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
					RequestManger.GetProjectList(mActivity, currpages, 10, new Listener<RequestCall>() {

						@Override
						public void onResponse(RequestCall response) {
							if (mPullToRefreshListView!=null && mPullToRefreshListView.isRefreshing()) 
								mPullToRefreshListView.onRefreshComplete();
							if (response.getParser().getResultSuccess()) {
								pAdapter.notifyDataSetChanged(ProjectListPaser.GetInstance().getContents());
							}
						}
					}, errorListener);
				}else {
					new GetDataTask(mPullToRefreshListView).execute();
				}
				
			}

		});
	}
	
	@Override
	public boolean onBackPressed() {
		return false;
	}

	
	private void initTitle(View view){
		iv_add = (ImageView) view.findViewById(R.id.iv_add);
		iv_messages=(ImageView) view.findViewById(R.id.iv_messages);
		iv_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mAddPopupWindow==null) 
					mAddPopupWindow=new MainAddPopupWindow(mActivity);
				mAddPopupWindow.openWindows();
				mAddPopupWindow.showAsDropDown(v, -40, 40);
			}
		});
		iv_messages.setOnClickListener(new OnClickListener() {
				
				@Override
			public void onClick(View v) {
					LoginUtils loginUtils=new LoginUtils() {
						
						@Override
						public void DoOnLongin() {
							startActivity(new Intent(mActivity,MessageCategoryActivity.class));
							MobclickAgentUtils.MobclickEventName(mActivity,"0002","消息中心","消息中心");
						}
					};
					loginUtils.ToLogin(mActivity, LoginUtils.TOLOGIN4);
			}
		});
	}
	
	private void initMessages(int msgNum){
		if (iv_messages==null) 
			return;
		BadgeView badgeView=new BadgeView(mActivity);
		badgeView.setTargetView(iv_messages);
		badgeView.setBadgeCount(msgNum);
		badgeView.setTextSize(6);
//		badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
//		badgeView.setBackgroundColor(Color.RED);
	}

	private class initMessage extends AsyncTask<Integer, Integer, String>{

		@Override
		protected String doInBackground(Integer... params) {
			return  UserPaser.GetInstance().userid;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			RequestManger.getUnReadMessage(mActivity,result, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()) {
						getSumMessage(UnReadMessagePaser.GetInstance());
					}else {
						 if (response.getParser().getResponseMsg()!=null) 
							 ToastUtils.showToastOnce(mActivity, response.getParser().getResponseMsg());
//							ToastTool.showShortToast(mActivity, response.getParser().getResponseMsg());
					}
				}
			}, errorListener);
		}
	}
	
	private void getSumMessage(ArrayList<UnReadMessage> list){
		int sun=0;
		for (UnReadMessage unReadMessage : list) {
			if (unReadMessage.getMsgSpec().equals("10009004")) {
				sun=sun+Integer.valueOf(unReadMessage.getUnReadMsgNum());
			} else if (unReadMessage.getMsgSpec().equals("10009006")) {
				sun=sun+Integer.valueOf(unReadMessage.getUnReadMsgNum());
			}else if (unReadMessage.getMsgSpec().equals("10009007")) {
				sun=sun+Integer.valueOf(unReadMessage.getUnReadMsgNum());
			}else if (unReadMessage.getMsgSpec().equals("10009005")) {
				sun=sun+Integer.valueOf(unReadMessage.getUnReadMsgNum());
			}else if (unReadMessage.getMsgSpec().equals("10009002")) {
				sun=sun+Integer.valueOf(unReadMessage.getUnReadMsgNum());
			}
		}
		if (sun!=0) 
			initMessages(sun);
	}

	
}
