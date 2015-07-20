package com.metrics.daemon.exception;

public class InvalidMetricException extends IllegalArgumentException {
	
	private static final long serialVersionUID = -114470532613989380L;
	
	public InvalidMetricException(String message) {
		super(message);
	}
}
