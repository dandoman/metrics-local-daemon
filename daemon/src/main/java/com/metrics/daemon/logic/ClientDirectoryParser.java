package com.metrics.daemon.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientDirectoryParser {
	public static List<File> getFileBatch(String logDirectoryName) {
		File dir = new File(logDirectoryName);
		List<File> allLogs = Arrays.<File>asList(dir.listFiles());
		return fiveMinuteAggregation(allLogs);
	}
	
	//TODO Take appropriate five minute log aggregations
	//Currently this just take this 3 latest files in the given log Directory
	private static List<File> fiveMinuteAggregation(List<File> allLogs) {
		List<File> firstFiveMinutes = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
			firstFiveMinutes.add(allLogs.get(i));
		}
		return firstFiveMinutes;
	}
}
