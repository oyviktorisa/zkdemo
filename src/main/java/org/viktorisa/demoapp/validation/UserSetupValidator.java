package org.viktorisa.demoapp.validation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.viktorisa.demoapp.domain.User;
import org.viktorisa.demoapp.finder.UserFinder;

@Component
public class UserSetupValidator {
	
	@Autowired
	UserFinder userFinder;

	public void validate(User subject)
			throws ValidationException {
		
		Map<String, String> mapValidate = new HashMap<String, String>();
		
		if(subject.getUserId()==null) {
			if(subject.getUserName()==null || subject.getUserName().equals("")) {
				mapValidate.put("USERNAME", "Username must be filled");
			} else {
				User example = new User();
				example.setUserName(subject.getUserName());
				List<User> userList = userFinder.selectByExample(example);
				if(userList!=null && !userList.isEmpty()) {
					mapValidate.put("USERNAME", "Username must be unique");
				}
			}
		}
		
		if(subject.getPassword()==null || subject.getPassword().equals("")) {
			mapValidate.put("PASSWORD", "Password must be filled");
		}
		
		if(mapValidate.size()>0) {
			throw new ValidationException(mapValidate);
		}
		
	}
	
}
