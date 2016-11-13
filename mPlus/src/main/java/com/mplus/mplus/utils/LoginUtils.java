package com.mplus.mplus.utils;

import com.mplus.mplus.activity.login.LoginActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;

import android.content.Intent;

public abstract class LoginUtils {

	public static final int TOLOGIN = 999;
	public static final int TOLOGIN2 = 998;
	public static final int TOLOGIN3 = 997;
	public static final int TOLOGIN4 = 996;
	public static final int TOLOGIN5 = 995;
	
	public void ToLogin(BaseActivity mActivity) {
		if (MPlusApplication.isLogin) {
			DoOnLongin();
		} else {
			mActivity.startActivityForResult(new Intent(mActivity, LoginActivity.class), TOLOGIN);
		}
	}

	public void ToLogin(BaseActivity mActivity, int requestCode) {
		if (MPlusApplication.isLogin) {
			DoOnLongin();
		} else {
			mActivity.startActivityForResult(new Intent(mActivity, LoginActivity.class), requestCode);
		}
	}

	public abstract void DoOnLongin();

}
