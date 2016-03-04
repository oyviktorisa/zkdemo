package org.viktorisa.demoapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.viktorisa.demoapp.domain.User;
import org.viktorisa.demoapp.service.UserService;
import org.zkoss.util.resource.Labels;
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
public class UserInquiryComposer extends SelectorComposer<Window> {
	
	@Wire
	Listbox lstUser;
	@Wire
	Textbox txtUserName;
	
	@WireVariable
	UserService userServiceImpl;
	
	
	@Listen("onClick=#btnFind")
	public void onFind() {
		User example = new User();
		if(txtUserName.getValue()!=null && !txtUserName.getValue().equals("")) {
			example.setUserName(txtUserName.getValue());
		}
		List<User> userList = userServiceImpl.selectInquiry(example);
		ListModelList<User> model = new ListModelList<User>(userList);
		lstUser.setModel(model);
		lstUser.setItemRenderer(getListItemRenderer());
		lstUser.renderAll();
	}
	
	@Listen("onClick=#btnAdd")
	public void onClickAdd() {
		Executions.createComponents("/user-screen/user_setup.zul", getSelf().getParent(), null);
		getSelf().detach();
	}
	
	private ListitemRenderer<User> getListItemRenderer() {
		return new ListitemRenderer<User>() {
			
			public void render(Listitem li, final User data, int i) throws Exception {
				new Listcell(data.getUserName()).setParent(li);
				new Listcell(data.isAdmin()?"YES":"NO").setParent(li);
				new Listcell(data.isActive()?"YES":"NO").setParent(li);
				
				A detailLink = new A();
				detailLink.setLabel("Detail");
				detailLink.setStyle("color:blue;text-decoration:underline");
				detailLink.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					public void onEvent(Event arg0) throws Exception {
						Map<String,Object> params = new HashMap<String,Object>();
						params.put(User.class.getName(), data);
						Executions.createComponents("/user-screen/user_setup.zul", getSelf().getParent(), params);
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
											userServiceImpl.delete(data);
											onFind();
										}
									}
								});
					}
				});
				
				A activateLink = new A();
				activateLink.setLabel("Activate");
				activateLink.setStyle("color:blue;text-decoration:underline");
				activateLink.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

					public void onEvent(Event arg0) throws Exception {
						Messagebox.show("Are you sure you want to activate?",
								"Confirmation", Messagebox.YES
										| Messagebox.NO, null,
								new SerializableEventListener<Event>() {

									public void onEvent(Event event) throws Exception {
										userServiceImpl.activate(data);
										onFind();
									}
								});
					}
				});
				
				Hbox operationBox = new Hbox();
				operationBox.appendChild(detailLink);
				if(data.isActive()) {
					operationBox.appendChild(deleteLink);
				} else {
					operationBox.appendChild(activateLink);
				}
				
				
				Listcell operationCell = new Listcell();
				operationBox.setParent(operationCell);
				operationCell.setParent(li);
				
			}
		};
	}
}
