package org.viktorisa.demoapp.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.viktorisa.demoapp.domain.Task;
import org.viktorisa.demoapp.domain.User;
import org.viktorisa.demoapp.service.AuthenticationService;
import org.viktorisa.demoapp.service.TaskService;
import org.viktorisa.demoapp.service.UserService;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@VariableResolver(DelegatingVariableResolver.class)
public class TaskInquiryComposer extends SelectorComposer<Window>{
	
	@Wire
	Listbox lstTask;
	@Wire
	Textbox txtTaskName;
	@Wire
	Button btnAdd;
	
	@WireVariable
	TaskService taskServiceImpl;
	@WireVariable
	AuthenticationService authenticationServiceImpl;
	
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		if(!authenticationServiceImpl.getUserCredential().isAdmin()) {
			btnAdd.setVisible(false);
		}
		
	}
	
	@Listen("onClick=#btnFind")
	public void onFind() {
		Task example = new Task();
		if(txtTaskName.getValue()!=null && !txtTaskName.getValue().equals("")) {
			example.setTaskName(txtTaskName.getValue());
		}
		if(!authenticationServiceImpl.getUserCredential().isAdmin()) {
			example.setAssignee(authenticationServiceImpl.getUserCredential().getUserId());
		}
		List<Task> taskList = taskServiceImpl.selectInquiry(example);
		ListModelList<Task> model = new ListModelList<Task>(taskList);
		lstTask.setModel(model);
		lstTask.setItemRenderer(getListItemRenderer());
		lstTask.renderAll();
	}
	
	@Listen("onClick=#btnAdd")
	public void onClickAdd() {
		Executions.createComponents("/task-screen/task_setup.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
	
	private ListitemRenderer<Task> getListItemRenderer() {
		return new ListitemRenderer<Task>() {
			
			public void render(Listitem li, final Task data, int i) throws Exception {
				new Listcell(data.getTaskName()).setParent(li);
				new Listcell(data.getAssigneeName()==null?"":data.getAssigneeName()).setParent(li);
				new Listcell(new SimpleDateFormat("dd-MM-yyyy").format(data.getCreationDate())).setParent(li);
				new Listcell(data.getFinishDate()==null?"":new SimpleDateFormat("dd-MM-yyyy").format(data.getFinishDate())).setParent(li);
				
				A detailLink = new A();
				detailLink.setLabel("Detail");
				detailLink.setStyle("color:blue;text-decoration:underline");
				detailLink.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					public void onEvent(Event arg0) throws Exception {
						Map<String,Object> params = new HashMap<String,Object>();
						params.put(Task.class.getName(), data);
						Executions.createComponents("/task-screen/task_setup.zul", getSelf().getParent(), params);
						getSelf().detach();
					}
				});
				
				A deleteLink = new A();
				deleteLink.setLabel("Delete");
				deleteLink.setStyle("color:blue;text-decoration:underline");
				deleteLink.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					public void onEvent(Event arg0) throws Exception {
						Messagebox.show("Are you sure you want to delete?",
								"Confirmation", Messagebox.YES
										| Messagebox.NO, null,
								new SerializableEventListener<Event>() {

									public void onEvent(Event event) throws Exception {
										int resultButton = Integer.parseInt(event.getData().toString());
										if (resultButton == Messagebox.YES) {
											taskServiceImpl.delete(data);
											onFind();
										}
									}
								});
					}
				});
				
				A assignLink = new A();
				assignLink.setLabel("Assign");
				assignLink.setStyle("color:blue;text-decoration:underline");
				assignLink.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					public void onEvent(Event arg0) throws Exception {
						Map<String,Object> params = new HashMap<String,Object>();
						params.put("assign", data);
						Executions.createComponents("/task-screen/task_setup.zul", getSelf().getParent(), params);
						getSelf().detach();
					}
				});
				
				A finishLink = new A();
				finishLink.setLabel("Finish");
				finishLink.setStyle("color:blue;text-decoration:underline");
				finishLink.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					public void onEvent(Event arg0) throws Exception {
						Messagebox.show("Are you sure you have finished the task?",
								"Confirmation", Messagebox.YES
										| Messagebox.NO, null,
								new SerializableEventListener<Event>() {

									public void onEvent(Event event) throws Exception {
										int resultButton = Integer.parseInt(event.getData().toString());
										if (resultButton == Messagebox.YES) {
											taskServiceImpl.finish(data);
											onFind();
										}
									}
								});
					}
				});
				
				Hbox operationBox = new Hbox();
				operationBox.appendChild(detailLink);
				if(data.getFinishDate()==null) {
					operationBox.appendChild(finishLink);
				}
				if(authenticationServiceImpl.getUserCredential().isAdmin()) {
					if(data.getAssignee()==null) {
						operationBox.appendChild(assignLink);
					}
					operationBox.appendChild(deleteLink);
				}
				
				
				Listcell operationCell = new Listcell();
				operationBox.setParent(operationCell);
				operationCell.setParent(li);
				
			}
		};
	}

}
