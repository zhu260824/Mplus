package com.mplus.mplus.activity.main;

import java.util.ArrayList;

import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.common.DictStringPaser;
import com.umeng.socialize.utils.Log;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ConditionSelectionOtherActivity extends BaseActivity {
	private ListView listview;
	private ArrayList<String> listw;
	private TextView select_other_concel_tv, select_other_title;
	private int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_condition_selection_other);
		Window dialogWindow = this.getWindow();
		WindowManager.LayoutParams p = dialogWindow.getAttributes();
		dialogWindow.setGravity(Gravity.RIGHT);
		p.width = (int) (MPlusApplication.Width * 0.8); // 宽度设置为屏幕的0.8
		p.height = (int) (MPlusApplication.Height);
		dialogWindow.setAttributes(p);
		String tag = getIntent().getStringExtra("tag");
		Log.e("tag", "-----------------------------------------" + tag);
		initView(tag);

	}

	private void initView(String t) {
		listview = (ListView) findViewById(R.id.height_listview);
		 type=Integer.valueOf(t);
		listw = DictStringPaser.GetInstance(type);
		if (listw == null || listw.size() < 1) {
			RequestManger.GetDictData(ConditionSelectionOtherActivity.this,type,
					new DictStringPaser(type), new Listener<RequestCall>() {

						@Override
						public void onResponse(RequestCall response) {
							if (response.getParser().getResultSuccess()) {
								listw = DictStringPaser.GetInstance(type);
								conditionSelcedAdapter adapter = new conditionSelcedAdapter(
										listw);
								listview.setAdapter(adapter);
							}
						}
					}, errorListener);
		} else {
			conditionSelcedAdapter adapter = new conditionSelcedAdapter(listw);
			listview.setAdapter(adapter);
		}
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 数据是使用Intent返回
				Intent intent = new Intent();
				// 把返回数据存入Intent
				intent.putExtra("result", listw.get(position));

				// 设置返回数据
				ConditionSelectionOtherActivity.this.setResult(RESULT_OK,
						intent);
				// 关闭Activity
				ConditionSelectionOtherActivity.this.finish();
			}
		});

		select_other_title = (TextView) findViewById(R.id.select_other_title);
		select_other_concel_tv = (TextView) findViewById(R.id.select_other_concel_tv);
		String tvv = "条件";
		if (t.equals("8")) {
			tvv = "身高";
		}else if (t.equals("14")) {
			tvv = "体重";
		}else if (t.equals("15")) {
			tvv = "年龄";
		}
		select_other_title.setText(tvv);
		select_other_concel_tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ConditionSelectionOtherActivity.this.finish();
			}
		});
	}

	class conditionSelcedAdapter extends BaseAdapter {
		private ArrayList<String> listx;

		public conditionSelcedAdapter(ArrayList<String> listx) {
			super();
			this.listx = listx;
		}

		@Override
		public int getCount() {
			return listx.size();
		}

		@Override
		public Object getItem(int position) {
			return listx.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		private class ViewHolder {
			TextView tempValue;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(parent.getContext(),
						R.layout.item_height, null);
				holder.tempValue = (TextView) convertView
						.findViewById(R.id.tempValue);
				convertView.setTag(holder);
				AutoUtils.autoSize(convertView);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tempValue.setText(listx.get(position));

			return convertView;
		}

	}
}
