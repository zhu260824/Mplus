package com.mplus.mplus.activity.pushproject;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.dtd365.library.Utils.AppUtils;
import com.dtd365.library.Utils.ValueUtils;
import com.dtd365.library.http.RequestCall;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.usercenter.ModificationMoreMsgActivity;
import com.mplus.mplus.activity.usercenter.ModificationOneLineMsgActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.dialog.ChangeProjectTimeBackgoupDialog;
import com.mplus.mplus.dialog.ChangeProjectTimeBackgoupDialog.OnConstellationListener;
import com.mplus.mplus.http.TokenUtils;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.manger.UrlManger;
import com.mplus.mplus.paser.common.DictStringPaser;
import com.mplus.mplus.paser.common.IntentEditTextData;
import com.mplus.mplus.paser.login.UserPaser;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我要发布
 *
 */
public class FirstPushWorksActivity extends BaseActivity implements OnClickListener{
	private static final int GETPIC=1;//选择图片
	private static final int MOVIETYPE=2;//影片类别
	private static final int MOVIETRAIT=3;//影片特征
	private static final int SHOOTINGPERMIT=4;//拍摄许可证
	private static final int STORYABOUT=5;//拍摄许可证
	private static final int STORYHIGLIGHT=6;//拍摄许可证
	private static final int TONEXT=7;//下一步
	private Button btn_tonext;
	private Intent intent;
	private EditText et_name;
	private ImageView iv_pic;
	private TextView tv_type,tv_time_backgroup,tv_move_type,tv_move_trait,tv_shooting_permit,tv_story_about,tv_story_highlight;
	private LinearLayout lin_type,lin_time_backgroup,lin_move_type,lin_move_trait,lin_shooting_permit,lin_story_about,lin_story_highlight;
	private String userid,mproductid,picurl,version="1";
	private IntentEditTextData ed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_push_works);
		initTitle(getString(R.string.push_project));
		btn_tonext=(Button) findViewById(R.id.btn_tonext);
		et_name=(EditText) findViewById(R.id.et_name);
		iv_pic=(ImageView) findViewById(R.id.iv_pic);
		tv_type=(TextView) findViewById(R.id.tv_type);
		tv_time_backgroup=(TextView) findViewById(R.id.tv_time_backgroup);
		tv_move_type=(TextView) findViewById(R.id.tv_move_type);
		tv_move_trait=(TextView) findViewById(R.id.tv_move_trait);
		tv_shooting_permit=(TextView) findViewById(R.id.tv_shooting_permit);
		tv_story_about=(TextView) findViewById(R.id.tv_story_about);
		tv_story_highlight=(TextView) findViewById(R.id.tv_story_highlight);
		lin_type=(LinearLayout) findViewById(R.id.lin_type);
		lin_time_backgroup=(LinearLayout) findViewById(R.id.lin_time_backgroup);
		lin_move_type=(LinearLayout) findViewById(R.id.lin_move_type);	
		lin_move_trait=(LinearLayout) findViewById(R.id.lin_move_trait);
		lin_shooting_permit=(LinearLayout) findViewById(R.id.lin_shooting_permit);	
		lin_story_about=(LinearLayout) findViewById(R.id.lin_story_about);
		lin_story_highlight=(LinearLayout) findViewById(R.id.lin_story_highlight);
		lin_type.setOnClickListener(this);
		lin_time_backgroup.setOnClickListener(this);
		lin_move_type.setOnClickListener(this);
		lin_move_trait.setOnClickListener(this);
		lin_shooting_permit.setOnClickListener(this);
		lin_story_about.setOnClickListener(this);
		lin_story_highlight.setOnClickListener(this);
		iv_pic.setOnClickListener(this);
		btn_tonext.setOnClickListener(this);
		userid=UserPaser.GetInstance().userid;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_tonext:
			if (mproductid==null || mproductid.length()<1) {
				requstProductFirst();
			}else {
				if (version.equals("1")) {
					requstProductSecond();
				}else {
					changeProduct();
				}
			}
			break;
		case R.id.iv_pic:
			gallery(GETPIC);
			break;
		case R.id.lin_type:
			ArrayList<String> arrayList3=DictStringPaser.GetInstance(17);
			if (arrayList3==null || arrayList3.size()<1) {
				RequestManger.GetDictData(FirstPushWorksActivity.this, 17, new DictStringPaser(17), new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						if (response.getParser().getResultSuccess()) {
							ArrayList<String> arrayList=DictStringPaser.GetInstance(17);
							ChangeProjectTimeBackgoupDialog cDialog=new ChangeProjectTimeBackgoupDialog(FirstPushWorksActivity.this, arrayList);
							Window dialogWindow = cDialog.getWindow();
					        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
					        dialogWindow.setGravity(Gravity.BOTTOM);
					        lp.width = LayoutParams.MATCH_PARENT; // 宽度
					        lp.height = LayoutParams.WRAP_CONTENT; // 高度
					        dialogWindow.setAttributes(lp);
					        dialogWindow.setWindowAnimations(R.style.dialog_bottom_style);
							cDialog.show();
							cDialog.setConstellationListener(new OnConstellationListener() {
								
								@Override
								public void onClick(String constellation) {
									tv_type.setText(constellation);
								}
							});
						}
					}
				}, errorListener);
			}else {
				ChangeProjectTimeBackgoupDialog cDialog=new ChangeProjectTimeBackgoupDialog(FirstPushWorksActivity.this, arrayList3);
				Window dialogWindow = cDialog.getWindow();
		        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		        dialogWindow.setGravity(Gravity.BOTTOM);
		        lp.width = LayoutParams.MATCH_PARENT; // 宽度
		        lp.height = LayoutParams.WRAP_CONTENT; // 高度
		        dialogWindow.setAttributes(lp);
		        dialogWindow.setWindowAnimations(R.style.dialog_bottom_style);
				cDialog.show();
				cDialog.setConstellationListener(new OnConstellationListener() {
					
					@Override
					public void onClick(String constellation) {
						tv_type.setText(constellation);
					}
				});
				RequestManger.GetDictData(FirstPushWorksActivity.this, 17, new DictStringPaser(17), new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
					}
				}, errorListener);
			}
			break;
		case R.id.lin_time_backgroup:
			ArrayList<String> arrayList=DictStringPaser.GetInstance(2);
			if (arrayList==null || arrayList.size()<1) {
				RequestManger.GetDictData(FirstPushWorksActivity.this, 2, new DictStringPaser(2), new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						if (response.getParser().getResultSuccess()) {
							ArrayList<String> arrayList=DictStringPaser.GetInstance(2);
							ChangeProjectTimeBackgoupDialog cDialog=new ChangeProjectTimeBackgoupDialog(FirstPushWorksActivity.this, arrayList);
							Window dialogWindow = cDialog.getWindow();
					        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
					        dialogWindow.setGravity(Gravity.BOTTOM);
					        lp.width = LayoutParams.MATCH_PARENT; // 宽度
					        lp.height = LayoutParams.WRAP_CONTENT; // 高度
					        dialogWindow.setAttributes(lp);
					        dialogWindow.setWindowAnimations(R.style.dialog_bottom_style);
							cDialog.show();
							cDialog.setConstellationListener(new OnConstellationListener() {
								
								@Override
								public void onClick(String constellation) {
									tv_time_backgroup.setText(constellation);
								}
							});
						}
					}
				}, errorListener);
			}else {
				ChangeProjectTimeBackgoupDialog cDialog=new ChangeProjectTimeBackgoupDialog(FirstPushWorksActivity.this, arrayList);
				Window dialogWindow = cDialog.getWindow();
		        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		        dialogWindow.setGravity(Gravity.BOTTOM);
		        lp.width = LayoutParams.MATCH_PARENT; // 宽度
		        lp.height = LayoutParams.WRAP_CONTENT; // 高度
		        dialogWindow.setAttributes(lp);
		        dialogWindow.setWindowAnimations(R.style.dialog_bottom_style);
				cDialog.show();
				cDialog.setConstellationListener(new OnConstellationListener() {
					
					@Override
					public void onClick(String constellation) {
						tv_time_backgroup.setText(constellation);
					}
				});
				RequestManger.GetDictData(FirstPushWorksActivity.this, 2, new DictStringPaser(2), new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
					}
				}, errorListener);
			}
			break;
		case R.id.lin_move_type:
			intent=new Intent(FirstPushWorksActivity.this,TraitSelcedActivity.class);
			intent.putExtra("intenttype", 1);
			startActivityForResult(intent, MOVIETYPE);
			break;
		case R.id.lin_move_trait:
			intent=new Intent(FirstPushWorksActivity.this,TraitSelcedActivity.class);
			intent.putExtra("intenttype", 2);
			startActivityForResult(intent, MOVIETRAIT);
			break;
		case R.id.lin_shooting_permit:
			intent=new Intent(FirstPushWorksActivity.this,ModificationMoreMsgActivity.class);
			String license=tv_shooting_permit.getText().toString();
			ed=new IntentEditTextData(license==null?"":license, 100, InputType.TYPE_CLASS_TEXT, "如乐视独家、优酷独家", getString(R.string.project_shooting_permit));
			intent.putExtra(ModificationOneLineMsgActivity.ETTD, ed);
			startActivityForResult(intent, SHOOTINGPERMIT);
			break;
		case R.id.lin_story_about:
			intent=new Intent(FirstPushWorksActivity.this,ModificationMoreMsgActivity.class);
			String data=tv_story_about.getText().toString();
			ed=new IntentEditTextData(data==null?"":data, 350, InputType.TYPE_CLASS_TEXT, getString(R.string.project_story_about), getString(R.string.project_story_about));
			intent.putExtra(ModificationMoreMsgActivity.ETTD, ed);
			startActivityForResult(intent, STORYABOUT);
			break;
		case R.id.lin_story_highlight:
			intent=new Intent(FirstPushWorksActivity.this,ModificationMoreMsgActivity.class);
			String data2=tv_story_highlight.getText().toString();
			ed=new IntentEditTextData(data2==null?"":data2, 1000, InputType.TYPE_CLASS_TEXT, "请在此处输入项目筹备地址及主要演职人员的微信、电话、邮箱等联系方式", getString(R.string.project_story_highlight));
			intent.putExtra(ModificationMoreMsgActivity.ETTD, ed);
			startActivityForResult(intent, STORYHIGLIGHT);
			break;
		default:
			break;
		}
	}
	
	private void requstProductSecond(){
		if (mproductid!=null) {
			String summary=tv_story_about.getText().toString();
			String points=tv_story_highlight.getText().toString();
			/*if (ValueUtils.isStrEmpty(summary)) {
				Toast.makeText(FirstPushWorksActivity.this, "您未输入故事概述", Toast.LENGTH_SHORT).show();
				return;
			}else if (ValueUtils.isStrEmpty(points)) {
				Toast.makeText(FirstPushWorksActivity.this, "您未输入故事亮点", Toast.LENGTH_SHORT).show();
				return;
			}*/
			showLoadingDialog();
			RequestManger.postPushProductSecond(FirstPushWorksActivity.this, userid, mproductid, summary, points, version,new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					dismissLoadingDialog();
					if (response.getParser().getResultSuccess()) {
						try {
							JSONObject jsonObject = new JSONObject(response.getParser().getData());
							version=jsonObject.isNull("version")?"":jsonObject.getString("version");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						intent=new Intent(FirstPushWorksActivity.this,SecondPushWorksActivity.class);
						intent.putExtra("mproductid", mproductid);
						intent.putExtra("userid", userid);
						intent.putExtra("version", version);
						startActivityForResult(intent, TONEXT);
					}else {
						String msg=response.getParser().getResponseMsg();
						if (msg!=null && msg.equals(getString(R.string.data_isold))) {
							Toast.makeText(FirstPushWorksActivity.this, "更新数据，请稍后再提交", Toast.LENGTH_SHORT).show();
							errorVesion();
						}else if (msg!=null) {
							Toast.makeText(FirstPushWorksActivity.this, msg, Toast.LENGTH_SHORT).show();
						}
					}
				}
			}, errorListener);
		}
	}
	
	private void requstProductFirst(){
		String name=et_name.getText().toString();
		String filmformat=tv_type.getText().toString();
		String filmtimes=tv_time_backgroup.getText().toString();
		String filetype=tv_move_type.getText().toString();
		String filmtag=tv_move_trait.getText().toString();
		String license=tv_shooting_permit.getText().toString();
		if (ValueUtils.isStrEmpty(name)) {
			Toast.makeText(FirstPushWorksActivity.this, "您未输入项目名称", Toast.LENGTH_SHORT).show();
			return;
		}else if (ValueUtils.isStrEmpty(filmformat)) {
			Toast.makeText(FirstPushWorksActivity.this, "您未选择影片格式", Toast.LENGTH_SHORT).show();
			return;
		}else if (ValueUtils.isStrEmpty(filmtimes)) {
			Toast.makeText(FirstPushWorksActivity.this, "您未选择时代背景", Toast.LENGTH_SHORT).show();
			return;
		}else if (ValueUtils.isStrEmpty(filetype)) {
			Toast.makeText(FirstPushWorksActivity.this, "您未选择影片类型", Toast.LENGTH_SHORT).show();
			return;
		}
	/*	else if (ValueUtils.isStrEmpty(filmtag)) {
			Toast.makeText(FirstPushWorksActivity.this, "您未选择影片特征", Toast.LENGTH_SHORT).show();
			return;
		}else if (ValueUtils.isStrEmpty(license)) {
			Toast.makeText(FirstPushWorksActivity.this, "您未输入拍摄许可证", Toast.LENGTH_SHORT).show();
			return;
		}else if (ValueUtils.isStrEmpty(picurl)) {
			Toast.makeText(FirstPushWorksActivity.this, "您未选择图片或者图片未上传成功", Toast.LENGTH_SHORT).show();
			return;
		}*/
		showLoadingDialog();
		RequestManger.postPushProductFirst(FirstPushWorksActivity.this, userid, name, filmformat, filmtimes, filetype, filmtag, license, picurl, new Listener<RequestCall>() {
			public void onResponse(RequestCall response) {
				dismissLoadingDialog();
				if (response.getParser().getResultSuccess()) {
					try {
						JSONObject jsonObject=new JSONObject(response.getParser().getData());
						mproductid=jsonObject.isNull("mproductid")?"":jsonObject.getString("mproductid");
						version=jsonObject.isNull("version")?"":jsonObject.getString("version");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if (mproductid!=null) {
						requstProductSecond();
					}
				}else {
					if (response.getParser().getResponseMsg()!=null) 
						Toast.makeText(FirstPushWorksActivity.this, response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
				}
			}
		}, errorListener);
	}
	
	private void changeProduct(){
		String name=et_name.getText().toString();
		String filmformat=tv_type.getText().toString();
		String filmtimes=tv_time_backgroup.getText().toString();
		String filetype=tv_move_type.getText().toString();
		String filmtag=tv_move_trait.getText().toString();
		String license=tv_shooting_permit.getText().toString();
		String summary=tv_story_about.getText().toString();
		String points=tv_story_highlight.getText().toString();
		showLoadingDialog();
		RequestManger.putChangeProduct(FirstPushWorksActivity.this, mproductid, name, filmformat, filmtimes, filetype, filmtag, license, picurl, summary, points, 
				0, null, null, null, null, null, version, new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						dismissLoadingDialog();
						if (response.getParser().getResultSuccess()) {
							try {
								JSONObject jsonObject=new JSONObject(response.getParser().getData());
								version=jsonObject.isNull("version")?"":jsonObject.getString("version");
							} catch (JSONException e) {
								e.printStackTrace();
							}
							intent=new Intent(FirstPushWorksActivity.this,SecondPushWorksActivity.class);
							intent.putExtra("mproductid", mproductid);
							intent.putExtra("userid", userid);
							intent.putExtra("version", version);
							startActivityForResult(intent, TONEXT);
						}else {
							String msg=response.getParser().getResponseMsg();
							if (msg!=null && msg.equals(getString(R.string.data_isold))) {
								Toast.makeText(FirstPushWorksActivity.this, "更新数据，请稍后再提交", Toast.LENGTH_SHORT).show();
								errorVesion();
							}else if (msg!=null) {
								Toast.makeText(FirstPushWorksActivity.this, msg, Toast.LENGTH_SHORT).show();
							}
						}
					}
				}, errorListener);
	}
	
	private void errorVesion(){
		if (mproductid!=null && mproductid.length()>1) {
			showLoadingDialog();
			RequestManger.getBaseProject(FirstPushWorksActivity.this, mproductid, userid, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					dismissLoadingDialog();
					try {
						JSONObject jsonObject=new JSONObject(response.getParser().getData());
						version=jsonObject.isNull("version")?"":jsonObject.getString("version");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, errorListener);
		}
	}
	
	/*
     * 从相册获取
     */
    private void gallery(int getPhotoNumber) {
        // 激活系统图库，选择一张图片
    	   Intent intent = new Intent(Intent.ACTION_PICK);  
           intent.setType("image/*");//相片类型  
           startActivityForResult(intent, getPhotoNumber);
    }
    
    @Override
   	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
   		super.onActivityResult(requestCode, resultCode, data);
   		if (requestCode==GETPIC && resultCode==RESULT_OK) {
   			if (data!=null) {
   				Uri uri = data.getData();
   				ImageLoader.getInstance().displayImage(uri.toString(), iv_pic);
   				uploadPic(uri);
			}
		}else if (requestCode==MOVIETYPE && resultCode==RESULT_OK) {
			String name=data.getStringExtra("data");
			if (name!=null) {
				tv_move_type.setText(name);
			}
		}else if (requestCode==MOVIETRAIT && resultCode==RESULT_OK) {
			String name=data.getStringExtra("data");
			if (name!=null) {
				tv_move_trait.setText(name);
			}
		}else if (requestCode==SHOOTINGPERMIT && resultCode==RESULT_OK) {
			String name=data.getStringExtra(ModificationOneLineMsgActivity.ETTD);
			if (name!=null) {
				tv_shooting_permit.setText(name);
			}
		}else if (requestCode==STORYABOUT && resultCode==RESULT_OK) {
			String name=data.getStringExtra(ModificationMoreMsgActivity.ETTD);
			if (name!=null) {
				tv_story_about.setText(name);
			}
		}else if (requestCode==STORYHIGLIGHT && resultCode==RESULT_OK) {
			String name=data.getStringExtra(ModificationMoreMsgActivity.ETTD);
			if (name!=null) {
				tv_story_highlight.setText(name);
			}
		}else if (requestCode==TONEXT && resultCode==RESULT_OK) {
			FirstPushWorksActivity.this.finish();
		}
    }
    
    private void uploadPic(Uri uri){
    	if (uri==null) 
			return;
    	RequestParams params = new RequestParams();
    	params.addBodyParameter("userid", userid);
		params.addBodyParameter("suffix", ".png");
		params.addBodyParameter("file", new File(uriToFilePath(uri)));
		try {
			params.addHeader("Authorization", TokenUtils.getAuthorizationHeader(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST,AppUtils.getMetaValue(FirstPushWorksActivity.this,"APP_URL")+UrlManger.PUSHPRODUCTPHOTO,params,new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				picurl=null;
				Toast.makeText(FirstPushWorksActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				try {
					picurl=(new JSONObject(arg0.result)).getJSONObject("data").getString("url");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (picurl!=null) {
					Toast.makeText(FirstPushWorksActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(FirstPushWorksActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
				}
			}
		});
    }
    
    
	@SuppressWarnings("deprecation")
    private String uriToFilePath(Uri uri){
    	String[] proj = { MediaStore.Images.Media.DATA };
		Cursor actualimagecursor = managedQuery(uri,proj,null,null,null);
    	int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    	actualimagecursor.moveToFirst();
		return actualimagecursor.getString(actual_image_column_index);
    }
}
