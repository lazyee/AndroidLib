package com.leeorz.lib.utils;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class WindowUtil {
	/**
	 * @Title: noTittle 
	 * @Description: make tittle hide
	 * @param @param activity    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public static void noTittle(Activity activity){
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	/**
	 * @Title: noStatusBar 
	 * @Description: make status bar hide
	 * @param @param activity    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public static void noStatusBar(Activity activity){
		// 隐去状态栏部分(电池等图标和一切修饰部分)
				activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	/**
	 * @Title: fullScreen 
	 * @Description: 全屏
	 * @param @param activity    设定文件 
	 * @return void    返回类型 
	 * @throws
	 */
	public static void fullScreen(Activity activity){
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
}
