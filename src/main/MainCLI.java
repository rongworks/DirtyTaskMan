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
	private static Scheduler scheduler;
	private static CronFileCollector cronCollect;
	private static boolean active;
	
	public static ApplicationManager getApplicationManager() {
		if (applicationManager == null) {
			applicationManager = new ApplicationManager();
		}
		return applicationManager;
	}

	public static void main(String[] args) {
		active = true; // run forever till terminated
		setUpScheduler();
		// Start the scheduler.
		scheduler.start();
		while(active){
			// run run run
		}
		scheduler.stop();
	}
	
	public static void setUpScheduler(){
		scheduler = new Scheduler();
		//TODO: Make CronFiles paths arguments
		cronCollect = new CronFileCollector("src/resources/jobs.txt");
		scheduler.addTaskCollector(cronCollect);
		logger.debug(String.format("Scheduler started %s",
				PresentationUtil.getCurrentTimeString()));
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		MainCLI.logger = logger;
	}

	public static Scheduler getScheduler() {
		return scheduler;
	}

	public static void setScheduler(Scheduler scheduler) {
		MainCLI.scheduler = scheduler;
	}

	public static CronFileCollector getCronCollect() {
		return cronCollect;
	}

	public static void setCronCollect(CronFileCollector cronCollect) {
		MainCLI.cronCollect = cronCollect;
	}

	public static boolean isActive() {
		return active;
	}

	public static void setActive(boolean active) {
		MainCLI.active = active;
	}

	public static void setApplicationManager(ApplicationManager applicationManager) {
		MainCLI.applicationManager = applicationManager;
	}
}
