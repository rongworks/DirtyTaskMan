package main.jobs;

import static org.junit.Assert.*;
import main.jobs.BaseTask;
import main.utils.TaskRepository;

import org.junit.Test;


public class BaseTaskTest {

	@Test
	public void testBaseTask() throws Exception {
		BaseTask t = new BaseTask("some name","some argument"){
			
		};
		assertNotNull(t.getName());
		assertNotNull(t.getArgument());
		assertTrue(TaskRepository.getInstance().getTasks().contains(t));
	}

}
