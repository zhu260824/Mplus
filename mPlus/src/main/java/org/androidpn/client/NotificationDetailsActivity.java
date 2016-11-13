package org.androidpn.client;

import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class NotificationDetailsActivity extends BaseActivity {
	private TextView tv_title, tv_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_details);
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_content = (TextView) findViewById(R.id.tv_content);
		initData();
	}

	private void initData() {
		Intent intent = getIntent();
		// String notificationId =
		// intent.getStringExtra(Constants.NOTIFICATION_ID);
		// String notificationApiKey =
		// intent.getStringExtra(Constants.NOTIFICATION_API_KEY);
		String notificationTitle = intent.getStringExtra(Constants.NOTIFICATION_TITLE);
		String notificationMessage = intent.getStringExtra(Constants.NOTIFICATION_MESSAGE);
		// String notificationUri =
		// intent.getStringExtra(Constants.NOTIFICATION_URI);
		// String notificationImageUri =
		// intent.getStringExtra(Constants.NOTIFICATION_IMAGE_URI);
		tv_title.setText(TextUtils.isEmpty(notificationTitle) ? "" : notificationTitle);
		tv_content.setText(TextUtils.isEmpty(notificationMessage) ? "" : notificationMessage);
	}

	protected void onPause() {
		super.onPause();
		finish();
	}

	protected void onStop() {
		super.onStop();
		finish();
	}
}
