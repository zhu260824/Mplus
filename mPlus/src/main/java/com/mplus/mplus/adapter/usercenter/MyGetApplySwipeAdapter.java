
package com.mplus.mplus.adapter.usercenter;

import java.util.ArrayList;

import com.mplus.mplus.R;
import com.mplus.mplus.activity.actorcenter.ActorCenterActivity;
import com.mplus.mplus.paser.MyGetApplyList;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.utils.Log;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 我收到的申请
 * 
 * @author June
 *
 */
public class MyGetApplySwipeAdapter extends BaseAdapter {
	/**
	 * 上下文对象
	 */
	private Context mContext = null;

	private int mRightWidth = 0;

	/**
	 * 单击事件监听器
	 */
	private IOnItemRightClickListener mListener = null;
	private IOnItemRightClickListener2 mListener2 = null;
	private IOnItemRightClickListener3 mListener3 = null;

	private ArrayList<MyGetApplyList.contents> lists;
	private DisplayImageOptions options;

	public interface IOnItemRightClickListener {
		void onRightClick(View v, int position);
	}

	public interface IOnItemRightClickListener2 {
		void onRightClick(View v, int position);
	}

	public interface IOnItemRightClickListener3 {
		void onRightClick(View v, int position);
	}

	/**
	 * @param mainActivity
	 */
	public MyGetApplySwipeAdapter(Context ctx, int rightWidth, ArrayList<MyGetApplyList.contents> lists,
			IOnItemRightClickListener l1, IOnItemRightClickListener2 l2, IOnItemRightClickListener3 l3) {
		mContext = ctx;
		this.lists = lists;
		mRightWidth = rightWidth;
		mListener = l1;
		mListener2 = l2;
		mListener3 = l3;
	}

	@Override
	public int getCount() {
		return lists == null ? 0 : lists.size();
	}

	public void notifyDataSetChanged(ArrayList<MyGetApplyList.contents> lists) {
		if (lists != null) {
			this.lists.addAll(lists);
			this.notifyDataSetChanged();
		}
	};

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder item;
		final int thisPosition = position;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.items_mygetapply, parent, false);
			item = new ViewHolder();
			item.item_left = (View) convertView.findViewById(R.id.item_left);
			item.item_right = (View) convertView.findViewById(R.id.item_right);
			item.item_left_other_tv = (TextView) convertView.findViewById(R.id.item_left_other_tv);
			item.item_right_txt = (TextView) convertView.findViewById(R.id.item_right_txt);
			item.item_right_txt2 = (TextView) convertView.findViewById(R.id.item_right_txt2);
			item.item_right_txt3 = (TextView) convertView.findViewById(R.id.item_right_txt3);

			item.tv_time_my_get_apply = (TextView) convertView.findViewById(R.id.tv_time_my_get_apply);
			item.tv_name_my_get_apply = (TextView) convertView.findViewById(R.id.tv_name_my_get_apply);
			item.iv_pic_my_get_apply = (ImageView) convertView.findViewById(R.id.iv_pic_my_get_apply);
			item.iv_sex_my_get_apply = (ImageView) convertView.findViewById(R.id.iv_sex_my_get_apply);
			item.tv_height_my_get_apply = (TextView) convertView.findViewById(R.id.tv_height_my_get_apply);
			item.tv_weight_my_get_apply = (TextView) convertView.findViewById(R.id.tv_weight_my_get_apply);
			convertView.setTag(item);
		} else {// 有直接获得ViewHolder
			item = (ViewHolder) convertView.getTag();
		}

		LinearLayout.LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		item.item_left.setLayoutParams(lp1);
		LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
		item.item_right.setLayoutParams(lp2);

		final MyGetApplyList.contents project = lists.get(position);

		item.item_right_txt.setText("收藏");
		item.item_right_txt2.setText("备选");
		item.item_right_txt3.setText("忽略");

		item.item_left_other_tv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// item.scrollTo((int)(-300), 0);
				scroll(thisPosition);
			}
		});

		item.item_right_txt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onRightClick(v, thisPosition);
				}
			}
		});

		item.item_right_txt2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener2.onRightClick(v, thisPosition);
				}
			}
		});

		item.item_right_txt3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener3.onRightClick(v, thisPosition);
				}
			}
		});
		if (project != null) {
			item.tv_time_my_get_apply.setText(project.getApplyTime() == null ? "" : project.getApplyTime());
			item.tv_name_my_get_apply.setText(project.getName() == null ? "" : project.getName());
			item.iv_sex_my_get_apply = (ImageView) convertView.findViewById(R.id.iv_sex_my_get_apply);
			item.tv_height_my_get_apply.setText(project.getHeight() == null ? "" : project.getHeight());
			item.tv_weight_my_get_apply.setText(project.getWeight() == null ? "" : project.getWeight());
			ImageLoader.getInstance().displayImage(project.getAvatar(), item.iv_pic_my_get_apply, options);
			item.iv_pic_my_get_apply.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(mContext,ActorCenterActivity.class);
					intent.putExtra(ActorCenterActivity.CUSTOMERID,project.getCustomerId());
					mContext.startActivity(intent);
				}
			});
//			if(project.==null){
//				holder.iv_sex_alternative.setVisibility(View.GONE);
//			}else{
//				holder.iv_sex_alternative.setVisibility(View.VISIBLE);
//				holder.iv_sex_alternative.setBackgroundResource(sProject.getSex().equals("男") ? R.drawable.man: R.drawable.woman);
//			}
		}
		return convertView;
	}

	protected void scroll(int index) {
		View v = (View) getItem(index);
		Log.e("MyGetApplySwipeAdapter", v.toString());
		v.scrollTo(-300, 0);

	}

	private class ViewHolder {
		View item_left;
		View item_right;
		TextView item_left_other_tv;
		TextView item_right_txt;
		TextView item_right_txt2;
		TextView item_right_txt3;
		TextView tv_height_my_get_apply;
		TextView tv_name_my_get_apply;
		TextView tv_weight_my_get_apply;
		TextView tv_time_my_get_apply;
		ImageView iv_pic_my_get_apply;
		ImageView iv_sex_my_get_apply;
	}
}
