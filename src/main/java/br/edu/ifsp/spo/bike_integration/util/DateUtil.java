package br.edu.ifsp.spo.bike_integration.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public interface DateUtil {

	public static String getFormattedHyphenStringDate(LocalDateTime date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	public static String getFormattedSlashStringDate(LocalDateTime date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}

	public static LocalDateTime parseDate(String date) {
		try {
			return LocalDateTime.parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String fixFormattDate(String date) {
		if (date == null) {
			return null;
		}
		if (date.contains("/")) {
			return fixDateHyphen(date);
		}
		return date;
	}

	/*
	 * PRIVATE METHODS
	 */

	private static String fixDateHyphen(String date) {
		String[] dateSplited = date.split("/");
		return dateSplited[2] + "-" + dateSplited[1] + "-" + dateSplited[0];
	}

}
