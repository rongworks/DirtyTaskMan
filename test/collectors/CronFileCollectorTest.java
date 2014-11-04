package collectors;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.TaskCollector;
import it.sauronsoftware.cron4j.TaskTable;

import java.io.BufferedReader;
import java.io.File;

import jobs.AlarmTask;
import jobs.BaseTask;
import jobs.BatchTask;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utils.TaskRepository;

public class CronFileCollectorTest {

	CronFileCollector collector;
	File testFile;

	String everyHourBatchPattern = "* * * * * BatchTask myBatchTask test.bat";
	String everyMinuteAlarmPattern = "*/1 * * * * AlarmTask myAlarmTask test.bat";
	String invalidSchedulePattern = "** * BatchTask test.bat";
	String invalidTypePattern = "*/2 * * * * SomeUnknownTask myUnknownTypeTask test.bat";

	@Before
	public void setUp() throws Exception {
		testFile = File.createTempFile("test", "txt");
		collector = new CronFileCollector(testFile.getAbsolutePath());
	}

	@After
	public void tearDown() throws Exception {
		testFile.delete();
	}

	@Test
	public void testInitializeFileReader() throws Exception {
		assertNotNull("TaskTable should have been initialized",
				collector.getTasks());
		assertNotNull("Reader should be initialized", collector.getReader());
		assertNotNull("TaskTable should be initialized", collector.getTasks());
		assertTrue("initial Tasks should be emtpy",
				collector.getTasks().size() == 0);
	}

	@Test
	public void testReadValidPatterns() throws Exception {
		BufferedReader mockReader = mock(BufferedReader.class);
		collector.setReader(mockReader);

		when(mockReader.readLine()).thenReturn("    ", // empty line
				everyHourBatchPattern, // Batch Task every hour
				everyMinuteAlarmPattern, // Alarm Task every Minute
				"# I'm just a comment", // comment line
				null); // eof
		int expectedTaskCount = 2;

		int amountParsedTasks = collector.getTasks().size();
		assertTrue("Expected to parse " + expectedTaskCount
				+ " Tasks, but got " + amountParsedTasks,
				amountParsedTasks == expectedTaskCount);
	}

	@Test
	public void testGenerateCronFile() throws Exception {
		int amountTasks = 2;

		Scheduler s = setUpScheduler();
		File f = collector.generateCronFile(s, "testCronGeneration");
		assertNotNull("CronFile should have been created", f);
		collector = new CronFileCollector(f.getAbsolutePath());
		int producedTasks = collector.getTasks().size();
		assertTrue("Should have produced " + amountTasks + " Tasks, got "
				+ producedTasks, amountTasks == producedTasks);
		s.stop();
	}

	@Test
	public void testGetTask() throws Exception {
		AlarmTask testTask = new AlarmTask("test", "test!");
		SchedulingPattern spat = new SchedulingPattern("* * * * *");
		collector.getTaskTable().add(spat, testTask);

		BaseTask compare = (BaseTask) collector.getTask(testTask.getName());
		assertEquals("Expected to get original Task back", testTask, compare);
		assertEquals(
				String.format("Expected %s, got %s", spat,
						collector.getSchedulingPattern(testTask.getName())),
				spat, collector.getSchedulingPattern(testTask.getName()));
	}

	private Scheduler setUpScheduler() {
		Scheduler s = new Scheduler();
		s.addTaskCollector(new TaskCollector() {

			public TaskTable getTasks() {
				String testPattern = "* * * * *";
				String testPattern2 = "* */4 3 1 *";
				BatchTask bt = new BatchTask("MyName", "MyCmd");
				AlarmTask at = new AlarmTask("MyName", "My Message");
				TaskTable tasks = new TaskTable();
				tasks.add(new SchedulingPattern(testPattern), bt);
				tasks.add(new SchedulingPattern(testPattern2), at);
				return tasks;
			}
		});
		s.start();
		return s;
	}

}
