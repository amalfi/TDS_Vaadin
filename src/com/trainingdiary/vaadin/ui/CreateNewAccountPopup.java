package com.trainingdiary.vaadin.ui;


import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.popupview.PopupViewServerRpc;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PopupView.PopupVisibilityEvent;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

// Create a dynamically updating content for the popup
public class CreateNewAccountPopup extends CustomComponent
{
    
	public static void AddComponentToPopup(Window window)
	{
		FormLayout content = new FormLayout();
	     
		TextField login = new TextField(); //pole z inputem do loginu
		TextField password = new TextField(); //pole z inputem do has³a
		Slider age = new Slider(); //age
		TextArea personDescription = new TextArea();
        Button savePerson = new Button(); 
		
		age.setImmediate(true);
        age.setMin(0.0);
        age.setMax(100.0);
        age.setValue(50.0);
 

        age.addValueChangeListener(new ValueChangeListener()
        {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				  final String valueString = String.valueOf(event.getProperty()
	                        .getValue());
	                Notification.show("Value changed:", valueString,
	                        Type.TRAY_NOTIFICATION);
			}
  
        });
			
		//setting content to popup window
        content.addComponent(login);
        content.addComponent(password);
        content.addComponent(age);
        content.addComponent(personDescription);
        content.addComponent(savePerson);
        
        
        
		window.setContent(content);
	    
	}
        
}