package com.leeorz.lib.app;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.leeorz.lib.util.AppUtils;
import com.leeorz.lib.util.DateUtils;
import com.leeorz.lib.util.FileUtils;

import java.io.File;


/**
 * 项目配置相关
 * @author 李嘉俊
 */
public class AppConfig {

	public static String DEVICE_ID = "";
	/**
	 * 是否注册到im服务器
	 */
	public static boolean IS_CONNECT_TO_IM_SUCCEED = false;
	/**
	 *APP程序名
	 */
	public static String APPNAME 		= "";

	/**
	 * 默认数据库名
	 */
	public static String DEFAULT_DBNAME = "";
	/**
	 *数据库名
	 */
	private static String DBNAME 			= DEFAULT_DBNAME;

	/**
	 * app版本号
	 */
	public static int APP_VERISON_CODE = 0;
	/**
	 *是否调试
	 */
	public static boolean 	DEBUG 	= true;
	/**
	 * 是否上传错误日志到共享文件夹
	 */
	public static boolean IS_UPLOAD_ERROR_LOG = true;
	/**
	 *数据库版本
	 */
	public static int 		DBVERSION 		= 1 ;
	/**
	 *屏幕的宽度
	 */
	public static int 		SCREEN_WIDTH 	= 0;
	/**
	 *屏幕的高度
	 */
	public static int 		SCREEN_HEIGHT 	= 0;
	/**
	 *屏幕的密度
	 */
	public static float 	SCREEN_DENSITY 	= 0.0f;
	/**
	 *程序文件夹
	 */
	public static String APP_PATH 		= "";

	/**
	 * 隐藏媒体文件
	 */
	public static String NOMEDIA			= ".nomedia";

	/**
	 *内部缓存路径
	 */
	public static String INTERNAL_DATA_CACHE_PATH = "";
	/**
	 *缓存路径
	 */
	public static String DATA_CACHE_PATH = "";
	/**
	 *缓存文件夹名
	 */
	public static String DATA_CACHE_DIR_NAME = "data";

	/**
	 *图片文件夹路径
	 */
	public static String IMAGE_PATH 		= "";
	/**
	 * 图片文件夹路径
	 */
	public static String IMAGE_PATH_NOMEDIA		= "";
	/**
	 * 图片文件夹名(用于Fresco)
	 */
	public static String IMAGE_DIR_NAME	= "images";
	/**
	 * 图片文件夹名2
	 */
	public static String IMAGE_DIR_NAME2 = "images2";
	/**
	 *音频文件夹
	 */
	public static String VOICE_PATH		= "";
	/**
	 *音频文件夹名
	 */
	public static String VOICE_DIR_NAME		= "voice";

	/**
	 * 视频文件夹
	 */
	public static String VIDEO_PATH = "";
	/**
	 * 视频文件夹名
	 */
	public static String VIDEO_DIR_NAME = "video";
	/**
	 *日志文件夹
	 */
	public static String LOG_PATH 		= "";
	/**
	 *日志文件夹名
	 */
	public static String LOG_DIR_NAME 		= "log";
	/**
	 *错误日志文件路径
	 */
	public static String ERROR_FILE_PATH = "";
	/**
	 *是否发送错误日志
	 */
	public static boolean 	IS_SEND_ERROR_LOG = false;
	/**
	 *发送给后台的错误日志的url
	 */
	public static String SEND_ERROR_WEBSERVICE_URL = "";
	/**
	 *默认的程序缓存文件夹目录(没有sdCard)
	 */
	public static String DEFUALT_CACHE_DIR = "";



	public static String getDbName() {
//		if(DEBUG){
//			return DATA_CACHE_PATH + DBNAME;
//		}else{
			return DBNAME;
//		}

	}

	public static void setDBNAME(String DBNAME) {
		AppConfig.DBNAME = DBNAME;
	}

	public static void setAPPNAME(String appname){
		APPNAME = appname;
	}

	/**
	 * 初始化参数
	 * @param context
	 */
	public static void init(Activity context) {
		if (SCREEN_DENSITY == 0 || SCREEN_WIDTH == 0 || SCREEN_HEIGHT == 0) {

			//屏幕参数初始化
			DisplayMetrics dm = new DisplayMetrics();
			context.getWindowManager().getDefaultDisplay().getMetrics(dm);
			AppConfig.SCREEN_DENSITY = dm.density;
			AppConfig.SCREEN_HEIGHT = dm.heightPixels;
			AppConfig.SCREEN_WIDTH = dm.widthPixels;

			APP_VERISON_CODE = AppUtils.getVersionCode(context);

			DEVICE_ID = AppUtils.getPesudoUniqueID();
			initAppFolder(context);
		}
	}

