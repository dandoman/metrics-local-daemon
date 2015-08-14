package com.metrics.daemon.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.metrics.daemon.logic.ClientLogParser;
import com.metrics.daemon.pojo.ClientLogState;
import com.metrics.daemon.pojo.RawStagedMetric;
import com.metrics.daemon.pojo.StagedMetric;

public class ClientLogScanner {
	public static List<StagedMetric> parseClientLog(String filename) {
		List<String> rawClientLog = scanRawClientLog(filename);
		List<RawStagedMetric> rawMetricList = ClientLogParser.rawClientLogToRawMetrics(rawClientLog, filename);
		List<StagedMetric> stagedMetricList = ClientLogParser.rawMetricsToStagedMetrics(rawMetricList, filename);
		return stagedMetricList;
	}
	
	/**
	 * Scans in the raw client log and uses ClientLogStateAccess to store
	 * the new state of the client log.
	 * @param filename name of client log to be parsed
	 * @return the raw client log
	 */
	private static List<String> scanRawClientLog(String filename) {
		long currentLineNumber = 0;
		if(ClientLogStateAccess.exists()) {
			currentLineNumber = ClientLogStateAccess.getCurrentState().getCurrentLineNumber();
		}
		try (Stream<String> lines = Files.lines(Paths.get(filename))
										 .skip(currentLineNumber)) {
			List<String> rawMetrics = lines.collect(Collectors.toList());
			long newLineNumber = currentLineNumber + rawMetrics.size();
			ClientLogState newState = new ClientLogState();
			newState.setCurrentLineNumber(newLineNumber);
			ClientLogStateAccess.saveCurrentState(newState);
			return rawMetrics;
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
	
	public static void main(String[] args) {
		List<StagedMetric> stagedMetricList = ClientLogScanner.parseClientLog("./src/main/resources/file/clientmetricsample.txt");
		for(StagedMetric metric : stagedMetricList) {
			System.out.println(metric);
		}
		
		/*
		 * Send out HTML request from client
		 */
		//MetricClient metricClient = new MetricClient();
		//metricClient.createMetric(stagedMetricList);
		/*
		 * End of Request
		 */
	}
}
