package com.mplus.mplus.activity.temporary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.main.PersonSelectActivity;
import com.mplus.mplus.activity.project.CollectResultActivity;
import com.mplus.mplus.adapter.AlternativeImageinedFigureAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.AlternativeImaginedFigureList;
import com.mplus.mplus.paser.AlternativeImaginedFigurePaser;
import com.mplus.mplus.paser.common.Page;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.DialongUtils;
import com.mplus.mplus.utils.ToastTool;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 备选影人 item items_alternative_imagined_figure
 *
 */
public class AlternativeImaginedFigureActivity extends BaseActivity {
	private PullToRefreshListView mPullToRefreshListView_alterenative_imagined;
	private ArrayList<AlternativeImaginedFigureList.contents> myProjectArrayList;
	private TextView tv_project_name_alternative;
	private TextView tv_plan_name_alternative,tv_alterenative_imagined;
	private AlternativeImageinedFigureAdapter alternativeImaginedFigureAdapter;
	private String mproductid;//项目id
	private String planId;
	private ArrayList<Map<String,Object>> aifaArrayList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alternative_imagined_figure);
		tv_project_name_alternative = (TextView) findViewById(R.id.tv_project_name_alternative);
		tv_plan_name_alternative = (TextView) findViewById(R.id.tv_plan_name_alternative);
		tv_alterenative_imagined = (TextView) findViewById(R.id.tv_alterenative_imagined);
		Intent intent = getIntent();
		final String mproductname=intent.getStringExtra("mproductname");
		final String planname=intent.getStringExtra("planname");
		tv_project_name_alternative.setText(mproductname);
		tv_plan_name_alternative.setText(planname);
		mproductid=intent.getStringExtra("mproductid");
		planId=intent.getStringExtra("planId");
		initTitle("备选演员");
		initShowRight(0,"全部通知", false, true, 40);
		mPullToRefreshListView_alterenative_imagined = (PullToRefreshListView) findViewById(
				R.id.mPullToRefreshListView_alterenative_imagined);
		mPullToRefreshListView_alterenative_imagined.setMode(Mode.BOTH);
		mPullToRefreshListView_alterenative_imagined.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// 这里写下拉刷新的任务
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				RequestManger.GetAlternativeInagined(AlternativeImaginedFigureActivity.this, planId, 1, 20,
						new Listener<RequestCall>() {
							public void onResponse(RequestCall response) {
								mPullToRefreshListView_alterenative_imagined.onRefreshComplete();
								dismissLoadingDialog();
								if (response.getParser().getResultSuccess()) {
									myProjectArrayList = AlternativeImaginedFigurePaser.GetInstance(2).getContents();
									alternativeImaginedFigureAdapter = new AlternativeImageinedFigureAdapter(AlternativeImaginedFigureActivity.this,
											myProjectArrayList,planId, false);
									mPullToRefreshListView_alterenative_imagined.getRefreshableView()
											.setAdapter(alternativeImaginedFigureAdapter);
								}else {
									if (response.getParser().getResponseMsg() != null)
										Toast.makeText(AlternativeImaginedFigureActivity.this,
												response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
								}
							}
						}, errorListener);
			}

			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				Page page;
				page = AlternativeImaginedFigurePaser.GetInstance(2).getPage();
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
					RequestManger.GetAlternativeInagined(AlternativeImaginedFigureActivity.this, planId, currpages, 20,
							new Listener<RequestCall>() {
						@Override
						public void onResponse(RequestCall response) {
							mPullToRefreshListView_alterenative_imagined.onRefreshComplete();
							if (response.getParser().getResultSuccess()) {
								myProjectArrayList = AlternativeImaginedFigurePaser.GetInstance(2).getContents();
								alternativeImaginedFigureAdapter.notifyData(myProjectArrayList);

							} else {
								if (response.getParser().getResponseMsg() != null)
									Toast.makeText(AlternativeImaginedFigureActivity.this,
											response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
							}
						}
					}, errorListener);

				} else {
					new GetDataTask(mPullToRefreshListView_alterenative_imagined).execute();
				}
			}
		});

		new inintDataTask().execute();
		//右键
		addRightClickListener(new OnClickListener() {
			public void onClick(View v) {
				aifaArrayList=new ArrayList<Map<String,Object>>();
				for(int i=0;i<myProjectArrayList.size();i++){
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("userId", myProjectArrayList.get(i).getUserId());
					map.put("planId", planId);
					aifaArrayList.add(map);
				}
				RequestManger.PostInform(AlternativeImaginedFigureActivity.this, UserPaser.GetInstance().userid,aifaArrayList, new Listener<RequestCall>() {
					public void onResponse(RequestCall response) {
						if (response.getParser().getResultSuccess()) {
							ToastTool.showShortToast(AlternativeImaginedFigureActivity.this, "通知成功！");
						}else {
							if (response.getParser().getResponseMsg()!=null) 
								ToastTool.showShortToast(AlternativeImaginedFigureActivity.this,response.getParser().getResponseMsg());
						}
					}
				}, errorListener);
			}
		});
		tv_alterenative_imagined.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialongUtils.showSelcedType(AlternativeImaginedFigureActivity.this, new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent=new Intent(AlternativeImaginedFigureActivity.this,PersonSelectActivity.class);
						intent.putExtra("planid", planId);
						intent.putExtra("mproductname",mproductname);
						intent.putExtra("planname", planname);
						startActivity(intent);
					}
				}, new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent intent=new Intent(AlternativeImaginedFigureActivity.this,CollectResultActivity.class);
						intent.putExtra("planid", planId);
						intent.putExtra("mproductname",mproductname);
						intent.putExtra("planname", planname);
						startActivity(intent);
					}
				});
			}
		});
	}

	private class inintDataTask extends AsyncTask<Integer, Integer, RequestCall> {
		protected RequestCall doInBackground(Integer... params) {
			return null;
		}
		protected void onPostExecute(RequestCall result) {
			super.onPostExecute(result);
			showLoadingDialog();
			// mproductid
			RequestManger.GetAlternativeInagined(AlternativeImaginedFigureActivity.this, planId, 1, 20,
					new Listener<RequestCall>() {
						public void onResponse(RequestCall response) {
							dismissLoadingDialog();
							if (response.getParser().getResultSuccess()) {
								myProjectArrayList = AlternativeImaginedFigurePaser.GetInstance(2).getContents();
								alternativeImaginedFigureAdapter = new AlternativeImageinedFigureAdapter(AlternativeImaginedFigureActivity.this,
										myProjectArrayList,planId, false);
								mPullToRefreshListView_alterenative_imagined.getRefreshableView()
										.setAdapter(alternativeImaginedFigureAdapter);
							}
						}
					}, errorListener);
		}
	}
}
