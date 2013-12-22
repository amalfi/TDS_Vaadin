package com.trainingdiary.vaadin.ui;


import com.vaadin.shared.ui.popupview.PopupViewServerRpc;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupView.PopupVisibilityEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

// Create a dynamically updating content for the popup
public class CreateNewAccountPopup extends CustomComponent
{
    
	public static void AddComponentToPopup(Window window)
	{
		FormLayout content = new FormLayout();
		
		//setting content to popup window
		window.setContent(content);
	    
	}
}