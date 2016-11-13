package com.dtd365.library.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



/**
 * @author cwtlib
 * @version 1.0.1
 * @create 2014-6-25
 */
public class ViewUtils {
	
	private static WindowDialog customDialog = null;
	private static PopupDialog popupDialog = null;
	private static ProcessDialog processDialog = null;
	
	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-26
	 * 
	 * @param process :
	 * @return ProcessDialog
	 * @description :
	 * this dialog is a view container ,the content is providered by
	 * xml and here is only create dialoge
	 */
	public static ProcessDialog createProcessDialog(Context context,Progress process) {
		if(!ValueUtils.isEmpty(processDialog)){
			dismissProcessDialog();
		}
		
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(process.getContentView(), null);
		
		Animation anim = null;
		if(0 != process.getProcessId() && 0 != process.getRotateAnimation()) {
			anim = AnimationUtils.loadAnimation(context, process.getRotateAnimation());
			view.findViewById(process.getProcessId()).startAnimation(anim);
		}
		
		processDialog = new ProcessDialog(context, view, process);
		processDialog.setCanceledOnTouchOutside(process.isClickable());
		return processDialog;
	}
	
	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-26
	 * 
	 * @param
	 * @return WindowDialog
	 * @description :
	 * this dialog is a view container ,the content is providered by
	 * xml and here is only handle with click event and create dialog
	 */
	public static WindowDialog createWindowDialog(Context context,DialogObj dialog) {
		dismissWindowDialog();
		
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(dialog.getContentView(), null);
		Object objValue= dialog.getTvTipValue();
		// tip content
		if(0 != dialog.getTvTipContent()) {
			TextView tvTipContent = (TextView) view.findViewById(dialog.getTipContentId());
			tvTipContent.setText(dialog.getTvTipContent());
		}
		
		//instanceof 
		 if(!"".equals(dialog.getTvTipValue())&& ((objValue instanceof String)) ) {
			TextView tvTipContent = (TextView) view.findViewById(dialog.getTipContentId());
			tvTipContent.setText(dialog.getTvTipValue());
		}
		
		// leftButton
		View btnLeftButton = null;
		if(0 != dialog.getLeftButton()) {
			btnLeftButton = view.findViewById(dialog.getLeftButton());
			btnLeftButton.setOnClickListener(dialog.getLeftClickListener());
			
			if(0 != dialog.getLeftBtnText()) {
				TextView tvTip = (TextView) btnLeftButton;
				tvTip.setText(dialog.getLeftBtnText());
			}
		}
		// rightButton
		View btnRightButton = null;
		if(0 != dialog.getRightButton()) {
			btnRightButton = view.findViewById(dialog.getRightButton());
			btnRightButton.setOnClickListener(dialog.getRightClickListener());
			
			if(0 != dialog.getRightBtnText()) {
				TextView tvTip = (TextView) btnRightButton;
				tvTip.setText(dialog.getRightBtnText());
			}
		}
		
		customDialog = new WindowDialog(context,view, dialog);
		customDialog.setCanceledOnTouchOutside(dialog.isClicklableOutSize());
		customDialog.setCancelable(true);
		return customDialog;
	}
	
	public static boolean isDialogShowing() {
		if(ValueUtils.isNotEmpty(customDialog) &&
				customDialog.isShowing()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isProcessShowing() {
		if(ValueUtils.isNotEmpty(processDialog) &&
				processDialog.isShowing()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @author cwtlib
	 * @version 1.0.5
	 * @create 2014-09-06
	 * @param 
	 * @return PopupDialog
	 
	 * @description :
	 * this dialog is a view container ,the content is providered by
	 * xml and here is only handle with creating dialog
	 */
	@SuppressLint("NewApi")
	public static PopupDialog createPopupDialog(Context context,Popup dialog) {
		dismissPopupDialog();
		
		View view = null;
		if(ValueUtils.isEmpty(dialog.getCustomView())) {
			LayoutInflater inflater = LayoutInflater.from(context);
			view = inflater.inflate(dialog.getContentView(), null);
		} else {
			view = dialog.getCustomView();
		}
		
		view.setOnTouchListener(dialog.getTouchListener());
		if(0 != dialog.getBgAlpha()) {
			view.setAlpha(dialog.getBgAlpha());
		}
		popupDialog = new PopupDialog(view,dialog.getvWidth(),dialog.getvHeight());
		ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);		// follow two lines is used for back key -00000
		popupDialog.setBackgroundDrawable(dw);
		popupDialog.setAnimationStyle(dialog.getAnimFadeInOut());
		popupDialog.setOutsideTouchable(dialog.isClickable());
		popupDialog.setFocusable(true);		// not allow user click popupwindow background event or not permit
		popupDialog.setOnDismissListener(dialog.getListener());
		popupDialog.update();
		return popupDialog;
	}
	
	public static void dismissWindowDialog() {
		if(ValueUtils.isNotEmpty(customDialog)) {
			customDialog.dismiss();
			customDialog = null;
		}
	}
	
	public static void dismissProcessDialog() {
		if(ValueUtils.isNotEmpty(processDialog)) {
			processDialog.dismiss();
			processDialog = null;
		}
	}
	
	public static void dismissPopupDialog() {
		if(ValueUtils.isNotEmpty(popupDialog) &&
				popupDialog.isShowing()) {
			popupDialog.dismiss();
			popupDialog = null;
		}
	}
	
	public static boolean isPopupShowing() {
		if(ValueUtils.isNotEmpty(popupDialog) &&
				popupDialog.isShowing()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-26
	 * 
	 * @param viewId : view resources id 
	 * @param visibleFlag : flag of handling with visible or invisible
	 *        View.visible   View.invisible  View.gone
	 * @return
	 * @description
	 */
	public static void setViewVisible(Context context,int viewId,int visibleFlag) {
		View view = ((Activity) context).findViewById(viewId);
		setViewVisible(view, visibleFlag);
	}
	
	public static void setViewVisible(View view, int visibleFlag) {
		if(ValueUtils.isEmpty(view)) {
			return;
		}
		
		if(view.getVisibility() == visibleFlag) {
			view.setVisibility(visibleFlag);
		}
	}
	
	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-25
	 * 
	 * @param 
	 * @return
	 */
	public static void hideKeyboard(Activity currentActivity) {
		InputMethodManager imm = (InputMethodManager) currentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (ValueUtils.isNotEmpty(imm)) {
			View focusView = currentActivity.getCurrentFocus();
			if (ValueUtils.isNotEmpty(focusView)) {
				imm.hideSoftInputFromWindow(focusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}
	
	/**
	 * @author cwtlib
	 * @version 1.0.1
	 * @create 2014-6-25
	 * 
	 * @param msg : show tip content
	 * @param flag : show dialog long or short time
	 * @return 
	 */
	public static void showToast(Context context,String msg,boolean flag) {
		int time = Toast.LENGTH_SHORT;
		if(flag) {
			time = Toast.LENGTH_LONG;
		}
		Toast.makeText(context, msg, time).show();
	}
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
               return;
        }
       
        int totalHeight = 0;
        for (int i = 0 ;i < listAdapter.getCount(); i++) {
               View listItem = listAdapter.getView(i, null, listView);
               listItem.measure(0, 0);  
               totalHeight += listItem.getMeasuredHeight();  
        }
       
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
	}
	
	
		  }  


