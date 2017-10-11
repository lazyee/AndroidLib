package com.leeorz.lib.utils;

import android.content.Context;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.progress.ProgressMonitor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	
	/**
	 * 压缩文件
	 * @return 压缩成功或失败
	 */
	public static boolean zipFile(String inFilePath, String outFilePath){
		ZipOutputStream zipOutputStream;
		try {
				zipOutputStream = new ZipOutputStream(new FileOutputStream(outFilePath));
				File file = new File(inFilePath);
				//压缩  
				ZipFiles(file.getParent()+ File.separator, file.getName(), zipOutputStream);
		        //完成,关闭  
		        zipOutputStream.finish();  
		        zipOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * 解压文件
	 * @param unZipfileName
	 * @param mDestPath
	 */
	public static void unZipFile(String unZipfileName, String mDestPath) {
	       if (!mDestPath.endsWith("/")) {  
	           mDestPath = mDestPath + "/";  
	       }  
	       FileOutputStream fileOut = null;
	       ZipInputStream zipIn = null;
	       ZipEntry zipEntry = null;
	       File file = null;
	       int readedBytes = 0;  
	       byte buf[] = new byte[4096];  
	       try {  
	           zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(unZipfileName)));
	           while ((zipEntry = zipIn.getNextEntry()) != null) {  
	               file = new File(mDestPath + zipEntry.getName());
	               if (zipEntry.isDirectory()) {  
	                   file.mkdirs();  
	               } else {  
	                   // 如果指定文件的目录不存在,则创建之.  
	                   File parent = file.getParentFile();
	                   if (!parent.exists()) {  
	                       parent.mkdirs();  
	                   }  
	                   fileOut = new FileOutputStream(file);
	                   while ((readedBytes = zipIn.read(buf)) > 0) {  
	                       fileOut.write(buf, 0, readedBytes);  
	                   }  
	                   fileOut.close();  
	               }  
	               zipIn.closeEntry();  
	           }  
	       } catch (IOException ioe) {
	           ioe.printStackTrace();  
	       }  
	   } 
	
	/** 
     * 压缩文件 
     * @param folderString 
     * @param fileString 
     * @param zipOutputSteam 
     * @throws Exception
     */  
    private static void ZipFiles(String folderString, String fileString, ZipOutputStream zipOutputSteam)throws Exception {
        android.util.Log.v("XZip", "ZipFiles(String, String, ZipOutputStream)");  
          
        if(zipOutputSteam == null)  
            return;  
          
        File file = new File(folderString+fileString);
          
        //判断是不是文件  
        if (file.isFile()) {  
  
            ZipEntry zipEntry =  new ZipEntry(fileString);
            FileInputStream inputStream = new FileInputStream(file);
            zipOutputSteam.putNextEntry(zipEntry);  
              
            int len;  
            byte[] buffer = new byte[4096];  
              
            while((len=inputStream.read(buffer)) != -1)  
            {  
                zipOutputSteam.write(buffer, 0, len);  
            }  
              
            zipOutputSteam.closeEntry();  
        }  
        else {  
              
            //文件夹的方式,获取文件夹下的子文件  
            String fileList[] = file.list();
              
            //如果没有子文件, 则添加进去即可  
            if (fileList.length <= 0) {  
                ZipEntry zipEntry =  new ZipEntry(fileString+ File.separator);
                zipOutputSteam.putNextEntry(zipEntry);  
                zipOutputSteam.closeEntry();                  
            }  
              
            //如果有子文件, 遍历子文件  
            for (int i = 0; i < fileList.length; i++) {  
                ZipFiles(folderString, fileString+ File.separator+fileList[i], zipOutputSteam);
            }//end of for  
      
        }//end of if  
          
    }//end of func

	/**
	 * 解压assets的zip压缩文件到指定目录
	 * @param context 上下文对象
	 * @param assetFileName 压缩文件名
	 * @param outputDirectory 输出目录
	 * @param isReWrite 是否覆盖
	 * @throws IOException
	 */
	public static void unZip(Context context, String assetFileName, String outputDirectory, boolean isReWrite) throws IOException {
		// 创建解压目标目录
		File file = new File(outputDirectory);
		// 如果目标目录不存在，则创建
		if (!file.exists()) {
			file.mkdirs();
		}
		// 打开压缩文件
		InputStream inputStream = context.getAssets().open(assetFileName);
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		// 读取一个进入点
		ZipEntry zipEntry = zipInputStream.getNextEntry();
		// 使用1Mbuffer
		byte[] buffer = new byte[1024 * 1024];
		// 解压时字节计数
		int count = 0;
		// 如果进入点为空说明已经遍历完所有压缩包中文件和目录
		while (zipEntry != null) {
			// 如果是一个目录
			if (zipEntry.isDirectory()) {
				file = new File(outputDirectory + File.separator + zipEntry.getName());
				// 文件需要覆盖或者是文件不存在
				if (isReWrite || !file.exists()) {
					file.mkdir();
				}
			} else {
				// 如果是文件
				file = new File(outputDirectory + File.separator + zipEntry.getName());
				// 文件需要覆盖或者文件不存在，则解压文件
				if (isReWrite || !file.exists()) {
					file.createNewFile();
					FileOutputStream fileOutputStream = new FileOutputStream(file);
					while ((count = zipInputStream.read(buffer)) > 0) {
						fileOutputStream.write(buffer, 0, count);
					}
					fileOutputStream.close();
				}
			}
			// 定位到下一个文件入口
			zipEntry = zipInputStream.getNextEntry();
		}
		zipInputStream.close();
	}


	public static void unZip(File zipFile, String outputFilePath, OnUnZipCallback onUnZipCallback, boolean isDeleteZip) throws ZipException {
		unZip(zipFile,null,outputFilePath,onUnZipCallback,isDeleteZip);
	}

	public static void unZip(final File zipFile, String password, final String outputFilePath, final OnUnZipCallback onUnZipCallback, final boolean isDeleteZip) throws ZipException {
		ZipFile zFile = new ZipFile(zipFile);
		zFile.setFileNameCharset("UTF-8");

		if (!zFile.isValidZipFile()) { //
			throw new ZipException("该文件不是有效的压缩包!");
		}
		File destDir = new File(outputFilePath); //
		if (destDir.isDirectory() && !destDir.exists()) {
			destDir.mkdir();
		}
		if (zFile.isEncrypted()) {
			zFile.setPassword(password == null?"":password); // 设置解压密码
		}

		final ProgressMonitor progressMonitor = zFile.getProgressMonitor();
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int precentDone = 0;
					if (onUnZipCallback == null)return;

					onUnZipCallback.onStart();
					while (true) {
						// 每隔50ms,发送一个解压进度出去
						Thread.sleep(1);
						precentDone = progressMonitor.getPercentDone();
						onUnZipCallback.onUnZipping(precentDone);
						if (precentDone >= 100) {
							break;
						}
					}
					onUnZipCallback.onSuccess();
				} catch (InterruptedException e) {
					onUnZipCallback.onFail();
					e.printStackTrace();
				} finally {
					if (isDeleteZip) {
						zipFile.delete();//将原压缩文件删除
					}
				}
			}
		});
		thread.start();
		zFile.setRunInThread(true); //true 在子线程中进行解压 , false主线程中解压
		zFile.extractAll(outputFilePath); //将压缩文件解压到filePath中...
	}

	public interface OnUnZipCallback{
		void onStart();
		void onUnZipping(int progress);
		void onFail();
		void onSuccess();
	}
}
