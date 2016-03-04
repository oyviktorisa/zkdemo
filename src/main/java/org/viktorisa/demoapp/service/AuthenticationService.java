package org.viktorisa.demoapp.service;

import org.viktorisa.demoapp.domain.User;

public interface AuthenticationService {
	
	public User getUserCredential();
	public boolean login(String username, String password);
	public void logout();

}
