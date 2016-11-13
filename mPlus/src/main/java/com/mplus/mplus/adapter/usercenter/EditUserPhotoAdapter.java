package com.mplus.mplus.adapter.usercenter;

import java.util.ArrayList;
import java.util.Map;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.actorcenter.PictureBigLookActivity;
import com.mplus.mplus.adapter.usercenter.EditUserPhotoAdapter.MyViewHolder;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorPhotoList;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.mplus.mplus.utils.ToastTool;
import com.mplus.mplus.utils.UrlStingTool;
import com.mplus.mplus.view.MyDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EditUserPhotoAdapter  extends RecyclerView.Adapter<MyViewHolder>{
	private Context mContext;
	private ArrayList<ActorPhotoList> list;
	private String customerid;
	private boolean canDelete=true;
	private DisplayImageOptions options;
	
	public EditUserPhotoAdapter(Context mContext, ArrayList<ActorPhotoList> list, String customerid ,boolean canDelete) {
		super();
		this.mContext = mContext;
		this.list = list;
		this.customerid = customerid;
		this.canDelete=canDelete;
		this.options=ImageLoadUtils.getMainPersionOptions();
	}


	public void notfit(ArrayList<ActorPhotoList> lists){
		if (lists!=null && lists.size()>=1) {
			list=new ArrayList<ActorPhotoList>();
			list.addAll(lists);
			EditUserPhotoAdapter.this.notifyDataSetChanged();
		}
	}
	
	@Override
	public int getItemCount() {
		return list == null ? 1 : list.size() ;
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		final ActorPhotoList photo=list.get(position);
		int imagewidth=MPlusApplication.Width*480/1080;
		if (photo!=null) {
			String url=photo.getMaterialUrl();
			Map<String, String> map=UrlStingTool.URLRequest(url);
			String width=map.get("width");
			String height=map.get("height");
			RelativeLayout.LayoutParams rp;
			if (TextUtils.isEmpty(width) || TextUtils.isEmpty(height)) {
				rp=new RelativeLayout.LayoutParams(imagewidth, imagewidth);
			}else {
				int h=Integer.valueOf(height)*imagewidth/Integer.valueOf(width);
				rp=new RelativeLayout.LayoutParams(imagewidth, h);
			}
			holder.iv_ico.setLayoutParams(rp);
			ImageLoader.getInstance().displayImage(photo.getMaterialUrl(), holder.iv_ico, options);
			holder.iv_ico.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(mContext,PictureBigLookActivity.class);
					intent.putExtra("selcedPic", photo.getMaterialUrl());
					intent.putExtra("images", getImages());
					mContext.startActivity(intent);
				}
			});
			if (canDelete) {
				holder.iv_delete.setOnClickListener(new OnClickListener() {
						
					@Override
					public void onClick(View v) {
						showTip(mContext, photo);
					}
				});
			}
		}

	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.items_edituserphoto, arg0, false));
		return holder;
	}

	
	
	class MyViewHolder extends ViewHolder {
		ImageView iv_ico,iv_delete;
		public MyViewHolder(View view) {
			super(view);
			iv_ico=(ImageView) view.findViewById(R.id.iv_ico);
			iv_delete=(ImageView) view.findViewById(R.id.iv_delete);
			if (canDelete) {
				iv_delete.setVisibility(View.VISIBLE);
			}else {
				iv_delete.setVisibility(View.GONE);
			}
			AutoUtils.autoSize(view);
		}
	}
	
	private ArrayList<String> getImages() {
		ArrayList<String> lists=new ArrayList<String>();
		for (ActorPhotoList photo : list) {
			lists.add(photo.getMaterialUrl());
		}
		return lists;
	}
	
	
	private void showTip(Context context,final ActorPhotoList photo) {
		View view = View.inflate(context, R.layout.dialog_forget,null);
		AutoUtils.autoSize(view);
		final MyDialog dialog = new MyDialog(context,R.style.CustomDialog);
		LinearLayout.LayoutParams dialoglp = new LinearLayout.LayoutParams(MPlusApplication.Width * 4 / 5,LinearLayout.LayoutParams.WRAP_CONTENT);
		dialog.addContentView(view, dialoglp);
		dialog.setCancelable(true);
		dialog.show();
		TextView tv_tip=(TextView) view.findViewById(R.id.tv_tip);
		TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
		TextView tv_cacel = (TextView) view.findViewById(R.id.tv_cancel);
		tv_tip.setText("是否确认删除该图片？");
		tv_sure.setText("确认");
		tv_cacel.setText("取消");
		tv_cacel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (dialog!=null && dialog.isShowing()) 
					dialog.dismiss();
			}
		});
		tv_sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (dialog!=null && dialog.isShowing()) 
					dialog.dismiss();
				RequestManger.DeleteActorPhotos(mContext, customerid, photo.getId(), new Listener<RequestCall>() {
					
					@Override
					public void onResponse(RequestCall response) {
						if (response.getParser().getResultSuccess()) {
							ToastTool.showShortToast(mContext, "删除艺人图片成功！");
							list.remove(photo);
							EditUserPhotoAdapter.this.notifyDataSetChanged();
						}else {
							if (response.getParser().getResponseMsg()!=null) 
								ToastTool.showShortToast(mContext, response.getParser().getResponseMsg());
						}
					}
				}, MHttpTools.getErrorListener(mContext));
			}
		});
	}
}
