package com.metrics.daemon;

import java.util.List;

import lombok.AllArgsConstructor;

import org.joda.time.DateTime;
import org.joda.time.Period;

import com.metrics.daemon.dao.ClientLogScanner;
import com.metrics.daemon.dao.ClientLogStateAccess;
import com.metrics.daemon.pojo.StagedMetric;

@AllArgsConstructor
public class ClientMinuteRunnable implements Runnable {
	private final String logDirectoryName;

	@Override
	public void run() {
		//String currentFileName = ClientLogStateAccess.getCurrentFileName();
		List<StagedMetric> stagedMetricList = ClientLogScanner.parseClientLog(logDirectoryName);

		// TODO
		/*
		 * REPLACE WITH HTTP POST REQUEST HERE, SAME AMOUNT OF TIME?
		 */
		for (StagedMetric metric : stagedMetricList) {
			System.out.println(metric);
		}
		
		//long currentLineNumber = ClientLogStateAccess.getCurrentLineNumber();
		//long newLineNumber = currentLineNumber + ClientLogScanner.getRawClientLogLength();
		//ClientLogStateAccess.writeCurrentState(newLineNumber, 
		//									   ClientLogStateAccess.getCurrentFileName(),
		//									   ClientLogStateAccess.getCurrentDate());
		//checkAndWriteNewHourLog();
	}

	private void checkAndWriteNewHourLog() {
		DateTime oldDate = ClientLogStateAccess.getCurrentDate();
		DateTime currentDate = DateTime.now();

		Period fromOldToCurrent = new Period(oldDate, currentDate);
		int minutesBetweenOldAndCurrent = fromOldToCurrent.getMinutes();
		int hoursBetweenOldAndCurrent = fromOldToCurrent.getHours();

		boolean onlyAddedOneMinute = minutesBetweenOldAndCurrent == 1
				&& hoursBetweenOldAndCurrent == 0;
		boolean addedMinuteAndHour = minutesBetweenOldAndCurrent == 1
				&& hoursBetweenOldAndCurrent == 1;

		if (onlyAddedOneMinute) {
			ClientLogStateAccess.writeCurrentState(ClientLogStateAccess.getCurrentLineNumber(), 
												   ClientLogStateAccess.getCurrentFileName(), 
												   currentDate);
		} 
		else if (addedMinuteAndHour) {
			ClientLogStateAccess.writeCurrentState(0, 
												   "service_log." + currentDate.toString("Y-M-d-H"), 
												   currentDate);
		} 
		else {
			// TODO
			// Read in all logs till current date
		}
	}

}
