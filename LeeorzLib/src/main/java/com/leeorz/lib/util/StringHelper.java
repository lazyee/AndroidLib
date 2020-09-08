package com.leeorz.lib.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

public class StringHelper {

	/**
	 * 将字符串中的不合法空格转换为合法的空格
	 * 
	 * @param srcString
	 * @return
	 */
	static public String replaceInvaliedBlankspace(String srcString) {
		if (srcString == null) {
			return srcString;
		}

		char[] items = srcString.toCharArray();
		for (int i = 0; i < items.length; i++) {
			if ((int) items[i] == 32 || (int) items[i] == 160) {
				items[i] = ' ';
			}
		}

		return new String(items);
	}

	/**
	 * @param content
	 * @return
	 */
	static public long parseToLong(Object content) {
		if (content == null)
			return -1;

		try {
			return Long.parseLong(content.toString());
		} catch (Exception e) {
		}

		return -1;
	}

	/**
	 * @param content
	 * @return
	 */
	static public int parseToInt(Object content) {
		if (content == null)
			return -1;

		try {
			return Integer.parseInt(content.toString());
		} catch (Exception e) {
		}

		return -1;
	}

	static public double parseToDouble(Object val) {
		if (val == null)
			return 0;

		try {
			return Double.parseDouble(val.toString());
		} catch (Exception e) {
		}

		return 0;
	}

	static public float parseToFloat(Object val) {
		if (val == null)
			return 0;

		try {
			return Float.parseFloat(val.toString());
		} catch (Exception e) {
		}

		return 0;
	}

	static public BigDecimal parseToBigDecimal(Object val) {
		if (val == null)
			return new BigDecimal(0);

		try {
			return new BigDecimal(val.toString());
		} catch (Exception e) {
		}

		return new BigDecimal(0);
	}

	static public boolean parseToBool(Object content, boolean defalutVal) {
		if (content == null)
			return defalutVal;

		try {
			return Boolean.parseBoolean(content.toString());
		} catch (Exception e) {
		}

		return defalutVal;
	}

	static private String emptyString = "";

	/**
	 * @return
	 */
	static public String empty() {
		return StringHelper.emptyString;
	}

	/**
	 * @return
	 */
	static public String createGuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * @param content
	 * @param encoding
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	static public byte[] stringToBytes(String content, String encoding) throws UnsupportedEncodingException {
		if (StringHelper.isNullOrEmpty(content))
			return null;

		if (StringHelper.isNullOrEmpty(encoding))
			encoding = "UTF-8";

		return content.getBytes(encoding);
	}

	/**
	 * @param bytes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	static public String bytesToString(byte[] bytes, String encoding) throws UnsupportedEncodingException {
		if (bytes == null || bytes.length == 0)
			return null;

		if (StringHelper.isNullOrEmpty(encoding))
			encoding = "UTF-8";

		return new String(bytes, encoding);
	}

	/**
	 * @param enumClass
	 * @param val
	 * @return
	 */
	static public Object enumTryParse(Class enumClass, Object val) {
		if (val == null)
			return null;

		Enum tmp = null;
		EnumSet set = EnumSet.allOf(enumClass);
		for (Object obj : set) {
			tmp = (Enum) obj;
			if (tmp.name().equalsIgnoreCase(val.toString()) || ((Integer) tmp.ordinal()).toString().equals(val.toString()))
				return tmp;
		}

		return null;
	}

	/**
	 * @param str
	 * @return
	 */
	static public boolean isNullOrEmpty(String str) {
		return (str == null || str.isEmpty());
	}

	static public boolean isNullOrTrimEmpty(String str) {
		return (str == null || str.trim().isEmpty());
	}

	/**
	 * @param str
	 * @param spliters
	 * @param removeEmptys
	 * @return
	 */
	static public String[] split(String str, String[] spliters, boolean removeEmptys) {

		return StringHelper.split(str, spliters, 0, removeEmptys);
	}

	/**
	 * @param str
	 * @param spliters
	 * @param limit
	 * @param removeEmptys
	 * @return
	 */
	static public String[] split(String str, String[] spliters, int limit, boolean removeEmptys) {
		if (StringHelper.isNullOrEmpty(str) || spliters == null || spliters.length == 0)
			return null;

		String regex = "";
		for (String spliter : spliters) {
			regex += "\\" + spliter + "|";
		}

		if (regex.endsWith("|"))
			regex = regex.substring(0, regex.lastIndexOf("|"));

		String[] result = str.split(regex, limit);
		if (result == null || result.length == 0)
			return null;

		if (removeEmptys) {
			LinkedList<String> finalList = new LinkedList<String>();
			for (String r : result) {
				if (StringHelper.isNullOrEmpty(r))
					continue;

				finalList.add(r);
			}

			if (finalList.size() == 0)
				return null;

			String[] array = new String[finalList.size()];
			return finalList.toArray(array);
		}

		return result;
	}

