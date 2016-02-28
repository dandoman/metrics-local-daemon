package com.metrics.daemon.logic;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class ClientLogValidationTest {

	@Test
	@Ignore
	public void testValidateLogName() {
		String validLog = "service_log.2000-12-25-12-34";
		assertTrue(ClientLogValidation.validateLogName(validLog));
		
		String invalidLog = "serv_log.2000-12-25-12-34";
		assertFalse(ClientLogValidation.validateLogName(invalidLog));
		
		String invalidLogMonth = "service_log.2000-13-25-12-34";
		assertFalse(ClientLogValidation.validateLogName(invalidLogMonth));
		
		String invalidLogDay = "service_log.2000-12-32-12-34";
		assertFalse(ClientLogValidation.validateLogName(invalidLogDay));
		
		String invalidLogHour = "service_log.2000-12-25-25-34";
		assertFalse(ClientLogValidation.validateLogName(invalidLogHour));
		
		String invalidLogMinute = "service_log.2000-12-25-12-61";
		assertFalse(ClientLogValidation.validateLogName(invalidLogMinute));
		
		String invalidLogNegative = "service_log.2000-12-25--12-34";
		assertFalse(ClientLogValidation.validateLogName(invalidLogNegative));
	}

}
