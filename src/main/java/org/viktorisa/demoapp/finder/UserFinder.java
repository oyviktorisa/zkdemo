package org.viktorisa.demoapp.finder;

import java.util.List;

import org.viktorisa.demoapp.domain.User;

public interface UserFinder {
	
	public List<User> selectAll();
	
	public List<User> selectActiveAll();
	
	public List<User> selectByExample(User example);
	
	public List<User> selectInquiry(User example);
	
	public void delete(User example);
	
	public void save(User user);
	
	public void update(User user);
	
	public void activate(User example);

}
