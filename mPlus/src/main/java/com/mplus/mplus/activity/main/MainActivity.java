package com.mplus.mplus.activity.main;

import org.androidpn.client.ServiceManager;

import com.dtd365.library.Utils.MobclickAgentUtils;
import com.dtd365.library.app.DtdApplication;
import com.mplus.mplus.R;
import com.mplus.mplus.activity.message.MessageCategoryActivity;
import com.mplus.mplus.activity.pushproject.FirstPushWorksActivity;
import com.mplus.mplus.activity.upresume.FirstStepActivity;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.base.BaseFragment;
import com.mplus.mplus.base.BaseFragment.BackHandledInterface;
import com.mplus.mplus.utils.LoginUtils;
import com.mplus.mplus.utils.ToastTool;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements OnClickListener, BackHandledInterface {
	private RelativeLayout rl_person, rl_project, rl_starsFace, rl_collect, rl_userCenter;
	private TextView tv_person, tv_project, tv_starsFace, tv_collect, tv_userCenter;
	private ImageView iv_person, iv_project, iv_collect, iv_userCenter;
	private BaseFragment personFragment, projectFragment, starsFaceFragment, collectFragment, userCenterFragment,
			lastSelectedFragment;
	private int selectedType = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rl_person = (RelativeLayout) findViewById(R.id.rl_person);
		rl_project = (RelativeLayout) findViewById(R.id.rl_project);
		rl_starsFace = (RelativeLayout) findViewById(R.id.rl_starsface);
		rl_collect = (RelativeLayout) findViewById(R.id.rl_collect);
		rl_userCenter = (RelativeLayout) findViewById(R.id.rl_usercenter);
		tv_person = (TextView) findViewById(R.id.tv_person);
		tv_project = (TextView) findViewById(R.id.tv_project);
		tv_starsFace = (TextView) findViewById(R.id.tv_starsface);
		tv_collect = (TextView) findViewById(R.id.tv_collect);
		tv_userCenter = (TextView) findViewById(R.id.tv_usercenter);
		iv_person = (ImageView) findViewById(R.id.iv_person);
		iv_project = (ImageView) findViewById(R.id.iv_project);
		iv_collect = (ImageView) findViewById(R.id.iv_collect);
		iv_userCenter = (ImageView) findViewById(R.id.iv_usercenter);
		rl_person.setOnClickListener(this);
		rl_project.setOnClickListener(this);
		rl_starsFace.setOnClickListener(this);
		rl_collect.setOnClickListener(this);
		rl_userCenter.setOnClickListener(this);
		selectedType = 1;
		selectedItems();
		 // Start the service
        ServiceManager serviceManager = new ServiceManager(this);
        serviceManager.setNotificationIcon(R.drawable.ic_launcher);
        serviceManager.startService();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_person:
			selectedType = 1;
			selectedItems();
			break;
		case R.id.rl_project:
			selectedType = 2;
			selectedItems();
			break;
		case R.id.rl_starsface:
			selectedType = 3;
			selectedItems();
			break;
		case R.id.rl_collect:
			selectedType = 4;
			selectedItems();
			break;
		case R.id.rl_usercenter:
			selectedType = 5;
			selectedItems();
			break;

		default:
			break;
		}
	}

	private void selectedItems() {
		switch (selectedType) {
		case 1:
			iv_person.setImageResource(R.drawable.main_action_person_on);
			tv_person.setTextColor(getResources().getColorStateList(R.color.blue_bg));
			iv_project.setImageResource(R.drawable.main_action_project_off);
			tv_project.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			tv_starsFace.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			iv_collect.setImageResource(R.drawable.main_action_collect_off);
			tv_collect.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			iv_userCenter.setImageResource(R.drawable.main_action_usercenter_off);
			tv_userCenter.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			if (personFragment == null) {
				personFragment = new PersonFragment();
				if (lastSelectedFragment == null) {
					getSupportFragmentManager().beginTransaction().add(R.id.main_content, personFragment)
							.show(personFragment).commit();
				} else {
					getSupportFragmentManager().beginTransaction().add(R.id.main_content, personFragment)
							.show(personFragment).hide(lastSelectedFragment).commit();
				}
			} else {
				if (lastSelectedFragment == null) {
					getSupportFragmentManager().beginTransaction().show(personFragment).commit();
				} else {
					getSupportFragmentManager().beginTransaction().hide(lastSelectedFragment).show(personFragment)
							.commit();
				}
			}
			lastSelectedFragment = personFragment;
			break;
		case 2:
			iv_person.setImageResource(R.drawable.main_action_person_off);
			tv_person.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			iv_project.setImageResource(R.drawable.main_action_project_on);
			tv_project.setTextColor(getResources().getColorStateList(R.color.blue_bg));
			tv_starsFace.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			iv_collect.setImageResource(R.drawable.main_action_collect_off);
			tv_collect.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			iv_userCenter.setImageResource(R.drawable.main_action_usercenter_off);
			tv_userCenter.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			if (projectFragment == null) {
				projectFragment = new ProjectFragment();
				if (lastSelectedFragment == null) {
					getSupportFragmentManager().beginTransaction().add(R.id.main_content, projectFragment)
							.show(projectFragment).commit();
				} else {
					getSupportFragmentManager().beginTransaction().add(R.id.main_content, projectFragment)
							.show(projectFragment).hide(lastSelectedFragment).commit();
				}
			} else {
				if (lastSelectedFragment == null) {
					getSupportFragmentManager().beginTransaction().show(projectFragment).commit();
				} else {
					getSupportFragmentManager().beginTransaction().hide(lastSelectedFragment).show(projectFragment)
							.commit();
				}
			}
			lastSelectedFragment = projectFragment;
			break;

		case 3:
			iv_person.setImageResource(R.drawable.main_action_person_off);
			tv_person.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			iv_project.setImageResource(R.drawable.main_action_project_off);
			tv_project.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			tv_starsFace.setTextColor(getResources().getColorStateList(R.color.blue_bg));
			iv_collect.setImageResource(R.drawable.main_action_collect_off);
			tv_collect.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			iv_userCenter.setImageResource(R.drawable.main_action_usercenter_off);
			tv_userCenter.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			if (starsFaceFragment == null) {
				starsFaceFragment = new StarsFaceFragment();
				if (lastSelectedFragment == null) {
					getSupportFragmentManager().beginTransaction().add(R.id.main_content, starsFaceFragment)
							.show(starsFaceFragment).commit();
				} else {
					getSupportFragmentManager().beginTransaction().add(R.id.main_content, starsFaceFragment)
							.show(starsFaceFragment).hide(lastSelectedFragment).commit();
				}
			} else {
				if (lastSelectedFragment == null) {
					getSupportFragmentManager().beginTransaction().show(starsFaceFragment).commit();
				} else {
					getSupportFragmentManager().beginTransaction().hide(lastSelectedFragment).show(starsFaceFragment)
							.commit();
				}
			}
			lastSelectedFragment = starsFaceFragment;
			break;

		case 4:
			LoginUtils loginUtils=new LoginUtils() {
				
				@Override
				public void DoOnLongin() {
					toCollect();
				}
			};
			loginUtils.ToLogin(MainActivity.this);
			break;

		case 5:
			iv_person.setImageResource(R.drawable.main_action_person_off);
			tv_person.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			iv_project.setImageResource(R.drawable.main_action_project_off);
			tv_project.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			tv_starsFace.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			iv_collect.setImageResource(R.drawable.main_action_collect_off);
			tv_collect.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
			iv_userCenter.setImageResource(R.drawable.main_action_usercenter_on);
			tv_userCenter.setTextColor(getResources().getColorStateList(R.color.blue_bg));
			if (userCenterFragment == null) {
				userCenterFragment = new UserCenterFragment();
				if (lastSelectedFragment == null) {
					getSupportFragmentManager().beginTransaction().add(R.id.main_content, userCenterFragment)
							.show(userCenterFragment).commit();
				} else {
					getSupportFragmentManager().beginTransaction().add(R.id.main_content, userCenterFragment)
							.show(userCenterFragment).hide(lastSelectedFragment).commit();
				}
			} else {
				if (lastSelectedFragment == null) {
					getSupportFragmentManager().beginTransaction().show(userCenterFragment).commit();
				} else {
					getSupportFragmentManager().beginTransaction().hide(lastSelectedFragment).show(userCenterFragment)
							.commit();
				}
			}
			lastSelectedFragment = userCenterFragment;
			break;

		default:
			break;
		}
	}

	
	/**
     * 两秒内按两次返回键退出程序
     */
    private long exitTime =   0;
 
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
            	ToastTool.showShortToast(MainActivity.this, "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
            	MainActivity.this.finish();
            	DtdApplication.exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

	
	
	@Override
	public void setSelectedFragment(BaseFragment selectedFragment) {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==LoginUtils.TOLOGIN && resultCode==RESULT_OK) {
			toCollect();
		}else if (requestCode==LoginUtils.TOLOGIN2 && resultCode==RESULT_OK) {
			Intent intent=new Intent(MainActivity.this,FirstPushWorksActivity.class);
			MainActivity.this.startActivity(intent);
			MobclickAgentUtils.MobclickEventName(MainActivity.this,"0005","发布通告","发布通告");
		}else if (requestCode==LoginUtils.TOLOGIN3 && resultCode==RESULT_OK) {
			Intent intent=new Intent(MainActivity.this,FirstStepActivity.class);
			MainActivity.this.startActivity(intent);
			MobclickAgentUtils.MobclickEventName(MainActivity.this,"0006","演员入驻","演员入驻");
		}else if (requestCode==LoginUtils.TOLOGIN4 && resultCode==RESULT_OK) {
			startActivity(new Intent(MainActivity.this,MessageCategoryActivity.class));
			MobclickAgentUtils.MobclickEventName(MainActivity.this,"0002","消息中心","消息中心");
		}
	}
	
	
	private void toCollect(){
		iv_person.setImageResource(R.drawable.main_action_person_off);
		tv_person.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
		iv_project.setImageResource(R.drawable.main_action_project_off);
		tv_project.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
		tv_starsFace.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
		iv_collect.setImageResource(R.drawable.main_action_collect_on);
		tv_collect.setTextColor(getResources().getColorStateList(R.color.blue_bg));
		iv_userCenter.setImageResource(R.drawable.main_action_usercenter_off);
		tv_userCenter.setTextColor(getResources().getColorStateList(R.color.text_light_grey));
		if (collectFragment == null) {
			collectFragment = new CollectFragment();
			if (lastSelectedFragment == null) {
				getSupportFragmentManager().beginTransaction().add(R.id.main_content, collectFragment)
						.show(collectFragment).commit();
			} else {
				getSupportFragmentManager().beginTransaction().add(R.id.main_content, collectFragment)
						.show(collectFragment).hide(lastSelectedFragment).commit();
			}
		} else {
			if (lastSelectedFragment == null) {
				getSupportFragmentManager().beginTransaction().show(collectFragment).commit();
			} else {
				getSupportFragmentManager().beginTransaction().hide(lastSelectedFragment).show(collectFragment).commit();
			}
		}
		lastSelectedFragment = collectFragment;
	}
	
	
	
}
