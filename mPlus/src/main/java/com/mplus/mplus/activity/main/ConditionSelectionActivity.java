
package com.mplus.mplus.activity.main;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.common.DictStringPaser;
import com.mplus.mplus.utils.MultipleTextUtils;
import com.mplus.mplus.utils.StringUtils;
import com.mplus.mplus.view.multipletext.MultipleTextViewGroup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ConditionSelectionActivity extends BaseActivity implements
		OnClickListener {
	private LinearLayout condition_select_height, condition_select_weight,
			condition_select_age;
	private TextView condition_select_heightvalue_tv,
			condition_select_weightvalue_tv, select_sure_tv, select_concel_tv,
			condition_select_agevalue_tv;
	private MultipleTextViewGroup multipleText;
	private RadioGroup gender_rg;
	private RadioButton male_rb, female_rb;
	private String sex = "", age = "", height = "", weight = "";
	private MultipleTextUtils multipleUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_condition_selection);
		Window dialogWindow = this.getWindow();
		WindowManager.LayoutParams p = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.RIGHT);
		p.width = (int) (MPlusApplication.Width * 0.8); // 宽度设置为屏幕的0.8
		p.height = (int) (MPlusApplication.Height);
		dialogWindow.setAttributes(p);
		select_concel_tv = (TextView) findViewById(R.id.select_concel_tv);
		select_sure_tv = (TextView) findViewById(R.id.select_sure_tv);
		multipleText = (MultipleTextViewGroup) findViewById(R.id.main_rl);
		gender_rg = (RadioGroup) findViewById(R.id.gender_rg);
		male_rb = (RadioButton) findViewById(R.id.male_rb);
		female_rb = (RadioButton) findViewById(R.id.female_rb);
//		no_rb = (RadioButton) findViewById(R.id.no_rb);
		condition_select_weightvalue_tv = (TextView) findViewById(R.id.condition_select_weightvalue_tv);
		condition_select_heightvalue_tv = (TextView) findViewById(R.id.condition_select_heightvalue_tv);
		condition_select_agevalue_tv = (TextView) findViewById(R.id.condition_select_agevalue_tv);
		condition_select_height = (LinearLayout) findViewById(R.id.condition_select_height);
		condition_select_weight = (LinearLayout) findViewById(R.id.condition_select_weight);
		condition_select_age = (LinearLayout) findViewById(R.id.condition_select_age);
		condition_select_weight.setOnClickListener(this);
		condition_select_height.setOnClickListener(this);
		condition_select_age.setOnClickListener(this);
		select_sure_tv.setOnClickListener(this);
		select_concel_tv.setOnClickListener(this);
		condition_select_age.setOnClickListener(this);
		initView();
		gender_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == male_rb.getId()) {
					sex = "男";
				} else if (checkedId == female_rb.getId()) {
					sex = "女";
				} else {
					sex = "";
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.condition_select_weight:
			Intent intent = new Intent(ConditionSelectionActivity.this,ConditionSelectionOtherActivity.class);
			intent.putExtra("tag", "14");
			startActivityForResult(intent, 14);
			// 第一个参数为启动时动画效果，第二个参数为退出时动画效果
			overridePendingTransition(0, R.anim.in_from_right);
			break;

		case R.id.condition_select_height:
			Intent ih = new Intent(ConditionSelectionActivity.this,ConditionSelectionOtherActivity.class);
			ih.putExtra("tag", "8");
			startActivityForResult(ih, 8);
			// 第一个参数为启动时动画效果，第二个参数为退出时动画效果
			overridePendingTransition(0, R.anim.in_from_right);
			break;
		case R.id.condition_select_age:
			Intent ia = new Intent(ConditionSelectionActivity.this,ConditionSelectionOtherActivity.class);
			ia.putExtra("tag", "15");
			startActivityForResult(ia, 15);
			// 第一个参数为启动时动画效果，第二个参数为退出时动画效果
			overridePendingTransition(0, R.anim.in_from_right);
			break;
		case R.id.select_sure_tv:
			Intent intentsure = new Intent();
			// 把返回数据存入Intent
			Bundle bundle=new Bundle();
			bundle.putString("sex",sex);
			bundle.putString("age",age );
			bundle.putString("height",height );
			bundle.putString("weight", weight);
			bundle.putString("tag",  StringUtils.ListToString(multipleUtils.getSelcedTag()));
			intentsure.putExtras(bundle);
			// 设置返回数据
			ConditionSelectionActivity.this.setResult(RESULT_OK, intentsure);
			// 关闭Activity
			ConditionSelectionActivity.this.finish();
			break;
		case R.id.select_concel_tv:
			ConditionSelectionActivity.this.finish();
			break;
		default:
			break;
		}

	}

	// 回调方法，从第二个页面回来的时候会执行这个方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 根据上面发送过去的请求吗来区别
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 14:
				String result = data.getExtras().getString("result");// 得到新Activity
																		// 关闭后返回的数据
				condition_select_weightvalue_tv.setText(result);
				weight=result;
				break;
			case 8:
				String result2 = data.getExtras().getString("result");// 得到新Activity
																		// 关闭后返回的数据
				condition_select_heightvalue_tv.setText(result2);
				height=result2;
				break;
			case 15:
				String result3 = data.getExtras().getString("result");// 得到新Activity
																		// 关闭后返回的数据
				condition_select_agevalue_tv.setText(result3);
				age=result3;
				break;
			default:
				break;
			}
		}
	}

	
	
	
	private void initView(){
		Intent intent=getIntent();
		sex=intent.getStringExtra("sex");
		age=intent.getStringExtra("age");
		height=intent.getStringExtra("height");
		weight=intent.getStringExtra("weight");
		String tag=intent.getStringExtra("tag");
		final ArrayList<String> tags=StringUtils.StringToList(tag);
		if (!TextUtils.isEmpty(sex)) {
			if (sex.equals("男")) {
				male_rb.setChecked(true);
			}else if (sex.equals("女")) {
				female_rb.setChecked(true);
			}
		}
		condition_select_agevalue_tv.setText(TextUtils.isEmpty(age)?"":age);
		condition_select_heightvalue_tv.setText(TextUtils.isEmpty(height)?"":height);
		condition_select_weightvalue_tv.setText(TextUtils.isEmpty(weight)?"":weight);
		ArrayList<String> arrayListtag = DictStringPaser.GetInstance(6);
			if (arrayListtag == null || arrayListtag.size() < 1) {
				RequestManger.GetDictData(ConditionSelectionActivity.this, 6,
						new DictStringPaser(6), new Listener<RequestCall>() {
							@Override
							public void onResponse(RequestCall response) {
								if (response.getParser().getResultSuccess()) {
									ArrayList<String> arrayList = DictStringPaser.GetInstance(6);
									multipleUtils=new MultipleTextUtils(multipleText, arrayList,tags);
									multipleUtils.AddTagWithSelced(ConditionSelectionActivity.this);
								}
							}
						}, errorListener);
			} else {
				multipleUtils=new MultipleTextUtils(multipleText, arrayListtag,tags);
				multipleUtils.AddTagWithSelced(ConditionSelectionActivity.this);
			}
	}
	
}
