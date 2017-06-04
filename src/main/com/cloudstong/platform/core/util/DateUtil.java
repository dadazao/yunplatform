package com.cloudstong.platform.core.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import com.cloudstong.platform.core.common.Constants;

/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a> to correct time
 *         pattern. Minutes should be mm not MM (MM is month).
 */
public class DateUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);
	private static final String TIME_PATTERN = "HH:mm";
	public static String DATEFORMAT_DAY = "yyyy-MM-dd";
	public static String STRING_DATEFORMAT_DAY = "yyyyMMdd";
	public static String DATEFORMAT_MINUTE = "yyyy-MM-dd HH:mm";
	public static String DATEFORMAT_SECOND = "yyyyMMddHHmmss";
	public static String FOC_DATEFORMAT_MINUTE = "yyyy-MM-dd HH.mm";
	public static String HOUR_MIN = "HHmm";
	public static String FOC_TEMP_DATA_DATE_FORMAT = "yyyy-MM-dd HH.mm.ss";
	public static String PSRML_DATE_FORMATE = "ddMMMyy";
	public static String LEG_TIMESTAMP_FORMATE = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Checkstyle rule: utility classes should not have public constructor
	 */
	private DateUtil() {
	}

	/**
	 * Return default datePattern (MM/dd/yyyy)
	 * 
	 * @return a string representing the date pattern on the UI
	 */
	public static String getDatePattern() {
		Locale locale = LocaleContextHolder.getLocale();
		String defaultDatePattern;
		try {
			defaultDatePattern = ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale).getString("date.format");
		} catch (MissingResourceException mse) {
			defaultDatePattern = "yyyy-MM-dd";
		}

		return defaultDatePattern;
	}

	public static String getDateTimePattern() {
		return DateUtil.getDatePattern() + " HH:mm:ss.S";
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static String getDate(Date aDate) {
		SimpleDateFormat df;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @see java.text.SimpleDateFormat
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df;
		Date date;
		df = new SimpleDateFormat(aMask);

		if (log.isDebugEnabled()) {
			log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			// log.error("ParseException: " + pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(TIME_PATTERN, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * 
	 * @see java.text.SimpleDateFormat
	 */
	public static String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the System Property 'dateFormat' in the format you specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Date convertStringToDate(String strDate) throws ParseException {
		Date aDate = null;

		try {
			if (log.isDebugEnabled()) {
				log.debug("converting date with pattern: " + getDatePattern());
			}

			aDate = convertStringToDate(getDatePattern(), strDate);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate + "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return aDate;
	}

	public static int getSchedule() {
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
		return day == 0 ? 7 : day;

	}

	public static int getSchedule(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return day == 0 ? 7 : day;

	}

	public static int getDayOfWeek(int SUN_FST_DAY_OF_WEEK) {
		if (SUN_FST_DAY_OF_WEEK > 7 || SUN_FST_DAY_OF_WEEK < 1)
			return 0;
		if (SUN_FST_DAY_OF_WEEK == 1)
			return 7;
		return SUN_FST_DAY_OF_WEEK - 1;
	}

	public static String toEtermDate(Date date) {

		String format = "ddMMMyy";

		DateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);

		String str = df.format(date);

		return str;
	}

	public static String getStringNow() {
		try {
			Date date = new Date();
			return new SimpleDateFormat(STRING_DATEFORMAT_DAY).format(date);
		} catch (Exception e) {
			return "";
		}
	}

	public static String getNow() {
		try {
			Date date = new Date();
			return new SimpleDateFormat(DATEFORMAT_DAY).format(date);
		} catch (Exception e) {
			return "";
		}
	}

	public static String getMorrow() {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, 1);
			return new SimpleDateFormat(DATEFORMAT_DAY).format(calendar.getTime());
		} catch (Exception e) {
			return "";
		}
	}

	public static String getBeforeYesterDay() {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -2);
			return new SimpleDateFormat(DATEFORMAT_DAY).format(calendar.getTime());
		} catch (Exception e) {
			return "";
		}
	}

	public static String getNowTime() {
		try {
			Date date = new Date();
			return new SimpleDateFormat(DATEFORMAT_MINUTE).format(date);
		} catch (Exception e) {
			return "";
		}

	}

	public static String getNowTimeSecond() {
		try {
			Date date = new Date();
			return new SimpleDateFormat(DATEFORMAT_SECOND).format(date);
		} catch (Exception e) {
			return "";
		}

	}

	public static String getNowTimeSecondMilli() {
		try {
			Date date = new Date();
			return new SimpleDateFormat("yyyyMMddHHmmssS").format(date);
		} catch (Exception e) {
			return "";
		}

	}

	public static String getDateString(Date date, String formatStr) {
		if (date == null) {
			return null;
		} else {
			SimpleDateFormat des = new SimpleDateFormat(formatStr);
			String result = des.format(date);
			return result;
		}
	}

	public static String getMothString(Date date) {
		String result = null;
		if (date == null) {
			result = null;
		} else {
			SimpleDateFormat des = new SimpleDateFormat("yyyy-MM");
			result = des.format(date);
		}
		return result;
	}

	public static String getDateString(Date date) {
		return getDateString(date, DATEFORMAT_DAY);
	}

	public static String getDateLongString(Date date) {
		return getDateString(date, LEG_TIMESTAMP_FORMATE);
	}

	public static long compareDateTime(String firstDateTime, String secondDateTime) throws ParseException {
		return format(firstDateTime).getTime() - format(secondDateTime).getTime();
	}

	public static Date formatDateTime(String dateString) {

		SimpleDateFormat format = new SimpleDateFormat(DATEFORMAT_MINUTE);
		Date dt = null;
		try {
			dt = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;

	}

	public static String getDateTimeString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static Date format(String dateString) {

		SimpleDateFormat format = new SimpleDateFormat(FOC_TEMP_DATA_DATE_FORMAT);
		Date dt = null;
		try {
			dt = format.parse(dateString);
		} catch (Exception e) {
			return null;
		}
		return dt;

	}

	public static Date formatDateHHMM(String dateString) {

		SimpleDateFormat format = new SimpleDateFormat(FOC_DATEFORMAT_MINUTE);
		Date dt = null;
		try {
			dt = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;

	}

	public static Timestamp formatTimestamp(String dateString) {
		Date date = formatDateHHMM(dateString);
		if (date != null) {
			return new Timestamp(date.getTime());
		} else {
			return null;
		}
	}

	public static Timestamp formatTimestampDateTime(String dateString) {
		Date date = formatDateTime(dateString);
		if (date != null) {
			return new Timestamp(date.getTime());
		} else {
			return null;
		}
	}

	public static Long formateLegTime(String dateString) {
		Long result = null;
		SimpleDateFormat format = new SimpleDateFormat(FOC_DATEFORMAT_MINUTE);
		Date dt = null;
		if (dateString != null) {
			try {
				dt = format.parse(dateString);
				result = new Long(dt.getTime());
			} catch (ParseException e) {
			}
		}
		return result;
	}

	public static Date formatDate(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat(DATEFORMAT_DAY);
		Date dt = null;
		try {
			dt = format.parse(dateString);
		} catch (ParseException e) {
			dt = null;
		}
		return dt;

	}

	public static Date formatML(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat(PSRML_DATE_FORMATE, Locale.US);
		Date dt = null;
		try {
			dt = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			dt = null;
		}
		return dt;
	}

	public static String getDateString4Transfer(Date date, String formatStr) {
		if (date == null) {
			return null;
		} else {
			SimpleDateFormat des = new SimpleDateFormat(formatStr, Locale.US);
			String result = des.format(date);
			return result;
		}
	}

	public static Date formatDate(String dateString, String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date dt = null;
		try {
			dt = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;
	}

	public static Date secondFormatDate(String src) {
		SimpleDateFormat format = new SimpleDateFormat(FOC_TEMP_DATA_DATE_FORMAT);
		Date dt = null;
		try {
			if (src != null)
				dt = format.parse(src);
			else
				dt = null;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;
	}

	public static String exchangeTimeZone(String origin, int offset) {
		if (origin == null)
			return null;

		Date date = format(origin);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, offset);

		date = cal.getTime();

		SimpleDateFormat formatter = new SimpleDateFormat(FOC_TEMP_DATA_DATE_FORMAT);

		String result = formatter.format(date);

		return result;
	}

	public static long compareDate(String flightDate, String date) throws ParseException {
		return (formatDate(flightDate).getTime() - formatDate(date).getTime()) / (1000 * 60 * 60 * 24);
	}

	public static Timestamp formatTimestamp(String dateString, String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date dt = null;
		try {
			dt = format.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return new Timestamp(dt.getTime());
	}

	public static Calendar setStartDay(Calendar cal) {
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		return cal;
	}

	public static Calendar setEndDay(Calendar cal) {
		cal.set(11, 23);
		cal.set(12, 59);
		cal.set(13, 59);
		return cal;
	}

	public static void copyYearMonthDay(Calendar destCal, Calendar sourceCal) {
		destCal.set(1, sourceCal.get(1));
		destCal.set(2, sourceCal.get(2));
		destCal.set(5, sourceCal.get(5));
	}

	public static String formatEnDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

		return sdf.format(date).replaceAll("上午", "AM").replaceAll("下午", "PM");
	}

	public static String addOneDay(String date) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			Date dd = format.parse(date);
			calendar.setTime(dd);
			calendar.add(5, 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String tmpDate = format.format(calendar.getTime());
		return tmpDate.substring(5, 7) + "/" + tmpDate.substring(8, 10) + "/" + tmpDate.substring(0, 4);
	}

	public static String addOneHour(String date) {
		String amPm = date.substring(20, 22);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();

		int hour = Integer.parseInt(date.substring(11, 13));
		try {
			if (amPm.equals("PM")) {
				hour += 12;
			}
			date = date.substring(0, 11) + (hour >= 10 ? Integer.valueOf(hour) : new StringBuilder("0").append(hour).toString())
					+ date.substring(13, 19);
			Date dd = format.parse(date);
			calendar.setTime(dd);
			calendar.add(11, 1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String tmpDate = format.format(calendar.getTime());

		hour = Integer.parseInt(tmpDate.substring(11, 13));
		amPm = (hour >= 12) && (hour != 0) ? "PM" : "AM";
		if (amPm.equals("PM")) {
			hour -= 12;
		}
		tmpDate = tmpDate.substring(5, 7) + "/" + tmpDate.substring(8, 10) + "/" + tmpDate.substring(0, 4) + " "
				+ (hour >= 10 ? Integer.valueOf(hour) : new StringBuilder("0").append(hour).toString()) + tmpDate.substring(13, tmpDate.length())
				+ " " + amPm;

		return tmpDate;
	}

	public static String timeStrToDateStr(String timeStr) {
		String dateStr = timeStr.substring(24, 28) + "-";

		String mon = timeStr.substring(4, 7);
		if (mon.equals("Jan"))
			dateStr = dateStr + "01";
		else if (mon.equals("Feb"))
			dateStr = dateStr + "02";
		else if (mon.equals("Mar"))
			dateStr = dateStr + "03";
		else if (mon.equals("Apr"))
			dateStr = dateStr + "04";
		else if (mon.equals("May"))
			dateStr = dateStr + "05";
		else if (mon.equals("Jun"))
			dateStr = dateStr + "06";
		else if (mon.equals("Jul"))
			dateStr = dateStr + "07";
		else if (mon.equals("Aug"))
			dateStr = dateStr + "08";
		else if (mon.equals("Sep"))
			dateStr = dateStr + "09";
		else if (mon.equals("Oct"))
			dateStr = dateStr + "10";
		else if (mon.equals("Nov"))
			dateStr = dateStr + "11";
		else if (mon.equals("Dec")) {
			dateStr = dateStr + "12";
		}

		dateStr = dateStr + "-" + timeStr.substring(8, 10);

		return dateStr;
	}

	public static int getExtraDayOfWeek(String sDate) {
		try {
			String formater = "yyyy-MM-dd";
			SimpleDateFormat format = new SimpleDateFormat(formater);
			Date date = format.parse(sDate);
			String weekday = date.toString().substring(0, 3);
			if (weekday.equals("Mon"))
				return 1;
			if (weekday.equals("Tue"))
				return 2;
			if (weekday.equals("Wed"))
				return 3;
			if (weekday.equals("Thu"))
				return 4;
			if (weekday.equals("Fri"))
				return 5;
			if (weekday.equals("Sat")) {
				return 6;
			}
			return 0;
		} catch (Exception ex) {
		}
		return 0;
	}

	public static String getDateWeekDay(String sDate) {
		try {
			String formater = "yyyy-MM-dd";
			SimpleDateFormat format = new SimpleDateFormat(formater);
			Date date = format.parse(sDate);
			return date.toString().substring(0, 3);
		} catch (Exception ex) {
		}
		return "";
	}

	public static List<String> getUpDownFiveYear(Calendar cal) {
		List yearlist = new ArrayList();

		int curyear = cal.get(1);
		yearlist.add(String.valueOf(curyear - 2));
		yearlist.add(String.valueOf(curyear - 1));
		yearlist.add(String.valueOf(curyear));
		yearlist.add(String.valueOf(curyear + 1));
		yearlist.add(String.valueOf(curyear + 2));

		return yearlist;
	}

	public static List<String> getTwelveMonth() {
		List monthlist = new ArrayList();

		for (int idx = 1; idx <= 12; idx++) {
			monthlist.add(String.valueOf(idx));
		}

		return monthlist;
	}

	public static String[] getDaysBetweenDate(String startTime, String endTime) {
		String[] dateArr = (String[]) null;
		try {
			String stime = timeStrToDateStr(startTime);
			String etime = timeStrToDateStr(endTime);

			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(stime);
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(etime);

			long day = (date1.getTime() - date2.getTime()) / 86400000L > 0L ? (date1.getTime() - date2.getTime()) / 86400000L
					: (date2.getTime() - date1.getTime()) / 86400000L;

			dateArr = new String[Integer.valueOf(String.valueOf(day + 1L)).intValue()];
			for (int idx = 0; idx < dateArr.length; idx++) {
				if (idx == 0) {
					dateArr[idx] = stime;
				} else {
					stime = addOneDay(stime);
					stime = stime.substring(6, 10) + "-" + stime.substring(0, 2) + "-" + stime.substring(3, 5);
					dateArr[idx] = stime;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dateArr;
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println();
	}
}
