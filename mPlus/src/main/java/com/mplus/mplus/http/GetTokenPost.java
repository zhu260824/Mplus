package com.mplus.mplus.http;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.dtd365.library.Utils.AppUtils;

import android.content.Context;
import android.util.Log;

public class GetTokenPost extends Request<String>{
	private Listener<String> sListener;
	private HashMap<String, String> parms;
	private String heards;
	
	public GetTokenPost(Context context,String UrlAction,HashMap<String, String> parms,String heards,Listener<String> sListener,ErrorListener listener) {
		super(Request.Method.POST, AppUtils.getMetaValue(context,"TOKEN_URL")+UrlAction, listener);
		Log.d("response", "url------>"+AppUtils.getMetaValue(context,"TOKEN_URL")+UrlAction);
		this.sListener = sListener;
		this.parms = parms;
		this.heards = heards;
	}
	
	/**
	 * 重复请求次数时间设置
	 * */
	@Override
	public RetryPolicy getRetryPolicy() {
		RetryPolicy retryPolicy = new DefaultRetryPolicy(1000*5, 0, 1.0f);  
		return retryPolicy; 
	}

	/**
	 * post请求添加参数
	 * */
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return parms;
	}
	
	/**
	 * 在heard里面加入cookies
	 * */
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		String heard=heards;
		if (heard!=null && heard.length()>1) {
			HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("Authorization", heard);
			return headers;
		}else {
			return super.getHeaders();
		}
	}
	
	@Override
	public String getBodyContentType() {
		return "application/x-www-form-urlencoded"+"; charset=" +"ISO-8859-1";
	}

	/**
	 * 对请求的结果进行处理
	 * */
	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		try {
            String jsonString =new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.d("response", jsonString);
            return Response.success(jsonString,HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } 
	}
	
	/**
	 * 设置请求成功回调
	 * */
	@Override
	protected void deliverResponse(String response) {
		sListener.onResponse(response);
	}
}