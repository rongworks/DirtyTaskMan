package main.listeners;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.sauronsoftware.cron4j.TaskExecutor;
import main.jobs.AlarmTask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//TODO: How to check for logs ?? Nothing asserting here
public class DirtyTaskExecutionListenerTest {
	TaskExecutor exe;
	DirtyTaskExecutionListener listener;
	
	@Before
	public void setUp(){
		exe = mock(TaskExecutor.class);
		listener = new DirtyTaskExecutionListener();
		AlarmTask testTask = new AlarmTask("alarm","ALARM");
		when(exe.getTask()).thenReturn(testTask);
	}
	
	@After
	public void tearDown(){
		exe = null;
	}
	
	@Test
	public void testCompletenessValueChanged() throws Exception {
		when(exe.getCompleteness()).thenReturn(0.5);
		listener.completenessValueChanged(exe, exe.getCompleteness());
		//TODO: what now?
	}

	@Test
	public void testStateChange(){
		listener.executionPausing(exe);
		listener.executionResuming(exe);
		listener.executionStopping(exe);
	}

	@Test
	public void testStatusMessageChanged() throws Exception {
		String status = "Some Status";
		listener.statusMessageChanged(exe, status);
		//??
	}

	@Test
	public void testExecutionTerminatedSuccessful() throws Exception {
		listener.executionTerminated(exe, null);
		//TODO: ??
	}

	@Test
	public void testExecutionTerminatedFailed() throws Exception {
		RuntimeException e = new RuntimeException("Failed misarably");
		listener.executionTerminated(exe, e);
		//TODO: ??
	}
	
	
	
}
