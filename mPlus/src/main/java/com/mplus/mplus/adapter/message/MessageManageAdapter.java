package com.mplus.mplus.adapter.message;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mplus.mplus.R;
import com.mplus.mplus.paser.message.MessageBean;
import com.zhy.autolayout.utils.AutoUtils;



public class MessageManageAdapter extends BaseAdapter{
	private ArrayList<MessageBean> list;
	private Context context;
	private boolean isAllSelced=false,canSelced=false;

	public MessageManageAdapter(ArrayList<MessageBean> list,
			Context context, boolean isAllSelced, boolean canSelced) {
		super();
		this.list = list;
		this.context = context;
		this.isAllSelced = isAllSelced;
		this.canSelced = canSelced;
	}

	public void notifyData(ArrayList<MessageBean> list){
		for (int i = 0; i < list.size(); i++) {
			this.list.add(list.get(i));
		}
		this.notifyDataSetChanged();
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
	
	@Override
	public View getView(final int position, View convertView, ViewGroup viewGroup) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = View.inflate(context,R.layout.item_information_manage, null);
			holder = new ViewHolder();
//			holder.rl_information = (RelativeLayout) convertView.findViewById(R.id.rl_information);
			holder.iv_information_onoff = (ImageView) convertView.findViewById(R.id.iv_information_onoff);
			holder.iv_information_read_onoff = (ImageView) convertView.findViewById(R.id.iv_information_read_onoff);
			holder.tv_information_manage_content= (TextView) convertView.findViewById(R.id.tv_information_manage_content);
			holder.tv_information_time= (TextView) convertView.findViewById(R.id.tv_information_time);
			holder.tv_information_manage_content.setTag("");
			convertView.setTag(holder);
			AutoUtils.autoSize(convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final MessageBean msg = list.get(position);
		// 操作按钮隐显状态
		if (canSelced) {
			holder.iv_information_onoff.setVisibility(View.VISIBLE);
		} else {
			holder.iv_information_onoff.setVisibility(View.GONE);
		}
		// 操作按钮隐显状态
		if (isAllSelced) {
			holder.iv_information_onoff.setImageResource(R.drawable.message_on);
			holder.iv_information_onoff.setTag(true);
		} else {
			holder.iv_information_onoff.setImageResource(R.drawable.message_off);
			holder.iv_information_onoff.setTag(false);
		}
		// 阅读状态 0已读 1未读
		if (msg.openStatus.equals("1")) {
			holder.iv_information_read_onoff.setImageResource(R.drawable.message_read_off);
		} else {
			holder.iv_information_read_onoff.setImageResource(R.drawable.message_read_on);
		}
		// 时间字段
		holder.tv_information_time.setText(msg.createTime);
		// 文本内容
		holder.tv_information_manage_content.setText(msg.content);
		holder.tv_information_manage_content.setTag(msg.msgId);
		return convertView;
	}
	

	private class ViewHolder {
		/** 编辑时左边出现的状态栏显示是否选择该栏*/
//		private RelativeLayout rl_information;
		/** 显示左边是否被选中*/
		private ImageView iv_information_onoff;
		/** 显示是否为已读状态*/
		private ImageView iv_information_read_onoff;
		/** 显示的内容*/
		private TextView tv_information_manage_content;
		/** 显示的时间*/
		private TextView tv_information_time;
	}
}

