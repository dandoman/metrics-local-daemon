package com.metrics.daemon;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Daemon {
	
	//should be own class
	Runnable minuteDaemon = new Runnable() {
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + " is Running Delayed Task.");
		}
	};
	
	//should be own class
	Runnable hourDaemon = new Runnable() {

		@Override
		public void run() {
			System.out.println("End of current file!");
		}
	};
	
	private final ScheduledExecutorService scheduledDaemonExecutor;
	
	public Daemon() {
		scheduledDaemonExecutor = Executors.newSingleThreadScheduledExecutor(); 
	}
	
	public void start() throws InterruptedException, ExecutionException {
		//daemon start
		scheduledDaemonExecutor.scheduleWithFixedDelay(minuteDaemon, 1, 1, TimeUnit.SECONDS);
		scheduledDaemonExecutor.scheduleAtFixedRate(hourDaemon, 4, 4, TimeUnit.SECONDS);
	}
	
	public void stop() {
		//daemon stop
		scheduledDaemonExecutor.shutdown();
		System.out.println("Is scheduledDaemonExecutor shutting down? " + scheduledDaemonExecutor.isShutdown());
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Daemon test = new Daemon();
		test.start();
		Thread.sleep(10000);
		test.stop();
	}

}
