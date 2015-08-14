package com.metrics.daemon.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.metrics.daemon.pojo.ClientLogState;

public class ClientLogStateAccess {
	
	private final static String clientLogStateName = "./src/main/resources/file/clientState.txt";
	
	public static ClientLogState getCurrentState() {
		try(FileInputStream fileIn = new FileInputStream(clientLogStateName);
			ObjectInputStream in = new ObjectInputStream(fileIn)) {
			return (ClientLogState)in.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static void saveCurrentState(ClientLogState state) {
		try(FileOutputStream fileOut = new FileOutputStream(clientLogStateName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
			out.writeObject(state);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static boolean exists() {
		return new File(clientLogStateName).exists();
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
