package main.jobs;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.sauronsoftware.cron4j.TaskExecutionContext;
import it.sauronsoftware.cron4j.TaskExecutor;

import java.io.File;

import org.junit.Test;


public class BaseTaskTest {

	@Test
	public void testBaseTask() throws Exception {
		BaseTask t = new BaseTask("someName","some argument"){
			
		};
		assertNotNull(t.getName());
		assertNotNull(t.getArgument());
	}

	@Test
	public void testExecute(){
		String taskName = "someName";
		TaskExecutionContext mockCon = mock(TaskExecutionContext.class);
		TaskExecutor mockExecutor = mock(TaskExecutor.class);
		
		when(mockCon.getTaskExecutor()).thenReturn(mockExecutor);
		BaseTask t = new BaseTask(taskName,"some argument"){};
		t.execute(mockCon);
	}
}
