package com.mplus.mplus.activity.usercenter;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.CheckUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class ModificationChangePswActivity extends BaseActivity{
	private EditText et_old_passworld,et_new_passworld,et_sure_new_passworld;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modification_changepsw);
		initTitle(getString(R.string.user_change_password));
		initShowRight(0, getString(R.string.save), false, true, 0);
		et_old_passworld=(EditText) findViewById(R.id.et_old_passworld);
		et_new_passworld=(EditText) findViewById(R.id.et_new_passworld);
		et_sure_new_passworld=(EditText) findViewById(R.id.et_sure_new_passworld);
		addRightClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String oldpsw=et_old_passworld.getText().toString();
				String newpsw=et_new_passworld.getText().toString();
				String surepsw=et_sure_new_passworld.getText().toString();
				if (CheckUtils.checkChangePasswrod(oldpsw, newpsw, surepsw)) {
					RequestManger.ChangePsw(ModificationChangePswActivity.this, UserPaser.GetInstance().userid, oldpsw, newpsw, listener, errorListener);
				}
				
			}
		});
	}
	
	private Listener<RequestCall> listener=new Listener<RequestCall>() {

		@Override
		public void onResponse(RequestCall response) {
			Toast.makeText(ModificationChangePswActivity.this, response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
			if (response.getParser().getResultSuccess()) {
				MPlusApplication.isLogin=false;
				Intent backdata=new Intent();
				setResult(RESULT_OK,backdata);
				ModificationChangePswActivity.this.finish();
			}
		}
	};
	
}
