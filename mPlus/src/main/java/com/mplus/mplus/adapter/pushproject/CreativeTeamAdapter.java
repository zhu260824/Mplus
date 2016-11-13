package com.mplus.mplus.adapter.pushproject;

import java.util.ArrayList;

import com.mplus.mplus.R;
import com.mplus.mplus.activity.pushproject.CreativeTeamItemActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.MPlusApplication;
import com.mplus.mplus.paser.pushproject.ProductJob;
import com.mplus.mplus.view.MyDialog;
import com.zhy.autolayout.utils.AutoUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreativeTeamAdapter extends BaseAdapter{
	private static final int ADDROLE=1;
	private static final int CHANGEROLE=2;
	private ArrayList<ProductJob> lists;
	private BaseActivity mActivity;

	public CreativeTeamAdapter(BaseActivity mActivity,ArrayList<ProductJob> lists) {
		super();
		this.lists = lists;
		this.mActivity = mActivity;
	}

	@Override
	public int getCount() {
		return lists==null?1:lists.size()+1;
	}

	@Override
	public Object getItem(int position) {
		return lists;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	private class ViewHolder{
		TextView tv_role, tv_name;
		ImageView iv_delete;
		Button btn_edit;
	} 
	
	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (position==lists.size()) {
			convertView=View.inflate(mActivity, R.layout.items_creative_team_add, null);
			AutoUtils.autoSize(convertView);
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mActivity, CreativeTeamItemActivity.class);
					ProductJob productJob=new ProductJob("", "", 0, "", "");
					intent.putExtra("pjob", productJob);
					mActivity.startActivityForResult(intent, ADDROLE);
				}
			});
		}else{
			final ViewHolder holder;
			holder=new ViewHolder();
			convertView=View.inflate(mActivity, R.layout.items_creative_team, null);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);	
			holder.tv_role=(TextView) convertView.findViewById(R.id.tv_role);
//			holder.tv_state=(TextView) convertView.findViewById(R.id.tv_state);
			holder.iv_delete=(ImageView) convertView.findViewById(R.id.iv_delete);
			holder.btn_edit=(Button) convertView.findViewById(R.id.btn_edit);
			AutoUtils.autoSize(convertView);
			final ProductJob lPJob=lists.get(position);
			initViewData(holder, lPJob);
			ItemsOnClick itemsOnClick=new ItemsOnClick(lPJob,position);
			holder.btn_edit.setOnClickListener(itemsOnClick);
			holder.iv_delete.setOnClickListener(itemsOnClick);
		}
		AutoUtils.auto(convertView);
		return convertView;
	}

	private class ItemsOnClick implements OnClickListener{
		private ProductJob lPJob;
		private int listIndex=0;
		
		public ItemsOnClick(ProductJob lPJob, int listIndex) {
			super();
			this.lPJob = lPJob;
			this.listIndex = listIndex;
		}
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_edit:
				Intent intent = new Intent(mActivity, CreativeTeamItemActivity.class);
				intent.putExtra("listIndex", listIndex);
				intent.putExtra("pjob", lPJob);
				mActivity.startActivityForResult(intent, CHANGEROLE);
				break;
			case R.id.iv_delete:
				showtips(lPJob);
				break;
			default:
				break;
			}
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	private void initViewData(ViewHolder holder,ProductJob lPJob){
		if (lPJob==null) {
			return;
		}
		holder.tv_role.setText(lPJob.role);
		holder.tv_name.setText(lPJob.name==null?"":lPJob.name);
		/*if (lPJob.action==0) {
			holder.tv_state.setText("拟请");
		}else {
			holder.tv_state.setText("已签约");
		}*/
	}
	
	
	
	
	@SuppressLint("ResourceAsColor")
	private void showtips(final ProductJob lPJob) {
		View view = View.inflate(mActivity, R.layout.dialog_forget,null);
		AutoUtils.autoSize(view);
		final MyDialog dialog = new MyDialog(mActivity,R.style.CustomDialog);
		LinearLayout.LayoutParams dialoglp = new LinearLayout.LayoutParams(MPlusApplication.Width * 4 / 5,LinearLayout.LayoutParams.WRAP_CONTENT);
		dialog.addContentView(view, dialoglp);
		dialog.setCancelable(true);
		dialog.show();
		TextView tv_tip =(TextView)view.findViewById(R.id.tv_tip);
		TextView tv_sure = (TextView) view.findViewById(R.id.tv_sure);
		TextView tv_cacel = (TextView) view.findViewById(R.id.tv_cancel);
		tv_tip.setText("是否删除该角色？");
		tv_sure.setText("否");
		tv_sure.setTextColor(mActivity.getResources().getColorStateList(R.color.blue_bg));
		tv_sure.setBackgroundResource(R.drawable.items_button_red);
		
		tv_cacel.setText("是");
		tv_cacel.setTextColor(Color.WHITE);
		tv_cacel.setBackgroundResource(R.drawable.red_roud_dialog_btn);
		tv_sure.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (dialog != null && dialog.isShowing())
					dialog.dismiss();
			}
		});
		tv_cacel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (dialog != null && dialog.isShowing())
					dialog.dismiss();
				lists.remove(lPJob);
				CreativeTeamAdapter.this.notifyDataSetChanged();
				
			}
		});
	}
}
