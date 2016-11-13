package com.mplus.mplus.activity.upresume;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshSwipeListView;
import com.handmark.pulltorefresh.library.SwipeListView;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.usercenter.EditActorHistoyDialog;
import com.mplus.mplus.adapter.usercenter.EditActorHistoryAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorPerformPaser;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.ToastTool;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondStepActivity extends BaseActivity implements OnClickListener {
	private SwipeListView mSwipeListView;  
	private EditActorHistoryAdapter adapter;
	private String customerid;
	private ImageView iv_add;
	private static final int ADDLIST=1,TONEXT=2;
	private TextView tv_skip,tv_next;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upresume_secondstep);
		initTitle(getString(R.string.actor_user_perform_history));
		PullToRefreshSwipeListView mPullRefreshListView = (PullToRefreshSwipeListView) findViewById(R.id.pull_refresh_list);
		tv_skip=(TextView) findViewById(R.id.tv_skip);
		tv_next=(TextView) findViewById(R.id.tv_next);
		iv_add=(ImageView) findViewById(R.id.iv_add);
		iv_add.setOnClickListener(this);
		tv_skip.setOnClickListener(this);
		tv_next.setOnClickListener(this);
		mPullRefreshListView.setMode(Mode.DISABLED);
		mSwipeListView = mPullRefreshListView.getRefreshableView();
//		footerView=View.inflate(SecondStepActivity.this, R.layout.items_add_actor_history, null);
//		mSwipeListView.addFooterView(footerView);
//		footerView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent=new Intent(SecondStepActivity.this,EditActorHistoyDialog.class);
//				intent.putExtra("IntentType", 1);
//				startActivityForResult(intent, ADDLIST);;
//			}
//		});
		new initData().execute();
	}
	
	private class initData extends AsyncTask<Integer, Integer, Integer>{

		@Override
		protected Integer doInBackground(Integer... params) {
			customerid=UserPaser.GetInstance().customerid;
			return null;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			RequestManger.GetActorHistory(SecondStepActivity.this, customerid, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()){ 
						adapter=new EditActorHistoryAdapter(SecondStepActivity.this, ActorPerformPaser.GetInstance(),mSwipeListView.getRightViewWidth(),mSwipeListView);
						mSwipeListView.setAdapter(adapter);
					}else {
						if (response.getParser().getResponseMsg()!=null) 
							ToastTool.showShortToast(SecondStepActivity.this, response.getParser().getResponseMsg());
					}
				}
			}, errorListener);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==ADDLIST && resultCode==RESULT_OK) {
			new initData().execute();
		}else if (requestCode==TONEXT && resultCode==RESULT_OK) {
			Intent backdata=new Intent();
			setResult(RESULT_OK,backdata);
			SecondStepActivity.this.finish();	
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_next:
			startActivityForResult(new Intent(SecondStepActivity.this,ThirdStepActivity.class),TONEXT);
			break;
		case R.id.tv_skip:
			startActivityForResult(new Intent(SecondStepActivity.this,ThirdStepActivity.class),TONEXT);
			break;
		case R.id.iv_add:
			Intent intent=new Intent(SecondStepActivity.this,EditActorHistoyDialog.class);
			intent.putExtra("IntentType", 1);
			startActivityForResult(intent, ADDLIST);
			break;
		default:
			break;
		}
	}
}
