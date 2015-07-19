package com.metrics.daemon.validation;

import java.util.List;

import com.metrics.daemon.exception.InvalidMetricException;
import com.metrics.daemon.logic.RawLogParser;
import com.metrics.daemon.pojo.RawStagedMetric;

//Might be worth validating if there is only 1 = sign in each variable.
//In other words, replace all return false by a log error.
public class LogicValidation {
	/**
	 * Validates variables.
	 * It guarantees that the variable type is correct.
	 * It guarantees that there is an = sign between variable and data.
	 * It guarantees that there is some non-whitespace char after the =.
	 * @param type before =
	 * @param line of data after =
	 * @return line of data if valid or null if invalid
	 */
	private static void validVariable(String type, String line) {
		//Regex for one or more non-whitespace chars
		if (!line.matches(type + "=\\S+")) {
			throw new InvalidMetricException("Error : ");
		} 
	}
	
	/**
	 * Validates data size.
	 * Current valid size is 7 variables.
	 * @param List<String> of rawStagedMetrics.
	 * @return True if size is 7 or else return false.
	 */
	private static void validDataSize(List<String> rawStagedMetrics) {
		final int NUMBER_OF_VARIABLES = 7;
		if(rawStagedMetrics.size() != NUMBER_OF_VARIABLES) {
			throw new InvalidMetricException("Error : ");
		}
	}
	
	//Right now this is optimized for return
	//This could however be changed to log all invalid data
	//In other words only return once and variable == null, then log failure.
	public static void validRawData(List<String> rawStagedMetrics) {
		validDataSize(rawStagedMetrics);
		validVariable("ApplicationName", rawStagedMetrics.get(0));
		validVariable("Operation", rawStagedMetrics.get(1));
		validVariable("MarketPlace", rawStagedMetrics.get(2));
		validVariable("HostName", rawStagedMetrics.get(3));
		validVariable("StartTime",rawStagedMetrics.get(4));
		validVariable("EndTime",rawStagedMetrics.get(5));
		validVariable("Metrics",rawStagedMetrics.get(6));
	}
	
	/**
	 * Checks the type of start and end time and validates the list of metrics.
	 * It guarantees that start and end time are positive longs.
	 * It guarantees that the metric has an = sign with non-whitespace chars on both sides.
	 * @param RawStagedMetric rawMetric
	 * @return true if guarantees are met or else false
	 */
	public static void validRawMetric(RawStagedMetric rawMetric) {
		TypeValidation.validTime(rawMetric);
		List<String> metricNameArray = RawLogParser.splitMetricNameList(rawMetric);
		for(String nameValue : metricNameArray) {
			validMetricValueFormat(nameValue);
			String stringValue = nameValue.split("=")[1];
			TypeValidation.validValue(stringValue);
		}
	}
	
	/**
	 * Validates that metric value has = contained by non-whitespace chars.
	 * @param metricValue as a string
	 * @return true if valid or else false
	 */
	private static void validMetricValueFormat(String metricValue) {
		if(!metricValue.matches("\\S+=\\S+")) {
			throw new InvalidMetricException("Error : ");
		}
	}
}
