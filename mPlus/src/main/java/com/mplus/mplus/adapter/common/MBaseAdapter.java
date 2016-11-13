package com.mplus.mplus.adapter.common;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MBaseAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected int layoutId;
	protected List<T> mDatas;
	
	public MBaseAdapter(Context mContext, int layoutId, List<T> mDatas) {
		super();
		this.mContext = mContext;
		this.layoutId = layoutId;
		this.mDatas = mDatas;
	}

	@Override
	public int getCount() {
		return mDatas==null?0:mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=ViewHolder.getViewHolder(mContext, convertView, parent, layoutId, position);
		setView(holder,getItem(position));
		return holder.getConvertView();
	}
	
	/**
	 * 替代Adapter的getView()方法
	 * */
	public abstract void setView(ViewHolder holder,T t);
}
