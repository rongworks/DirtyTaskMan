package main;

import it.sauronsoftware.cron4j.Scheduler;
import main.collectors.CronFileCollector;
import main.manager.ApplicationManager;
import main.utils.PresentationUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainCLI {
	private static Logger logger = LoggerFactory.getLogger(MainCLI.class);
	private static ApplicationManager applicationManager;

	public static ApplicationManager getApplicationManager() {
		if (applicationManager == null) {
			applicationManager = new ApplicationManager();
		}
		return applicationManager;
	}

	public static void main(String[] args) {
		boolean active = true; // run forever till terminated
		
		Scheduler s = new Scheduler();
		logger.debug(String.format("Scheduler started %s",
				PresentationUtil.getCurrentTimeString()));
		s.addTaskCollector(new CronFileCollector("src/resources/jobs.txt"));
		
		// Start the scheduler.
		s.start();
		while(active){
			// run run run
		}
		s.stop();
	}
}
