package main.listeners;

import it.sauronsoftware.cron4j.TaskExecutor;
import it.sauronsoftware.cron4j.TaskExecutorListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import main.jobs.BaseTask;
import main.utils.MailHandling;

import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirtyTaskExecutionListener implements TaskExecutorListener{
	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
	public void completenessValueChanged(TaskExecutor executor,
			double completenessValue) {
		// TODO Auto-generated method stub
		
	}

	public void executionPausing(TaskExecutor executor) {
		// TODO Auto-generated method stub
		
	}

	public void executionResuming(TaskExecutor executor) {
		// TODO Auto-generated method stub
		
	}

	public void executionStopping(TaskExecutor executor) {
		// TODO Auto-generated method stub
		
	}

	public void executionTerminated(TaskExecutor executor, Throwable exception) {
		String time = new SimpleDateFormat("dd:MM:yy HH:mm").format(new Date());
		String exit = exception==null ? "Terminated successfully":exception.getMessage();
		logger.info(String.format("%s %s %s",executor.getTask().toString(),time,exit));
		if(exception != null){
			BaseTask t = (BaseTask) executor.getTask();
			try {
				logger.debug("Sending Mail");
				MailHandling.createMail(String.format(" Task %s failed! [%s]",t.getName(),time), t.getLog()).send();
			} catch (EmailException e) {
				e.printStackTrace();
			}
		}
	}

	public void statusMessageChanged(TaskExecutor executor, String statusMessage) {
		logger.info(String.format("%s %s %s",executor.getTask().toString(),new SimpleDateFormat("dd:MM:yy HH:mm").format(new Date()),statusMessage));
		
	}

}
