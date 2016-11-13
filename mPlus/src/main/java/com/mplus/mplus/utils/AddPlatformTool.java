package com.mplus.mplus.utils;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.MailShareContent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.SmsShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.EmailHandler;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.TencentWBSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import android.app.Activity;

public class AddPlatformTool {
	
	private  final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	private Activity activity;
	
	public AddPlatformTool(Activity activity) {
		super();
		this.activity = activity;
		configPlatforms();
	}

	public void shareMsg(String content,String title,String url,int localImageId,String urlImageUrl){
		AddPlatformTool.addEmail();
		AddPlatformTool.addSMS();
		AddPlatformTool.setShareContent(activity,mController,content,title,url,localImageId,urlImageUrl);
		mController.getConfig().setPlatforms(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,SHARE_MEDIA.SINA,
				SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
                    SHARE_MEDIA.EMAIL,SHARE_MEDIA.SMS);
        mController.openShare(activity, false);
	}
	
	  /**
	   * 配置分享平台参数
	   */
	  private void configPlatforms() {
	     // 添加新浪SSO授权
	      mController.getConfig().setSsoHandler(new SinaSsoHandler());
	      // 添加腾讯微博SSO授权
	      mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
	      // 添加QQ、QZone平台
	     AddPlatformTool.addQQQZonePlatform(activity);

	     // 添加微信、微信朋友圈平台
	     AddPlatformTool.addWXPlatform(activity);
	 }

	/**
     * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
     *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
     *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
     *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
     * @return
     */
    public static void addQQQZonePlatform(Activity activity) {
        String appId = "1104874573";
        String appKey = "eeBp8yFbqneGVUNz";
        // 添加QQ支持, 并且设置QQ分享内容的target url
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity,appId, appKey);
        qqSsoHandler.addToSocialSDK();
        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, appId, appKey);
        qZoneSsoHandler.addToSocialSDK();
    }
    
    /**
     * @功能描述 : 添加微信平台分享
     * @return
     */
    public static void addWXPlatform(Activity activity) {
        // 注意：在微信授权的时候，必须传递appSecret
        // wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
        String appId = "wx5d5bd9b9532cabd5";
        String appSecret = "d4624c36b6795d1d99dcf0547af5443d";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(activity, appId, appSecret);
        wxHandler.addToSocialSDK();

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(activity, appId, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
    }
    
    
    
    /**
     * 添加短信平台</br>
     */
    public static void addSMS() {
        // 添加短信
        SmsHandler smsHandler = new SmsHandler();
        smsHandler.addToSocialSDK();
    }

    /**
     * 添加Email平台</br>
     */
    public static void addEmail() {
        // 添加email
        EmailHandler emailHandler = new EmailHandler();
        emailHandler.addToSocialSDK();
    }
    /**
     * 根据不同的平台设置不同的分享内容</br>
     */
    public static void setShareContent(Activity activity,UMSocialService mController,String content,String title,String url,int localImageId,String urlImageUrl) {
	    UMImage localImage=null;
	    if (localImageId!=0) {
	    	localImage=new UMImage(activity, localImageId);
	    }
	    UMImage urlImage=null;
	    if (urlImageUrl!=null) {
	    	urlImage=new UMImage(activity, urlImageUrl);
	    }	
       
    	WeiXinShareContent weixinContent = new WeiXinShareContent();
        weixinContent.setShareContent(content);
        weixinContent.setTitle(title);
        weixinContent.setTargetUrl(url);
        if (localImage!=null) 
			weixinContent.setShareImage(localImage);
        if (urlImage!=null) 
			weixinContent.setShareImage(urlImage);
        mController.setShareMedia(weixinContent);

        // 设置朋友圈分享的内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareContent(content);
        circleMedia.setTitle(title);
        circleMedia.setTargetUrl(url);
        if (localImage!=null) 
        	circleMedia.setShareImage(localImage);
        if (urlImage!=null) 
            circleMedia.setShareImage(urlImage);
        mController.setShareMedia(circleMedia);



        // 设置QQ空间分享内容
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent(content);
        qzone.setTitle(title);
        // qzone.setShareMedia(uMusic);
        qzone.setTargetUrl(url);
        if (localImage!=null) 
        	qzone.setShareImage(localImage);
        if (urlImage!=null) 
        	qzone.setShareImage(urlImage);
        mController.setShareMedia(qzone);

        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent(content);
        qqShareContent.setTitle(title);
        qqShareContent.setTargetUrl(url);
        if (localImage!=null) 
        	qqShareContent.setShareImage(localImage);
        if (urlImage!=null) 
        	qqShareContent.setShareImage(urlImage);
        mController.setShareMedia(qqShareContent);

       /* TencentWbShareContent tencent = new TencentWbShareContent();
        tencent.setShareContent(content);
        // 设置tencent分享内容
        tencent.setTargetUrl("www.mplus.cn");
        mController.setShareMedia(tencent);*/

        // 设置邮件分享内容， 如果需要分享图片则只支持本地图片
        MailShareContent mail = new MailShareContent();
        mail.setTitle(title);
        mail.setShareContent(content);
        if (localImage!=null) 
        	mail.setShareImage(localImage);
        if (urlImage!=null) 
        	mail.setShareImage(urlImage);
        // 设置tencent分享内容
        mController.setShareMedia(mail);

        // 设置短信分享内容
        SmsShareContent sms = new SmsShareContent();
        sms.setShareContent(content);
        if (localImage!=null) 
        	sms.setShareImage(localImage);
        if (urlImage!=null) 
        	sms.setShareImage(urlImage);
        mController.setShareMedia(sms);

        SinaShareContent sinaContent = new SinaShareContent();
        sinaContent.setShareContent(content);
        sinaContent.setTargetUrl(url);
        sinaContent.setTitle(title);
        if (localImage!=null) 
        	sinaContent.setShareImage(localImage);
        if (urlImage!=null) 
        	sinaContent.setShareImage(urlImage);
        mController.setShareMedia(sinaContent);
    }
	
}
