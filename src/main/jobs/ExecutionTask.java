package main.jobs;

import it.sauronsoftware.cron4j.TaskExecutionContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExecutionTask extends BaseTask {

	private String[] cmdParsed;

	public ExecutionTask(String name, String cmd) {
		super(name,cmd);
		cmdParsed = parseCommand(cmd);
	}

	/**
	 * Execute a file or command as a System Process
	 * The STDIN and STDERR are redirected to the Tasks log
	 * @throws RuntimeException for ExitCode != 0
	 * TODO: Enhance Exception Handling
	 * TODO: Test on Linux
	 */
	@Override
	public void execute(TaskExecutionContext context) throws RuntimeException {
		super.execute(context);
		log("executing command: "+Arrays.toString(cmdParsed));
		getLogger().debug("executing command: "+Arrays.toString(cmdParsed));
		try{
		  ProcessBuilder processBuilder = new ProcessBuilder(cmdParsed);
		  processBuilder.redirectErrorStream(true);
		  Process process = processBuilder.start();
		
		  InputStream input = process.getInputStream();
		  InputStreamReader sr = new InputStreamReader(input);
		  BufferedReader reader = new BufferedReader(sr);
		  process.getOutputStream().close();
		  
		  String line;
		  while ((line = reader.readLine ()) != null) {
	        log(line);
	        context.setStatusMessage(popStatus());
	      }
		  int exitCode = process.waitFor();
		  if(exitCode != 0){
			  throw new RuntimeException(String.format("Process finished with exit code: %d",exitCode));
		  }
		  log(String.format("Process finished with exit code: %d",exitCode));
		  context.setStatusMessage(popStatus());
		  context.setStatusMessage("full log: "+getLog());
		}
		catch(IOException io){
			throw new RuntimeException(io.getMessage());
		} catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
		
		
	}
	
	public String[] parseCommand(String command){
		ArrayList<String> parsed = new ArrayList<String>();
		Pattern pattern = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'"); 
		Matcher matcher = pattern.matcher(command);
		while(matcher.find()){
			parsed.add(matcher.group());
		}
		return parsed.toArray(new String[]{});
	}
}
