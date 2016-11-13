package com.mplus.mplus.manger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.dtd365.library.http.BaseParser;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.paser.AlternativeImaginedFigurePaser;
import com.mplus.mplus.paser.MyGetApplyPaser;
import com.mplus.mplus.paser.SimpleProjectPaser;
import com.mplus.mplus.paser.actor.ActorDetailsPaser;
import com.mplus.mplus.paser.actor.ActorListPaser;
import com.mplus.mplus.paser.actor.ActorPerformPaser;
import com.mplus.mplus.paser.actor.ActorPhotoListPaser;
import com.mplus.mplus.paser.actor.ProjectJobListPaser;
import com.mplus.mplus.paser.common.AppVersionPaser;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.paser.message.MessageBeanPaser;
import com.mplus.mplus.paser.message.UnReadMessagePaser;
import com.mplus.mplus.paser.project.JobDetailsPaser;
import com.mplus.mplus.paser.project.JobListPaser;
import com.mplus.mplus.paser.project.ProjectDetailsPaser;
import com.mplus.mplus.paser.project.ProjectListPaser;
import com.mplus.mplus.paser.project.ProjectNameListPaser;
import com.mplus.mplus.paser.project.UserAddJobListPaser;
import com.mplus.mplus.paser.pushproject.ProductJob;

import android.content.Context;
import android.text.TextUtils;

/**
 * @author ZL 接口调用
 */
