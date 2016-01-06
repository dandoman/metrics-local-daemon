package com.metrics.daemon.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.metrics.daemon.dao.ClientLogStateAccess;
import com.metrics.daemon.exception.LogNotFoundException;

public class ClientDirectoryParser {
	public static List<File> getFileBatch(String logDirectoryName) {
		List<File> earliestLogList = new ArrayList<>();
		try {
			File earliestLog = getEarliestLog(logDirectoryName);
			earliestLogList.add(earliestLog);
		} catch (LogNotFoundException lnfe) {
			//Return empty list if no logs are found
		}
		return earliestLogList;
	}
	
	public static File getEarliestLog(String logDirectoryName) throws LogNotFoundException {
		File dir = new File(logDirectoryName);
		List<File> allLogs = Arrays.<File>asList(dir.listFiles());
		//TODO need to check here if logs are in fact valid
		//TODO need to also check if given log directory is valid somehow
		File earliestLog = null;
		for(File log : allLogs) {
			if(log.isDirectory()) continue;
			String logName = log.getName();
			if(ClientLogValidation.validateLogName(logName)) {
				return log;
			}
		}
		if(earliestLog == null) {
			throw new LogNotFoundException("No logs found in " + logDirectoryName);
		}
		return earliestLog;
	}
	
	public static void deleteParsedLog(String logDirectoryName) {
		File earliestLog = null;
		try {
			earliestLog = getEarliestLog(logDirectoryName);
			Files.delete(earliestLog.toPath());
		} catch (LogNotFoundException lnfe) { 
			//Do nothing if no valid log is found
		}
		catch (IOException e) { 
			
		}
	}
}
