package com.mplus.mplus.activity.systemset;

import com.mplus.mplus.R;
import com.mplus.mplus.activity.common.ShowTermActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.utils.AddPlatformTool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SystemSetAboutActivity extends BaseActivity implements OnClickListener {
	private TextView tv_mtrem;
	private Button btn_share;
	private AddPlatformTool platformTool;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_systemset_about);
		initTitle(getString(R.string.text_system_aboutme));
		tv_mtrem = (TextView) findViewById(R.id.tv_mtrem);
		btn_share=(Button) findViewById(R.id.btn_share);
		btn_share.setOnClickListener(this);
		tv_mtrem.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_mtrem:
			startActivity(new Intent(SystemSetAboutActivity.this,ShowTermActivity.class));
			break;
		case R.id.btn_share:
			if (platformTool==null) {
				platformTool=new AddPlatformTool(SystemSetAboutActivity.this);
			}
			platformTool.shareMsg("M+是一个专为影视圈从业人员及影视项目投资者打造的一站式资源互惠平台。我们旨在为您投资电影项目，参演电影角色，发布拍摄诉求提供资源与便利。使用M+专属 Android 客户端，省流量更美观。点击下载：http://www.mplus.cn","www.mplus.cn","http://www.mplus.cn",R.drawable.ic_launcher,null);
			break;
		default:
			break;
		}

	}

	
}
