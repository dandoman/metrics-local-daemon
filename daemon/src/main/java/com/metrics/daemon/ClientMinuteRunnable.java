package com.metrics.daemon;

import java.util.List;

import com.metrics.daemon.dao.ClientLogScanner;
import com.metrics.daemon.dao.ClientLogStateAccess;
import com.metrics.daemon.pojo.StagedMetric;

public class ClientMinuteRunnable implements Runnable {

	@Override
	public void run() {
		String currentFileName = ClientLogStateAccess.getCurrentFileName();
		List<StagedMetric> stagedMetricList = ClientLogScanner.parseClientLog(currentFileName);
		
		//TODO
		/*
		 * REPLACE WITH HTTP POST REQUEST HERE, SAME AMOUNT OF TIME?
		 */
		for(StagedMetric metric : stagedMetricList) {
			System.out.println(metric);
		}
		//TODO
	}

}
