package com.mplus.mplus.activity.pushproject;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.transition.SidePropagation;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.dialog.ChangeProjectTimeDialog;
import com.mplus.mplus.dialog.ChangeProjectTimeDialog.OnTimeListener;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.utils.CheckUtils;

public class SecondPushWorksActivity extends BaseActivity implements OnClickListener{
	private static final int TONEXT=1;//下一步
	private Button btn_tonext;
	private EditText et_money,et_shooting_adress;
	private TextView tv_openrobot_time,tv_finish_time,tv_finish_movie_time,tv_show_time;
	private LinearLayout lin_openrobot_time,lin_finish_time,lin_finish_movie_time,lin_show_time;
	private String mproductid,userid,version="1";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_push_works);
		initTitle(getString(R.string.push_project));
		et_money=(EditText) findViewById(R.id.et_money);
		et_shooting_adress=(EditText) findViewById(R.id.et_shooting_adress);
		tv_openrobot_time=(TextView) findViewById(R.id.tv_openrobot_time);
		tv_finish_time=(TextView) findViewById(R.id.tv_finish_time);
		tv_finish_movie_time=(TextView) findViewById(R.id.tv_finish_movie_time);
		tv_show_time=(TextView) findViewById(R.id.tv_show_time);
		lin_openrobot_time=(LinearLayout) findViewById(R.id.lin_openrobot_time);
		lin_finish_time=(LinearLayout) findViewById(R.id.lin_finish_time);
		lin_finish_movie_time=(LinearLayout) findViewById(R.id.lin_finish_movie_time);
		lin_show_time=(LinearLayout) findViewById(R.id.lin_show_time);
		lin_show_time.setOnClickListener(this);
		lin_openrobot_time.setOnClickListener(this);
		lin_finish_time.setOnClickListener(this);
		lin_finish_movie_time.setOnClickListener(this);
		btn_tonext=(Button) findViewById(R.id.btn_tonext);
		btn_tonext.setOnClickListener(this);
		mproductid=getIntent().getStringExtra("mproductid");
		userid=getIntent().getStringExtra("userid");
		version=getIntent().getStringExtra("version");
		tv_openrobot_time.setTag("");
		tv_finish_time.setTag("");
		tv_finish_movie_time.setTag("");
		tv_show_time.setTag("");
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_tonext:
			if (mproductid!=null) 
				requstProductThird();
			break;
		case R.id.lin_openrobot_time:
			ChangeProjectTimeDialog cDialog=new ChangeProjectTimeDialog(SecondPushWorksActivity.this);
			Window dialogWindow = cDialog.getWindow();
	        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
	        dialogWindow.setGravity(Gravity.BOTTOM);
	        lp.width = LayoutParams.MATCH_PARENT; // 宽度
	        lp.height = LayoutParams.WRAP_CONTENT; // 高度
	        dialogWindow.setAttributes(lp);
	        dialogWindow.setWindowAnimations(R.style.dialog_bottom_style);
			cDialog.show();
			cDialog.setTimeListener(new OnTimeListener() {
				
				@Override
				public void onClick(String year, String month, String day) {
					tv_openrobot_time.setText(year+"年"+month+"月");
					tv_openrobot_time.setTag(year+"-"+month+"-"+day);
				}
			});
			break;
		case R.id.lin_finish_time:
			ChangeProjectTimeDialog cDialog1=new ChangeProjectTimeDialog(SecondPushWorksActivity.this);
			Window dialogWindow1 = cDialog1.getWindow();
	        WindowManager.LayoutParams lp1 = dialogWindow1.getAttributes();
	        dialogWindow1.setGravity(Gravity.BOTTOM);
	        lp1.width = LayoutParams.MATCH_PARENT; // 宽度
	        lp1.height = LayoutParams.WRAP_CONTENT; // 高度
	        dialogWindow1.setAttributes(lp1);
	        dialogWindow1.setWindowAnimations(R.style.dialog_bottom_style);
			cDialog1.show();
			cDialog1.setTimeListener(new OnTimeListener() {
				
				@Override
				public void onClick(String year, String month, String day) {
					tv_finish_time.setText(year+"年"+month+"月");
					tv_finish_time.setTag(year+"-"+month+"-"+day);
				}
			});
			break;
		case R.id.lin_finish_movie_time:
			ChangeProjectTimeDialog cDialog2=new ChangeProjectTimeDialog(SecondPushWorksActivity.this);
			Window dialogWindow2 = cDialog2.getWindow();
	        WindowManager.LayoutParams lp2 = dialogWindow2.getAttributes();
	        dialogWindow2.setGravity(Gravity.BOTTOM);
	        lp2.width = LayoutParams.MATCH_PARENT; // 宽度
	        lp2.height = LayoutParams.WRAP_CONTENT; // 高度
	        dialogWindow2.setAttributes(lp2);
	        dialogWindow2.setWindowAnimations(R.style.dialog_bottom_style);
			cDialog2.show();
			cDialog2.setTimeListener(new OnTimeListener() {
				
				@Override
				public void onClick(String year, String month, String day) {
					tv_finish_movie_time.setText(year+"年"+month+"月");
					tv_finish_movie_time.setTag(year+"-"+month+"-"+day);
				}
			});
			break;
		case R.id.lin_show_time:
			ChangeProjectTimeDialog cDialog3=new ChangeProjectTimeDialog(SecondPushWorksActivity.this);
			Window dialogWindow3 = cDialog3.getWindow();
	        WindowManager.LayoutParams lp3 = dialogWindow3.getAttributes();
	        dialogWindow3.setGravity(Gravity.BOTTOM);
	        lp3.width = LayoutParams.MATCH_PARENT; // 宽度
	        lp3.height = LayoutParams.WRAP_CONTENT; // 高度
	        dialogWindow3.setAttributes(lp3);
	        dialogWindow3.setWindowAnimations(R.style.dialog_bottom_style);
			cDialog3.show();
			cDialog3.setTimeListener(new OnTimeListener() {
				
				@Override
				public void onClick(String year, String month, String day) {
					tv_show_time.setText(year+"年"+month+"月");
					tv_show_time.setTag(year+"-"+month+"-"+day);
				}
			});
			break;
		default:
			break;
		}
	}
	
	private void requstProductThird(){
		String budget="20";
		final String shootingtime="2016-01-30";
		final String wraptime =tv_finish_time.getTag().toString();
		String completetime=tv_finish_movie_time.getTag().toString();
		String showtime=tv_show_time.getTag().toString();
		String location=et_shooting_adress.getText().toString();
		String movietime=CheckUtils.checkMovieTime(showtime, completetime, wraptime, shootingtime);
	/*	if (budget==null || budget.length()<1) {
			Toast.makeText(SecondPushWorksActivity.this, "您还未填写投资预算", Toast.LENGTH_SHORT).show();
			return;
		}else*/
		if (!movietime.equals("true")) {
			Toast.makeText(SecondPushWorksActivity.this, movietime, Toast.LENGTH_SHORT).show();
			return;
		}else if (location==null || location.length()<1) {
			Toast.makeText(SecondPushWorksActivity.this, "您还未填写拍摄地点", Toast.LENGTH_SHORT).show();
			return;
		}
		showLoadingDialog();
		RequestManger.postPushProductThird(SecondPushWorksActivity.this, userid, mproductid, Long.parseLong(budget)*10000, shootingtime, wraptime, completetime, showtime, location,version, new Listener<RequestCall>() {

			@Override
			public void onResponse(RequestCall response) {
				dismissLoadingDialog();
				if (response.getParser().getResultSuccess()) {
					Intent intent=new Intent(SecondPushWorksActivity.this,CreativeTeamActivity.class);
					intent.putExtra("wraptime", wraptime);
					intent.putExtra("shootingtime", shootingtime);
					intent.putExtra("mproductid", mproductid);
					startActivityForResult(intent, TONEXT);
				}else {
					String msg=response.getParser().getResponseMsg();
					if (msg!=null && msg.equals(getString(R.string.data_isold))) {
						Toast.makeText(SecondPushWorksActivity.this, "更新数据，请稍后再提交", Toast.LENGTH_SHORT).show();
						errorVesion();
					}else if (msg!=null) {
						Toast.makeText(SecondPushWorksActivity.this, msg, Toast.LENGTH_SHORT).show();
					}
				}
			}
		}, errorListener);
	}
	
	
	private void errorVesion(){
		if (mproductid!=null && mproductid.length()>1) {
			showLoadingDialog();
			RequestManger.getBaseProject(SecondPushWorksActivity.this, mproductid, userid, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					dismissLoadingDialog();
					try {
						JSONObject jsonObject=new JSONObject(response.getParser().getData());
						version=jsonObject.isNull("version")?"":jsonObject.getString("version");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, errorListener);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	   super.onActivityResult(requestCode, resultCode, data);
	   	if (requestCode==TONEXT && resultCode==RESULT_OK) {
	   		Intent backdata=new Intent();
			setResult(RESULT_OK,backdata);
	   		SecondPushWorksActivity.this.finish();
	   	}
	 }
}
