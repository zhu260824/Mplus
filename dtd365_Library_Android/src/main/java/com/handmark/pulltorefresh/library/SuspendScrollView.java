package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class SuspendScrollView extends ScrollView{
	private SuspendScrollViewListener scrollViewListener;

	public SuspendScrollView(Context context) {
		super(context);
	}
	
	public SuspendScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public SuspendScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public void setScrollViewListener(SuspendScrollViewListener scrollViewListener) {  
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
