package org.viktorisa.demoapp.controller;

import org.viktorisa.demoapp.service.AuthenticationService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

@VariableResolver(DelegatingVariableResolver.class)
public class LogoutController extends SelectorComposer<Component> {
	@WireVariable
	AuthenticationService authenticationServiceImpl;
     
	@Listen("onClick=#btnLogout")
	public void doLogout(){
		authenticationServiceImpl.logout();      
	    Executions.sendRedirect("/login.zul");
	}
}
