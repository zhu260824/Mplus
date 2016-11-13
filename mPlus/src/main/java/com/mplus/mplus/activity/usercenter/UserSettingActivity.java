package com.mplus.mplus.activity.usercenter;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.manger.SaveTools;
import com.mplus.mplus.paser.common.IntentEditTextData;
import com.mplus.mplus.paser.login.User;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.ToastTool;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserSettingActivity extends BaseActivity implements OnClickListener {
	private static final int CHANGEMAILADRESS=1,CHANGEPSW=2;
	private LinearLayout lin_change_password,lin_mail_address;
	private TextView tv_mail_address;
	private Intent intent;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_user_setting);
		initTitle(getString(R.string.user_usered_setting));
		lin_mail_address=(LinearLayout) findViewById(R.id.lin_mail_address);
		lin_change_password=(LinearLayout) findViewById(R.id.lin_change_password);
		tv_mail_address=(TextView) findViewById(R.id.tv_mail_address);
		lin_mail_address.setOnClickListener(this);
		lin_change_password.setOnClickListener(this);
		user=UserPaser.GetInstance();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lin_mail_address:
			intent=new Intent(UserSettingActivity.this,ModificationOneLineMsgActivity.class);
			IntentEditTextData ed=new IntentEditTextData(TextUtils.isEmpty(user.email)?"":user.email, 30, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, getString(R.string.user_mail_address), getString(R.string.user_mail_address));
			intent.putExtra(ModificationOneLineMsgActivity.ETTD, ed);
			startActivityForResult(intent, CHANGEMAILADRESS);
			break;
		case R.id.lin_change_password:
			Intent intent=new Intent(UserSettingActivity.this,ModificationChangePswActivity.class);
			startActivityForResult(intent, CHANGEPSW);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==CHANGEPSW && resultCode==RESULT_OK) {
			Intent backdata=new Intent();
			setResult(RESULT_OK,backdata);
			UserSettingActivity.this.finish();
		}else if (requestCode==CHANGEMAILADRESS && resultCode==RESULT_OK) {
			String email=data.getStringExtra(ModificationOneLineMsgActivity.ETTD);
			changeUserEmaile(email);
		}
	}
	
	private void changeUserEmaile(String email){
			if (TextUtils.isEmpty(email)) 
				return;
			RequestManger.updateBaseUserOne(UserSettingActivity.this,user.userid, "email", email, user.version, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()) {	
						try {
							String resonses=response.getParser().getData();
							if (resonses!=null) {
								JSONObject jsObject=new JSONObject(resonses);
								if (jsObject!=null) {
									String version=jsObject.getString("version");
									user.version=version;
									SaveTools.upDateUser(user);
									RequestManger.GetUserData(UserSettingActivity.this, user.userid, new Listener<RequestCall>() {

										@Override
										public void onResponse(RequestCall response) {
											if (response.getParser().getResultSuccess()) {
												user=UserPaser.GetInstance();
											}else {
												if (response.getParser().getResponseMsg()!=null) 
													ToastTool.showShortToast(UserSettingActivity.this, response.getParser().getResponseMsg());
											}
										}
									}, errorListener);
								}
							}
						} catch (JSONException e) {
								e.printStackTrace();
							}
					}else {
						String msg=response.getParser().getResponseMsg();
						if (msg!=null && msg.equals(getString(R.string.data_isold))) {
							ToastTool.showShortToast(UserSettingActivity.this, "更新数据，请稍后再提交");
						
						}else if (msg!=null) {
							ToastTool.showShortToast(UserSettingActivity.this, msg);
						}
					}
				}
			}, errorListener);
	}
	
}
