package com.mplus.mplus.paser.projectdetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;

import com.dtd365.library.http.BaseParser;
import com.dtd365.library.tools.SerializableFactory;

public class ProjectConferListPaser extends BaseParser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ArrayList<ProjectConferList> instance=null;
	
	
	@Override
	public void parse(JSONObject obj) {
		super.parse(obj);
		try {
			if (getResultSuccess()) {
				if (getData() != null) {
					SerializableFactory.SaveData("ProjectConferList", getData());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			parseError("");
		}
	}
	/*
	 * 代码中如果需要获取到启动对象返回值时，通过此方法获取
	 */
	public static ArrayList<ProjectConferList> GetInvestsInstance() {
		try {
			String jsonstring= SerializableFactory.GetData("ProjectConferList");
			if (jsonstring!=null) 
				instance=paserInvestsLists(jsonstring);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

	public static ArrayList<ProjectConferList> GetPlansInstance() {
		try {
			String jsonstring= SerializableFactory.GetData("ProjectConferList");
			if (jsonstring!=null) 
				instance=paserPlansLists(jsonstring);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	@SuppressLint("SimpleDateFormat")
	private static ArrayList<ProjectConferList> paserInvestsLists(String data){
		ArrayList<ProjectConferList> lists=new ArrayList<ProjectConferList>();
		if (data!=null) {
			try {
				JSONObject jsonObject=new JSONObject(data);
				JSONArray inArray=jsonObject.getJSONArray("invests");
				for (int i = 0; i < inArray.length(); i++) {
					JSONObject items=inArray.getJSONObject(i);
					String investName=items.isNull("investName")?"":items.getString("investName");
					lists.add(new ProjectConferList(null, null, null, investName, true, false));
					JSONArray iArray=items.getJSONArray("items");
					int imax=iArray.length();
					if (imax==0) {
						String nowtime=new SimpleDateFormat("yy/MM/dd").format(System.currentTimeMillis());
						lists.add(new ProjectConferList("神秘人", "？？？", null, nowtime, false, true));
					}
					for (int j = 0; j < imax; j++) {
						JSONObject iObject=iArray.getJSONObject(j);
						if (j!=imax-1) {
							String name=iObject.isNull("participator")?"":iObject.getString("participator");
							String status=iObject.isNull("conferStatus")?"":iObject.getString("conferStatus");
							String shortcut=iObject.isNull("shortcut")?"":iObject.getString("shortcut");
							String otime=iObject.isNull("invitedate")?"":iObject.getString("invitedate");
							String time;
							if (otime==null || otime.equals("")) {
								time=new SimpleDateFormat("yy/MM/dd").format(System.currentTimeMillis());
							}else {
								time=new SimpleDateFormat("yy/MM/dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(otime));
							}
							lists.add(new ProjectConferList(name, status, shortcut, time, false, false));
						}else {
							String name=iObject.isNull("participator")?"":iObject.getString("participator");
							String status=iObject.isNull("conferStatus")?"":iObject.getString("conferStatus");
							String shortcut=iObject.isNull("shortcut")?"":iObject.getString("shortcut");
							String otime=iObject.isNull("invitedate")?"":iObject.getString("invitedate");
							String time;
							if (otime==null || otime.equals("")) {
								time=new SimpleDateFormat("yy/MM/dd").format(System.currentTimeMillis());
							}else {
								time=new SimpleDateFormat("yy/MM/dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(otime));
							}
							lists.add(new ProjectConferList(name, status, shortcut, time, false, true));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lists;
	}
	
	@SuppressLint("SimpleDateFormat")
	private static ArrayList<ProjectConferList> paserPlansLists(String data){
		ArrayList<ProjectConferList> lists=new ArrayList<ProjectConferList>();
		if (data!=null) {
			try {
				JSONObject jsonObject=new JSONObject(data);
				JSONArray inArray=jsonObject.getJSONArray("plans");
				for (int i = 0; i < inArray.length(); i++) {
					JSONObject items=inArray.getJSONObject(i);
					String investName=items.isNull("planName")?"":items.getString("planName");
					lists.add(new ProjectConferList(null, null, null, investName, true, false));
					JSONArray iArray=items.getJSONArray("items");
					int imax=iArray.length();
					if (imax==0) {
						String nowtime=new SimpleDateFormat("yy/MM/dd").format(System.currentTimeMillis());
						lists.add(new ProjectConferList("神秘人", "？？？", null, nowtime, false, true));
					}
					for (int j = 0; j < imax; j++) {
						JSONObject iObject=iArray.getJSONObject(j);
						if (j!=imax-1) {
							String name=iObject.isNull("participator")?"":iObject.getString("participator");
							String status=iObject.isNull("conferStatus")?"":iObject.getString("conferStatus");
							String shortcut=iObject.isNull("shortcut")?"":iObject.getString("shortcut");
							String otime=iObject.isNull("invitedate")?"":iObject.getString("invitedate");
							String time;
							if (otime==null || otime.equals("")) {
								time=new SimpleDateFormat("yy/MM/dd").format(System.currentTimeMillis());
							}else {
								time=new SimpleDateFormat("yy/MM/dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(otime));
							}
							lists.add(new ProjectConferList(name, status, shortcut, time, false, false));
						}else {
							String name=iObject.isNull("participator")?"":iObject.getString("participator");
							String status=iObject.isNull("conferStatus")?"":iObject.getString("conferStatus");
							String shortcut=iObject.isNull("shortcut")?"":iObject.getString("shortcut");
							String otime=iObject.isNull("invitedate")?"":iObject.getString("invitedate");
							String time;
							if (otime==null || otime.equals("")) {
								time=new SimpleDateFormat("yy/MM/dd").format(System.currentTimeMillis());
							}else {
								time=new SimpleDateFormat("yy/MM/dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(otime));
							}
							lists.add(new ProjectConferList(name, status, shortcut, time, false, true));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return lists;
	}
}
