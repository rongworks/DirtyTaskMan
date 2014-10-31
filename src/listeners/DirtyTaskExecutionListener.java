package listeners;

import it.sauronsoftware.cron4j.TaskExecutor;
import it.sauronsoftware.cron4j.TaskExecutorListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.mail.EmailException;

import utils.MailHandling;
import jobs.BaseTask;

public class DirtyTaskExecutionListener implements TaskExecutorListener{

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
		System.out.println(String.format("%s %s %s",executor.getTask().toString(),time,exit));
		if(exception != null){
			BaseTask t = (BaseTask) executor.getTask();
			try {
				System.out.println("Sending Mail");
				MailHandling.createMail(String.format(" Task %s failed! [%s]",t.getName(),time), t.getLog()).send();
			} catch (EmailException e) {
				e.printStackTrace();
			}
		}
	}

	public void statusMessageChanged(TaskExecutor executor, String statusMessage) {
		System.out.println(String.format("%s %s %s",executor.getTask().toString(),new SimpleDateFormat("dd:MM:yy HH:mm").format(new Date()),statusMessage));
		
	}

}
