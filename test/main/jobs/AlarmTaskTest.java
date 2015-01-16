package main.jobs;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.sauronsoftware.cron4j.TaskExecutionContext;
import it.sauronsoftware.cron4j.TaskExecutor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class AlarmTaskTest extends TaskTests{

	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testExecute() throws Exception {
		TaskExecutionContext mockCon = mock(TaskExecutionContext.class);
		TaskExecutor mockExecutor = mock(TaskExecutor.class);
		
		when(mockCon.getTaskExecutor()).thenReturn(mockExecutor);
		String testMessage = "Alarm!";
		AlarmTask alarm = new AlarmTask("MyAlarmTest", testMessage);
		alarm.setShowPopUp(false);
		alarm.execute(mockCon);
		assertTrue(alarm.getLog().contains(testMessage));
	}

}
