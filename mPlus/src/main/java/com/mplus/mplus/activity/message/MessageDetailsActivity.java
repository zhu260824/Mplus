package com.mplus.mplus.activity.message;

import android.os.Bundle;
import android.widget.TextView;

import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;

public class MessageDetailsActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_details);	
		initTitle(getString(R.string.message_details));
		String content=getIntent().getStringExtra("content");
		TextView tv_content=(TextView)findViewById(R.id.tv_content);
		tv_content.setText(content==null?"":content);	
	}
	
}
