package com.metrics.daemon.logic;

import java.util.ArrayList;
import java.util.List;

import com.metrics.daemon.pojo.RawStagedMetric;
import com.metrics.daemon.pojo.StagedMetric;

public class RawLogParser {
	
	/**
	 * Splits up raw log using = delimeter populates a rawStagedMetric.
	 * Needs to be validated before it comes in here.
	 * @param List<String> rawLog for one metric.
	 * @return RawStagedMetric with valid input.
	 */
	public static RawStagedMetric buildRawStagedMetric(List<String> rawLog) {
		RawStagedMetric newRawMetric = new RawStagedMetric();
		
		String applicationName = rawLog.get(0).split("=")[1];
		newRawMetric.setApplicationName(applicationName);
		String operation = rawLog.get(1).split("=")[1];
		newRawMetric.setOperation(operation);
		String marketPlace = rawLog.get(2).split("=")[1];
		newRawMetric.setMarketplace(marketPlace);
		String hostName = rawLog.get(3).split("=")[1];
		newRawMetric.setHostName(hostName);
		String startTime = rawLog.get(4).split("=")[1];
		newRawMetric.setStartTime(startTime);
		String endTime = rawLog.get(5).split("=")[1];
		newRawMetric.setEndTime(endTime);
		String metricNameValue = rawLog.get(6).split("=")[1];
		newRawMetric.setMetricNameValue(metricNameValue);
		
		return newRawMetric;
	}
	
	/**
	 * RawStagedMetric of strings is converted into a stagedMetric of types.
	 * This function assumes that the RawStagedMetric has already been validated.
	 * @param RawStagedMetric object
	 * @return List<StagedMetric> depending on how many metrics in given list
	 */
	public static List<StagedMetric> rawToStagedMetricList(RawStagedMetric raw) {
		String applicationName = raw.getApplicationName();
		String operation = raw.getOperation();
		String marketPlace = raw.getMarketplace();
		String hostName = raw.getHostName();
		long startTime = Long.parseLong(raw.getStartTime());
		long endTime = Long.parseLong(raw.getEndTime());
		
		List<StagedMetric> stagedMetricList = new ArrayList<StagedMetric>();
		String[] metricList = splitMetricNameList(raw);
		for(String nameValue : metricList) {
			String[] metricAndValue = nameValue.split("=");
			String metricName = metricAndValue[0];
			double value = Double.parseDouble(metricAndValue[1]);
			
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
	public static String[] splitMetricNameList(RawStagedMetric rawMetric) {
		String metricNameValue = rawMetric.getMetricNameValue();
		metricNameValue = metricNameValue.replaceAll("\\(|\\)", "");
		return metricNameValue.split(",");
	}
}
