package main.manager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ApplicationManagerTest {

	ApplicationManager app;
	
	@Before
	public void setUp() throws Exception {
		app = new ApplicationManager();
	}

	@After
	public void tearDown() throws Exception {
		app = null;
	}

	@Test
	public void testInit() throws Exception {
		assertNotNull("Should initialize Logger",app.getLogger());
		assertNotNull("Should initialize Settings",app.getSettings());
		assertNotNull("Should initialize MailHandler",app.getMailHandler());
	}
	
	

}
