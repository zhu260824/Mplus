package com.mplus.mplus.http;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.FakeX509TrustManager;
import com.android.volley.toolbox.HttpHeaderParser;
import com.dtd365.library.Utils.JsonUtils;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.http.RequestCall;

import android.content.Context;
import android.util.Log;
/**
 * @author ZL
 * 自定义网络请求
 * */
public class MplusRequest extends Request<RequestCall>{
	private RequestCall call;
	private Listener<RequestCall> sListener;
	
	public MplusRequest(Context context, RequestCall call,Listener<RequestCall> sListener,ErrorListener listener) {
		super(call.getMethod(), call.getUrl(context), listener);
		Log.d("response", "url------>"+call.getUrl(context));
		if (call.getParms()!=null) 
			Log.d("response", JsonUtils.toJson(call.getParms()));
		FakeX509TrustManager.allowAllSSL();
		this.call = call;
		this.sListener = sListener;
	}
	
	/**
	 * 重复请求次数时间设置
	 * */
	@Override
	public RetryPolicy getRetryPolicy() {
		RetryPolicy retryPolicy = new DefaultRetryPolicy(1000*5, 0, 1.0f);  
		return retryPolicy; 
	}

	@Override
	public byte[] getBody() throws AuthFailureError {
		Map<String, Object> params = call.getParms();
        if (params != null && params.size() > 0) {
            try {
				return JsonUtils.toJson(params).getBytes(getParamsEncoding());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
        }
        return null;
	}
	
	/**
	 * 在heard里面加入cookies
	 * */
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		String heard=call.getHearder();
		if (heard!=null && heard.length()>1) {
			Map<String, String> headers=new HashMap<String, String>();
			headers.put("Authorization", heard);
			return headers;
		}else {
			return super.getHeaders();
		}
	}
	
	@Override
	public String getBodyContentType() {
		return "application/json; charset=" + getParamsEncoding();
	}

	/**
	 * 对请求的结果进行处理
	 * */
	@Override
	protected Response<RequestCall> parseNetworkResponse(NetworkResponse response) {
		try {
            String jsonString =new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Log.d("response", jsonString);
            if (call.getParser()==null) {
            	call.setParser(new BaseParser());
			}
            call.getParser().parse(new JSONObject(jsonString));
            return Response.success(call,HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
	}
	
	/**
	 * 设置请求成功回调
	 * */
	@Override
	protected void deliverResponse(RequestCall response) {
		sListener.onResponse(response);
	}
	
	
}
