package com.metrics.daemon.validation;

import com.metrics.daemon.pojo.RawStagedMetric;

public class TypeValidation {
	
	/**
	 * Checks both startTime and endTime metric values.
	 * @param rawMetric
	 * @return
	 */
	public static boolean validTime(RawStagedMetric rawMetric) {
		String stringStartTime = rawMetric.getStartTime();
		long startTime = checkLongTime(stringStartTime);
		if(startTime < 0) {
			return false;
		}
		
		String stringEndTime = rawMetric.getEndTime();
		long endTime = checkLongTime(stringEndTime);
		if(endTime < 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Validates time if it is a valid long value.
	 * @param time as a string
	 * @return Time as a long if valid or else -1.
	 */
	public static long checkLongTime(String time) {
		long longTime;
		try {
			longTime = Long.parseLong(time);
			return longTime;
		}
		catch(NumberFormatException nfe) {
			return -1;
		}
	}
	
	/**
	 * Checks if double value is valid and positive.
	 * @param stringValue
	 * @return
	 */
	public static boolean validValue(String stringValue) {
		double value = validDoubleValue(stringValue);
		if(value < 0) {
			return false;
		}
		return true;
	}
	
	private static double validDoubleValue(String value) {
		double doubleValue;
		try {
			doubleValue = Double.parseDouble(value);
			return doubleValue;
		}
		catch(NumberFormatException nfe) {
			return -1;
		}
	}
}
