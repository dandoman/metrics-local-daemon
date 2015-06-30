package com.metrics.daemon.pojo;

import lombok.Data;

@Data
public class RawStagedMetric {
	private String applicationName;
	private String operation;
	private String marketplace;
	private String hostName;
	private String startTime;
	private String endTime;
	private String metricNameValue;
}
