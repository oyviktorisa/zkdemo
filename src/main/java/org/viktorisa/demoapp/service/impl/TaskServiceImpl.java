package org.viktorisa.demoapp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.viktorisa.demoapp.dao.DemoTaskMapper;
import org.viktorisa.demoapp.domain.DemoTask;
import org.viktorisa.demoapp.domain.DemoTaskExample;
import org.viktorisa.demoapp.domain.Task;
import org.viktorisa.demoapp.finder.TaskFinder;
import org.viktorisa.demoapp.service.AuthenticationService;
import org.viktorisa.demoapp.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskFinder taskFinder;
	@Autowired
	DemoTaskMapper demoTaskMapper;
	@Autowired
	AuthenticationService authenticationServiceImpl;
	
	public List<Task> selectByExample(Task example) {
		return taskFinder.selectByExample(example);
	}
	
	public List<Task> selectInquiry(Task example) {
		return taskFinder.selectInquiry(example);
	}

	public void assignTask(Task task) {
		taskFinder.assignTask(task);
	}

	public void update(Task task) {
		taskFinder.update(task);
	}

	public void save(Task task) {
		if(task.getTaskId()!=null) {
			update(task);
		} else {
			taskFinder.save(task);
		}
	}

	public void delete(Task task) {
		taskFinder.delete(task);
	}

	public void finish(Task task) {
		task.setFinishDate(new Date());
		task.setLastUpdatedBy(authenticationServiceImpl.getUserCredential().getUserId());
		taskFinder.finish(task);
	}

	public List<DemoTask> findByExample(DemoTaskExample example) {
		return demoTaskMapper.selectByExample(example);
	}

}
