package com.mesutemre.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mesudemre
 * @version 1.0
 * @version 2.0 (24.04.2017)
 * @version 3.0 (19.08.2017)
 * @since <b style='color:#0D68FA'>11.04.2017</b> , <b style =
 *        'color:orange'>DateUtil class'ı create edildi ve
 *        convertStringToSQLDate() eklendi</b></br> <b
 *        style='color:#0D68FA'>30.04.2017</b> , <b style =
 *        'color:orange'>convertStringToSQLDate() metoduna null kontrolü
 *        eklendi</b></br> <b style='color:#0D68FA'>21.05.2017</b> , <b style =
 *        'color:orange'>getCurrentDate() metodu eklendi.</b></br> <b
 *        style='color:#0D68FA'>24.05.2017</b> , <b style =
 *        'color:orange'>convertSqlDateToString() metodu eklendi.</b></br>
 */

public class DateUtil {

	public static java.sql.Date convertStringToSQLDate(String date) {
		try {
			if (date != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
				Date formattedDate = sdf.parse(date);

				return new java.sql.Date(formattedDate.getTime());
			}
		} catch (ParseException e) {
			System.err
					.println("DateUtil classında convertStringToSQLDate() metodunda hata meydana geldi!");
			e.printStackTrace();
		}
		return null;
	}

	public static String getCurrentDate() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			String formattedDate = sdf.format(new Date());

			return formattedDate;
		} catch (Exception e) {
			System.err
					.println("DateUtil classında getCurrentDate() metodunda hata meydana geldi!");
			e.printStackTrace();
		}
		return null;
	}

	public static String convertSqlDateToString(java.sql.Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String text = sdf.format(date);
		return text;
	}

	public static Date changeDateFormat(Date date, String format)
			throws ParseException {
		DateFormat df = new SimpleDateFormat(format);
		return df.parse(df.format(date));
	}

	public static String date2String(long date, String format) {

		return date2String(new Date(date), format);

	}

	public static String date2String(Date date, String format) {

		if (date != null) {

			SimpleDateFormat sdf = new SimpleDateFormat();

			sdf.applyPattern(format);

			return sdf.format(date);

		} else {

			return "";

		}

	}

	public static Date string2DateFormat(String stringDate, String format) {

		if (!stringDate.equals("")) {

			SimpleDateFormat dateFormat = new SimpleDateFormat(format);

			Date convertedDate = null;

			try {

				convertedDate = dateFormat.parse(stringDate);

			} catch (ParseException e) {

				e.printStackTrace();

			}

			return convertedDate;

		} else {

			return null;

		}

	}

}
