package org.viktorisa.demoapp.service;

import java.util.List;

import org.viktorisa.demoapp.domain.User;

public interface UserService {
	
	public List<User> selectAll();
	
	public List<User> selectActiveAll();
	
	public List<User> selectByExample(User example);
	
	public List<User> selectInquiry(User example);
	
	public void delete(User example);
	
	public void save(User user);
	
	public void activate(User example);

}
