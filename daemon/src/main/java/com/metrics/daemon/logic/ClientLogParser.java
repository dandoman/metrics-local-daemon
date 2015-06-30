package com.metrics.daemon.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.metrics.daemon.pojo.RawStagedMetric;
import com.metrics.daemon.pojo.StagedMetric;
import com.metrics.daemon.validation.LogicValidation;

public class ClientLogParser {

	public List<StagedMetric> logInput(String filename) {
		try {
			File clientLog = new File(filename);
			return parseLog(clientLog);
		} 
		catch (FileNotFoundException fnfe) {
			// Deal with the exception;
		}
		return new ArrayList<StagedMetric>();
	}

	private List<StagedMetric> parseLog(File clientLog) throws FileNotFoundException {
		Scanner in = new Scanner(clientLog);

		List<StagedMetric> metricList = listMetrics(in);

		return metricList;
	}

	/**
	 * Gets list of all raw metrics and then validates their type and logic.
	 * After validation, it adds each raw metric to the staged metric list.
	 * @param Scanner in
	 * @return Validated List<StagedMetric>
	 */
	private List<StagedMetric> listMetrics(Scanner in) {
		List<RawStagedMetric> rawStagedMetricList = buildRawMetricList(in);
		
		List<StagedMetric> stagedMetricList = new ArrayList<StagedMetric>();
		for(RawStagedMetric rsm : rawStagedMetricList) {
			if(LogicValidation.validRawMetric(rsm)) {
				addValidRawToMetricList(rsm, stagedMetricList);
			}
		}
		return stagedMetricList;
	}
	
	/**
	 * Helper function to split up a raw metric into individual metrics and
	 * add it to the stagedMetricList.
	 * @param rsm
	 * @param stagedMetricList
	 */
	private void addValidRawToMetricList(RawStagedMetric rsm, List<StagedMetric> stagedMetricList) {
		List<StagedMetric> splitMetricList = RawLogParser.rawToStagedMetricList(rsm);
		for(StagedMetric sm : splitMetricList) {
			stagedMetricList.add(sm);
		}
	}
	
	/**
	 * Converts raw string log data into a list of raw staged metrics
	 * @param Scanner in for client log.
	 * @return List<RawStagedMetric> of logic validated raw metrics.
	 */
	private List<RawStagedMetric> buildRawMetricList(Scanner in) {
		List<RawStagedMetric> rawStagedMetricList = new ArrayList<RawStagedMetric>();
		while(in.hasNextLine()) {
			in.nextLine(); //skip a line here
			List<String> rawLog = buildRawList(in);
			//If data is valid then add it to raw staged metric list
			if(LogicValidation.validRawData(rawLog)) {
				RawStagedMetric newRawMetric = RawLogParser.buildRawStagedMetric(rawLog);
				rawStagedMetricList.add(newRawMetric);
			}
			//Or else just continue to next raw metric in log
			//This else is redundant
			else {
				continue;
			}
		}
		return rawStagedMetricList;
	}
	
	/**
	 * Builds a raw list of string data taken from client log.
	 * Blindly takes in raw metric data until a whitespace is reached.
	 * Whitespace delimiting here is crucial.
	 * @param Scanner in for client log
	 * @return List<String> of raw un-validated metrics
	 */
	private List<String> buildRawList(Scanner in) {
		List<String> rawLog = new ArrayList<String>();
		String nextLine = in.nextLine();
		// Need to ensure that there are white lines between metric clusters
		while (!nextLine.equals("\\n")) {
			rawLog.add(nextLine);
			nextLine = in.nextLine();
		}
		return rawLog;
	}
	
	
}
