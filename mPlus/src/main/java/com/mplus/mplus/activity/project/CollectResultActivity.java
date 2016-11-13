package com.mplus.mplus.activity.project;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.main.ConditionSelectionActivity;
import com.mplus.mplus.adapter.project.SearchResultAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorList;
import com.mplus.mplus.paser.actor.ActorListPaser;
import com.mplus.mplus.paser.common.Page;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.StringUtils;
import com.mplus.mplus.utils.ToastTool;
import com.mplus.mplus.view.multipletext.MultipleTextViewGroup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CollectResultActivity extends BaseActivity{
	private PullToRefreshListView mPullToRefreshListView;
	private SearchResultAdapter cAdapter;
	private String key, sex, age, height, weight, tag;
	private EditText cf_key_et;
	private TextView fc_search_tv;
	private String planid;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newjob_search_result);
		initTitle("收藏");
		initShowRight(R.drawable.select_icon,getString(R.string.collect_action), true, true, 0);
		planid=getIntent().getStringExtra("planid");
		initView();
	}
	
	private void initView() {
		addRightClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(CollectResultActivity.this,ConditionSelectionActivity.class);
				startActivityForResult(i, 5);
			}
		});
		mPullToRefreshListView = (PullToRefreshListView)findViewById(R.id.mPullToRefreshListView);
		cf_key_et = (EditText) findViewById(R.id.cf_key_et);
		fc_search_tv = (TextView) findViewById(R.id.fc_search_tv);
		LinearLayout lin_search = (LinearLayout) findViewById(R.id.lin_search);
		MultipleTextViewGroup multipleText = (MultipleTextViewGroup) findViewById(R.id.main_rl);
		lin_search.setVisibility(View.VISIBLE);
		multipleText.setVisibility(View.GONE);
		TextView tv_plan_name_alternative = (TextView) findViewById(R.id.tv_plan_name_alternative);
		TextView tv_project_name_alternative = (TextView) findViewById(R.id.tv_project_name_alternative);
		String mproductname=getIntent().getStringExtra("mproductname");
		String planname=getIntent().getStringExtra("planname");
		tv_project_name_alternative.setText(TextUtils.isEmpty(mproductname)?"":mproductname);
		tv_plan_name_alternative.setText(TextUtils.isEmpty(planname)?"":planname);
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(getApplicationContext(),System.currentTimeMillis(),DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
						requestgetdata(null, null, null, null, null, null,false);
					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(getApplicationContext(),System.currentTimeMillis(),DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
						requestgetdata(key, sex, age, height, weight, tag, true);
						
					}
				});

		fc_search_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				key = cf_key_et.getText().toString();
				if (key.length() > 0) {
					requestgetdata(key, null, null, null, null, null, false);
				} else {
					ToastTool.showShortToast(CollectResultActivity.this, "请填写影人姓名！");
				}

			}
		});
		requestgetdata(null, null, null, null, null, null, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		requestgetdata(key, sex, age, height, weight, tag, false);
	}

	private void initViewData(ArrayList<ActorList> list) {
		cAdapter = new SearchResultAdapter(CollectResultActivity.this, list,planid);
		mPullToRefreshListView.getRefreshableView().setAdapter(cAdapter);
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 5:
				sex = data.getExtras().getString("sex");// ,,weight,tag
				age = data.getExtras().getString("age");//
				height = data.getExtras().getString("height");//
				weight = data.getExtras().getString("weight");//
				tag = data.getExtras().getString("tag");//
				age = StringUtils.FormatDict(15, age);
				height = StringUtils.FormatDict(8, height);
				weight = StringUtils.FormatDict(14, weight);
				break;
			default:
				break;
			}
		}
	}

	private void requestgetdata(String k, String s, String a, String h,String w, String t, boolean addmore) {
		String userid = UserPaser.GetInstance().userid;
		if (addmore) {
			Page page = ActorListPaser.GetPage(3);
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
				RequestManger.GetMyFavorite(CollectResultActivity.this, userid, currpages,10, k, s, a, h, w, t, new Listener<RequestCall>() {

								@Override
								public void onResponse(RequestCall response) {
									if (mPullToRefreshListView!=null && mPullToRefreshListView.isRefreshing()) 
										mPullToRefreshListView.onRefreshComplete();
									if (response.getParser().getResultSuccess()) {
										cAdapter.notifyDataSetChanged(ActorListPaser.GetInstance(3));
									}
								}
							}, errorListener);
				}else{
					new GetDataTask(mPullToRefreshListView).execute();
				}
			} else {
				RequestManger.GetMyFavorite(CollectResultActivity.this, userid, 1, 10, k, s, a,
						h, w, t, new Listener<RequestCall>() {
							@Override
							public void onResponse(RequestCall response) {
								if (mPullToRefreshListView!=null && mPullToRefreshListView.isRefreshing()) 
									mPullToRefreshListView.onRefreshComplete();
								if (response.getParser().getResultSuccess())
									initViewData(ActorListPaser.GetInstance(3));
							}
						}, errorListener);
			}
	}
	
}
