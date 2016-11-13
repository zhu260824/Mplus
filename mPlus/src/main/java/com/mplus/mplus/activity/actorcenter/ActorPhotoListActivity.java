package com.mplus.mplus.activity.actorcenter;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridLayout;
import com.mplus.mplus.R;
import com.mplus.mplus.adapter.usercenter.EditUserPhotoAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorPhotoListPaser;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class ActorPhotoListActivity extends BaseActivity{
	private RecyclerView sGridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actor_photolist);
		initTitle(getString(R.string.actor_user_photo_list));
		PullToRefreshStaggeredGridLayout mPullToRefreshStaggerdGridView = (PullToRefreshStaggeredGridLayout)findViewById(R.id.mStaggeredGridView);
		mPullToRefreshStaggerdGridView.setMode(Mode.DISABLED);
		sGridView = mPullToRefreshStaggerdGridView.getRefreshableView();
		final String customerid=getIntent().getStringExtra("customerid");
		RequestManger.GetActorPhotos(ActorPhotoListActivity.this, customerid, new Listener<RequestCall>() {

			@Override
			public void onResponse(RequestCall response) {
				if (response.getParser().getResultSuccess()) 
					sGridView.setAdapter(new EditUserPhotoAdapter(ActorPhotoListActivity.this, ActorPhotoListPaser.GetInstance(),customerid,false));
			}
		}, errorListener);
	}
}
