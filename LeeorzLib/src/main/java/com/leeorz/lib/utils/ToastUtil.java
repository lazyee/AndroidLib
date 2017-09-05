package com.leeorz.lib.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 *
 */
public class ToastUtil {



	/**
	 * 短时间显示Toast
	 * 
	 * @param context
	 * @param message
	 */
	public static void showShort(Context context, CharSequence message) {
		if(context == null)return;
		Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 短时间显示Toast
	 *
	 * @param context
	 * @param strResId
	 */
	public static void showShort(Context context, int strResId) {
		if(context == null)return;
		Toast.makeText(context,context.getResources().getText(strResId), Toast.LENGTH_SHORT).show();
	}

	/**
	 * 长时间显示Toast
	 *
	 * @param context
	 * @param message
	 */
	public static void showLong(Context context, CharSequence message) {
		if(context == null)return;
		Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * 长时间显示Toast
	 *
	 * @param context
	 * @param strResId
	 */
	public static void showLong(Context context, int strResId) {
		if(context == null)return;
		Toast.makeText(context, context.getResources().getText(strResId), Toast.LENGTH_LONG).show();
	}

	/**
	 * 自定义显示Toast时间
	 *
	 * @param context
	 * @param message
	 * @param duration
	 */
	public static void show(Context context, CharSequence message, int duration) {
		if(context == null)return;
		Toast.makeText(context, message, duration).show();
	}

	/**
	 * 自定义显示Toast时间
	 * 
	 * @param context
	 * @param strResId
	 * @param duration
	 */
	public static void show(Context context, int strResId, int duration) {
		if(context == null)return;
		Toast.makeText(context, context.getResources().getText(strResId), duration).show();
	}
}
