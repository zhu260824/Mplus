package com.mplus.mplus.dialog;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.login.ProvincePaser;
import com.mplus.mplus.paser.login.Province_item;
import com.ywl5320.pickaddress.wheel.widget.adapters.AbstractWheelTextAdapter;
import com.ywl5320.pickaddress.wheel.widget.views.OnWheelChangedListener;
import com.ywl5320.pickaddress.wheel.widget.views.OnWheelScrollListener;
import com.ywl5320.pickaddress.wheel.widget.views.WheelView;

/**
 * 更改封面对话框
 * 
 * @author ywl
 *
 */
@SuppressLint("ResourceAsColor")
public class ChangeAddressDialog extends Dialog implements android.view.View.OnClickListener {

	private WheelView wvProvince;
	private WheelView wvCitys;
	private View lyChangeAddress;
	private View lyChangeAddressChild;
	private TextView btnSure;
	private TextView btnCancel;

	private Context context;
	private AddressTextAdapter provinceAdapter;
	private AddressCityAdapter cityAdapter;
	private ArrayList<Province_item> pList=new ArrayList<Province_item>();

	private String strProvince = "上海";
	private String strCity = "上海";
	private OnAddressCListener onAddressCListener;

	private int maxsize = 20;
	private int minsize = 16;

	public ChangeAddressDialog(Context context) {
		super(context, R.style.ShareDialog);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_myinfo_changeaddress);
		wvProvince = (WheelView) findViewById(R.id.wv_address_province);
		wvCitys = (WheelView) findViewById(R.id.wv_address_city);
		lyChangeAddress = findViewById(R.id.ly_myinfo_changeaddress);
		lyChangeAddressChild = findViewById(R.id.ly_myinfo_changeaddress_child);
		btnSure = (TextView) findViewById(R.id.btn_myinfo_sure);
		btnCancel = (TextView) findViewById(R.id.btn_myinfo_cancel);
		lyChangeAddress.setOnClickListener(this);
		lyChangeAddressChild.setOnClickListener(this);
		btnSure.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		initProvinces();
	}

	private void inintData(){
		provinceAdapter = new AddressTextAdapter(context, pList, 0, maxsize, minsize);
		wvProvince.setVisibleItems(5);
		wvProvince.setViewAdapter(provinceAdapter);
		wvProvince.setCurrentItem( 0);
		initCitys(pList.get(0).getList());
		cityAdapter = new AddressCityAdapter(context, pList.get(0).getList(), 0, maxsize, minsize);
		wvCitys.setVisibleItems(5);
		wvCitys.setViewAdapter(cityAdapter);
		wvCitys.setCurrentItem(0);
		wvProvince.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
				strProvince = currentText;
				setTextviewSize(currentText, provinceAdapter);
//				String[] citys = mCitisDatasMap.get(currentText);
				initCitys(pList.get(newValue).getList());
				cityAdapter = new AddressCityAdapter(context, pList.get(newValue).getList(), 0, maxsize, minsize);
				wvCitys.setVisibleItems(5);
				wvCitys.setViewAdapter(cityAdapter);
				wvCitys.setCurrentItem(0);
			}
		});
		wvProvince.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
				setTextviewSize(currentText, provinceAdapter);
			}
		});

		wvCitys.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
				strCity = currentText;
				setTextviewSize(currentText, cityAdapter);
			}
		});

		wvCitys.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
				setTextviewSize(currentText, cityAdapter);
			}
		});
	}
	
	
	private class AddressTextAdapter extends AbstractWheelTextAdapter {
		ArrayList<Province_item> list;

		protected AddressTextAdapter(Context context, ArrayList<Province_item> list, int currentItem, int maxsize, int minsize) {
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
			return list==null?0:list.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index).getProvincename();
		}
	}
	
	private class AddressCityAdapter extends AbstractWheelTextAdapter {
		ArrayList<String> list;

		protected AddressCityAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
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
			return list==null?0:list.size();
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
	public void setTextviewSize(String curriteItemText, AbstractWheelTextAdapter adapter) {
		ArrayList<View> arrayList = adapter.getTestViews();
		int size = arrayList.size();
		String currentText;
		for (int i = 0; i < size; i++) {
			TextView textvew = (TextView) arrayList.get(i);
			currentText = textvew.getText().toString();
			
			
			if (curriteItemText.equals(currentText)) {
				textvew.setTextSize(maxsize);
				textvew.setTextColor(R.color.text_dark_grey);
			} else {
				textvew.setTextSize(minsize);
				textvew.setTextColor(R.color.text_light_grey);
			}
		}
	}

	public void setAddresskListener(OnAddressCListener onAddressCListener) {
		this.onAddressCListener = onAddressCListener;
	}

	@Override
	public void onClick(View v) {
		if (v == btnSure) {
			if (onAddressCListener != null) {
				onAddressCListener.onClick(strProvince, strCity);
			}
		} else if (v == btnCancel) {

		} else if (v == lyChangeAddressChild) {
			return;
		} else {
			dismiss();
		}
		dismiss();
	}

	/**
	 * 回调接口
	 * 
	 * @author Administrator
	 *
	 */
	public interface OnAddressCListener {
		public void onClick(String province, String city);
	}


	/**
	 * 初始化省会
	 */
	public void initProvinces() {
		pList=ProvincePaser.GetInstance();
		if (pList==null || pList.size()<1) {
			if (pList==null) 
				pList=new ArrayList<Province_item>();
			Province_item province_item=new Province_item();
			province_item.setProvincename("上海");
			ArrayList<String> citys=new ArrayList<String>();
			citys.add("上海");
			province_item.setList(citys);
			pList.add(province_item);
			RequestManger.GetDictData(context, 0,
					new ProvincePaser(), new Listener<RequestCall>() {

						@Override
						public void onResponse(RequestCall response) {
							pList=ProvincePaser.GetInstance();
							if (pList!=null && pList.size()>=1)
								strProvince=pList.get(0).getProvincename();
							inintData();
						}
					}, errorListener);
		}else {
			if (pList!=null && pList.size()>=1)
				strProvince=pList.get(0).getProvincename();
			inintData();
			RequestManger.GetDictData(context, 0,
					new ProvincePaser(), new Listener<RequestCall>() {

						@Override
						public void onResponse(RequestCall response) {
						}
					}, errorListener);
		}
	}

	/**
	 * 根据省会，生成该省会的所有城市
	 * 
	 * @param citys
	 */
	public void initCitys(ArrayList<String> citys) {
		if (citys != null && citys.size() > 0) {
			strCity = citys.get(0);
		}
	}

	/**
	 * 初始化地点
	 * 
	 * @param province
	 * @param city
	 */
	public void setAddress(String province, String city) {
		if (province != null && province.length() > 0) {
			this.strProvince = province;
		}
		if (city != null && city.length() > 0) {
			this.strCity = city;
		}
	}
	protected ErrorListener errorListener = new ErrorListener() {
		public void onErrorResponse(VolleyError error) {
			Toast.makeText(context, error.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
	};
}