package com.mplus.mplus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 重写ScrollView,以解决ScrollView与水平listView滑动时冲突
 */
public class MyScrollView extends ScrollView {
	private GestureDetector mGestureDetector;
	private ScrollViewListener scrollViewListener = null;  

	public MyScrollView(Context context) {
		super(context);
		mGestureDetector = new GestureDetector(context,new YScrollDetector());
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGestureDetector = new GestureDetector(context,new YScrollDetector());
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mGestureDetector = new GestureDetector(context,new YScrollDetector());
	}

	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
	}

	class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			  /** 
             * 如果我们滚动更接近水平方向,返回false,让子视图来处理它 
             */  
            return (Math.abs(distanceY) > Math.abs(distanceX));  
		}
	}

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {  
        this.scrollViewListener = scrollViewListener;  
    }  
  
    @Override  
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {  
        super.onScrollChanged(x, y, oldx, oldy);  
        if (scrollViewListener != null) {  
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);  
        }  
    }  
}