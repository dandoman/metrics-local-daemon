package com.metrics.daemon;

import java.util.List;

import com.metrics.daemon.dao.ClientLogScanner;
import com.metrics.daemon.pojo.StagedMetric;

public class ClientMinuteRunnable implements Runnable {

	@Override
	public void run() {
		List<StagedMetric> stagedMetricList = ClientLogScanner.parseClientLog("./src/main/resources/file/clientmetricsample.txt");
		for(StagedMetric metric : stagedMetricList) {
			System.out.println(metric);
		}
	}

}
