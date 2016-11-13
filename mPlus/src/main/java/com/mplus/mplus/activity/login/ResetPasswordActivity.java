package com.mplus.mplus.activity.login;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.utils.CheckUtils;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @Title: 重置密码界面
 * @Description:
 * @author zhaochaoyue
 * @date 2015年8月18日
 */
public class ResetPasswordActivity extends BaseActivity {
	private EditText et_password_two, et_password_one,et_mobile_reset_password, et_verification_code_reset_password;
	private ImageButton ibut_password_one_show, ibut_password_two_show;
	private Boolean isCheckedOne = true, isCheckedTwo = true;
	private Button but_reset_password;
	private TextView tv_time;

	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_reset_password);
		et_password_one = (EditText) findViewById(R.id.et_password_one);
		et_password_two = (EditText) findViewById(R.id.et_password_two);
		tv_time=(TextView) findViewById(R.id.tv_time);
		et_verification_code_reset_password = (EditText) findViewById(R.id.et_verification_code_reset_password);
		et_mobile_reset_password = (EditText) findViewById(R.id.et_mobile_reset_password);
		ibut_password_one_show = (ImageButton) findViewById(R.id.ibut_password_one_show);
		ibut_password_two_show = (ImageButton) findViewById(R.id.ibut_password_two_show);
		but_reset_password = (Button) findViewById(R.id.but_reset_password);
		initTitle(getString(R.string.text_register_reset_password));
		tv_time.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						String phone = et_mobile_reset_password.getText().toString();
						if (!CheckUtils.checkMobilePre(phone)) {
							return;
						}
						RequestManger.GetCaptchas(ResetPasswordActivity.this,phone, "1", new Listener<RequestCall>() {

							@Override
							public void onResponse(RequestCall response) {
								if (response.getParser().getResultSuccess()) {
									timer.start();
									Toast.makeText(ResetPasswordActivity.this,getResources().getString(R.string.captcha_send), Toast.LENGTH_SHORT).show();
								}else {
									if (response.getParser().getResultSuccess())
										Toast.makeText(ResetPasswordActivity.this,response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
								}
							}
						}, MHttpTools.getErrorListener(ResetPasswordActivity.this));
					}
				});

		// 隐藏显示密码
		ibut_password_one_show.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				if (isCheckedOne) {
					isCheckedOne = false;
					// 显示密码
					ibut_password_one_show.setBackgroundResource(R.drawable.password_show);
					et_password_one.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				} else {
					isCheckedOne = true;
					// 隐藏密码
					ibut_password_one_show.setBackgroundResource(R.drawable.password_hide);
					et_password_one.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}
			}
		});

		ibut_password_two_show.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				if (isCheckedTwo) {
					isCheckedTwo = false;
					// 显示密码
					ibut_password_two_show.setBackgroundResource(R.drawable.password_show);
					et_password_two.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				} else {
					isCheckedTwo = true;
					// 隐藏密码
					ibut_password_two_show.setBackgroundResource(R.drawable.password_hide);
					et_password_two.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}

			}
		});
		// 重置密码点击返回主页
		but_reset_password.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				String phone = et_mobile_reset_password.getText().toString();
				String code = et_verification_code_reset_password.getText().toString();
				String psw = et_password_one.getText().toString();
				String apsw = et_password_two.getText().toString();
				if (!CheckUtils.checkResetPasswrod(phone, code, psw, apsw)) {
					return;
				}
				showLoadingDialog();
				RequestManger.ForgetPsw(ResetPasswordActivity.this, phone,code, psw, new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						dismissLoadingDialog();
						if (response.getParser().getResultSuccess()) {
							ResetPasswordActivity.this.finish();
							Toast.makeText(ResetPasswordActivity.this,getResources().getString(R.string.resetpsw_scuss), Toast.LENGTH_SHORT).show();
						}else {
							if (response.getParser().getResultSuccess())
								Toast.makeText(ResetPasswordActivity.this,response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
						}
					}
				}, MHttpTools.getErrorListener(ResetPasswordActivity.this));
			}
		});
	}

	// 倒数计时器
	@SuppressWarnings("deprecation")
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
