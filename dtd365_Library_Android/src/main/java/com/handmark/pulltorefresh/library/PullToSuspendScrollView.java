package com.handmark.pulltorefresh.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import cn.mplus.dtd365_library_android.R;

public class PullToSuspendScrollView extends PullToRefreshBase<SuspendScrollView> implements SuspendScrollViewListener{
	private SuspendScrollView mScrollView;
	private int tabLayoutTop=0;
	private ViewGroup suspend,newsuspend,oldsuspend;
	public PullToSuspendScrollView(Context context) {
		super(context);
	}

	public PullToSuspendScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullToSuspendScrollView(Context context, Mode mode) {
		super(context, mode);
	}

	public PullToSuspendScrollView(Context context, Mode mode, AnimationStyle style) {
		super(context, mode, style);
	}

	@Override
	public final Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	@Override
	protected SuspendScrollView createRefreshableView(Context context, AttributeSet attrs) {
		if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			mScrollView = new InternalSuspendScrollViewSDK9(context, attrs);
		} else {
			mScrollView = new SuspendScrollView(context, attrs);
		}
		mScrollView.setId(R.id.scrollview);
		mScrollView.setScrollViewListener(this);
		return mScrollView;
	}
	
	public void setSuspend(ViewGroup suspend) {
		this.suspend = suspend;
	}

	public void setNewsuspend(ViewGroup newsuspend) {
		this.newsuspend = newsuspend;
	}

	public void setOldsuspend(ViewGroup oldsuspend) {
		this.oldsuspend = oldsuspend;
	}


	
	@Override
	protected boolean isReadyForPullStart() {
		return mRefreshableView.getScrollY() == 0;
	}

	@Override
	protected boolean isReadyForPullEnd() {
		View scrollViewChild = mRefreshableView.getChildAt(0);
		if (null != scrollViewChild) {
			return mRefreshableView.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
		}
		return false;
	}

	@TargetApi(9)
	final class InternalSuspendScrollViewSDK9 extends SuspendScrollView {

		public InternalSuspendScrollViewSDK9(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
				int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

			final boolean returnValue = super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
					scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

			// Does all of the hard work...
			OverscrollHelper.overScrollBy(PullToSuspendScrollView.this, deltaX, scrollX, deltaY, scrollY,
					getScrollRange(), isTouchEvent);

			return returnValue;
		}

		/**
		 * Taken from the AOSP ScrollView source
		 */
		private int getScrollRange() {
			int scrollRange = 0;
			if (getChildCount() > 0) {
				View child = getChildAt(0);
				scrollRange = Math.max(0, child.getHeight() - (getHeight() - getPaddingBottom() - getPaddingTop()));
			}
			return scrollRange;
		}
	}

	@Override
	public void onScrollChanged(SuspendScrollView scrollView, int x, int y,int oldx, int oldy) {
		if (tabLayoutTop==0) {
			tabLayoutTop = oldsuspend.getTop();// 获取searchLayout的顶部位置
			LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, oldsuspend.getHeight());
			oldsuspend.setLayoutParams(lp);
		}
		if (y >= tabLayoutTop) {
			if (suspend.getParent() != newsuspend) {
				ViewGroup parent=(ViewGroup) suspend.getParent();
				if (parent!=null) {
					parent.removeAllViews();
				}
//				newsuspend.removeAllViews();
//				oldsuspend.removeView(suspend);
				newsuspend.addView(suspend);
			}
		} else {
			if (suspend.getParent() != oldsuspend) {
				ViewGroup parent=(ViewGroup) suspend.getParent();
				if (parent!=null) {
					parent.removeAllViews();
				}
//				oldsuspend.removeAllViews();
//				newsuspend.removeView(suspend);
				oldsuspend.addView(suspend);
			}
		}

	}
}
