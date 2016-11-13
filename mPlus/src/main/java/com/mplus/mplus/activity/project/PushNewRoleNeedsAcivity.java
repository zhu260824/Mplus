package com.mplus.mplus.activity.project;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.main.PersonSelectActivity;
import com.mplus.mplus.adapter.pushproject.TraitSelcedAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.dialog.ChangeProjectTimeBackgoupDialog;
import com.mplus.mplus.dialog.ChangeProjectTimeBackgoupDialog.OnConstellationListener;
import com.mplus.mplus.dialog.ChangeProjectTimeDialog;
import com.mplus.mplus.dialog.ChangeProjectTimeDialog.OnTimeListener;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorTag;
import com.mplus.mplus.paser.actor.ActorTagPaser;
import com.mplus.mplus.paser.common.DictStringPaser;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.paser.pushproject.RoleType;
import com.mplus.mplus.utils.CheckUtils;
import com.mplus.mplus.utils.DialongUtils;
import com.mplus.mplus.view.MyDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 发布新角色
 * 
 * 
 * 两个跳转没有弄 164行417行
 *
 */
public class PushNewRoleNeedsAcivity extends BaseActivity implements OnClickListener {
	private static final int SELCEDROLE = 1;
	private static final int SELCEDPERSON = 2;
	private LinearLayout lin_role_tag, lin_role, lin_start_time, lin_end_time,
			lin_height,lin_age,lin_weight,lin_need_experience;
	private TextView  tv_role, tv_start_time, tv_end_time, tv_height,tv_role_tag,tv_unit,tv_age,tv_weight;
	private ImageView iv_need_experience;
	private EditText et_depict, et_paycheck,et_nickname;
	private Button btn_push, btn_apply;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push_newrole_needs);
		initTitle("角色要求");
		et_depict = (EditText) findViewById(R.id.et_depict);
		tv_role = (TextView) findViewById(R.id.tv_role);
		tv_role_tag = (TextView) findViewById(R.id.tv_role_tag);
		tv_start_time = (TextView) findViewById(R.id.tv_start_time);
		tv_end_time = (TextView) findViewById(R.id.tv_end_time);
//		tv_close_time = (TextView) findViewById(R.id.tv_close_time);
		tv_height = (TextView) findViewById(R.id.tv_height);
		et_paycheck = (EditText) findViewById(R.id.et_paycheck);
		tv_age=(TextView)findViewById(R.id.tv_age);//新功能添加年龄
		tv_weight=(TextView)findViewById(R.id.tv_weight);//新功能添加体重
		et_nickname = (EditText) findViewById(R.id.et_nickname);
//		et_likecustion = (EditText) findViewById(R.id.et_likecustion);
		iv_need_experience = (ImageView) findViewById(R.id.iv_need_experience);
		lin_role = (LinearLayout) findViewById(R.id.lin_role);
		lin_role_tag = (LinearLayout) findViewById(R.id.lin_role_tag);
		lin_start_time = (LinearLayout) findViewById(R.id.lin_start_time);
		lin_end_time = (LinearLayout) findViewById(R.id.lin_end_time);
		lin_age=(LinearLayout)findViewById(R.id.lin_age);//新版本添加年龄
		lin_weight=(LinearLayout)findViewById(R.id.lin_weight);//新功能添加体重
		lin_need_experience = (LinearLayout) findViewById(R.id.lin_need_experience);
//		lin_close_time = (LinearLayout) findViewById(R.id.lin_close_time);
		lin_height = (LinearLayout) findViewById(R.id.lin_height);
		btn_push = (Button) findViewById(R.id.btn_push);
		btn_apply = (Button) findViewById(R.id.btn_apply);
		tv_unit = (TextView) findViewById(R.id.tv_unit);
		lin_role.setOnClickListener(this);
		lin_role_tag.setOnClickListener(this);
		lin_start_time.setOnClickListener(this);
		lin_end_time.setOnClickListener(this);
		lin_need_experience.setOnClickListener(this);
//		lin_close_time.setOnClickListener(this);
		lin_height.setOnClickListener(this);
		btn_push.setOnClickListener(this);
		btn_apply.setOnClickListener(this);
		lin_weight.setOnClickListener(this);
		lin_age.setOnClickListener(this);
		tv_start_time.setTag("");
		tv_end_time.setTag("");
