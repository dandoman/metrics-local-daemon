package com.metrics.daemon;

import java.util.Date;

import com.metrics.daemon.dao.ClientLogStateAccess;

public class ClientFileRunnable implements Runnable {
	
	@Override
	public void run() {
		Date newDate = new Date();
		//TODO
		//Could store filepath/name in an enum
		String newFileName = "./src/main/resources/file/" + newDate.getTime() + ".txt";
		ClientLogStateAccess.saveCurrentState(0, newFileName);
		System.out.println("New file name is " + newFileName);
	}

}
