package com.mplus.mplus.activity.usercenter;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridLayout;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.common.CropImageActivity;
import com.mplus.mplus.activity.common.UploadPhotoActivity;
import com.mplus.mplus.adapter.usercenter.EditUserPhotoAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorPhotoListPaser;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.ToastTool;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class EditUserPhotoActivity extends BaseActivity{
	private static final int TOPHOTO=   1;// 
	private String customerid;
	private RecyclerView sGridView;
	private EditUserPhotoAdapter edAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_userphoto);
		initTitle(getString(R.string.photo_list));
		PullToRefreshStaggeredGridLayout mPullToRefreshStaggerdGridView = (PullToRefreshStaggeredGridLayout)findViewById(R.id.mStaggeredGridView);
		mPullToRefreshStaggerdGridView.setMode(Mode.DISABLED);
		sGridView = mPullToRefreshStaggerdGridView.getRefreshableView();
		((ImageView) findViewById(R.id.iv_add)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(EditUserPhotoActivity.this,UploadPhotoActivity.class);
				intent.putExtra(CropImageActivity.RATIOTYPE, CropImageActivity.THREEDTYPE);
				startActivityForResult(intent, TOPHOTO);
			}
		});
		new initData(true).execute();
	}
	
	private class initData extends AsyncTask<Integer, Integer, Integer>{
		private boolean isRefresh=true;
		
		public initData(boolean isRefresh) {
			super();
			this.isRefresh = isRefresh;
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			customerid=UserPaser.GetInstance().customerid;
			return null;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			RequestManger.GetActorPhotos(EditUserPhotoActivity.this,customerid, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()) {
						if (isRefresh) {
							edAdapter=new EditUserPhotoAdapter(EditUserPhotoActivity.this, ActorPhotoListPaser.GetInstance(),customerid,true);
							sGridView.setAdapter(edAdapter);
						}else {
							edAdapter.notfit(ActorPhotoListPaser.GetInstance());
						}
					}
				}
			}, errorListener);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==TOPHOTO && resultCode==RESULT_OK) {
			String shortcut=data.getStringExtra(UploadPhotoActivity.PHOTO_URL);
			RequestManger.PostAddActorPhotos(EditUserPhotoActivity.this, customerid, shortcut, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()) {
						ToastTool.showShortToast(EditUserPhotoActivity.this, "添加艺人图片成功！");
						new initData(false).execute();
					}else {
						if (response.getParser().getResponseMsg()!=null) 
							ToastTool.showShortToast(EditUserPhotoActivity.this, response.getParser().getResponseMsg());
					}
				}
			}, errorListener);
		}
	}
	
}
