package com.metrics.daemon.dao;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import com.metrics.daemon.Daemon;

public class ClientLogStateAccessTest {

	@Test
	public void testInit() {
		System.out.println(System.getProperty("java.io.tmpdir"));
		ClientLogStateAccess.init("/tmp");
		assertEquals(ClientLogStateAccess.getCurrentFileName(), "service_log.2015-12-31-13-26");
	}

	@Ignore
	@Test
	public void testGetCurrentLineNumber() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetCurrentFileName() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testGetCurrentDate() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testExists() {
		fail("Not yet implemented");
	}

	@Ignore
	@Test
	public void testWriteCurrentState() {
		fail("Not yet implemented");
	}

}