	/**
	 * 简单实现C#中string类型的format方法
	 * 
	 * @param pattern
	 * @param objects
	 * @return
	 */
	static public String format(String pattern, Object... objects) {
		// // 避免非字符串类型的参数对表达式差生的副作用，在格式化之前将所有参数都转换为字符串类型
		// LinkedList<String> list = new LinkedList<String>();
		// for (Object object : objects) {
		// list.add(object.toString());
		// }
		//
		// return MessageFormat.format(pattern, list.toArray());

		String result = pattern;
		String placeholder = null;
		String replacement = null;
		for (int index = 0; index < objects.length; index++) {
			placeholder = "{" + index + "}";
			if (objects[index] == null)
				replacement = StringHelper.empty();
			else
				replacement = objects[index].toString();

			// result = org.hibernate.util.StringHelper.replace(result,
			// placeholder, replacement);
			result = result.replace(placeholder, replacement);
		}

		return result;
	}

	/**
	 * 获取换行符
	 * 
	 * @return
	 */
	static public String newLine() {
		return StringHelper.getSystemProperty("line_separator");
	}

	/**
	 * 获取系统常量
	 * 
	 * @param val
	 * @return
	 */
	static public String getSystemProperty(String val) {
		return System.getProperty(val.toString());
	}

	static public String reinstateInvalidChar(String text) {
		if (isNullOrEmpty(text))
			return "";
		return text.replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\"").replace("&apos;", "\'").replace("&amp;", "&");
	}

	static public String replaceInvalidChar(String text) {
		if (isNullOrEmpty(text))
			return "";

		String result = text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("\'", "&apos;");
		return result;
	}

	static public String removeLastChar(String str) {
		if (isNullOrEmpty(str))
			return null;
		return str.substring(0, str.length() - 1);
	}

	static public String encodeBase64(byte[] buf) {
		if (buf == null)
			return null;

		// encode的时候末尾会出现额外字符的情况，原因大概是java本身是127位而不是128位的缘故
		//return removeLastChar(new BASE64Encoder().encode(buf));
		return  Base64.encodeToString(buf, Base64.DEFAULT);
	}

	static public byte[] decodeBase64(String str) {
		try {
			//return new BASE64Decoder().decodeBuffer(str);
			return Base64.decode(str, Base64.DEFAULT);
		} catch (Exception e) {
		}
		return null;
	}

	public static String getStringFromBase64(String str) {
		if (isNullOrEmpty(str))
			return null;
		//BASE64Decoder decoder = new BASE64Decoder();
		try {
			//byte[] b = decoder.decodeBuffer(str);
			byte[] b = decodeBase64(str);
			return new String(b, "UTF-8");
		} catch (Exception e) {
		}
		return null;
	}

	public static String getBase64String(String str) {
		if (isNullOrEmpty(str))
			return null;

		try {
			//return new BASE64Encoder().encode(str.getBytes("UTF-8"));
			return Base64.encodeToString(str.getBytes("UTF-8"), Base64.DEFAULT);
		} catch (Exception e) {
		}
		return null;
	}

	public static String htmlEncode(String str) {
		return htmlEncode(str, "UTF-8");
	}

	public static String htmlEncode(String str, String code) {
		try {
			return java.net.URLEncoder.encode(str, code);
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	public static String htmlDecode(String str) {
		try {
			return new String(str.getBytes("UTF-8"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}

	public static String truncateString(String origString, Integer maxLength) {
		if (StringHelper.isNullOrEmpty(origString))
			return "";

		if (origString.length() <= maxLength)
			return origString;

		return origString.substring(0, maxLength);
	}

	public static String truncateString2(String origString) {
		if (StringHelper.isNullOrEmpty(origString))
			return "";

		if (origString.length() <= 30)
			return origString;

		return origString.substring(0, 30) + "...";
	}

	public static String truncateString2(String origString, Integer maxLength) {
		if (StringHelper.isNullOrEmpty(origString))
			return "";

		if (origString.length() <= maxLength)
			return origString;

		return origString.substring(0, maxLength) + "...";
	}

	public static String replaceSpecialChar(String queryStr) {
		return queryStr.replace("/", "//").replace("%", "/%").replace("_", "/_");
	}

	/**
	 * 将array中的内容以delimiter为间隔拼接字符串
	 * 
	 * @param array
	 * @param delimiter
	 * @return
	 */
	public static String join(Object[] array, String delimiter) {
		if (array == null) {
			throw new IllegalArgumentException();
		}

		if (array.length == 0) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		for (Object item : array) {
			builder.append(item.toString() + delimiter);
		}
		builder.delete(builder.length() - delimiter.length(), builder.length());
		return builder.toString();
	}

	/**
	 * 将list中的内容以delimiter为间隔拼接字符串
	 * 
	 * @param list
	 * @param delimiter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String join(List list, String delimiter) {
		if (list == null) {
			return "";
		}

		return join(list.toArray(), delimiter);
	}

	/**
	 * 过滤特殊字符
	 * 
	 * @param str
	 * @return
	 */
	public static String stringFilter(String str) {
		// str = str.replaceAll("[\\?\\\\/'&:|< >\\*\"]", "");
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符，保留"- ."
		String regEx = "[`~!@#$%^&*()+=|{}':_\";',\\[\\]<>\\\\/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/*
	 * 判断是否为数字类型
	 */
	public static boolean isNumeric(Object obj) {
		if (obj == null)
			return false;
		String str = obj.toString();
		return str.matches("\\d *");
	}

	/**
	 * 替换文件名的特殊字符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceInvalidChar4FileName(String str) {
		if (StringHelper.isNullOrEmpty(str))
			return "";
		String reg = "[\\\\/:#*()`'?\"<>|]";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

}