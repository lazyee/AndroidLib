package com.leeorz.lib.util;

import java.io.DataOutputStream;

public class RootUtils {
	/**
	 * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
	 * 
	 * @return 应用程序是/否获取Root权限
	 */
	public static boolean upgradeRootPermission(String pkgCodePath) {
		Process process = null;
		DataOutputStream os = null;
		try {
			String cmd = "chmod 777 " + pkgCodePath;
			process = Runtime.getRuntime().exec("su"); // 切换到root帐号
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
			}
		}
		return true;
	}
	
	/**
	 * 隐藏平板的状态栏
	 */
	public static void hideStateBarForPad() {
		/*
		 * 隐藏运行Android 4.0以上系统的平板的屏幕下方的状态栏
		 */
		try {
			String ProcID = "79";
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
				ProcID = "42"; // ICS
			// 需要root 权限
			Process proc = Runtime.getRuntime().exec(
					new String[] {
							"su",
							"-c",
							"service call activity " + ProcID
									+ " s16 com.android.systemui" }); // WAS
			proc.waitFor();
		} catch (Exception ex) {
		}
	}

	/**
	 * 显示平板的状态栏
	 */
	public static void showStateBarForPad() {
		/*
		 * 恢复运行Android 4.0以上系统的平板的屏幕下方的状态栏
		 */
		 try {
	            Process proc = Runtime.getRuntime().exec(new String[]{
	                    "am","startservice","-n","com.android.systemui/.SystemUIService"});
	            proc.waitFor();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
}
