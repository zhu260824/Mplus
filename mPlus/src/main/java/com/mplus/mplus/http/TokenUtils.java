package com.mplus.mplus.http;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.dtd365.library.http.RequestCall;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.manger.UrlManger;
import com.mplus.mplus.utils.Base64;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

public class TokenUtils {
	
	public static void RequestNet(final Context context,final RequestCall call,final Listener<RequestCall> listener, final ErrorListener errorListener){
		if (MPlusApplication.isLogin) {
			String logintoken=MPlusApplication.getLoginToken();
			if (logintoken==null || isExpire(logintoken)) {
				MPlusApplication.isLogin=false;
				MPlusApplication.setLoginToken(null);
				Toast.makeText(context, "当前登录已过期，请重新登录", Toast.LENGTH_SHORT).show();
				String phone = SerializableFactory.sharedPreferencesReadString("NameAndPassword", "phone");
				String password = SerializableFactory.sharedPreferencesReadString("NameAndPassword", "password");
				RequestManger.Login(context, phone, password, new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						RequestNetWork(context, call, listener, errorListener);
					}
				}, errorListener);
			}else {
				RequestNetWork(context, call, listener, errorListener);
			}
		}else {
			String existingToken = MPlusApplication.getAccessToken(); 
			if(existingToken == null || isExpire(existingToken)){
				try {
					getAcessToken(context, new Listener<String>() {

						@Override
						public void onResponse(String response) {
							MPlusApplication.setAccessToken(saveSystemTime(response));
							RequestNetWork(context, call, listener, errorListener);
						}
					}, errorListener);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else {
				RequestNetWork(context, call, listener, errorListener);
			}
		}
	}
	
	private static void RequestNetWork(Context context,RequestCall call,Listener<RequestCall> listener, ErrorListener errorListener){
		try {
			call.setHearder(getAuthorizationHeader(call.getMethod()));
			RequestQueue requestQueue = Volley.newRequestQueue(context);
			MplusRequest mRequest=new MplusRequest(context, call, listener, errorListener);
			requestQueue.add(mRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressLint("NewApi")
	public static String getAuthorizationHeader (int method) throws Exception{
		//生成服务调用的header
		MessageDigest DIGEST = MessageDigest.getInstance("SHA-1");
		String token="";
		if (MPlusApplication.isLogin) {
			token=MPlusApplication.getLoginToken();
		}else {
			token=MPlusApplication.getAccessToken();
		}
		if (token==null || token.length()<10) {
			return "";
		}
		JSONObject jsObject = JSONObject.parseObject(token);
		String clientId = "MPlusClient";
		String tokenId = jsObject.getString("token_id");
		String accessToken = jsObject.getString("access_token");
		String nonce = UUID.randomUUID().toString();
		long timestamp = System.currentTimeMillis();
		String toSign = clientId + "," + tokenId + "," + nonce + "," + timestamp + "," + getMode(method) + "," + accessToken;
		String signature =  new String(Base64.getEncoder().encode(bytes2HexString(DIGEST.digest(toSign.getBytes("UTF-8"))).getBytes()), "UTF-8");
		String header = "Signing client_id=" + clientId + ", token_id=" + tokenId + ", timestamp=" + timestamp + ", nonce=" + nonce + ", signature=" + signature;
		return header;
	}
	
	private static boolean isExpire(String token){
		JSONObject jsObject = JSONObject.parseObject(token);
		Long expires_in = jsObject.getLong("expires_in");
		if(expires_in<System.currentTimeMillis()){
			return true;
		}
		return false;
	}
	
	public static String saveSystemTime(String token){
		JSONObject jsObject = JSONObject.parseObject(token);
		Long expires_in = jsObject.getLong("expires_in");
		long systemTime=System.currentTimeMillis()+(expires_in * 1000);
		jsObject.remove("expires_in");
		jsObject.put("expires_in", systemTime);
		return jsObject.toJSONString();
	}
	
	
	
	private static String getBasicHeader(Map<String, String> params) throws UnsupportedEncodingException {
		String head =  String.format("Basic %s",new String(Base64.getEncoder().encode(String.format("%s:%s", params.get("client_id"),params.get("client_secret")).getBytes("UTF-8")), "UTF-8"));
		return head;
	}
	
	private static  void getAcessToken(Context context,Listener<String> sListener,ErrorListener eListener) throws UnsupportedEncodingException {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("client_id", "MPlusClient");
		params.put("client_secret", "y471l12D2y55U5558rd2");
		params.put("grant_type", "client_credentials");
		params.put("scope", "TRUST");
		String head = getBasicHeader(params);
		params.remove("client_secret");
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		GetTokenPost tokenPost=new GetTokenPost(context, UrlManger.POSTACCESSTOKEN, params, head, sListener, eListener);
		requestQueue.add(tokenPost);
	}
	
	/**
	 *  int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
	 * 
	 * */
	private static String getMode(int method){
		String mode="GET";
		switch (method) {
		case 0:
			mode="GET";
			break;
		case 1:
			mode="POST";
			break;	
		case 2:
			mode="PUT";	
			break;
		case 3:
			mode="DELETE";		
			break;
		case 4:
			mode="HEAD";	
			break;
		case 5:
			mode="OPTIONS";		
			break;
		case 6:
			mode="TRACE";	
			break;
		case 7:
			mode="PATCH";		
			break;
		default:
			break;
		}
		return mode;
	}
	
	private static String bytes2HexString(byte[] b) {  
	  	  String ret = "";  
	  	  for (int i = 0; i < b.length; i++) {  
	  	   String hex = Integer.toHexString(b[ i ] & 0xFF);  
	  	   if (hex.length() == 1) {  
	  	    hex = '0' + hex;  
	  	   }  
	  	   ret += hex;  
	  	  }  
	  	  return ret;  
	  }  
}
