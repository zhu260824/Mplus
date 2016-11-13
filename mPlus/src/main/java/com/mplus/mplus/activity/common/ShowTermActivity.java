package com.mplus.mplus.activity.common;

import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

public class ShowTermActivity extends BaseActivity {
	private WebView webView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_term);
		initTitle(getString(R.string.text_reset_register_user_treaty));
		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);// 这样网页就可加载JavaScript了
		webView.getSettings().setBuiltInZoomControls(false);// 显示放大缩小按钮
		webView.getSettings().setSupportZoom(false);// 允许放大缩小
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);// 支持内容从新布局
		webView.getSettings().setAllowFileAccess(true);// 设置可以访问文件
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);// 支持通过JS打开新窗口
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.loadUrl("file:///android_asset/Term.html");
	}
}
