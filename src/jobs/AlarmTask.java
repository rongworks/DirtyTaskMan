package jobs;

import it.sauronsoftware.cron4j.TaskExecutionContext;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmTask extends BaseTask{

	private String message;
	private static final String DEFAULT_MESSAGE = "BOOM!";
	
	public AlarmTask(String message){
		this.message = message;
	}
	
	public AlarmTask() {
		message = DEFAULT_MESSAGE;
	}
	
	@Override
	public void execute(TaskExecutionContext exec) throws RuntimeException {
		super.execute(exec);
		String time = new SimpleDateFormat("HH:mm").format(new Date());
		log( String.format("%s -> %s", time, message ));
		exec.setStatusMessage(popStatus());
	}

	@Override
	public boolean supportsStatusTracking() {
		return true;
	}
	
	

	
}
