package com.mplus.mplus.activity.usercenter;

import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.paser.common.IntentEditTextData;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class ModificationMoreMsgActivity extends BaseActivity{
	public static final String ETTD="EditTextData";
	private TextView tv_number;
	private EditText et_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modification_more_msg);
		tv_number=(TextView) findViewById(R.id.tv_number);
		et_content=(EditText) findViewById(R.id.et_content);
		init();
	
	
	}
	
	
	private void init() {
		final IntentEditTextData et=(IntentEditTextData) getIntent().getExtras().get(ETTD);
		initTitle(et==null?"":et.getTitle()==null?"":et.getTitle());
		initShowRight(0, getString(R.string.save), false, true, 0);
		showLeftText(true, getString(R.string.cancel), 0);
		if (et==null) {
			return;
		}
		String Etcontent=et.getEtcontent();
		if (Etcontent!=null){ 
			et_content.setText(et.getEtcontent());
			tv_number.setText(Etcontent.length()+"/"+et.getEtlength());
		}else {
			tv_number.setText("0/"+et.getEtlength());
		}
		if (et.getEtlength()!=0) 
			et_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(et.getEtlength())});
		if (et.getHint()!=null) 
			et_content.setHint(et.getHint());
		et_content.setInputType(et.getInputtype());
		et_content.setSelection(et_content.getText().length());
		//文本显示的位置在EditText的最上方
		et_content.setGravity(Gravity.TOP);
		//改变默认的单行模式
		et_content.setSingleLine(false);
		//水平滚动设置为False
		et_content.setHorizontallyScrolling(false);
		addRightClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent backdata=new Intent();
				backdata.putExtra(ETTD,et_content.getText().toString());;
				setResult(RESULT_OK,backdata);
				ModificationMoreMsgActivity.this.finish();	
			}
		});
		et_content.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String content=et_content.getText().toString();
				 tv_number.setText(content.length()+"/"+et.getEtlength());  
			}
		});
	}
	
}
