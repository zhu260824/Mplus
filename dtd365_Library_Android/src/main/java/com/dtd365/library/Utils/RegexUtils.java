package com.dtd365.library.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cwtlib
 * @version 1.0.1
 * @create 2014-6-25
 */
public class RegexUtils {
	
	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-25
	 * 
	 * @param code
	 * @return boolean
	 */
	public static boolean isEmail(String email) {
		String check = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern pattern = Pattern.compile(check);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-25
	 * 
	 * @param code
	 * @return boolean
	 */
	public static boolean isMobile(String mobile) {
		Pattern p=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0,6,7])|(147))\\d{8}$");
		//Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,2,5-9]))\\d{8}$");

//		Pattern p = Pattern.compile("^((1[0-9]))\\d{9}$");
//		Pattern p=Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,3-9])|(17[0,6,7])|(147))\\d{8}$");//		Pattern p = Pattern.compile("^[1]([3][0-9]{1}|59|58|88|89|50|82|52|87)[0-9]{8}$");
		return p.matcher(mobile).matches();
	}
	
	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-25
	 * 
	 * @param code
	 * @return boolean
	 */
	public static boolean isIMEIEmpty(String imeiStr) {
		return "".equals(imeiStr) ? true : imeiStr.matches("0+");
	}
	
	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-25
	 * 
	 * @param code : this is verify code strings
	 * @param digit : this is verifycode's digit
	 * @return boolean
	 */
	public static boolean isVerifyCode(String code,int digit){
		String regexString = "[0-9]{" + digit + "}";
		Pattern pattern = Pattern.compile(regexString);
		Matcher matcher = pattern.matcher(code);
		return matcher.matches();
	}
}
