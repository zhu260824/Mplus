package com.mplus.mplus.adapter;

import java.util.ArrayList;

import com.dtd365.library.Utils.ValueUtils;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.temporary.AlternativeImaginedFigureActivity;
import com.mplus.mplus.activity.temporary.MyGetApplyActivity;
import com.mplus.mplus.paser.project.ProjectNameList;
import com.zhy.autolayout.utils.AutoUtils;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProjectNameAdapter  extends BaseAdapter {

	private ArrayList<ProjectNameList.contents> list;
//	private DisplayImageOptions options;
	private Context context;
	private boolean canSelced=false,isAllSelced=false;
	private ArrayList<String> selcedlist = new ArrayList<String>();
	private String mproductname,mproductid;
	public ProjectNameAdapter(Context context, ArrayList<ProjectNameList.contents> list,boolean canSelced,String mproductname,String mproductid) {
		super();
		this.context = context;
		this.list = list;
		this.canSelced=canSelced;
		this.mproductname=mproductname;
		this.mproductid=mproductid;
//		options = ImageLoadUtils.getImageOptions();
	}

	public void notifyData(ArrayList<ProjectNameList.contents> projectlist) {
		for (int i = 0; i < projectlist.size(); i++) {
			list.add(projectlist.get(i));
		}

		ProjectNameAdapter.this.notifyDataSetChanged();
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
		TextView tv_role_name, tv_onoff_experience, tv_height_project_name,tv_auction_calendar_project_name,tv_age_project_name,tv_weight_project_name,tv_remuneration_project_name,
		tv_label_project_name,tv_click_alternative_imagined_figure,tv_receive_apply_for_project_name,tv_role_title,
		tv_charactet_profile_project_name, tv_alternative_imagined_figure_project_name, tv_receive_apply_for;
		LinearLayout ll_show_project_name;
		ImageView iv_offon_project_name;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(parent.getContext(), R.layout.items_project_name, null);
			holder.ll_show_project_name=(LinearLayout)convertView.findViewById(R.id.ll_show_project_name);
			holder.tv_role_name = (TextView) convertView.findViewById(R.id.tv_role_name);//名字
			holder.tv_onoff_experience = (TextView) convertView.findViewById(R.id.tv_onoff_experience);//是否需要经验
			holder.tv_height_project_name = (TextView) convertView.findViewById(R.id.tv_height_project_name);//身高
			holder.tv_auction_calendar_project_name = (TextView) convertView.findViewById(R.id.tv_auction_calendar_project_name);//档期
			holder.tv_label_project_name = (TextView) convertView.findViewById(R.id.tv_label_project_name);//类型
			holder.tv_receive_apply_for = (TextView) convertView.findViewById(R.id.tv_receive_apply_for);//收到申请
			holder.tv_charactet_profile_project_name = (TextView) convertView.findViewById(R.id.tv_charactet_profile_project_name);//人物小转
			holder.tv_alternative_imagined_figure_project_name = (TextView) convertView.findViewById(R.id.tv_alternative_imagined_figure_project_name);//备选影人
			holder.iv_offon_project_name=(ImageView)convertView.findViewById(R.id.iv_offon_project_name);
			holder.tv_role_title = (TextView) convertView.findViewById(R.id.tv_role_title);//角色称谓
			holder.tv_age_project_name = (TextView) convertView.findViewById(R.id.tv_age_project_name);//角色年龄
			holder.tv_weight_project_name = (TextView) convertView.findViewById(R.id.tv_weight_project_name);//角色体重
			holder.tv_remuneration_project_name = (TextView) convertView.findViewById(R.id.tv_remuneration_project_name);//片酬
			holder.tv_click_alternative_imagined_figure=(TextView)convertView.findViewById(R.id.tv_click_alternative_imagined_figure);
			holder.tv_receive_apply_for_project_name=(TextView)convertView.findViewById(R.id.tv_receive_apply_for_project_name);
			
			convertView.setTag(holder);
			AutoUtils.autoSize(convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ProjectNameList.contents sProject = list.get(position);
		if (sProject != null) {
			holder.tv_role_name.setText(sProject.getPlanName() == null ? "" : sProject.getPlanName() );
			holder.tv_onoff_experience.setText(sProject.getNeedRelatedExp() == 1 ? "需要经验" : "无需经验");
			holder.tv_height_project_name.setText(sProject.getPlanHeight() == null ? "不限制" : sProject.getPlanHeight());
			if (ValueUtils.isStrEmpty(sProject.getNickName())) {
				holder.tv_role_title.setText("");
			}else {
				holder.tv_role_title.setText( "(" +sProject.getNickName()+")");
			}
			String starttime = TextUtils.isEmpty(sProject.getScheduleStartTime()) ? "" : sProject.getScheduleStartTime();
			String endtime = TextUtils.isEmpty(sProject.getScheduleEndTime()) ? "" : sProject.getScheduleEndTime();
			String time=starttime + "至" + endtime;
			if (time.length()<=2) {
				holder.tv_auction_calendar_project_name.setText("不限");
			}else {
				holder.tv_auction_calendar_project_name.setText(time);
			}
			holder.tv_label_project_name.setText(ValueUtils.isStrEmpty(sProject.getPlanFeature()) ? "不限制": sProject.getPlanFeature());
			holder.tv_charactet_profile_project_name.setText(sProject.getPlanDesc() == null ? "":sProject.getPlanDesc());
			holder.tv_alternative_imagined_figure_project_name.setText(sProject.getPlanOptionNum() == null ? "0":sProject.getPlanOptionNum());
			holder.tv_receive_apply_for.setText(sProject.getPlanApplyNum() == null ? "0":sProject.getPlanApplyNum());
			if (TextUtils.isEmpty(sProject.getPlanAge())) {
				holder.tv_age_project_name.setText("不限制");
			}else if (sProject.getPlanAge().equals("不限制")) {
				holder.tv_age_project_name.setText("不限制");
			}else {
				holder.tv_age_project_name.setText(sProject.getPlanAge()+"岁");
			}
			String palycheck=ValueUtils.isStrEmpty(sProject.getPaycheck()) ? "面议" : sProject.getPaycheck();
			if (palycheck!=null && !palycheck.equals("面议")) {
				holder.tv_remuneration_project_name.setText(palycheck+"万");
			}else {
				holder.tv_remuneration_project_name.setText("面议");
			}
			holder.tv_weight_project_name.setText(sProject.getPlanWeight() == null ? "不限制":sProject.getPlanWeight());
		}
		if(canSelced){
			holder.ll_show_project_name.setVisibility(View.VISIBLE);
			if (sProject.isSelced()) {
				holder.iv_offon_project_name.setImageResource(R.drawable.blue_point);
				selcedlist.add(sProject.getPlanId());
			} else {
				holder.iv_offon_project_name.setImageResource(R.drawable.message_off);
				selcedlist.remove(sProject.getPlanId());
			}
			holder.ll_show_project_name.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (sProject.isSelced()) {
						holder.iv_offon_project_name.setImageResource(R.drawable.message_off);
						sProject.setSelced(false);
						selcedlist.remove(sProject.getPlanId());
					}else {
						holder.iv_offon_project_name.setImageResource(R.drawable.blue_point);
						sProject.setSelced(true);
						selcedlist.add(sProject.getPlanId());
					}
				}
			});		
		}else{
			holder.ll_show_project_name.setVisibility(View.GONE);
			holder.tv_click_alternative_imagined_figure.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
				Intent intent =new Intent(context,AlternativeImaginedFigureActivity.class);
				intent.putExtra("mproductname",mproductname);
				intent.putExtra("planname", sProject.getPlanName());
				intent.putExtra("mproductid", mproductid);
				intent.putExtra("planId",  sProject.getPlanId());
				context.startActivity(intent);
					
				}
			});
			holder.tv_receive_apply_for_project_name.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent2 =new Intent(context,MyGetApplyActivity.class);
					intent2.putExtra("mproductname",mproductname);
					intent2.putExtra("planname", sProject.getPlanName());
					intent2.putExtra("planId",sProject.getPlanId());
					context.startActivity(intent2);
				}
			});
		}
		return convertView;
	}

	public ArrayList<String> getSelcedlist() {
		return selcedlist;
	}
}