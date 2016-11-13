package com.dtd365.library.Utils;

import android.view.View;

/**
 * @author cwtlib
 * @version 1.0.1
 * @create 2014-6-26
 * @description :
 * this object is used for windowdialog ,it
 * provider dialog content and event listener .
 */
public class DialogObj {
	private int theme;			// the dialog theme
	private int contentView;	// the view of dialog
	private int width;
	private int height;
	
	private int leftButton;		// button of left (default)
	private int rightButton; 	// button of right
	private int leftBtnText;	// button of left text
	private int rightBtnText;	// button of right text
	
	private int tipContentId;	// id of tip content
	private int tvTipContent;	// tip of dialog content
	private String tvTipValue;	// tip of dialog value
	
	private boolean keyCodeBackable = true;
	
	private View.OnClickListener leftClickListener;		
	private View.OnClickListener rightClickListener;
	
	private boolean isClicklableOutSize;
	
	public DialogObj() {
	}

	public View.OnClickListener getLeftClickListener() {
		return leftClickListener;
	}

	public void setLeftClickListener(View.OnClickListener leftClickListener) {
		this.leftClickListener = leftClickListener;
	}

	public View.OnClickListener getRightClickListener() {
		return rightClickListener;
	}

	public void setRightClickListener(View.OnClickListener rightClickListener) {
		this.rightClickListener = rightClickListener;
	}

	public int getContentView() {
		return contentView;
	}

	public void setContentView(int contentView) {
		this.contentView = contentView;
	}

	public int getLeftButton() {
		return leftButton;
	}

	public void setLeftButton(int leftButton) {
		this.leftButton = leftButton;
	}

	public int getRightButton() {
		return rightButton;
	}

	public void setRightButton(int rightButton) {
		this.rightButton = rightButton;
	}

	public int getTheme() {
		return theme;
	}

	public void setTheme(int theme) {
		this.theme = theme;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isClicklableOutSize() {
		return isClicklableOutSize;
	}

	public void setClicklableOutSize(boolean isClicklableOutSize) {
		this.isClicklableOutSize = isClicklableOutSize;
	}

	public int getLeftBtnText() {
		return leftBtnText;
	}

	public void setLeftBtnText(int leftBtnText) {
		this.leftBtnText = leftBtnText;
	}

	public int getRightBtnText() {
		return rightBtnText;
	}

	public void setRightBtnText(int rightBtnText) {
		this.rightBtnText = rightBtnText;
	}

	public int getTipContentId() {
		return tipContentId;
	}

	public void setTipContentId(int tipContentId) {
		this.tipContentId = tipContentId;
	}

	public int getTvTipContent() {
		return tvTipContent;
	}

	public void setTvTipContent(int tvTipContent) {
		this.tvTipContent = tvTipContent;
	}

	public boolean isKeyCodeBackable() {
		return keyCodeBackable;
	}

	public void setKeyCodeBackable(boolean keyCodeBackable) {
		this.keyCodeBackable = keyCodeBackable;
	}

	public String getTvTipValue() {
		return tvTipValue;
	}

	public void setTvTipValue(String tvTipValue) {
		this.tvTipValue = tvTipValue;
	}
	
}
