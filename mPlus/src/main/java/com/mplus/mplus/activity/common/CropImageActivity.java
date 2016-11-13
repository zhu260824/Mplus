package com.mplus.mplus.activity.common;

import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.utils.FileUtils;
import com.mplus.mplus.utils.ToastTool;
import com.mplus.mplus.view.crop.CropImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CropImageActivity extends BaseActivity implements OnClickListener {
	public static final int RATIO_1_1=1,RATIO_16_10=2,RATIO_10_16=3,THREEDTYPE=4;
	public static final String RATIOTYPE="RATIOTYPE",PHOTOURI="PHOTOURI",SAVEPATH="SAVEPATH";
	private TextView tv_ratio_1_1, tv_ratio_16_10, tv_ratio_10_16, tv_sure,tv_cancel;
	private CropImageView cropImageView;
	private LinearLayout lin_ratio;
	private String savePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crop_image);
		cropImageView = (CropImageView) findViewById(R.id.cropImageView);
		tv_ratio_1_1 = (TextView) findViewById(R.id.tv_ratio_1_1);
		tv_ratio_16_10 = (TextView) findViewById(R.id.tv_ratio_16_10);
		tv_ratio_10_16 = (TextView) findViewById(R.id.tv_ratio_10_16);
		tv_sure = (TextView) findViewById(R.id.tv_sure);
		tv_cancel= (TextView) findViewById(R.id.tv_cancel);
		lin_ratio= (LinearLayout) findViewById(R.id.lin_ratio);
		tv_ratio_1_1.setOnClickListener(this);
		tv_ratio_16_10.setOnClickListener(this);
		tv_ratio_10_16.setOnClickListener(this);
		tv_sure.setOnClickListener(this);
		tv_cancel.setOnClickListener(this);
		inintView();
	}


	
	private void inintView(){
		Intent intent=getIntent();
		int ratioType=intent.getIntExtra(RATIOTYPE,1);
		switch (ratioType) {
		case THREEDTYPE:
			lin_ratio.setVisibility(View.VISIBLE);
			cropImageView.setCropMode(CropImageView.CropMode.RATIO_1_1);
			break;
		case RATIO_16_10:
			lin_ratio.setVisibility(View.GONE);	
			cropImageView.setCropMode(CropImageView.CropMode.RATIO_16_10);
			break;
		case RATIO_10_16:
			lin_ratio.setVisibility(View.GONE);	
			cropImageView.setCropMode(CropImageView.CropMode.RATIO_10_16);
			break;
		case RATIO_1_1:
		default:
			lin_ratio.setVisibility(View.GONE);	
			cropImageView.setCropMode(CropImageView.CropMode.RATIO_1_1);
			break;
		}
		String uri=intent.getStringExtra(PHOTOURI);
		if (TextUtils.isEmpty(uri)) {
			ToastTool.showShortToast(CropImageActivity.this, "图片地址为空");
		}else {
			ImageLoader.getInstance().displayImage("file://"+uri, cropImageView, new DisplayImageOptions.Builder()
					.showImageForEmptyUri(R.drawable.heisebeijing)// 设置图片Uri为空或是错误的时候显示的图片
					.showImageOnFail(R.drawable.heisebeijing)// 设置错误时候显示的图片
					.showImageOnLoading(R.drawable.heisebeijing)//设置图片在下载期间显示的图片
					.cacheInMemory(false)// 设置下载的图片是否缓存在内存中
					.cacheOnDisk(false)// 设置下载的图片是否缓存在SD卡中
					.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
					.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
//					.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
					.build());
		}
		savePath=intent.getStringExtra(SAVEPATH);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_ratio_1_1:
			cropImageView.setCropMode(CropImageView.CropMode.RATIO_1_1);
			break;
		case R.id.tv_ratio_16_10:
			cropImageView.setCropMode(CropImageView.CropMode.RATIO_16_10);	
			break;
		case R.id.tv_ratio_10_16:
			cropImageView.setCropMode(CropImageView.CropMode.RATIO_10_16);
			break;
		case R.id.tv_sure:
			savePath=FileUtils.savaBitmapToFile(CropImageActivity.this,cropImageView.getCroppedBitmap(), savePath);
			Intent backdata=new Intent();
			backdata.putExtra(SAVEPATH, savePath);
			setResult(RESULT_OK,backdata);
			CropImageActivity.this.finish();
			break;
		case R.id.tv_cancel:
			CropImageActivity.this.finish();
			break;
		default:
			break;
		}
	}
	
	
}
