package utils;

import org.apache.commons.mail.EmailException;

public class MailHandling {

	public static Mail createMail(String from, String to, String subject, String msg){
		try {
			Mail mail = new Mail(from,to,subject,msg);
			return mail;
		} catch (EmailException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Mail createMail(String subject, String msg){
		Mail mail = null;
		try {
			mail = new Mail(subject,msg);
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mail;
	}
}
