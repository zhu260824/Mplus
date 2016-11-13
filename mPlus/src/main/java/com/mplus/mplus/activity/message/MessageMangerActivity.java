package com.mplus.mplus.activity.message;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mplus.mplus.R;
import com.mplus.mplus.adapter.message.MessageManageAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.common.Page;
import com.mplus.mplus.paser.login.User;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.paser.message.MessageBean;
import com.mplus.mplus.paser.message.MessageBeanPaser;
import com.mplus.mplus.utils.StringUtils;
import com.mplus.mplus.view.MyDialog;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @Title: 消息管理
 * @Description:
 * @author zhaochaoyue
 * 
 * @date 2015年9月14日
 */
public class MessageMangerActivity extends BaseActivity {
	private final static int TODETAILS=1;
	/** 消息数据 */
	private ArrayList<MessageBean> list = new ArrayList<MessageBean>();
	private ArrayList<String> selcedlist = new ArrayList<String>();
	/** 编辑时候显示出来的底部页面，默认为隐藏 */
	private LinearLayout ll_information_manage_bottom;
	
	private TextView tv_already_information,tv_detele_information;
	
	private MessageManageAdapter informationManageAdapter;
	
	private PullToRefreshListView mPullRefreshListView;
	private User user;
	private int type=1;
	private boolean isAllSelced=false,canSelced=false;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_manage);
		ll_information_manage_bottom = (LinearLayout) findViewById(R.id.ll_information_manage_bottom);
		tv_already_information=(TextView)findViewById(R.id.tv_already_information);
		tv_detele_information=(TextView)findViewById(R.id.tv_detele_information);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		View emptyView=View.inflate(MessageMangerActivity.this, R.layout.view_empty_list, null);
		TextView list_empty_text=(TextView) emptyView.findViewById(R.id.list_empty_text);
		list_empty_text.setText("暂时还木有消息！");
		mPullRefreshListView.setEmptyView(emptyView);
		//上一页传过来标题信息
		type=getIntent().getIntExtra("type", 1);
		if(type==1){
			initTitle(getString(R.string.message_option));
		}else if(type==2){
			initTitle(getString(R.string.message_apply));
		}else if(type==3){
			initTitle(getString(R.string.message_collect));
		}else if (type==4) {
			initTitle(getString(R.string.message_interview));
		}else if (type==5) {
			initTitle(getString(R.string.message_system));
		}
		user=UserPaser.GetInstance();
		initShowRight(0, getString(R.string.message_write), false, true, 0);
		addRightClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//字段变成全选显示左边栏
				String righttext=getRightText();
				if (righttext.equals("编辑")) {
					ll_information_manage_bottom.setVisibility(View.VISIBLE);
					setRightText("全选");
					isAllSelced=false;canSelced=true;
					setAdapter(list, isAllSelced, canSelced);
				} else if(righttext.equals("全选")){
					setRightText("取消全选");
					isAllSelced=true;canSelced=true;
					setAdapter(list, isAllSelced, canSelced);
					selcedAll();
				}else if(righttext.equals("取消全选")){
					setRightText("全选");
					isAllSelced=false;canSelced=true;
					setAdapter(list, isAllSelced, canSelced);
					selcedlist=new ArrayList<String>();
				}
			}
		});

		//设置为已读
		tv_already_information.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				setToRead();
			}
		});
		tv_detele_information.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String messageIds=StringUtils.ListToString(selcedlist);
				showTip(messageIds);
			}
		});
		
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				// 这里写下拉刷新的任务
				String label = DateUtils.formatDateTime(getApplicationContext(),System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				RequestManger.getUserMessage(MessageMangerActivity.this, type, user.userid,  1, 10, new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						mPullRefreshListView.onRefreshComplete();
						if (response.getParser().getResultSuccess()) {
							list=MessageBeanPaser.GetInstance(type);
							informationManageAdapter=new MessageManageAdapter(list, MessageMangerActivity.this, isAllSelced, canSelced);
							mPullRefreshListView.getRefreshableView().setAdapter(informationManageAdapter);
						}
					}
				}, errorListener);
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getApplicationContext(),System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// 这里写上拉加载更多的任务
				Page page=MessageBeanPaser.GetPage(type);
				if (page==null){
					page=new Page();
					page.currpage=1;
					page.pagenum=1;
					page.totalcount=1;
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
					RequestManger.getUserMessage(MessageMangerActivity.this, type, user.userid,  currpages, 10, new Listener<RequestCall>() {

						@Override
						public void onResponse(RequestCall response) {
							mPullRefreshListView.onRefreshComplete();
							if (response.getParser().getResultSuccess()) {
								list=MessageBeanPaser.GetInstance(type);
								informationManageAdapter.notifyData(list);
							}
						}
					}, errorListener);
				}else {
					new GetDataTask(mPullRefreshListView).execute();
				}
			}
		});
		
		
		mPullRefreshListView.getRefreshableView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if (canSelced) {
					if (view!=null) {
						ImageView iv_information_onoff=(ImageView) view.findViewById(R.id.iv_information_onoff);
						TextView tv_information_manage_content=(TextView) view.findViewById(R.id.tv_information_manage_content);
						if (iv_information_onoff!=null) {
							boolean isSelced=(Boolean) iv_information_onoff.getTag();
							if (isSelced) {
								iv_information_onoff.setBackgroundResource(R.drawable.message_off);
								iv_information_onoff.setTag(false);
								if (tv_information_manage_content!=null && tv_information_manage_content.getTag()!=null) 
									selcedlist.remove(selcedlist.indexOf(tv_information_manage_content.getTag().toString()));
							}else {
								iv_information_onoff.setBackgroundResource(R.drawable.message_on);
								iv_information_onoff.setTag(true);
								if (tv_information_manage_content!=null && tv_information_manage_content.getTag()!=null) 
									selcedlist.add(tv_information_manage_content.getTag().toString());
							}
						}
					}
				}else {
					if (view!=null) {
						TextView tv_information_manage_content=(TextView) view.findViewById(R.id.tv_information_manage_content);
						if (tv_information_manage_content!=null && tv_information_manage_content.getText()!=null) {
							Intent intent=new Intent(MessageMangerActivity.this,MessageDetailsActivity.class);
							intent.putExtra("content", tv_information_manage_content.getText().toString());
							selcedlist=new ArrayList<String>();
							if (tv_information_manage_content.getTag()!=null) {
								selcedlist.add(tv_information_manage_content.getTag().toString());
							}
							startActivityForResult(intent, TODETAILS);
						}
					}
				}
			}
		});
		showLoadingDialog();
		RequestManger.getUserMessage(this, type, user.userid, 1, 10, new Listener<RequestCall>() {

			@Override
			public void onResponse(RequestCall response) {
				dismissLoadingDialog();
				if (response.getParser().getResultSuccess()) {
					new inintDataTask().execute();
				}
			}
		}, errorListener);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==TODETAILS) {
			setToRead();
		}
	}
	
	private void setToRead(){
		String messageIds=StringUtils.ListToString(selcedlist);
		RequestManger.postReadUserMessage(MessageMangerActivity.this, user.userid, messageIds, new Listener<RequestCall>() {

			@Override
			public void onResponse(RequestCall response) {
				if (response.getParser().getResultSuccess()) {
					for (int i = 0; i < list.size(); i++) {
						for (int j = 0; j < selcedlist.size(); j++) {
							if (list.get(i).msgId.equals(selcedlist.get(j))) {
								list.get(i).openStatus="0";
							}
						}
					}
					selcedlist=new ArrayList<String>();
					ll_information_manage_bottom.setVisibility(View.GONE);
					setRightText("编辑");
					isAllSelced=false;canSelced=false;
					setAdapter(list, isAllSelced, canSelced);
				}else {
					if (response.getParser().getResponseMsg()!=null) 
						Toast.makeText(MessageMangerActivity.this, response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
				}
			}
		}, errorListener);
	}
	
	
	private class inintDataTask extends AsyncTask<Integer, Integer, ArrayList<MessageBean>>{

		@Override
		protected  ArrayList<MessageBean> doInBackground(Integer... params) {
			list=MessageBeanPaser.GetInstance(type);
			return list;
		}
		@Override
		protected void onPostExecute( ArrayList<MessageBean> result) {
			super.onPostExecute(result);
			setAdapter(result, isAllSelced,canSelced);
		}
	}
	
	private void setAdapter(ArrayList<MessageBean> list,boolean isall,boolean canSelced){
		informationManageAdapter = new MessageManageAdapter(list, MessageMangerActivity.this, isall,canSelced);
		mPullRefreshListView.getRefreshableView().setAdapter(informationManageAdapter);
	}
	
	private void selcedAll(){
		for (MessageBean msg : list) {
			selcedlist.add(msg.msgId);
		}
	}
	
	private void showTip(final String messageIds){
		View view = View.inflate(MessageMangerActivity.this, R.layout.dialog_forget,null);
		AutoUtils.autoSize(view);
		final MyDialog dialog = new MyDialog(MessageMangerActivity.this,R.style.CustomDialog);
		LinearLayout.LayoutParams dialoglp = new LinearLayout.LayoutParams(MPlusApplication.Width * 4 / 5,LinearLayout.LayoutParams.WRAP_CONTENT);
		dialog.addContentView(view, dialoglp);
		dialog.setCancelable(true);
		dialog.show();
		TextView tv_tip=(TextView) view.findViewById(R.id.tv_tip);
		TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
		TextView tv_cacel = (TextView) view.findViewById(R.id.tv_cancel);
		tv_tip.setText("是否删除已选信息？");
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
				RequestManger.deleteDeleteUserMessage(MessageMangerActivity.this,user.userid,messageIds, new Listener<RequestCall>() {

					public void onResponse(RequestCall response) {
						if (response.getParser().getResultSuccess()) {
							if (list!=null && list.size()>=1 && selcedlist!=null && selcedlist.size()>=1) {
								for (int i = 0; i < list.size(); i++) {
									for (int j = 0; j < selcedlist.size(); j++) {
										if (list.get(i).msgId.equals(selcedlist.get(j))) {
											list.remove(i);
										}
									}
								}
							}
							selcedlist=new ArrayList<String>();
							ll_information_manage_bottom.setVisibility(View.GONE);
							setRightText("编辑");
							isAllSelced=false;canSelced=false;
							setAdapter(list, isAllSelced, canSelced);
						}else {
							if (response.getParser().getResponseMsg()!=null) 
								Toast.makeText(MessageMangerActivity.this, response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
						}
					}
				}, errorListener);
			}
		});
	}
	
	
	
	
}
