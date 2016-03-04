package org.viktorisa.demoapp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.viktorisa.demoapp.domain.Task;
import org.viktorisa.demoapp.domain.User;
import org.viktorisa.demoapp.service.AuthenticationService;
import org.viktorisa.demoapp.service.TaskService;
import org.viktorisa.demoapp.service.UserService;
import org.viktorisa.demoapp.validation.TaskSetupValidator;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(DelegatingVariableResolver.class)
public class TaskSetupComposer extends SelectorComposer<Window> {
	
	@WireVariable
	private Map<String, Object> arg;
	
	@Wire Textbox txtTaskName;
	@Wire Textbox txtDetail;
	@Wire Combobox cmbAssignee;
	@Wire Button btnSave;
	@Wire Hbox lblAssignee;
	@Wire Div dvError;
	
	@WireVariable
	AuthenticationService authenticationServiceImpl;
	@WireVariable
	TaskService taskServiceImpl;
	@WireVariable
	TaskSetupValidator taskSetupValidator;
	@WireVariable
	UserService userServiceImpl;
	
	private Task task;
	private Long assigneeId;
	
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		initCombobox();
		if(arg.containsKey(Task.class.getName())) {
			task = (Task) arg.get(Task.class.getName());
			txtTaskName.setValue(task.getTaskName());
			txtTaskName.setDisabled(true);
			txtDetail.setValue(task.getTaskDescription());
			if(!authenticationServiceImpl.getUserCredential().isAdmin()) {
				txtDetail.setDisabled(true);
				btnSave.setVisible(false);
			}
		} else if(arg.containsKey("assign")) {
			task = (Task) arg.get("assign");
			txtTaskName.setValue(task.getTaskName());
			txtTaskName.setDisabled(true);
			txtDetail.setValue(task.getTaskDescription());
			txtDetail.setDisabled(true);
			cmbAssignee.setVisible(true);
			lblAssignee.setVisible(true);
		}
	}
	
	private void initCombobox() {
		User example = new User();
		List<User> userList = userServiceImpl.selectActiveAll();
		ListModelList<User> model = new ListModelList<User>(userList);
		cmbAssignee.setModel(model);
		cmbAssignee.setItemRenderer(new ComboitemRenderer<User>() {

			public void render(Comboitem ci, User data, int i) throws Exception {
				ci.setLabel(data.getUserName());
				ci.setValue(data);
			}
		});
		cmbAssignee.setVisible(false);
		lblAssignee.setVisible(false);
	}
	
	@Listen("onSelect=#cmbAssignee")
	public void onSelectAssignee() {
		User assignee = cmbAssignee.getSelectedItem().getValue();
		assigneeId = assignee.getUserId();
	}
	
	@Listen("onClick=#btnCancel") 
	public void onClickCancel() {
		Executions.createComponents("/task-screen/task_inquiry.zul", getSelf().getParent(), null);
		getSelf().detach();
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
								taskSetupValidator.validate(task);
								if(arg.containsKey("assign")) {
									taskServiceImpl.assignTask(task);
								} else {
									taskServiceImpl.save(task);
								}
								Messagebox.show("Success!");
								Executions.createComponents("/task-screen/task_inquiry.zul", getSelf().getParent(), null);
								getSelf().detach();
							} catch (ValidationException e) {
								showError(e);
							}
						}
					}
				});
	}
	
	private void setObjectValue() {
		if(task==null) {
			task = new Task();
		}
		
		task.setTaskName(txtTaskName.getValue());
		task.setTaskDescription(txtDetail.getValue());
		if(arg.containsKey("assign")) {
			task.setAssignee(assigneeId);
			task.setAssignBy(authenticationServiceImpl.getUserCredential().getUserId());
		}
		if(task.getTaskId()==null) {
			task.setCreatedBy(authenticationServiceImpl.getUserCredential().getUserId());
			task.setCreationDate(new Date());
		}
		task.setLastUpdatedBy(authenticationServiceImpl.getUserCredential().getUserId());
		task.setCreationDate(new Date());
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
		}
	}
}
