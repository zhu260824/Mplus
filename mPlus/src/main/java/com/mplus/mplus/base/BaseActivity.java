package com.mplus.mplus.base;

import com.android.volley.Response.ErrorListener;
import com.dtd365.library.Utils.MobclickAgentUtils;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.mplus.mplus.R;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.utils.DialongUtils;
import com.mplus.mplus.utils.ToastTool;
import com.zhy.autolayout.AutoLayoutActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author ZL activity基础类
 * */
@SuppressLint("Registered")
public class BaseActivity extends AutoLayoutActivity {
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
	/** 进度条 */
	protected Dialog loadingDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 网络权限设置
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath().build());
		MPlusApplication.getInstance().addActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgentUtils.startPage(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgentUtils.endtPage(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		MPlusApplication.getInstance().removeActivity(this);
	}
	
	protected ErrorListener errorListener = MHttpTools.getErrorListener(BaseActivity.this);
	
	/**
	 * @Title: initTitle
	 * @Description: 标题栏 固定右边为返回按钮
	 * @return void 返回类型
	 */
	protected void initTitle(String title) {
		try {
			rl_left = (RelativeLayout) findViewById(R.id.rl_left);
			tv_title = (TextView) findViewById(R.id.tv_title);
			if (tv_title != null)
				tv_title.setText(title);
			if (rl_left!=null) 
				rl_left.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						BaseActivity.this.finish();
					}
				});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 设置右边的标题栏图片跟文字      imageView 图片        str 标题        hasImage 是否显示图片    hasText是否显示文字    textSize文字大小
	 * */
	protected void initShowRight(int imageView, String str, Boolean hasImage,Boolean hasText,int textSize){
		try {
			lin_right = (LinearLayout) findViewById(R.id.lin_right);
			tv_right = (TextView) findViewById(R.id.tv_right);
			iv_right = (ImageView) findViewById(R.id.iv_right);
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
	protected void showLeftText(Boolean hasText,String text,int textSize){
		try {
			tv_left=(TextView) findViewById(R.id.tv_left);
			iv_back=(ImageView) findViewById(R.id.iv_back);
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

	protected void setRightText(String text){
		if (tv_right!=null) 
			tv_right.setText(text);
	}
	
	protected  void  setTitle(String title) {
		if (tv_title!=null && !TextUtils.isEmpty(title)) 
			tv_title.setText(title);
	}
	
	protected String getRightText(){
		String text="";
		if (tv_right!=null && tv_right.getText()!=null) 
			text=tv_right.getText().toString();
		return text;
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

	public void showLoadingDialog(){
		if (loadingDialog==null) 
			loadingDialog=DialongUtils.showLoading(BaseActivity.this);
		loadingDialog.show();
	}
	
	public void dismissLoadingDialog(){
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
			ToastTool.showShortToast(BaseActivity.this, getString(R.string.no_more_content));
			if (mPullRefreshListView!=null && mPullRefreshListView.isRefreshing()) 
				mPullRefreshListView.onRefreshComplete();
		}
	}	

}
