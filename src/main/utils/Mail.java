package main.utils;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mail extends MultiPartEmail {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	public Mail(String from, String to, String subject, String msg, String hostname, String user, String pw) throws EmailException {
		// Create the email message
		setHostName(hostname);
		setAuthentication(user, pw);
		addTo(to);
		setFrom(from);
		setSubject(subject);
		setMsg(msg);
		
		logger.debug("Mail created: "+describe());
		
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

	@Override
	public String send() throws EmailException {
		logger.info("Sending Mail "+describe());
		return super.send();
	}
}
