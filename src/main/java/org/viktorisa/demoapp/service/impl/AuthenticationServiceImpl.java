package org.viktorisa.demoapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.viktorisa.demoapp.domain.User;
import org.viktorisa.demoapp.finder.UserFinder;
import org.viktorisa.demoapp.service.AuthenticationService;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired
	private UserFinder userFinder;

	public User getUserCredential() {
		 Session sess = Sessions.getCurrent();
		 if(sess.hasAttribute("userCredential")) {
			 User cre = (User)sess.getAttribute("userCredential");
			 return cre;
		 }
	     
		 return null;
	     
	}

	public boolean login(String username, String password) {
		User example = new User();
		example.setUserName(username);
		
		List<User> userList = userFinder.selectByExample(example);
		
        if(userList==null || userList.isEmpty()){
            return false;
        } else {
        	User user = userList.get(0);
        	if(user.getPassword().equals(password) && user.isActive()) {
        		Session sess = Sessions.getCurrent();
        		sess.setAttribute("userCredential",user);
        	} else {
        		return false;
        	}
        }
		
        return true;
	}

	public void logout() {
		Session sess = Sessions.getCurrent();
        sess.removeAttribute("userCredential");
	}

}
