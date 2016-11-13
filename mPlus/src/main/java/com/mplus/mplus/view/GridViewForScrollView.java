package com.mplus.mplus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class GridViewForScrollView extends GridView {
	public GridViewForScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GridViewForScrollView(Context context) {
		super(context);
	}

	public GridViewForScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction()==MotionEvent.ACTION_MOVE) {
    		return false;
		}else {
			return super.dispatchTouchEvent(ev);
		}
	}
}