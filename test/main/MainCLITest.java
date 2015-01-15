package main;

import static org.junit.Assert.*;

import org.junit.Test;

public class MainCLITest {

	@Test
	public void testGetApplicationManager() throws Exception {
		assertNotNull(
				"Should Create ApplicationManager or return running AppManager",
				MainCLI.getApplicationManager());
	}

	@Test
	public void testSetUpScheduler() throws Exception {
		MainCLI.setUpScheduler();
		assertNotNull("should initialize Scheduler", MainCLI.getScheduler());
		assertNotNull("should initialize CronFileCollector",
				MainCLI.getCronCollect());
	}

	@Test
	public void testApplicationFlow() {
		// start Application
		Thread startT = new Thread() {
			public void run() {
				System.out.println("Starting DirtyTaskMan");
				MainCLI.main(new String[] { "start" });
			}
		};
		startT.start();
		
		try {
		    Thread.sleep(2000);         
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		assertTrue(MainCLI.isActive());
		assertTrue(MainCLI.getScheduler().isStarted());
		
		try {
		    Thread.sleep(2000);         
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		
		// stop Application
		Thread stopT = new Thread() {
			public void run() {
				System.out.println("Stopping DirtyTaskMan");
				MainCLI.main(new String[] { "stop" });
			}
		};
		stopT.start();
		
		try {
		    Thread.sleep(2000);         
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		assertFalse(MainCLI.isActive());
		assertFalse(MainCLI.getScheduler().isStarted());
		
		try {
			startT.join();
			stopT.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
