package com.mplus.mplus.activity.usercenter;

import com.dtd365.library.Utils.RegexUtils;
import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.paser.common.IntentEditTextData;
import com.mplus.mplus.utils.ToastTool;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class ModificationOneLineMsgActivity extends BaseActivity{
	public static final String ETTD="EditTextData";
	private ImageView iv_delete;
	private EditText et_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modification_one_line_msg);
		iv_delete=(ImageView) findViewById(R.id.iv_delete);
		et_content=(EditText) findViewById(R.id.et_content);
//		et_content.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
//		et_content.setSingleLine(false); 
//		et_content.setHorizontallyScrolling(false);
		init();
	}
	
	
	private void init(){
		final IntentEditTextData et=(IntentEditTextData) getIntent().getExtras().get(ETTD);
		initTitle(et==null?"":et.getTitle()==null?"":et.getTitle());
		initShowRight(0, getString(R.string.save), false, true, 0);
		showLeftText(true, getString(R.string.cancel), 0);
		if (et==null) {
			return;
		}
		if (et.getEtcontent()!=null) 
			et_content.setText(et.getEtcontent());
		if (et.getEtlength()!=0) 
			et_content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(et.getEtlength())});
		if (et.getHint()!=null) 
			et_content.setHint(et.getHint());
		et_content.setInputType(et.getInputtype());
		et_content.setSelection(et_content.getText().length());
		iv_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				et_content.setText("");
			}
		});
		addRightClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String value=et_content.getText().toString();
				if (et.getInputtype()==InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) {
					if (RegexUtils.isEmail(value)) {
						Intent backdata=new Intent();
						backdata.putExtra(ETTD,value);;
						setResult(RESULT_OK,backdata);
						ModificationOneLineMsgActivity.this.finish();
					}else {
						ToastTool.showShortToast(ModificationOneLineMsgActivity.this, "请输入正确的邮箱地址");
					}
				}else {
					Intent backdata=new Intent();
					backdata.putExtra(ETTD,value);;
					setResult(RESULT_OK,backdata);
					ModificationOneLineMsgActivity.this.finish();
				}
			}
		});
	}
	
	
	
	

}
