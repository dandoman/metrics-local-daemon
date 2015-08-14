package com.metrics.daemon.pojo;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClientLogState implements Serializable {
	private static final long serialVersionUID = 6514393267674187932L;
	private long currentLineNumber;
}
