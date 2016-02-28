package com.metrics.daemon.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ClientLogValidation {
	public static boolean validateLogName(String logName) {
		String[] splitString = logName.split("\\.");
		if(splitString == null){
			return false;
		}
		if(splitString.length < 2){
			return false;
		}
		String name = splitString[0];
		String dateString = splitString[1];
		
		if(!name.equals("service_log")) return false;
		
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
	    sdf.setLenient(false);
	    try {
	        sdf.parse(dateString);
	        return true;
	    } catch (ParseException ex) {
	        return false;
	    }
	}
}
