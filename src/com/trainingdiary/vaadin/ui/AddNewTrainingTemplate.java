package com.trainingdiary.vaadin.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class AddNewTrainingTemplate  extends CustomComponent implements View
{

	private static final long serialVersionUID = 1L;

	static Logger log = Logger.getLogger(CreateNewAccountPopup.class);
	  
    final Table table = new Table();
	
	public static void AddContentToEditableTrainingTemplate(String trainingProgramName, String numberOfExcersises, String numberOfSets, String breakBetweenSets, Table table, Window window)
	{
		FormLayout content = new FormLayout();
		
		// Define the names and data types of columns.
		table.addContainerProperty("Name of Excersise",  String.class,  null);
		table.addContainerProperty("Number of sets",  String.class,  null);
		table.addContainerProperty("Break between sets",  String.class,  null);
		//When we will save a new training program template, training program description and training program name will be added to this list
		
		
		// Add a few items in the table.
		for (int i=0; i<Integer.valueOf(numberOfExcersises); i++) 
		{
		    // Create the table row.
		    table.addItem(new Object[] {"",numberOfSets,breakBetweenSets},new Integer(i)); // Item identifier
		}
		 
		table.setPageLength(8);
		content.addComponent(table);
		content.setMargin(true);
		window.setContent(content);
	}
	
	@Override
	public void enter(ViewChangeEvent event) 
	{
	}
	
}
