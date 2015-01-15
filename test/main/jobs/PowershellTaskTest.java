package main.jobs;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.sauronsoftware.cron4j.TaskExecutionContext;
import it.sauronsoftware.cron4j.TaskExecutor;

import org.junit.Test;


public class PowershellTaskTest extends TaskTests{

	@Test
	public void testPowershellTask() throws Exception {
		TaskExecutionContext mockCon = mock(TaskExecutionContext.class);
		TaskExecutor mockExecutor = mock(TaskExecutor.class);
		String testString = "\"I am working\"";
		
		when(mockCon.getTaskExecutor()).thenReturn(mockExecutor);
		PowershellTask task = new PowershellTask("TestPSTask", "echo "+testString);
		task.execute(mockCon);
		
		assertTrue(task.getLog().contains(testString));
	}

}
