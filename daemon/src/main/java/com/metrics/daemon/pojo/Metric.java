package com.metrics.daemon.pojo;

import lombok.Data;

@Data
public class Metric {
	private String applicationName;
	private String operation;
	private String marketplace;
	private String hostName;
	private long timeStamp;
	private TimeLevel timeLevel;
	private String metricName;
	private double p0;
	private double p50;
	private double p75;
	private double p90;
	private double p99;
	private double p999;
	private double p9999;
	private double p100;
	private double avg;
	private double count;
	private double sum;
}
