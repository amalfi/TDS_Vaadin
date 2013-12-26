package com.trainingdiary.vaadin.ui;


import org.apache.log4j.Logger;

import com.trainingdiary.User;
import com.trainingdiary.tools.ButtonActions;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.popupview.PopupViewServerRpc;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PopupView.PopupVisibilityEvent;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

// Create a dynamically updating content for the popup
public class CreateNewAccountPopup extends CustomComponent implements View
{

	   static Logger log = Logger.getLogger(CreateNewAccountPopup.class);
	  
	public static void AddComponentToPopup(Window window)
	{
		FormLayout content = new FormLayout();
	     
		final TextField login = new TextField("User login"); //pole z inputem do loginu
		final PasswordField password = new PasswordField("User password"); //pole z inputem do has³a
	    Button savePerson = new Button("Create account"); 
	    final Notification notification = new Notification("Save Successfull","User has been added correctly",Notification.TYPE_HUMANIZED_MESSAGE);
	    
	    
	    savePerson.addClickListener(new Button.ClickListener() 
	    {
		private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) 
			{

				ProgressBar bar = new ProgressBar();
				bar.setVisible(true);
				bar.setEnabled(true);
				bar.setIndeterminate(true);
				
				if(User.SaveUser(login.getValue(), password.getValue()).equals("Exist"))
				{
					Notification.show("Error","User with this login already exist",Notification.Type.WARNING_MESSAGE);	
				}
				else
				{
					User.SaveUser(login.getValue(), password.getValue());
					//Notification.show("Success","User saved successfull",Notification.Type.HUMANIZED_MESSAGE);
					notification.setDelayMsec(600);
					notification.setPosition(Position.MIDDLE_CENTER);
					notification.show(Page.getCurrent());
				}
			}
	    });
	    
	    
		//setting content to popup window
	    content.addComponent(login);
	    content.addComponent(password);
	    content.addComponent(savePerson);
	    content.setMargin(true);
		window.setContent(content);
		//window.setSizeFull();
    
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
        
}