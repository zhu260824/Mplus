package org.androidpn.client;

import com.mplus.mplus.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


public class BootCompletedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences preferences = context.getSharedPreferences(
				Constants.SHARED_PREFERENCE_NAME, Context.MODE_APPEND);
		if (preferences.getBoolean(Constants.SETTINGS_AUTOSTART, true)) {
			ServiceManager serviceManager = new ServiceManager(context);
			serviceManager.setNotificationIcon(R.drawable.ic_launcher);
			serviceManager.startService();
			Log.d("BootCompletedReceiver",
					"boot compeleted,serviceManager startService");
		}
	}

}
