package com.metrics.daemon.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metrics.daemon.pojo.StagedMetric;

public class ClientLogParser {
	private final static ObjectMapper mapper = new ObjectMapper();
	
	public static List<StagedMetric> logToJson(List<String> rawLog) {
		List<StagedMetric> smList = new ArrayList<>();
		for(String metric : rawLog) {
			StagedMetric sm;
			try {
				sm = mapper.readValue(metric, StagedMetric.class);
				smList.add(sm);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// This will simply skip over invalid json
			}
		}
		return smList;
	}
}
