package com.mplus.mplus.activity.project;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mplus.mplus.R;
import com.mplus.mplus.adapter.project.SearchResultAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorListPaser;
import com.mplus.mplus.paser.common.Page;
import com.mplus.mplus.utils.MultipleTextUtils;
import com.mplus.mplus.utils.StringUtils;
import com.mplus.mplus.view.multipletext.MultipleTextViewGroup;
import com.mplus.mplus.view.multipletext.MultipleTextViewGroup.OnMultipleTVItemClickListener;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SearchResultActivity extends BaseActivity{
	private PullToRefreshListView mPullToRefreshListView;
	private SearchResultAdapter cAdapter;
	private MultipleTextViewGroup multipleText;
	private String sex, age, height, weight, tag, key;
	private boolean search;
	private String planid;
	private MultipleTextUtils multipleUtils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newjob_search_result);
		initTitle("搜索结果");
		initView();
	}
	private void initView() {
		multipleText = (MultipleTextViewGroup) findViewById(R.id.main_rl);
		mPullToRefreshListView = (PullToRefreshListView)findViewById(R.id.mPullToRefreshListView);
		LinearLayout lin_search = (LinearLayout) findViewById(R.id.lin_search);
		lin_search.setVisibility(View.GONE);
		multipleText.setVisibility(View.VISIBLE);
		TextView tv_plan_name_alternative = (TextView) findViewById(R.id.tv_plan_name_alternative);
		TextView tv_project_name_alternative = (TextView) findViewById(R.id.tv_project_name_alternative);
		String mproductname=getIntent().getStringExtra("mproductname");
		String planname=getIntent().getStringExtra("planname");
		tv_project_name_alternative.setText(TextUtils.isEmpty(mproductname)?"":mproductname);
		tv_plan_name_alternative.setText(TextUtils.isEmpty(planname)?"":planname);
		sex = getIntent().getStringExtra("sex");
		age = getIntent().getStringExtra("age");
		height = getIntent().getStringExtra("height");
		weight = getIntent().getStringExtra("weight");
		tag = getIntent().getStringExtra("tag");
		key = getIntent().getStringExtra("key");
		search = getIntent().getBooleanExtra("search", false);
		planid=getIntent().getStringExtra("planid");
		if (!search) 
			showselectlayout();
		getPersonList(true);
		mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(getApplicationContext(),System.currentTimeMillis(),DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
						getPersonList(true);
					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(getApplicationContext(),System.currentTimeMillis(),DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
						getPersonList(false);
					}
				});
	}

	private void showselectlayout() {
		final ArrayList<String> tags=new ArrayList<String>();
		if (sex != null && sex.length()>=1) 
			tags.add(sex);
		/*addview(sex, new OnClickListener() {
			@Override
			public void onClick(View v) {
				sex = "";
				select_term_ly.removeView(v);
				getPersonList(true);

			}
		});*/
		if (age != null && age.length()>=1) 
			tags.add(age);
		/*} else {
			addview(age, new OnClickListener() {
				@Override
				public void onClick(View v) {
					age = "";
					select_term_ly.removeView(v);
					getPersonList(true);
				}
			});
		}*/
		if (height != null && height.length()>=1) 
			tags.add(height);
	/*	} else {
			addview(height, new OnClickListener() {
				@Override
				public void onClick(View v) {
					height = "";
					select_term_ly.removeView(v);
					getPersonList(true);
				}
			});
		}*/

		if (weight != null && weight.length()>=1 ) 
			tags.add(weight);
		/*} else {
			addview(weight, new OnClickListener() {
				@Override
				public void onClick(View v) {
					weight = "";
					select_term_ly.removeView(v);
					getPersonList(true);
				}
			});
		}*/
		if (tag!=null || tag.length()>1) 
			tags.addAll(StringUtils.StringToList(tag));
		multipleUtils=new MultipleTextUtils(multipleText, tags);
		multipleUtils.AddTagWithClick(new OnMultipleTVItemClickListener() {
			
			@Override
			public void onMultipleTVItemClick(View view, String textContent) {
				if (tags.remove(textContent)) {
					multipleText.removeView(view);
					getPersonList(true);
				}
			}
		});
		
		
		
//		if (tag.trim().length() > 0) {
//			tagarr = (tag.split(","));
//			if (tagarr.length > 0) {
//				for (int i = 0; i < tagarr.length; i++) {
//					final int index = i;
//					addview(tagarr[i], new OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							tagarr[index] = "";
//							select_term_ly.removeView(v);
//							tag = getTagstr();
//							getPersonList(true);
//						}
//					});
//				}
//			}
//		}

	}

	
	protected void getPersonList(boolean isRefresh) {
		if (isRefresh) {
			RequestManger.GetActorList(SearchResultActivity.this, 1, 10, key,
					StringUtils.FormatDict(8, height),
					StringUtils.FormatDict(14, weight),
					StringUtils.FormatDict(15, age), sex, tag,
					new Listener<RequestCall>() {

						@Override
						public void onResponse(RequestCall response) {
							if (mPullToRefreshListView!=null && mPullToRefreshListView.isRefreshing()) 
								mPullToRefreshListView.onRefreshComplete();
							if (response.getParser().getResultSuccess())
								cAdapter = new SearchResultAdapter(SearchResultActivity.this, ActorListPaser.GetInstance(1),planid);
								mPullToRefreshListView.getRefreshableView().setAdapter(cAdapter);
						}
					}, errorListener);
		}else {
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
				RequestManger.GetActorList(SearchResultActivity.this, currpages, 10, key,
						StringUtils.FormatDict(8, height),
						StringUtils.FormatDict(14, weight),
						StringUtils.FormatDict(15, age), sex, tag,
						new Listener<RequestCall>() {

							@Override
							public void onResponse(RequestCall response) {
								if (mPullToRefreshListView!=null && mPullToRefreshListView.isRefreshing()) 
									mPullToRefreshListView.onRefreshComplete();
								if (response.getParser().getResultSuccess())
									cAdapter.notifyDataSetChanged( ActorListPaser.GetInstance(1)); 
							}
						}, errorListener);
				}else{
					new GetDataTask(mPullToRefreshListView).execute();
				}
		}
	}




}
