package com.mplus.mplus.activity.temporary;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.project.PushNewRoleNeedsAcivity;
import com.mplus.mplus.adapter.ProjectNameAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.common.Page;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.paser.project.ProjectNameList;
import com.mplus.mplus.paser.project.ProjectNameListPaser;
import com.mplus.mplus.utils.StringUtils;
import com.mplus.mplus.view.MyDialog;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 我的项目名称
 *
 */
public class ProjectNameActivity extends BaseActivity {
	private static final int CHANGEPIC = 1;
	private PullToRefreshListView mPullRefreshListView;
	private ArrayList<ProjectNameList.contents> myProjectArrayList;
	private ProjectNameAdapter projectAdapter;
	private Boolean canSelced = false;
	private TextView tv_project_name;
	private String mproductname,mproductId;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_name);
		tv_project_name = (TextView) findViewById(R.id.tv_project_name);
		Intent it = getIntent();
		mproductname=it.getStringExtra("mproductname");
		mproductId=it.getStringExtra("mproductid");
		initTitle(mproductname);
		initShowRight(0, "编辑", false, true, 48);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.mPullToRefreshListView_project_name);
		mPullRefreshListView.setMode(Mode.BOTH);
		View emptyView=View.inflate(ProjectNameActivity.this, R.layout.view_empty_list, null);
		TextView list_empty_text=(TextView) emptyView.findViewById(R.id.list_empty_text);
		list_empty_text.setText("该项目暂未发布职位	");
		mPullRefreshListView.setEmptyView(emptyView);
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// 这里写下拉刷新的任务
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				RequestManger.GetProjectName(ProjectNameActivity.this,mproductId, 1, 20, new Listener<RequestCall>() {
					public void onResponse(RequestCall response) {
						mPullRefreshListView.onRefreshComplete();
						if (response.getParser().getResultSuccess()) {
							myProjectArrayList = ProjectNameListPaser.GetInstance(2).getContents();
							projectAdapter = new ProjectNameAdapter(ProjectNameActivity.this, myProjectArrayList,false,mproductname,mproductId);
							mPullRefreshListView.getRefreshableView().setAdapter(projectAdapter);
						} else {
							if (response.getParser().getResponseMsg() != null)
								Toast.makeText(ProjectNameActivity.this, response.getParser().getResponseMsg(),Toast.LENGTH_SHORT).show();
						}
					}
				}, errorListener);
			}

			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
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

					RequestManger.GetProjectName(ProjectNameActivity.this,mproductId, currpages, 20,
							new Listener<RequestCall>() {
						@Override
						public void onResponse(RequestCall response) {
							mPullRefreshListView.onRefreshComplete();
							if (response.getParser().getResultSuccess()) {
								myProjectArrayList = ProjectNameListPaser.GetInstance(2).getContents();
								projectAdapter.notifyData(myProjectArrayList);

							} else {
								if (response.getParser().getResponseMsg() != null)
									Toast.makeText(ProjectNameActivity.this, response.getParser().getResponseMsg(),
											Toast.LENGTH_SHORT).show();
							}
						}
					}, errorListener);

				} else {
					new GetDataTask(mPullRefreshListView).execute();
				}
			}
		});

		new inintDataTask().execute();
		addRightClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (getRightText().equals("编辑")) {
					canSelced = true;
					initShowRight(0, "完成", false, true, 48);
					projectAdapter = new ProjectNameAdapter(ProjectNameActivity.this, myProjectArrayList, canSelced,mproductname,mproductId);
					projectAdapter.notifyDataSetChanged();
					mPullRefreshListView.getRefreshableView().setAdapter(projectAdapter);
					tv_project_name.setText("删除职位");
					mPullRefreshListView.setMode(Mode.DISABLED);
				} else if (getRightText().equals("完成")) {
					canSelced = false;
					initShowRight(0, "编辑", false, true, 48);
					projectAdapter = new ProjectNameAdapter(ProjectNameActivity.this, myProjectArrayList, canSelced,mproductname,mproductId);
					projectAdapter.notifyDataSetChanged();
					mPullRefreshListView.getRefreshableView().setAdapter(projectAdapter);
					tv_project_name.setText("发布新职位");
					mPullRefreshListView.setMode(Mode.BOTH);
				}
			}
		});
		tv_project_name.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (canSelced) {
					setToRead();
				} else {
//					boolean stage=getIntent().getBooleanExtra("stage",false);
//					if (stage) {
						Intent intent = new Intent(ProjectNameActivity.this,PushNewRoleNeedsAcivity.class);
						intent.putExtra("mproductid", mproductId);
						startActivityForResult(intent, CHANGEPIC);	
//					}else {
//						ToastTool.showShortToast(ProjectNameActivity.this, "请等待项目通过后再发布角色需求");
//					}
				}
			}
		});
	}

	private void setToRead() {
		//删除职位
		String messageIds = StringUtils.ListToString(projectAdapter.getSelcedlist());
		showTip(messageIds);
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == CHANGEPIC && arg1 == RESULT_OK) {

			RequestManger.GetProjectName(ProjectNameActivity.this,mproductId, 1, 20, new Listener<RequestCall>() {
				public void onResponse(RequestCall response) {
					dismissLoadingDialog();
					if (response.getParser().getResultSuccess()) {
						myProjectArrayList = ProjectNameListPaser.GetInstance(2).getContents();
						projectAdapter = new ProjectNameAdapter(ProjectNameActivity.this, myProjectArrayList, false,mproductname,mproductId);
						mPullRefreshListView.getRefreshableView().setAdapter(projectAdapter);
					}
				}
			}, errorListener);
		}
	}
	private class inintDataTask extends AsyncTask<Integer, Integer, RequestCall> {

		protected RequestCall doInBackground(Integer... params) {
			return null;
		}
		protected void onPostExecute(RequestCall result) {
			super.onPostExecute(result);
			showLoadingDialog();
			// 1479

			RequestManger.GetProjectName(ProjectNameActivity.this,mproductId, 1, 20, new Listener<RequestCall>() {
				public void onResponse(RequestCall response) {
					dismissLoadingDialog();
					if (response.getParser().getResultSuccess()) {
						myProjectArrayList = ProjectNameListPaser.GetInstance(2).getContents();
						projectAdapter = new ProjectNameAdapter(ProjectNameActivity.this, myProjectArrayList,false,mproductname,mproductId);
						mPullRefreshListView.getRefreshableView().setAdapter(projectAdapter);
					}
				}
			}, errorListener);
		}
	}
	
	private void showTip(final String messageIds) {
		View view = View.inflate(ProjectNameActivity.this, R.layout.dialog_forget,null);
		AutoUtils.autoSize(view);
		final MyDialog dialog = new MyDialog(ProjectNameActivity.this,R.style.CustomDialog);
		LinearLayout.LayoutParams dialoglp = new LinearLayout.LayoutParams(MPlusApplication.Width * 4 / 5,LinearLayout.LayoutParams.WRAP_CONTENT);
		dialog.addContentView(view, dialoglp);
		dialog.setCancelable(true);
		dialog.show();
		TextView tv_tip=(TextView) view.findViewById(R.id.tv_tip);
		TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
		TextView tv_cacel = (TextView) view.findViewById(R.id.tv_cancel);
		tv_tip.setText("是否删除当前职位？");
		tv_sure.setText("确认");
		tv_cacel.setText("取消");
		tv_cacel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (dialog!=null && dialog.isShowing()) 
					dialog.dismiss();
			}
		});
		tv_sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (dialog!=null && dialog.isShowing()) 
					dialog.dismiss();
				RequestManger.deletePosition(ProjectNameActivity.this, messageIds,UserPaser.GetInstance().userid, new Listener<RequestCall>() {
					
					public void onResponse(RequestCall response) {
						if (response.getParser().getResultSuccess()) {
							canSelced = false;
							if (myProjectArrayList != null && myProjectArrayList.size() >= 1
									&& projectAdapter.getSelcedlist() != null && projectAdapter.getSelcedlist().size() >= 1) {
								for (int i = 0; i < myProjectArrayList.size(); i++) {
									for (int j = 0; j < projectAdapter.getSelcedlist().size(); j++) {
										if (myProjectArrayList.get(i).getPlanId()
												.equals(projectAdapter.getSelcedlist().get(j))) {
											myProjectArrayList.remove(i);
										}
									}
								}
							}
							projectAdapter = new ProjectNameAdapter(ProjectNameActivity.this, myProjectArrayList, canSelced,mproductname,mproductId);
							projectAdapter.notifyDataSetChanged();
							mPullRefreshListView.getRefreshableView().setAdapter(projectAdapter);
							initShowRight(0, "编辑", false, true, 48);
							tv_project_name.setText("发布新职位");
						} else {
							if (response.getParser().getResponseMsg() != null)
								Toast.makeText(ProjectNameActivity.this, response.getParser().getResponseMsg(),Toast.LENGTH_SHORT).show();
						}
					}
				}, errorListener);
			}
		});
	}
	
	
	
	
}
