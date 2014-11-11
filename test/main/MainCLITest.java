package main;

import static org.junit.Assert.*;

import org.junit.Test;


public class MainCLITest {

	@Test
	public void testGetApplicationManager() throws Exception {
		assertNotNull("Should Create ApplicationManager or return running AppManager",MainCLI.getApplicationManager());
	}

	@Test
	public void testSetUpScheduler() throws Exception {
		MainCLI.setUpScheduler();
		assertNotNull("should initialize Scheduler",MainCLI.getScheduler());
		assertNotNull("should initialize CronFileCollector",MainCLI.getCronCollect());
	}

}
