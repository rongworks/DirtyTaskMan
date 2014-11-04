package jobs;

import static org.junit.Assert.*;

import org.junit.Test;

import utils.TaskRepository;


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
