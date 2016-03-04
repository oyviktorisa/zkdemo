package org.viktorisa.demoapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.viktorisa.demoapp.domain.User;
import org.viktorisa.demoapp.service.AuthenticationService;
import org.viktorisa.demoapp.service.UserService;
import org.viktorisa.demoapp.validation.UserSetupValidator;
import org.viktorisa.demoapp.validation.ValidationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(DelegatingVariableResolver.class)
public class UserSetupComposer extends SelectorComposer<Window>{
	
	@WireVariable
	private Map<String, Object> arg;
	
	@Wire Textbox txtUsername;
	@Wire Textbox txtPassword;
	@Wire Radiogroup rdgType;
	@Wire Radio rdUser;
	@Wire Radio rdAdmin;
	@Wire Div dvError;
	
	@WireVariable
	AuthenticationService authenticationServiceImpl;
	@WireVariable
	UserSetupValidator userSetupValidator;
	@WireVariable
	UserService userServiceImpl;
	
	private User user;
	
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		if (arg.containsKey(User.class.getName())) {
			user = (User) arg.get(User.class.getName());
			initData();
			txtUsername.setDisabled(true);
		} else {
			rdUser.setChecked(true);
		}
	}
	
	private void initData() {
		txtUsername.setValue(user.getUserName());
		txtPassword.setValue(user.getPassword());
		rdUser.setChecked(!user.isAdmin());
		rdAdmin.setChecked(user.isAdmin());
	}
	
	@Listen("onClick=#btnSave")
	public void onClickSave() {
		clearError();
		Messagebox.show("Are you sure you want to save?",
				"Confirmation", Messagebox.YES
						| Messagebox.NO, null,
				new SerializableEventListener<Event>() {

					public void onEvent(Event event) {
						int resultButton = Integer.parseInt(event.getData().toString());
						if (resultButton == Messagebox.YES) {
							try {
								setObjectValue();
								userSetupValidator.validate(user);
								userServiceImpl.save(user);
								Messagebox.show("Success!");
								Executions.createComponents("/user-screen/user_inquiry.zul", getSelf().getParent(), null);
								getSelf().detach();
							} catch (ValidationException e) {
								showError(e);
							}
						}
					}
				});
	}
	
	private void setObjectValue() {
		if(user==null) {
			user = new User();
		}
		
		user.setUserName(txtUsername.getValue());
		user.setPassword(txtPassword.getValue());
		user.setAdmin(rdAdmin.isChecked());
		if(user.getUserId()==null) {
			user.setCreatedBy(authenticationServiceImpl.getUserCredential().getUserId());
			user.setCreationDate(new Date());
		}
		user.setLastUpdatedBy(authenticationServiceImpl.getUserCredential().getUserId());
		user.setCreationDate(new Date());
	}
	
	private void clearError() {
		List<Component> compList = new ArrayList<Component>();
		for(Component comp : dvError.getChildren()) {
			compList.add(comp);
		}
		for(Component comp : compList) {
			dvError.removeChild(comp);
		}
	}
	
	private void showError(ValidationException e) {
		for(String msg : e.getValues()) {
			dvError.appendChild(new Label(msg));
			dvError.appendChild(new Separator());
		}
	}
	
	@Listen("onClick=#btnCancel") 
	public void onClickCancel() {
		Executions.createComponents("/user-screen/user_inquiry.zul", getSelf().getParent(), null);
		getSelf().detach();
	}

}
