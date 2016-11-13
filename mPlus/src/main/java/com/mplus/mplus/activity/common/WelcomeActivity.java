package com.mplus.mplus.activity.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.dtd365.library.Utils.MobclickAgentUtils;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.main.MainActivity;
import com.mplus.mplus.base.BaseActivity;

public class WelcomeActivity extends BaseActivity{
	private final static int TOUPDATA=1;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ImageView iv=new ImageView(WelcomeActivity.this);
		iv.setScaleType(ScaleType.CENTER_CROP);
		iv.setImageResource(R.drawable.welcome);
		setContentView(iv, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		MobclickAgentUtils.upDataToInternet(this);
//		RequestManger.getAppVersion(WelcomeActivity.this, new Listener<RequestCall>() {
//
//			@Override
//			public void onResponse(RequestCall response) {
//				if (response.getParser().getResultSuccess()) {
//					AppVersion appVersion=AppVersionPaser.GetInstance();
//					String ignorecode=	SerializableFactory.GetData(SaveType.IgnoreCode);
//					if (ignorecode!=null && ignorecode.equals(appVersion.versioncode+"")) {
//						sleepToIntent();
//					}else {
//						int nowVersionCode=SystemsTool.getVersionCode(WelcomeActivity.this);
//						if (appVersion.versioncode >nowVersionCode) {
//							startActivityForResult(new Intent(WelcomeActivity.this,UpDataActivity.class),TOUPDATA);
//						}else {
//							sleepToIntent();
//						}
//					}
//				}else  {
//					sleepToIntent();
//				}
//			}
//		}, new ErrorListener() {
//
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				intentToMain(WelcomeActivity.this);
//			}
//		});
		sleepToIntent();
	}
	
	private void sleepToIntent(){
		new Thread(){
			public void run() {
				try {
					sleep(1000);
					runOnUiThread(new Runnable() {
						public void run() {
							intentToMain(WelcomeActivity.this);
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	
	public static  void intentToMain(Activity activity){
		activity.startActivity(new Intent(activity,MainActivity.class));
		activity.finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
   		super.onActivityResult(requestCode, resultCode, data);
   		if (requestCode==TOUPDATA) 
			intentToMain(WelcomeActivity.this);
	}

	
	
	   
}
