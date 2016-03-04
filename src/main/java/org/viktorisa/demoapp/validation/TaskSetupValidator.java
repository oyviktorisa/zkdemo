package org.viktorisa.demoapp.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.viktorisa.demoapp.domain.Task;
import org.viktorisa.demoapp.domain.User;
import org.viktorisa.demoapp.finder.TaskFinder;

@Component
public class TaskSetupValidator {
	
	@Autowired
	TaskFinder taskFinder;
	
	public void validate(Task subject)
			throws ValidationException {
		
		Map<String, String> mapValidate = new HashMap<String, String>();
		
		if(subject.getTaskId()==null) {
			if(subject.getTaskName()==null || subject.getTaskName().equals("")) {
				mapValidate.put("TASK_NAME", "Task name must be filled");
			} else {
				Task example = new Task();
				example.setTaskName(subject.getTaskName());
				List<Task> taskList = taskFinder.selectByExample(example);
				if(taskList!=null && !taskList.isEmpty()) {
					mapValidate.put("TASK_NAME", "Task name must be unique");
				}
			}
		}
		
		if(subject.getTaskDescription()==null || subject.getTaskDescription().equals("")) {
			mapValidate.put("DETAIL", "Detail must be filled");
		}
		
		if(subject.getAssignBy()!=null && subject.getAssignee()==null) {
			mapValidate.put("ASSIGNEE", "Assignee must be filled");
		}
		
		if(mapValidate.size()>0) {
			throw new ValidationException(mapValidate);
		}
		
	}

}
