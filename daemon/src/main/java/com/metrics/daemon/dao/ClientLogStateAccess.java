package com.metrics.daemon.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ClientLogStateAccess {
	
	private final static String clientLogStateName = "./src/main/resources/file/clientState.txt";
	
	private final static ClientLogState currentState = 
			new ClientLogState("./src/main/resources/file/clientmetricsample.txt", 0);
	
	@Data
	@AllArgsConstructor
	private static class ClientLogState implements Serializable {
		private static final long serialVersionUID = 6514393267674187932L;
		private String currentFileName;
		private long currentLineNumber;
	}
	
	//Could replace with getCurrentState to get from memory
	public static long getCurrentLineNumber() {
		return currentState.getCurrentLineNumber();
	}
	
	//Could replace with getCurrentState to get from memory
	public static String getCurrentFileName() {
		return currentState.getCurrentFileName();
	}
	
	public static void saveCurrentState(long newLineNumber,
										String newFileName) {
		currentState.setCurrentLineNumber(newLineNumber);
		currentState.setCurrentFileName(newFileName);
		try(FileOutputStream fileOut = new FileOutputStream(clientLogStateName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(currentState);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static boolean exists() {
		return new File(clientLogStateName).exists();
	}
	 
	private static ClientLogState getCurrentState() {
		try(FileInputStream fileIn = new FileInputStream(clientLogStateName);
			ObjectInputStream in = new ObjectInputStream(fileIn)) {
			return (ClientLogState)in.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/*
	 * Quick test to see if read/write works
	 */
	public static void main(String[] args) {
		//ClientLogState oldState = new ClientLogState();
		//oldState.setCurrentLineNumber(2);
		//ClientLogStateAccess.saveCurrentState(oldState);
		ClientLogState newState = ClientLogStateAccess.getCurrentState();
		System.out.println(newState);
	}
}
