package com.mplus.mplus.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.activity.common.WelcomeActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.utils.ToastTool;

import android.app.Activity;
import android.content.Context;

public class MHttpTools {
	
	/**
	 * post发起请求
	 * */
	public static void PostRequest(Context context, RequestCall call,Listener<RequestCall> listener, ErrorListener errorListener) {
		call.setMethod(Method.POST);
		TokenUtils.RequestNet(context, call, listener, getErrorListener(context, call, listener, errorListener));
	}

	/**
	 * Delete发起请求
	 * */
	public static void DeleteRequest(Context context, RequestCall call,Listener<RequestCall> listener, ErrorListener errorListener) {
		call.setMethod(Method.DELETE);
		TokenUtils.RequestNet(context, call, listener, getErrorListener(context, call, listener, errorListener));
	}

	/**
	 * get发起请求
	 * */

	public static void GetRequest(Context context, RequestCall call,
			Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parms = call.getParms();
		if (parms != null && parms.size() > 0)
			call.setUrlAction(call.getUrlAction() + "?" + mapToUrl(parms));
		call.setMethod(Method.GET);
		TokenUtils.RequestNet(context, call, listener, getErrorListener(context, call, listener, errorListener));
	}

	/**
	 * put请求
	 * */
	public static void PutRequest(Context context, RequestCall call,
			Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parms = call.getParms();
		if (parms != null && parms.size() > 0)
			call.setUrlAction(call.getUrlAction() + "?" + mapToUrl(parms));
		call.setMethod(Method.PUT);
		TokenUtils.RequestNet(context, call, listener, getErrorListener(context, call, listener, errorListener));
	}
	
	public static ErrorListener getErrorListener(final Context mContext) {
		ErrorListener errorListener = new ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				try {
					BaseActivity activity = (BaseActivity)mContext;
				    if(activity  instanceof BaseActivity){
				    	activity.dismissLoadingDialog();
				    }
				} catch (Exception e) {
				    e.printStackTrace();
				    //说明是ApplicationContext
				}
				ToastTool.showShortToast(mContext, "网络错误2");
			}
		};
		return errorListener;
	}
	
	
	
	private static ErrorListener getErrorListener(final Context context,final RequestCall call,final Listener<RequestCall> listener,final ErrorListener eListener){
		final ErrorListener errorListener=new ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				if (error!=null && error.networkResponse!=null) {
					if (error.networkResponse.statusCode==401) {
							TokenUtils.RequestNet(context, call, listener, eListener);
					}
				}else {
					try {
					    Activity activity = (Activity)context;
					    if(activity  instanceof WelcomeActivity){
					    	WelcomeActivity.intentToMain(activity);
					    }
					} catch (Exception e) {
					    e.printStackTrace();
					    //说明是ApplicationContext
					}
					ToastTool.showShortToast(context, "网络错误");
				}
			}
		};
		return errorListener;
	}

	private static String mapToUrl(HashMap<String, Object> params) {
		String paramsEncoding = "UTF-8";
		StringBuilder encodedParams = new StringBuilder();
		try {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				if (entry.getValue() != null) {
					encodedParams.append(URLEncoder.encode(entry.getKey(),paramsEncoding));
					encodedParams.append('=');
					encodedParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), paramsEncoding));
					encodedParams.append('&');
				}
			}
			encodedParams.deleteCharAt(encodedParams.lastIndexOf("&"));
			return encodedParams.toString();
		} catch (UnsupportedEncodingException uee) {
			throw new RuntimeException("Encoding not supported: "+ paramsEncoding, uee);
		}
	}

}
