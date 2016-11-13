package com.mplus.mplus.activity.usercenter;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.adapter.usercenter.EditActorTagAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorTag;
import com.mplus.mplus.paser.actor.ActorTagPaser;
import com.mplus.mplus.utils.StringUtils;
import com.mplus.mplus.utils.ToastTool;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class EditActorTagActivity extends BaseActivity{
	private ListView list;
	private EditActorTagAdapter tAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_role_selced);
		initTitle(getString(R.string.project_role_tag));
		initShowRight(0, getString(R.string.save), false, true, 0);
		list=(ListView) findViewById(R.id.lv);
		new initData().execute();
		addRightClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent backdata=new Intent();
				backdata.putExtra(ModificationOneLineMsgActivity.ETTD,getTag());;
				setResult(RESULT_OK,backdata);
				EditActorTagActivity.this.finish();	
			}
		});
	}
	

	private class initData extends AsyncTask<Integer, Integer,  ArrayList<ActorTag> >{

		@Override
		protected  ArrayList<ActorTag>  doInBackground(Integer... params) {
			return ActorTagPaser.GetInstance(13);
		}
		
		@Override
		protected void onPostExecute( ArrayList<ActorTag>  result) {
			super.onPostExecute(result);
			tAdapter=new EditActorTagAdapter(EditActorTagActivity.this, result);
			list.setAdapter(tAdapter);
			if (result==null || result.size()<1) {
				RequestManger.GetDictData(EditActorTagActivity.this, 13,new  ActorTagPaser(13), new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						if (response.getParser().getResultSuccess()) {
							tAdapter=new EditActorTagAdapter(EditActorTagActivity.this, ActorTagPaser.GetInstance(13));
							list.setAdapter(tAdapter);
						}else {
							if (response.getParser().getResponseMsg()!=null) 
								ToastTool.showShortToast(EditActorTagActivity.this, response.getParser().getResponseMsg());
						}
					}
				}, errorListener);
			}else {
				RequestManger.GetDictData(EditActorTagActivity.this, 13,new  ActorTagPaser(13), new Listener<RequestCall>() {

					@Override
					public void onResponse(RequestCall response) {
						
					}
				}, errorListener);
			}
		}
	}
	
	private String getTag() {
		String tag=null;
		if (tAdapter!=null) {
			ArrayList<ActorTag> list =tAdapter.getList();
			ArrayList<String> slist=new ArrayList<String>();
			for (ActorTag actorTag : list) {
				if (actorTag.isSelced()) 
					slist.add(actorTag.getName());
			}
			tag=StringUtils.ListToString(slist);
		}
		return tag;
	}
	
	
}
