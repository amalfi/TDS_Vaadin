package com.trainingdiary.vaadin.ui;

import org.apache.log4j.Logger;

import com.trainingdiary.ProgramType;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

public class AddNewTrainingTemplate  extends CustomComponent implements View
{

	private static final long serialVersionUID = 1L;

	static Logger log = Logger.getLogger(CreateNewAccountPopup.class);

    final Table table = new Table();

	
	public static void AddContentToEditableTrainingTemplate(/*final Notification successfullySavedTrainingTemplate,*/ final String trainingProgramName, final String numberOfExcersises, final String numberOfSets, final String breakBetweenSets, final String programDescription, final Table table, Window window)
	{
	
	    Button generatePDF = new Button("Generate PDF");
	    Button saveNewTrainingProgram = new Button("Save new training program");
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
		//--------------------------------------------------------------------------------------------Edit table checkbox
		final CheckBox switchEditable = new CheckBox("Editable");
		switchEditable.addValueChangeListener(new Property.ValueChangeListener() 
	    {
		
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				 table.setEditable(((Boolean)event.getProperty()
                         .getValue()).booleanValue());
		}
		});
		switchEditable.setImmediate(true);
		content.addComponent(switchEditable);
		//--------------------------------------------------------------------------------------------
		content.addComponent(table);
		content.addComponent(generatePDF);
		content.addComponent(saveNewTrainingProgram);
		
		//---------------------------------------------------------------------------------------------
		
		saveNewTrainingProgram.addClickListener(new Button.ClickListener()
	      {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) 
			{

				ProgramType.SaveProgram(trainingProgramName, numberOfExcersises, numberOfSets, breakBetweenSets, programDescription, table);
			
			}
		
	      });
		//---------------------------------------------------------------------------------------------
		
		content.setMargin(true);
		window.setContent(content);
		
		
	}
	
	public static boolean fieldValidator(String sTrainingProgramName, String sNumberOfExcersises, String sNumberOfSetsOfEachExcersise, String sBreakBetweenSets, String sTextFieldThirdTab, Table table2, Window window2)
	{
		boolean bValidationStatus=false;
		
		if(sTrainingProgramName.equals("")||sTrainingProgramName==null)
		{
			bValidationStatus=false;
		}
		else if(sNumberOfExcersises.equals("")||sNumberOfExcersises==null)
		{

			bValidationStatus=false;
		}
		else if(sNumberOfSetsOfEachExcersise.equals("")||sNumberOfSetsOfEachExcersise==null)
		{

			bValidationStatus=false;
		}
		else if(sBreakBetweenSets.equals("")||sBreakBetweenSets==null)
		{

			bValidationStatus=false;
		}
		else if(sTextFieldThirdTab.equals("")||sTextFieldThirdTab==null)
		{

			bValidationStatus=false;
		}
		else
		{
			bValidationStatus=true;
		}
		
		
		return bValidationStatus;
		
	}
	
	
	@Override
	public void enter(ViewChangeEvent event) 
	{
	}
	
}
