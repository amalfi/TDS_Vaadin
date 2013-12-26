package com.trainingdiary.vaadin.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class UserProfile extends CustomComponent
{

	private static final long serialVersionUID = 1L;

	public UserProfile()
	{
		String currentUserNameValue = SimpleLoginView.currentLoadedUser;
		
		VerticalLayout vl = new VerticalLayout();
		Label label = new Label("Welcome " + currentUserNameValue);
		vl.addComponent(label);
		vl.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		
		setCompositionRoot(vl);
	}
}
