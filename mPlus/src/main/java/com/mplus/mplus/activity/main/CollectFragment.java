package com.mplus.mplus.activity.main;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mplus.mplus.R;
import com.mplus.mplus.adapter.main.CollectFragmentAdapter;
import com.mplus.mplus.base.BaseFragment;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorList;
import com.mplus.mplus.paser.actor.ActorListPaser;
import com.mplus.mplus.paser.common.Page;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.ToastTool;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CollectFragment extends BaseFragment {
	private PullToRefreshListView mPullToRefreshListView;
	private CollectFragmentAdapter cAdapter;
	private String userid;
	private EditText cf_key_et;
	private TextView fc_search_tv;
	private ImageView iv_have_messages;
	private String key, sex, age, height, weight, tag;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(mActivity, R.layout.fragment_main_collect,null);
		((TextView)(view.findViewById(R.id.tv_title))).setText(getString(R.string.main_action_collect));
//		initTitle(view, getString(R.string.main_action_collect));
		initShowRight(view, R.drawable.select_icon,getString(R.string.collect_action), true, true, 0);
		initView(view);
		return view;
	}

	private void initView(View view) {
		addRightClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				if (!MPlusApplication.isLogin) {
//					ToastTool.showShortToast(mActivity, "你还么有登录，请先去登录！");
//					return;
//				}
				intentToSearch();
			}
		});
		cf_key_et = (EditText) view.findViewById(R.id.cf_key_et);
		fc_search_tv = (TextView) view.findViewById(R.id.fc_search_tv);
		iv_have_messages=(ImageView) view.findViewById(R.id.iv_have_messages);
		iv_have_messages.setVisibility(View.GONE);
		mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.mPullToRefreshListView);
		mPullToRefreshListView.setMode(Mode.BOTH);
		View emptyView=View.inflate(mActivity, R.layout.view_empty_list, null);
		TextView list_empty_text=(TextView) emptyView.findViewById(R.id.list_empty_text);
		list_empty_text.setText("你还没有收藏演员哦！");
		mPullToRefreshListView.setEmptyView(emptyView);
		mPullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(mActivity,System.currentTimeMillis(),DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
						requestgetdata(key, sex, age, height, weight, tag,false);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(mActivity, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
						requestgetdata(key, sex, age, height, weight, tag, true);
						
					}
				});

		fc_search_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!MPlusApplication.isLogin) {
					ToastTool.showShortToast(mActivity, "你还么有登录，请先去登录！");
					return;
				}
				key = cf_key_et.getText().toString();
				if (key.length() > 0) {
					requestgetdata(key, null, null, null, null, null, false);
				} else {
					requestgetdata(null, null, null, null, null, null, false);
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
		cAdapter = new CollectFragmentAdapter(mActivity, list, userid);
		mPullToRefreshListView.getRefreshableView().setAdapter(cAdapter);
	}

	@Override
	public boolean onBackPressed() {
		return false;
	}

	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == mActivity.RESULT_OK) {
			switch (requestCode) {
			case 5:
				sex = data.getExtras().getString("sex");// ,,weight,tag
				age = data.getExtras().getString("age");//
				height = data.getExtras().getString("height");//
				weight = data.getExtras().getString("weight");//
				tag = data.getExtras().getString("tag");//
//				age =TextUtils.isEmpty(age)?"":StringUtils.FormatDict(15, lage);
//				height = TextUtils.isEmpty(height)?"":StringUtils.FormatDict(8, lheight);
//				weight = TextUtils.isEmpty(weight)?"":StringUtils.FormatDict(14, lweight);
				showHaveMessages();
				break;
			default:
				break;
			}
		}
	}

	
	private void intentToSearch(){
		Intent i = new Intent(mActivity,ConditionSelectionActivity.class);
		i.putExtra("sex", sex);
		i.putExtra("age",age );
		i.putExtra("height", height);
		i.putExtra("weight", weight);
		i.putExtra("tag", tag);
		CollectFragment.this.startActivityForResult(i, 5);
	}
	
	private void showHaveMessages(){
		if (!TextUtils.isEmpty(sex) || !TextUtils.isEmpty(age) || !TextUtils.isEmpty(height) || !TextUtils.isEmpty(weight) || !TextUtils.isEmpty(tag)) {
			iv_have_messages.setVisibility(View.VISIBLE);
		}
	}
	
	
	
	
	private void requestgetdata(String k, String s, String a, String h,String w, String t, boolean addmore) {
		if (!MPlusApplication.isLogin) {
			ToastTool.showShortToast(mActivity, "你还没有登录，请先去登录！");
		} else {
			userid = UserPaser.GetInstance().userid;
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
					RequestManger.GetMyFavorite(mActivity, userid, currpages,10, k, s, a, h, w, t, new Listener<RequestCall>() {

								@Override
								public void onResponse(RequestCall response) {
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
				RequestManger.GetMyFavorite(mActivity, userid, 1, 10, k, s, a,h, w, t, new Listener<RequestCall>() {
							@Override
							public void onResponse(RequestCall response) {
								mPullToRefreshListView.onRefreshComplete();
								if (response.getParser().getResultSuccess())
									initViewData(ActorListPaser.GetInstance(3));
							}
						}, errorListener);
			}
		}
	}
}
