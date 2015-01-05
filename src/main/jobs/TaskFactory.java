package main.jobs;


public class TaskFactory {

	// Could use dynamic class loader here
	public static BaseTask getTask(String type, String name, String argument){
		BaseTask t = null;
		if(type.equals("ExecutionTask")){
			t = new ExecutionTask(name, argument);
		}
		else if(type.equals("AlarmTask")){
			t = new AlarmTask(name,argument);
		}
		else if(type.equals("PowershellTask")){
			t = new PowershellTask(name,argument);
		}
		else{
			throw new RuntimeException("Unknown Task type:" +type);
		}
		return t;
	}
}
