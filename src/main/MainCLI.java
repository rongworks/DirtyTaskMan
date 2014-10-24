package main;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.sauronsoftware.cron4j.Scheduler;
import jobs.AlarmTask;

public class MainCLI {

		public static void main(String[] args) {
			// Creates a Scheduler instance.
			Scheduler s = new Scheduler();
			String time = new SimpleDateFormat("HH:mm").format(new Date());
			System.out.println(String.format("Scheduler started %s",time));
			// Schedule a once-a-minute task.
			s.schedule("*/2 * * * *",new AlarmTask());
			// Starts the scheduler.
			s.start();
			// Will run for ten minutes.
			try {
				Thread.sleep(1000L * 60L * 10L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Stops the scheduler.
			s.stop();
		}
}
