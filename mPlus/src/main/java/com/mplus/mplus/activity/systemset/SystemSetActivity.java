package com.mplus.mplus.activity.systemset;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.common.UpDataActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.manger.SaveType;
import com.mplus.mplus.paser.common.AppVersion;
import com.mplus.mplus.paser.common.AppVersionPaser;
import com.mplus.mplus.utils.DataCleanManager;
import com.mplus.mplus.utils.SystemsTool;
import com.mplus.mplus.view.MyDialog;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SystemSetActivity extends BaseActivity implements OnClickListener {
	private LinearLayout ly_system_feedback, ly_system_version,
			ly_system_about,ly_system_clean;
	private TextView tv_nowversion;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_system_set);
		initTitle(getString(R.string.text_system_settings));
		init();
	}

	private void init() {
		tv_nowversion=(TextView) findViewById(R.id.tv_nowversion);
		ly_system_about = (LinearLayout) findViewById(R.id.ly_system_about);
		ly_system_version = (LinearLayout) findViewById(R.id.ly_system_version);
		ly_system_feedback = (LinearLayout) findViewById(R.id.ly_system_feedback);
		ly_system_clean = (LinearLayout) findViewById(R.id.ly_system_clean);
		ly_system_feedback.setOnClickListener(this);
		ly_system_version.setOnClickListener(this);
		ly_system_about.setOnClickListener(this);
		ly_system_clean.setOnClickListener(this);
		tv_nowversion.setText("当前版本：V"+SystemsTool.getVersionName(SystemSetActivity.this));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ly_system_about:
			startActivity(new Intent(SystemSetActivity.this, SystemSetAboutActivity.class));
			break;
		case R.id.ly_system_version:
			RequestManger.getAppVersion(this, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()) {
						AppVersion appVersion=AppVersionPaser.GetInstance();
						String ignorecode=	SerializableFactory.GetData(SaveType.IgnoreCode);
						if (ignorecode!=null && ignorecode.equals(appVersion.versioncode+"")) {
							showMsg();
							return;
						}
						int nowVersionCode=SystemsTool.getVersionCode(SystemSetActivity.this);
						if (appVersion.versioncode >nowVersionCode) {
							startActivity(new Intent(SystemSetActivity.this,UpDataActivity.class));
						}else {
							Toast.makeText(SystemSetActivity.this, "当前版本已是最新", Toast.LENGTH_SHORT).show();
						}
					}else {
						if (response.getParser().getResponseMsg()!=null)
							Toast.makeText(SystemSetActivity.this, response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
					}			
				}
			}, MHttpTools.getErrorListener(SystemSetActivity.this));
			
			break;
		case R.id.ly_system_feedback:
			startActivity(new Intent(SystemSetActivity.this, SystemSetFeedbackActivity.class));
			break;
		case R.id.ly_system_clean:
			DataCleanManager.cleanApplicationData(SystemSetActivity.this);
			Toast.makeText(SystemSetActivity.this, "清理完毕", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}
	
	private void showMsg() {
		View view = View.inflate(SystemSetActivity.this, R.layout.dialog_forget,null);
		AutoUtils.autoSize(view);
		final MyDialog dialog = new MyDialog(SystemSetActivity.this,R.style.CustomDialog);
		LinearLayout.LayoutParams dialoglp = new LinearLayout.LayoutParams(MPlusApplication.Width * 4 / 5,LinearLayout.LayoutParams.WRAP_CONTENT);
		dialog.addContentView(view, dialoglp);
		dialog.setCancelable(true);
		dialog.show();
		TextView tv_tip =(TextView)view.findViewById(R.id.tv_tip);
		TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
		TextView tv_cacel = (TextView) view.findViewById(R.id.tv_cancel);
		tv_tip.setText("您已忽略最新版本，是否更新？");
		tv_cacel.setText("继续忽略");
		tv_sure.setBackgroundResource(R.drawable.red_roud_dialog_btn);
		tv_sure.setTextColor(Color.WHITE);
		tv_sure.setText("立即检测");
		tv_cacel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (dialog != null && dialog.isShowing())
					dialog.dismiss();
			}
		});
		tv_sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (dialog != null && dialog.isShowing())
					dialog.dismiss();
				SerializableFactory.SaveData("IgnoreCode", null);
				startActivity(new Intent(SystemSetActivity.this,UpDataActivity.class));
			}
		});
	}
	

}
