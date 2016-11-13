package com.mplus.mplus.adapter.common;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewHolder {
	private SparseArray<View> mViews;
	private int position;
	private View convertView;
	
	public ViewHolder(Context context, ViewGroup viewGroup ,int layoutId , int position) {
		super();
		this.mViews = new SparseArray<View>();
		this.position = position;
		this.convertView = LayoutInflater.from(context).inflate(layoutId, viewGroup,false);
		this.convertView.setTag(ViewHolder.this);
	}
	
	public static ViewHolder getViewHolder(Context context, View convertView , ViewGroup viewGroup ,int layoutId , int position){
		if (convertView==null) {
			return new ViewHolder(context, viewGroup, layoutId, position);
		}else {
			ViewHolder viewHolder=(ViewHolder) convertView.getTag();
			viewHolder.position=position;
			return viewHolder;
		}
	}
	
	/**
	 * 通过viewId获取控件
	 * @param viewId	
	 * @return
	 * */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId){
		View view=mViews.get(viewId);
		if (view==null) {
			view=convertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public int getPosition() {
		return position;
	}

	public View getConvertView() {
		return convertView;
	}
	
	/**
	 * 通过viewId设置TextView的内容
	 * @param viewId	
	 * @return
	 * */
	public ViewHolder setText(int viewId,String text){
		TextView textView=getView(viewId);
		textView.setText(text);
		return ViewHolder.this;
	}
	
}