public class RequestManger {
	/**
	 * 登录
	 */
	public static void Login(Context context, String username, String password, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("mobile", username);
		parms.put("password", password);
		RequestCall call = new RequestCall(UrlManger.LOGIN, parms, new BaseParser());
		MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	/**
	 * QQ登录
	 */
	public static void QQLogin(Context context, String openid, int thirdtype, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("openid", openid);
		parms.put("thirdtype", thirdtype);
		RequestCall call = new RequestCall(UrlManger.QQLOGIN, parms, new BaseParser());
		MHttpTools.PostRequest(context, call, listener, errorListener);

	}

	/**
	 * 获取验证码 opttype=0,注册 opttype=0,忘记密码
	 */
	public static void getCaptchas(Context context, String phone, String optype, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("mobile", phone);
		parms.put("optype", optype);
		RequestCall call = new RequestCall(UrlManger.GETCAPTCHAS, parms, null);
		MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	public static void GetCaptchas(Context context, String phone, String optype, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		RequestCall call = new RequestCall(UrlManger.GETCAPTCHAS + "/" + phone + "/" + optype, null, null);
		MHttpTools.GetRequest(context, call, listener, errorListener);
	}

	/**
	 * 注册
	 */
	public static void Regest(Context context, String username, String password, String captchas,
			Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("mobile", username);
		parms.put("password", password);
		parms.put("captchas", captchas);
		RequestCall call = new RequestCall(UrlManger.REGEST, parms, null);
		MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	/**
	 * 修改密码
	 */

	public static void ChangePsw(Context context, String userid, String oldpwd, String newpwd,
			Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("userid", userid);
		parms.put("oldpwd", oldpwd);
		parms.put("newpwd", newpwd);
		RequestCall call = new RequestCall(UrlManger.CHANGEPSW, parms, null);
		MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	/**
	 * 重置密码
	 */
	public static void ForgetPsw(Context context, String mobile, String captchas, String newPwd,
			Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("mobile", mobile);
		parms.put("captchas", captchas);
		parms.put("newpwd", newPwd);
		RequestCall call = new RequestCall(UrlManger.FORGETPSW, parms, new BaseParser());
		MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	/**
	 * 更新用户基础信息
	 */

	public static void updateBasic(Context context, String userid, String alias, String sex, String province,
			String city, String persign, String address, String education, String age, String horoscope,
			String birthday, String resume, String version, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("userid", userid);
		parms.put("realname", alias);
		parms.put("sex", sex);
		parms.put("province", province);
		parms.put("city", city);
		parms.put("persign", persign);
		parms.put("address", address);
		parms.put("education", education);
		parms.put("age", age);
		parms.put("horoscope", horoscope);
		parms.put("birthday", birthday);
		parms.put("resume", resume);
		parms.put("version", version);
		RequestCall call = new RequestCall(UrlManger.UPDATEUSERBASIC, parms, new BaseParser());
		MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	/**
	 * 更新用户单个基本信息 昵称：alias 性别：sex 省：province 市：city 个性签名 persign 收货地址 address
	 * 教育情况 education 年龄 age 邮箱 email 星座 horoscope String 出生日期 birthday String
	 * “2015-08-03” 个人简介 resume String height 身高   weight 体重
	 * 
	 */

	public static void updateBaseUserOne(Context context, String userid, String field, String value, String version,
			Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("userid", userid);
		parms.put("field", field);
		parms.put("value", value);
		parms.put("version", version);
		RequestCall call = new RequestCall(UrlManger.CHANGUSERONE, parms, new BaseParser());
		MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	/**
	 * 获取用户个人信息
	 */

	public static void GetUserData(Context context, String userid, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		RequestCall call = new RequestCall(UrlManger.GETUSERMSG + "/" + userid, null, new UserPaser());
		MHttpTools.GetRequest(context, call, listener, errorListener);
	}

	/**
	 * 邮箱认证
	 */

	public static void AuthToEmail(Context context, String userid, String email, String version,
			Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("email", email);
		parms.put("version", version);
		RequestCall call = new RequestCall(UrlManger.AUTHEMAIL + "/" + userid, parms, new BaseParser());
		MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	/**
	 * 获取字典数据 字典值类型 {type} 0：省、市 1：角色 2:影片时代背景 3:影片类型 4:影片特征 5:角色模板 6:角色标签
	 * 7:投资期限 8:身高 9:合意条款支付方式
	 */

	public static void GetDictData(Context context, int type, BaseParser parser, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		RequestCall call = new RequestCall(UrlManger.GETDICTDATA + "/" + type, null, parser);
		MHttpTools.GetRequest(context, call, listener, errorListener);
	}

	/**
	 * 版本升级
	 */
	public static void getAppVersion(Context context, Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("platform", 1);
		RequestCall call = new RequestCall(UrlManger.GETAPPVERSION, parm, new AppVersionPaser());
		MHttpTools.GetRequest(context, call, listener, errorListener);
	}

	/**
	 * 用户反馈
	 */
	public static void postFeedBack(Context context, String userid, String title, String content, String devicemode,
			String ipaddress, String vesionname, String mobile, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("userid", userid);
		parm.put("title", title);
		parm.put("content", content);
		parm.put("devicemode", devicemode);
		parm.put("ipaddress", ipaddress);
		parm.put("versionname", vesionname);
		parm.put("mobile", mobile);
		parm.put("platform", 1);
		RequestCall call = new RequestCall(UrlManger.FEEDBACK, parm, new BaseParser());
		MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	/**
	 * 
	 * 
	 * 发布项目第一步 项目名称 name string 影片类型 filmformat string 时代背景 filmtimes string
	 * 影片类别 filetype string 影片特征 filmtag string 许可证编号 license string 封面URL url
	 * string
	 */

	public static void postPushProductFirst(Context context, String userid, String name, String filmformat,
			String filmtimes, String filmtype, String filmtag, String license, String url,
			Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("name", name);
		parms.put("filmformat", filmformat);
		parms.put("filmtimes", filmtimes);
		parms.put("filmtype", filmtype);
		parms.put("filmtag", filmtag);
		parms.put("license", license);
		parms.put("url", url);
		RequestCall call = new RequestCall(UrlManger.MPRODUCT + "/" + userid + "/" + 1, parms, new BaseParser());
		MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	/**
	 * 
	 * 发布项目第二步 项目ID mproductid 故事概括 summary string 项目亮点 points string
	 */

	public static void postPushProductSecond(Context context, String userid, String mproductid, String summary,
			String points, String version, Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("mproductid", mproductid);
		parms.put("summary", summary);
		parms.put("points", points);
		parms.put("version", version);
		RequestCall call = new RequestCall(UrlManger.MPRODUCT + "/" + userid + "/" + 2, parms, new BaseParser());
		MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	/**
	 * 
	 * 发布项目第三步 项目ID mproductid 投资预算 budget string 时间都用字符串格式"2015-09-08" 开机时间
	 * shootingtime string 杀青时间 wraptime string 成片时间 completetime string 上映时间
	 * showtime string 拍摄地点 location string
	 */

	public static void postPushProductThird(Context context, String userid, String mproductid, long budget,
			String shootingtime, String wraptime, String completetime, String showtime, String location, String version,
			Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("mproductid", mproductid);
		parms.put("budget", budget);
		parms.put("shootingtime", shootingtime);
		parms.put("wraptime", wraptime);
		parms.put("completetime", completetime);
		parms.put("showtime", showtime);
		parms.put("location", location);
		parms.put("version", version);
		RequestCall call = new RequestCall(UrlManger.MPRODUCT + "/" + userid + "/" + 3, parms, new BaseParser());
		MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	public static void putChangeProduct(Context context, String mproductid, String name, String filmformat,
			String filmtimes, String filmtype, String filmtag, String license, String url, String summary,
			String points, long budget, String shootingtime, String wraptime, String completetime, String showtime,
			String location, String version, Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("name", name);
		parms.put("filmformat", filmformat);
		parms.put("filmtimes", filmtimes);
		parms.put("filmtype", filmtype);
		parms.put("filmtag", filmtag);
		parms.put("license", license);
		parms.put("url", url);
		parms.put("summary", summary);
		parms.put("points", points);
		parms.put("budget", budget);
		parms.put("shootingtime", shootingtime);
		parms.put("wraptime", wraptime);
		parms.put("completetime", completetime);
		parms.put("showtime", showtime);
		parms.put("location", location);
		parms.put("version", version);
		RequestCall call = new RequestCall(UrlManger.MPRODUCT + "/" + mproductid, parms, new BaseParser());
		MHttpTools.PutRequest(context, call, listener, errorListener);
	}

	/**
	 * 
	 * 获取项目详情 项目id mproductid
	 */

	public static void getBaseProject(Context context, String mproductid, String userid, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		if (userid != null)
			parms.put("userid", userid);
		RequestCall call = new RequestCall(UrlManger.MPRODUCT + "/" + mproductid, parms, new ProjectDetailsPaser());
		MHttpTools.GetRequest(context, call, listener, errorListener);
	}

	/**
	 * 
	 * 项目角色发布 项目ID {mproductid} string 传入参数：[{}] json数组 角色名称 role string 姓名 name
	 * string 动作 action string(0:拟请、1:已签约) 是否需要招募 needRecruit int 0否|1是 是否需要拍摄经验
	 * needRelatedexp int 0否|1是 招募特征 desc string
	 */

	public static void postPushProductJobFirst(Context context, String userid, String mproductid,
			ArrayList<ProductJob> pJobs, Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("data", pJobs);
		parms.put("userid", userid);
		RequestCall call = new RequestCall(UrlManger.MPRODUCT + "/" + mproductid + "/team", parms, new BaseParser());
		MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	/**
	 * category {备选消息10009004，面试消息10009005，系统消息10009002，申请消息10009006，收藏消息10009007}
	 * 获取个人消息列表 type=1 备选消息 type=2 申请消息 type=3 收藏消息type=4 面试消息type=5 系统消息
	 */
	public static void getUserMessage(Context context, int type, String userid, int currpage, int pagenum,
			Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("currpage", currpage);
		parm.put("pagenum", pagenum);
		parm.put("userId", userid);
		switch (type) {
		case 1:
			parm.put("category", "10009004");
			break;
		case 2:
			parm.put("category", "10009006");	
			break;
		case 3:
			parm.put("category", "10009007");
			break;
		case 4:
			parm.put("category", "10009005");
			break;
		case 5:
			parm.put("category", "10009002");
			break;
		default:
			break;
		}
		RequestCall call = new RequestCall(UrlManger.GETUSERMESSAGE, parm, new MessageBeanPaser(type));
		if (call != null)
			MHttpTools.GetRequest(context, call, listener, errorListener);
	}


	/**
	 * 获取未读消息
	 */
	public static void getUnReadMessage(Context context,String userid,Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		RequestCall call = new RequestCall(UrlManger.GETUSERMESSAGE+"/statistics/unread", parm, new UnReadMessagePaser());
		parm.put("userId", userid);
		if (call != null)
			MHttpTools.GetRequest(context, call, listener, errorListener);
	}
	
	/**
	 * 标记个人消息未已读 type=1 项目消息 type=2 邀约消息 type=3 站内消息 messageIds 多个已逗号分隔","
	 */
	public static void postReadUserMessage(Context context, String userid, String messageIds,Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		RequestCall call = new RequestCall(UrlManger.GETUSERMESSAGE+UrlManger.GETUSERMSG+"/"+userid+"/"+messageIds, parm, new BaseParser());
		if (call != null)
			MHttpTools.PutRequest(context, call, listener, errorListener);
	}

	/**
	 * 删除个人消息未已读 type=1 项目消息 type=2 邀约消息 type=3 站内消息 messageIds 多个已逗号分隔","
	 */
	public static void deleteDeleteUserMessage(Context context, String userid, String messageIds,Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		RequestCall call = new RequestCall(UrlManger.GETUSERMESSAGE+UrlManger.GETUSERMSG+"/"+userid+"/"+messageIds, parm, new BaseParser());
		if (call != null)
			MHttpTools.DeleteRequest(context, call, listener, errorListener);
	}

	/**
	 * customerid 认证用户ID
	 */
	public static void GetActorDetails(Context context, String customerid,String userid, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		if (userid!=null) 
			parm.put("userId", userid);
		RequestCall call = new RequestCall(UrlManger.GETCUSTOMER + "/" + customerid, parm, new ActorDetailsPaser());
		if (call != null)
			MHttpTools.GetRequest(context, call, listener, errorListener);
	}

	/**
	 * currpage 所在页面 int pagenum 页面数int key 关键字（演员名） string height 身高 string
	 * 170,180 weight 体重 string 50,60 age 年龄 string 25,45 sex 性别 string feature
	 * 特征 string 多个,号分隔
	 */
	public static void GetActorList(Context context, int currpage, int pagenum, String key, String height,
			String weight, String age, String sex, String feature, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("currpage", currpage);
		parm.put("pagenum", pagenum);
		if (!TextUtils.isEmpty(key))
			parm.put("key", key);
		if (!TextUtils.isEmpty(height))
			parm.put("height", height);
		if (!TextUtils.isEmpty(weight))
			parm.put("weight", weight);
		if (!TextUtils.isEmpty(age))
			parm.put("age", age);
		if (!TextUtils.isEmpty(sex))
			parm.put("sex", sex);
		if (!TextUtils.isEmpty(feature))
			parm.put("feature", feature);
		RequestCall call = new RequestCall(UrlManger.GETCUSTOMERLIST, parm, new ActorListPaser(1));
		if (call != null)
			MHttpTools.GetRequest(context, call, listener, errorListener);
	}

	
	/**
	 * currpage 所在页面 int pagenum 页面数int key 关键字（演员名）

	 * */
	public static void GetActorStartFace(Context context,int currpage,int pagenum,String key,Listener<RequestCall> listener,ErrorListener errorListener){
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("currpage", currpage);
		parm.put("pagenum", pagenum);
		if (!TextUtils.isEmpty(key))
			parm.put("key", key);
		RequestCall call = new RequestCall(UrlManger.GETCUSTOMERSTARFACE, parm, new ActorListPaser(2));
		if (call != null)
			MHttpTools.GetRequest(context, call, listener, errorListener);
	}

	/**
	 * currpage 所在页面 int   pagenum  页面数int  starfaceid 
	 * */
	public static void GetActorStartFaceList(Context context,int currpage,int pagenum,String starfaceid,Listener<RequestCall> listener,ErrorListener errorListener){
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("currpage",currpage );
		parm.put("pagenum", pagenum);
		RequestCall call=new RequestCall(UrlManger.GETCUSTOMERSTARFACELIST+starfaceid, parm, new ActorListPaser(2));
		if (call!=null) 
			MHttpTools.GetRequest(context, call, listener, errorListener);
	}
	

	/**
	 * 获取艺人图片
	 * customerid 认证用户ID
	 */
	public static void GetActorPhotos(Context context, String customerid, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		RequestCall call = new RequestCall(UrlManger.GETACTORPHOTO + "/" + customerid, parm, new ActorPhotoListPaser());
		if (call != null)
			MHttpTools.GetRequest(context, call, listener, errorListener);
	}

	/**
	 * 艺人图片上传
	 * customerid 认证用户ID
	 * 图片URL shortcut string
	 */
	public static void PostAddActorPhotos(Context context, String customerid, String shortcut,Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("shortcut",shortcut);
		RequestCall call = new RequestCall(UrlManger.ARTWORK + "/" + customerid+"/album", parm, new BaseParser());
		if (call != null)
			MHttpTools.PostRequest(context, call, listener, errorListener);
	}
	
	/**
	 * 艺人图片删除
	 * customerid 认证用户ID
	 * 图片ID {artworkid} 
	 */
	public static void DeleteActorPhotos(Context context, String customerid,String artworkid,Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		RequestCall call = new RequestCall(UrlManger.ARTWORK + "/" + customerid+"/album/"+artworkid, parm, new ActorPhotoListPaser());
		if (call != null)
			MHttpTools.DeleteRequest(context, call, listener, errorListener);
	}

	/**
	 * 获取演艺经历
	 * customerid 认证用户ID
	 */
	public static void GetActorHistory(Context context, String customerid, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		RequestCall call = new RequestCall(UrlManger.ARTWORK+UrlManger.ARTHISTORY + "/" + customerid, parm, new ActorPerformPaser());
		if (call != null)
			MHttpTools.GetRequest(context, call, listener, errorListener);
	}

	
	/**
	 * 添加演艺经历
	 * customerid 认证用户ID
	 * 作品标题     title string  作品封面url  shortcut string  作品简介     summary string  上映日期     releasedate string (2001)
	 */

	public static void PostAddActorHistory(Context context, String customerid,String title,String shortcut,String summary,String releasedate,String role,Listener<RequestCall> listener,ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("title",title);
		parm.put("shortcut",shortcut);
		parm.put("summary",summary);
		parm.put("releasedate",releasedate);
		parm.put("role",role);
		RequestCall call = new RequestCall(UrlManger.ARTWORK + "/" + customerid+UrlManger.ARTHISTORY, parm, new BaseParser());
		if (call != null)

			MHttpTools.PostRequest(context, call, listener, errorListener);
	}
	

	/**
	 * 修改演艺经历
	 * customerid 认证用户ID 作品ID {artworkid} long
	 * 作品标题     title string  作品封面url  shortcut string  作品简介     summary string  上映日期     releasedate string (2001)
	 */

	public static void PutChangeActorHistory(Context context, String customerid,String artworkid,String title,String shortcut,String summary,String releasedate,String role,String version,Listener<RequestCall> listener,ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("title",title);
		parm.put("shortcut",shortcut);
		parm.put("summary",summary);
		parm.put("releasedate",releasedate);
		parm.put("role",role);
		parm.put("version", version);
		RequestCall call = new RequestCall(UrlManger.ARTWORK + "/" + customerid+UrlManger.ARTHISTORY+ "/" +artworkid, parm, new BaseParser());
		if (call != null)
			MHttpTools.PutRequest(context, call, listener, errorListener);
	}
	
	/**
	 * 删除演艺经历
	 * customerid 认证用户ID  作品ID {artworkid} long
	 */
	public static void DeleteActorHistory(Context context, String customerid, String artworkid,Listener<RequestCall> listener,ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		RequestCall call = new RequestCall(UrlManger.ARTWORK+"/" + customerid+UrlManger.ARTHISTORY+"/"+artworkid, parm, new ActorPerformPaser());
		if (call != null)
			MHttpTools.DeleteRequest(context, call, listener, errorListener);
	}

	
	
	/**
	 * 添加我的影人收藏 用户ID userId 被收藏用户ID toUserId
	 */
	public static void PostAddFavorite(Context context, String userId, String toUserId, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("userId", userId);
		parm.put("toUserId", toUserId);
		RequestCall call = new RequestCall(UrlManger.GETUSERMSG + UrlManger.FAVORITE, parm, new BaseParser());
		if (call != null)
			MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	/**
	 * 删除我的收藏 收藏记录 用户ID userId 被收藏用户ID toUserId
	 */
	public static void DeleteFavorite(Context context, String userId, String toUserId, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("userId", userId);
		RequestCall call=new RequestCall(UrlManger.GETUSERMSG+"/"+userId+UrlManger.FAVORITE+"/"+toUserId, parm, new BaseParser());
		if (call!=null) 
			MHttpTools.DeleteRequest(context, call, listener, errorListener);
	}

	/**
	 * 我收藏的影人列表 用户ID userid 当前页 currpage 每页显示数 pagenum 搜索关键字 key string 性别 sex
	 * string 年龄 age string (10,20) 身高 height string (170,180) 体重 weight string
	 * (50,60) 气质 feature string
	 */
	public static void GetMyFavorite(Context context, String userid, int currpage, int pagenum, String key, String sex,
			String age, String height, String weight, String feature, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("currpage", currpage);
		parm.put("pagenum", pagenum);
		if (!TextUtils.isEmpty(key))
			parm.put("key", key);
		if (!TextUtils.isEmpty(height))
			parm.put("height", height);
		if (!TextUtils.isEmpty(weight))
			parm.put("weight", weight);
		if (!TextUtils.isEmpty(age))
			parm.put("age", age);
		if (!TextUtils.isEmpty(sex))
			parm.put("sex", sex);
		if (!TextUtils.isEmpty(feature))
			parm.put("feature", feature);
		RequestCall call = new RequestCall(UrlManger.GETUSERMSG + "/" + userid + UrlManger.FAVORITE, parm,
				new ActorListPaser(3));
		if (call != null)
			MHttpTools.GetRequest(context, call, listener, errorListener);
	}

	/**
	 * 同意申请并添加备选 用户ID userId long申请ID applyId long
	 */
	public static void PostOptionAggrement(Context context, String userId, String applyId,
			Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("userId", userId);
		parm.put("applyId", applyId);
		RequestCall call = new RequestCall(UrlManger.POSTCONFEROPTION + "/aggrement", parm, new BaseParser());
		if (call != null)
			MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	/**
	 * 添加备选到某个职位 登录用户ID userId long 备选用户ID toUserId long 职位ID planId long
	 */
	public static void PostOptionToJob(Context context, String userId, String toUserId, String planId,
			Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("userId", userId);
		parm.put("toUserId", toUserId);
		parm.put("planId", planId);
		RequestCall call = new RequestCall(UrlManger.POSTCONFEROPTION, parm, new BaseParser());
		if (call != null)
			MHttpTools.PostRequest(context, call, listener, errorListener);
	}

	/**
	 * 查询用户发起的通过审核的项目和职位 登录用户ID userId long
	 */
	public static void GetProjectJob(Context context, String userId, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		RequestCall call = new RequestCall(UrlManger.MPRODUCT + UrlManger.GETUSERMSG + "/" + userId + "/planStatistics",
				parm, new ProjectJobListPaser());
		if (call != null)
			MHttpTools.GetRequest(context, call, listener, errorListener);
	}

	/**
	 * 首页项目列表 当前页 currpage 每页显示数 pagenum 搜索关键字 key string
	 */
	public static void GetProjectList(Context context, int currpage, int pagenum, Listener<RequestCall> listener,
			ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("currpage", currpage);
		parm.put("pagenum", pagenum);
		RequestCall call = new RequestCall(UrlManger.GETMPRODUCTLIST, parm, new ProjectListPaser());
		if (call != null)
			MHttpTools.GetRequest(context, call, listener, errorListener);
	}

	/**
	 * 我发布的职位
	 */
	public static void getMyProject(Context context, String userid, int currpage, int pagenum,
			Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("currpage", currpage);
		parm.put("pagenum", pagenum);
		RequestCall call = new RequestCall(UrlManger.MPRODUCT + "/user/" + userid, parm, new SimpleProjectPaser(2));
		if (call != null)
			MHttpTools.GetRequest(context, call, listener, errorListener);
	}

	
	/**
	 *申请角色接口
	 *职位ID planid long 申请人ID userid long
	 * */
	public static void PostApplyJob(Context context,String userId ,String planId,Listener<RequestCall> listener,ErrorListener errorListener){
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("userid",userId );
		parm.put("planid", planId);
		RequestCall call=new RequestCall(UrlManger.POSTAPPLYJOB, parm, new BaseParser());
		if (call!=null) 
			MHttpTools.PostRequest(context, call, listener, errorListener);
	}
	
	/**
	 *项目下角色列表
	 *当前页 currpage int   每页显示数 pagenum int
	 * */
	public static void GetProjectJobList(Context context,String mproductid,int currpage ,int pagenum,Listener<RequestCall> listener,ErrorListener errorListener){
		HashMap<String, Object> parm = new HashMap<String, Object>();
		parm.put("currpage",currpage );
		parm.put("pagenum", pagenum);
		RequestCall call=new RequestCall(UrlManger.JOB+"/"+mproductid+"/list", parm, new JobListPaser());
		if (call!=null) 
			MHttpTools.GetRequest(context, call, listener, errorListener);
	}
	

	/**
	 * 获取职位详情
	 * 职位ID planid (路径参数)   登录用户ID userid
	 * */
	public static void getJobDetails(Context context, String planid,String userid, Listener<RequestCall> listener,ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		if (userid!=null) 
			parms.put("userid", userid);
		RequestCall call = new RequestCall(UrlManger.JOB + "/"+ planid, parms, new JobDetailsPaser());
		MHttpTools.GetRequest(context, call, listener, errorListener);
	}
	
	/**
	 * 我参与的职位（包括项目信息）
	 * userid 用户ID   currpage 当前页     pagenum 每页显示数
	 * */
	public static void GetUserAddJob(Context context, String userid,int currpage ,int pagenum, Listener<RequestCall> listener,ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("currpage",currpage );
		parms.put("pagenum", pagenum);
		RequestCall call = new RequestCall(UrlManger.GETUSERADDJOB + "/"+ userid, parms, new UserAddJobListPaser(1));
		MHttpTools.GetRequest(context, call, listener, errorListener);
	}

	/**
	 * 用户申请的所有职位(包括项目信息)
	 * userid 用户ID   currpage 当前页     pagenum 每页显示数
	 * */
	public static void GetUserAskJob(Context context, String userid,int currpage ,int pagenum, Listener<RequestCall> listener,ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("currpage",currpage );
		parms.put("pagenum", pagenum);
		RequestCall call = new RequestCall(UrlManger.GETUSERASKJOB + "/"+ userid, parms, new UserAddJobListPaser(2));
		MHttpTools.GetRequest(context, call, listener, errorListener);
	}
	/**
	 * 项目职位信息
	 * mproductid 项目ID   currpage 当前页     pagenum 每页显示数
	 * */
	public static void GetProjectName(Context context, String mproductid,int currpage ,int pagenum, Listener<RequestCall> listener,ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("currpage",currpage );
		parms.put("pagenum", pagenum);

		RequestCall call = new RequestCall(UrlManger.JOB+UrlManger.MPRODUCT + "/"+ mproductid, parms, new ProjectNameListPaser(2));
		MHttpTools.GetRequest(context, call, listener, errorListener);
	}
	
	/**
	 * 删除职位
	 * planids 职位id userid 用户id
	 */
	public static void deletePosition(Context context, String planid,String userid, Listener<RequestCall> listener,ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
//		if (userid!=null) 
//			parms.put("userid", userid);
		RequestCall call = new RequestCall(UrlManger.JOB + "/"+ planid+UrlManger.GETUSERMSG+"/"+userid, parms, new JobDetailsPaser());
		MHttpTools.DeleteRequest(context, call, listener, errorListener);
	}
	/**
	 * 备选影人
	 * mproductid 项目ID   currpage 当前页     pagenum 每页显示数
	 * */
	public static void GetAlternativeInagined(Context context, String mproductid,int currpage ,int pagenum, Listener<RequestCall> listener,ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("currpage",currpage );
		parms.put("pagenum", pagenum);
		RequestCall call = new RequestCall(UrlManger.GETCUSTOMER+"/option/plan"+ "/"+ mproductid, parms, new AlternativeImaginedFigurePaser(2));
		MHttpTools.GetRequest(context, call, listener, errorListener);
	}
	
	/**
	 * 
	 * 项目职位发布 项目ID mproductid long 角色 role string 特征 feature string 多选,分隔 档期开始时间
	 * startschedule string "2015-09-01" 档期结束时间 endschedule  string "2015-09-02" 类比
	 * analogy string 描述 desc string身高 height string  片酬  paycheck string
	 * */
	
	public static void postPushProductJobSecond(Context context,String userid,String mproductid, String role, String feature, String startschedule,
			String endschedule, String desc,String needrelatedexp,String height,String paycheck,String nickname,String weight,String age,String userids,Listener<RequestCall> listener, ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("userid", userid);//项目ID
		parms.put("role", role);//角色
		parms.put("feature", feature);//角色标签
		parms.put("starttime", startschedule);//开机时间
		parms.put("endtime", endschedule);//结束时间
//		parms.put("deadline", deadline);//角色截止时间
//		parms.put("analogy", analogy);//类比
		parms.put("roledesc", desc);//描述
		parms.put("needrelatedexp", needrelatedexp);//是有需要经验0否 1是
		parms.put("height", height);//身高
		parms.put("paycheck", paycheck);//片酬
		parms.put("nickname", nickname);//剧中昵称
		parms.put("weight", weight);//体重
		parms.put("age", age);//年纪
		if (!TextUtils.isEmpty(userid)) 
			parms.put("userids", userids);//邀约对象id
		RequestCall call = new RequestCall(UrlManger.JOB + "/mproduct/"+ mproductid , parms, new BaseParser());
		MHttpTools.PostRequest(context, call, listener, errorListener);
	}
	
	/**
	 * 查询某个职位下的申请记录
	 * planid 职位id userid 用户id
	 * */
	public static void GetMyGetApply(Context context, String planid,String userid,int currpage ,int pagenum, Listener<RequestCall> listener,ErrorListener errorListener) {
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("userid", userid);
		parms.put("currpage",currpage );
		parms.put("pagenum", pagenum);
		RequestCall call = new RequestCall(UrlManger.GETMYAPPLY+"/"+planid, parms, new MyGetApplyPaser(2));
		MHttpTools.GetRequest(context, call, listener, errorListener);
	}
	
	/**
	 *通知影人
	 *职位ID planid long 申请人ID userid long
	 * */
	public static void PostInform(Context context,String userId,ArrayList<Map<String,Object>> data,Listener<RequestCall> listener,ErrorListener errorListener){
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("userId", userId);
		parms.put("data",data);
		RequestCall call=new RequestCall(UrlManger.GETUSERMESSAGE, parms, new BaseParser());
		if (call!=null) 
			MHttpTools.PostRequest(context, call, listener, errorListener);
	}
	
	/**
	 *添加我的影人收藏
	 *userId用户Id
	 *toUserId 被收藏的用户Id
	 * */
	public static void addCOllect(Context context,String userId,String toUserId,Listener<RequestCall> listener,ErrorListener errorListener){
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("userId", userId);
		parms.put("toUserId",toUserId);
		RequestCall call=new RequestCall(UrlManger.GETUSERMSG+"/favorite", parms, new BaseParser());
		if (call!=null) 
			MHttpTools.PostRequest(context, call, listener, errorListener);
	}
	/**
	 *同意影人申请
	 *userId用户Id
	 *toUserId 被收藏的用户Id
	 * */
	public static void agreeApplyFor(Context context,String userId,String applyId,Listener<RequestCall> listener,ErrorListener errorListener){
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("userId", userId);
		parms.put("applyId",applyId);
		RequestCall call=new RequestCall(UrlManger.POSTCONFEROPTION+"/aggrement", parms, new BaseParser());
		if (call!=null) 
			MHttpTools.PostRequest(context, call, listener, errorListener);
	}
	/**
	 *同意影人申请
	 *userId用户Id
	 *toUserId 被收藏的用户Id
	 * */
	public static void ignoreApplyFor(Context context,String userId,String applyid,Listener<RequestCall> listener,ErrorListener errorListener){
		HashMap<String, Object> parms = new HashMap<String, Object>();
		parms.put("userId", userId);

		RequestCall call=new RequestCall(UrlManger.DELETEAPPLICATION+"/"+applyid+UrlManger.GETUSERMSG+"/"+userId,parms, new BaseParser());
		if (call!=null) 
			MHttpTools.DeleteRequest(context, call, listener, errorListener);
	}
}