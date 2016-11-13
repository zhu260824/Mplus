package com.dtd365.library.Utils;

import android.app.Dialog;
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
 * simple window view.such as it's style and size
 */
public class WindowDialog extends Dialog {
	private View rootView = null;
	
	private DialogObj obj = null;
	
	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-26
	 * 
	 * @param view : current dialog view
	 * @param theme : style id of dialog view
	 * @description :
	 * this is used to create and initial dialog attributes
	 */
	public WindowDialog(Context context, View view, DialogObj dialog) {
		super(context, dialog.getTheme());
		obj = dialog;
		
		LayoutParams lp = getWindow().getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.dimAmount = 0;
		lp.flags = LayoutParams.FLAG_LAYOUT_NO_LIMITS;
		
		getWindow().setAttributes(lp);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		
		setContentView(view);
		rootView = view;
	}
	
	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-26
	 * 
	 * @param view : current dialog view
	 * @param theme : style id of dialog view
	 * @description :
	 * this is used to create and initial dialog attributes
	 */
	public WindowDialog(Context context, View view, Progress process) {
		super(context, process.getTheme());
		
		LayoutParams lp = getWindow().getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.dimAmount = 0;
		lp.flags = LayoutParams.FLAG_LAYOUT_NO_LIMITS | LayoutParams.FLAG_NOT_TOUCH_MODAL;
		
		getWindow().setAttributes(lp);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(view);
		rootView = view;
	}
	
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		 if(KeyEvent.KEYCODE_BACK == keyCode) {
			if(!obj.isKeyCodeBackable()) {
				return true;
			}
		 }
		 
		 return super.onKeyDown(keyCode, event);
	}
	
	public View getRootView() {
		return rootView;
	}

	public void setRootView(View rootView) {
		this.rootView = rootView;
	}
}
