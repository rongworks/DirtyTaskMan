package main.utils;

import main.Settings;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class Mail extends MultiPartEmail {
	public static final String SET_HOST_NAME = "host_name";
	public static final String SET_USER = "user";
	public static final String SET_PW = "pw"; //FIXME: insecure
	public static final String SET_FROM = "from";
	public static final String SET_TO = "to";
	
	
	public Mail(String from, String to, String subject, String msg) throws EmailException {
		// Create the email message
		setHostName(Settings.getInstance().getSetting(SET_HOST_NAME));
		setAuthentication(Settings.getInstance().getSetting(SET_USER), Settings.getInstance().getSetting(SET_PW));
		addTo(to);
		setFrom(from);
		setSubject(subject);
		setMsg(msg);
		
		System.out.println(describe());
		
	}
	
	public Mail(String subject, String msg) throws EmailException {
		this(Settings.getInstance().getSetting(SET_FROM),Settings.getInstance().getSetting(SET_TO),subject,msg);
	}

	public Mail attach(String path, String name) throws EmailException {
		// Create the attachment
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(path);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		//attachment.setDescription("Some description");
		attachment.setName(name);
		// add the attachment
		super.attach(attachment);
		return this;
	}
	
	public String describe(){
		return String.format("F: %s T: %s S: %s", getFromAddress(),getToAddresses().toString(),getSubject());
	}
}