//		tv_close_time.setTag("");
		iv_need_experience.setTag(false);
		et_paycheck.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				if (s.length() == 0) {
					et_paycheck.setText("面议");
				} else {
					tv_unit.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			public void afterTextChanged(Editable s) {
				if (s.toString().equals("面议")) {
					tv_unit.setVisibility(View.GONE);
					setEditTextCursorLocation(et_paycheck);
				}
			}
		});
		tv_age.setText("不限制");
		tv_height.setText("不限制");
		tv_weight.setText("不限制");
	}

	public void setEditTextCursorLocation(EditText editText){
		CharSequence text=editText.getText();
		if(text instanceof Spannable){
			Spannable spanText=(Spannable)text;
			Selection.setSelection(spanText, text.length());
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lin_role:
			Intent intent = new Intent(PushNewRoleNeedsAcivity.this, RoleSelcedActivity.class);
			intent.putExtra("intenttype", 2);
			startActivityForResult(intent, SELCEDROLE);
			;
			break;
		case R.id.lin_role_tag:
			showRoleTag(PushNewRoleNeedsAcivity.this);
			break;
		case R.id.lin_start_time:
			ChangeProjectTimeDialog cDialog = new ChangeProjectTimeDialog(PushNewRoleNeedsAcivity.this);
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
					tv_start_time.setText(year + "年" + month + "月");
					tv_start_time.setTag(year + "-" + month + "-" + day);
				}
			});
			break;
		case R.id.lin_end_time:
			ChangeProjectTimeDialog cDialog2 = new ChangeProjectTimeDialog(PushNewRoleNeedsAcivity.this);
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
					tv_end_time.setText(year + "年" + month + "月");
					tv_end_time.setTag(year + "-" + month + "-" + day);
				}
			});
			break;
		case R.id.lin_need_experience:
			boolean isexperience = (Boolean) iv_need_experience.getTag();
			if (isexperience) {
				iv_need_experience.setImageResource(R.drawable.checkbox_off);
				iv_need_experience.setTag(false);
			} else {
				iv_need_experience.setImageResource(R.drawable.checkbox_on);
				iv_need_experience.setTag(true);
			}
			break;
