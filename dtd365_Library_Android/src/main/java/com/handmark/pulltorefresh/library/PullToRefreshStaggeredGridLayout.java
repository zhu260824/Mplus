package com.handmark.pulltorefresh.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import cn.mplus.dtd365_library_android.R;

public class PullToRefreshStaggeredGridLayout extends
		PullToRefreshBase<RecyclerView> {
	public PullToRefreshStaggeredGridLayout(Context context) {
		super(context);
	}

	public PullToRefreshStaggeredGridLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	public final Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	 //重写4个方法
    //2  滑动的View
    @Override
    protected RecyclerView createRefreshableView(Context context, AttributeSet attrs) {
        RecyclerView view = new RecyclerView(context, attrs);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(
				2, StaggeredGridLayoutManager.VERTICAL);
        view.setLayoutManager(mLayoutManager);
        view.setId(R.id.straggereddGridLayout);
        return view;
    }

    //重写4个方法
    //3 是否滑动到底部
    @Override
    protected boolean isReadyForPullEnd() {
        View view = getRefreshableView().getChildAt(getRefreshableView().getChildCount() - 1);
        if (null != view) {
            return getRefreshableView().getBottom() >= view.getBottom();
        }
        return false;
    }

    //重写4个方法
    //4 是否滑动到顶部
    @Override
    protected boolean isReadyForPullStart() {
        View view = getRefreshableView().getChildAt(0);

        if (view != null) {
            return view.getTop() >= getRefreshableView().getTop();
        }
        return true;
    }


}
