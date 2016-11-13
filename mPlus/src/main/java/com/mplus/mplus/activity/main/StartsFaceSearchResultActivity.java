package com.mplus.mplus.activity.main;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.GridView;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.mplus.mplus.R;
import com.mplus.mplus.adapter.main.starsfaceResultActivityAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorList;
import com.mplus.mplus.paser.actor.ActorListPaser;
import com.mplus.mplus.paser.common.Page;

public class StartsFaceSearchResultActivity extends BaseActivity {
	private String starFaceId;
	 private GridView sGridView;
	private PullToRefreshGridView mPullToRefreshStaggerdGridView;
	private ArrayList<ActorList> list = new ArrayList<ActorList>();
	private starsfaceResultActivityAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_starsface_searchresult);
		initTitle("明星脸");
		starFaceId = getIntent().getStringExtra("starFaceId");
		initView();

	}

	private void initView() {
		mPullToRefreshStaggerdGridView = (PullToRefreshGridView) findViewById(R.id.sGridView);
		sGridView=mPullToRefreshStaggerdGridView.getRefreshableView();
		mPullToRefreshStaggerdGridView.setMode(Mode.BOTH);
		sGridView.setNumColumns(2);; 
		mPullToRefreshStaggerdGridView.setOnRefreshListener(new OnRefreshListener<GridView>() {

					@Override
					public void onRefresh(PullToRefreshBase<GridView> refreshView) {
						String label = DateUtils.formatDateTime(StartsFaceSearchResultActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy() .setLastUpdatedLabel(label);
						if (refreshView.isHeaderShown()) {
							onRefreshPerson();
						} else {
							Page page = ActorListPaser.GetPage(4);
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
								RequestManger.GetActorList(StartsFaceSearchResultActivity.this,currpages, 10, null, null, null, null,null, null,new Listener<RequestCall>() {

											@Override
											public void onResponse( RequestCall response) {
												// pAdapter.notifyDataSetChanged(ActorListPaser.GetInstance(1));
												if (response.getParser() .getResultSuccess()) {
													adapter.notifyDataSetChanged(ActorListPaser .GetInstance(4));
												}

											}
										}, errorListener);
							}
						}
						mPullToRefreshStaggerdGridView.onRefreshComplete();
					}
				});
		onRefreshPerson();

	}

	protected void onRefreshPerson() {
		RequestManger.GetActorStartFaceList(
				StartsFaceSearchResultActivity.this, 1, 10, starFaceId,
				new Listener<RequestCall>() {
					@Override
					public void onResponse(RequestCall response) {
						if (response.getParser().getResultSuccess())
							initViewData(ActorListPaser.GetInstance(4));
					}
				}, errorListener);
	}

	private void initViewData(ArrayList<ActorList> list) {
		if (list.size() > 0) {
			adapter = new starsfaceResultActivityAdapter(
					getApplicationContext(), list);
			sGridView.setAdapter(adapter);
		}

	}
}
