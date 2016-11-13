package com.mplus.mplus.activity.main;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.Utils.MobclickAgentUtils;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridLayout;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.message.MessageCategoryActivity;
import com.mplus.mplus.adapter.main.PersonFragmentRecyclerAdapter;
import com.mplus.mplus.base.BaseFragment;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.dialog.MainAddPopupWindow;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorList;
import com.mplus.mplus.paser.actor.ActorListPaser;
import com.mplus.mplus.paser.common.Page;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.paser.message.UnReadMessage;
import com.mplus.mplus.paser.message.UnReadMessagePaser;
import com.mplus.mplus.utils.LoginUtils;
import com.mplus.mplus.utils.ToastUtils;
import com.mplus.mplus.view.BadgeView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PersonFragment extends BaseFragment {
	private RecyclerView sGridView;
	private ImageView iv_messages;
	private ImageView iv_add;
	private MainAddPopupWindow mAddPopupWindow;
	private LinearLayout person_select_ly;
	private PersonFragmentRecyclerAdapter pAdapter;
	private PullToRefreshStaggeredGridLayout mPullToRefreshStaggerdGridView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(mActivity, R.layout.fragment_main_person, null);
		initView(view);
		initTitle(view);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (MPlusApplication.isLogin)
			new initMessage().execute();
	}

	private void initView(View view) {
		person_select_ly = (LinearLayout) view.findViewById(R.id.person_select_ly);
		mPullToRefreshStaggerdGridView = (PullToRefreshStaggeredGridLayout) view.findViewById(R.id.mStaggeredGridView);
		mPullToRefreshStaggerdGridView.setMode(Mode.BOTH);
		sGridView = mPullToRefreshStaggerdGridView.getRefreshableView();
		mPullToRefreshStaggerdGridView.setOnRefreshListener(new OnRefreshListener2<RecyclerView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
				String label = DateUtils.formatDateTime(mActivity,System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				onRefreshPerson();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
				String label = DateUtils.formatDateTime(mActivity,System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
					Page page = ActorListPaser.GetPage(1);
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
						RequestManger.GetActorList(mActivity,currpages, 10, null, null, null, null,
								null, null,new Listener<RequestCall>() {

									@Override
									public void onResponse(RequestCall response) {
										pAdapter.notifyDataSetChanged(ActorListPaser.GetInstance(1));
									}
								}, errorListener);
					}
				
				mPullToRefreshStaggerdGridView.onRefreshComplete();
			}
		});
		

		person_select_ly.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(mActivity, PersonSelectActivity.class));
				MobclickAgentUtils.MobclickEventName(mActivity,"0001","使用搜索","使用搜索");
			}
		});
		new initData().execute();
	}

	private class initData extends AsyncTask<Integer, Integer, ArrayList<ActorList>> {

		@Override
		protected ArrayList<ActorList> doInBackground(Integer... params) {
			return ActorListPaser.GetInstance(1);
		}

		@Override
		protected void onPostExecute(ArrayList<ActorList> result) {
			super.onPostExecute(result);
			onRefreshPerson();
		}
	}

	private void onRefreshPerson() {
		RequestManger.GetActorList(mActivity, 1, 10, null, null, null, null,
				null, null, new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						
						if (pAdapter==null) {
							pAdapter = new PersonFragmentRecyclerAdapter(ActorListPaser.GetInstance(1), mActivity);
							sGridView.setAdapter(pAdapter);
						}else{
							pAdapter.refreshdata(ActorListPaser.GetInstance(1));
						}
					}
				}, errorListener);
		mPullToRefreshStaggerdGridView.onRefreshComplete();
	}

	private void initTitle(View view) {
		iv_add = (ImageView) view.findViewById(R.id.iv_add);
		iv_messages = (ImageView) view.findViewById(R.id.iv_messages);
		iv_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mAddPopupWindow == null)
					mAddPopupWindow = new MainAddPopupWindow(mActivity);
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

	private void initMessages(int msgNum) {
		if (iv_messages == null)
			return;
		BadgeView badgeView = new BadgeView(mActivity);
		badgeView.setTargetView(iv_messages);
		badgeView.setBadgeCount(msgNum);
		badgeView.setTextSize(6);
		// badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
		// badgeView.setBackgroundColor(Color.RED);
	}

	@Override
	public boolean onBackPressed() {
		return false;
	}

	
	
	
	
	private class initMessage extends AsyncTask<Integer, Integer, String> {

		@Override
		protected String doInBackground(Integer... params) {
			return UserPaser.GetInstance().userid;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			RequestManger.getUnReadMessage(mActivity, result,
					new Listener<RequestCall>() {
						public void onResponse(RequestCall response) {
							if (response.getParser().getResultSuccess()) {
								getSumMessage(UnReadMessagePaser.GetInstance());
							} else {
								if (response.getParser().getResponseMsg() != null)
									ToastUtils.showToastOnce(mActivity,response.getParser().getResponseMsg());
							}
						}
					}, errorListener);
		}
	}

	private void getSumMessage(ArrayList<UnReadMessage> list) {
		int sun = 0;
		for (UnReadMessage unReadMessage : list) {
			if (unReadMessage.getMsgSpec().equals("10009004")) {
				sun = sun + Integer.valueOf(unReadMessage.getUnReadMsgNum());
			} else if (unReadMessage.getMsgSpec().equals("10009006")) {
				sun = sun + Integer.valueOf(unReadMessage.getUnReadMsgNum());
			} else if (unReadMessage.getMsgSpec().equals("10009007")) {
				sun = sun + Integer.valueOf(unReadMessage.getUnReadMsgNum());
			} else if (unReadMessage.getMsgSpec().equals("10009005")) {
				sun = sun + Integer.valueOf(unReadMessage.getUnReadMsgNum());
			} else if (unReadMessage.getMsgSpec().equals("10009002")) {
				sun = sun + Integer.valueOf(unReadMessage.getUnReadMsgNum());
			}
		}
		if (sun != 0)
			initMessages(sun);
	}

}
