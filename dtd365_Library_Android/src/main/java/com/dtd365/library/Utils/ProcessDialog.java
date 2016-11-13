package com.dtd365.library.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;

/**
 * @author cwtlib
 * @version 1.0.1
 * @create 2014-6-26
 * 
 * @description :
 * this is a common dialog ,it provider for
 * simple progress view.such as it's style and size
 */
public class ProcessDialog extends ProgressDialog {
	private Progress process = null;
	
	public ProcessDialog(Context context,View view,Progress process) {
		super(context,process.getTheme());
		this.process = process;
		
		LayoutParams lp = getWindow().getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.dimAmount = process.getAlpha();
		lp.flags = LayoutParams.FLAG_LAYOUT_NO_LIMITS;
		
		getWindow().setAttributes(lp);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		
		show();
		setContentView(view);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(KeyEvent.KEYCODE_BACK == keyCode) {
			if(!process.isKeyCodeBackable()) {
				return true;
			}
		 }
		 
		 return super.onKeyDown(keyCode, event);
	}
}
