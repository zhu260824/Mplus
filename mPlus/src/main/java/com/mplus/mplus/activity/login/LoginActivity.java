package com.mplus.mplus.activity.login;

import java.util.Map;

import org.androidpn.client.ServiceManager;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.dtd365.library.Utils.MobclickAgentUtils;
import com.dtd365.library.http.RequestCall;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.http.TokenUtils;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.manger.SaveTools;
import com.mplus.mplus.manger.SaveType;
import com.mplus.mplus.paser.login.User;
import com.mplus.mplus.utils.CheckUtils;
import com.mplus.mplus.view.MyDialog;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.UMSsoHandler;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @Title: 登录页面
 * @Description:
 * @author zhaochaoyue
 * @date 2015年8月17日
 */
public class LoginActivity extends BaseActivity {
	private ImageView login_phone_clean, login_pwd_show, login_back,ibut_checkbox_loading;
	private EditText et_name_loading, et_password_loading;
	private Boolean checkBoxOnOff = true;
	private String phone, password;
	private TextView tv_forget_the_password_loading;
	private LinearLayout lin_loading;
	private Button btnLoading,btnRegister;
	private int lastLenght = 0;
	private boolean flag = false;
	private ImageView ibut_qq, ibut_sina,ibut_weixin;
	private UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ibut_checkbox_loading = (ImageView) findViewById(R.id.ibut_checkbox_on_loading);
		tv_forget_the_password_loading = (TextView) findViewById(R.id.tv_forget_the_password_loading);
		ibut_qq = (ImageView) findViewById(R.id.ibut_qq);
		ibut_sina = (ImageView) findViewById(R.id.ibut_sina);
		ibut_weixin=(ImageView)findViewById(R.id.ibut_weixin);
		lin_loading = (LinearLayout) findViewById(R.id.lin_loading);
		et_name_loading = (EditText) findViewById(R.id.et_name_loading);
		btnLoading = (Button) findViewById(R.id.btnLoading);
		et_password_loading = (EditText) findViewById(R.id.et_password_loading);
		login_phone_clean = (ImageView) findViewById(R.id.login_phone_clean);
		login_back = (ImageView) findViewById(R.id.login_back);
		login_pwd_show = (ImageView) findViewById(R.id.login_pwd_show);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		// 读取存储手机中的用户名跟密码
		phone = SerializableFactory.sharedPreferencesReadString("NameAndPassword", "phone");
		password = SerializableFactory.sharedPreferencesReadString("NameAndPassword", "password");
		if (phone != null) {
			et_name_loading.setText(phone);
			if (password != null)
				et_password_loading.setText(password);
		}

		// 跳转到注册页面
		btnRegister.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
				LoginActivity.this.finish();
			}
		});

		// 是否记录用户名跟密码
		lin_loading.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (!checkBoxOnOff) {
					checkBoxOnOff = true;
					ibut_checkbox_loading.setBackgroundResource(R.drawable.checkbox_on);
				} else {
					checkBoxOnOff = false;
					ibut_checkbox_loading.setBackgroundResource(R.drawable.checkbox_off);
				}
			}
		});
		// QQ登录
		ibut_qq.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Toast.makeText(LoginActivity.this, "内测版本，暂不提供",Toast.LENGTH_SHORT).show();
//				try {
//					LoginActivity.this.getPackageManager().getApplicationInfo("com.tencent.mobileqq",PackageManager.GET_UNINSTALLED_PACKAGES);
//				} catch (NameNotFoundException e) {
//					Toast.makeText(LoginActivity.this, "请先安装QQ",Toast.LENGTH_SHORT).show();
//					return;
//				}
//				AddPlatformTool.addQQQZonePlatform(LoginActivity.this);
//				login(SHARE_MEDIA.QQ, 1);
			}
		});
		ibut_sina.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Toast.makeText(LoginActivity.this, "内测版本，暂不提供",Toast.LENGTH_SHORT).show();
