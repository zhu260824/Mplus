package com.mplus.mplus.dialog;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mplus.mplus.R;
import com.ywl5320.pickaddress.wheel.widget.adapters.AbstractWheelTextAdapter;
import com.ywl5320.pickaddress.wheel.widget.views.OnWheelChangedListener;
import com.ywl5320.pickaddress.wheel.widget.views.OnWheelScrollListener;
import com.ywl5320.pickaddress.wheel.widget.views.WheelView;
import com.zhy.autolayout.utils.AutoUtils;

/**
 *星座滚轮选择
 * 
 * @author y
 *
 */
@SuppressLint("ResourceAsColor")
public class ChangeConstellationDialog extends Dialog implements android.view.View.OnClickListener {
	private OnConstellationListener onConstellationListener;
	private Context context;
	private WheelView wvConstellation;
	private View vChangeBirth;
	private View vChangeBirthChild;
	private CalendarTextAdapter mConstellationAdapter;
	private TextView btnSure;
	private TextView btnCancel;
	private int maxTextSize = 20;
	private int minTextSize = 16;
	private String constellation="白羊座";
	private String[] costellationList = { "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座",
			"处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座" };
	private ArrayList<String> arry_constellation = new ArrayList<String>();
	private View customView;
	public ChangeConstellationDialog(Context context) {
		super(context, R.style.ShareDialog);
		this.context = context;
		LayoutInflater inflater= LayoutInflater.from(context);  
        customView = inflater.inflate(R.layout.dialog_myinfo_changeconstellation, null);
		
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(customView);
		AutoUtils.autoSize(customView);
		wvConstellation = (WheelView) findViewById(R.id.wv_constellation_year);
		vChangeBirth = findViewById(R.id.ly_myinfo_constellation);
		vChangeBirthChild = findViewById(R.id.ly_myinfo_constellation_child);
		btnSure = (TextView) findViewById(R.id.btn_constellation_sure);
		btnCancel = (TextView) findViewById(R.id.btn_constellation_cancel);
		vChangeBirth.setOnClickListener(this);
		vChangeBirthChild.setOnClickListener(this);
		btnSure.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		initConstellation();
		mConstellationAdapter = new CalendarTextAdapter(context,arry_constellation, 0, maxTextSize, minTextSize);
		wvConstellation.setVisibleItems(5);
		wvConstellation.setViewAdapter(mConstellationAdapter);
		wvConstellation.setCurrentItem(0);
		wvConstellation.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				String currentText = (String) mConstellationAdapter
						.getItemText(wheel.getCurrentItem());
				constellation = currentText;
				setTextviewSize(currentText, mConstellationAdapter);
			}
		});

		wvConstellation.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				String currentText = (String) mConstellationAdapter
						.getItemText(wheel.getCurrentItem());
				setTextviewSize(currentText, mConstellationAdapter);
			}
		});
	}

	public void onClick(View v) {
		if (v == btnSure) {
			if (onConstellationListener != null) {
				onConstellationListener.onClick(constellation);
			}
		} else if (v == btnSure) {

		} else if (v == vChangeBirthChild) {
			return;
		} else {
			dismiss();
		}
		dismiss();
	}

	private class CalendarTextAdapter extends AbstractWheelTextAdapter {
		ArrayList<String> list;

		protected CalendarTextAdapter(Context context, ArrayList<String> list,
				int currentItem, int maxsize, int minsize) {
			super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem,
					maxsize, minsize);
			this.list = list;
			setItemTextResource(R.id.tempValue);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return list.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index) + "";
		}
	}

	/**
	 * 设置字体大小
	 * 
	 * @param curriteItemText
	 * @param adapter
	 */
	public void setTextviewSize(String curriteItemText,
			CalendarTextAdapter adapter) {
		ArrayList<View> arrayList = adapter.getTestViews();
		int size = arrayList.size();
		String currentText;
		for (int i = 0; i < size; i++) {
			TextView textvew = (TextView) arrayList.get(i);
			currentText = textvew.getText().toString();
			if (curriteItemText.equals(currentText)) {
				textvew.setTextSize(maxTextSize);
				textvew.setTextColor(R.color.text_dark_grey);
			} else {
				textvew.setTextSize(minTextSize);
				textvew.setTextColor(R.color.text_light_grey);
			}
		}
		
	}

	public interface OnConstellationListener {
		public void onClick(String constellation);
	}

	public void setConstellationListener(
			OnConstellationListener onConstellationListener) {
		this.onConstellationListener = onConstellationListener;
	}

	public void initConstellation() {
		for (int i = 0; i<costellationList.length; i++) {
			arry_constellation.add(costellationList[i]);
		}
	}
}
