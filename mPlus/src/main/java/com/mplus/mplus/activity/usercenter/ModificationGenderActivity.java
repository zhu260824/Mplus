package com.mplus.mplus.activity.usercenter;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.manger.SaveTools;
import com.mplus.mplus.paser.login.User;
import com.mplus.mplus.paser.login.UserPaser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * @Title: 修改性别
 * @Description: 
 * @author zhaochaoyue
 *
 * @date 2015年8月27日
 */
public class ModificationGenderActivity extends BaseActivity {
	private ImageButton ibut_man, ibut_woman;
	private RelativeLayout rl_man, rl_woman;
	private Boolean man_or_woman = true;
	private User user ;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modification_gender);
		ibut_man = (ImageButton) findViewById(R.id.ibut_man_modification_gender);
		ibut_woman = (ImageButton) findViewById(R.id.ibut_woman_modification_gender);
		rl_man = (RelativeLayout) findViewById(R.id.rl_man_modification_gender);
		rl_woman = (RelativeLayout) findViewById(R.id.rl_woman_modification_gender);
		initTitle(getString(R.string.text_personal_data_gender));
		initShowRight(0, getString(R.string.save), false, true, 0);
		initdata();
		rl_man.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				man_or_woman = true;
				ibut_woman.setVisibility(View.VISIBLE);
				ibut_man.setVisibility(View.GONE);
			}
		});
		rl_woman.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				man_or_woman = false;
				ibut_man.setVisibility(View.VISIBLE);
				ibut_woman.setVisibility(View.GONE);
			}
		});
		addRightClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RequestManger.updateBaseUserOne(ModificationGenderActivity.this,user.userid, "sex", (man_or_woman) ? "男" : "女",  user.version,listener, errorListener);
				
			}
		});
	}
	
	private void initdata(){
		user = UserPaser.GetInstance();
		if (user != null) {
			if (user.sex==null) {
				man_or_woman = true;
				ibut_man.setVisibility(View.GONE);
				ibut_woman.setVisibility(View.VISIBLE);
			}else {
				if (user.sex.equals("女")) {
					man_or_woman = false;
					ibut_man.setVisibility(View.VISIBLE);
					ibut_woman.setVisibility(View.GONE);
				}
			}
		}
	}
	
	private Listener<RequestCall> listener=new Listener<RequestCall>() {

		@Override
		public void onResponse(RequestCall response) {
			if (response.getParser().getResultSuccess()) {	
				try {
					String resonses=response.getParser().getData();
					if (resonses!=null) {
						JSONObject jsObject=new JSONObject(resonses);
						if (jsObject!=null) {
							String version=jsObject.getString("version");
							user.version=version;
							String gender = (man_or_woman) ? "男" : "女";
							user.sex=gender;
							SaveTools.upDateUser(user);
							Intent backdata=new Intent();
							backdata.putExtra("gender", gender);;
							setResult(RESULT_OK,backdata);
							ModificationGenderActivity.this.finish();
						}
					}
				} catch (JSONException e) {
						e.printStackTrace();
					}
			}else {
				String msg=response.getParser().getResponseMsg();
				if (msg!=null && msg.equals(getString(R.string.data_isold))) {
					Toast.makeText(ModificationGenderActivity.this, "更新数据，请稍后再提交", Toast.LENGTH_SHORT).show();
					RequestManger.GetUserData(ModificationGenderActivity.this, user.userid, new Listener<RequestCall>() {

						@Override
						public void onResponse(RequestCall response) {
							initdata();
						}
					}, errorListener);
				}else if (msg!=null) {
					Toast.makeText(ModificationGenderActivity.this, msg, Toast.LENGTH_SHORT).show();
				}
			}
		}
	};
}