	/**
	 * 创建app目标目录
	 * @param context
	 */
	public static void initAppFolder(Context context){
		//默认的程序缓存文件夹目录(没有sdCard)
		DEFUALT_CACHE_DIR = APPNAME + File.separator + "temp" + File.separator;

		//缓存目录
		DATA_CACHE_PATH = AppUtils.isSDCard() == true ? Environment
				.getExternalStorageDirectory().getPath()
				+ File.separator
				+ DEFUALT_CACHE_DIR
				+ DATA_CACHE_DIR_NAME
				+ File.separator
				:context.getCacheDir().getPath()
				+ File.separator
				+ DEFUALT_CACHE_DIR
				+ DATA_CACHE_DIR_NAME
				+ File.separator;

		 //内部数据存储路径
		try {
			INTERNAL_DATA_CACHE_PATH = context.getCacheDir().getPath() + File.separator + DATA_CACHE_DIR_NAME;
		}catch (Exception e){}
		//程序安装路径
		APP_PATH = context.getCacheDir().getPath() + File.separator;

		//图片目录
		IMAGE_PATH = (AppUtils.isSDCard() == true ? Environment.getExternalStorageDirectory().getPath()
				+ File.separator
				+ DEFUALT_CACHE_DIR
				+ IMAGE_DIR_NAME
				+ File.separator
				: context.getCacheDir().getPath()
				+ File.separator
				+ DEFUALT_CACHE_DIR
				+ IMAGE_DIR_NAME
				+ File.separator);
		//图片目录
		IMAGE_PATH_NOMEDIA = (AppUtils.isSDCard() == true ? Environment
				.getExternalStorageDirectory().getPath()
				+ File.separator
				+ DEFUALT_CACHE_DIR
				+ NOMEDIA
				+ File.separator
				+ IMAGE_DIR_NAME2
				+ File.separator
				: context
				.getCacheDir().getPath()
				+ File.separator
				+ DEFUALT_CACHE_DIR
				+ NOMEDIA
				+ File.separator
				+ IMAGE_DIR_NAME2
				+ File.separator);
		//音频目录
		VOICE_PATH = (AppUtils.isSDCard() == true ? Environment
				.getExternalStorageDirectory().getPath()
				+ File.separator
				+ DEFUALT_CACHE_DIR
				+ NOMEDIA
				+ File.separator
				+ VOICE_DIR_NAME
				+ File.separator
				: context
				.getCacheDir().getPath()
				+ File.separator
				+ DEFUALT_CACHE_DIR
				+ NOMEDIA
				+ File.separator
				+ VOICE_DIR_NAME
				+ File.separator);
		//视频目录
		VIDEO_PATH = (AppUtils.isSDCard() == true ? Environment
				.getExternalStorageDirectory().getPath()
				+ File.separator
				+ DEFUALT_CACHE_DIR
				+ NOMEDIA
				+ File.separator
				+ VIDEO_DIR_NAME
				+ File.separator
				: context
				.getCacheDir().getPath()
				+ File.separator
				+ DEFUALT_CACHE_DIR
				+ NOMEDIA
				+ File.separator
				+ VIDEO_DIR_NAME
				+ File.separator);

		//错误日志目录
		LOG_PATH = (AppUtils.isSDCard() == true ? Environment
				.getExternalStorageDirectory().getPath()
				+ File.separator
				+ DEFUALT_CACHE_DIR
				+ LOG_DIR_NAME
				+ File.separator
				: context
				.getCacheDir().getPath()
				+ File.separator
				+ DEFUALT_CACHE_DIR
				+ LOG_DIR_NAME
				+ File.separator);


		//初始化错误日志文件路径
		ERROR_FILE_PATH = LOG_PATH +"error" + DateUtils.getCurrentDate(DateUtils.dateFormatYMD) + ".txt";

		//创建文件夹
		FileUtils.createFolder(IMAGE_PATH);
		FileUtils.createFolder(IMAGE_PATH_NOMEDIA);
		FileUtils.createFolder(DATA_CACHE_PATH);
		FileUtils.createFolder(LOG_PATH);
		FileUtils.createFolder(VOICE_PATH);
		FileUtils.createFolder(VIDEO_PATH);
		FileUtils.createFolder(INTERNAL_DATA_CACHE_PATH);


	}

}
