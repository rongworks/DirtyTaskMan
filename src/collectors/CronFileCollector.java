package collectors;

import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.TaskCollector;
import it.sauronsoftware.cron4j.TaskTable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jobs.BaseTask;
import jobs.TaskFactory;

/**
 * Reads a cron-like File containing this pattern:
 * 
 * minute hour day month type name argument
 *  
 * @author Rong
 *
 */
public class CronFileCollector implements TaskCollector{
	public static final String regex = "([\\d|\\*|/|\\s]+)(\\w+\\s)(\\w+)(.*)";
	private TaskTable tasks;
	private String path;
	
	public CronFileCollector(String path){
		this.path = path;
	}
	
	public TaskTable getTasks() {
		if(tasks == null){
			try {
				readFile(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return tasks;
	}
	
	private void readFile(String path) throws IOException{
		tasks = new TaskTable();
		File f = new File(path);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		String line ="#";
		while(line != null){
			
			// Ignore commented line
			if(line.trim().startsWith("#")){
				line = br.readLine();
				continue;
			}
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(line);
			matcher.matches();
			System.out.println("Reading line:\n"+line+"\n");
			
			String schedule = matcher.group(1);
			String type = matcher.group(2).trim();
			String name = matcher.group(3).trim();
			String arg = matcher.group(4).trim();
			BaseTask task = TaskFactory.getTask(type, name, arg);
			SchedulingPattern sp = new SchedulingPattern(schedule);
			tasks.add(sp, task);
			System.out.println("Added Task "+task+" from line:\n"+line);
			line = br.readLine();
		}
	}

}
