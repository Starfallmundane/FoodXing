package com.lx.foodxing.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;


public class ToastUtils {
	

	/**
	 * 显示toast
	 * @param msg
	 */
	public static void showToast(final Context context, final String msg){
		
		// 获得当前线程的名称
		String threadName = Thread.currentThread().getName();
		// 判断是否是主线程，如果是主线程，直接显示toast
		if("main".equals(threadName)){
			Toast.makeText(context, msg, Toast. LENGTH_SHORT).show();
		}else{
			// 如果不是，主线程，
			((Activity) context).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(context, msg, Toast. LENGTH_SHORT).show();
				}
			});
		}
	}





}
