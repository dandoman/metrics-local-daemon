package com.metrics.daemon.logic;

import java.util.ArrayList;
import java.util.List;

import com.metrics.daemon.exception.InvalidMetricException;
import com.metrics.daemon.exception.handler.InvalidMetricHandler;
import com.metrics.daemon.pojo.RawStagedMetric;
import com.metrics.daemon.pojo.StagedMetric;

public class ClientLogParser {
	/**
	 * Handles InvalidMetricException here so that it can continue to parse
	 * after the exception is caught.
	 * @param rawMetricList
	 * @param filename
	 * @return
	 */
	public static List<StagedMetric> rawMetricsToStagedMetrics(List<RawStagedMetric> rawMetricList, String filename) {
		List<StagedMetric> stagedMetricList = new ArrayList<StagedMetric>();
		int lineNumber = 1;
		for(RawStagedMetric rawMetric : rawMetricList) {
			try {
				addValidRawToMetricList(rawMetric, stagedMetricList);
			} catch(InvalidMetricException ime) {
				InvalidMetricHandler.handle(filename,lineNumber,ime); //handle here to continue parsing list
			}
			lineNumber++;
		}
		return stagedMetricList;
	}
	
	/**
	 * Helper function to split up a raw metric into individual metrics and
	 * add it to the stagedMetricList.
	 * @param rsm
	 * @param stagedMetricList
	 */
	private static void addValidRawToMetricList(RawStagedMetric rsm, List<StagedMetric> stagedMetricList) {
		List<StagedMetric> splitMetricList = RawLogParser.rawToStagedMetricList(rsm);
		for(StagedMetric sm : splitMetricList) {
			stagedMetricList.add(sm);
		}
	}
	
	/**
	 * 
	 * @param rawClientLog
	 * @param filename
	 * @return
	 */
	public static List<RawStagedMetric> rawClientLogToRawMetrics(List<String> rawClientLog, String filename) {
		List<RawStagedMetric> rawMetricList = new ArrayList<RawStagedMetric>();
		int lineNumber = 1;
		for(String rawLog : rawClientLog) {
			RawStagedMetric rawMetric = null;
			try {
				rawMetric = RawLogParser.buildRawStagedMetric(rawLog);
			} catch(InvalidMetricException ime) {
				InvalidMetricHandler.handle(filename,lineNumber,ime); //handle here to continue parsing list
			}
			if(rawMetric != null) {
				rawMetricList.add(rawMetric);
			}
			lineNumber++;
		}
		return rawMetricList;
	}
}
