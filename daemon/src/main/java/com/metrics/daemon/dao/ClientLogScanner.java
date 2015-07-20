package com.metrics.daemon.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.metrics.daemon.logic.ClientLogParser;
import com.metrics.daemon.pojo.RawStagedMetric;
import com.metrics.daemon.pojo.StagedMetric;

public class ClientLogScanner {
	public List<StagedMetric> parseClientLog(String filename) {
		List<String> rawClientLog = scanRawClientLog(filename);
		List<RawStagedMetric> rawMetricList = ClientLogParser.rawClientLogToRawMetrics(rawClientLog, filename);
		List<StagedMetric> stagedMetricList = ClientLogParser.rawMetricsToStagedMetrics(rawMetricList, filename);
		return stagedMetricList;
	}
	
	private List<String> scanRawClientLog(String filename) {
		File clientLog = new File(filename);
		Scanner in = null;
		try {
			in = new Scanner(clientLog);
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException(fnfe);
		} finally {
			in.close();
		}

		List<String> rawMetrics = new ArrayList<String>();
		while (in.hasNext()) {
			rawMetrics.add(in.nextLine());
		}
		return rawMetrics;
	}
}
