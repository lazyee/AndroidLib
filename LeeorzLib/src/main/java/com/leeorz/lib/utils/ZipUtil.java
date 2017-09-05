package com.leeorz.lib.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
}
