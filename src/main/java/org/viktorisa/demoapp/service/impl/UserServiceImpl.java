package org.viktorisa.demoapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.viktorisa.demoapp.domain.User;
import org.viktorisa.demoapp.finder.UserFinder;
import org.viktorisa.demoapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserFinder userFinder;
	
	public List<User> selectAll() {
		return userFinder.selectAll();
	}
	
	public List<User> selectActiveAll() {
		return userFinder.selectActiveAll();
	}

	public List<User> selectByExample(User example) {
		return userFinder.selectByExample(example);
	}

	public List<User> selectInquiry(User example) {
		return userFinder.selectInquiry(example);
	}

	public void delete(User example) {
		userFinder.delete(example);
	}

	public void save(User user) {
		if(user.getUserId()==null) {
			userFinder.save(user);
		} else {
			userFinder.update(user);
		}
	}

	public void activate(User example) {
		userFinder.activate(example);
	}

}
