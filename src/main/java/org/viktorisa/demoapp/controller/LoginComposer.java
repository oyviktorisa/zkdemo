package org.viktorisa.demoapp.controller;

import org.viktorisa.demoapp.domain.User;
import org.viktorisa.demoapp.service.AuthenticationService;
import org.viktorisa.demoapp.service.impl.AuthenticationServiceImpl;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

@VariableResolver(DelegatingVariableResolver.class)
public class LoginComposer extends SelectorComposer<Component>{
	 
    @Wire
    Textbox account;
    @Wire
    Textbox password;
    @Wire
    Label message;
     
    @WireVariable
    AuthenticationService authenticationServiceImpl;
     
    @Listen("onClick=#btnLogin")
    public void doLogin(){
        String nm = account.getValue();
        String pd = password.getValue();
         
        if(!authenticationServiceImpl.login(nm,pd)){
            message.setValue("account or password are not correct.");
            return;
        }
         
        Executions.sendRedirect("/index.zul");
         
    }
}
