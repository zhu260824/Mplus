package com.mplus.mplus.utils;

import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.view.video.UniversalMediaController;
import com.mplus.mplus.view.video.UniversalVideoView;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;

public class VideoWindowsUtils {
	private WindowManager mWindowManager;
	private WindowManager.LayoutParams param;
	private View mLayout;
	private UniversalVideoView videoView;
	private UniversalMediaController mMediaController;
	private BaseActivity mActivity;
	private int mSeekPosition = 0;
	private String video_Url;
	private boolean showBig=true;
	private int top=0;
	
	public VideoWindowsUtils(BaseActivity mActivity, int mSeekPosition,String video_Url,int top) {
		super();
		this.mActivity = mActivity;
		this.mSeekPosition = mSeekPosition;
		this.video_Url = video_Url;
		this.top=top;
		initView();
	}
	
	private void initView(){
		// 获取WindowManager
		mLayout=View.inflate(mActivity, R.layout.windows_play_video, null);
		videoView = (UniversalVideoView)mLayout.findViewById(R.id.videoView);
	    mMediaController = (UniversalMediaController) mLayout.findViewById(R.id.media_controller);
	    videoView.setMediaController(mMediaController);
		mWindowManager = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
		// 设置LayoutParams(全局变量）相关参数
		param =new WindowManager.LayoutParams();

		param.type = LayoutParams.TYPE_PHONE;
//				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT; // 系统提示类型,重要
		param.format = 1;
		param.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 不能抢占聚焦点
		param.flags = param.flags
				| WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
		param.flags = param.flags
				| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排版不受限制
		param.alpha = 1.0f;
		// 显示myFloatView图像
		mWindowManager.addView(mLayout, param);
		videoView.setVideoPath(video_Url);
		if (mSeekPosition > 0) {
			videoView.seekTo(mSeekPosition);
        }
//		videoView.start();
		mMediaController.showComplete();
		videoView.requestFocus();
	}

	
	
	
	public void showMinView() {
		param.gravity = Gravity.RIGHT | Gravity.TOP; // 调整悬浮窗口至左上角
		// 以屏幕左上角为原点，设置x、y初始值
		param.x = 0;
		param.y = top;

		// 设置悬浮窗口长宽数据
		param.width = 480;
		param.height = 360;
		FrameLayout.LayoutParams flp=new FrameLayout.LayoutParams(param.width,param.height);
		if (videoView!=null) 
			videoView.setLayoutParams(flp);
		if (mMediaController!=null) 
			videoView.setLayoutParams(flp);
		showBig=false;
		mWindowManager.updateViewLayout(mLayout, param);
	}
	
	public boolean isShowBig() {
		return showBig;
	}

	
	public void moveView(int[] location){
		if (showBig) {
			param.x=location[0];
			param.y=location[1];
			mWindowManager.updateViewLayout(mLayout, param);
		}
	}
	
	public void removeVideo(){
		if (mWindowManager!=null && mLayout!=null) {
			mWindowManager.removeView(mLayout);
		}
	}
	
	
	public void stopvideo(){
		if (mWindowManager!=null && mLayout!=null) {
			if (videoView!=null && videoView.isPlaying()) {
				mSeekPosition=videoView.getCurrentPosition();
			}
			mWindowManager.removeView(mLayout);
		}
	}

	public void startvideo(){
		if (mWindowManager!=null && mLayout!=null && videoView!=null) {
			videoView.setVideoPath(video_Url);
			if (mSeekPosition > 0) {
				videoView.seekTo(mSeekPosition);
	        }
			videoView.start();
			videoView.requestFocus();
			mWindowManager.addView(mLayout, param);
			showBigView(top);
		}
	}
	
	public void showBigView(int top) {
		param.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角
		// 以屏幕左上角为原点，设置x、y初始值
		param.x = 0;
		param.y = top;

		// 设置悬浮窗口长宽数据
		param.width = MPlusApplication.Width;
		param.height = MPlusApplication.Width*9/16;
		showBig=true;
		FrameLayout.LayoutParams flp=new FrameLayout.LayoutParams(param.width,param.height);
		if (videoView!=null) 
			videoView.setLayoutParams(flp);
		if (mMediaController!=null) 
			videoView.setLayoutParams(flp);
		mWindowManager.updateViewLayout(mLayout, param);
	}
}
