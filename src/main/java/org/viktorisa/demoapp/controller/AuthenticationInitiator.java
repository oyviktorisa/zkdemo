package org.viktorisa.demoapp.controller;
import java.util.Map;

import org.viktorisa.demoapp.domain.User;
import org.viktorisa.demoapp.service.AuthenticationService;
import org.viktorisa.demoapp.service.impl.AuthenticationServiceImpl;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

@VariableResolver(DelegatingVariableResolver.class)
public class AuthenticationInitiator implements Initiator {
	
	@WireVariable
    AuthenticationService authenticationServiceImpl;
     
    public void doInit(Page page, Map<String, Object> args) throws Exception {
    	Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
        User cre = authenticationServiceImpl.getUserCredential();
        if(cre==null){
            Executions.sendRedirect("/login.zul");
            return;
        }
    }
}
