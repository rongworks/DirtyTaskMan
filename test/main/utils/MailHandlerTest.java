package main.utils;

import static org.junit.Assert.*;
import main.configuration.Settings;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class MailHandlerTest {
//TODO: test sending??, test attachments
	@Test
	public void testCreateMail() throws Exception {
		Settings settings = mock(Settings.class);
		when(settings.getSetting(MailHandler.FROM)).thenReturn("bla@example.com");
		when(settings.getSetting(MailHandler.TO)).thenReturn("blubb@example.com");
		when(settings.getSetting(MailHandler.HOSTNAME)).thenReturn("example.com");
		when(settings.getSetting(MailHandler.USER)).thenReturn("bla");
		when(settings.getSetting(MailHandler.PW)).thenReturn("something");
		
		MailHandler handler = new MailHandler(settings);
		String subject = "subject";
		String message = "message";
		Mail mail = handler.createMail(subject, message);
		assertEquals("Should have created a mail with given subject",mail.getSubject(),subject);
	}
	

}
