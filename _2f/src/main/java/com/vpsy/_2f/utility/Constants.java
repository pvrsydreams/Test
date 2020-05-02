package com.vpsy._2f.utility;

import java.text.SimpleDateFormat;

public class Constants {

	/** Database value search pattern. Ex: name:jey,age>18 */
	public static final String SEARCH_PATTERN = "(\\w+?)(:|<|>)(\\w+?),";

	/** Variable to hold date format used in the application*/
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	/** Variable to hold time format used in the application*/
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/** Variable to hold OTP expiry time in milliseconds. Ex: 10min*/
	public static final long OTP_EXPIRES_IN = 10 * 60 * 1000;
}
