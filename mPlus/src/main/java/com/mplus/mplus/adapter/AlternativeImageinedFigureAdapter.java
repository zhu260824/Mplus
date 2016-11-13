package com.mplus.mplus.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.dtd365.library.http.RequestCall;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.project.JobDetailsActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.http.MHttpTools;
import com.mplus.mplus.manger.RequestManger;
import com.mplus.mplus.paser.AlternativeImaginedFigureList;
import com.mplus.mplus.paser.SendInform;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.ImageLoadUtils;
import com.mplus.mplus.utils.ToastTool;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AlternativeImageinedFigureAdapter extends BaseAdapter {

	private ArrayList<AlternativeImaginedFigureList.contents> list;
	private DisplayImageOptions options;
	private Context context;
	private ArrayList<String> selcedlist = new ArrayList<String>();
	private ArrayList<Map<String,Object>> sendInform;
	private String planId;
	public AlternativeImageinedFigureAdapter(Context context, ArrayList<AlternativeImaginedFigureList.contents> list,String planId,
			boolean canSelced) {
		super();
		this.planId=planId;
		this.context = context;
		this.list = list;
		options = ImageLoadUtils.getImageOptions();
	}

	public void notifyData(ArrayList<AlternativeImaginedFigureList.contents> projectlist) {
		for (int i = 0; i < projectlist.size(); i++) {
			list.add(projectlist.get(i));
		}

		AlternativeImageinedFigureAdapter.this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		TextView tv_name_alternative, tv_height_alternative, tv_weight_alternative, tv_inform_alternative;
		ImageView iv_pic_alternative,iv_sex_alternative;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(parent.getContext(), R.layout.items_alternative_imagined_figure, null);
			
			holder.tv_name_alternative = (TextView) convertView.findViewById(R.id.tv_name_alternative);// 名字
			holder.tv_height_alternative = (TextView) convertView.findViewById(R.id.tv_height_alternative);// 身高
			holder.tv_weight_alternative = (TextView) convertView.findViewById(R.id.tv_weight_alternative);// 体重
			holder.tv_inform_alternative = (TextView) convertView.findViewById(R.id.tv_inform_alternative);// 通知
			holder.iv_pic_alternative = (ImageView) convertView.findViewById(R.id.iv_pic_alternative);
			holder.iv_sex_alternative = (ImageView) convertView.findViewById(R.id.iv_sex_alternative);
			convertView.setTag(holder);
			AutoUtils.autoSize(convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final AlternativeImaginedFigureList.contents sProject = list.get(position);
		if (sProject != null) {
			String sex=sProject.getSex();
			holder.tv_name_alternative.setText(sProject.getName() == null ? "" : sProject.getName());
			holder.tv_height_alternative.setText(sProject.getHeight() == null ? "" : sProject.getHeight());
			holder.tv_weight_alternative.setText(sProject.getWeight() == null ? "" : sProject.getWeight());
			ImageLoader.getInstance().displayImage(sProject.getAvatar(), holder.iv_pic_alternative, options);
			if(sex==null){
				holder.iv_sex_alternative.setVisibility(View.GONE);
			}else{
				holder.iv_sex_alternative.setVisibility(View.VISIBLE);
				holder.iv_sex_alternative.setBackgroundResource(sProject.getSex().equals("男") ? R.drawable.image_gender_man: R.drawable.woman);
			}
			holder.tv_inform_alternative.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					sendInform=new ArrayList<Map<String,Object>>();
					Map<String,Object> map=new HashMap<String,Object>();  
					map.put("userId", sProject.getUserId());
					map.put("planId", planId);
					sendInform.add(map);
					RequestManger.PostInform(context, UserPaser.GetInstance().userid,sendInform, new Listener<RequestCall>() {
						public void onResponse(RequestCall response) {
							if (response.getParser().getResultSuccess()) {
								ToastTool.showShortToast(context, "通知成功！");
							}else {
								if (response.getParser().getResponseMsg()!=null) 
									ToastTool.showShortToast(context,response.getParser().getResponseMsg());
							}
						}
					}, errorListener);
				}
			});
		}
		return convertView;
	}
	protected ErrorListener errorListener = MHttpTools.getErrorListener(context);
	public ArrayList<String> getSelcedlist() {
		return selcedlist;
	}
}
