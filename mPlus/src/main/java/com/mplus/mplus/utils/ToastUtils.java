package com.mplus.mplus.utils;

import android.content.Context;
import android.widget.Toast;


public class ToastUtils {
	private static String oldMsg;  
	protected static Toast toast   = null;  
	private static long oneTime=0;  
    private static long twoTime=0;  

	 public static void showToastOnce(Context context, String s){      
         if(toast==null){   
             toast =Toast.makeText(context, s, Toast.LENGTH_SHORT);  
             toast.show();  
            oneTime=System.currentTimeMillis();  
        }else{  
             twoTime=System.currentTimeMillis();  
             if(s.equals(oldMsg)){  
                 if(twoTime-oneTime>Toast.LENGTH_SHORT){  
                     toast.show();  
                 }  
             }else{  
                 oldMsg = s;  
                 toast.setText(s);  
                 toast.show();  
             }         
        }  
         oneTime=twoTime;  
     }  
      
       
     public static void showToastOnce(Context context, int resId){     
         showToastOnce(context, context.getString(resId));  
     }  
}
