package org.viktorisa.demoapp.controller;

import java.util.List;

import org.viktorisa.demoapp.service.AuthenticationService;
import org.viktorisa.demoapp.service.impl.AuthenticationServiceImpl;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

@VariableResolver(DelegatingVariableResolver.class)
public class SidebarController extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Wire
	Grid fnList;
	
	@WireVariable
    AuthenticationService authenticationServiceImpl;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		// to initial view after view constructed.
		Rows rows = fnList.getRows();
		
		//add menu
		rows.appendChild(constructSidebarRow("fn1", "Task Administration", "/task-screen/task_inquiry.zul"));
		if(authenticationServiceImpl.getUserCredential()!=null
				&& authenticationServiceImpl.getUserCredential().isAdmin()) {
			rows.appendChild(constructSidebarRow("fn2", "User Administration","/user-screen/user_inquiry.zul"));
		}
	}

	private Row constructSidebarRow(String name, String label, final String locationUri) {
		// construct component and hierarchy
		Row row = new Row();
		Label lab = new Label(label);
		row.appendChild(lab);
		// set style attribute
		row.setSclass("sidebar-fn");
		EventListener<Event> actionListener = new SerializableEventListener<Event>() {
			private static final long serialVersionUID = 1L;

			public void onEvent(Event event) throws Exception {
				 //redirect current url to new location
                if(locationUri.startsWith("http")){
                    //open a new browser tab
                    Executions.getCurrent().sendRedirect(locationUri);
                }else{
                    //use iterable to find the first include only
                    Include include = (Include)Selectors.iterable(fnList.getPage(), "#mainInclude")
                            .iterator().next();
                    include.setSrc(locationUri);
                }
			}
		};
		row.addEventListener(Events.ON_CLICK, actionListener);
		return row;
	}

}