//		case R.id.lin_close_time:
//			ChangeProjectTimeDialog cDialog3 = new ChangeProjectTimeDialog(PushNewRoleNeedsAcivity.this);
//			Window dialogWindow3 = cDialog3.getWindow();
//			WindowManager.LayoutParams lp3 = dialogWindow3.getAttributes();
//			dialogWindow3.setGravity(Gravity.BOTTOM);
//			lp3.width = LayoutParams.MATCH_PARENT; // 宽度
//			lp3.height = LayoutParams.WRAP_CONTENT; // 高度
//			dialogWindow3.setAttributes(lp3);
//			dialogWindow3.setWindowAnimations(R.style.dialog_bottom_style);
//			cDialog3.show();
//			cDialog3.setTimeListener(new OnTimeListener() {
//
//				@Override
//				public void onClick(String year, String month, String day) {
//					tv_close_time.setText(year + "年" + month + "月");
//					tv_close_time.setTag(year + "-" + month + "-" + day);
//					IsAllNotNull();
//				}
//			});
//			break;
		case R.id.lin_height:
			ArrayList<String> heightarrayList = DictStringPaser.GetInstance(8);
			if (heightarrayList == null || heightarrayList.size() < 1) {
				RequestManger.GetDictData(PushNewRoleNeedsAcivity.this, 8, new DictStringPaser(8),
						new Listener<RequestCall>() {

							@Override
							public void onResponse(RequestCall response) {
								if (response.getParser().getResultSuccess()) {
									ArrayList<String> heightarrayList2 = DictStringPaser.GetInstance(8);
									ChangeProjectTimeBackgoupDialog heightcDialog = new ChangeProjectTimeBackgoupDialog(
											PushNewRoleNeedsAcivity.this, heightarrayList2);
									Window heightdialogWindow = heightcDialog.getWindow();
									WindowManager.LayoutParams heightlp = heightdialogWindow.getAttributes();
									heightdialogWindow.setGravity(Gravity.BOTTOM);
									heightlp.width = LayoutParams.MATCH_PARENT; // 宽度
									heightlp.height = LayoutParams.WRAP_CONTENT; // 高度
									heightdialogWindow.setAttributes(heightlp);
									heightdialogWindow.setWindowAnimations(R.style.dialog_bottom_style);
									heightcDialog.show();
									heightcDialog.setConstellationListener(new OnConstellationListener() {

										@Override
										public void onClick(String constellation) {
											tv_height.setText(constellation);
										}
									});
								}
							}
						}, errorListener);
			} else {
				ChangeProjectTimeBackgoupDialog heightcDialog4 = new ChangeProjectTimeBackgoupDialog(
						PushNewRoleNeedsAcivity.this, heightarrayList);
				Window heightdialogWindow4 = heightcDialog4.getWindow();
				WindowManager.LayoutParams lp4 = heightdialogWindow4.getAttributes();
				heightdialogWindow4.setGravity(Gravity.BOTTOM);
				lp4.width = LayoutParams.MATCH_PARENT; // 宽度
				lp4.height = LayoutParams.WRAP_CONTENT; // 高度
				heightdialogWindow4.setAttributes(lp4);
				heightdialogWindow4.setWindowAnimations(R.style.dialog_bottom_style);
				heightcDialog4.show();
				heightcDialog4.setConstellationListener(new OnConstellationListener() {

					public void onClick(String constellation) {
						tv_height.setText(constellation);
					}
				});
				RequestManger.GetDictData(PushNewRoleNeedsAcivity.this, 8, new DictStringPaser(8),
						new Listener<RequestCall>() {

							@Override
							public void onResponse(RequestCall response) {
							}
						}, errorListener);
			}
			break;
		case R.id.lin_age:
			ArrayList<String> ageArrayList = DictStringPaser.GetInstance(15);
			if (ageArrayList == null || ageArrayList.size() < 1) {
				RequestManger.GetDictData(PushNewRoleNeedsAcivity.this, 15, new DictStringPaser(15),
						new Listener<RequestCall>() {

							public void onResponse(RequestCall response) {
								if (response.getParser().getResultSuccess()) {
									ArrayList<String> agearrayList = DictStringPaser.GetInstance(15);
									ChangeProjectTimeBackgoupDialog agecDialog = new ChangeProjectTimeBackgoupDialog(
											PushNewRoleNeedsAcivity.this, agearrayList);
									Window agedialogWindow = agecDialog.getWindow();
									WindowManager.LayoutParams agelp = agedialogWindow.getAttributes();
									agedialogWindow.setGravity(Gravity.BOTTOM);
									agelp.width = LayoutParams.MATCH_PARENT; // 宽度
									agelp.height = LayoutParams.WRAP_CONTENT; // 高度
									agedialogWindow.setAttributes(agelp);
									agedialogWindow.setWindowAnimations(R.style.dialog_bottom_style);
									agecDialog.show();
									agecDialog.setConstellationListener(new OnConstellationListener() {

										public void onClick(String constellation) {
											tv_age.setText(constellation);
										}
									});
								}
							}
						}, errorListener);
			} else {
				ChangeProjectTimeBackgoupDialog agecDialog4 = new ChangeProjectTimeBackgoupDialog(
						PushNewRoleNeedsAcivity.this, ageArrayList);
				Window agedialogWindow4 = agecDialog4.getWindow();
				WindowManager.LayoutParams agelp4 = agedialogWindow4.getAttributes();
				agedialogWindow4.setGravity(Gravity.BOTTOM);
				agelp4.width = LayoutParams.MATCH_PARENT; // 宽度
				agelp4.height = LayoutParams.WRAP_CONTENT; // 高度
				agedialogWindow4.setAttributes(agelp4);
				agedialogWindow4.setWindowAnimations(R.style.dialog_bottom_style);
				agecDialog4.show();
				agecDialog4.setConstellationListener(new OnConstellationListener() {
					public void onClick(String constellation) {
						tv_age.setText(constellation);
					}
				});
				RequestManger.GetDictData(PushNewRoleNeedsAcivity.this, 15, new DictStringPaser(15),
						new Listener<RequestCall>() {
							public void onResponse(RequestCall response) {
							}
						}, errorListener);
			}
			break;
		case R.id.lin_weight:
			ArrayList<String> weightArrayList = DictStringPaser.GetInstance(14);
			if (weightArrayList == null || weightArrayList.size() < 1) {
				RequestManger.GetDictData(PushNewRoleNeedsAcivity.this, 14, new DictStringPaser(14),
						new Listener<RequestCall>() {

							public void onResponse(RequestCall response) {
								if (response.getParser().getResultSuccess()) {
									ArrayList<String> weightarrayList = DictStringPaser.GetInstance(14);
									ChangeProjectTimeBackgoupDialog weightDialog = new ChangeProjectTimeBackgoupDialog(
											PushNewRoleNeedsAcivity.this, weightarrayList);
									Window weightdialogWindow = weightDialog.getWindow();
									WindowManager.LayoutParams weightlp = weightdialogWindow.getAttributes();
									weightdialogWindow.setGravity(Gravity.BOTTOM);
									weightlp.width = LayoutParams.MATCH_PARENT; // 宽度
									weightlp.height = LayoutParams.WRAP_CONTENT; // 高度
									weightdialogWindow.setAttributes(weightlp);
									weightdialogWindow.setWindowAnimations(R.style.dialog_bottom_style);
									weightDialog.show();
									weightDialog.setConstellationListener(new OnConstellationListener() {

										public void onClick(String constellation) {
											tv_weight.setText(constellation);
										}
									});
								}
							}
						}, errorListener);
			} else {
				ChangeProjectTimeBackgoupDialog weightDialog4 = new ChangeProjectTimeBackgoupDialog(
						PushNewRoleNeedsAcivity.this, weightArrayList);
				Window weightdialogWindow4 = weightDialog4.getWindow();
				WindowManager.LayoutParams weightlp4 = weightdialogWindow4.getAttributes();
				weightdialogWindow4.setGravity(Gravity.BOTTOM);
				weightlp4.width = LayoutParams.MATCH_PARENT; // 宽度
				weightlp4.height = LayoutParams.WRAP_CONTENT; // 高度
				weightdialogWindow4.setAttributes(weightlp4);
				weightdialogWindow4.setWindowAnimations(R.style.dialog_bottom_style);
				weightDialog4.show();
				weightDialog4.setConstellationListener(new OnConstellationListener() {
					public void onClick(String constellation) {
						tv_weight.setText(constellation);
					}
				});
				RequestManger.GetDictData(PushNewRoleNeedsAcivity.this, 14, new DictStringPaser(14),
						new Listener<RequestCall>() {
							public void onResponse(RequestCall response) {
							}
						}, errorListener);
			}
			break;
			//点击发布新角色
		case R.id.btn_push:
			pushNewJob(1);
			break;
			//添加别选人
		case R.id.btn_apply:
			pushNewJob(2);
			break;
		default:
			break;
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SELCEDROLE && resultCode == RESULT_OK) {
			RoleType roleType = (RoleType) data.getExtras().get("data");
			if (roleType != null) {
				tv_role.setText(roleType.role);
				setChangeRole();
				if (roleType.type.equals("0")) {
					lin_role_tag.setVisibility(View.GONE);
					lin_height.setVisibility(View.GONE);
					lin_weight.setVisibility(View.GONE);
					lin_age.setVisibility(View.GONE);
				} else {
					lin_role_tag.setVisibility(View.VISIBLE);
					lin_height.setVisibility(View.VISIBLE);
					lin_weight.setVisibility(View.VISIBLE);
					lin_age.setVisibility(View.VISIBLE);
				}
			}
		} else if (requestCode == SELCEDPERSON && resultCode == RESULT_OK) {
			PushNewRoleNeedsAcivity.this.finish();
		}
	}

	private ArrayList<ActorTag> roletags;
	private  TraitSelcedAdapter rAdapter;

	private void showRoleTag(final Context context) {
		View view = View.inflate(context, R.layout.dialog_selced_role_tag, null);
		final MyDialog dialog = new MyDialog(context, R.style.CustomDialog);
		LinearLayout.LayoutParams dialoglp = new LinearLayout.LayoutParams(MPlusApplication.Width * 4 / 5,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		dialog.addContentView(view, dialoglp);
		dialog.setCancelable(true);
		final ListView lv = (ListView) view.findViewById(R.id.lv);
		TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
		TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
		roletags = ActorTagPaser.GetInstance(6);
		if (roletags == null || roletags.size() < 1) {
			RequestManger.GetDictData(context, 6, new ActorTagPaser(6), new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					roletags = ActorTagPaser.GetInstance(6);
					rAdapter = new TraitSelcedAdapter(PushNewRoleNeedsAcivity.this, roletags, false, 1);
					lv.setAdapter(rAdapter);
				}
			}, errorListener);
		} else {
			 rAdapter = new TraitSelcedAdapter(PushNewRoleNeedsAcivity.this,roletags, false,1);
			lv.setAdapter(rAdapter);
			RequestManger.GetDictData(context, 6, new ActorTagPaser(6), new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {

				}
			}, errorListener);

		}
		dialog.show();
		tv_sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv_role_tag.setText(rAdapter.getSelcedList());
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
			}
		});
		tv_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
	private void pushNewJob(final int type){
		String role = tv_role.getText().toString();
		String feature = tv_role_tag.getText().toString();
		String starttime = tv_start_time.getTag().toString();
		String endtime = tv_end_time.getTag().toString();
//		String closetime = tv_close_time.getTag().toString();
//		String analogy = et_likecustion.getText().toString();
		String nickname = et_nickname.getText().toString();
		String desc = et_depict.getText().toString();
		String mproductid = getIntent().getStringExtra("mproductid");
		boolean isexperience1 = (Boolean) iv_need_experience.getTag();
		String height = tv_height.getText().toString();
		String paycheck = et_paycheck.getText().toString();
		String age = tv_age.getText().toString().trim();
		String weight = tv_weight.getText().toString().trim();
		String needRelatedexp = "0";
		String checkrolestime = CheckUtils.checkNewRolesTime(starttime, endtime);
		if (isexperience1) {
			needRelatedexp = "1";
		} else {
			needRelatedexp = "0";
		}
		if (role == null || role.length() < 1) {
			Toast.makeText(PushNewRoleNeedsAcivity.this, "请选择角色", Toast.LENGTH_SHORT).show();
			return;
		} 
		/*else if (nickname == null || nickname.length() < 1) {
			Toast.makeText(PushNewRoleNeedsAcivity.this, "请填写剧中称谓", Toast.LENGTH_SHORT).show();
			return;
		} */
		else if (!checkrolestime.equals("true")) {
			Toast.makeText(PushNewRoleNeedsAcivity.this, checkrolestime, Toast.LENGTH_SHORT).show();
			return;
		} else if (desc == null || desc.length() < 1) {
			Toast.makeText(PushNewRoleNeedsAcivity.this, "请填写人物小传", Toast.LENGTH_SHORT).show();
			return;
//		} else if (closetime == null || closetime.length() < 1) {
//			Toast.makeText(PushNewRoleNeedsAcivity.this, "请填写截止时间", Toast.LENGTH_SHORT).show();
//			return;
		} else {
			showLoadingDialog();
			RequestManger.postPushProductJobSecond(PushNewRoleNeedsAcivity.this,UserPaser.GetInstance().userid,
					mproductid, role, feature, starttime, endtime, desc, needRelatedexp,
					height, paycheck, nickname,weight,age,null, new Listener<RequestCall>() {

						public void onResponse(RequestCall response) {
							dismissLoadingDialog();
							if (response.getParser().getResultSuccess()) {
								if (type==1) {
									setResult(RESULT_OK);
									PushNewRoleNeedsAcivity.this.finish();
								}else {
									try {
										JSONObject jsonObject=new JSONObject(response.getParser().getData());
										final String planid=jsonObject.isNull("planid")?"":jsonObject.getString("planid");
										if (!TextUtils.isEmpty(planid)) {
											DialongUtils.showSelcedType(PushNewRoleNeedsAcivity.this, new OnClickListener() {
												
												@Override
												public void onClick(View v) {
													Intent intent=new Intent(PushNewRoleNeedsAcivity.this,PersonSelectActivity.class);
													intent.putExtra("planid", planid);
													startActivity(intent);
													PushNewRoleNeedsAcivity.this.finish();
												}
											}, new OnClickListener() {
												
												@Override
												public void onClick(View v) {
													Intent intent=new Intent(PushNewRoleNeedsAcivity.this,CollectResultActivity.class);
													intent.putExtra("planid", planid);
													startActivity(intent);
													PushNewRoleNeedsAcivity.this.finish();
												}
											});
										}
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
							} else {
								if (response.getParser().getResponseMsg() != null)
									Toast.makeText(PushNewRoleNeedsAcivity.this,response.getParser().getResponseMsg(), Toast.LENGTH_SHORT).show();
							}
						}
					}, errorListener);
		}
	}
	
	
	private void setChangeRole(){
		et_nickname.setText("");
		et_paycheck.setText("");
		et_depict.setText("");
		tv_role_tag.setText("");
		tv_age.setText("不限制");
		tv_height.setText("不限制");
		tv_weight.setText("不限制");
		tv_start_time.setText("");
		tv_end_time.setText("");
	}
	
	
}

