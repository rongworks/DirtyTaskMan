package listeners;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.sauronsoftware.cron4j.TaskExecutor;
import it.sauronsoftware.cron4j.TaskExecutorListener;

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
	}

	public void statusMessageChanged(TaskExecutor executor, String statusMessage) {
		System.out.println(String.format("%s %s %s",executor.getTask().toString(),new SimpleDateFormat("dd:MM:yy HH:mm").format(new Date()),statusMessage));
		
	}

}
