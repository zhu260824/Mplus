package com.mplus.mplus.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastTool {

	
	public static void showLongToast(Context context ,String tip){
		Toast.makeText(context, tip, Toast.LENGTH_LONG).show();
	}
	
	public static void showShortToast(Context context ,String tip){
		Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
	}
	
}
