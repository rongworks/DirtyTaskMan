package main.manager;

import main.configuration.FileSettings;
import main.configuration.Settings;
import main.utils.MailHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationManager {
	Logger logger = LoggerFactory.getLogger(getClass());
	Settings settings;
	MailHandler mailHandler;
	
	public ApplicationManager(){
		init();
	}
	
	public void init(){
		String path = "config/settings.properties";
		settings = new FileSettings(path);
		settings.load();
		mailHandler = new MailHandler(settings);
		logger.debug("Settings and MailHandler initialized.");
	}
	
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public Settings getSettings() {
		return settings;
	}
	public void setSettings(Settings settings) {
		this.settings = settings;
	}
	public MailHandler getMailHandler() {
		return mailHandler;
	}
	public void setMailHandler(MailHandler mailHandler) {
		this.mailHandler = mailHandler;
	}
	
	
}
