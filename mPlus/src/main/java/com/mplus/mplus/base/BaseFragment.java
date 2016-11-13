package com.mplus.mplus.base;

import java.lang.reflect.Field;

import com.android.volley.Response.ErrorListener;
import com.dtd365.library.Utils.MobclickAgentUtils;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mplus.mplus.R;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.utils.DialongUtils;
import com.mplus.mplus.utils.ToastTool;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class BaseFragment extends Fragment {
	protected BackHandledInterface mBackHandledInterface;
	public abstract boolean onBackPressed();
	protected BaseActivity mActivity;
	/** 标题栏返回按钮 */
	private RelativeLayout rl_left;
	private LinearLayout lin_right;
	/** 标题栏标题 */
	private TextView tv_title;
	/** 标题栏左侧返回按钮 */
	private ImageView iv_back;
	/** 标题栏左侧按钮 */
	private TextView tv_left;
	/** 标题栏右侧按钮 */
	private TextView tv_right;
	/** 标题栏右侧图片按钮 */
	private ImageView iv_right;
	protected Dialog loadingDialog;

	public interface BackHandledInterface {
		public abstract void setSelectedFragment(BaseFragment selectedFragment);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (BaseActivity) activity;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!(getActivity() instanceof BackHandledInterface)) {
			throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
		} else {
			this.mBackHandledInterface = (BackHandledInterface) getActivity();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		// 告诉FragmentActivity，当前Fragment在栈顶
		mBackHandledInterface.setSelectedFragment(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgentUtils.startFragment(this);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		MobclickAgentUtils.endFragment(this);
	}
	
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

	}


	/**
	 * @Title: initTitle
	 * @Description: 标题栏 固定右边为返回按钮
	 * @return void 返回类型
	 */
	protected void initTitle(View view,String title) {
		try {
			rl_left = (RelativeLayout)view.findViewById(R.id.rl_left);
			tv_title = (TextView)view.findViewById(R.id.tv_title);
			ImageView iv_back = (ImageView)view.findViewById(R.id.iv_back);
			iv_back.setVisibility(View.INVISIBLE);
			if (tv_title != null)
				tv_title.setText(title);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected ErrorListener errorListener = MHttpTools.getErrorListener(mActivity);
	
	/**
	 * 设置右边的标题栏图片跟文字      imageView 图片        str 标题        hasImage 是否显示图片    hasText是否显示文字    textSize文字大小
	 * */
	protected void initShowRight(View view,int imageView, String str, Boolean hasImage,Boolean hasText,int textSize){
		try {
			lin_right = (LinearLayout) view.findViewById(R.id.lin_right);
			tv_right = (TextView) view.findViewById(R.id.tv_right);
			iv_right = (ImageView) view.findViewById(R.id.iv_right);
			lin_right.setVisibility(View.VISIBLE);
			if (hasText) {
				tv_right.setVisibility(View.VISIBLE);
				if (str!=null) 
					tv_right.setText(str);
				if (textSize!=0) 
					tv_right.setTextSize(textSize);
			}
			if (hasImage) {
				iv_right.setVisibility(View.VISIBLE);
				if (imageView!=0)
					iv_right.setImageResource(imageView);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置左边的标题栏文字          text 文字           hasText是否显示文字    textSize文字大小
	 * */
	protected void showLeftText(View view,Boolean hasText,String text,int textSize){
		try {
			tv_left=(TextView)view.findViewById(R.id.tv_left);
			iv_back=(ImageView) view.findViewById(R.id.iv_back);
			if (hasText) {
				tv_left.setVisibility(View.VISIBLE);
				iv_back.setVisibility(View.GONE);
				if (text!=null) 
					tv_left.setText(text);
				if (textSize!=0) 
					tv_left.setTextSize(textSize);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void addLeftClickListener(OnClickListener leftClickListener){
		if (rl_left!=null) {
			rl_left.setOnClickListener(leftClickListener);
		}
	}
	
	protected void addRightClickListener(OnClickListener rightClickListener){
		if (lin_right!=null) {
			lin_right.setOnClickListener(rightClickListener);
		}
	}
	
	protected void showLoadingDialog(){
		if (loadingDialog==null) 
			loadingDialog=DialongUtils.showLoading(getActivity());
		loadingDialog.show();
	}
	
	protected void dismissLoadingDialog(){
		DialongUtils.dismissLoading(loadingDialog);
	}
	
	protected class GetDataTask extends AsyncTask<Void, Void, String[]> {
		private PullToRefreshListView mPullRefreshListView;
		
		public GetDataTask(PullToRefreshListView mPullRefreshListView) {
			super();
			this.mPullRefreshListView = mPullRefreshListView;
		}

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			super.onPostExecute(result);
			ToastTool.showShortToast(mActivity, getString(R.string.no_more_content));
			if (mPullRefreshListView!=null && mPullRefreshListView.isRefreshing()) 
				mPullRefreshListView.onRefreshComplete();
		}
	}	
}
