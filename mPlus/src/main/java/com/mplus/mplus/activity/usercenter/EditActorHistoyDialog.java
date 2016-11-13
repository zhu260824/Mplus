package com.mplus.mplus.activity.usercenter;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.common.CropImageActivity;
import com.mplus.mplus.activity.common.UploadPhotoActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorPerform;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.mplus.mplus.utils.ToastTool;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditActorHistoyDialog extends BaseActivity implements OnClickListener {
	private static final int TOPHOTO=1;
	private TextView tv_cancle, tv_sure;
	private EditText  et_title, et_time, et_desc;
	private ActorPerform perform;
	private ImageView iv_ico;
	private String customerid;
	private int IntentType=1;
	private String shortcut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_save_works);
		et_time = (EditText) findViewById(R.id.et_time);
		et_title = (EditText) findViewById(R.id.et_title);
		et_desc = (EditText) findViewById(R.id.et_desc);
		tv_cancle = (TextView) findViewById(R.id.tv_cancle);
		iv_ico = (ImageView) findViewById(R.id.iv_ico);
		tv_sure = (TextView) findViewById(R.id.tv_sure);
		tv_sure.setOnClickListener(this);
		tv_cancle.setOnClickListener(this);
		iv_ico.setOnClickListener(this);
		new initData().execute();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_sure:
			String title =et_title.getText().toString();
			String summary=et_desc.getText().toString();
			String releasedate=et_time.getText().toString();
			if (TextUtils.isEmpty(title) && TextUtils.isEmpty(summary) && TextUtils.isEmpty(releasedate)) {
				ToastTool.showShortToast(EditActorHistoyDialog.this, "请至少填写一项信息");
				return;
			}
			if (IntentType==1) {
				RequestManger.PostAddActorHistory(EditActorHistoyDialog.this, customerid, title, shortcut, null, releasedate, summary, new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						if (response.getParser().getResultSuccess()) {
							setResult(RESULT_OK);
							EditActorHistoyDialog.this.finish();
							ToastTool.showShortToast(EditActorHistoyDialog.this, "添加演艺经历成功！");
						}else {
							if (response.getParser().getResponseMsg()!=null) 
								ToastTool.showShortToast(EditActorHistoyDialog.this, response.getParser().getResponseMsg());
						}
					}
				}, errorListener);
			}else {
				RequestManger.PutChangeActorHistory(EditActorHistoyDialog.this, customerid, perform.getId(), title, shortcut, null, releasedate, summary,perform.getVersion(), new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						if (response.getParser().getResultSuccess()) {
							setResult(RESULT_OK);
							EditActorHistoyDialog.this.finish();
							ToastTool.showShortToast(EditActorHistoyDialog.this, "修改演艺经历成功！");
						}else {
							if (response.getParser().getResponseMsg()!=null) 
								ToastTool.showShortToast(EditActorHistoyDialog.this, response.getParser().getResponseMsg());
						}
					}
				}, errorListener);
			}
			break;
		case R.id.tv_cancle:
			EditActorHistoyDialog.this.finish();
			break;
		case R.id.iv_ico:
			Intent intent=new Intent(EditActorHistoyDialog.this,UploadPhotoActivity.class);
			intent.putExtra(CropImageActivity.RATIOTYPE, CropImageActivity.RATIO_16_10);
			startActivityForResult(intent, TOPHOTO);;
			break;
		default:
			break;
		}
	}

	private class initData extends AsyncTask<Integer, Integer, Integer>{

		@Override
		protected Integer doInBackground(Integer... params) {
			IntentType=getIntent().getIntExtra("IntentType", 1);
			if (IntentType==2) 
				perform=(ActorPerform) getIntent().getExtras().get("perform");
			customerid=UserPaser.GetInstance().customerid;
			return null;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (IntentType==2) {
				et_title.setText(TextUtils.isEmpty(perform.getTitle())?"":perform.getTitle());
				et_time.setText(TextUtils.isEmpty(perform.getTime())?"":perform.getTime());
				et_desc.setText(TextUtils.isEmpty(perform.getRole())?"":perform.getRole());
				ImageLoader.getInstance().displayImage(perform.getMaterialUrl(), iv_ico, ImageLoadUtils.getMainPersionOptions());
				shortcut=perform.getMaterialUrl();
			}
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==TOPHOTO && resultCode==RESULT_OK) {
			shortcut=data.getStringExtra(UploadPhotoActivity.PHOTO_URL);
			ImageLoader.getInstance().displayImage(shortcut, iv_ico, ImageLoadUtils.getMainPersionOptions());
			
		}
	}
}
