package com.mplus.mplus.activity.main;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.actorcenter.PersonSelectResultActivity;
import com.mplus.mplus.activity.project.SearchResultActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.dialog.ChangeProjectTimeBackgoupDialog;
import com.mplus.mplus.dialog.ChangeProjectTimeBackgoupDialog.OnConstellationListener;
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
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PersonSelectActivity extends BaseActivity {
	private TextView person_select_left_tv, person_select_right_tv,
			person_search_tv, condition_select_heightvalue_tv,
			condition_select_weightvalue_tv, condition_select_agevalue_tv;
	private LinearLayout person_select_ly, person_search_ly,
			condition_select_height, condition_select_weight,
			condition_select_age;
	private Button person_select_btn;
	private RadioGroup gender_rg;
	private RadioButton male_rb, female_rb, no_rb;
	private MultipleTextViewGroup multipleText;
	private String sex = "", age = "", height = "", weight = "";
	private EditText name_et;
	private MultipleTextUtils multipleUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_select);
		initTitle("筛选搜索");
		initView();

	}

	private void initView() {
		person_select_left_tv = (TextView) findViewById(R.id.person_select_left_tv);
		person_select_right_tv = (TextView) findViewById(R.id.person_select_right_tv);
		person_search_tv = (TextView) findViewById(R.id.person_search_tv);
		multipleText = (MultipleTextViewGroup) findViewById(R.id.main_rl);
		name_et = (EditText) findViewById(R.id.name_et);
		gender_rg = (RadioGroup) findViewById(R.id.gender_rg);
		male_rb = (RadioButton) findViewById(R.id.male_rb);
		female_rb = (RadioButton) findViewById(R.id.female_rb);
		no_rb = (RadioButton) findViewById(R.id.no_rb);
		condition_select_heightvalue_tv = (TextView) findViewById(R.id.condition_select_heightvalue_tv);
		condition_select_weightvalue_tv = (TextView) findViewById(R.id.condition_select_weightvalue_tv);
		condition_select_agevalue_tv = (TextView) findViewById(R.id.condition_select_agevalue_tv);
		person_select_btn = (Button) findViewById(R.id.person_select_btn);
		person_search_ly = (LinearLayout) findViewById(R.id.person_search_ly);
		person_select_ly = (LinearLayout) findViewById(R.id.person_select_ly);

		condition_select_height = (LinearLayout) findViewById(R.id.condition_select_height);
		condition_select_weight = (LinearLayout) findViewById(R.id.condition_select_weight);
		condition_select_age = (LinearLayout) findViewById(R.id.condition_select_age);
		person_select_left_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				person_select_left_tv.setBackgroundResource(R.drawable.consensusmanger_letf_off);
				person_select_right_tv.setBackgroundResource(R.drawable.consensusmanger_right_on);
				person_select_ly.setVisibility(View.VISIBLE);
				person_select_btn.setVisibility(View.VISIBLE);
				person_search_ly.setVisibility(View.GONE);

			}
		});

		person_select_right_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				person_select_left_tv.setBackgroundResource(R.drawable.consensusmanger_letf_on);
				person_select_right_tv.setBackgroundResource(R.drawable.consensusmanger_right_off);
				person_select_ly.setVisibility(View.GONE);
				person_select_btn.setVisibility(View.GONE);
				person_search_ly.setVisibility(View.VISIBLE);
			}
		});
		final String planid=getIntent().getStringExtra("planid");
		final String mproductname=getIntent().getStringExtra("mproductname");
		final String planname=getIntent().getStringExtra("planname");
		
		person_search_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i;
				if (TextUtils.isEmpty(planid)) {
					 i = new Intent(PersonSelectActivity.this,PersonSelectResultActivity.class);
				}else {
					i = new Intent(PersonSelectActivity.this,SearchResultActivity.class);
					i.putExtra("planid", planid);
					i.putExtra("mproductname", mproductname);
					i.putExtra("planname",planname);
				}
				i.putExtra("sex", "");
				i.putExtra("age", "");
				i.putExtra("height", "");
				i.putExtra("weight", "");
				i.putExtra("tag", "");
				i.putExtra("key", name_et.getText().toString());
				i.putExtra("search", true);
				startActivity(i);			}
		});

		person_select_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i;
				if (TextUtils.isEmpty(planid)) {
					 i = new Intent(PersonSelectActivity.this,PersonSelectResultActivity.class);
				}else {
					i = new Intent(PersonSelectActivity.this,SearchResultActivity.class);
					i.putExtra("planid", planid);
					i.putExtra("mproductname", mproductname);
					i.putExtra("planname",planname);
				}
				i.putExtra("sex", sex);
				i.putExtra("age", age);
				i.putExtra("height", height);
				i.putExtra("weight", weight);
				if (multipleUtils!=null && multipleUtils.getSelcedTag()!=null) 
					i.putExtra("tag",StringUtils.ListToString(multipleUtils.getSelcedTag()));
				i.putExtra("key", "");
				i.putExtra("search", false);
				startActivity(i);
			}
		});

		condition_select_height.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<String> arrayList = DictStringPaser.GetInstance(8);
				if (arrayList == null || arrayList.size() < 1) {
					RequestManger.GetDictData(PersonSelectActivity.this, 8,new DictStringPaser(8),new Listener<RequestCall>() {

								@Override
								public void onResponse(RequestCall response) {
									if (response.getParser().getResultSuccess()) {
										ArrayList<String> arrayList = DictStringPaser
												.GetInstance(8);
										ChangeProjectTimeBackgoupDialog cDialog = new ChangeProjectTimeBackgoupDialog(
												PersonSelectActivity.this,
												arrayList);
										Window dialogWindow = cDialog
												.getWindow();
										WindowManager.LayoutParams lp = dialogWindow
												.getAttributes();
										dialogWindow.setGravity(Gravity.BOTTOM);
										lp.width = LayoutParams.MATCH_PARENT; // 宽度
										lp.height = LayoutParams.WRAP_CONTENT; // 高度
										dialogWindow.setAttributes(lp);
										dialogWindow
												.setWindowAnimations(R.style.dialog_bottom_style);
										cDialog.show();
										cDialog.setConstellationListener(new OnConstellationListener() {

											@Override
											public void onClick(
													String constellation) {
												height = constellation;
												condition_select_heightvalue_tv
														.setText(constellation);
											}
										});
									}
								}
							}, errorListener);
				} else {
					ChangeProjectTimeBackgoupDialog cDialog = new ChangeProjectTimeBackgoupDialog(
							PersonSelectActivity.this, arrayList);
					Window dialogWindow = cDialog.getWindow();
					WindowManager.LayoutParams lp = dialogWindow
							.getAttributes();
					dialogWindow.setGravity(Gravity.BOTTOM);
					lp.width = LayoutParams.MATCH_PARENT; // 宽度
					lp.height = LayoutParams.WRAP_CONTENT; // 高度
					dialogWindow.setAttributes(lp);
					dialogWindow
							.setWindowAnimations(R.style.dialog_bottom_style);
					cDialog.show();
					cDialog.setConstellationListener(new OnConstellationListener() {

						@Override
						public void onClick(String constellation) {
							condition_select_heightvalue_tv
									.setText(constellation);
							height = constellation;
						}
					});

				}
			}
		});

		condition_select_weight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<String> arrayList = DictStringPaser.GetInstance(14);
				if (arrayList == null || arrayList.size() < 1) {
					RequestManger.GetDictData(PersonSelectActivity.this, 14,
							new DictStringPaser(14),
							new Listener<RequestCall>() {

								@Override
								public void onResponse(RequestCall response) {
									if (response.getParser().getResultSuccess()) {
										ArrayList<String> arrayList = DictStringPaser
												.GetInstance(14);
										ChangeProjectTimeBackgoupDialog cDialog = new ChangeProjectTimeBackgoupDialog(
												PersonSelectActivity.this,
												arrayList);
										Window dialogWindow = cDialog
												.getWindow();
										WindowManager.LayoutParams lp = dialogWindow
												.getAttributes();
										dialogWindow.setGravity(Gravity.BOTTOM);
										lp.width = LayoutParams.MATCH_PARENT; // 宽度
										lp.height = LayoutParams.WRAP_CONTENT; // 高度
										dialogWindow.setAttributes(lp);
										dialogWindow
												.setWindowAnimations(R.style.dialog_bottom_style);
										cDialog.show();
										cDialog.setConstellationListener(new OnConstellationListener() {

											@Override
											public void onClick(
													String constellation) {
												condition_select_weightvalue_tv
														.setText(constellation);
												weight = constellation;
											}
										});
									}
								}
							}, errorListener);
				} else {
					ChangeProjectTimeBackgoupDialog cDialog = new ChangeProjectTimeBackgoupDialog(
							PersonSelectActivity.this, arrayList);
					Window dialogWindow = cDialog.getWindow();
					WindowManager.LayoutParams lp = dialogWindow
							.getAttributes();
					dialogWindow.setGravity(Gravity.BOTTOM);
					lp.width = LayoutParams.MATCH_PARENT; // 宽度
					lp.height = LayoutParams.WRAP_CONTENT; // 高度
					dialogWindow.setAttributes(lp);
					dialogWindow
							.setWindowAnimations(R.style.dialog_bottom_style);
					cDialog.show();
					cDialog.setConstellationListener(new OnConstellationListener() {

						@Override
						public void onClick(String constellation) {
							condition_select_weightvalue_tv
									.setText(constellation);
							weight = constellation;
						}
					});

				}
			}
		});

		condition_select_age.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ArrayList<String> arrayList = DictStringPaser.GetInstance(15);
				if (arrayList == null || arrayList.size() < 1) {
					RequestManger.GetDictData(PersonSelectActivity.this, 15,
							new DictStringPaser(15),
							new Listener<RequestCall>() {

								@Override
								public void onResponse(RequestCall response) {
									if (response.getParser().getResultSuccess()) {
										ArrayList<String> arrayList = DictStringPaser
												.GetInstance(15);
										ChangeProjectTimeBackgoupDialog cDialog = new ChangeProjectTimeBackgoupDialog(
												PersonSelectActivity.this,
												arrayList);
										Window dialogWindow = cDialog
												.getWindow();
										WindowManager.LayoutParams lp = dialogWindow
												.getAttributes();
										dialogWindow.setGravity(Gravity.BOTTOM);
										lp.width = LayoutParams.MATCH_PARENT; // 宽度
										lp.height = LayoutParams.WRAP_CONTENT; // 高度
										dialogWindow.setAttributes(lp);
										dialogWindow
												.setWindowAnimations(R.style.dialog_bottom_style);
										cDialog.show();
										cDialog.setConstellationListener(new OnConstellationListener() {

											@Override
											public void onClick(
													String constellation) {
												condition_select_agevalue_tv
														.setText(constellation);
												age = constellation;
											}
										});
									}
								}
							}, errorListener);
				} else {
					ChangeProjectTimeBackgoupDialog cDialog = new ChangeProjectTimeBackgoupDialog(
							PersonSelectActivity.this, arrayList);
					Window dialogWindow = cDialog.getWindow();
					WindowManager.LayoutParams lp = dialogWindow
							.getAttributes();
					dialogWindow.setGravity(Gravity.BOTTOM);
					lp.width = LayoutParams.MATCH_PARENT; // 宽度
					lp.height = LayoutParams.WRAP_CONTENT; // 高度
					dialogWindow.setAttributes(lp);
					dialogWindow
							.setWindowAnimations(R.style.dialog_bottom_style);
					cDialog.show();
					cDialog.setConstellationListener(new OnConstellationListener() {

						@Override
						public void onClick(String constellation) {
							condition_select_agevalue_tv.setText(constellation);
							age = constellation;
						}
					});

				}
			}
		});

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

		// tag
		ArrayList<String> arrayListtag = DictStringPaser.GetInstance(13);
		if (arrayListtag == null || arrayListtag.size() < 1) {
			RequestManger.GetDictData(PersonSelectActivity.this, 13,
					new DictStringPaser(13), new Listener<RequestCall>() {
						@Override
						public void onResponse(RequestCall response) {
							if (response.getParser().getResultSuccess()) {
								ArrayList<String> arrayList = DictStringPaser.GetInstance(13);
								multipleUtils=new MultipleTextUtils(multipleText, arrayList);
								multipleUtils.AddTagWithSelced(PersonSelectActivity.this);

							}
						}
					}, errorListener);
		} else {
			multipleUtils=new MultipleTextUtils(multipleText, arrayListtag);
			multipleUtils.AddTagWithSelced(PersonSelectActivity.this);
		}
	}

	
	
	
	
}
