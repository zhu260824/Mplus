package com.mplus.mplus.activity.login;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.dtd365.library.tools.SerializableFactory;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.common.UploadPicturesActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.dialog.ChangeAddressDialog;
import com.mplus.mplus.dialog.ChangeAddressDialog.OnAddressCListener;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.manger.SaveType;
import com.mplus.mplus.utils.CheckUtils;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;

/**
 * @Title: 个人设置
 * @Description:
 * @author zhaochaoyue
 * @date 2015年8月18日
 */
public class PersonalDataActivity extends BaseActivity implements OnClickListener {
	private static final int CHANGEPIC = 1;
//	private LinearLayout lin_adress;
	private TextView tv_setpic,tv_sex_man, tv_sex_woman;
	private Button but_next_personal_data;
	private EditText et_nike_nanme;
	private boolean isboy = true;
	private ImageView iv_sex_man, iv_sex_woman;
	private ImageView iv_roundimage;
	private String userid =null;
//			province="山东",city="济南";
	private String version="1";
	
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_personal_data);
//		lin_adress = (LinearLayout) findViewById(R.id.lin_adress);
//		tv_adress = (TextView) findViewById(R.id.tv_adress);
		tv_sex_man = (TextView) findViewById(R.id.tv_sex_man);
		tv_sex_woman = (TextView) findViewById(R.id.tv_sex_woman);
		et_nike_nanme = (EditText) findViewById(R.id.et_nike_nanme);
		but_next_personal_data = (Button) findViewById(R.id.but_next_personal_data);
		iv_sex_man = (ImageView) findViewById(R.id.iv_sex_man);
		iv_sex_woman = (ImageView) findViewById(R.id.iv_sex_woman);
		iv_roundimage = (ImageView) findViewById(R.id.iv_roundimage);
		tv_setpic = (TextView) findViewById(R.id.tv_setpic);
		tv_setpic.setOnClickListener(this);
		iv_roundimage.setOnClickListener(this);
		but_next_personal_data.setOnClickListener(this);
//		lin_adress.setOnClickListener(this);
		iv_sex_man.setOnClickListener(this);
		iv_sex_woman.setOnClickListener(this);
		initTitle(getString(R.string.personaldata));
		initShowRight(R.drawable.skip, "跳过", true, true, 0);
		addRightClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SerializableFactory.sharedPreferencesSave(SaveType.NameAndPassword,SaveType.phone, getIntent().getStringExtra("phone"));
				SerializableFactory.sharedPreferencesSave(SaveType.NameAndPassword,SaveType.password, null);
				startActivity(new Intent(PersonalDataActivity.this,LoginActivity.class));
				PersonalDataActivity.this.finish();
			}
		});
		userid = getIntent().getStringExtra("userid");
	}

	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.lin_adress:
//			ChangeAddressDialog mChangeAddressDialog = new ChangeAddressDialog(PersonalDataActivity.this);
//			Window dialogWindow = mChangeAddressDialog.getWindow();
//	        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//	        dialogWindow.setGravity(Gravity.BOTTOM);
//	        lp.width = LayoutParams.MATCH_PARENT; // 宽度
//	        lp.height = LayoutParams.WRAP_CONTENT; // 高度
//	        dialogWindow.setAttributes(lp);
//	        dialogWindow.setWindowAnimations(R.style.dialog_bottom_style);
//			mChangeAddressDialog.show();
//			mChangeAddressDialog
//					.setAddresskListener(new OnAddressCListener() {
//						public void onClick(String province, String city) {
//							tv_adress.setText(province+"  "+city);
//							PersonalDataActivity.this.province=province;
//							PersonalDataActivity.this.city=city;
//						}
//					});
//			break;
		case R.id.but_next_personal_data:
			if (userid == null)
				return;
			String alias = et_nike_nanme.getText().toString();
			SerializableFactory.sharedPreferencesSave(SaveType.NameAndPassword,SaveType.phone, getIntent().getStringExtra("phone"));
			SerializableFactory.sharedPreferencesSave(SaveType.NameAndPassword,SaveType.password, null);
			if (CheckUtils.checkNickName(alias)) {
				String sex = "男";
				if (!isboy)
					sex = "女";
				RequestManger.updateBasic(PersonalDataActivity.this, userid,
						alias, sex,null, null, null, null, null, null,
						null, null, null,version==null?"1":version, listener, MHttpTools.getErrorListener(PersonalDataActivity.this));
			}
			break;
		case R.id.iv_sex_man:
			onClickSexBoy();
			break;
		case R.id.iv_sex_woman:
			onClickSexGirl();
			break;
		case R.id.tv_setpic:
		case R.id.iv_roundimage:
			if (userid == null)
				return;
			Intent intent = new Intent(PersonalDataActivity.this,UploadPicturesActivity.class);
			intent.putExtra("userid", userid);
			startActivityForResult(intent, CHANGEPIC);
			break;
		default:
			break;
		}
	}

	private Listener<RequestCall> listener = new Listener<RequestCall>() {

		@Override
		public void onResponse(RequestCall response) {
			startActivity(new Intent(PersonalDataActivity.this,LoginActivity.class));
			PersonalDataActivity.this.finish();
		}
	};

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == CHANGEPIC && arg1 == RESULT_OK) {
			String uri = arg2.getStringExtra("picurl");
			String version=arg2.getStringExtra("version");
			if (uri!=null && uri.length()>1){
				ImageLoader.getInstance().displayImage(uri, iv_roundimage, ImageLoadUtils.getPicOptions());
			}
			if (version!=null && version.length()>=1) {
				PersonalDataActivity.this.version=version;
			}
		}
	}

	private void onClickSexBoy() {
		isboy = true;
		iv_sex_man.setImageResource(R.drawable.login_man_on);
		iv_sex_woman.setImageResource(R.drawable.login_woman_off);
		tv_sex_man.setTextColor(getResources().getColorStateList(R.color.blue_bg));
		tv_sex_woman.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
	}

	private void onClickSexGirl() {
		isboy = false;
		iv_sex_man.setImageResource(R.drawable.login_man_off);
		iv_sex_woman.setImageResource(R.drawable.login_woman_on);
		tv_sex_man.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
		tv_sex_woman.setTextColor(getResources().getColorStateList(R.color.blue_bg));
	}
}
