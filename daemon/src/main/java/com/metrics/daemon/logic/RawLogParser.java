package com.metrics.daemon.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.metrics.daemon.pojo.RawStagedMetric;
import com.metrics.daemon.pojo.StagedMetric;
import com.metrics.daemon.validation.LogicValidation;

public class RawLogParser {
	
	/**
	 * Delimit the raw log by semicolons and return this array as a 
	 * List<String>.
	 * @param rawLog to be delimited
	 * @return List<String> of semicolon delimited log
	 */
	public static List<String> semicolonDelimit(String rawLog) {
		return Arrays.<String>asList(rawLog.split(";"));
	}
	
	/**
	 * Splits up raw log using = delimeter populates a rawStagedMetric.
	 * Needs to be validated before it comes in here.
	 * @param List<String> rawLog for one metric.
	 * @return RawStagedMetric with valid input.
	 */
	public static RawStagedMetric buildRawStagedMetric(String rawLog) {
		List<String> rawMetric = semicolonDelimit(rawLog);
		LogicValidation.validRawData(rawMetric);
		
		RawStagedMetric newRawMetric = new RawStagedMetric();
		
		String applicationName = rawMetric.get(0).split("=")[1];
		newRawMetric.setApplicationName(applicationName);
		String operation = rawMetric.get(1).split("=")[1];
		newRawMetric.setOperation(operation);
		String marketPlace = rawMetric.get(2).split("=")[1];
		newRawMetric.setMarketplace(marketPlace);
		String hostName = rawMetric.get(3).split("=")[1];
		newRawMetric.setHostName(hostName);
		String startTime = rawMetric.get(4).split("=")[1];
		newRawMetric.setStartTime(startTime);
		String endTime = rawMetric.get(5).split("=")[1];
		newRawMetric.setEndTime(endTime);
		String metricNameValue = rawMetric.get(6).split("=")[1];
		newRawMetric.setMetricNameValue(metricNameValue);
		
		return newRawMetric;
	}
	
	/**
	 * RawStagedMetric of strings is converted into a stagedMetric of types.
	 * @param RawStagedMetric object
	 * @return List<StagedMetric> depending on how many metrics in given list
	 */
	public static List<StagedMetric> rawToStagedMetricList(RawStagedMetric raw) {
		LogicValidation.validRawMetric(raw); //InvalidMetricException thrown
		
		String applicationName = raw.getApplicationName();
		String operation = raw.getOperation();
		String marketPlace = raw.getMarketplace();
		String hostName = raw.getHostName();
		long startTime = Long.parseLong(raw.getStartTime());
		long endTime = Long.parseLong(raw.getEndTime());
		
		List<StagedMetric> stagedMetricList = new ArrayList<>();
		List<String> metricList = splitMetricNameList(raw);
		for(String nameValue : metricList) {
			List<String> thismetricAndValue = Arrays.<String>asList(nameValue.split("="));
			String metricName = thismetricAndValue.get(0);
			String metricValue = thismetricAndValue.get(1);
			double value = Double.parseDouble(metricValue);
			
			StagedMetric stagedMetric = new StagedMetric();
			stagedMetric.setApplicationName(applicationName);
			stagedMetric.setOperation(operation);
			stagedMetric.setMarketplace(marketPlace);
			stagedMetric.setHostName(hostName);
			stagedMetric.setStartTime(startTime);
			stagedMetric.setEndTime(endTime);
			stagedMetric.setMetricName(metricName);
			stagedMetric.setValue(value);
			stagedMetricList.add(stagedMetric);
		}
		return stagedMetricList;
	}
	
	/**
	 * WARNING : () brackets may not be best choice here as they might be in metric name. (maybe curly brackets)
	 * Splits up comma delimited metric list.
	 * Also removes all parentheses in the list.
	 * @param rawMetric
	 * @return
	 */
	public static List<String> splitMetricNameList(RawStagedMetric rawMetric) {
		String metricNameValue = rawMetric.getMetricNameValue();
		metricNameValue = metricNameValue.replaceAll("\\(|\\)", "");
		return Arrays.<String>asList(metricNameValue.split(","));
	}
}
