package com.mplus.mplus.activity.message;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.login.LoginActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.paser.message.UnReadMessage;
import com.mplus.mplus.paser.message.UnReadMessagePaser;
import com.mplus.mplus.utils.ToastTool;
import com.mplus.mplus.view.BadgeView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MessageCategoryActivity extends BaseActivity implements OnClickListener{
	private LinearLayout lin_option,lin_apply,lin_collect,lin_interview,lin_system;
	private ImageView iv_option,iv_apply,iv_collect,iv_interview,iv_system;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_category);
		initTitle(getString(R.string.message_center));
		lin_option=(LinearLayout) findViewById(R.id.lin_option);
		lin_collect=(LinearLayout) findViewById(R.id.lin_collect);
		lin_apply=(LinearLayout) findViewById(R.id.lin_apply);
		lin_interview=(LinearLayout) findViewById(R.id.lin_interview);
		lin_system=(LinearLayout) findViewById(R.id.lin_system);
		iv_option=(ImageView) findViewById(R.id.iv_option);
		iv_apply=(ImageView) findViewById(R.id.iv_apply);
		iv_collect=(ImageView) findViewById(R.id.iv_collect);
		iv_interview=(ImageView) findViewById(R.id.iv_interview);
		iv_system=(ImageView) findViewById(R.id.iv_system);
		lin_option.setOnClickListener(this);
		lin_collect.setOnClickListener(this);
		lin_apply.setOnClickListener(this);
		lin_system.setOnClickListener(this);
		lin_interview.setOnClickListener(this);
	}


	@Override
	protected void onResume() {
		super.onResume();
		new initData().execute();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lin_option:
			intentToManager(1);
			break;
		case R.id.lin_apply:
			intentToManager(2);
			break;
		case R.id.lin_collect:
			intentToManager(3);	
			break;
		case R.id.lin_interview:
			intentToManager(4);	
			break;
		case R.id.lin_system:
			intentToManager(5);
			break;
		default:
			break;
		}
	}
	
	
	private void intentToManager(int type){
		if (MPlusApplication.isLogin) {
			Intent it=new Intent(MessageCategoryActivity.this,MessageMangerActivity.class);
			it.putExtra("type", type);
			MessageCategoryActivity.this.startActivity(it);
		}else {
			startActivity(new Intent(MessageCategoryActivity.this,LoginActivity.class));
		}
		
	}
	
	
	private class initData extends AsyncTask<Integer, Integer, String>{

		@Override
		protected String doInBackground(Integer... params) {
			return  UserPaser.GetInstance().userid;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			RequestManger.getUnReadMessage(MessageCategoryActivity.this,result, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()) {
						intData(UnReadMessagePaser.GetInstance());
					}else {
						 if (response.getParser().getResponseMsg()!=null) 
							ToastTool.showShortToast(MessageCategoryActivity.this, response.getParser().getResponseMsg());
					}
				}
			}, errorListener);
		}
	}
	
	
	
	private void intData(ArrayList<UnReadMessage> list){
		for (UnReadMessage unReadMessage : list) {
			//备选消息10009004，面试消息10009005，系统消息10009002，申请消息10009006，收藏消息10009007}
			if (unReadMessage.getMsgSpec().equals("10009004")) {
				initMessages(iv_option, Integer.valueOf(unReadMessage.getUnReadMsgNum()));
			} else if (unReadMessage.getMsgSpec().equals("10009006")) {
				initMessages(iv_apply, Integer.valueOf(unReadMessage.getUnReadMsgNum()));
			}else if (unReadMessage.getMsgSpec().equals("10009007")) {
				initMessages(iv_collect, Integer.valueOf(unReadMessage.getUnReadMsgNum()));
			}else if (unReadMessage.getMsgSpec().equals("10009005")) {
				initMessages(iv_interview, Integer.valueOf(unReadMessage.getUnReadMsgNum()));
			}else if (unReadMessage.getMsgSpec().equals("10009002")) {
				initMessages(iv_system, Integer.valueOf(unReadMessage.getUnReadMsgNum()));
			}
		}
	}
	
	private void initMessages(ImageView iv,int msgNum){
		if (iv==null) 
			return;
		if (msgNum==0) 
			return;
		BadgeView badgeView=new BadgeView(MessageCategoryActivity.this);
		badgeView.setTargetView(iv);
		badgeView.setBadgeCount(msgNum);
		badgeView.setTextSize(6);
//		badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
//		badgeView.setBackgroundColor(Color.RED);
	}
	
}
