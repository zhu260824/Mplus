package com.mplus.mplus.utils;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.dialog.TranMyProgressDialog;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.view.MyDialog;
import com.zhy.autolayout.utils.AutoUtils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public  class DialongUtils {
	public static TranMyProgressDialog showLoading(Context context) {
		TranMyProgressDialog loadingDialog = TranMyProgressDialog.createDialog(context);
		return loadingDialog;
	}

	public static void dismissLoading(Dialog loadingDialog) {
		if (loadingDialog != null && loadingDialog.isShowing()) {
			loadingDialog.dismiss();
			loadingDialog = null;
		}
	}

	public static Dialog showDialogTip(Context context,String tip,String sure,String cacel,OnClickListener sureOnClik){
		View view = View.inflate(context, R.layout.dialog_forget,null);
		AutoUtils.autoSize(view);
		final MyDialog dialog = new MyDialog(context,R.style.CustomDialog);
		LinearLayout.LayoutParams dialoglp = new LinearLayout.LayoutParams(MPlusApplication.Width * 4 / 5,LinearLayout.LayoutParams.WRAP_CONTENT);
		dialog.addContentView(view, dialoglp);
		dialog.setCancelable(true);
		dialog.show();
		TextView tv_tip=(TextView) view.findViewById(R.id.tv_tip);
		TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
		TextView tv_cacel = (TextView) view.findViewById(R.id.tv_cancel);
		if (tip!=null) 
			tv_tip.setText(tip);
		if (sure!=null) 
			tv_sure.setText(sure);
		if (cacel!=null) 
			tv_cacel.setText(cacel);
		tv_cacel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (dialog!=null && dialog.isShowing()) 
					dialog.dismiss();
			}
		});
		tv_sure.setOnClickListener(sureOnClik);
		return dialog;
	}
	
	

	public static  void showSelcedType(Context mContext,OnClickListener searchOnClick,OnClickListener forCollectOnClick){
		View view = View.inflate(mContext, R.layout.dialog_selced_actor_fortype, null);
		AutoUtils.autoSize(view);
		final MyDialog dialog = new MyDialog(mContext, R.style.CustomDialog);
		LinearLayout.LayoutParams dialoglp = new LinearLayout.LayoutParams(MPlusApplication.Width * 4 / 5,LinearLayout.LayoutParams.WRAP_CONTENT);
		dialog.addContentView(view, dialoglp);
		dialog.setCancelable(true);
		dialog.show();
		TextView tv_cancel=(TextView) view.findViewById(R.id.tv_cancel);
		TextView tv_search=(TextView) view.findViewById(R.id.tv_search);
		TextView tv_for_collect=(TextView) view.findViewById(R.id.tv_for_collect);
		tv_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (dialog!=null && dialog.isShowing()) 
					dialog.dismiss();
			}
		});
		tv_search.setOnClickListener(searchOnClick);
		tv_for_collect.setOnClickListener(forCollectOnClick);
	}
	
	
	public static void showAskOptionDialog(final Context context,final String userid,final String actoruserid,final String planid){
		View view = View.inflate(context, R.layout.dialog_forget,null);
		AutoUtils.autoSize(view);
		final MyDialog dialog = new MyDialog(context,R.style.CustomDialog);
		LinearLayout.LayoutParams dialoglp = new LinearLayout.LayoutParams(MPlusApplication.Width * 4 / 5,LinearLayout.LayoutParams.WRAP_CONTENT);
		dialog.addContentView(view, dialoglp);
		dialog.setCancelable(true);
		dialog.show();
		TextView tv_tip=(TextView) view.findViewById(R.id.tv_tip);
		TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
		TextView tv_cacel = (TextView) view.findViewById(R.id.tv_cancel);
		tv_tip.setText("是否添加为该职位备选");
		tv_sure.setText("确定");
		tv_cacel.setText("取消");
		tv_cacel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (dialog!=null && dialog.isShowing()) 
					dialog.dismiss();
			}
		});
		tv_sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RequestManger.PostOptionToJob(context, userid, actoruserid, planid, new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						if (dialog!=null && dialog.isShowing()) 
							dialog.dismiss();
						if (response.getParser().getResultSuccess()) {
							ToastTool.showShortToast(context, "添加备选成功！");
						}else {
							if (response.getParser().getResponseMsg()!=null) 
								ToastTool.showShortToast(context,response.getParser().getResponseMsg());
						}
					}
				}, MHttpTools.getErrorListener(context));	
			}
		});
	}
}
