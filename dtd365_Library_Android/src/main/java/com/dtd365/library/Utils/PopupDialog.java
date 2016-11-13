package com.dtd365.library.Utils;

import android.view.View;
import android.widget.PopupWindow;

/**
 * @author cwtlib
 * @version 1.0.5
 * @create 2014-09-06
 * 
 * @description :
 * this is a common use dialog.it can provider
 * by android's popupwindow ,use it ,you can 
 * custom style and animation and so. 
 */
public class PopupDialog extends PopupWindow {
	
	public PopupDialog(View view,int width,int height) {
		super(view,width,height);
	}
}
