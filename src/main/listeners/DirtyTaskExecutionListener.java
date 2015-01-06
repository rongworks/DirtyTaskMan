package main.listeners;

import it.sauronsoftware.cron4j.TaskExecutor;
import it.sauronsoftware.cron4j.TaskExecutorListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import main.jobs.BaseTask;
import main.utils.FileOperations;
import main.utils.MailHandler;
import main.utils.PresentationUtil;

import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirtyTaskExecutionListener implements TaskExecutorListener {
	private Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
	MailHandler mailHandler = null;

	public DirtyTaskExecutionListener() {

	}

	public DirtyTaskExecutionListener(MailHandler mailHandler) {
		this.mailHandler = mailHandler;
	}

	public void completenessValueChanged(TaskExecutor executor,
			double completenessValue) {
		logger.info(String.format("%s %s %s ", executor.getTask().toString(),
				PresentationUtil.getCurrentTimeString(), "" + completenessValue
						* 100 + "%"));
	}

	public void executionPausing(TaskExecutor executor) {
		logger.info(String.format("%s %s %s", executor.getTask().toString(),
				PresentationUtil.getCurrentTimeString(), "PAUSED"));
	}

	public void executionResuming(TaskExecutor executor) {
		logger.info(String.format("%s %s %s", executor.getTask().toString(),
				PresentationUtil.getCurrentTimeString(), "RESUMED"));

	}

	public void executionStopping(TaskExecutor executor) {
		logger.info(String.format("%s %s %s", executor.getTask().toString(),
				PresentationUtil.getCurrentTimeString(), "STOPPED"));
	}

	public void executionTerminated(TaskExecutor executor, Throwable exception) {
		String time = PresentationUtil.getCurrentTimeString();
		String exit = exception == null ? "Terminated successfully" : exception
				.getMessage();
		logger.info(String.format("%s %s %s", executor.getTask().toString(),
				time, exit));
		BaseTask t = (BaseTask) executor.getTask();
		// store log file until next run
		try{
			FileOperations.writeFile("log/"+t.getName()+".log", t.getLog());
		}catch(Exception e){
			logger.error("Could not write log file for "+t.getName()+"\n"+e.getMessage());
		}
		if (exception != null) {
			try {
				if (mailHandler != null) {
					mailHandler.createMail(
							String.format(" Task %s failed! [%s]", t.getName(),
									time), t.getLog()).send();
				} else {
					logger.info("Mail delivery deactivated, not sending mails");
				}
			} catch (EmailException e) {
				e.printStackTrace();
			}
		}
	}

	public void statusMessageChanged(TaskExecutor executor, String statusMessage) {
		logger.info(String.format("%s %s %s", executor.getTask().toString(),
				PresentationUtil.getCurrentTimeString(), statusMessage));

	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public MailHandler getMailHandler() {
		return mailHandler;
	}

	public void setMailHandler(MailHandler mailHandler) {
		this.mailHandler = mailHandler;
	}

}
