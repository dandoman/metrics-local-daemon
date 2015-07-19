package com.metrics.daemon.validation;

import com.metrics.daemon.exception.InvalidMetricException;
import com.metrics.daemon.pojo.RawStagedMetric;

public class TypeValidation {

	/**
	 * Checks both startTime and endTime metric values.
	 * 
	 * @param rawMetric
	 * @return
	 */
	public static void validTime(RawStagedMetric rawMetric) {
		String stringStartTime = rawMetric.getStartTime();
		try {
			Long.parseLong(stringStartTime);
		} catch (NumberFormatException nfe) {
			throw new InvalidMetricException("Error : ");
		}

		String stringEndTime = rawMetric.getEndTime();
		try {
			Long.parseLong(stringEndTime);
		} catch (NumberFormatException nfe) {
			throw new InvalidMetricException("Error : ");
		}
	}

	/**
	 * Checks if double value is valid and positive.
	 * 
	 * @param stringValue
	 * @return
	 */
	public static void validValue(String stringValue) {
		try {
			Double.parseDouble(stringValue);
		} catch (NumberFormatException nfe) {
			throw new InvalidMetricException("Error : ");
		}
	}
}
