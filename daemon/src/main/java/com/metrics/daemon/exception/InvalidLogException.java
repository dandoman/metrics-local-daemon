package com.metrics.daemon.exception;

public class InvalidLogException extends RuntimeException {
	private static final long serialVersionUID = -8860319491560545165L;

	public InvalidLogException(String message) {
		super(message);
	}
}
