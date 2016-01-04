package com.metrics.daemon.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.joda.time.DateTime;

import com.metrics.daemon.exception.InvalidLogException;
import com.metrics.daemon.exception.LogNotFoundException;
import com.metrics.daemon.logic.ClientLogValidation;

import lombok.Data;
import lombok.NoArgsConstructor;

public class ClientLogStateAccess {

	private final static String clientLogStateName = 
			System.getProperty("java.io.tmpdir") + "/" + "clientState.dat";

	private final static ClientLogState currentState;

	// TODO
	// Store all names maybe in an enum somewhere as strings.
	// Specifically for log name, client log state name and maybe the general app directory.
	// Keep track of name of service log and where it's going. 
	/*
	 * Initializing the daemon state .dat file.
	 * This ensures that clientLogState will be initialized regardless of what exists.
	 */
	static {
		final File previousLogState = new File(clientLogStateName);
		if (previousLogState.exists()) {
			currentState = readCurrentState();
		} else {
			DateTime currentDate = DateTime.now();
			currentState = new ClientLogState();
			writeCurrentState(0, "service_log." + currentDate.toString("Y-M-d-H-m"), currentDate);
		}
	}

	@Data
	@NoArgsConstructor
	private static class ClientLogState implements Serializable {
		private static final long serialVersionUID = 6514393267674187932L;
		//TODO
		//turn currentFileName into file object so we can have access to file path
		private long currentLineNumber;
		private String currentFileName;
		private DateTime currentDate;
	}
	
	/*
	 * Checks to see if we have a valid old directory or not.
	 * If directories dont match, then we start in new directory.
	 */
	public static void init(String logDirectory) throws LogNotFoundException {
		String currentLogName = getCurrentLogName(logDirectory);
		String dateString = currentLogName.split("\\.")[1];
		String[] arrayDate = dateString.split("\\-");
		DateTime date = new DateTime(Integer.parseInt(arrayDate[0]),
									 Integer.parseInt(arrayDate[1]),
									 Integer.parseInt(arrayDate[2]),
									 Integer.parseInt(arrayDate[3]),
									 Integer.parseInt(arrayDate[4]));
		writeCurrentState(0, currentLogName, date);
	}
	
	private static String getCurrentLogName(String newLogDirectory) throws LogNotFoundException {
		File logDirectory = new File(newLogDirectory);
		File[] allLogs = logDirectory.listFiles();
		File oldestLog = allLogs[allLogs.length-1];
		String oldestLogName = oldestLog.getName();
		
		if(!oldestLog.exists()) {
			LogNotFoundException lgfe = new LogNotFoundException(oldestLog.getAbsolutePath() + " is not a valid log.");
			throw lgfe;
		}
		
		if(!ClientLogValidation.validateLogName(oldestLogName)) {
			//TODO handle this exception
			//throw new InvalidLogException(oldestLogName + " is not a valid log name.");
		}
		
		return oldestLogName;
	}

	// Could replace with getCurrentState to get from memory
	public static long getCurrentLineNumber() {
		return currentState.getCurrentLineNumber();
	}

	// Could replace with getCurrentState to get from memory
	public static String getCurrentFileName() {
		return currentState.getCurrentFileName();
	}

	public static DateTime getCurrentDate() {
		return currentState.getCurrentDate();
	}
	
	public static boolean exists() {
		return new File(clientLogStateName).exists();
	}

	public static void writeCurrentState(long newLineNumber,
			String newFileName, DateTime newDate) {
		currentState.setCurrentLineNumber(newLineNumber);
		currentState.setCurrentFileName(newFileName);
		currentState.setCurrentDate(newDate);
		try (FileOutputStream fileOut = new FileOutputStream(clientLogStateName);
				ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(currentState);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static ClientLogState readCurrentState() {
		try (FileInputStream fileIn = new FileInputStream(clientLogStateName);
				ObjectInputStream in = new ObjectInputStream(fileIn)) {
			return (ClientLogState) in.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

}