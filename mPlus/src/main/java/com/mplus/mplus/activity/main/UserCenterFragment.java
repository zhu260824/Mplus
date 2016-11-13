package com.mplus.mplus.activity.main;

import com.dtd365.library.Utils.MobclickAgentUtils;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.login.LoginActivity;
import com.mplus.mplus.activity.login.RegisterActivity;
import com.mplus.mplus.activity.project.MyProjectActivity;
import com.mplus.mplus.activity.project.UserAddProjectActivity;
import com.mplus.mplus.activity.systemset.SystemSetActivity;
import com.mplus.mplus.activity.usercenter.UserCenterActivity;
import com.mplus.mplus.activity.usercenter.UserSettingActivity;
import com.mplus.mplus.base.BaseFragment;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.paser.login.User;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UserCenterFragment extends BaseFragment implements OnClickListener {
	private LinearLayout lin_nologin,lin_sys_setting,lin_user_setting,lin_base_data,lin_user_add,lin_myproject;
	private Button btn_login,btn_register,btn_logout;
	private RelativeLayout rl_islogin;
	private TextView tv_nikename;
	private ImageView iv_ico;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=View.inflate(mActivity, R.layout.fragment_main_usercenter, null);
		initView(view);
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		new inintDataTask().execute();
	}
	
	
	private void initView(View view){
		btn_login=(Button) view.findViewById(R.id.btn_login);
		btn_register=(Button) view.findViewById(R.id.btn_register);
		btn_logout=(Button) view.findViewById(R.id.btn_logout);
		
		lin_nologin=(LinearLayout) view.findViewById(R.id.lin_nologin);
		rl_islogin=(RelativeLayout) view.findViewById(R.id.rl_islogin);
		tv_nikename=(TextView) view.findViewById(R.id.tv_nikename);
		iv_ico=(ImageView) view.findViewById(R.id.iv_ico);
		
		lin_user_setting=(LinearLayout) view.findViewById(R.id.lin_user_setting);
		lin_base_data=(LinearLayout) view.findViewById(R.id.lin_base_data);
		lin_sys_setting=(LinearLayout) view.findViewById(R.id.lin_sys_setting);
		lin_myproject=(LinearLayout) view.findViewById(R.id.lin_myproject);
		lin_user_add=(LinearLayout) view.findViewById(R.id.lin_user_add);
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		btn_logout.setOnClickListener(this);
		iv_ico.setOnClickListener(this);
		lin_sys_setting.setOnClickListener(this);
		lin_user_setting.setOnClickListener(this);
		lin_base_data.setOnClickListener(this);
		lin_user_add.setOnClickListener(this);
		lin_myproject.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_ico:
			if (!MPlusApplication.isLogin){
				startActivity(new Intent(getActivity(),LoginActivity.class));
				return;
			}
			startActivity(new Intent(mActivity,UserCenterActivity.class));
			break;
		case R.id.btn_login:
			startActivity(new Intent(mActivity,LoginActivity.class));
			break;
		case R.id.btn_register:
			startActivity(new Intent(mActivity,RegisterActivity.class));
			break;
		case R.id.lin_myproject:
			if (!MPlusApplication.isLogin){
				startActivity(new Intent(getActivity(),LoginActivity.class));
				return;
			}
			startActivity(new Intent(mActivity,MyProjectActivity.class));
			MobclickAgentUtils.MobclickEventName(mActivity, "0009", "查看我发布的项目", "查看我发布的项目");
			break;
		case R.id.btn_logout:
			MPlusApplication.isLogin=false;
			new inintDataTask().execute();
			break;
		case R.id.lin_user_add:
			if (!MPlusApplication.isLogin){
				startActivity(new Intent(getActivity(),LoginActivity.class));
				return;
			}
			startActivity(new Intent(mActivity,UserAddProjectActivity.class));
			MobclickAgentUtils.MobclickEventName(mActivity, "0008", "查看我参与的项目", "查看我参与的项目");
			break;
		case R.id.lin_user_setting:
			if (!MPlusApplication.isLogin){
				startActivity(new Intent(getActivity(),LoginActivity.class));
				return;
			}
			startActivity(new Intent(mActivity,UserSettingActivity.class));
			break;

		case R.id.lin_base_data:
			if (!MPlusApplication.isLogin){
				startActivity(new Intent(getActivity(),LoginActivity.class));
				return;
			}
			startActivity(new Intent(mActivity,UserCenterActivity.class));
			break;
		
		case R.id.lin_sys_setting:
			startActivity(new Intent(mActivity,SystemSetActivity.class));
			break;

		default:
			break;
		}
	}
	
	private class inintDataTask extends AsyncTask<Integer, Integer, User>{

		@Override
		protected User doInBackground(Integer... params) {
			User user=null;
			if (MPlusApplication.isLogin) 
				user=UserPaser.GetInstance();
			return user;
		}
		@Override
		protected void onPostExecute(User result) {
			super.onPostExecute(result);
			initUserData(result);
		}
	}
	
	private void initUserData(User user){
		if (MPlusApplication.isLogin) {
			rl_islogin.setVisibility(View.VISIBLE);
			lin_nologin.setVisibility(View.GONE);
			if (user!=null) {
				tv_nikename.setText(user.stageName==null?user.realname==null?user.username:user.realname:user.stageName);
				if(user.portraitpath!=null){
					ImageLoader.getInstance().displayImage(user.portraitpath, iv_ico,ImageLoadUtils.getPicOptions());
				}else {
					iv_ico.setImageResource(R.drawable.person_default);
				}
			}
		}else {
			rl_islogin.setVisibility(View.GONE);
			lin_nologin.setVisibility(View.VISIBLE);
			iv_ico.setImageResource(R.drawable.person_default);
		}
	}
	
	

	@Override
	public boolean onBackPressed() {
		return false;
	}

}

