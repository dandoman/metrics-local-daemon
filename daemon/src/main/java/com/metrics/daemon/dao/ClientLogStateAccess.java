package com.metrics.daemon.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.joda.time.DateTime;

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
	 */
	static {
		final File previousLogState = new File(clientLogStateName);
		if (previousLogState.exists()) {
			currentState = readCurrentState();
		} else {
			DateTime currentDate = DateTime.now();
			currentState = new ClientLogState();
			writeCurrentState(0, "service_log." + currentDate.toString("Y-M-d-H"), currentDate);
		}
	}

	@Data
	@NoArgsConstructor
	private static class ClientLogState implements Serializable {
		private static final long serialVersionUID = 6514393267674187932L;
		//TODO
		//turn currentFileName into file object so we can have access to file path
		private String currentFileName;
		private long currentLineNumber;
		private DateTime currentDate;
	}
	
	/*
	 * Checks to see if we have a valid old directory or not.
	 * If directories dont match, then we start in new directory.
	 */
	public static void init(String logDirectory) {
		File newFileLogDirectory = new File(logDirectory);
		File oldFileLogDirectory = new File(getCurrentFileName());
		if(!newFileLogDirectory.getPath().equals(oldFileLogDirectory.getPath())) {
			DateTime now = DateTime.now();
			writeCurrentState(0, 
							  logDirectory + "service_log." + now.toString("Y-M-d-H"),
							  now);
		}
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