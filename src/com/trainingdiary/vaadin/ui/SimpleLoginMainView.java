package com.trainingdiary.vaadin.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

public class SimpleLoginMainView extends CustomComponent implements View  //tutaj dodajesz elementy widoczne po zalogowaniu
{ //Main view after dialog

    public static final String NAME = "";

  TabSheet tabsheet = new TabSheet();
  Label label = new Label();
  Label label2 = new Label();
  Label label3 = new Label();
  Label label4 = new Label();
  Label label5 = new Label();
   
  
   /* Button logout = new Button("Logout", new Button.ClickListener() 
    {

        @Override
        public void buttonClick(ClickEvent event) 
        {

            // "Logout" the user
            getSession().setAttribute("user", null);

            // Refresh this view, should redirect to login view
            getUI().getNavigator().navigateTo(NAME);
        }
    });*/

    
    public SimpleLoginMainView() 
    {
     // setCompositionRoot(new CssLayout(text, logout));  //tutaj w parametrachSetCompositionRoot ustawiamy komponenty jakie maja byc wstawione do widoku, 
    	setCompositionRoot(tabsheet);
    	
    }

    @Override
    public void enter(ViewChangeEvent event) 
    {
    	label.setValue("Test Value");
    	tabsheet.addTab(label).setCaption("Create new Diary");
    	tabsheet.addTab(label2).setCaption("Add new session into existing diary");
    	tabsheet.addTab(label3).setCaption("Add new training");
    	tabsheet.addTab(label4).setCaption("Edit existing diary");
    	tabsheet.addTab(label5).setCaption("Edit existing training");
    	
    	
        // Get the user name from the session
        //String username = String.valueOf(getSession().getAttribute("user"));

        // And show the username
      //  text.setValue("Hello " + username);
    //-----------------------------------------------------
    	
    }
}