package com.mplus.mplus.activity.systemset;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.SystemsTool;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
 * 问题反馈
 */
public class SystemSetFeedbackActivity extends BaseActivity implements OnClickListener{

	private EditText et_feedback_content,et_feedback_phone;
	private TextView tv_autograph_number_of_words;
	private Button btn_feedback_commit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_systemset_feedback);
		initTitle(getString(R.string.text_system_feedback));
		init();
	}

	private void init() {
		btn_feedback_commit = (Button) findViewById(R.id.btn_feedback_commit);
		tv_autograph_number_of_words = (TextView) findViewById(R.id.tv_number_of_words);
		et_feedback_content=(EditText) findViewById(R.id.et_feedback_content);
		et_feedback_phone=(EditText) findViewById(R.id.et_feedback_phone);
		et_feedback_content.addTextChangedListener(new TextWatcher() {   
		    public void afterTextChanged(Editable s) {  
		    }  
		  	public void beforeTextChanged(CharSequence s, int start, int count,  
		            int after) {  
		    }  
		  	public void onTextChanged(CharSequence s, int start, int before,  
		            int count) {  
		        String content = et_feedback_content.getText().toString();  
		        tv_autograph_number_of_words.setText(""+(400-content.length()));  
		    }  
		});  
		btn_feedback_commit.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_feedback_commit:
			if (et_feedback_content==null || et_feedback_content.getText()==null) {
				Toast.makeText(SystemSetFeedbackActivity.this, "请输入您的宝贵意见", Toast.LENGTH_SHORT).show();
				return;
			}
			String content=et_feedback_content.getText().toString();
			if (content==null || content.length()<1) {
				Toast.makeText(SystemSetFeedbackActivity.this, "请输入您的宝贵意见", Toast.LENGTH_SHORT).show();
				return;
			}
			String userid=null;
			if (MPlusApplication.isLogin) 
				userid=UserPaser.GetInstance().userid;
			RequestManger.postFeedBack(SystemSetFeedbackActivity.this, userid, null, content, android.os.Build.MODEL, SystemsTool.getLocalIpAddress(), SystemsTool.getVersionName(SystemSetFeedbackActivity.this), et_feedback_phone.getText().toString(), new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()) {
						Toast.makeText(SystemSetFeedbackActivity.this, "十分感谢您的宝贵意见", Toast.LENGTH_SHORT).show();
						SystemSetFeedbackActivity.this.finish();
					}else {
						if (response.getParser().getResponseMsg()!=null) 
							Toast.makeText(SystemSetFeedbackActivity.this, response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
					}
				}
			}, MHttpTools.getErrorListener(SystemSetFeedbackActivity.this));
			break;
		default:
			break;
		}
	}
}
