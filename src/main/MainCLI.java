package main;
import it.sauronsoftware.cron4j.Scheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import main.collectors.CronFileCollector;
import main.utils.PresentationUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainCLI {
private static Logger logger = LoggerFactory.getLogger(MainCLI.class);

		public static void main(String[] args) {
			try {
				Settings.getInstance().init();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// Creates a Scheduler instance.
			Scheduler s = new Scheduler();
			logger.debug(String.format("Scheduler started %s",PresentationUtil.getCurrentTimeString()));
			s.addTaskCollector(new CronFileCollector("src/resources/jobs.txt"));
			// Starts the scheduler.
			s.start();
			// Will run for ten minutes.
			//s.launch(new BatchTask("BatchTask", "test.bat"));
			try {
				Thread.sleep(1000L * 60L * 10L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Stops the scheduler.
			s.stop();
		}
}
