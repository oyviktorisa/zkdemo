package org.viktorisa.demoapp.finder;

import java.util.List;

import org.viktorisa.demoapp.domain.Task;

public interface TaskFinder {
	
	public List<Task> selectByExample(Task example);
	
	public List<Task> selectInquiry(Task example);
	
	public void assignTask(Task task);
	
	public void update(Task task);
	
	public void save(Task task);
	
	public void delete(Task task);
	
	public void finish(Task task);

}
