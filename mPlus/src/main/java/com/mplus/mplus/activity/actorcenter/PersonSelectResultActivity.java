package com.mplus.mplus.activity.actorcenter;

import java.util.ArrayList;

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
import com.mplus.mplus.paser.actor.ActorListPaser;
import com.mplus.mplus.paser.common.Page;
import com.mplus.mplus.utils.MultipleTextUtils;
import com.mplus.mplus.utils.StringUtils;
import com.mplus.mplus.view.multipletext.MultipleTextViewGroup;
import com.mplus.mplus.view.multipletext.MultipleTextViewGroup.OnMultipleTVItemClickListener;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

public class PersonSelectResultActivity extends BaseActivity {
	private GridView sGridView;
	private PullToRefreshGridView mPullToRefreshGridView;
	private MultipleTextViewGroup multipleText;
	private String sex, age, height, weight, tag, key;
	private boolean search;
	private starsfaceResultActivityAdapter pAdapter;
	private MultipleTextUtils multipleUtils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_selectresult);
		initTitle("搜索结果");
		initView();

	}

	private void initView() {
		multipleText =  (MultipleTextViewGroup) findViewById(R.id.main_rl);
		sex = getIntent().getStringExtra("sex");
		age = getIntent().getStringExtra("age");
		height = getIntent().getStringExtra("height");
		weight = getIntent().getStringExtra("weight");
		tag = getIntent().getStringExtra("tag");
		key = getIntent().getStringExtra("key");
		search = getIntent().getBooleanExtra("search", false);
		if (!search) 
			showselectlayout();
		mPullToRefreshGridView = (PullToRefreshGridView) findViewById(R.id.sGridView);
		sGridView=mPullToRefreshGridView.getRefreshableView();
		mPullToRefreshGridView.setMode(Mode.BOTH);
		sGridView.setNumColumns(2);
		View emptyView=View.inflate(PersonSelectResultActivity.this, R.layout.view_empty_list, null);
		TextView list_empty_text=(TextView) emptyView.findViewById(R.id.list_empty_text);
		list_empty_text.setText("没有找到你想要的哦！换个条件试试！");
		mPullToRefreshGridView.setEmptyView(emptyView);
		mPullToRefreshGridView.setOnRefreshListener(new OnRefreshListener<GridView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<GridView> refreshView) {
						String label = DateUtils.formatDateTime(
								PersonSelectResultActivity.this,
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						if (refreshView.isHeaderShown()) {
							getPersonList();
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
								RequestManger.GetActorList(PersonSelectResultActivity.this, currpages, 10, key,
										StringUtils.FormatDict(8, height),
										StringUtils.FormatDict(14, weight),
										StringUtils.FormatDict(15, age), sex, tag,
										new Listener<RequestCall>() {

											@Override
											public void onResponse(RequestCall response) {
												if (response.getParser().getResultSuccess())
												pAdapter.notifyDataSetChanged(ActorListPaser.GetInstance(1));
											}
										}, errorListener);
							}
						}
						mPullToRefreshGridView.onRefreshComplete();
					}
				});
		getPersonList();
	}

	private void showselectlayout() {
		final ArrayList<String> tags=new ArrayList<String>();
		if (sex != null && sex.length()>=1) 
			tags.add(sex);
		if (age != null && age.length()>=1) 
			tags.add(age);
		if (height != null && height.length()>=1) 
			tags.add(height);
		if (weight != null && weight.length()>=1 ) 
			tags.add(weight);
		if (tag!=null && tag.length()>1) 
			tags.addAll(StringUtils.StringToList(tag));
		multipleUtils=new MultipleTextUtils(multipleText, tags);
		multipleUtils.AddTagWithClick(new OnMultipleTVItemClickListener() {
			
			@Override
			public void onMultipleTVItemClick(View view, String textContent) {
				if (tags.remove(textContent)) {
					multipleText.removeAllViews();
					multipleText.setTextViews(tags);
					tag=StringUtils.ListToString(tags);
					getPersonList();
				}
			}
		});
	}



	protected void getPersonList() {
		RequestManger.GetActorList(PersonSelectResultActivity.this, 1, 10, key,
				StringUtils.FormatDict(8, height),
				StringUtils.FormatDict(14, weight),
				StringUtils.FormatDict(15, age), sex, tag,
				new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						if (response.getParser().getResultSuccess())
							pAdapter = new starsfaceResultActivityAdapter(PersonSelectResultActivity.this,ActorListPaser.GetInstance(1));
						sGridView.setAdapter(pAdapter);
					}
				}, errorListener);
	}

	

}
