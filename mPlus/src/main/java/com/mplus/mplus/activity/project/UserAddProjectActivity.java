package com.mplus.mplus.activity.project;

import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.BaseFragment;
import com.mplus.mplus.base.BaseFragment.BackHandledInterface;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class UserAddProjectActivity extends BaseActivity implements BackHandledInterface, OnClickListener {
	private TextView tv_add, tv_ask;
	private UserAddJobFragment addFragment;
	private UserAskJobFragment askfragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_add_project);
		initTitle(getString(R.string.user_add_project));
		tv_add = (TextView) findViewById(R.id.tv_add);
		tv_ask = (TextView) findViewById(R.id.tv_ask);
		tv_add.setOnClickListener(this);
		tv_ask.setOnClickListener(this);
		showAddFragment();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_add:
			showAddFragment();
			break;
		case R.id.tv_ask:
			showAskFragment();
			break;
		default:
			break;
		}
	}

	private void showAskFragment() {
		tv_ask.setBackgroundResource(R.drawable.consensusmanger_right_off);
		tv_add.setBackgroundResource(R.drawable.consensusmanger_letf_on);
		if (askfragment==null){
			askfragment=new UserAskJobFragment();
			if (addFragment==null) {
				getSupportFragmentManager().beginTransaction().add(R.id.list_content, askfragment).show(askfragment).commit();
			}else {
				getSupportFragmentManager().beginTransaction().add(R.id.list_content, askfragment).show(askfragment).hide(addFragment).commit();
			}
		}else {
			if (addFragment == null) {
				getSupportFragmentManager().beginTransaction().show(askfragment).commit();
			} else {
				getSupportFragmentManager().beginTransaction().hide(addFragment).show(askfragment).commit();
			}
		}
	}



	private void showAddFragment() {
		tv_ask.setBackgroundResource(R.drawable.consensusmanger_right_on);
		tv_add.setBackgroundResource(R.drawable.consensusmanger_letf_off);
		if (addFragment==null){
			addFragment=new UserAddJobFragment();
			if (askfragment==null) {
				getSupportFragmentManager().beginTransaction().add(R.id.list_content, addFragment).show(addFragment).commit();
			}else {
				getSupportFragmentManager().beginTransaction().add(R.id.list_content, addFragment).show(addFragment).hide(askfragment).commit();
			}
		}else {
			if (askfragment == null) {
				getSupportFragmentManager().beginTransaction().show(addFragment).commit();
			} else {
				getSupportFragmentManager().beginTransaction().hide(askfragment).show(addFragment).commit();
			}
		}
	}

	
	
	@Override
	public void setSelectedFragment(BaseFragment selectedFragment) {

	}
}
