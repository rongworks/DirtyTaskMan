package main.utils;

import main.configuration.Settings;

import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailHandler {

	Settings settings;
	final static String HOSTNAME = "host_name";
	final static String USER = "user";
	final static String PW = "pw";
	final static String FROM = "from";
	final static String TO = "to";
	Logger logger = LoggerFactory.getLogger(getClass());
	
	public MailHandler(Settings settings){
		this.settings = settings;
	}
	
	public Mail createMail(String from, String to, String subject, String msg) {
		String hostname = settings.getSetting(HOSTNAME);
		String user = settings.getSetting(USER);
		String pw = settings.getSetting(PW);

		try {
			Mail mail = new Mail(from, to, subject, msg, hostname, user, pw);
			return mail;
		} catch (EmailException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Mail createMail(String subject, String msg) {
		String from = settings.getSetting(FROM);
		String to = settings.getSetting(TO);
		return createMail(from, to, subject, msg);
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
