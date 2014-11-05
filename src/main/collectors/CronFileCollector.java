package main.collectors;

import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskCollector;
import it.sauronsoftware.cron4j.TaskExecutor;
import it.sauronsoftware.cron4j.TaskTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.jobs.BaseTask;
import main.jobs.TaskFactory;
import main.utils.FileOperations;
import main.utils.PresentationUtil;
import main.utils.TaskRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads a cron-like File containing this pattern:
 * 
 * minute hour day month JavaClass task_name argument <br>
 * 
 * task_name must not contain whitespaces! Everything after task_name is read as
 * an argument (whitespaces allowed)
 * 
 * @author Rong
 *
 */
public class CronFileCollector implements TaskCollector {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public static final String regex = "([\\d|\\*|/|\\s]+)(\\w+\\s)(\\w+)(.*)";
	private TaskTable taskTable;
	private String path;
	private BufferedReader reader;
	private int lineCount;

	public CronFileCollector(String path) {
		this.path = path;
		taskTable = new TaskTable();
		try {
			initializeFileReader(path);
		} catch (IOException e) {
			logger.error("Could not initialize TaskCollector", e);
		}
	}

	public TaskTable getTasks() {
		taskTable = new TaskTable();
		try {
			boolean eof = false;
			while (!eof) {
				String line = readNextLine();
				if (line != null) {
					parseLineToTaskTable(line);
				} else {
					eof = true;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return taskTable;
	}

	/**
	 * Read next line from {@link BufferedReader}, ignoring comments and empty
	 * lines At EOF, reader is closed and null returned
	 * 
	 * @return read line or null at EOF
	 * @throws IOException
	 */
	private String readNextLine() throws IOException {
		String line = reader.readLine();
		// eof, reset the reader for next iteration and return null for current
		// iteration
		if (line == null) {
			initializeFileReader(path);
			return null;
		}
		if (line.trim().startsWith("#") || line.trim().isEmpty()) {
			return readNextLine();
		}
		return line;
	}

	/**
	 * Convert a parsed line to an instance extending BaseTask and add it to the
	 * {@link TaskTable}
	 * 
	 * @param line
	 */
	private void parseLineToTaskTable(String line) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);
		if (matcher.matches()) {
			logger.debug("Reading line:\n" + line + "\n");
			String schedule = matcher.group(1);
			String type = matcher.group(2).trim();
			String name = matcher.group(3).trim();
			String arg = matcher.group(4).trim();
			BaseTask task = TaskFactory.getTask(type, name, arg);
			SchedulingPattern sp = new SchedulingPattern(schedule);
			taskTable.add(sp, task);
			logger.debug("Added Task " + task + " from line:\n" + line);
		} else {
			throw new RuntimeException(String.format(
					"Could not read line %d :\n %s |\n %s", lineCount, line,
					matcher.toString()));
		}
	}

	private void initializeFileReader(String path) throws IOException {
		File f = new File(path);
		FileReader fr = new FileReader(f);
		reader = new BufferedReader(fr);
		lineCount = 0;
	}

	/**
	 * Collect all tasks from a given scheduler and generate a cron-like File <br>
	 * Only applicable on Classes that extend {@link BaseTask}!
	 * TODO: REFACTOR
	 * @param scheduler
	 *            - {@link Scheduler} whose tasks should be saved
	 * @param name
	 *            - Destination for the resulting File
	 * @return Generated file
	 */
	public File generateCronFile(Scheduler scheduler, String name) {
		String fileContent = "# " + name
				+ "\n # auto generated cron-task File \n # created_at: "
				+ PresentationUtil.getCurrentTimeString() + "\n";
		for (TaskCollector collector : scheduler.getTaskCollectors()) {
			TaskTable table = collector.getTasks();
			for (int i = 0; i < table.size(); i++) {
				Task t = table.getTask(i);
				if (t instanceof BaseTask) {
					BaseTask bt = (BaseTask) t;
					String taskName = bt.getName();
					String pattern = table.getSchedulingPattern(i).toString();
					String arg = bt.getArgument();
					String className = bt.getClass().getSimpleName();
					fileContent += String.format("%s %s %s %s \n", pattern,
							className, taskName, arg);
				} else {
					logger.error("Could not convert Task "+t+"-"+t.getClass().getName());
				}
			}
		}
		return FileOperations.writeFile(name, fileContent);
	}

	public TaskTable getTaskTable(){
		return taskTable;
	}
	
	
	public Task getTask(String name) {
		return taskTable.getTask(getIndex(name));
	}

	public SchedulingPattern getSchedulingPattern(String taskName) {
		return taskTable.getSchedulingPattern(getIndex(taskName));
	}

	public int getIndex(String taskName) {
		int index = 0;
		while (index < taskTable.size()) {
			Task t = taskTable.getTask(index);
			if (t instanceof BaseTask) {
				if (((BaseTask) t).getName().equals(taskName)) {
					return index;
				}
			}
			index++;
		}
		return -1;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

	public int getLineCount() {
		return lineCount;
	}

	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}

	public void setTaskTable(TaskTable taskTable) {
		this.taskTable = taskTable;
	}

	
}
