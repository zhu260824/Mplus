package com.mplus.mplus.activity.common;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.dtd365.library.Utils.AppUtils;
import com.dtd365.library.http.BaseParser;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.mplus.mplus.R;
import com.mplus.mplus.base.BaseActivity;
import com.mplus.mplus.http.TokenUtils;
import com.mplus.mplus.manger.UrlManger;
import com.mplus.mplus.paser.login.UserPaser;
import com.mplus.mplus.utils.FileUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
/**
* @Title: 拍照调用相册
* @Description: 
* @author    zhulin
* @date       2015年8月26日
*/
public class UploadPhotoActivity extends BaseActivity implements OnClickListener {
	public static final String PHOTO_URL ="PHOTO_URL";// 
	private static final int PHOTO_REQUEST_CAMERA =   1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY =   2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT =   3;// 剪切结果
	  /* 头像名称 */
	private LinearLayout lin_from_cramea, lin_from_photo;
	private String filename,camerpath;
	private ProgressBar pd;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uploadpic);
		lin_from_cramea = (LinearLayout) findViewById(R.id.lin_from_cramea);
		lin_from_photo = (LinearLayout) findViewById(R.id.lin_from_photo);
		pd=(ProgressBar) findViewById(R.id.pb);
		lin_from_cramea.setOnClickListener(this);
		lin_from_photo.setOnClickListener(this);
		if (pd!=null && pd.getVisibility()==View.VISIBLE) {
			pd.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.lin_from_cramea:
			camera();
			break;
		case R.id.lin_from_photo:
			gallery();
			break;
		default:
			break;
		}
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==PHOTO_REQUEST_GALLERY) {
			if (resultCode!=RESULT_OK) 
				return;
	        // 得到图片的全路径
	        Uri uri = data.getData();
	        crop(FileUtils.getUriPath(UploadPhotoActivity.this, uri));
		}else if (requestCode==PHOTO_REQUEST_CAMERA) {
			if (resultCode!=RESULT_OK) 
				return;
			if (FileUtils.isExternalStorageAvaliable()) {
                crop(camerpath);
            } else {
                Toast.makeText(UploadPhotoActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                return;
            }
		}else if (requestCode==PHOTO_REQUEST_CUT) {
			if (resultCode!=RESULT_OK) 
				return;
			String userid=UserPaser.GetInstance().userid;
			filename=data.getStringExtra(CropImageActivity.SAVEPATH);
			UpLoadPic(userid, "png", new RequestCallBack<String>() {
				@Override
				public void onStart() {
					super.onStart();
					if (pd!=null && pd.getVisibility()==View.GONE) {
						pd.setVisibility(View.VISIBLE);
					}
				}
				@Override
				public void onLoading(long total, long current,boolean isUploading) {
					super.onLoading(total, current, isUploading);
					if (isUploading) {
						if (pd!=null && pd.isShown()) {
							pd.setProgress((int)((current/total)*100));
						}
					}
				}
				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					if (pd!=null && pd.isShown()) {
						pd.setVisibility(View.GONE);
					}
					try {
						BaseParser baseParser =new BaseParser();
						baseParser.parse(new JSONObject(arg0.result));
						Toast.makeText(UploadPhotoActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
						if (baseParser.getResultSuccess()) {
							String uri=new JSONObject(baseParser.getData()).getString("url");
							Intent backdata=new Intent();
							backdata.putExtra(PHOTO_URL, uri);
							setResult(RESULT_OK,backdata);
							UploadPhotoActivity.this.finish();
						}
						FileUtils.deleteFile(filename);
						FileUtils.deleteFile(camerpath);
					} catch (JSONException e) {
						Toast.makeText(UploadPhotoActivity.this,arg0.result, Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
				}
				
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					if (pd!=null && pd.isShown()) {
						pd.setVisibility(View.GONE);
					}
					FileUtils.deleteFile(filename);
					FileUtils.deleteFile(camerpath);
					Toast.makeText(UploadPhotoActivity.this, arg1, Toast.LENGTH_SHORT).show();
				}
			});
		}
	
	}
	
	 /**
     * 剪切图片
     * 
     */
    private void crop(String filePath) {
        Intent intent = new Intent(UploadPhotoActivity.this,CropImageActivity.class);
        intent.putExtra(CropImageActivity.PHOTOURI, filePath);
        intent.putExtra(CropImageActivity.RATIOTYPE, getIntent().getIntExtra(CropImageActivity.RATIOTYPE, 1));
        intent.putExtra(CropImageActivity.SAVEPATH, filename);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
      
    }
    /*
     * 从相册获取
     */
    public void gallery() {
        // 激活系统图库，选择一张图片
    	Intent intent = new Intent(Intent.ACTION_PICK);  
        intent.setType("image/*");//相片类型  
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }
 
    /*
     * 从相机获取
     */
    @SuppressLint("SimpleDateFormat")
	public void camera() {
    	  Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");     
        // 判断存储卡是否可以用，可用进行存储
        if (FileUtils.isExternalStorageAvaliable()) {
        	camerpath=FileUtils.getAppSaveImageOnSDCard(UploadPhotoActivity.this);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(camerpath)));
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }
 
    
	private void UpLoadPic(String userid,String suffix,RequestCallBack<String> reCallBack) {
		RequestParams params = new RequestParams();
//		params.addHeader("name", "value");
//		params.addQueryStringParameter("name", "value");

		// 只包含字符串参数时默认使用BodyParamsEntity，
		// 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
		params.addBodyParameter("userid", userid);
		params.addBodyParameter("suffix", "."+suffix);
		try {
			params.addHeader("Authorization", TokenUtils.getAuthorizationHeader(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
		// 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
		// 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
		// MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
		// 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
		if (filename==null)
			return;
		params.addBodyParameter("file", new File(filename));
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST,AppUtils.getMetaValue(UploadPhotoActivity.this,"APP_URL")+UrlManger.POSTPHOTO,params,reCallBack);
	}
    
    
    
 
}
