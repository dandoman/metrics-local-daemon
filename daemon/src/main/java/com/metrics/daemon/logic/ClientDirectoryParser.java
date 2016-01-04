package com.metrics.daemon.logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientDirectoryParser {
	public static List<File> getFileBatch(String logDirectoryName) {
		File dir = new File(logDirectoryName);
		List<File> allLogs = Arrays.<File>asList(dir.listFiles());
		List<File> earliestLog = new ArrayList<>();
		earliestLog.add(allLogs.get(allLogs.size()-1));
		return earliestLog;
	}
}
