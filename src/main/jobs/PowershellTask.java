package main.jobs;

public class PowershellTask extends ExecutionTask{

	public PowershellTask(String name, String cmd) {
		super(name, "powershell.exe -ExecutionPolicy Bypass -Command "+cmd);
		
	}

}
