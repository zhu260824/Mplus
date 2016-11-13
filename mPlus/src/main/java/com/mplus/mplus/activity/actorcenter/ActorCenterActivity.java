package com.mplus.mplus.activity.actorcenter;

import java.util.ArrayList;
import java.util.Map;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.usercenter.UserCenterActivity;
import com.mplus.mplus.adapter.actor.ActorPerformAdapter;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorDetails;
import com.mplus.mplus.paser.actor.ActorDetailsPaser;
import com.mplus.mplus.paser.actor.ActorPerformPaser;
import com.mplus.mplus.paser.actor.ActorPhotoList;
import com.mplus.mplus.paser.actor.ActorPhotoListPaser;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.AddPlatformTool;
import com.mplus.mplus.utils.DialongUtils;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.mplus.mplus.utils.LoginUtils;
import com.mplus.mplus.utils.MultipleTextUtils;
import com.mplus.mplus.utils.StringUtils;
import com.mplus.mplus.utils.ToastTool;
import com.mplus.mplus.utils.UrlStingTool;
import com.mplus.mplus.view.multipletext.MultipleTextViewGroup;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ActorCenterActivity extends BaseActivity implements OnClickListener {
	public final static String CUSTOMERID = "customerid";
	private ListView list_history;
	private ImageView iv_ico, iv_sex;
	private TextView tv_name, tv_height, tv_weight, tv_signature, tv_profile,tv_collect, tv_option, tv_cdata, tv_age;
	private MultipleTextViewGroup multipleText;
	private LinearLayout lin_coollect_option, lin_photolist, lin_tag,lin_photos;
	private AddPlatformTool platformTool;
	private String userid = null, actoruserid = null;
	private HorizontalScrollView horscroll;
	private View view_video;

	// private long clickTime=System.currentTimeMillis();
	// private VideoWindowsUtils videoUtils;
	// private int videobutton=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actor_center);
		initTitle(getString(R.string.change_user_center));
		initShowRight(R.drawable.ico_share, null, true, false, 0);
		View headerView = View.inflate(this, R.layout.heard_actor_center, null);
		iv_ico = (ImageView) headerView.findViewById(R.id.iv_ico);
		tv_name = (TextView) headerView.findViewById(R.id.tv_name);
		tv_height = (TextView) headerView.findViewById(R.id.tv_height);
		tv_weight = (TextView) headerView.findViewById(R.id.tv_weight);
		iv_sex = (ImageView) headerView.findViewById(R.id.iv_sex);
		tv_age = (TextView) headerView.findViewById(R.id.tv_age);
		tv_signature = (TextView) headerView.findViewById(R.id.tv_signature);
		tv_profile = (TextView) headerView.findViewById(R.id.tv_profile);
		lin_coollect_option = (LinearLayout) findViewById(R.id.lin_coollect_option);
		lin_photolist = (LinearLayout) headerView.findViewById(R.id.lin_photolist);
		lin_tag = (LinearLayout) headerView.findViewById(R.id.lin_tag);
		lin_photos = (LinearLayout) headerView.findViewById(R.id.lin_photos);
		horscroll = (HorizontalScrollView) headerView.findViewById(R.id.horscroll);
		tv_collect = (TextView) findViewById(R.id.tv_collect);
		tv_option = (TextView) findViewById(R.id.tv_option);
		tv_cdata = (TextView) findViewById(R.id.tv_cdata);
		multipleText = (MultipleTextViewGroup) headerView.findViewById(R.id.main_rl);
		list_history = (ListView) findViewById(R.id.list_history);
		view_video = headerView.findViewById(R.id.view_video);
		view_video.setVisibility(View.GONE);
		// LinearLayout.LayoutParams lp=new
		// LinearLayout.LayoutParams(MPlusApplication.Width,
		// MPlusApplication.Width*9/16);
		// view_video.setLayoutParams(lp);
		tv_collect.setOnClickListener(this);
		tv_option.setOnClickListener(this);
		tv_cdata.setOnClickListener(this);
		lin_photolist.setOnClickListener(this);
		list_history.addHeaderView(headerView);
	}

	@Override
	protected void onResume() {
		super.onResume();
		new initData().execute();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// if (videoUtils!=null) {
		// videoUtils.stopvideo();
		// }
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_collect:
			LoginUtils loginUtils=new LoginUtils() {
				
				@Override
				public void DoOnLongin() {
					AddCollect();
				}
			};
			loginUtils.ToLogin(ActorCenterActivity.this, LoginUtils.TOLOGIN);
			break;
		case R.id.tv_option:
			LoginUtils loginUtils2=new LoginUtils() {
				
				@Override
				public void DoOnLongin() {
					AddOption();;
				}
			};
			loginUtils2.ToLogin(ActorCenterActivity.this, LoginUtils.TOLOGIN2);
			break;
		case R.id.tv_cdata:
			if (userid == null) {
			} else {
				startActivity(new Intent(ActorCenterActivity.this,UserCenterActivity.class));
			}
			break;
		case R.id.lin_photolist:
			Intent intent = new Intent(ActorCenterActivity.this,ActorPhotoListActivity.class);
			intent.putExtra("customerid", getIntent().getStringExtra(CUSTOMERID));
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	private class initData extends AsyncTask<Integer, Integer, String> {

		@Override
		protected String doInBackground(Integer... params) {
			if (MPlusApplication.isLogin)
				userid = UserPaser.GetInstance().userid;
			return getIntent().getStringExtra(CUSTOMERID);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			RequestManger.GetActorDetails(ActorCenterActivity.this, result,userid, new Listener<RequestCall>() {

						@Override
						public void onResponse(RequestCall response) {
							if (response.getParser().getResultSuccess())
								initViewData(ActorDetailsPaser.GetInstance());
						}
					}, errorListener);
			RequestManger.GetActorPhotos(ActorCenterActivity.this, result,new Listener<RequestCall>() {

						@Override
						public void onResponse(RequestCall response) {
							if (response.getParser().getResultSuccess())addPhoto(ActorPhotoListPaser.GetInstance(),lin_photos);
						}
					}, errorListener);
			RequestManger.GetActorHistory(ActorCenterActivity.this, result,new Listener<RequestCall>() {

						@Override
						public void onResponse(RequestCall response) {
							if (response.getParser().getResultSuccess()) {
								list_history.setAdapter(new ActorPerformAdapter(ActorCenterActivity.this,ActorPerformPaser.GetInstance()));
							} else {
								if (response.getParser().getResponseMsg() != null)
									ToastTool.showShortToast(ActorCenterActivity.this, response.getParser().getResponseMsg());
							}
						}
					}, errorListener);
		}

	}

	private void initViewData(final ActorDetails actor) {
		if (actor != null) {
			actoruserid = actor.getUserId();
			if (userid != null && actoruserid != null&& userid.equals(actoruserid)) {
				lin_coollect_option.setVisibility(View.GONE);
				tv_cdata.setVisibility(View.VISIBLE);
			} else {
				lin_coollect_option.setVisibility(View.VISIBLE);
				tv_cdata.setVisibility(View.GONE);
			}
			ImageLoader.getInstance().displayImage(actor.getAvatar(), iv_ico,ImageLoadUtils.getPicOptions());
			tv_name.setText(TextUtils.isEmpty(actor.getName()) ? "" : actor.getName());
			if (!TextUtils.isEmpty(actor.getHeight())&& !actor.getHeight().equals("0")) {
				tv_height.setVisibility(View.VISIBLE);
				tv_height.setText(actor.getHeight() + "cm");
			} else {
				tv_height.setVisibility(View.GONE);
			}

			if (!TextUtils.isEmpty(actor.getWeight())&& !actor.getWeight().equals("0")) {
				tv_weight.setVisibility(View.VISIBLE);
				tv_weight.setText(actor.getWeight() + "kg");
			} else {
				tv_weight.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(actor.getAge())&& !actor.getAge().equals("0")) {
				tv_age.setVisibility(View.VISIBLE);
				tv_age.setText(actor.getAge() + "岁");
			} else {
				tv_age.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(actor.getSex())) {
				if (actor.getSex().equals("男")) {
					iv_sex.setVisibility(View.VISIBLE);
					iv_sex.setImageResource(R.drawable.image_gender_man);
				} else if (actor.getSex().equals("女")) {
					iv_sex.setVisibility(View.VISIBLE);
					iv_sex.setImageResource(R.drawable.image_gender_woman);
				} else {
					iv_sex.setVisibility(View.GONE);
				}
			} else {
				iv_sex.setVisibility(View.GONE);
			}
			setTitle(TextUtils.isEmpty(actor.getName()) ? getString(R.string.change_user_center): actor.getName());
			String signature = TextUtils.isEmpty(actor.getSignature()) ? "主人很低调，无暇填写！": actor.getSignature();
			String profile = TextUtils.isEmpty(actor.getResume()) ? "" : actor.getResume();
			tv_signature.setText(Html.fromHtml("<font color=\"#575757\" size=\"44px\">"+ getString(R.string.change_user_signature)+ "：</font>" + signature));
			tv_profile.setText(Html.fromHtml("<font color=\"#575757\" size=\"44px\">"+ getString(R.string.change_user_profile)+ "：</font>" + profile));
			addRightClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (platformTool == null) {
						platformTool = new AddPlatformTool(ActorCenterActivity.this);
					}
					platformTool.shareMsg("这个演员不错，分享给你！", actor.getName(),actor.getActorShareLink(), 0, actor.getAvatar());
				}
			});
			if (actor.getIsFavorite().equals("true")) {
				tv_collect.setText(getString(R.string.collect_cannelcollect));
			} else {
				tv_collect.setText(getString(R.string.actor_user_add_collect));
			}
			String feature = TextUtils.isEmpty(actor.getActorFeature()) ? "": actor.getActorFeature();
			ArrayList<String> tags = StringUtils.StringToList(feature);
			if (tags != null && tags.size() >= 1) {
				lin_tag.setVisibility(View.VISIBLE);
				MultipleTextUtils mu = new MultipleTextUtils(multipleText, tags);
				mu.AddTag();
			} else {
				lin_tag.setVisibility(View.GONE);
			}
			// if (!TextUtils.isEmpty(actor.getIntroVideoLink())) {
			// videoUtils=new VideoWindowsUtils(ActorCenterActivity.this, 0,
			// "http://7xptra.com1.z0.glb.clouddn.com/Be%20My%20Lover.mp4",159);
			// videoUtils.showBigView(159);
			// int[] xy=new int[2];
			// view_video.getLocationInWindow(xy);
			// Log.i("canshu", Arrays.toString(xy));
			// videoUtils.moveView(xy);
			// }
		}
	}

	private void addPhoto(ArrayList<ActorPhotoList> pList, LinearLayout plins) {
		if (pList == null || pList.size() < 1) {
			horscroll.setVisibility(View.GONE);
			return;
		}
		horscroll.setVisibility(View.VISIBLE);
		DisplayImageOptions options = ImageLoadUtils.getMainPersionOptions();
		int imageHight = MPlusApplication.Height * 320 / 1920;
		plins.removeAllViews();
		final ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < pList.size();  i++) {
			list.add(pList.get(i).getMaterialUrl());
		}
		for (int i = 0; i < pList.size(); i++) {
			final String url = pList.get(i).getMaterialUrl();
			ImageView iv = new ImageView(ActorCenterActivity.this);
			iv.setScaleType(ScaleType.CENTER_CROP);
			Map<String, String> map = UrlStingTool.URLRequest(url);
			String width = map.get("width");
			String height = map.get("height");
			LinearLayout.LayoutParams lp;
			if (TextUtils.isEmpty(width) || TextUtils.isEmpty(height)) {
				lp = new LinearLayout.LayoutParams(imageHight, imageHight);
			} else {
				lp = new LinearLayout.LayoutParams(Integer.valueOf(width)* imageHight / Integer.valueOf(height), imageHight);
			}
			lp.setMargins(0, 0, 15, 0);
			iv.setLayoutParams(lp);
			ImageLoader.getInstance().displayImage(url, iv, options);
			iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(ActorCenterActivity.this,PictureBigLookActivity.class);
					intent.putExtra("selcedPic", url);
					intent.putExtra("images", list);
					startActivity(intent);
				}
			});
			plins.addView(iv);

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==LoginUtils.TOLOGIN && resultCode==RESULT_OK) {
			userid = UserPaser.GetInstance().userid;
			AddCollect();
		}else if (requestCode==LoginUtils.TOLOGIN2 && resultCode==RESULT_OK) {
			userid = UserPaser.GetInstance().userid;
			AddOption();
		}
	}
	
	
	private void AddCollect(){
		showLoadingDialog();
		if (tv_collect.getText().toString().equals(getString(R.string.collect_cannelcollect))) {
			RequestManger.DeleteFavorite(ActorCenterActivity.this, userid,actoruserid, new Listener<RequestCall>() {

						@Override
						public void onResponse(RequestCall response) {
							dismissLoadingDialog();
							if (response.getParser().getResultSuccess()) {
								ToastTool.showShortToast(ActorCenterActivity.this, "已取消收藏！");
								tv_collect.setText(getString(R.string.actor_user_add_collect));
							} else {
								if (response.getParser().getResponseMsg() != null)
									ToastTool.showShortToast(ActorCenterActivity.this,response.getParser().getResponseMsg());
							}
						}
					}, errorListener);
		} else {
			RequestManger.PostAddFavorite(ActorCenterActivity.this, userid,actoruserid, new Listener<RequestCall>() {

						@Override
						public void onResponse(RequestCall response) {
							dismissLoadingDialog();
							if (response.getParser().getResultSuccess()) {
								ToastTool.showShortToast(ActorCenterActivity.this,"已添加到收藏！");
								tv_collect.setText(getString(R.string.collect_cannelcollect));
							} else {
								if (response.getParser().getResponseMsg() != null)
									ToastTool.showShortToast(ActorCenterActivity.this,response.getParser().getResponseMsg());
							}
						}
					}, errorListener);
		}
	}
	
	private void AddOption(){
		final String planid = getIntent().getStringExtra("planid");
		if (TextUtils.isEmpty(planid)) {
			Intent intent = new Intent(ActorCenterActivity.this,SelcedUserProjectActivity.class);
			intent.putExtra("userid", userid);
			intent.putExtra("actoruserid", actoruserid);
			startActivity(intent);
		} else {
			DialongUtils.showAskOptionDialog(ActorCenterActivity.this,userid, actoruserid, planid);
		}
	}
	
}
