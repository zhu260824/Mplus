package com.mplus.mplus.activity.project;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.temporary.ModificationMyRoleActivity;
import com.mplus.mplus.adapter.pushproject.RoleSelcedAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.pushproject.RoleType;
import com.mplus.mplus.paser.pushproject.RoleTypePaser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RoleSelcedActivity extends BaseActivity {
	private static final int MYROLE=1;
	private ListView lv;
	private RoleSelcedAdapter rAdapter;
	private ArrayList<RoleType> lists;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_role_selced);
		initTitle(getString(R.string.project_role_selced));
		lv=(ListView) findViewById(R.id.lv);
		final int intenttype=getIntent().getIntExtra("intenttype", 1);
		if (intenttype==1) {
			View view=View.inflate(RoleSelcedActivity.this, R.layout.items_role_selced, null);
			TextView tv_name=(TextView) view.findViewById(R.id.tv_name);
			ImageView iv_ico=(ImageView) view.findViewById(R.id.iv_ico);
			iv_ico.setVisibility(View.GONE);
			tv_name.setText("自定义");
			lv.addFooterView(view);
		}
		lists=RoleTypePaser.GetInstance();
		if (lists==null || lists.size()<1) {
			RequestManger.GetDictData(RoleSelcedActivity.this, 5,new RoleTypePaser(), new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					lists=RoleTypePaser.GetInstance();
					rAdapter=new RoleSelcedAdapter(showRole(lists));
					lv.setAdapter(rAdapter);
				}
			}, errorListener);
		}else {
			rAdapter=new RoleSelcedAdapter(showRole(lists));
			lv.setAdapter(rAdapter);
			RequestManger.GetDictData(RoleSelcedActivity.this,  5,new RoleTypePaser(), new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
				}
			}, errorListener);
		}
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				if (id==-1) {
					startActivityForResult(new Intent(RoleSelcedActivity.this,ModificationMyRoleActivity.class), MYROLE);
				}
				TextView tv_name=(TextView) view.findViewById(R.id.tv_name);
				if (tv_name!=null && tv_name.getTag()!=null) {
					RoleType roleType=(RoleType) tv_name.getTag();
						Intent backdata=new Intent();
						backdata.putExtra("data", roleType);
						setResult(RESULT_OK,backdata);
						RoleSelcedActivity.this.finish();
				}
			}
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
   		super.onActivityResult(requestCode, resultCode, data);
   		if (requestCode==MYROLE && resultCode==RESULT_OK) {
   			if (data!=null) {
				RoleType roleType=(RoleType) data.getExtras().get("data");
				lists.add(roleType);
				rAdapter.notifyDataSetChanged();
			}
   		}
	}
	
	
	private ArrayList<RoleType> showRole(ArrayList<RoleType> list){
		ArrayList<RoleType> sLists=new ArrayList<RoleType>();
		for (RoleType roleType : list) {
			if (roleType.type.equals("1")) {
				sLists.add(roleType);
			}
		}
		return sLists;
	}
	

}
