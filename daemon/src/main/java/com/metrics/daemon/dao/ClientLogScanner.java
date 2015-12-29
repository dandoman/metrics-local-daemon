package com.metrics.daemon.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.metrics.daemon.logic.ClientLogParser;
import com.metrics.daemon.pojo.RawStagedMetric;
import com.metrics.daemon.pojo.StagedMetric;

public class ClientLogScanner {
	
	private static List<String> rawClientLog;
	
	public static List<StagedMetric> parseClientLog(String filename) {
		rawClientLog = scanRawClientLog(filename);
		List<RawStagedMetric> rawMetricList = ClientLogParser.rawClientLogToRawMetrics(rawClientLog, filename);
		List<StagedMetric> stagedMetricList = ClientLogParser.rawMetricsToStagedMetrics(rawMetricList, filename);
		return stagedMetricList;
	}
	
	public static long getRawClientLogLength() {
		//In case not yet initialized
		try {
			return rawClientLog.size();
		} catch(NullPointerException npe) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * Scans in the raw client log and uses ClientLogStateAccess to store
	 * the new state of the client log.
	 * @param filename name of client log to be parsed
	 * @return the raw client log
	 */
	private static List<String> scanRawClientLog(String filename) {
		//Empty stream is returned if filename skips less than currentLineNumber elements
		//CHECK OFF BY ONE ERROR IN UNIT TESTING
		try {
			return Files.lines(Paths.get(filename))
					 	.skip(ClientLogStateAccess.getCurrentLineNumber())
					 	.collect(Collectors.toList());
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
}
