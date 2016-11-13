package com.mplus.mplus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ExpandableListView;

public class ExpandableListViewForScrollView extends ExpandableListView {
	 public ExpandableListViewForScrollView(Context context) {
	        super(context);
	    }
	    public ExpandableListViewForScrollView(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }
	    public ExpandableListViewForScrollView(Context context, AttributeSet attrs,
	        int defStyle) {
	        super(context, attrs, defStyle);
	    }
	    @Override
	    /**
	     * 重写该方法，达到使ListView适应ScrollView的效果
	     */
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
	        super.onMeasure(widthMeasureSpec, expandSpec);
	    }
	    
	    @Override
	    public boolean dispatchTouchEvent(MotionEvent ev) {
	    	getParent().requestDisallowInterceptTouchEvent(false); //让父类不拦截触摸事件
//	    	if (ev.getAction()==MotionEvent.ACTION_MOVE) {
//	    		return false;
//			}else {
//				return super.dispatchTouchEvent(ev);
//			}
	    	
	    	
	    	return super.dispatchTouchEvent(ev);
//	    	return false;
	    }
}
