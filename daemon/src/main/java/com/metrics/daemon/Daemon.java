package com.metrics.daemon;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;

import com.metrics.daemon.dao.ClientLogStateAccess;


public class Daemon {
	private final ScheduledExecutorService scheduledDaemonExecutor;
	private final TimeUnit timeUnit = TimeUnit.SECONDS;
	private final long logParseTime;
	private final String logDirectory;
	private final String apiKey;
	
	public Daemon(long logParseTime, String logDirectory, String apiKey) {
		this.logParseTime = logParseTime;
		this.logDirectory = logDirectory;
		this.apiKey = apiKey;
		scheduledDaemonExecutor = Executors.newSingleThreadScheduledExecutor();
	}
	
	//Default daemon runs every 60 seconds
	//Should be 5 minutes eventually
	public Daemon(String logDirectory, String apiKey) {
		 this(60, logDirectory, apiKey);
	}
	
	public void start() throws InterruptedException, ExecutionException {
		long initialTime = 60 - DateTime.now().getSecondOfMinute();
		//TODO use clientlogstate access for optimized checking of file names
		ClientLogStateAccess.init(logDirectory);
		scheduledDaemonExecutor.scheduleWithFixedDelay(new ClientMinuteRunnable(logDirectory, apiKey), 
				initialTime, logParseTime, timeUnit);
	}
	
	public void stop() {
		scheduledDaemonExecutor.shutdown();
		System.out.println("Schedule daemon shutdown successfully? " 
								+ scheduledDaemonExecutor.isShutdown());
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		if(args.length != 1) {
			System.out.println("Need to pass in the api key");
			System.exit(0);
		}
		String apiKey = args[0];
		System.out.println("Staring up daemon for api key: " + apiKey);
		Daemon d = new Daemon(10, "/var/metrics", apiKey);
		d.start();
	}
}
