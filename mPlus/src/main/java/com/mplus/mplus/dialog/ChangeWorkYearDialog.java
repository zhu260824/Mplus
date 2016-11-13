package com.mplus.mplus.dialog;

import java.util.ArrayList;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mplus.mplus.R;
import com.ywl5320.pickaddress.wheel.widget.adapters.AbstractWheelTextAdapter;
import com.ywl5320.pickaddress.wheel.widget.views.OnWheelChangedListener;
import com.ywl5320.pickaddress.wheel.widget.views.OnWheelScrollListener;
import com.ywl5320.pickaddress.wheel.widget.views.WheelView;

/**
 * 日期选择对话框
 * 
 * @author ywl
 *
 */
@SuppressLint("ResourceAsColor")
public class ChangeWorkYearDialog extends Dialog implements android.view.View.OnClickListener {

	private Context context;
	private WheelView wvYear;

	private View vChangeBirth;
	private View vChangeBirthChild;
	private TextView btnSure;
	private TextView btnCancel;

	private ArrayList<String> arry_years = new ArrayList<String>();
	private CalendarTextAdapter mYearAdapter;
	private int currentYear = getYear();

	private int maxTextSize = 20;
	private int minTextSize = 16;


	private String selectYear;

	private OnWorkyearListener OnWorkyearListener;

	public ChangeWorkYearDialog(Context context) {
		super(context, R.style.ShareDialog);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_myinfo_changeworkyear);
		wvYear = (WheelView) findViewById(R.id.wv_birth_year);
		vChangeBirth = findViewById(R.id.ly_myinfo_changebirth);
		vChangeBirthChild = findViewById(R.id.ly_myinfo_changebirth_child);
		btnSure = (TextView) findViewById(R.id.btn_myinfo_sure);
		btnCancel = (TextView) findViewById(R.id.btn_myinfo_cancel);

		vChangeBirth.setOnClickListener(this);
		vChangeBirthChild.setOnClickListener(this);
		btnSure.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		initYears();
		mYearAdapter = new CalendarTextAdapter(context, arry_years, setYear(currentYear), maxTextSize, minTextSize);
		wvYear.setVisibleItems(5);
		wvYear.setViewAdapter(mYearAdapter);
		wvYear.setCurrentItem(setYear(currentYear));
		selectYear=currentYear+"";

		wvYear.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
				selectYear = currentText;
				setTextviewSize(currentText, mYearAdapter);
				currentYear = Integer.parseInt(currentText);
				setYear(currentYear);
			}
		});

		wvYear.addScrollingListener(new OnWheelScrollListener() {

			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub

			}

			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
				String currentText = (String) mYearAdapter.getItemText(wheel.getCurrentItem());
				setTextviewSize(currentText, mYearAdapter);
			}
		});


	}

	public void initYears() {
		for (int i = getYear(); i > 1950; i--) {
			arry_years.add(i + "");
		}
	}


	private class CalendarTextAdapter extends AbstractWheelTextAdapter {
		ArrayList<String> list;

		protected CalendarTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
			super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
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

	public void setWorkyearListener(OnWorkyearListener OnWorkyearListener) {
		this.OnWorkyearListener = OnWorkyearListener;
	}

	@Override
	public void onClick(View v) {

		if (v == btnSure) {
			if (OnWorkyearListener != null) {
				OnWorkyearListener.onClick(selectYear);
			}
		} else if (v == btnSure) {

		} else if (v == vChangeBirthChild) {
			return;
		} else {
			dismiss();
		}
		dismiss();

	}

	public interface OnWorkyearListener {
		public void onClick(String year);
	}

	/**
	 * 设置字体大小
	 * 
	 * @param curriteItemText
	 * @param adapter
	 */
	public void setTextviewSize(String curriteItemText, CalendarTextAdapter adapter) {
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

	public int getYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}

	public int getMonth() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH) + 1;
	}

	public int getDay() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DATE);
	}


	/**
	 * 设置年份
	 * 
	 * @param year
	 */
	public int setYear(int year) {
		int yearIndex = 0;
		for (int i = getYear(); i > 1950; i--) {
			if (i == year) {
				return yearIndex;
			}
			yearIndex++;
		}
		return yearIndex;
	}


}