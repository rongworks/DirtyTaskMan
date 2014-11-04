package jobs;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.PresentationUtil;
import utils.TaskRepository;
import listeners.DirtyTaskExecutionListener;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;

public abstract class BaseTask extends Task{
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String log = ""; // complete log since execution of Task
	private LinkedBlockingQueue<String> logQueue = new LinkedBlockingQueue<String>(); // Log-Queue for querying log-messages
	public String name; // Name of the Task, used in logs
	public String argument; // Execution context, e.g. message to display, cmd to execute etc.
	
	public BaseTask(String name, String argument){
		this.name = name;
		this.argument = argument;
		TaskRepository.getInstance().addTask(this);
	}
	
	public String getName(){
		return name;
	}
	
	public String getArgument(){
		return argument;
	}
	
	/**
	 * Get the complete log of this Task
	 * 
	 * @return log
	 */
	public String getLog(){
		return log;
	}
	
	/**
	 * Execution called by Scheduler
	 */
	@Override
	public void execute(TaskExecutionContext context) throws RuntimeException {
		log = "";
		logger.debug("Starting Task "+getName());
		context.getTaskExecutor().addTaskExecutorListener(new DirtyTaskExecutionListener());
	}
	
	/**
	 * Get last log message written for this Task
	 * Warning: Will delete this message after retrieval!!
	 * @return last log message
	 */
	public synchronized String popStatus(){
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
	
	/**
	 * Write a log message
	 * Will append the message to the log and to the log-Queue
	 * @param message
	 */
	public synchronized void log(String message){
		log += String.format("[%s] [%s] %s \n",getName(), PresentationUtil.getCurrentTimeString(),message);
		logQueue.add(message);
	}
	
	@Override
	public String toString(){
		return getName();
	}
}
