package com.mplus.mplus.adapter.main;

import java.util.ArrayList;

import com.mplus.mplus.R;
import com.mplus.mplus.activity.actorcenter.ActorCenterActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.paser.actor.ActorList;
import com.mplus.mplus.utils.ImageLoadUtils;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PersonFragmentAdapter extends BaseAdapter {
	private ArrayList<ActorList> lists;
	private Context mContext;
	private DisplayImageOptions options;

	public PersonFragmentAdapter(ArrayList<ActorList> lists, Context mContext) {
		super();
		this.lists = lists;
		this.mContext = mContext;
		this.options = ImageLoadUtils.getMainPersionOptions();
	}

	public void notifyDataSetChanged(ArrayList<ActorList> list) {
		if (list != null) {
			this.lists.addAll(list);
			this.notifyDataSetChanged();;
		}
	}

	@Override
	public int getCount() {
		return lists == null ? 1 : lists.size() + 1;
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position - 1);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		ImageView iv_ico;
		TextView tv_name, tv_height, tv_weight;
		LinearLayout lin_text, lin_image;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext,R.layout.items_gridview_main_person_image, null);
			holder.iv_ico = (ImageView) convertView.findViewById(R.id.iv_ico);
			// holder.iv_sex=(ImageView) convertView.findViewById(R.id.iv_sex);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_height = (TextView) convertView.findViewById(R.id.tv_height);
			holder.tv_weight = (TextView) convertView.findViewById(R.id.tv_weight);
			holder.lin_text = (LinearLayout) convertView.findViewById(R.id.lin_text);
			holder.lin_image = (LinearLayout) convertView.findViewById(R.id.lin_image);
			AutoUtils.auto(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (position == 0) {
			holder.lin_image.setVisibility(View.GONE);
			holder.lin_text.setVisibility(View.VISIBLE);
		} else {
			holder.lin_image.setVisibility(View.VISIBLE);
			holder.lin_text.setVisibility(View.GONE);
			RelativeLayout.LayoutParams ss=new RelativeLayout.LayoutParams(MPlusApplication.Width/2, RelativeLayout.LayoutParams.WRAP_CONTENT);
			holder.lin_image.setLayoutParams(ss);
			holder.lin_image.setPadding(10, 10, 10, 10);
			final ActorList actor = lists.get(position - 1);
			if (actor != null) {
				ImageLoader.getInstance().displayImage(actor.getAvatar(),holder.iv_ico, options);
				/*
				 * if (!TextUtils.isEmpty(actor.getSex())) { if
				 * (actor.getSex().equals("男")) {
				 * holder.iv_sex.setImageResource(R.drawable.image_gender_man);
				 * }else {
				 * holder.iv_sex.setImageResource(R.drawable.image_gender_woman
				 * ); } }
				 */
				holder.tv_name.setText(TextUtils.isEmpty(actor.getName()) ? "": actor.getName());
				holder.tv_height.setText(TextUtils.isEmpty(actor.getHeight()) ? "0cm": actor.getHeight() + "cm");
				holder.tv_weight.setText(TextUtils.isEmpty(actor.getWeight()) ? "0kg": actor.getWeight() + "kg");
				convertView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(mContext,ActorCenterActivity.class);
						intent.putExtra(ActorCenterActivity.CUSTOMERID,actor.getCustomerId());
						mContext.startActivity(intent);
					}
				});
			}
		}
		return convertView;
	}

}
