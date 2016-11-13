package com.mplus.mplus.adapter.usercenter;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.handmark.pulltorefresh.library.SwipeListView;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.usercenter.EditActorHistoyDialog;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.actor.ActorPerform;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.mplus.mplus.utils.ToastTool;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class EditActorHistoryAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<ActorPerform> list;
	private DisplayImageOptions options;
	private int mRightWidth = 0;
	private SwipeListView mSwipeListView;  

	public EditActorHistoryAdapter(Context mContext, ArrayList<ActorPerform> list, int mRightWidth,SwipeListView mSwipeListView) {
		super();
		this.mContext = mContext;
		this.list = list;
		this.mRightWidth = mRightWidth;
		this.mSwipeListView=mSwipeListView;
		this.options=ImageLoadUtils.getActorHistoryOptions();
	}


	@Override
	public int getCount() {
		return list==null?0:list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	private class ViewHolder{
		ImageView iv_ico;
		TextView tv_name,tv_time,tv_desc,tv_delete;
		LinearLayout lin_content;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView==null) {
			holder=new ViewHolder();
			convertView=View.inflate(mContext, R.layout.items_edit_history,null);
			holder.iv_ico=(ImageView) convertView.findViewById(R.id.iv_ico);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_time=(TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_desc=(TextView) convertView.findViewById(R.id.tv_desc);
			holder.tv_delete=(TextView) convertView.findViewById(R.id.tv_delete);
			holder.lin_content=(LinearLayout) convertView.findViewById(R.id.lin_content);
			LinearLayout.LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			holder.lin_content.setLayoutParams(lp1);
		    LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
		    holder.tv_delete.setLayoutParams(lp2);
			AutoUtils.auto(convertView);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		final ActorPerform perform=list.get(position);
		if (perform!=null) {
			if (!TextUtils.isEmpty(perform.getTitle())) {
				holder.tv_name.setVisibility(View.VISIBLE);
				holder.tv_name.setText("《"+perform.getTitle()+"》");
			}else {
				holder.tv_name.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(perform.getTime())) {
				holder.tv_time.setVisibility(View.VISIBLE);
				holder.tv_time.setText(perform.getTime()+"年");
			}else {
				holder.tv_time.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(perform.getRole())) {
				holder.tv_desc.setVisibility(View.VISIBLE);
				holder.tv_desc.setText("饰演："+perform.getRole());
			}else {
				holder.tv_desc.setVisibility(View.GONE);
			}
			ImageLoader.getInstance().displayImage(perform.getMaterialUrl(), holder.iv_ico, options);
			holder.tv_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					RequestManger.DeleteActorHistory(mContext, UserPaser.GetInstance().customerid, perform.getId(), new Listener<RequestCall>() {

						@Override
						public void onResponse(RequestCall response) {
							if (response.getParser().getResultSuccess()) {
								ToastTool.showShortToast(mContext, "删除演艺经历成功！");
								list.remove(perform);
								EditActorHistoryAdapter.this.notifyDataSetChanged();
								if (mSwipeListView!=null) 
									mSwipeListView.resetItems();
							}else {
								if (response.getParser().getResponseMsg()!=null) 
									ToastTool.showShortToast(mContext, response.getParser().getResponseMsg());
							}
						}
					}, MHttpTools.getErrorListener(mContext));
				}
			});
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(mContext,EditActorHistoyDialog.class);
					intent.putExtra("perform", perform);
					intent.putExtra("IntentType", 2);
					mContext.startActivity(intent);
				}
			});
		}
		return convertView;
	}

}
