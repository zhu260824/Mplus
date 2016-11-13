package com.mplus.mplus.activity.upresume;

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
import android.widget.TextView;

public class ThirdStepActivity extends BaseActivity {
	private static final int TOPHOTO=1;// 
	private String customerid;
	private TextView tv_next;
	private ImageView iv_add;
	private RecyclerView sGridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upresume_thirdstep);
		initTitle(getString(R.string.actor_user_add_photo_list));
		PullToRefreshStaggeredGridLayout mPullToRefreshStaggerdGridView = (PullToRefreshStaggeredGridLayout)findViewById(R.id.mStaggeredGridView);
		mPullToRefreshStaggerdGridView.setMode(Mode.DISABLED);
		sGridView = mPullToRefreshStaggerdGridView.getRefreshableView();
		tv_next=(TextView) findViewById(R.id.tv_next);
		iv_add=(ImageView) findViewById(R.id.iv_add);
		tv_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent backdata=new Intent();
				setResult(RESULT_OK,backdata);
				ThirdStepActivity.this.finish();	
			}
		});
		new initData().execute();
		iv_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ThirdStepActivity.this,UploadPhotoActivity.class);
				intent.putExtra(CropImageActivity.RATIOTYPE, CropImageActivity.THREEDTYPE);
				startActivityForResult(intent, TOPHOTO);
			}
		});
	}
	
	private class initData extends AsyncTask<Integer, Integer, Integer>{

		@Override
		protected Integer doInBackground(Integer... params) {
			customerid=UserPaser.GetInstance().customerid;
			return null;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			RequestManger.GetActorPhotos(ThirdStepActivity.this, customerid, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()) 
						sGridView.setAdapter(new EditUserPhotoAdapter(ThirdStepActivity.this, ActorPhotoListPaser.GetInstance(),customerid,true));
				}
			}, errorListener);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==TOPHOTO && resultCode==RESULT_OK) {
			String shortcut=data.getStringExtra(UploadPhotoActivity.PHOTO_URL);
			RequestManger.PostAddActorPhotos(ThirdStepActivity.this, customerid, shortcut, new Listener<RequestCall>() {

				@Override
				public void onResponse(RequestCall response) {
					if (response.getParser().getResultSuccess()) {
						ToastTool.showShortToast(ThirdStepActivity.this, "添加艺人图片成功！");
						new initData().execute();
					}else {
						if (response.getParser().getResponseMsg()!=null) 
							ToastTool.showShortToast(ThirdStepActivity.this, response.getParser().getResponseMsg());
					}
				}
			}, errorListener);
		}
	}
}
