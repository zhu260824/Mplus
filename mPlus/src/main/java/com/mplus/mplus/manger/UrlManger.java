package com.mplus.mplus.manger;

/**
 * @author ZL Url地址
 */
public class UrlManger {
	public final static String LOGIN = "/user/login";// 登陆
	public final static String QQLOGIN = "/user/third/login";// QQ登陆
	public final static String GETCAPTCHAS = "/user/getMobileCaptchas";// 获取验证码
	public final static String REGEST = "/user/regUser";// 注册
	public final static String CHANGEPSW = "/user/updatePwd";// 修改密码
	public final static String FORGETPSW = "/user/forgetPwd";// 重置密码
	public final static String UPDATEUSERBASIC = "/user/updateBasic";// 更新用户基础信息
	public final static String UPLOADFILE = "/file/upload";// 上传文件
	public final static String CHANGUSERONE = "/user/updateField/basic";// 单个修改用户信息
	public final static String GETUSERMSG = "/user";// 获取用户信息
	public final static String AUTHEMAIL = "/user/email";// 邮箱认证
	public final static String GETDICTDATA = "/dict";// 字典值查询
	public final static String FEEDBACK = "/feedback";// 用户反馈接口
	public final static String GETAPPVERSION = "/app/version";// app版本升级接口
	public final static String POSTACCESSTOKEN = "/oauthserver-web/oauth/token";// 获取accesstoken
	public final static String MPRODUCT="/mproduct";//项目
	public final static String GETMPRODUCTLIST="/mproduct/list";//项目
	public final static String PUSHPRODUCTPHOTO="/file/photo/upload";//项目封面图片上传接口
	public final static String GETUSERMESSAGE="/message";//获取用户信息
	public final static String GETCUSTOMER="/customer";//影人详情
	public final static String GETCUSTOMERLIST="/customer/list";//影人列表
	public final static String GETCUSTOMERSTARFACE="/customer/starface";//影人列表
	public final static String GETACTORPHOTO="/artwork/album";//个人照片
	public final static String ARTWORK="/artwork";//演艺经历
	public final static String ARTHISTORY="/arthistory";//演艺经历
	public final static String FAVORITE="/favorite";//收藏的影人
	public final static String POSTCONFEROPTION="/confer/option";//添加备选
	public final static String POSTAPPLYJOB="/job/apply";//申请角色接口
	public final static String JOB="/job";//角色
	public final static String GETCUSTOMERSTARFACELIST="/customer/actor/starface/";
	public final static String GETUSERADDJOB="/job/participation/user";//用户参与的所有职位
	public final static String GETUSERASKJOB="/job/application/user";//用户申请的所有职位
	public final static String GETMYAPPLY="/application/plan";//查询某个职位下的申请记录
	public final static String POSTPHOTO="/file/photo/upload"; //上传图像公用接口
	public final static String GETRELEASEROLE="/job/mproduct";//发布角色
	public final static String DELETEAPPLICATION="/application";//忽略收到的申请
}
