package com.metrics.daemon.pojo;

import lombok.Data;

@Data
public class CreateMonitorRequest {
	private String apiKey;
	private String applicationName;
	private String operation;
	private String marketplace;
	private String hostName;
	private String metricName;
	private String type;
	private Double threshold;
	private Integer counts;
	private Boolean less;
	
	private String description;
	private String emailRecipient;
}
