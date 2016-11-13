package com.mplus.mplus.activity.pushproject;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.adapter.pushproject.CreativeTeamAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.paser.pushproject.ProductJob;

public class CreativeTeamActivity extends BaseActivity {
	private static final int ADDROLE=1;
	private static final int CHANGEROLE=2;
	private GridView gv;
	private ArrayList<ProductJob> lists;
	private CreativeTeamAdapter cAdapter;
	private TextView tv_tonext;
	private String mproductid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creative_team);
		initTitle(getString(R.string.project_creative_team));
		tv_tonext=(TextView) findViewById(R.id.tv_tonext);
		gv=(GridView) findViewById(R.id.gv);
		lists=new ArrayList<ProductJob>();
		cAdapter=new CreativeTeamAdapter(CreativeTeamActivity.this,lists);
		gv.setAdapter(cAdapter);
		mproductid=getIntent().getStringExtra("mproductid");
		tv_tonext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String userid=null;
				if (MPlusApplication.isLogin) {
					userid=UserPaser.GetInstance().userid;
				}
				showLoadingDialog();
				RequestManger.postPushProductJobFirst(CreativeTeamActivity.this, userid,mproductid,lists, new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						dismissLoadingDialog();
						if (response.getParser().getResultSuccess()) {
							Toast.makeText(CreativeTeamActivity.this,"项目已提交审核！", Toast.LENGTH_SHORT).show();
							Intent backdata=new Intent();
							setResult(RESULT_OK,backdata);
							CreativeTeamActivity.this.finish();
						}else {
							if (response.getParser().getResponseMsg()!=null) 
								Toast.makeText(CreativeTeamActivity.this, response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
						}
					}
				}, errorListener);
			}
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
   		super.onActivityResult(requestCode, resultCode, data);
   		if (requestCode==ADDROLE && resultCode==RESULT_OK) {
   			if (data!=null) {
   				ProductJob pJob = (ProductJob) data.getSerializableExtra("pjob");
		   		lists.add(pJob);
		   		cAdapter.notifyDataSetChanged();
			}
		}else if (requestCode==CHANGEROLE && resultCode==RESULT_OK) {
			ProductJob pJob = (ProductJob) data.getSerializableExtra("pjob");
			int listIndex=(Integer) data.getExtras().get("listIndex");
			if (listIndex<lists.size()) {
				lists.set(listIndex, pJob);
			}
	   		cAdapter.notifyDataSetChanged();
		}
	}
}
