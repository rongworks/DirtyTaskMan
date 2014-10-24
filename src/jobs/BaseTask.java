package jobs;

import java.util.concurrent.LinkedBlockingQueue;

import listeners.DirtyTaskExecutionListener;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;

public abstract class BaseTask extends Task{

	private String log = "";
	private LinkedBlockingQueue<String> logQueue = new LinkedBlockingQueue<String>();
	
	public String getLog(){
		return log;
	}
	
	@Override
	public void execute(TaskExecutionContext context) throws RuntimeException {
		System.out.println("Yay, starting.");
		context.getTaskExecutor().addTaskExecutorListener(new DirtyTaskExecutionListener());
	}
	
	public String popStatus(){
		if(logQueue.isEmpty()){
			return "RUNNING";
		}
		try {
			return logQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "RUNNING";
	}
	
	public void log(String message){
		log += message;
		logQueue.add(message);
	}
}
