package com.mplus.mplus.activity.login;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.common.ShowTermActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.utils.CheckUtils;
import com.mplus.mplus.utils.ToastTool;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Selection;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @Title: 注册界面
 * @Description:
 * @author zhaochaoyue
 * @date 2015年8月18日
 */
public class RegisterActivity extends BaseActivity {
	private TextView tv_clause_volue_register;
	private Boolean checkBoxOnOff = true, isCheckShow = true;
	private ImageView register_back,iv_password_register,iv_checkbox_on_register;
	private EditText et_password_register, et_phone_register,et_verification_register;
	private Button but_register;
	private LinearLayout right_ly,lin_agress_term;
	private TextView tv_time;
	protected void onCreate(Bundle arg0) { 
		super.onCreate(arg0);
		setContentView(R.layout.activity_register);
		iv_checkbox_on_register = (ImageView) findViewById(R.id.iv_checkbox_on_register);
		iv_password_register = (ImageView) findViewById(R.id.iv_password_register);
		tv_clause_volue_register = (TextView) findViewById(R.id.tv_clause_volue_register);
		register_back = (ImageView) findViewById(R.id.register_back);
		lin_agress_term = (LinearLayout) findViewById(R.id.lin_agress_term);
		et_password_register = (EditText) findViewById(R.id.et_password_register);
		et_phone_register = (EditText) findViewById(R.id.et_phone_register);
		et_verification_register = (EditText) findViewById(R.id.et_verification_register);
		but_register = (Button) findViewById(R.id.but_register);
		right_ly = (LinearLayout) findViewById(R.id.right_ly);
		tv_time=(TextView) findViewById(R.id.tv_time);
		initTitle(getString(R.string.register));
		// 获取密码倒计时
		tv_time.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						String phone = et_phone_register.getText().toString();
						if (!CheckUtils.checkMobilePre(phone)) {
							return;
						}
						timer.start();
						RequestManger.GetCaptchas(RegisterActivity.this, phone,"0",  new Listener<RequestCall>() {
							public void onResponse(RequestCall response) {
								if (response.getParser().getResultSuccess()) {
									Toast.makeText(RegisterActivity.this,getResources().getString(R.string.captcha_send), Toast.LENGTH_SHORT).show();
								}else {
									if (response.getParser().getResponseMsg()!=null)
										Toast.makeText(RegisterActivity.this,response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
								}
							}
						}, MHttpTools.getErrorListener(RegisterActivity.this));
					}
				});
		// 点击是否同意条款
		lin_agress_term.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (!checkBoxOnOff) {
					checkBoxOnOff = true;
					iv_checkbox_on_register.setBackgroundResource(R.drawable.checkbox_on);
				} else {
					checkBoxOnOff = false;
					iv_checkbox_on_register.setBackgroundResource(R.drawable.checkbox_off);
				}
			}
		});
		// 点击条款
		tv_clause_volue_register.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				startActivity(new Intent(RegisterActivity.this,ShowTermActivity.class));
			}
		});
		iv_password_register.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (isCheckShow) {
					isCheckShow = false;
					// 下面两行代码实现: 输入框光标一直在输入文本后面
					Editable etable = et_password_register.getText();
					Selection.setSelection(etable, etable.length());
					// 显示密码
					iv_password_register.setBackgroundResource(R.drawable.password_show);
//					et_password_register.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
					et_password_register.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				} else {
					isCheckShow = true;
					// 下面两行代码实现: 输入框光标一直在输入文本后面
					Editable etable = et_password_register.getText();
					Selection.setSelection(etable, etable.length());
					// 隐藏密码
					iv_password_register.setBackgroundResource(R.drawable.password_hide);
//					et_password_register.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
					et_password_register.setTransformationMethod(PasswordTransformationMethod.getInstance());
				}

			}
		});
		// 点击注册
		but_register.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String phone = et_phone_register.getText().toString();
				String code = et_verification_register.getText().toString();
				String psw = et_password_register.getText().toString();
				if (!CheckUtils.checkRegister(phone, code, psw, checkBoxOnOff)) {
					return;
				}
				showLoadingDialog();
				RequestManger.Regest(RegisterActivity.this, phone, psw, code,new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						dismissLoadingDialog();
						if (response.getParser().getResultSuccess()) {
							String userid=null;					
							try {
								userid=new JSONObject(response.getParser().getData()).getString("userid");
							} catch (JSONException e) {
								e.printStackTrace();
							}
							if (userid!=null) {
								Intent it=new Intent(RegisterActivity.this,PersonalDataActivity.class);
								it.putExtra("userid", userid);
								it.putExtra("phone", et_phone_register.getText().toString());
//								it.putExtra("password", et_password_register.getText().toString());
								RegisterActivity.this.startActivity(it);
							}else {
								startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
							}
							ToastTool.showShortToast(RegisterActivity.this, getResources().getString(R.string.regest_sucess));
							RegisterActivity.this.finish();
						}else {
							if (response.getParser().getResponseMsg()!=null)
								ToastTool.showShortToast(RegisterActivity.this, response.getParser().getResponseMsg());
						}
					}
				}, MHttpTools.getErrorListener(RegisterActivity.this));
			}
		});
		
		//跳转到登录
		right_ly.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
			}
		});
		
		//
		register_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				RegisterActivity.this.finish();
			}
		});
	}

	// 倒数计时器
	private CountDownTimer timer = new CountDownTimer(60000, 1000) {
		public void onTick(long millisUntilFinished) {
			tv_time.setClickable(false);
			tv_time.setTextColor(getResources().getColorStateList(R.color.blue_tag));
			tv_time.setText(getString(R.string.text_reset_register_gain_verification_code)+" ("+(millisUntilFinished / 1000) +"s)");
		}

		public void onFinish() {
			tv_time.setClickable(true);
			tv_time.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			tv_time.setText(getString(R.string.text_reset_register_gain_verification_code));
		}
	};
}
