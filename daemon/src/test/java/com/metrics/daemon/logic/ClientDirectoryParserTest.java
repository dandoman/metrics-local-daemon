package com.metrics.daemon.logic;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import com.metrics.daemon.exception.LogNotFoundException;

public class ClientDirectoryParserTest {
	@Rule public ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	@Test
	public void testGetFileBatch() {
		fail("Not yet implemented");
	}

	@Test(expected = LogNotFoundException.class)
	public void testGetEarliestLog() throws IOException, LogNotFoundException {
		String tmp = System.getProperty("java.io.tmpdir");
		File f1 = new File(tmp + "/service_log.2016-01-06-11-24");
		PrintWriter out = new PrintWriter(f1);
		String f1Json = "{\"applicationName\":\"app_test\",\"operation\":\"op_test\",\"marketplace\":\"market_test\",\"hostName\":\"host_test\",\"timeStamp\":1452097448634,\"metricName\":\"day\",\"value\":240.09651819965993}\n" + 
						"{\"applicationName\":\"app_test\",\"operation\":\"op_test\",\"marketplace\":\"market_test\",\"hostName\":\"host_test\",\"timeStamp\":1452097458713,\"metricName\":\"day\",\"value\":972.7486098835537}\n" + 
						"{\"applicationName\":\"app_test\",\"operation\":\"op_test\",\"marketplace\":\"market_test\",\"hostName\":\"host_test\",\"timeStamp\":1452097468714,\"metricName\":\"day\",\"value\":42.069956013471185}\n" + 
						"{\"applicationName\":\"app_test\",\"operation\":\"op_test\",\"marketplace\":\"market_test\",\"hostName\":\"host_test\",\"timeStamp\":1452097478715,\"metricName\":\"day\",\"value\":54.848197510061205}\n" + 
						"{\"applicationName\":\"app_test\",\"operation\":\"op_test\",\"marketplace\":\"market_test\",\"hostName\":\"host_test\",\"timeStamp\":1452097488716,\"metricName\":\"day\",\"value\":432.00121981748316}\n";
		out.write(f1Json);
		out.close();
		try {
			File earliestLog = ClientDirectoryParser.getEarliestLog(tmp);
			assertEquals(earliestLog.getName(), "service_log.2016-01-06-11-24");
		} catch (LogNotFoundException e) {
		}
		
		Files.delete(f1.toPath());
		ClientDirectoryParser.getEarliestLog(tmp);
	}

	@Ignore
	@Test
	public void testDeleteParsedLog() {
		fail("Not yet implemented");
	}

}
