package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import jobs.BaseTask;

public class TaskRepository extends Observable{
	private static TaskRepository instance;
	private List<BaseTask> tasks;

	enum changes{
		TASK_ADDED,
		TASK_REMOVED
	}
	public TaskRepository(){
		tasks = new ArrayList<BaseTask>();
	}
	
	public static TaskRepository getInstance() {
		if (instance == null) {
			instance = new TaskRepository();
		}
		return instance;
	}

	public void addTask(BaseTask baseTask) {
		tasks.add(baseTask);
		setChanged();
		notifyObservers();
	}

	public boolean removeTask(BaseTask baseTask) {
		boolean success = tasks.remove(baseTask);
		setChanged();
		notifyObservers();
		return success;
	}

	public List<BaseTask> getTasks() {
		return tasks;
	}
	
	public BaseTask getByName(String name){
		for(BaseTask task:tasks){
			if(name.equals(task.getName())){
				return task;
			}
		}
		throw new RuntimeException("Could not find task '"+name+"'");
	}
}
