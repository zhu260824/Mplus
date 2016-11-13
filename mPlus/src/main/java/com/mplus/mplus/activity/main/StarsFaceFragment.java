
package com.mplus.mplus.activity.main;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.Utils.MobclickAgentUtils;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseFragment;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorList;
import com.mplus.mplus.paser.actor.ActorListPaser;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.mplus.mplus.view.RippleView;
import com.mplus.mplus.view.RippleView.OnRippleCompleteListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StarsFaceFragment extends BaseFragment {

	private RippleView main_starsface_face_ry;
	private ImageView sf_iv;
	private EditText et_content;
	private TextView tv_search, tv_name;
	private String key = "";
	private String starFaceId;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = View.inflate(mActivity, R.layout.fragment_main_starsface,null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		main_starsface_face_ry = (RippleView) view.findViewById(R.id.main_starsface_face_ry);
		sf_iv = (ImageView) view.findViewById(R.id.sf_iv);
		et_content = (EditText) view.findViewById(R.id.et_content);
		tv_search = (TextView) view.findViewById(R.id.tv_search);
		tv_name = (TextView) view.findViewById(R.id.tv_name);
		tv_name.setText("范冰冰");
		tv_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				key = et_content.getText().toString().trim();
				if (key.equals("")) {
					Toast.makeText(mActivity, "请输入明星名称!", Toast.LENGTH_SHORT).show();
				} else {
					RequestManger.GetActorStartFace(mActivity, 1, 10, key,new Listener<RequestCall>() {

								@Override
								public void onResponse(RequestCall response) {
									if (response.getParser().getResultSuccess())
										initViewData(ActorListPaser.GetInstance(2));
									MobclickAgentUtils.MobclickEventName(mActivity, "0007", "明星脸", key);
								}
							}, errorListener);
				}
			}
		});

		main_starsface_face_ry.setOnRippleCompleteListener(new OnRippleCompleteListener() {

					@Override
					public void onComplete(RippleView rippleView) {
						if (starFaceId!=null) {
							Intent i = new Intent(mActivity,
									StartsFaceSearchResultActivity.class);
									i.putExtra("starFaceId", starFaceId);
							startActivity(i);
						}else{
							Toast.makeText(mActivity, "请输入明星名称!", Toast.LENGTH_SHORT).show();
						}
						
					}
				});
		RequestManger.GetActorStartFace(mActivity, 1, 10, "范冰冰",new Listener<RequestCall>() {

			@Override
			public void onResponse(RequestCall response) {
				if (response.getParser().getResultSuccess())
					initViewData(ActorListPaser.GetInstance(2));
			}
		}, errorListener);
	}

	private void initViewData(ArrayList<ActorList> list) {
		if (list.size()>0) {
			ActorList actor = list.get(0);
			tv_name.setText(actor.getName());
			ImageLoader.getInstance().displayImage(actor.getStarAvatar(), sf_iv, ImageLoadUtils.getImageOptions());
			starFaceId = actor.getStarFaceId();
		}else{
			Toast.makeText(mActivity, "没有找到姓名是"+key+"的明星脸！", Toast.LENGTH_SHORT).show();
		}
		
	}

	@Override
	public boolean onBackPressed() {
		return false;
	}
}
