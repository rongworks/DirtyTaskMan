package main.jobs;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.sauronsoftware.cron4j.TaskExecutionContext;
import it.sauronsoftware.cron4j.TaskExecutor;

import java.io.File;
import java.io.IOException;

import main.utils.FileOperations;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExecutionTaskTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testExecuteWindowsBat() throws Exception {
		String testString = "echo hello world";
		File file = createTestFile(testString);
		TaskExecutionContext mockCon = mock(TaskExecutionContext.class);
		TaskExecutor mockExecutor = mock(TaskExecutor.class);
		
		when(mockCon.getTaskExecutor()).thenReturn(mockExecutor);
		
		ExecutionTask bt = new ExecutionTask("testBatch", file.getAbsolutePath());
		bt.execute(mockCon);
		file.delete();
		
		assertTrue(bt.getLog().contains(testString));
	}
	
	private File createTestFile(String content) throws IOException{
		File f = File.createTempFile("testBat", ".bat");
		FileOperations.writeFile(f.getAbsolutePath(),content);
		return f;
	}

	@Test
	public void testParseCommand() throws Exception {
		throw new RuntimeException("not yet implemented");
	}

}
