package com.mplus.mplus.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dtd365.library.Utils.RegexUtils;
import com.dtd365.library.Utils.ValueUtils;
import com.dtd365.library.Utils.ViewUtils;
import com.mplus.mplus.R;
import com.mplus.mplus.base.MPlusApplication;

import android.annotation.SuppressLint;
/**
 * @auther david
 * @create 2014-07-28
 * @description: this tool is used to check the user register check steps.
 */
/**
 * @author Administrator
 *
 */
public class CheckUtils {

	/**
	 * @auther zhaochaoyue
	 * @create 2014-8-1
	 * @param 用户名和密码
	 * @return boolean
	 * @description:
	 */
	public static boolean loginCheckPre(String userName, String password) {
		boolean result = true;
		String errorToast = "";
		if (ValueUtils.isStrEmpty(userName) || ValueUtils.isStrEmpty(password)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_check_username_null_toast);
			result = false;
		} else if (!RegexUtils.isMobile(userName)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_check_username_format_username_toast);
			result = false;
		} else if (6 > password.length()) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_check_username_format_password_toast);
			result = false;
		}

		if (ValueUtils.isStrNotEmpty(errorToast)) {
			ViewUtils.showToast(MPlusApplication.getInstance(), errorToast,false);
		}

		return result;
	}
	

	/**
	 * @auther zhaochaoyue
	 * @create 2014-8-1
	 * @param mobile
	 * @return boolean
	 * @description:判断手机号
	 */
	public static boolean checkMobilePre(String mobile) {
		boolean result = true;
		String errorToast = "";
		if (ValueUtils.isStrEmpty(mobile)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_check_mobile_null_toast);
			result = false;
		} else if (!RegexUtils.isMobile(mobile)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_check_username_format_username_toast);
			result = false;
		}

		if (ValueUtils.isStrNotEmpty(errorToast)) {
			ViewUtils.showToast(MPlusApplication.getInstance(), errorToast,false);
		}

		return result;
	}

	/**
	 * @auther zhaochaoyue
	 * @create 2014-8-1
	 * @param mobile
	 * @return boolean
	 * @description:判断昵称
	 */
	public static boolean checkNickName(String nickname) {
		boolean result = true;
		String errorToast = "";
		if (ValueUtils.isStrEmpty(nickname)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_nullnickname_loading);
			result = false;
		} else if (nickname.length() > 10) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_isnickname_loading);
			result = false;
		}

		if (ValueUtils.isStrNotEmpty(errorToast)) {
			ViewUtils.showToast(MPlusApplication.getInstance(), errorToast,false);
		}

		return result;
	}

	/**
	 * @auther zhaochaoyue
	 * @create 2014-8-1
	 * @param mobile
	 * @return boolean
	 * @description:判断验证码
	 */
	public static boolean checkVerificationCode(String code) {
		boolean result = true;
		String errorToast = "";
		if (ValueUtils.isStrEmpty(code)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_nullverificationcode_loading);
			result = false;
		} else if (!RegexUtils.isVerifyCode(code, 6)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_isverificationcode_loading);
			result = false;
		}

		if (ValueUtils.isStrNotEmpty(errorToast)) {
			ViewUtils.showToast(MPlusApplication.getInstance(), errorToast,false);
		}

		return result;
	}

	/**
	 * @auther zhaochaoyue
	 * @create 2014-8-1
	 * @param mobile
	 * @return boolean
	 * @description:注册判断
	 */
	public static boolean checkRegister(String mobile, String code,String password, Boolean clause) {
		boolean result = true;
		String errorToast = "";
		if (ValueUtils.isStrEmpty(mobile)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_check_mobile_null_toast);
			result = false;
		} else if (!RegexUtils.isMobile(mobile)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_check_username_format_username_toast);
			result = false;
		} else if (ValueUtils.isStrEmpty(code)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_nullverificationcode_loading);
			result = false;
		} else if(!checkVerificationCode(code)){
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_isverificationcode_loading);
			result = false;
		}else if (ValueUtils.isStrEmpty(password)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_password_null_error);
			result = false;
		}else if (password.length()<6) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_check_username_format_password_toast);
			result = false;
		}else if (!isValidPwd(password)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_passwrold_error);
			result = false;
		}else if(!clause){
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_bank_card_bound_agree_notnull_error);
			result = false;
		}
		if (ValueUtils.isStrNotEmpty(errorToast)) {
			ViewUtils.showToast(MPlusApplication.getInstance(), errorToast,false);
		}

		return result;
	}
	/**
	 * @auther zhaochaoyue
	 * @create 2014-8-1
	 * @param mobile
	 * @return boolean
	 * @description:重置密码
	 */
	public static boolean checkResetPasswrod(String mobile, String code,String password,String password2) {
		boolean result = true;
		String errorToast = "";
		if (ValueUtils.isStrEmpty(mobile)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_check_mobile_null_toast);
			result = false;
		} else if (!RegexUtils.isMobile(mobile)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_check_username_format_username_toast);
			result = false;
		} else if (ValueUtils.isStrEmpty(code)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_nullverificationcode_loading);
			result = false;
		} else if(!checkVerificationCode(code)){
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_isverificationcode_loading);
			result = false;
		}else if (ValueUtils.isStrEmpty(password)||ValueUtils.isStrEmpty(password2)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_password_null_error);
			result = false;
		}else if (6 > password.length()) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_check_username_format_password_toast);
			result = false;
		}else if (6 > password2.length()) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_check_username_format_password_toast);
			result = false;
		}else if (!isValidPwd(password) || !isValidPwd(password2)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_passwrold_error);
			result = false;
		}else if(!password.equals(password2)){
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_update_check_password_no_same_toast);
			result = false;
		}

		if (ValueUtils.isStrNotEmpty(errorToast)) {
			ViewUtils.showToast(MPlusApplication.getInstance(), errorToast,false);
		}

		return result;
	}
	
	/**
	 * @auther zhulin
	 * @create 2015-9-1
	 * @return boolean
	 * @description:修改密码
	 */
	public static boolean checkChangePasswrod(String oldpsw,String newpsw,String surepsw) {
		boolean result = true;
		String errorToast = "";
		if (ValueUtils.isStrEmpty(oldpsw)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_nullverificationcode_loading);
			result = false;
		} else if(6 > oldpsw.length()){
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_isverificationcode_loading);
			result = false;
		}else if (ValueUtils.isStrEmpty(newpsw)||ValueUtils.isStrEmpty(surepsw)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_password_null_error);
			result = false;
		}else if (6 > newpsw.length()) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_check_username_format_password_toast);
			result = false;
		}else if (6 > surepsw.length()) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_check_username_format_password_toast);
			result = false;
		}else if (!isValidPwd(newpsw) || !isValidPwd(surepsw)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_passwrold_error);
			result = false;
		}else if(!newpsw.equals(surepsw)){
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_update_check_password_no_same_toast);
			result = false;
		}

		if (ValueUtils.isStrNotEmpty(errorToast)) {
			ViewUtils.showToast(MPlusApplication.getInstance(), errorToast,false);
		}

		return result;
	}
	/**
	 * @auther david
	 * @create 2014-8-1
	 * @param verifyCode
	 * @return boolean
	 * @description:判断验证码
	 */
	public static boolean checkVerifyCodePre(String verifyCode) {
		boolean result = true;
		String errorToast = "";
		if (ValueUtils.isStrEmpty(verifyCode)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_forgret_password_verifycode_notnull);
			result = false;
		} else if (!RegexUtils.isVerifyCode(verifyCode, 4)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_forgret_password_verifycode_error);
			result = false;
		}

		if (ValueUtils.isStrNotEmpty(errorToast)) {
			ViewUtils.showToast(MPlusApplication.getInstance(), errorToast,
					false);
		}

		return result;
	}

	/**
	 * @auther david
	 * @create 2014-8-1
	 * @param verifyCode
	 * @return boolean
	 * @description:判断验证码
	 */
	public static boolean checkPasswordCodePre(String password) {
		boolean result = true;
		if (ValueUtils.isStrEmpty(password)) {
			ViewUtils.showToast(MPlusApplication.getInstance(),MPlusApplication.getInstance().getString(R.string.text_password_null_error), false);
			result = false;
		} else if (password.length() < 6 || password.length() > 12) {
			ViewUtils.showToast(MPlusApplication.getInstance(),MPlusApplication.getInstance().getString(R.string.text_password_error), false);
			result = false;
		}

		return result;
	}

	/**
	 * @auther zhaochaoyue
	 * @create 2014-9-10
	 * @param oldpassword
	 *            and newpassword
	 * @return boolean
	 * @description:判断新旧密码
	 */
	public static boolean updateCheckPre(String oldPassword, String newPassword) {
		boolean result = true;
		String errorToast = "";
		if (ValueUtils.isStrEmpty(oldPassword)|| ValueUtils.isStrEmpty(newPassword)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_update_check_password_null_toast);
			result = false;
		} else if (oldPassword.equals(newPassword)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_update_check_password_same_toast);
			result = false;
		} else if (6 > oldPassword.length() || 6 > newPassword.length()) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_update_check_format_password_toast);
			result = false;
		}

		if (ValueUtils.isStrNotEmpty(errorToast)) {
			ViewUtils.showToast(MPlusApplication.getInstance(), errorToast,false);
		}

		return result;
	}


	/**
	 * @auther zhaochaoyue
	 * @create 2014-8-1
	 * @param 用户名和密码
	 * @return boolean
	 * @description:
	 */
	public static boolean modificationAddress(String name, String mobile,String address,String doorplate) {
		boolean result = true;
		String errorToast = "";
		if (ValueUtils.isStrEmpty(name) || ValueUtils.isStrEmpty(mobile)|| ValueUtils.isStrEmpty(address)|| ValueUtils.isStrEmpty(doorplate)){
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_modification_address);
			result = false;
		} else if (!RegexUtils.isMobile(mobile)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_userlogin_check_username_format_username_toast);
			result = false;
		} 
		if (ValueUtils.isStrNotEmpty(errorToast)) {
			ViewUtils.showToast(MPlusApplication.getInstance(), errorToast,false);
		}

		return result;
	}

	
	public static boolean isValidPwd(String str){
		boolean result = false;
		if(str.matches("\\w{6,18}"))//6-18位
		{
			//必须出现数字，字母，_ 三者中的两者
			if(str.matches("[\\w]*([0-9][a-zA-Z]|[a-zA-Z][0-9]|[_][a-zA-z]|[a-zA-z][_]|[_][0,9]|[0,9][_])+[\\w]*"))
			{
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 上映时间>成片时间>杀青时间>开机时间
	 * releasetime  //上映时间
	 * pieceTime  //成片时间
	 * fixationTime  //杀青时间
	 * bootTime  //开机时间 
	 * */
	@SuppressLint("SimpleDateFormat")
	public static String checkMovieTime( String releasetime,String pieceTime,String fixationTime,String bootTime){
		String info="您有时间未输入";
		try {
			if (releasetime==null || releasetime.length()<8) {
				info="请选择上映时间";
				return info;
			}
//			if (pieceTime==null || pieceTime.length()<8) {
//				info="请选择成片时间";
//				return info;
//			}
			if (fixationTime==null || fixationTime.length()<8) {
				info="请选择杀青时间";
				return info;
			}
			if (bootTime==null || bootTime.length()<8) {
				info="请选择开机时间";
				return info;
			}
			SimpleDateFormat sFormat=new SimpleDateFormat("yyyy-MM-dd");
			Date reldate=sFormat.parse(releasetime);
//			Date piedate=sFormat.parse(pieceTime);
			Date fixdate=sFormat.parse(fixationTime);
			Date bootdate=sFormat.parse(bootTime);
			if ( reldate!=null && fixdate!=null && bootdate!=null) {
				long rellong=reldate.getTime();
//				long pielong=piedate.getTime();
				long fixlong=fixdate.getTime();
				long bootlong=bootdate.getTime();
				if (fixlong-bootlong<=0) {
					info="杀青时间需大于开机时间";
					return info;
				}else if (rellong-fixlong<=0) {
					info="上映时间需大于杀青时间";
					return info;
				}
				
				/*else if (rellong-pielong<=0) {
					info="上映时间需大于成片时间";
					return info;
				}*/
				
				else {
					info="true";
					return info;
				}
			}
		} catch (Exception e) {
		}
		return info;
	}
	
	
	/**
	 * 档期结束时间>杀青时间
	 * 档期开始时间<开机时间
	 * starttime  //档期开始时间
	 * endTime  //档期结束时间
	 * fixationTime  //杀青时间
	 * bootTime  //开机时间
	 * */
	@SuppressLint("SimpleDateFormat")
	public static String checkNewRolesTime(String starttime,String endTime){
		String info="您有时间未输入";
		try {
			if (starttime==null || starttime.length()<8) {
				info="请选择档期开始时间";
				return info;
			}
			if (endTime==null || endTime.length()<8) {
				info="请选择档期结束时间";
				return info;
			}
			SimpleDateFormat sFormat=new SimpleDateFormat("yyyy-MM-dd");
			Date reldate=sFormat.parse(starttime);
			Date piedate=sFormat.parse(endTime);
			if (reldate!=null && piedate!=null) {
				long rellong=reldate.getTime();
				long pielong=piedate.getTime();
				if (pielong-rellong<=0) {
					info="档期结束时间需大于档期开始时间";
					return info;
				}
			}
			info="true";
			return info;
		} catch (Exception e) {
		}
		return info;
	}
	
	
	/**
	 * 档期结束时间>杀青时间
	 * 档期开始时间<开机时间
	 * starttime  //档期开始时间
	 * endTime  //档期结束时间
	 * fixationTime  //杀青时间
	 * bootTime  //开机时间
	 * */
	@SuppressLint("SimpleDateFormat")
	public static String checkStartTime(String starttime,String bootTime){
		String info="您有时间未输入";
		try {
			if (starttime==null || starttime.length()<8) {
				info="请选择档期开始时间";
				return info;
			}
			
			SimpleDateFormat sFormat=new SimpleDateFormat("yyyy-MM-dd");
			Date reldate=sFormat.parse(starttime);
			Date fixdate=sFormat.parse(bootTime);
			if (reldate!=null && fixdate!=null ) {
				long rellong=reldate.getTime();
				long fixlong=fixdate.getTime();
				if (fixlong-rellong<=0) {
					info="档期开始时间需小于开机时间\n                 "+bootTime;
					return info;
				}else {
					info="true";
					return info;
				}
			}
		} catch (Exception e) {
		}
		return info;
	}
	
	/**
	 * 档期结束时间>杀青时间
	 * 档期开始时间<开机时间
	 * starttime  //档期开始时间
	 * endTime  //档期结束时间
	 * fixationTime  //杀青时间
	 * bootTime  //开机时间
	 * */
	@SuppressLint("SimpleDateFormat")
	public static String checkEndTime( String starttime,String endTime,String fixationTime){
		String info="您有时间未输入";
		try {
			if (starttime==null || starttime.length()<8) {
				info="请选择档期开始时间";
				return info;
			}
			
			if (endTime==null || endTime.length()<8) {
				info="请选择档期结束时间";
				return info;
			}
			
			SimpleDateFormat sFormat=new SimpleDateFormat("yyyy-MM-dd");
			Date strdate=sFormat.parse(starttime);
			Date reldate=sFormat.parse(endTime);
			Date fixdate=sFormat.parse(fixationTime);
			if (reldate!=null && fixdate!=null ) {
				long strlong=strdate.getTime();
				long rellong=reldate.getTime();
				long fixlong=fixdate.getTime();
				if (rellong-strlong<=0) {
					info="档期结束时间需大于档期开始时间";
					return info;
				}
				if (rellong-fixlong<=0) {
					info="档期结束时间需大于杀青时间\n                 "+fixationTime;
					return info;
				}else {
					info="true";
					return info;
				}
			}
		} catch (Exception e) {
		}
		return info;
	}
	
	
	
	
	
	
	
	public static boolean isEmail(String email) {
		boolean result = true;
		String errorToast = "";
		if (ValueUtils.isStrEmpty(email)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_email_not_null);
			result = false;
		} else if (!RegexUtils.isEmail(email)) {
			errorToast = ValueUtils.getString(MPlusApplication.getInstance(),R.string.text_email_form_mistake);
			result = false;
		}

		if (ValueUtils.isStrNotEmpty(errorToast)) {
			ViewUtils.showToast(MPlusApplication.getInstance(), errorToast,false);
		}

		return result;
	}
}


