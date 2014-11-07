package main.jobs;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;


public class BaseTaskTest {

	@Test
	public void testBaseTask() throws Exception {
		BaseTask t = new BaseTask("some name","some argument"){
			
		};
		assertNotNull(t.getName());
		assertNotNull(t.getArgument());
	}

}
