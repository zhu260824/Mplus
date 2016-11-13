package com.mplus.mplus.adapter.main;

import java.util.ArrayList;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.actorcenter.ActorCenterActivity;
import com.mplus.mplus.activity.actorcenter.SelcedUserProjectActivity;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorList;
import com.mplus.mplus.utils.DialongUtils;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.mplus.mplus.utils.ToastUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectFragmentAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<ActorList> list;
	private DisplayImageOptions options;
	private String userid;
//	private long clickTime=System.currentTimeMillis();
	protected Dialog loadingDialog;
	public CollectFragmentAdapter(Context mContext, ArrayList<ActorList> list,
			String userid) {
		super();
		this.mContext = mContext;
		this.list = list;
		this.userid = userid;
		this.options = ImageLoadUtils.getImageOptions();
	}

	public void notifyDataSetChanged(ArrayList<ActorList> lists) {
		if (lists != null) {
			this.list.addAll(lists);
			this.notifyDataSetChanged();
		}
	};

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		ImageView iv_ico;
		TextView tv_name, tv_height, tv_weight, tv_age;
		Button tv_delete, tv_add_option;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.items_collect_stars,null);
			holder.iv_ico = (ImageView) convertView.findViewById(R.id.iv_ico);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_height = (TextView) convertView.findViewById(R.id.tv_height);
			holder.tv_weight = (TextView) convertView.findViewById(R.id.tv_weight);
			holder.tv_age = (TextView) convertView.findViewById(R.id.tv_age);
			holder.tv_delete = (Button) convertView.findViewById(R.id.tv_delete);
			holder.tv_add_option = (Button) convertView.findViewById(R.id.tv_add_option);
			AutoUtils.auto(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ActorList actor = list.get(position);
		if (actor != null) {
			ImageLoader.getInstance().displayImage(actor.getAvatar(),holder.iv_ico, options);
			holder.tv_name.setText(TextUtils.isEmpty(actor.getName()) ? "": actor.getName());
			if (!TextUtils.isEmpty(actor.getHeight()) &&  !actor.getHeight().equals("0")) {
				holder.tv_height.setVisibility(View.VISIBLE);
				holder.tv_height.setText(actor.getHeight()+"cm");
			}else {
				holder.tv_height.setVisibility(View.GONE);
			}
			
			if (!TextUtils.isEmpty(actor.getWeight()) &&  !actor.getWeight().equals("0")) {
				holder.tv_weight.setVisibility(View.VISIBLE);
				holder.tv_weight.setText(actor.getWeight()+"kg");
			}else {
				holder.tv_weight.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(actor.getAge()) &&  !actor.getAge().equals("0")) {
				holder.tv_age.setVisibility(View.VISIBLE);
				holder.tv_age.setText(actor.getAge()+"岁");
			}else {
				holder.tv_age.setVisibility(View.GONE);
			}
			holder.iv_ico.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext,ActorCenterActivity.class);
					intent.putExtra(ActorCenterActivity.CUSTOMERID,actor.getCustomerId());
					mContext.startActivity(intent);
				}
			});
			holder.tv_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
//					if (System.currentTimeMillis()-clickTime>=500) {
//						clickTime=System.currentTimeMillis();
					showLoadingDialog();
						if (holder.tv_delete.getText().equals("取消收藏")) {
							RequestManger.DeleteFavorite(mContext, userid,actor.getUserId(), new Listener<RequestCall>() {

										@Override
										public void onResponse(RequestCall response) {
											dismissLoadingDialog();
											if (response.getParser().getResultSuccess()) {
												holder.tv_delete.setText("加入收藏");
												ToastUtils.showToastOnce(mContext,"取消收藏");
											}
										}
									}, new ErrorListener() {

										@Override
										public void onErrorResponse(
												VolleyError error) {
											dismissLoadingDialog();
											
										}
									});
						}

						if (holder.tv_delete.getText().equals("加入收藏")) {
							showLoadingDialog();
							RequestManger.PostAddFavorite(mContext, userid,actor.getUserId(), new Listener<RequestCall>() {

										@Override
										public void onResponse(RequestCall response) {
											dismissLoadingDialog();
											if (response.getParser().getResultSuccess()) {
												holder.tv_delete.setText("取消收藏");
												ToastUtils.showToastOnce(mContext,"收藏成功");
											}
										}
									}, new ErrorListener() {

										@Override
										public void onErrorResponse(
												VolleyError error) {
											dismissLoadingDialog();
											
										}
									});

						}
//					}else {
//						ToastTool.showLongToast(mContext, "您点击过于频繁！");
//					}
				}
			});
			holder.tv_add_option.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext,SelcedUserProjectActivity.class);
					intent.putExtra("userid", userid);
					intent.putExtra("actoruserid", actor.getUserId());
					mContext.startActivity(intent);
				}
			});
		}
		return convertView;
	}

	protected void showLoadingDialog(){
		if (loadingDialog==null) 
			loadingDialog=DialongUtils.showLoading(mContext);
		loadingDialog.show();
	}
	
	protected void dismissLoadingDialog(){
		DialongUtils.dismissLoading(loadingDialog);
	}
	
}
