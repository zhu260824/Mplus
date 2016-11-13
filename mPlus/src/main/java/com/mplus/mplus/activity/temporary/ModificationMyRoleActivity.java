package com.mplus.mplus.activity.temporary;

import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.paser.pushproject.RoleType;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

public class ModificationMyRoleActivity  extends BaseActivity {
	private EditText et_delete_modification_nickname;
	private ImageButton ibut_delete_modification_nickname;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modification_nickname);
		et_delete_modification_nickname = (EditText)findViewById(R.id.et_delete_modification_nickname);
		ibut_delete_modification_nickname = (ImageButton)findViewById(R.id.ibut_delete_modification_nickname);
		initTitle("自定义角色");
		initShowRight(0, "保存", false, true, 48);
		initdata();
	
		addRightClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String role=et_delete_modification_nickname.getText().toString();
				if (role!=null) {
					RoleType roleType=new RoleType(role, "3", true);
					Intent backdata=new Intent();
					backdata.putExtra("data", roleType);
					setResult(RESULT_OK,backdata);
					ModificationMyRoleActivity.this.finish();
				}
			}
		});
		ibut_delete_modification_nickname.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
			et_delete_modification_nickname.setText("");
			}
		});
	}
	
	private void initdata(){
		et_delete_modification_nickname.setHint("请输入自定义角色名");
		et_delete_modification_nickname.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
		et_delete_modification_nickname.setSelection(et_delete_modification_nickname.getText().length());
	}
}
