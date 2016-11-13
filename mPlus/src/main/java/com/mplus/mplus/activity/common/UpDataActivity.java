package com.mplus.mplus.activity.common;

import java.io.File;

import com.dtd365.library.tools.SerializableFactory;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.SaveType;
import com.mplus.mplus.paser.common.AppVersion;
import com.mplus.mplus.paser.common.AppVersionPaser;
import com.mplus.mplus.utils.FileUtils;
import com.mplus.mplus.utils.SystemsTool;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class UpDataActivity extends BaseActivity implements OnClickListener{
	private TextView tv_nowversion,tv_newversion,tv_upcontent;
	private LinearLayout lin_ignore;
	private ImageView iv_ignore;
	private Button btn_sure,btn_cancel;
	private boolean isIgnore=false;
	private  AppVersion appVersion;
    private	NotificationManager mNotificationManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFinishOnTouchOutside(false);
		setContentView(R.layout.activity_updata);
		tv_nowversion=(TextView) findViewById(R.id.tv_nowversion);
		tv_newversion=(TextView) findViewById(R.id.tv_newversion);
		tv_upcontent=(TextView) findViewById(R.id.tv_upcontent);
		lin_ignore=(LinearLayout) findViewById(R.id.lin_ignore);
		iv_ignore=(ImageView) findViewById(R.id.iv_ignore);
		btn_sure=(Button) findViewById(R.id.btn_sure);
		btn_cancel=(Button) findViewById(R.id.btn_cancel);
		lin_ignore.setOnClickListener(this);
		btn_sure.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		iv_ignore.setImageResource(R.drawable.checkbox_off);
		new initData().execute();
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lin_ignore:
			if (isIgnore) {
				iv_ignore.setImageResource(R.drawable.checkbox_off);
				isIgnore=false;
			}else {
				iv_ignore.setImageResource(R.drawable.checkbox_on);
				isIgnore=true;
			}
			break;
		case R.id.btn_sure:
			initNotify(appVersion.url);
			UpDataActivity.this.finish();
			break;
		case R.id.btn_cancel:
			if (isIgnore) {
				SerializableFactory.SaveData(SaveType.IgnoreCode, appVersion.versioncode+"");
			}
			UpDataActivity.this.finish();
			break;
		default:
			break;
		}
	}
	
	private class initData extends AsyncTask<Integer, Integer, AppVersion>{

		@Override
		protected AppVersion doInBackground(Integer... params) {
			appVersion=AppVersionPaser.GetInstance();
			return appVersion;
		}
		
		@Override
		protected void onPostExecute(AppVersion result) {
			super.onPostExecute(result);
			tv_nowversion.setText("当前版本：V"+SystemsTool.getVersionName(UpDataActivity.this));
			if (appVersion!=null) {
				tv_newversion.setText(appVersion.versionname==null?"新版本：":"新版本：V"+appVersion.versionname);
				tv_upcontent.setText(appVersion.content==null?"":appVersion.content);
				if (appVersion.forceupdate==1) {
					lin_ignore.setVisibility(View.GONE);
					btn_cancel.setVisibility(View.GONE);
				}
			}
			
		}
	}
	
	@SuppressLint({ "SdCardPath", "InlinedApi" })
	@SuppressWarnings("rawtypes")
	private void initNotify(String url){
		if (!FileUtils.isExternalStorageAvaliable()) {
			Toast.makeText(UpDataActivity.this, "未检测到SD卡！", Toast.LENGTH_SHORT).show();
			return;
		}
		final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(UpDataActivity.this);
		mBuilder.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
		// .setNumber(number)//显示数量
		.setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
		// .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
		.setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
		.setDefaults(Notification.DEFAULT_ALL)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
		// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
		// requires VIBRATE permission
		.setSmallIcon(R.drawable.ic_launcher)
		.setOnlyAlertOnce(true);
		final RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.view_down_notfication);
		mRemoteViews.setImageViewResource(R.id.iv_notifiy_ico, R.drawable.ic_launcher);
		mRemoteViews.setTextViewText(R.id.tv_name, "Mplus");
		mRemoteViews.setTextViewText(R.id.tv_progress, "0%");
		mRemoteViews.setProgressBar(R.id.progress, 100, 0, false);
		mRemoteViews.setViewVisibility(R.id.progress, View.VISIBLE);
		mBuilder.setContent(mRemoteViews)
				.setTicker("版本更新");
		final Notification nitify = mBuilder.build();
		nitify.contentView = mRemoteViews;
		mNotificationManager.notify(102, nitify);
		final String  apkPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/Andriod/data/"+getPackageName()+"/APK/MPlus.apk";
		File file=new File(apkPath);
		if (file.exists()) {
			file.delete();
		}
		HttpUtils http = new HttpUtils();
		@SuppressWarnings("unused")
		HttpHandler handler = http.download(url,apkPath,
		    true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
		    false, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
		    new RequestCallBack<File>() {

		        @Override
		        public void onStart() {
		        }

		        @Override
		        public void onLoading(long total, long current, boolean isUploading) {
		        	int progress=(int) (current*100/total);
		    		mRemoteViews.setTextViewText(R.id.tv_progress, progress+"%");
		    		mRemoteViews.setProgressBar(R.id.progress, 100, progress, false);
		    		nitify.contentView = mRemoteViews;
		    		mNotificationManager.notify(102, nitify);
		        }

		        @Override
		        public void onSuccess(ResponseInfo<File> responseInfo) {
		        	Log.i("canshu", responseInfo.toString());
		        	File file=new File(apkPath);
		        	if (FileUtils.getFileMD5(file).equals(appVersion.md5)) {
						FileUtils.openFile(getApplicationContext(), file);
					}
		        	Intent apkIntent = new Intent();
		    		apkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    		apkIntent.setAction(android.content.Intent.ACTION_VIEW);
		    		//注意：这里的这个APK是放在assets文件夹下，获取路径不能直接读取的，要通过COYP出去在读或者直接读取自己本地的PATH，这边只是做一个跳转APK，实际打不开的
//		    		Uri uri = Uri.parse(apk_path);
		    		Uri uri = Uri.fromFile(file);
		    		apkIntent.setDataAndType(uri, "application/vnd.android.package-archive");
		    		// context.startActivity(intent);
		    		PendingIntent contextIntent = PendingIntent.getActivity(UpDataActivity.this, 0,apkIntent, 0);
		    		mBuilder.setContentIntent(contextIntent);
		    		mNotificationManager.notify(102, nitify);
		        }


		        @Override
		        public void onFailure(HttpException error, String msg) {
		        	mRemoteViews.setTextViewText(R.id.tv_name, "下载失败");
		    		mRemoteViews.setTextViewText(R.id.tv_progress, "0%");
		    		mRemoteViews.setProgressBar(R.id.progress, 100, 0, false);
		    		nitify.contentView = mRemoteViews;
		    		mNotificationManager.notify(102, nitify);
		        }
		});
		
	}
}
