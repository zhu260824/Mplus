package com.mplus.mplus.activity.upresume;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.common.UploadPicturesActivity;
import com.mplus.mplus.activity.usercenter.EditActorTagActivity;
import com.mplus.mplus.activity.usercenter.ModificationGenderActivity;
import com.mplus.mplus.activity.usercenter.ModificationMoreMsgActivity;
import com.mplus.mplus.activity.usercenter.ModificationOneLineMsgActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.manger.SaveTools;
import com.mplus.mplus.paser.common.IntentEditTextData;
import com.mplus.mplus.paser.login.User;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.mplus.mplus.utils.ToastTool;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FirstStepActivity extends BaseActivity implements OnClickListener{
	private static final int CHANGENAME=1,CHANGECUSNAME=2,CHANGEGENDER=3,CHANGEAGE=4,CHANGEHEIGHT=5,CHANGEWEIGHT=6,
			 CHANGESIGNATURE=7,CHANGEPROFILE=8,CHANGEPIC=9,CHANGETAG=10,TONEXT=11;
	private LinearLayout lin_change_userico, lin_name, lin_cusname, lin_gender, lin_age, lin_height, lin_weight,
			lin_signature, lin_profile,lin_tags;
	private TextView tv_name, tv_cusname, tv_gender, tv_age,tv_tags, tv_height, tv_weight, tv_signature, tv_profile,tv_next;
	private ImageView iv_ico;
	private DisplayImageOptions options;
	private IntentEditTextData ed;
	private User user;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upresume_firststep);
		initTitle(getString(R.string.user_base_data));
		lin_name=(LinearLayout) findViewById(R.id.lin_name);
		lin_cusname=(LinearLayout) findViewById(R.id.lin_cusname);
		lin_gender=(LinearLayout) findViewById(R.id.lin_gender);
		lin_age=(LinearLayout) findViewById(R.id.lin_age);
		lin_height=(LinearLayout) findViewById(R.id.lin_height);
		lin_weight=(LinearLayout) findViewById(R.id.lin_weight);
		lin_tags=(LinearLayout) findViewById(R.id.lin_tags);
		lin_signature=(LinearLayout) findViewById(R.id.lin_signature);
		lin_profile=(LinearLayout) findViewById(R.id.lin_profile);
		lin_change_userico=(LinearLayout) findViewById(R.id.lin_change_userico);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_cusname=(TextView) findViewById(R.id.tv_cusname);
		tv_gender=(TextView) findViewById(R.id.tv_gender);
		tv_age=(TextView) findViewById(R.id.tv_age);
		tv_height=(TextView) findViewById(R.id.tv_height);
		tv_weight=(TextView) findViewById(R.id.tv_weight);
		tv_tags=(TextView) findViewById(R.id.tv_tags);
		tv_signature=(TextView) findViewById(R.id.tv_signature);
		tv_profile=(TextView) findViewById(R.id.tv_profile);
		tv_next=(TextView) findViewById(R.id.tv_next);
		iv_ico=(ImageView) findViewById(R.id.iv_ico);
		lin_name.setOnClickListener(this);
		lin_cusname.setOnClickListener(this);
		lin_gender.setOnClickListener(this);
		lin_age.setOnClickListener(this);
		lin_height.setOnClickListener(this);
		lin_weight.setOnClickListener(this);
		lin_tags.setOnClickListener(this);
		tv_next.setOnClickListener(this);
		lin_signature.setOnClickListener(this);
		lin_profile.setOnClickListener(this);
		lin_change_userico.setOnClickListener(this);
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		new inintDataTask().execute();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lin_change_userico:
			intent=new Intent(FirstStepActivity.this,UploadPicturesActivity.class);
			startActivityForResult(intent, CHANGEPIC);
			break;
		case R.id.lin_name:
			intent=new Intent(FirstStepActivity.this,ModificationOneLineMsgActivity.class);
			ed=new IntentEditTextData(user==null?"":user.realname==null?"":user.realname, 7, InputType.TYPE_CLASS_TEXT, getString(R.string.text_name), getString(R.string.text_name));
			intent.putExtra(ModificationOneLineMsgActivity.ETTD, ed);
			startActivityForResult(intent, CHANGENAME);
			break;
		case R.id.lin_cusname:
			intent=new Intent(FirstStepActivity.this,ModificationOneLineMsgActivity.class);
			ed=new IntentEditTextData(user==null?"":user.stageName==null?"":user.stageName, 7, InputType.TYPE_CLASS_TEXT, getString(R.string.text_personal_data_cusname), getString(R.string.text_personal_data_cusname));
			intent.putExtra(ModificationOneLineMsgActivity.ETTD, ed);
			startActivityForResult(intent, CHANGECUSNAME);		
			break;
		case R.id.lin_gender:
			intent=new Intent(FirstStepActivity.this,ModificationGenderActivity.class);
			startActivityForResult(intent, CHANGEGENDER);
			break;
		case R.id.lin_age:
			intent=new Intent(FirstStepActivity.this,ModificationOneLineMsgActivity.class);
			ed=new IntentEditTextData(user==null?"":user.age==null?"":user.age, 2, InputType.TYPE_CLASS_NUMBER, getString(R.string.change_user_age), getString(R.string.change_user_age));
			intent.putExtra(ModificationOneLineMsgActivity.ETTD, ed);
			startActivityForResult(intent, CHANGEAGE);
			break;
		case R.id.lin_height:
			intent=new Intent(FirstStepActivity.this,ModificationOneLineMsgActivity.class);
			ed=new IntentEditTextData(user==null?"":user.height==null?"":user.height, 3, InputType.TYPE_CLASS_NUMBER, getString(R.string.change_user_height), getString(R.string.change_user_height));
			intent.putExtra(ModificationOneLineMsgActivity.ETTD, ed);
			startActivityForResult(intent, CHANGEHEIGHT);
			break;
		case R.id.lin_weight:
			intent=new Intent(FirstStepActivity.this,ModificationOneLineMsgActivity.class);
			ed=new IntentEditTextData(user==null?"":user.weight==null?"":user.weight, 3, InputType.TYPE_CLASS_NUMBER, getString(R.string.change_user_weight), getString(R.string.change_user_weight));
			intent.putExtra(ModificationOneLineMsgActivity.ETTD, ed);
			startActivityForResult(intent, CHANGEWEIGHT);
			break;
		case R.id.lin_tags:
			intent=new Intent(FirstStepActivity.this,EditActorTagActivity.class);
			startActivityForResult(intent,CHANGETAG);
			break;
		case R.id.lin_signature:
			intent=new Intent(FirstStepActivity.this,ModificationMoreMsgActivity.class);
			ed=new IntentEditTextData(user==null?"":user.persign==null?"":user.persign, 350, InputType.TYPE_CLASS_TEXT, getString(R.string.change_user_signature), getString(R.string.change_user_signature));
			intent.putExtra(ModificationMoreMsgActivity.ETTD, ed);
			startActivityForResult(intent, CHANGESIGNATURE);
			break;
		case R.id.lin_profile:
			intent=new Intent(FirstStepActivity.this,ModificationMoreMsgActivity.class);
			ed=new IntentEditTextData(user==null?"":user.resume==null?"":user.resume, 350, InputType.TYPE_CLASS_TEXT, getString(R.string.change_user_profile), getString(R.string.change_user_profile));
			intent.putExtra(ModificationMoreMsgActivity.ETTD, ed);
			startActivityForResult(intent, CHANGEPROFILE);
			break;
		case R.id.tv_next:
			intent=new Intent(FirstStepActivity.this,SecondStepActivity.class);
			startActivityForResult(intent,TONEXT);
			break;
		default:
			break;
		}
	}

	private class inintDataTask extends AsyncTask<Integer, Integer, User>{

		@Override
		protected User doInBackground(Integer... params) {
			user=UserPaser.GetInstance();
			options=ImageLoadUtils.getPicOptions();
			return null;
		}
		@Override
		protected void onPostExecute(User result) {
			super.onPostExecute(result);
			RequestManger.GetUserData(FirstStepActivity.this, user.userid, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()) {
						user=UserPaser.GetInstance();
						initData(user);
					}else {
						if (response.getParser().getResponseMsg()!=null) 
							ToastTool.showShortToast(FirstStepActivity.this, response.getParser().getResponseMsg());
					}
				}
			}, errorListener);
		}
	}
	
	protected void initData(User user) {
		if (user==null) 
			return;
		if (user.portraitpath!=null) 
			ImageLoader.getInstance().displayImage(user.portraitpath, iv_ico,options);
		tv_name.setText(TextUtils.isEmpty(user.realname)?"":user.realname);
		tv_cusname.setText(TextUtils.isEmpty(user.stageName)?"":user.stageName);
		tv_signature.setText(TextUtils.isEmpty(user.persign)?"":user.persign);
		tv_gender.setText(TextUtils.isEmpty(user.sex)?"":user.sex);
		tv_age.setText(TextUtils.isEmpty(user.age)?"":user.age);
		tv_profile.setText(TextUtils.isEmpty(user.resume)?"":user.resume);
		tv_height.setText(TextUtils.isEmpty(user.height)?"0cm":user.height+"cm");
		tv_weight.setText(TextUtils.isEmpty(user.weight)?"0kg":user.weight+"kg");
		tv_tags.setText(TextUtils.isEmpty(user.feature)?"":user.feature);
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode==RESULT_OK) {
			switch (requestCode) {
			case CHANGENAME:
				changeUserData(requestCode, data);
				break;
			case CHANGECUSNAME:
				changeUserData(requestCode, data);		
				break;
			case CHANGEGENDER:
				String gender=data.getStringExtra("gender");
				tv_gender.setText(gender);
				break;
			case CHANGEAGE:
				changeUserData(requestCode, data);
				break;
			case CHANGEHEIGHT:
				changeUserData(requestCode, data);
				break;
			case CHANGEWEIGHT:
				changeUserData(requestCode, data);
				break;
			case CHANGESIGNATURE:
				changeUserData(requestCode, data);
				break;
			case CHANGEPROFILE:
				changeUserData(requestCode, data);
				break;
			case CHANGEPIC:
				String uri=data.getStringExtra("picurl");
				String version=data.getStringExtra("version");
				if (uri!=null && uri.length()>1){
					user.portraitpath=uri;
					ImageLoader.getInstance().displayImage(uri, iv_ico,options);
				}
				if (version!=null && version.length()>=1) {
					user.version=version;
					SaveTools.upDateUser(user);
				}
				break;
			case CHANGETAG:
				changeUserData(requestCode, data);
				break;
			case TONEXT:
				FirstStepActivity.this.finish();
				break;
			default:
				break;
			}
			
			
		}
	}
	
	private void changeUserData(final int requestCode,Intent data){
		if (data!=null) {
			final String value=data.getStringExtra(ModificationOneLineMsgActivity.ETTD);
			if (requestCode==CHANGENAME) {
				if (TextUtils.isEmpty(value)) {
					return;
				}
			}
			String field=getField(requestCode);
			if (TextUtils.isEmpty(field)) 
				return;
			RequestManger.updateBaseUserOne(FirstStepActivity.this, user.userid, field, value, user.version, new Listener<RequestCall>() {

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
									changeTextData(value,requestCode);
								}
							}
						} catch (JSONException e) {
								e.printStackTrace();
							}
					}else {
						String msg=response.getParser().getResponseMsg();
						if (msg!=null && msg.equals(getString(R.string.data_isold))) {
							ToastTool.showShortToast(FirstStepActivity.this, "更新数据，请稍后再提交");
							new inintDataTask().execute();
						}else if (msg!=null) {
							ToastTool.showShortToast(FirstStepActivity.this, msg);
						}
					}
				}
			}, errorListener);
		}
	}
	
	
	private void changeTextData(String value,int requestCode) {
		value=TextUtils.isEmpty(value)?"":value;
		switch (requestCode) {
		case CHANGENAME:
			tv_name.setText(value);
			break;
		case CHANGECUSNAME:
			tv_cusname.setText(value);	
			break;
		case CHANGEAGE:
			tv_age.setText(value);
			break;
		case CHANGEHEIGHT:
			tv_height.setText(value+"cm");
			break;
		case CHANGEWEIGHT:
			tv_weight.setText(value+"kg");
			break;
		case CHANGESIGNATURE:
			tv_signature.setText(value);
			break;
		case CHANGEPROFILE:
			tv_profile.setText(value);
			break;
		case CHANGETAG:
			tv_tags.setText(value);
			break;
		default:
			break;
		}
	}

	private String getField(int requestCode) {
		String field=null;
		switch (requestCode) {
		case CHANGENAME:
			field="realname";
			break;
		case CHANGECUSNAME:
			field="stageName";		
			break;
		case CHANGEAGE:
			field="age";
			break;
		case CHANGEHEIGHT:
			field="height";
			break;
		case CHANGEWEIGHT:
			field="weight";
			break;
		case CHANGESIGNATURE:
			field="persign";
			break;
		case CHANGEPROFILE:
			field="resume";
			break;
		case CHANGETAG:
			field="feature";
			break;
		default:
			break;
		}
		return field;
	}

}
