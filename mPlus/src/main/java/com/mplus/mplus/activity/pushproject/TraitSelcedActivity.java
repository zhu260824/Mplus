package com.mplus.mplus.activity.pushproject;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.adapter.pushproject.TraitSelcedAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorTag;
import com.mplus.mplus.paser.actor.ActorTagPaser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class TraitSelcedActivity extends BaseActivity {
	private ListView lv;
	private int intenttype=1,dicttype=3;
	private TraitSelcedAdapter rAdapter;
	private ArrayList<ActorTag> lists;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_role_selced);
		initShowRight(0, getString(R.string.save), false, true, 0);
		showLeftText(true, getString(R.string.cancel), 0);
		lv=(ListView) findViewById(R.id.lv);
		intenttype=getIntent().getIntExtra("intenttype", 1);
		switch (intenttype) {
		case 1:
			initTitle(getString(R.string.project_move_type));
			dicttype=3;
			break;
		case 2:
			initTitle(getString(R.string.project_move_trait));	
			dicttype=4;
			break;
		case 3:
			initTitle(getString(R.string.project_role_selced));
			dicttype=5;
			break;
		case 4:
			initTitle(getString(R.string.project_role_selced));
			dicttype=5;
			break;
		default:
			break;
		}
//		lists=ActorTagPaser.GetInstance(dicttype);
//		if (lists==null || lists.size()<1) {
			RequestManger.GetDictData(TraitSelcedActivity.this, dicttype,new ActorTagPaser(dicttype), new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					lists=ActorTagPaser.GetInstance(dicttype);
					 rAdapter=new TraitSelcedAdapter(TraitSelcedActivity.this,lists, false,intenttype);
					lv.setAdapter(rAdapter);
				}
			}, errorListener);
//		}else {
//			rAdapter=new TraitSelcedAdapter(TraitSelcedActivity.this,lists, false,intenttype);
//			lv.setAdapter(rAdapter);
//			RequestManger.GetDictData(TraitSelcedActivity.this, dicttype,new DictStringPaser(dicttype), new Listener<RequestCall>() {
//
//				@Override
//				public void onResponse(RequestCall response) {
//				}
//			}, errorListener);
//		}
		
		addRightClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent backdata=new Intent();
				backdata.putExtra("data",rAdapter.getSelcedList());
				setResult(RESULT_OK,backdata);
				TraitSelcedActivity.this.finish();
			}
		});
	}
	
}
