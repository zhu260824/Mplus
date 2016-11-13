
package com.mplus.mplus.dialog;

import com.dtd365.library.Utils.MobclickAgentUtils;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.pushproject.FirstPushWorksActivity;
import com.mplus.mplus.activity.upresume.FirstStepActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.utils.LoginUtils;
import com.zhy.autolayout.utils.AutoUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.PopupWindow;


public class MainAddPopupWindow extends PopupWindow {
	/**
	 * 上下文对象
	 */
	private BaseActivity mActivity;

	

	public MainAddPopupWindow(BaseActivity mActivity) {
		super();
		this.mActivity = mActivity;
		initView();
	}



	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	private void initView() {
		LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = inflater.inflate(R.layout.popupwindow_main_action_add, null);
		AutoUtils.auto(popupView);
		setContentView(popupView);
		setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		// 设置动画，也可以不设置。不设置则是显示默认的
		setAnimationStyle(R.style.popupwindow_animation);
		this.setFocusable(true);
		this.setBackgroundDrawable(new BitmapDrawable());
		this.setOutsideTouchable(true);
		openWindows();
		this.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss() {
//				backgroundAlpha(1.0f);
			}
		});
		popupView.findViewById(R.id.lin_add_pushwork).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				LoginUtils loginUtils=new LoginUtils() {
					
					@Override
					public void DoOnLongin() {
						Intent intent=new Intent(mActivity,FirstPushWorksActivity.class);
						mActivity.startActivity(intent);
						MobclickAgentUtils.MobclickEventName(mActivity,"0005","发布通告","发布通告");
					}
				};
				loginUtils.ToLogin(mActivity, LoginUtils.TOLOGIN2);
			}
		});
		popupView.findViewById(R.id.lin_add_upuserdata).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				LoginUtils loginUtils=new LoginUtils() {
					
					@Override
					public void DoOnLongin() {
						Intent intent=new Intent(mActivity,FirstStepActivity.class);
						mActivity.startActivity(intent);
						MobclickAgentUtils.MobclickEventName(mActivity,"0006","演员入驻","演员入驻");
					}
				};
				loginUtils.ToLogin(mActivity, LoginUtils.TOLOGIN3);
			}
		});
	}
	
	public void openWindows(){
//		backgroundAlpha(0.5f);
	}
	
	/**
	 * 设置添加屏幕的背景透明度
	 * @param bgAlpha
	 */
	public void backgroundAlpha(float bgAlpha){
		WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().setAttributes(lp);
	}
	
	

}
