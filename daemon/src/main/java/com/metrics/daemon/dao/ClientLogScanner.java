package com.metrics.daemon.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.metrics.daemon.logic.ClientDirectoryParser;
import com.metrics.daemon.logic.ClientLogParser;
import com.metrics.daemon.pojo.StagedMetric;

public class ClientLogScanner {
	
	private static List<String> rawClientLog;
	
	public static List<StagedMetric> parseClientLog(String logdirectory) {
		List<File> logsToParse = ClientDirectoryParser.getFileBatch(logdirectory);
		rawClientLog = aggregateLogs(logsToParse);
		List<StagedMetric> stagedMetricList = ClientLogParser.logToJson(rawClientLog);
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
	
	//TODO This can be removed for efficiency if we only parse one 5-minute log per daemon cycle
	private static List<String> aggregateLogs(List<File> logsToParse) {
		List<String> allRawMetrics = new ArrayList<>();
		for(File log : logsToParse) {
			List<String> rawLog = scanRawClientLog(log.getAbsolutePath());
			for(String metric : rawLog) {
				allRawMetrics.add(metric);
			}
		}
		return allRawMetrics;
	}
	
	/**
	 * Scans in the raw client log and uses ClientLogStateAccess to store
	 * the new state of the client log.
	 * @param logdirectory name of client log to be parsed
	 * @return the raw client log
	 */
	private static List<String> scanRawClientLog(String filename) {
		//Empty stream is returned if logdirectory skips less than currentLineNumber elements
		//CHECK OFF BY ONE ERROR IN UNIT TESTING
		try {
			return Files.lines(Paths.get(filename))
					 	//.skip(ClientLogStateAccess.getCurrentLineNumber())
					 	.collect(Collectors.toList());
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
}
