package com.metrics.daemon;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Daemon {
	private final ScheduledExecutorService scheduledDaemonExecutor;
	private final TimeUnit timeUnit = TimeUnit.SECONDS;
	private final long logParseTime = 30;
	private final long fileChangeTime = 31;
	
	public Daemon() {
		scheduledDaemonExecutor = Executors.newSingleThreadScheduledExecutor(); 
	}
	
	public void start() throws InterruptedException, ExecutionException {
		//daemon start
		scheduledDaemonExecutor.scheduleWithFixedDelay(new ClientMinuteRunnable(), 
				logParseTime, logParseTime, timeUnit);
		scheduledDaemonExecutor.scheduleAtFixedRate(new ClientFileRunnable(), 
				fileChangeTime, fileChangeTime, timeUnit);
	}
	
	public void stop() {
		//daemon stop
		scheduledDaemonExecutor.shutdown();
		System.out.println("Schedule daemon shutdown successfully? " 
								+ scheduledDaemonExecutor.isShutdown());
	}

	/*
	 * Small daemon test
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Daemon test = new Daemon();
		test.start();
		Thread.sleep(190000);
		test.stop();
	}

}
