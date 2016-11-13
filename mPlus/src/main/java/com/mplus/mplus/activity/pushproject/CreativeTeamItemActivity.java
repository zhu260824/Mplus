package com.mplus.mplus.activity.pushproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.paser.pushproject.ProductJob;
import com.mplus.mplus.paser.pushproject.RoleType;

public class CreativeTeamItemActivity extends BaseActivity {
	private static final int SELCEDROLE=11;
	private TextView  tv_role,et_desc,tv_state;
	private EditText et_name,et_nickname;
	private Button btn_commit;
	private LinearLayout ly_role,lin_apply_static;
	private ProductJob productJob;
	private int listIndex=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_creative_team_item);
		initTitle(getString(R.string.role_details));
		tv_role = (TextView) findViewById(R.id.tv_role);
		et_desc = (TextView) findViewById(R.id.et_desc);
		et_name = (EditText) findViewById(R.id.et_name);
		et_nickname = (EditText) findViewById(R.id.et_nickname);
		tv_state = (TextView) findViewById(R.id.tv_state);
		btn_commit = (Button) findViewById(R.id.btn_commit);
		ly_role = (LinearLayout) findViewById(R.id.ly_role);
		lin_apply_static=(LinearLayout) findViewById(R.id.lin_apply_static);
		productJob=(ProductJob) getIntent().getExtras().get("pjob");
		listIndex=getIntent().getIntExtra("listIndex", 0);
		if (productJob==null) {
			productJob=new ProductJob("", "", 0, "", "");
		}
		inintView(productJob);
		ly_role.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CreativeTeamItemActivity.this, RoleSelcedActivity.class);
				startActivityForResult(intent, SELCEDROLE);
			}
		});
		lin_apply_static.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (productJob.action==1) {
					productJob.action=0;
					tv_state.setText("拟请");
				}else {
					productJob.action=1;
					tv_state.setText("已签约");
				}
			}
		});
		btn_commit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (check()) {
					Intent intent=new Intent(); 
					Bundle bundle = new Bundle();
					bundle.putSerializable("pjob",productJob); 
					bundle.putInt("listIndex", listIndex);
					intent.putExtras(bundle);
			        setResult(RESULT_OK, intent);  
			        finish();
				}
			}
		});
	}
	
	private void inintView(ProductJob pJob){
		if (pJob!=null) {
			tv_role.setText(pJob.role==null?"":pJob.role);
			et_name.setText(pJob.name==null?"":pJob.name);
			et_nickname.setText(pJob.nickname==null?"":pJob.nickname);
			et_desc.setText(pJob.desc==null?"":pJob.desc);
			if (pJob.action==1) {
				tv_state.setText("已签约");
			}else {
				tv_state.setText("拟请");
			}
		}
	}
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
   		super.onActivityResult(requestCode, resultCode, data);
   		if (requestCode==SELCEDROLE && resultCode==RESULT_OK) {
   			if (data!=null) {
   				RoleType roletype=(RoleType) data.getSerializableExtra("data");
   				tv_role.setText(roletype.role);
			}
		}
	}
	
	private  boolean check() {
		productJob.role=tv_role.getText().toString();
		productJob.name=et_name.getText().toString();
		productJob.nickname=et_nickname.getText().toString();
		productJob.desc=et_desc.getText().toString();
		if (productJob.role==null || productJob.role.trim().length()==0) {
			Toast.makeText(CreativeTeamItemActivity.this, "请选择角色", Toast.LENGTH_SHORT).show();
			return false;
		}else if (productJob.name==null || productJob.name.trim().length()==0) {
			Toast.makeText(CreativeTeamItemActivity.this, "请填写姓名", Toast.LENGTH_SHORT).show();
			return false;
		}
		/*else if (productJob.nickname==null || productJob.nickname.trim().length()==0) {
			Toast.makeText(CreativeTeamItemActivity.this, "请填写剧中称谓", Toast.LENGTH_SHORT).show();
			return false;
		}*/
		else {
			return true;
		}
	}
}
