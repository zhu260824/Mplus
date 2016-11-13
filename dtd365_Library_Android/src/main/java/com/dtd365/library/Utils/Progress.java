package com.dtd365.library.Utils;

/**
 * @author cwtlib
 * @version 1.0.1
 * @create 2014-6-26
 * @description :
 * this object is used for processdialog ,it
 * provider process view and event listener.
 */
public class Progress {
	private int theme;			// the dialog theme
	private int contentView;	// the view of dialog
	private int width;
	private int height;
	
	private int processId;		// the process view of dialog
	
	private int rotateAnimation; // the animation of rotate view
	
	private boolean isClickable;
	
	private float alpha;
	
	private boolean keyCodeBackable = true;		// controll onkeydown can return or not that default can back
	
	public Progress() {
	}

	public int getTheme() {
		return theme;
	}

	public void setTheme(int theme) {
		this.theme = theme;
	}

	public int getContentView() {
		return contentView;
	}

	public void setContentView(int contentView) {
		this.contentView = contentView;
	}

	public int getProcessId() {
		return processId;
	}

	public void setProcessId(int processId) {
		this.processId = processId;
	}

	public int getRotateAnimation() {
		return rotateAnimation;
	}

	public void setRotateAnimation(int rotateAnimation) {
		this.rotateAnimation = rotateAnimation;
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

	public boolean isClickable() {
		return isClickable;
	}

	public void setClickable(boolean isClickable) {
		this.isClickable = isClickable;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public boolean isKeyCodeBackable() {
		return keyCodeBackable;
	}

	public void setKeyCodeBackable(boolean keyCodeBackable) {
		this.keyCodeBackable = keyCodeBackable;
	}
}