//				mController.getConfig().setSsoHandler(new SinaSsoHandler());
//				login(SHARE_MEDIA.SINA, 2);
			}
		});
		ibut_weixin.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Toast.makeText(LoginActivity.this, "内测版本，暂不提供",Toast.LENGTH_SHORT).show();
				
			}
		});
		tv_forget_the_password_loading.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						showRestPsw();
					}
				});

		btnLoading.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String userName = et_name_loading.getText().toString().trim();
				String password = et_password_loading.getText().toString();
				boolean flag = CheckUtils.loginCheckPre(userName, password);
				if (!flag) {
					return;
				}
				// 判断保存手机密码
				if (checkBoxOnOff) {
					SerializableFactory.sharedPreferencesSave(SaveType.NameAndPassword, SaveType.phone, userName);
					SerializableFactory.sharedPreferencesSave(SaveType.NameAndPassword, SaveType.password, password);
				} else {
					SerializableFactory.sharedPreferencesRemove(SaveType.NameAndPassword, null, 0);
				}
				showLoadingDialog();
				RequestManger.Login(LoginActivity.this, userName, password,new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						dismissLoadingDialog();
						Toast.makeText(LoginActivity.this,response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
						if (response.getParser().getResultSuccess()) {
							MPlusApplication.isLogin = true;
							try {
								JSONObject jsonObject = new JSONObject(response.getParser().getData());
								User user = new User();
								user.portraitpath = jsonObject.isNull("shortcut") ? "": jsonObject.getString("shortcut");
								user.alias = jsonObject.isNull("alias") ? "" : jsonObject.getString("alias");
								user.userid = jsonObject.isNull("userid") ? "" : jsonObject.getString("userid");
								user.username = jsonObject.isNull("username") ? "": jsonObject.getString("username");
								ServiceManager.setAlias(user.username, getApplicationContext());
								SaveTools.upDateUser(user);
								String token=jsonObject.isNull("token") ? "": jsonObject.getString("token");
								MPlusApplication.setLoginToken(TokenUtils.saveSystemTime(token));
								MobclickAgentUtils.MobclickProfilrSiginIn(user.userid);
								RequestManger.GetUserData(LoginActivity.this, user.userid,
										new Listener<RequestCall>() {
											public void onResponse(RequestCall response) {
											}
										}, MHttpTools.getErrorListener(LoginActivity.this));
							} catch (JSONException e) {
								e.printStackTrace();
							}
							Intent backdata = new Intent();
							setResult(RESULT_OK, backdata);
							LoginActivity.this.finish();
						}
					}
				}, MHttpTools.getErrorListener(LoginActivity.this));
			}
		});
		et_name_loading.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (et_name_loading.getText() != null) {
					String name = et_name_loading.getText().toString();
					if (name != null) {
						if (name.length() < lastLenght) {
							SerializableFactory.sharedPreferencesSave(SaveType.NameAndPassword, SaveType.password, "");
							et_password_loading.setText("");
						}
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				if (et_name_loading.getText() != null) {
					String name = et_name_loading.getText().toString();
					if (name != null) {
						lastLenght = name.length();
					}
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		login_phone_clean.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				et_name_loading.setText("");
			}
		});

		login_pwd_show.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (flag) {
					// 文本以密码形式显示
					et_password_loading.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					// 下面两行代码实现: 输入框光标一直在输入文本后面
					Editable etable = et_password_loading.getText();
					Selection.setSelection(etable, etable.length());
					login_pwd_show.setImageResource(R.drawable.password_hide);
					flag = false;
				} else {
					et_password_loading.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					Editable etable = et_password_loading.getText();
					Selection.setSelection(etable, etable.length());
					login_pwd_show.setImageResource(R.drawable.password_show);
					flag = true;
				}

			}
		});

		login_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginActivity.this.finish();
			}
		});
	}

	private void showRestPsw() {
		View view = View.inflate(LoginActivity.this, R.layout.dialog_forget,null);
		AutoUtils.autoSize(view);
		final MyDialog dialog = new MyDialog(LoginActivity.this,R.style.CustomDialog);
		LinearLayout.LayoutParams dialoglp = new LinearLayout.LayoutParams(MPlusApplication.Width * 4 / 5,LinearLayout.LayoutParams.WRAP_CONTENT);
		dialog.addContentView(view, dialoglp);
		dialog.setCancelable(true);
		dialog.show();
		TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
		TextView tv_cacel = (TextView) view.findViewById(R.id.tv_cancel);
		tv_cacel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (dialog != null && dialog.isShowing())
					dialog.dismiss();
			}
		});
		tv_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (dialog != null && dialog.isShowing())
					dialog.dismiss();
				startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
			}
		});
	}

	// 各种登录接口
	private void login(final SHARE_MEDIA platform, final int type) {
		mController.doOauthVerify(LoginActivity.this, platform,new UMAuthListener() {
					public void onStart(SHARE_MEDIA platform) {
						Toast.makeText(LoginActivity.this, "正在授权中请稍等",Toast.LENGTH_SHORT).show();
					}

					public void onError(SocializeException e,
							SHARE_MEDIA platform) {
					}

					public void onComplete(Bundle value, SHARE_MEDIA platform) {
						// QQ登录
						if (type == 1) {
							String openid = value.getString("openid");
							RequestManger.QQLogin(LoginActivity.this, openid,1, new Listener<RequestCall>() {
										public void onResponse(RequestCall response) {
											if (response.getParser().getResultSuccess()) {
												JSONObject jsonObject;
												try {
													jsonObject = new JSONObject(response.getParser().getData());
													User user = new User();
													user.userid = jsonObject.isNull("userid") ? "": jsonObject.getString("userid");
													RequestManger.GetUserData(LoginActivity.this,user.userid,new Listener<RequestCall>() {
																		public void onResponse(RequestCall response) {

																		}
																	},
																	MHttpTools.getErrorListener(LoginActivity.this));
												} catch (JSONException e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												}

											}
										}
									}, MHttpTools.getErrorListener(LoginActivity.this));
							if (!TextUtils.isEmpty(openid)) {
								getUserInfo(platform, type);
							} else {
								Toast.makeText(LoginActivity.this, "授权失败...",Toast.LENGTH_SHORT).show();
							}
						} else {
							// 新浪
							String uid = value.getString("uid");
							RequestManger.QQLogin(LoginActivity.this, uid, 1,
									new Listener<RequestCall>() {
										public void onResponse(RequestCall response) {
											if (response.getParser().getResultSuccess()) {
												JSONObject jsonObject;
												try {
													jsonObject = new JSONObject(response.getParser().getData());
													User user = new User();
													user.userid = jsonObject.isNull("userid") ? "": jsonObject.getString("userid");
													RequestManger.GetUserData(LoginActivity.this,user.userid,new Listener<RequestCall>() {
																		public void onResponse(RequestCall response) {

																		}
																	},
															MHttpTools.getErrorListener(LoginActivity.this));
												} catch (JSONException e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												}

											}
										}
									}, MHttpTools.getErrorListener(LoginActivity.this));
							if (!TextUtils.isEmpty(uid)) {
								getUserInfo(platform, type);
							} else {
								Toast.makeText(LoginActivity.this, "授权失败...",Toast.LENGTH_SHORT).show();
							}
						}
					}

					public void onCancel(SHARE_MEDIA platform) {
					}
				});

	}

	/**
	 * 获取授权平台的用户信息</br>
	 */
	private void getUserInfo(SHARE_MEDIA platform, final int type) {
		mController.getPlatformInfo(LoginActivity.this, platform,
				new UMDataListener() {
					public void onStart() {
					}

					public void onComplete(int status, Map<String, Object> info) {
						@SuppressWarnings("unused")
						String showText = "";
						if (status == StatusCode.ST_CODE_SUCCESSED) {
							showText = "用户名："+ info.get("screen_name").toString();
							User user = new User();
							if (type == 2) {
//								user.address = info.get("location").toString();
								user.userid = info.get("uid").toString();
								user.username = info.get("screen_name").toString();
								user.portraitpath = info.get("profile_image_url").toString();
							} else if (type == 1) {
								user.province = info.get("province").toString();
								user.sex = info.get("gender").toString();
								user.username = info.get("screen_name").toString();
								user.city = info.get("city").toString();
								user.portraitpath = info.get("profile_image_url").toString();
							}
							SaveTools.upDateUser(user);
						} else {
							showText = "获取用户信息失败";
						}
						// if (info != null) {
						// Toast.makeText(LoginActivity.this, info.toString(),
						// Toast.LENGTH_SHORT).show();
						// }
						Intent backdata = new Intent();
						setResult(RESULT_OK, backdata);
						LoginActivity.this.finish();
					}
				});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}
}
