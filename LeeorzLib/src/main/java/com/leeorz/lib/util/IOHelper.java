package com.leeorz.lib.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * IO处理相关（包括文件、文件夹、路径等）
 * 
 * @author linweixian
 * 
 */
public class IOHelper {

	/**
	 * 获取文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(String filePath) {
		if (StringHelper.isNullOrEmpty(filePath))
			return null;

		if (!exists(filePath)) {
			return "";
		}
		File file = new File(filePath);
		return file.getName();
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean exists(String fileName) {
		if (StringHelper.isNullOrEmpty(fileName)) {
			return false;
		}

		File file = new File(fileName);

		return file.exists();
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * 获取带点的文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtensionNameWithDot(String filename) {
		/*
		 * if ((filename != null) && (filename.length() > 0)) { int dot =
		 * filename.lastIndexOf('.'); if ((dot > -1) && (dot <
		 * (filename.length() - 1))) { return filename.substring(dot); } }
		 * return filename;
		 */
		String result = getExtensionName(filename);
		if (!StringHelper.isNullOrEmpty(result)) {
			return "." + result;
		}
		return "";
	}

	/**
	 * 获取不带扩展名的文件名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileNameWithoutExtension(String filename) {
		if (StringHelper.isNullOrEmpty(filename)) {
			return "";
		}
		String fileExtName = "." + getExtensionName(filename);
		String fileName = getFileName(filename);
		return fileName.replace(fileExtName, "");
	}

	/**
	 * 获取不带扩展名的文件
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileWithoutExtension(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	/**
	 * 从文本文件中读取内容
	 * 
	 * @param path
	 * @return
	 */
	public static String readFile(String path) {
		if (!exists(path))
			return "";
		return readFile(path, "UTF-8");
	}

	/**
	 * 从文本文件中读取内容
	 * 
	 * @param path
	 * @return
	 */
	public static String readFile(String path, String charsetName) {
		String readStr = "";
		try {
			String filepath = path; // 得到文本文件的路径
			// File file = new File(filepath);
			// FileReader fileread = new FileReader(file);
			// BufferedReader bufread = new BufferedReader(fileread);

			BufferedReader bufread = new BufferedReader(new InputStreamReader(
					new FileInputStream(filepath), charsetName));
			String read = "";
			while ((read = bufread.readLine()) != null) {
				if (!StringHelper.isNullOrEmpty(readStr)) {
					readStr += " /r/n ";
				}
				readStr += read;
			}
			bufread.close();

		} catch (Exception d) {
			System.out.println(d.getMessage());
		}
		return readStr; // 返回从文本文件中读取内容
	}

	/**
	 * 把文件读成字节数组 the traditional io way
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFileToByteArray(String filename) {
		File f = new File(filename);
		if (!f.exists()) {
			return null;
			// throw new FileNotFoundException(filename);
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
			// throw e;
		} finally {
			try {
				in.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 把文件读成字节数组 NIO way
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFileToByteArray2(String filename) {
		File f = new File(filename);
		if (!f.exists()) {
			// throw new FileNotFoundException(filename);
			return null;
		}

		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
				// do nothing
				// System.out.println("reading");
			}
			return byteBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
			// throw e;
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 把文件读成字节数组 Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFileToByteArray3(String filename) {
		FileChannel fc = null;
		try {
			fc = new RandomAccessFile(filename, "r").getChannel();
			MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,
					fc.size()).load();
			System.out.println(byteBuffer.isLoaded());
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				// System.out.println("remain");
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
			// throw e;
		} finally {
			try {
				fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 向文本文件中写入内容
	 * 
	 * @param path
	 * @param content
	 * @param append
	 */
	public static void writeFile(String path, String content, boolean append) {
		try {
			boolean addStr = append; // 通过这个对象来判断是否向文本文件中追加内容
			String filepath = path; // 得到文本文件的路径
			String filecontent = content; // 需要写入的内容
			File writefile = new File(filepath);
			if (writefile.exists() == false) // 如果文本文件不存在则创建它
			{
				writefile.createNewFile();
				writefile = new File(filepath); // 重新实例化
			}
			FileWriter filewriter = new FileWriter(writefile, addStr);
			// 删除原有文件的内容
			RandomAccessFile file = new RandomAccessFile(path,
					"rw");
			file.setLength(0);
			// 写入新的文件内容
			filewriter.write(filecontent);
			filewriter.flush();
			filewriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写文件
	 * 
	 * @param filePath
	 * @param fileContent
	 */
	public static void writeFile(String filePath, byte[] fileContent) {
		if (StringHelper.isNullOrEmpty(filePath) || fileContent == null
				|| fileContent.length == 0)
			return;

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(filePath));
			out.write(fileContent);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写文本文件
	 * 
	 * @param filepath
	 * @param content
	 * @param charset
	 */
	public static void writeTextFile(String filepath, String content,
									 String charset, boolean includedHeadCode) {
		FileOutputStream fw = null;
		if (StringHelper.isNullOrEmpty(charset))
			charset = "UTF-8";

		try {
			fw = new FileOutputStream(filepath);
			if (includedHeadCode) {
				byte[] bom = null;
				if (charset.toLowerCase().equals("utf-8")) {
					bom = new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
				} else if (charset.toLowerCase().equals("utf-16")) {
					bom = new byte[] { (byte) 0xEF, (byte) 0xFF };
				} else if (charset.toLowerCase().equals("utf-32")) {
					bom = new byte[] { (byte) 0xFF, (byte) 0xFE, (byte) 0x00,
							(byte) 0x00 };
				}
				if (bom != null) {
					fw.write(bom);
					fw.flush();
				}
			}
			fw.write(StringHelper.stringToBytes(content, charset));
			fw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fw != null)
					fw.close();
			} catch (Exception ex) {
			}
		}
		/*
		 * BufferedWriter bw = null; try { if
		 * (StringHelper.IsNullOrEmpty(charset)) charset = "UTF-8"; bw = new
		 * BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath),
		 * charset)); bw.write(content); bw.flush(); } catch (Exception e) {
		 * e.printStackTrace(); } finally { try { if (bw != null) bw.close(); }
		 * catch (Exception ex) { } }
		 */
	}

	/**
	 * 写文本文件
	 * 
	 * @param filepath
	 * @param content
	 */
	public static void writeTextFile(String filepath, String content) {
		writeTextFile(filepath, content, "UTF-8", true);
	}

	/**
	 * 写Json文件
	 * 
	 * @param filepath
	 * @param content
	 */
	public static void writeJsonFile(String filepath, String content) {
		writeTextFile(filepath, content, "UTF-8", false);
	}

	/**
	 * 合并文件路径
	 * 
	 * @param path1
	 * @param path2
	 * @return
	 */
	public static String combinePath(String path1, String path2) {
		return combinePath(path1, path2, false);
	}

	/**
	 * 合并文件路径
	 * 
	 * @param path1
	 * @param path2
	 * @param isWebPath
	 * @return
	 */
	public static String combinePath(String path1, String path2,
									 boolean isWebPath) {
		if (path1 == null) {
			path1 = "";
		}

		if (path2 == null) {
			path2 = "";
		}

		String placeholder = "/";
		String replacement = File.separatorChar + "";
		if (isWebPath) {
			placeholder = File.separatorChar + "";
			replacement = "/";
		}

		while (path1.endsWith(placeholder) || path1.endsWith(replacement)) {
			path1 = path1.substring(0, path1.length() - 1);
		}

		while (path2.startsWith(placeholder) || path2.startsWith(replacement)) {
			path2 = path2.substring(1);
		}

		String newPath = StringHelper.format("{0}{1}{2}", path1, replacement, path2);
		/*while (newPath.indexOf(placeholder) >= 0) {
			newPath = newPath.replace(placeholder, replacement);
		}*/

		return newPath;
	}

	/**
	 * 复制文件
	 * 
	 * @param srcFilename
	 *            源文件
	 * @param desFilename
	 *            目标文件
	 * @return
	 */
	static public boolean copyFile(String srcFilename, String desFilename) {
		if (StringHelper.isNullOrEmpty(srcFilename)
				|| StringHelper.isNullOrEmpty(desFilename)) {
			return false;
		}

		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		boolean result = true;
		try {
			File srcFile = new File(srcFilename);
			if (!srcFile.exists()) {
				return false;
			}
			File desFile = new File(desFilename);
			if (desFile.exists()) {
				desFile.setWritable(true);
				desFile.delete();
			}

			inBuff = new BufferedInputStream(new FileInputStream(srcFile));
			outBuff = new BufferedOutputStream(new FileOutputStream(desFile));

			byte[] bytes = new byte[1024];
			int length = inBuff.read(bytes);
			while (length > 0) {
				outBuff.write(bytes, 0, length);
				length = inBuff.read(bytes);
			}

			outBuff.flush();

		} catch (Exception ex) {
			result = false;
		} finally {
			if (inBuff != null) {
				try {
					inBuff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (outBuff != null) {
				try {
					outBuff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	/**
	 * 
	 * 文件夹拷贝(文件内含有文件和文件夹)
	 * 
	 */
	public static void copyDirecory(String src, String des) {
		if (StringHelper.isNullOrEmpty(src) || StringHelper.isNullOrEmpty(des))
			return;
		File desfile = new File(des);
		if (!desfile.exists()) {
			desfile.mkdirs();
		}
		File srcfile = new File(src);
		File[] srcfiles = srcfile.listFiles();
		for (File file : srcfiles) {
			if (file.isFile()) {
				transferFile(file.getPath(),
						des + File.separator + file.getName());
			} else if (file.isDirectory()) {
				copyDirecory(file.getPath(),
						des + File.separator + file.getName());
			}
		}
	}

	/**
	 * 删除文件夹(文件内含有文件和文件夹)
	 * @param dirpath
	 * @return
	 */
	public static boolean deleteDir(String dirpath) {
		return deleteDir(dirpath,null);
	}
	
	/**
	 * 删除文件夹(文件内含有文件和文件夹)
	 * @param dirpath
	 * @param exceptFileList
	 * @return
	 */
	public static boolean deleteDir(String dirpath, List<String> exceptFileList) {
		if (StringHelper.isNullOrEmpty(dirpath))
			return true;
		
		File file = new File(dirpath);
		if (file.isDirectory()) {
			String[] children = file.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(file, children[i]).getPath(), exceptFileList);
				if (!success)
					return false;
			}
		}
		try {
			if(exceptFileList != null && exceptFileList.contains(dirpath))
				return true;
			
			return file.delete();
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * public static void ClearDir(String dirpath) { File file = new
	 * File(dirpath); if (file.exists()) { if (file.isDirectory()) { File[]
	 * files = file.listFiles(); for (File subfile : files) { if
	 * (subfile.isDirectory()) ClearDir(subfile.getPath()); else
	 * subfile.delete(); } } file.delete(); } }
	 */

	private static FilenameFilter getByExtFilter(String ext) {
		final String _extension = ext;
		return new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(_extension);
			}
		};
	}

	private static FilenameFilter getByRegexFilter(String regex) {
		final String _regex = regex;
		return new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.matches(_regex);
			}
		};
	}

	private static FilenameFilter getByExtStrFilter(String extstr) {
		final String[] extArr = extstr.split(";");
		return new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				boolean result = false;
				for (int i = 0; i < extArr.length; i++) {
					if (name.endsWith(extArr[i]))
						result = true;
				}
				return result;
			}
		};
	}

	/**
	 * 根据文件后缀获取文件
	 * 
	 * @param dirpath
	 * @param ext
	 * @return
	 */
	public static File[] getFilesByExt(String dirpath, String ext) {
		File file = new File(dirpath);
		File[] files = file.listFiles(getByExtFilter(ext));
		return files;
	}

	/**
	 * 根据正则表达式获取文件
	 * 
	 * @param dirpath
	 * @param regex
	 * @return
	 */
	public static File[] getFilesByRegex(String dirpath, String regex) {
		File file = new File(dirpath);
		File[] files = file.listFiles(getByRegexFilter(regex));
		return files;
	}

	/**
	 * 根据文件后缀字符串获取文件
	 * 
	 * @param dirpath
	 * @param extstr
	 * @return
	 */
	public static File[] getFilesByExtStr(String dirpath, String extstr) {
		File file = new File(dirpath);
		File[] files = file.listFiles(getByExtStrFilter(extstr));
		return files;
	}

	public static String createRandomSubPath(String rootpath) {
		String newPath = combinePath(rootpath, UUID.randomUUID().toString(),
				true);
		new File(newPath).mkdirs();
		return newPath;
	}

	/**
	 * 创建文件夹
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean createDirectory(String dir) {
		if (StringHelper.isNullOrEmpty(dir))
			return false;
		if (new File(dir).exists())
			return true;
		return new File(dir).mkdirs();
	}

	public static ArrayList<File> getSubDirectoryList(String dirpath) {
		File file = new File(dirpath);
		File[] files = file.listFiles();
		ArrayList<File> result = new ArrayList<File>();
		for (File obj : files) {
			if (obj.isDirectory()) {
				result.add(obj);
				// obj.getAbsolutePath();
			}
		}
		return result;
	}

	/**
	 * 移除路径首字符为/或\
	 * 
	 * @param path
	 * @return
	 */
	public static String truncateFirstRootDirectoryChar(String path) {
		if (StringHelper.isNullOrEmpty(path))
			return StringHelper.empty();

		String result = path;
		while (result.startsWith("/") || result.startsWith("\\")) {
			result = result.substring(1);
		}
		return result;
	}

	/**
	 * 递归查找文件
	 * 
	 * @param baseDirName
	 *            查找的文件夹路径
	 * @param targetFileName
	 *            需要查找的文件名
	 * @param fileList
	 *            查找到的文件集合
	 */
	public static void getFiles(String baseDirName, String targetFileName,
								List<File> fileList) {

		File baseDir = new File(baseDirName); // 创建一个File对象
		if (!baseDir.exists() || !baseDir.isDirectory()) { // 判断目录是否存在
			System.out.println("文件查找失败：" + baseDirName + "不是一个目录！");
			return;
		}
		String tempName = null;
		// 判断目录是否存在
		File tempFile;
		File[] files = baseDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			tempFile = files[i];
			if (tempFile.isDirectory()) {
				getFiles(tempFile.getAbsolutePath(), targetFileName, fileList);
			} else if (tempFile.isFile()) {
				tempName = tempFile.getName();
				if (wildcardMatch(targetFileName, tempName)) {
					// 匹配成功，将文件名添加到结果集
					fileList.add(tempFile.getAbsoluteFile());
				}
			}
		}
	}

	/**
	 * 通配符匹配
	 * 
	 * @param pattern
	 *            通配符模式
	 * @param str
	 *            待匹配的字符串
	 * @return 匹配成功则返回true，否则返回false
	 */
	private static boolean wildcardMatch(String pattern, String str) {
		int patternLength = pattern.length();
		int strLength = str.length();
		int strIndex = 0;
		char ch;
		for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
			ch = pattern.charAt(patternIndex);
			if (ch == '*') {
				// 通配符星号*表示可以匹配任意多个字符
				while (strIndex < strLength) {
					if (wildcardMatch(pattern.substring(patternIndex + 1),
							str.substring(strIndex))) {
						return true;
					}
					strIndex++;
				}
			} else if (ch == '?') {
				// 通配符问号?表示匹配任意一个字符
				strIndex++;
				if (strIndex > strLength) {
					// 表示str中已经没有字符匹配?了。
					return false;
				}
			} else {
				if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
					return false;
				}
				strIndex++;
			}
		}
		return (strIndex == strLength);
	}

	/**
	 * 判断文件夹是否存在
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean directoryExists(String dir) {
		File file = new File(dir);
		return file != null && file.exists() && file.isDirectory();
	}

	/**
	 * 获取文件
	 * 
	 * @param dir
	 * @param includeSub
	 * @return
	 */
	public static ArrayList<File> getFiles(String dir, boolean includeSub) {
		ArrayList<File> result = new ArrayList<File>();
		if (!directoryExists(dir))
			return result;
		File[] files = new File(dir).listFiles();
		for (File file : files) {
			if (file.isDirectory() && includeSub) {
				ArrayList<File> temp = getFiles(file.getAbsolutePath(), includeSub);
				if (!temp.isEmpty())
					result.addAll(temp);
			} else if (file.isFile()) {
				result.add(file);
			}
		}
		return result;
	}

	/**
	 * 删除文件
	 * 
	 * @param filepath
	 * @return
	 */
	public static boolean deleteFile(String filepath) {
		if (!exists(filepath))
			return true;
		try {
			File file = new File(filepath);
			file.setWritable(true);
			return file.delete();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 根据文件路径获取文件所在的目录
	 * 
	 * @param filepath
	 * @return
	 */
	public static String getFileDir(String filepath) {
		if (!StringHelper.isNullOrEmpty(filepath)) {
			int dot = filepath.lastIndexOf('\\');
			if ((dot > -1) && (dot < (filepath.length()))) {
				return filepath.substring(0, dot);
			}
		}
		return filepath;
	}

	/**
	 * 拷贝文件
	 * 
	 * @param source
	 * @param target
	 */
	public static void transferFile(String source, String target) {
		FileChannel in = null;
		FileChannel out = null;
		FileInputStream inStream = null;
		FileOutputStream outStream = null;
		try {
			inStream = new FileInputStream(source);
			outStream = new FileOutputStream(target);
			in = inStream.getChannel();
			out = outStream.getChannel();
			in.transferTo(0, in.size(), out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				inStream.close();
				outStream.close();
			} catch (Exception e2) {
			}
		}
	}

	/**
	 * 判断文件夹是否为空
	 * @param dir
	 * @return
	 */
	public static Boolean directoryIsEmpty(String dir) {
		if (StringHelper.isNullOrEmpty(dir))
			return true;
		File file = new File(dir);
		return !(file.isDirectory() && file.listFiles().length > 0);
	}
	
}