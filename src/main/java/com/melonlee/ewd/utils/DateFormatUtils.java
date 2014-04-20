package com.melonlee.ewd.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFormatUtils {

	static SimpleDateFormat dateFomaFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static String getFormat(Date date) {

		return dateFomaFormat.format(date);

	}

	public static String getNow() {

		return dateFomaFormat.format(new Date());

	}

	public static String subDate(String date) {

		return date.substring(0, 10);

	}

	public static void main(String[] args) {
		
		System.out.println(getDayBefore().size());
	}

	public static String subNow() {
		return getNow().substring(0, 10);
	}

	public static String getTimeStamp(String string) {

		return string.substring(11, 19);
	}

	@SuppressWarnings("static-access")
	public static ArrayList<String> getDayBefore() {
		ArrayList<String> datas = new ArrayList<String>();
		String putDate = null;
		Calendar calendar = new GregorianCalendar();
		try {
			for (int temp = StaticParam.DAY_BEFORE; temp > 0; temp--) {
				calendar.setTime(dateFomaFormat.parse(getNow()));
				calendar.add(calendar.DATE, -temp);// 把日期往后增加一天.整数往后推,负数往前移动
				putDate = dateFomaFormat.format(calendar.getTime()); // 增加一天后的日期
				if (null != putDate) {
					datas.add(subDate(putDate));
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return datas;
	}
}
