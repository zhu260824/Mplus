package com.mplus.mplus.activity.actorcenter;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.adapter.usercenter.SelcedProjectAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ProjectJobList;
import com.mplus.mplus.paser.actor.ProjectJobListPaser;
import com.mplus.mplus.utils.ToastTool;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ExpandableListView;

public class SelcedUserProjectActivity extends BaseActivity{
	private ExpandableListView expListView;
	private String userid=null,actoruserid=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selced_userproject);
		initTitle(getString(R.string.actor_selced_project));
		expListView=(ExpandableListView) findViewById(R.id.explist);
		new initData().execute();
	}
	
	private class initData extends AsyncTask<Integer, Integer, Integer>{

		@Override
		protected Integer doInBackground(Integer... params) {
			userid=getIntent().getStringExtra("userid");
			actoruserid=getIntent().getStringExtra("actoruserid");
			return null;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			RequestManger.GetProjectJob(SelcedUserProjectActivity.this, userid, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()) {
						ArrayList<ProjectJobList> list=ProjectJobListPaser.GetInstance();
						if (list!=null && list.size()>=1) {
							expListView.setAdapter(new SelcedProjectAdapter(SelcedUserProjectActivity.this,list,userid,actoruserid));
							expListView.expandGroup(0);
						}else {
							ToastTool.showShortToast(SelcedUserProjectActivity.this, "您暂时木有通过审核的项目！");
						}
						
					}else {
						if (response.getParser().getResponseMsg()!=null) 
							ToastTool.showShortToast(SelcedUserProjectActivity.this, response.getParser().getResponseMsg());
					}
					
				}
			}, errorListener);
		}
	}
	
	
	
}
