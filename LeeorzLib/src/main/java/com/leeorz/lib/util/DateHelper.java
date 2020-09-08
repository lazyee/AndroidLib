package com.leeorz.lib.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

	static public final String DateFormat = "yyyy-MM-dd";
	static public final String DateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	static public final String DateTimeFormatNoSecond = "yyyy-MM-dd HH:mm";
	
	static public String date2String(Timestamp time, String format) {
		if (time == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(time);
	}

	static public String date2String(Date date, String format) {
		if(date == null)
		{
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 格式：yyyy-MM-dd
	 * 
	 * @param time
	 * @return
	 */
	static public String date2SystemFormatString(Timestamp time) {
		return date2String(time, "yyyy-MM-dd");
	}
	
	/**
	 * 格式：yyyy-MM-dd HH:mm:ss
	 * @param timestamp
	 * @return
	 */
	static public String toDateTimeString(Timestamp timestamp) {
		return date2String(timestamp, DateTimeFormat);
	}

	static public Timestamp string2Timsestamp(String strdate) {
		return string2Date(strdate, DateTimeFormat);
	}

	static public Timestamp string2TimsestampNoSecond(String strdate) {
		return string2Date(strdate, DateTimeFormatNoSecond);
	}
	
	static public Timestamp date2Timsestamp(Date date) {
		try { 
			return new Timestamp(date.getTime());
		} catch (Exception e) {
			return new Timestamp(new Date().getTime());
		}
	}

	static public java.sql.Date date2SqlDate(Date date) {
		try { 
			return new java.sql.Date(date.getTime());
		} catch (Exception e) {
			return new java.sql.Date(new Date().getTime());
		}
	}
	
	static public Timestamp date2SqlTimestamp(Date date) {
		try { 
			return new Timestamp(date.getTime());
		} catch (Exception e) {
			return new Timestamp(new Date().getTime());
		}
	}
	
	/**
	 * @param dateString
	 * @param formatString
	 * @return
	 */
	static public Timestamp string2Date(String dateString, String formatString) {
		try {
			dateString = dateString.replace("T", " ");
			if (dateString.indexOf("+") > 0) {
				dateString = dateString.substring(0, dateString.indexOf("+"));
			}
			
			SimpleDateFormat format = new SimpleDateFormat(formatString);
			Date date = format.parse(dateString);

			return new Timestamp(date.getTime());
		} catch (Exception e) {
			return new Timestamp(new Date().getTime());
		}
	}
	
	public static Date string2Date(String str) {
		java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
		date = df.parse(str);
		} catch (ParseException e) {
		e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 返回数据库日期
	 * @param dateStr
	 * @return 返回日期类型 yyyy-MM-dd
	 */
	static public java.sql.Date string2ShortDate(String dateStr) {
		try { 
			SimpleDateFormat format = new SimpleDateFormat(DateFormat);
			Date date = format.parse(dateStr);
			return new java.sql.Date(date.getTime());
		} catch (Exception e) {
			return new java.sql.Date(new Date().getTime());
		}
	}
	
	/**
	 * 获取当前数据库日期
	 * @return
	 */
	static public java.sql.Date getNowSqlDate() {
		try { 
			return new java.sql.Date(getNowDate().getTime());
		} catch (Exception e) {
			return new java.sql.Date(new Date().getTime());
		}
	}

	/**
	 * 获取当前数据库日期（含时间）
	 * @return
	 */
	static public Timestamp getNowSqlDateTime() {
		try { 
			return new Timestamp(new Date().getTime());
		} catch (Exception e) {
			return new Timestamp(new Date().getTime());
		}
	}
	
	/**
	   * 获取现在时间
	   * 
	   * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	   */
	public static Date getNowDate() {
	   Date currentTime = new Date();
	   return currentTime;
	   /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(currentTime);
	   ParsePosition pos = new ParsePosition(8);
	   Date currentTime_2 = formatter.parse(dateString, pos);
	   return currentTime_2;*/
	}	
		
	/**
	 * 返回当前日期的字符串
	 * @return 格式yyyy-MM-dd
	 */
	static public String getNowDateString() {
		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat(DateFormat);
		return df.format(now);
	}
	
	/**
	 * 返回当前日期的字符串
	 * @return 格式yyyy-MM-dd HH:mm:ss
	 */
	static public String getNowDateTimeString() {
		Date now = new Date();
		SimpleDateFormat df = new SimpleDateFormat(DateTimeFormat);
		return df.format(now);
	}
	
	/**
	 * 返回两时间的差，单位：分钟
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static float getDateDiff(Date beginDate, Date endDate) {
		long time1 = beginDate.getTime();
		long time2 = endDate.getTime();
		long diff = Math.abs(time2 - time1);
		return (float) (Math.round((diff / 1000 / 60f) * 100)) / 100;
	}
}
