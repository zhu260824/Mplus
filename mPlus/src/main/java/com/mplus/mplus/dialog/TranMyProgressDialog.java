package com.mplus.mplus.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;

import com.mplus.mplus.R;

/********************************************************************
 * TODO  
 * 进度对话框
 *******************************************************************/

public class TranMyProgressDialog extends Dialog {
	private static TranMyProgressDialog MyProgressDialog = null;

	public TranMyProgressDialog(Context context) {
		super(context);
	}

	public TranMyProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static TranMyProgressDialog createDialog(Context context) {
		MyProgressDialog = new TranMyProgressDialog(context,R.style.TranstyleDialog);
		MyProgressDialog.setContentView(R.layout.my_progressdialog);
		MyProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		return MyProgressDialog;
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		if (MyProgressDialog == null) {
			return;
		}
		ImageView imageView = (ImageView) MyProgressDialog.findViewById(R.id.loadingImageView);
		AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
		animationDrawable.start();
	}

	
	public TranMyProgressDialog setTitile(String strTitle) {
		return MyProgressDialog;
	}

	
	// public TranMyProgressDialog setMessage(String strMessage){
	// TextView tvMsg =
	// (TextView)MyProgressDialog.findViewById(R.id.id_tv_loadingmsg);
	//
	// if (tvMsg != null){
	// tvMsg.setText(strMessage);
	// }
	//
	// return MyProgressDialog;
	// }

}
