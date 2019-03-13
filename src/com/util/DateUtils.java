/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/senyint/ma">ma</a> All rights reserved.
 */
package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author senyint
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 * 根据年 月 获取对应的月份 天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysByYearMonth(int year, int month) {
		if (year < 1900 || year > 3000 || month < 1 || month > 12) {
			return -1;
		}
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
	
	/**
	 * 根据日期 找到对应日期的 星期 
	 * @param date
	 * @return
	 */
	public static String getDayOfWeekByDate(String date) {
		String dayOfweek = "-1";
		try {
			SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate = myFormatter.parse(date);
			SimpleDateFormat formatter = new SimpleDateFormat("E");
			String str = formatter.format(myDate);
			dayOfweek = str;
		} catch (Exception e) {
		}
		return dayOfweek;
	}

	public static int getDayIndexOfWeekByDate(String date) {
		int iDay = -1;
		try {
			SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
			Date myDate = myFormatter.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(myDate);
			iDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		} catch (Exception e) {
		}
		return iDay;
	}
	
	public static int getDayIndexOfWeekByDate(Date myDate) {
		int iDay = -1;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(myDate);
			iDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		} catch (Exception e) {
		}
		return iDay;
	}
	
	/**
	 * 输入年月份，和具体的星期几，获得具体的日期
	 * @param year
	 * @param month
	 * @param iWeekArray 1, 2, 3, 4, 5, 6, 0
	 * @return
	 * @eg. 2016年09月，周1周2的日期：5,6,12,13,19,20,26,27,
	 */
	public static List<Integer> getDaysByYearMonthWeek(int year, int month, int... iWeekArray) {
		List<Integer> lsRet = new ArrayList<Integer>();
		int iMaxDays = getDaysByYearMonth(year, month);
		if (iWeekArray.length > 0 && iMaxDays > 0) {
			Map<Integer, Integer> weekMap = new HashMap<Integer, Integer>();
			for (int item : iWeekArray) {
				if (item > -1 && item < 7) {
					weekMap.put(item, item);
				}
			}
			if (weekMap.size() > 0) {
				for (int i = 1; i < iMaxDays + 1; i++) {
					int iWeek = getDayIndexOfWeekByDate(year + "-" + month + "-" + i);
					if (weekMap.containsKey(iWeek)) {
						lsRet.add(i);
					}
				}
			}
		}
		return lsRet;
	}
	
	
	/**
	 * 获取当月的 天数
	 * @return
	 */
	public static int getCurrentMonthDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);
		a.roll(Calendar.DATE, -1);
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
    
	 /**
	   * 切割时间段
	   *
	   * @param dateType 分割类型 M/D/H/N/m -->每月/每天/每小时/每秒/每分钟
	   * @param start yyyy-MM-dd HH:mm:ss
	   * @param end  yyyy-MM-dd HH:mm:ss
	   * @param splitFor 分割数
	   * @return list 分割后时间列表
	   * 
	   * @Example 
	   * 		//从2016-09-27 11:00:00到2016-09-27 12:00:00按照10分钟分割
	   * 		DateUtils.cutDate("m", "2016-09-27 11:00:00", "2016-09-27 12:00:00", 10)
	   */
	  public static List<String> cutDate(String dateType, String start, String end, int splitFor) {
	    try {
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      Date dBegin = sdf.parse(start);
	      Date dEnd = sdf.parse(end);
	      return findDates(dateType.charAt(0), dBegin, dEnd, splitFor);
	    } catch (Exception e) {
	      e.getStackTrace();
	    }
	    return null;
	  }
	  public static List<String> findDates(char dateType, Date dBegin, Date dEnd, int splitFor) throws Exception {
	    List<String> listDate = new ArrayList<String>();
	    listDate.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dBegin));
	    Calendar calBegin = Calendar.getInstance();
	    calBegin.setTime(dBegin);
	    Calendar calEnd = Calendar.getInstance();
	    calEnd.setTime(dEnd);
	    while (calEnd.after(calBegin)) {
	      switch (dateType) {
	        case 'M':
	          calBegin.add(Calendar.MONTH, splitFor);
	          break;
	        case 'D':
	          calBegin.add(Calendar.DAY_OF_YEAR, splitFor);break;
	        case 'H':
	          calBegin.add(Calendar.HOUR, splitFor);break;
	        case 'N':
	          calBegin.add(Calendar.SECOND, splitFor);break;
	        case 'm':
		          calBegin.add(Calendar.MINUTE, splitFor);break;
	      }
	      if (calEnd.after(calBegin))
	        listDate.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calBegin.getTime()));
	      else
	        listDate.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calEnd.getTime()));
	    }
	    return listDate;
	  }
	
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));
//        int currentMaxDays = getCurrentMonthDay();  
//        
//        int maxDaysByDate = getDaysByYearMonth(2012, 11); 
//        List<Integer> lsDate =getDaysByYearMonthWeek(2016, 9, 1, 2);
//          
//        String week = getDayOfWeekByDate("2016-10-02");
//        int iWeek = getDayIndexOfWeekByDate("2016-10-02");
//        
//          
//        System.out.println("本月天数：" + currentMaxDays);  
//        System.out.println("2012年11月天数：" + maxDaysByDate); 
//        System.out.println("2016-10-02是：" + iWeek);  
//        System.out.println("2016-10-02是：" + week);
//        
//		System.out.print("2016年09月，周1周2的日期：");
//		for (Integer item : lsDate) {
//			System.out.print(item + ",");
//		}
//		System.out.println();
		String start = "2016-09-27 00:00:00";
	    String end = "2016-09-27 02:00:00";
	    List<String> list = cutDate("m", start, end, 20);
	    for (String str :list){
	      System.out.println(str);
	    }
	}
}
