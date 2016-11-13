package com.mplus.mplus.activity.usercenter;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshSwipeListView;
import com.handmark.pulltorefresh.library.SwipeListView;
import com.mplus.mplus.R;
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

public class EditActorHistoryActivity extends BaseActivity{
	private SwipeListView mSwipeListView;  
	private EditActorHistoryAdapter adapter;
	private String customerid;
	private ImageView iv_add;
	private static final int ADDLIST=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_actorhistory);
		initTitle(getString(R.string.actor_user_perform_history));
		PullToRefreshSwipeListView mPullRefreshListView = (PullToRefreshSwipeListView) findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.DISABLED);
		mSwipeListView = mPullRefreshListView.getRefreshableView();
//		footerView=View.inflate(EditActorHistoryActivity.this, R.layout.items_add_actor_history, null);
//		mSwipeListView.addFooterView(footerView);
//		footerView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent=new Intent(EditActorHistoryActivity.this,EditActorHistoyDialog.class);
//				intent.putExtra("IntentType", 1);
//				startActivityForResult(intent, ADDLIST);;
//			}
//		});
		iv_add=(ImageView) findViewById(R.id.iv_add);
		iv_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(EditActorHistoryActivity.this,EditActorHistoyDialog.class);
				intent.putExtra("IntentType", 1);
				startActivityForResult(intent, ADDLIST);;
			}
		});
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
			RequestManger.GetActorHistory(EditActorHistoryActivity.this, customerid, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()){ 
						adapter=new EditActorHistoryAdapter(EditActorHistoryActivity.this, ActorPerformPaser.GetInstance(),mSwipeListView.getRightViewWidth(),mSwipeListView);
						mSwipeListView.setAdapter(adapter);
					}else {
						if (response.getParser().getResponseMsg()!=null) 
							ToastTool.showShortToast(EditActorHistoryActivity.this, response.getParser().getResponseMsg());
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
		}
	}
	
}
