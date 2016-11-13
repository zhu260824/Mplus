package com.mplus.mplus.utils;

import java.util.ArrayList;

import com.mplus.mplus.R;
import com.mplus.mplus.view.multipletext.MultipleTextViewGroup;
import com.mplus.mplus.view.multipletext.MultipleTextViewGroup.OnMultipleTVItemClickListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class MultipleTextUtils {
	private MultipleTextViewGroup multipleText;
	private ArrayList<String> tags;
	private ArrayList<String> selcedTag;
	
	public MultipleTextUtils(MultipleTextViewGroup multipleText, ArrayList<String> tags) {
		super();
		this.multipleText = multipleText;
		this.tags = tags;
	}

	public MultipleTextUtils(MultipleTextViewGroup multipleText, ArrayList<String> tags, ArrayList<String> selcedTag) {
		super();
		this.multipleText = multipleText;
		this.tags = tags;
		this.selcedTag = selcedTag;
	}


	public  void AddTag(){
		isNull();
	}
	
	public  void AddTagWithDelete(){
		isNull();
		multipleText.setOnMultipleTVItemClickListener(new OnMultipleTVItemClickListener() {
			
			@Override
			public void onMultipleTVItemClick(View view, String textContent) {
				if (tags.remove(textContent)) {
					isNull();
				}
			}
		});;
	}
	
	public  void AddTagWithClick(OnMultipleTVItemClickListener clickListener){
		isNull();
		multipleText.setOnMultipleTVItemClickListener(clickListener);
	}
	
	public void AddTagWithSelced(final Context mContext){
		isNull();
		multipleText.setOnMultipleTVItemClickListener(new OnMultipleTVItemClickListener() {
			
			@SuppressLint("ResourceAsColor")
			@Override
			public void onMultipleTVItemClick(View view,String textContent) {
				if (selcedTag==null) 
					selcedTag=new ArrayList<String>();
				if (view.getTag()==null || !((Boolean) view.getTag())) {
					view.setBackgroundResource(R.drawable.bg_text_backgroup_blue);
					((TextView)view).setTextColor(mContext.getResources().getColorStateList(R.color.text_blue_color));
					view.setTag(true);
					selcedTag.add(textContent);
				}else {
					view.setBackgroundResource(R.drawable.bg_text_backgroup_gray);
					((TextView)view).setTextColor(mContext.getResources().getColorStateList(R.color.text_light_grey));
					view.setTag(false);
					selcedTag.remove(textContent);
				}
			}
		});;
	}
	
	public ArrayList<String> getTags() {
		return tags;
	}

	public ArrayList<String> getSelcedTag() {
		return selcedTag;
	}

	private void isNull(){
		if (multipleText==null) 
			return;
		if (tags==null || tags.size()<=0) {
			multipleText.setVisibility(View.GONE);
			return;
		}
		if (selcedTag!=null && selcedTag.size()>=1) {
			multipleText.setTextViews(tags,selcedTag);
		}else {
			multipleText.setTextViews(tags);
		}
		
	}
	
	
	public MultipleTextUtils(MultipleTextViewGroup multipleText) {
		super();
		this.multipleText = multipleText;
	}

	public  void AddTag(ArrayList<String> tags){
		if (multipleText==null) 
			return;
		if (tags==null || tags.size()<=0) {
			multipleText.setVisibility(View.GONE);
			return;
		}
		multipleText.setTextViewsNocheck(tags);
	}
}
