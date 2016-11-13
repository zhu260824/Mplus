package com.mplus.mplus.activity.usercenter;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.common.UploadPicturesActivity;
import com.mplus.mplus.adapter.actor.ActorPerformAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.manger.SaveTools;
import com.mplus.mplus.paser.actor.ActorPerformPaser;
import com.mplus.mplus.paser.actor.ActorPhotoList;
import com.mplus.mplus.paser.actor.ActorPhotoListPaser;
import com.mplus.mplus.paser.common.IntentEditTextData;
import com.mplus.mplus.paser.login.User;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.mplus.mplus.utils.ToastTool;
import com.mplus.mplus.utils.UrlStingTool;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class UserCenterActivity extends BaseActivity implements OnClickListener {
	private static final int CHANGENAME=1,CHANGECUSNAME=2,CHANGEGENDER=3,CHANGEAGE=4,CHANGEHEIGHT=5,CHANGEWEIGHT=6,
			 CHANGESIGNATURE=7,CHANGEPROFILE=8,CHANGEPIC=9,CHANGETAG=10;
	private LinearLayout lin_change_userico, lin_photo_list, lin_name, lin_cusname, lin_gender, lin_age, lin_height, lin_weight,
			lin_signature, lin_profile, lin_history_project,lin_tags,lin_photos;
	private TextView tv_name, tv_cusname, tv_gender, tv_age,tv_tags, tv_height, tv_weight, tv_signature, tv_profile;
	private ImageView iv_ico;
	private ListView list_history;
	private DisplayImageOptions options;
	private IntentEditTextData ed;
	private User user;
	private Intent intent;
	private HorizontalScrollView horscroll;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usercenter);
		initTitle(getString(R.string.personaldata));
		list_history=(ListView) findViewById(R.id.list_history);
		View headerView=View.inflate(this, R.layout.heard_usercenter, null);
		lin_name=(LinearLayout) headerView.findViewById(R.id.lin_name);
		lin_cusname=(LinearLayout)headerView. findViewById(R.id.lin_cusname);
		lin_gender=(LinearLayout) headerView.findViewById(R.id.lin_gender);
		lin_age=(LinearLayout) headerView.findViewById(R.id.lin_age);
		lin_height=(LinearLayout) headerView.findViewById(R.id.lin_height);
		lin_weight=(LinearLayout) headerView.findViewById(R.id.lin_weight);
		lin_tags=(LinearLayout) headerView.findViewById(R.id.lin_tags);
		lin_signature=(LinearLayout) headerView.findViewById(R.id.lin_signature);
		lin_profile=(LinearLayout) headerView.findViewById(R.id.lin_profile);
		lin_history_project=(LinearLayout) headerView.findViewById(R.id.lin_history_project);
		lin_change_userico=(LinearLayout) headerView.findViewById(R.id.lin_change_userico);
		tv_name=(TextView) headerView.findViewById(R.id.tv_name);
		tv_cusname=(TextView) headerView.findViewById(R.id.tv_cusname);
		tv_gender=(TextView) headerView.findViewById(R.id.tv_gender);
		tv_age=(TextView) headerView.findViewById(R.id.tv_age);
		tv_height=(TextView) headerView.findViewById(R.id.tv_height);
		tv_weight=(TextView) headerView.findViewById(R.id.tv_weight);
		tv_tags=(TextView) headerView.findViewById(R.id.tv_tags);
		tv_signature=(TextView) headerView.findViewById(R.id.tv_signature);
		tv_profile=(TextView) headerView.findViewById(R.id.tv_profile);
		iv_ico=(ImageView) headerView.findViewById(R.id.iv_ico);
		View footerView=View.inflate(this, R.layout.footer_usercenter, null);
		lin_photo_list=(LinearLayout) footerView.findViewById(R.id.lin_photo_list);
		lin_photos=(LinearLayout) footerView.findViewById(R.id.lin_photos);
		horscroll=(HorizontalScrollView) footerView.findViewById(R.id.horscroll);
		lin_name.setOnClickListener(this);
		lin_cusname.setOnClickListener(this);
		lin_gender.setOnClickListener(this);
		lin_age.setOnClickListener(this);
		lin_height.setOnClickListener(this);
		lin_weight.setOnClickListener(this);
		lin_tags.setOnClickListener(this);
		lin_signature.setOnClickListener(this);
		lin_profile.setOnClickListener(this);
		lin_history_project.setOnClickListener(this);
		lin_photo_list.setOnClickListener(this);
		lin_change_userico.setOnClickListener(this);
		list_history.addHeaderView(headerView);
		list_history.addFooterView(footerView);
		list_history.setAdapter(new ActorPerformAdapter(UserCenterActivity.this,null));
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
			intent=new Intent(UserCenterActivity.this,UploadPicturesActivity.class);
			startActivityForResult(intent, CHANGEPIC);
			break;
		case R.id.lin_name:
			intent=new Intent(UserCenterActivity.this,ModificationOneLineMsgActivity.class);
			ed=new IntentEditTextData(user==null?"":user.realname==null?"":user.realname, 7, InputType.TYPE_CLASS_TEXT, getString(R.string.text_name), getString(R.string.text_name));
			intent.putExtra(ModificationOneLineMsgActivity.ETTD, ed);
			startActivityForResult(intent, CHANGENAME);
			break;
		case R.id.lin_cusname:
			intent=new Intent(UserCenterActivity.this,ModificationOneLineMsgActivity.class);
			ed=new IntentEditTextData(user==null?"":user.stageName==null?"":user.stageName, 7, InputType.TYPE_CLASS_TEXT, getString(R.string.text_personal_data_cusname), getString(R.string.text_personal_data_cusname));
			intent.putExtra(ModificationOneLineMsgActivity.ETTD, ed);
			startActivityForResult(intent, CHANGECUSNAME);		
			break;
		case R.id.lin_gender:
			intent=new Intent(UserCenterActivity.this,ModificationGenderActivity.class);
			startActivityForResult(intent, CHANGEGENDER);
			break;
		case R.id.lin_age:
			intent=new Intent(UserCenterActivity.this,ModificationOneLineMsgActivity.class);
			ed=new IntentEditTextData(user==null?"":user.age==null?"":user.age, 2, InputType.TYPE_CLASS_NUMBER, getString(R.string.change_user_age), getString(R.string.change_user_age));
			intent.putExtra(ModificationOneLineMsgActivity.ETTD, ed);
			startActivityForResult(intent, CHANGEAGE);
			break;
		case R.id.lin_height:
			intent=new Intent(UserCenterActivity.this,ModificationOneLineMsgActivity.class);
			ed=new IntentEditTextData(user==null?"":user.height==null?"":user.height, 3, InputType.TYPE_CLASS_NUMBER, getString(R.string.change_user_height), getString(R.string.change_user_height));
			intent.putExtra(ModificationOneLineMsgActivity.ETTD, ed);
			startActivityForResult(intent, CHANGEHEIGHT);
			break;
		case R.id.lin_weight:
			intent=new Intent(UserCenterActivity.this,ModificationOneLineMsgActivity.class);
			ed=new IntentEditTextData(user==null?"":user.weight==null?"":user.weight, 3, InputType.TYPE_CLASS_NUMBER, getString(R.string.change_user_weight), getString(R.string.change_user_weight));
			intent.putExtra(ModificationOneLineMsgActivity.ETTD, ed);
			startActivityForResult(intent, CHANGEWEIGHT);
			break;
		case R.id.lin_tags:
			intent=new Intent(UserCenterActivity.this,EditActorTagActivity.class);
			startActivityForResult(intent,CHANGETAG);
			break;
		case R.id.lin_signature:
			intent=new Intent(UserCenterActivity.this,ModificationMoreMsgActivity.class);
			ed=new IntentEditTextData(user==null?"":user.persign==null?"":user.persign, 350, InputType.TYPE_CLASS_TEXT, getString(R.string.change_user_signature), getString(R.string.change_user_signature));
			intent.putExtra(ModificationMoreMsgActivity.ETTD, ed);
			startActivityForResult(intent, CHANGESIGNATURE);
			break;
		case R.id.lin_profile:
			intent=new Intent(UserCenterActivity.this,ModificationMoreMsgActivity.class);
			ed=new IntentEditTextData(user==null?"":user.resume==null?"":user.resume, 350, InputType.TYPE_CLASS_TEXT, getString(R.string.change_user_profile), getString(R.string.change_user_profile));
			intent.putExtra(ModificationMoreMsgActivity.ETTD, ed);
			startActivityForResult(intent, CHANGEPROFILE);
			break;
		case R.id.lin_history_project:
			intent=new Intent(UserCenterActivity.this,EditActorHistoryActivity.class);
			startActivity(intent);
			break;
		case R.id.lin_photo_list:
			intent=new Intent(UserCenterActivity.this,EditUserPhotoActivity.class);
			startActivity(intent);
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
			RequestManger.GetUserData(UserCenterActivity.this, user.userid, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()) {
						user=UserPaser.GetInstance();
						initData(user);
					}else {
						if (response.getParser().getResponseMsg()!=null) 
							ToastTool.showShortToast(UserCenterActivity.this, response.getParser().getResponseMsg());
					}
				}
			}, errorListener);
			
			
			RequestManger.GetActorPhotos(UserCenterActivity.this, user.customerid, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()) 
						addPhoto(ActorPhotoListPaser.GetInstance(), lin_photos);
				}
			}, errorListener);
			
			RequestManger.GetActorHistory(UserCenterActivity.this, user.customerid, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()) 
						list_history.setAdapter(new ActorPerformAdapter(UserCenterActivity.this, ActorPerformPaser.GetInstance()));
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
			RequestManger.updateBaseUserOne(UserCenterActivity.this, user.userid, field, value, user.version, new Listener<RequestCall>() {

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
							ToastTool.showShortToast(UserCenterActivity.this, "更新数据，请稍后再提交");
							new inintDataTask().execute();
						}else if (msg!=null) {
							ToastTool.showShortToast(UserCenterActivity.this, msg);
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

	private void addPhoto(ArrayList<ActorPhotoList> pList,LinearLayout plins){
		if (pList==null || pList.size()<1){
			horscroll.setVisibility(View.GONE);
			return;
		} 
		if (plins==null){
			horscroll.setVisibility(View.GONE);
			return;
		} 
		horscroll.setVisibility(View.VISIBLE);
		DisplayImageOptions options=ImageLoadUtils.getMainPersionOptions();
		int imageHight=MPlusApplication.Height*320/1920;
		plins.removeAllViews();
		for (int i = 0; i < pList.size(); i++) {
			String url=pList.get(i).getMaterialUrl();
			ImageView iv=new ImageView(UserCenterActivity.this);
			iv.setScaleType(ScaleType.CENTER_CROP);
			Map<String, String> map=UrlStingTool.URLRequest(url);
			String width=map.get("width");
			String height=map.get("height");
			LinearLayout.LayoutParams lp;
			if (TextUtils.isEmpty(width) || TextUtils.isEmpty(height)) {
				lp=new LinearLayout.LayoutParams(imageHight, imageHight);
			}else {
				lp=new LinearLayout.LayoutParams(Integer.valueOf(width)*imageHight/Integer.valueOf(height), imageHight);
			}
			lp.setMargins(0, 0, 15, 0);
			iv.setLayoutParams(lp);
			ImageLoader.getInstance().displayImage(url, iv,options);
			plins.addView(iv);
		}
	}
	
}
