package main.jobs;

import it.sauronsoftware.cron4j.TaskExecutionContext;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class AlarmTask extends BaseTask{

	private String message;
	private static final String DEFAULT_MESSAGE = "BOOM!";
	private boolean showPopUp = true;
	
	public AlarmTask(String name, String message){
		super(name,message);
		this.message = getArgument();
	}
	
	public AlarmTask() {
		super("Alarm",DEFAULT_MESSAGE);
	}
	
	@Override
	public void execute(TaskExecutionContext exec) throws RuntimeException {
		super.execute(exec);
		String time = new SimpleDateFormat("HH:mm").format(new Date());
		log( String.format("%s -> %s", time, message ));
		if(showPopUp){
			JOptionPane.showMessageDialog(null,
				    message,name,JOptionPane.INFORMATION_MESSAGE);
		}
		exec.setStatusMessage(popStatus());
	}

	@Override
	public boolean supportsStatusTracking() {
		return true;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isShowPopUp() {
		return showPopUp;
	}

	public void setShowPopUp(boolean showPopUp) {
		this.showPopUp = showPopUp;
	}
	
	

	
}
