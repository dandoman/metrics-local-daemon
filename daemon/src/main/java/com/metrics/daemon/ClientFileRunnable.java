package com.metrics.daemon;

public class ClientFileRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println("End of current file!");
	}

}
