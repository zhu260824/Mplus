package com.mplus.mplus.adapter.main;

import java.util.ArrayList;

import com.dtd365.library.Utils.MobclickAgentUtils;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.actorcenter.ActorCenterActivity;
import com.mplus.mplus.adapter.main.PersonFragmentRecyclerAdapter.MyViewHolder;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.paser.actor.ActorList;
import com.mplus.mplus.utils.ImageLoadUtils;
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

public class PersonFragmentRecyclerAdapter extends
		RecyclerView.Adapter<MyViewHolder> {
	private ArrayList<ActorList> lists;
	private Context mContext;
	private DisplayImageOptions options;
//	private OnRecyclerViewItemClickListener mOnItemClickListener = null;

	public PersonFragmentRecyclerAdapter(ArrayList<ActorList> lists,
			Context mContext) {
		super();
		this.lists = lists;
		this.mContext = mContext;
		this.options = ImageLoadUtils.getMainPersionOptions();
		
	}

	public void notifyDataSetChanged(ArrayList<ActorList> list) {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				lists.add(list.get(i));
			}
			notifyDataSetChanged();
		}
	}
	
	
	public void refreshdata(ArrayList<ActorList> list) {
		if (list.size() > 0) {
			lists.clear();
			for (int i = 0; i < list.size(); i++) {
				lists.add(list.get(i));
			}
			notifyDataSetChanged();
			
		}
	}
	
	

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return lists == null ? 1 : lists.size() + 1;
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		if (position == 0) {
			holder.lin_image.setVisibility(View.GONE);
			holder.lin_text.setVisibility(View.VISIBLE);
		} else {
			holder.lin_image.setVisibility(View.VISIBLE);
			holder.lin_text.setVisibility(View.GONE);
			RelativeLayout.LayoutParams ss = new RelativeLayout.LayoutParams(
					MPlusApplication.Width / 2,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			holder.lin_image.setLayoutParams(ss);
			holder.lin_image.setPadding(10, 10, 10, 10);
			final ActorList actor = lists.get(position - 1);
			if (actor != null) {
				ImageLoader.getInstance().displayImage(actor.getAvatar(),holder.iv_ico, options);
				holder.tv_name.setText(TextUtils.isEmpty(actor.getName()) ? "": actor.getName());
				if (!TextUtils.isEmpty(actor.getHeight()) && !actor.getHeight().equals("0")) {
					holder.tv_height.setVisibility(View.VISIBLE);
					holder.tv_height.setText(actor.getHeight() + "cm");
				}else {
					holder.tv_height.setVisibility(View.GONE);
				}
				
				if (!TextUtils.isEmpty(actor.getWeight()) && !actor.getWeight().equals("0")) {
					holder.tv_weight.setVisibility(View.VISIBLE);
					holder.tv_weight.setText(actor.getWeight() + "kg");
				}else {
					holder.tv_weight.setVisibility(View.GONE);
				}
//				holder.lin_text.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						Intent intent = new Intent(mContext,
//								ActorCenterActivity.class);
//						intent.putExtra(ActorCenterActivity.CUSTOMERID,
//								actor.getCustomerId());
//						mContext.startActivity(intent);
//					}
//				});
			}
		}

	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.items_gridview_main_person_image, arg0, false));
		return holder;
	}

	
//    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
//    this.mOnItemClickListener = listener;
//}
//	public static interface OnRecyclerViewItemClickListener {
//	    void onItemClick(View view , ActorList data);
//	}
	
	class MyViewHolder extends ViewHolder {
		ImageView iv_ico;
		TextView tv_name, tv_height, tv_weight;
		LinearLayout lin_text, lin_image;

		public MyViewHolder(View view) {
			super(view);
			iv_ico = (ImageView) view.findViewById(R.id.iv_ico);
			tv_name = (TextView) view.findViewById(R.id.tv_name);
			tv_height = (TextView) view.findViewById(R.id.tv_height);
			tv_weight = (TextView) view.findViewById(R.id.tv_weight);
			lin_text = (LinearLayout) view.findViewById(R.id.lin_text);
			lin_image = (LinearLayout) view.findViewById(R.id.lin_image);
			AutoUtils.auto(view);
			itemView.setOnClickListener(new OnClickListener() {  
                @Override  
                public void onClick(View v) {  
                	if (getPosition()!=0) {
                		ActorList actor = lists.get(getPosition()-1);
                		Intent intent = new Intent(mContext,ActorCenterActivity.class);
                		intent.putExtra(ActorCenterActivity.CUSTOMERID,actor.getCustomerId());
                		MobclickAgentUtils.MobclickEventName(mContext,"0003","查看演员详情",actor.getName());
                		mContext.startActivity(intent);
                	}
                }
            });
		}
	}
}
