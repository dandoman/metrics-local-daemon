package com.metrics.daemon.exception;

public class LogNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -687991492884005033L;
	
	public LogNotFoundException(String message) {
		super(message);
	}
}
