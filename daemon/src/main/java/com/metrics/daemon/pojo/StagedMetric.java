package com.metrics.daemon.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StagedMetric {
	private String applicationName;
	private String operation;
	private String marketplace;
	private String hostName;
	private long startTime;
	private long endTime;
	private String metricName;
	private double value;
}
