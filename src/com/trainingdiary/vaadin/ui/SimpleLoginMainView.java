package com.trainingdiary.vaadin.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.trainingdiary.DiaryBean;
import com.trainingdiary.ProgramType;
import com.trainingdiary.Training;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class SimpleLoginMainView extends CustomComponent implements View  //tutaj dodajesz elementy widoczne po zalogowaniu
{ 
	//Main view after dialog
	static Logger log = Logger.getLogger(DiaryBean.class);
	private static final long serialVersionUID = 1L;
	public static final String NAME = "";
	TabSheet tabsheet = new TabSheet();
	
//------------------------------------------- ------------------------------------------- Layouts in each tab 
	
  VerticalLayout verticalViewCreateNewDiary = new VerticalLayout(); //VerticalLayout of 'CreateNewDiary' tab
  VerticalLayout verticalAddNewTrainingIntoExistingDiary = new VerticalLayout(); //VerticalLayout of 'Add new training into existing diary' tab
  VerticalLayout verticalAddNewTrainingProgram = new VerticalLayout();
  VerticalLayout verticalEditExistingTraining = new VerticalLayout();
  VerticalLayout editExistingDiary = new VerticalLayout();
  VerticalLayout logoutLayout = new VerticalLayout();
//------------------------------------------- ------------------------------------------- Components of FirstTab

  TextField textfield = new TextField("Name");
  ComboBox select = new ComboBox("Training Programs list");
  DateField date = new DateField();
  RichTextArea area = new RichTextArea();
 // CustomTabSheet ctb = new CustomTabSheet();
  
  Label label = new Label();
  Label label2 = new Label();
  Label label3 = new Label();
  Label label4 = new Label();
  Label label5 = new Label();
  Label label6 = new Label();
  //--------------------------------------------------- -------------------------------------------Helping labels
  Label datelabel = new Label("Date");
  Label descriptionlabel = new Label("Description");
  Label programType = new Label("Program Type");
  //---------------------------------------------------
  Button button = new Button("Save new diary");
//-------------------------------------------
  String sProgramType;
  Date dDate;
  String sNameOfTrainingDiary;
  String sDescription;
  
//------------------------------------------- -------------------------------------------Components of SecondTab

  com.vaadin.ui.TextArea textfieldSecondTab = new com.vaadin.ui.TextArea("Training Description");
  ComboBox selectSecondTab = new ComboBox("Training diaries list");
  Button buttonInSecondTab = new Button("Add new training");
  
  
//--------------------------------------------
  
//------------------------------------------- -------------------------------------------Components of ThirdTab
  ComboBox selectThirdTab = new ComboBox("Training programs list");
  com.vaadin.ui.TextArea textfieldThirdTab = new com.vaadin.ui.TextArea("Traning program description");
  Button buttonInThirdTab = new Button("Add new training program");

//--------------------------------------------
  

//------------------------------------------- -------------------------------------------Components of FourthTab
  final Table table = new Table();
  
//--------------------------------------------
//------------------------------------------- -------------------------------------------Components of SeventhTab
  Button logoutButton = new Button("Logout", new Button.ClickListener() 
  {

	private static final long serialVersionUID = 1L;

	@Override
      public void buttonClick(ClickEvent event) {

          // "Logout" the user
          getSession().setAttribute("user", null);

          // Refresh this view, should redirect to login view
          getUI().getNavigator().navigateTo(NAME);
      }
  }
  );
  
//--------------------------------------------
  
    public SimpleLoginMainView()  //here we can add action listeners etc
    {
//******************************************************************CREATE NEW DIARY TAB******************************************************************   	 
     // setCompositionRoot(new CssLayout(text, logout));  //tutaj w parametrachSetCompositionRoot ustawiamy komponenty jakie maja byc wstawione do widoku, 
    	/*ctb.setVisible(true);
    	ctb.setEnabled(true);*/

     Training training = new Training();
    	
      ArrayList<Training> trainings = new ArrayList<Training>();
      trainings = training.LoadTrainings();
	  
      for(int i=0; i<trainings.size(); i++)
      {
    	  log.debug("Currently loaded training : " + String.valueOf(trainings.get(i)));
    	  select.addItem(trainings.get(i).getDescription());
      }

  //--------------------------------------------------------- -------------------------------------------Adding components to vertical layout 	
	  verticalViewCreateNewDiary.addComponent(textfield);
	  verticalViewCreateNewDiary.addComponent(datelabel);
  	  verticalViewCreateNewDiary.addComponent(date);
  	  
  	  verticalViewCreateNewDiary.addComponent(programType);
	  verticalViewCreateNewDiary.addComponent(select);
	  
	  verticalViewCreateNewDiary.addComponent(descriptionlabel);
	  verticalViewCreateNewDiary.addComponent(area);
	  
	  verticalViewCreateNewDiary.addComponent(button);
	  //---------------------------------------------------------
	  
//******************************************************************END OF CREATE NEW DIARY TAB******************************************************************   	

//******************************************************************CREATE ADD NEW TRAINING TAB******************************************************************   	 
	  HorizontalLayout fittingLayout = new HorizontalLayout();
	  fittingLayout.addComponent(verticalAddNewTrainingIntoExistingDiary);
	  
	  //-----Here we add content to selectSecondTab
	  	DiaryBean diarybean = new DiaryBean();
	  	HashMap<String, Object> dzienniki = diarybean.LoadDiaries();
	  	
	  	for(int i=0; i<dzienniki.size(); i++)
	  	{
	  		selectSecondTab.addItem(dzienniki.get(i));
	  	}
	  	
	  //-----
	  
	  
	  
	  verticalAddNewTrainingIntoExistingDiary.addComponent(textfieldSecondTab);
	  verticalAddNewTrainingIntoExistingDiary.addComponent(selectSecondTab);
	  verticalAddNewTrainingIntoExistingDiary.addComponent(buttonInSecondTab);
	  
//******************************************************************END OF CREATE NEW DIARY TAB******************************************************************   	 

//******************************************************************ADD NEW TRAINING PROGRAM*********************************************************************
	verticalAddNewTrainingProgram.addComponent(selectThirdTab);
	  	verticalAddNewTrainingProgram.setComponentAlignment(selectThirdTab, Alignment.MIDDLE_CENTER);
	  
  	verticalAddNewTrainingProgram.addComponent(textfieldThirdTab);
		verticalAddNewTrainingProgram.setComponentAlignment(textfieldThirdTab, Alignment.MIDDLE_CENTER);
		
    verticalAddNewTrainingProgram.addComponent(buttonInThirdTab);
		verticalAddNewTrainingProgram.setComponentAlignment(buttonInThirdTab, Alignment.MIDDLE_CENTER);
	  
	  //   viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
	  
//******************************************************************END OF NEW TRAINING PROGRAM*********************************************************************
	
		
//******************************************************************EDIT EXSITING DIARY*********************************************************************
	  
	/*  
	//  table.addStyleName("components-inside");
	   Define the names and data types of columns.
	  * The "default value" parameter is meaningless here. 
	  table.addContainerProperty("Sum", Label.class, null);
	  table.addContainerProperty("Is Transferred", CheckBox.class, null);
	  table.addContainerProperty("Comments", TextField.class, null);
	  table.addContainerProperty("Details", Button.class, null);
	   Add a few items in the table. 
	  for (int i=0; i<100; i++) 
	  {
		// Create the fields for the current table row
		  Label sumField = new Label(String.format(
		  "Sum is <b>$%04.2f</b><br/><i>(VAT incl.)</i>",
		  new Object[] {new Double(Math.random()*1000)}),
		  Label.CONTENT_XHTML);
		  CheckBox transferredField = new CheckBox("is transferred");
		  // Multiline text field. This required modifying the
		  // height of the table row.
		  TextField commentsField = new TextField();
	
		  // The Table item identifier for the row.
		  Integer itemId = new Integer(i);
		  // Create a button and handle its click. A Button does not
		  // know the item it is contained in, so we have to store the
		  // item ID as user-defined data.
		  Button detailsField = new Button("show details");
		  detailsField.setData(itemId);
		  detailsField.addClickListener(new Button.ClickListener()
		  {
		  
	    private static final long serialVersionUID = 1L;

		public void buttonClick(ClickEvent event) 
		{
		  // Get the item identifier from the user-defined data
			  Integer iid = (Integer)event.getButton().getData();
			  Notification.show("Link " +
			  iid.intValue() + " clicked.");
	     }
			  });
			  detailsField.addStyleName("link");
			  // Create the table row.
			  table.addItem(new Object[] {sumField, transferredField,
			  commentsField, detailsField},
			  itemId);
			  }
			  // Show just three rows because they are so high.
			  table.setPageLength(3);
	  }
*/
    
//******************************************************************END OF EDIT EXSITING DIARY*********************************************************************

		
//******************************************************************EDIT EXSITING TRAINING*********************************************************************

		
		
//******************************************************************END OF EDIT EXSITING TRAINING********************************************************************

//******************************************************************LOGOUT*********************************************************************
		
		logoutLayout.addComponent(logoutButton);

//******************************************************************END OF LOGOUT*********************************************************************

		
	  setCompositionRoot(tabsheet);
	 

//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Button Actions	&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&  
	 
//******************************************************************Action of 'Save new diary' button ************************************************	 
	  button.addClickListener(new Button.ClickListener() 
	  {
		private static final long serialVersionUID = 1L;
		public void buttonClick(ClickEvent event) 
		  {
			 try
			 {
			
					log.debug("Now i'm getting values of fields in UI");
				  String sProgramType = String.valueOf(programType.getValue()); //programType
				  Date dDate = date.getValue(); //diaryCreationDate
				  String sNameOfTrainingDiary = String.valueOf(textfield.getValue()); //diaryDescription
				  String sDescription = String.valueOf(area.getValue());
				  
				  DiaryBean diary = new DiaryBean();
				  log.debug("Now i try to save date ");
				  DiaryBean.SaveDiary(sProgramType, dDate, sDescription, sNameOfTrainingDiary);
				  
			 }
			 catch(Exception e)
			 {
				 
				 log.debug("Zapis dziennika nie powiod³ siê");
				 log.debug(e.getMessage());
				 e.printStackTrace();
			 }
		  
		  }
	  }
	  );
//******************************************************************END OF Action of 'Save new diary' button ************************************************	 

//******************************************************************Action of 'Add new training' button ************************************************	 

	  //buttonInSecondTab
	  buttonInSecondTab.addClickListener(new Button.ClickListener() 
	  {
		private static final long serialVersionUID = 1L;
		public void buttonClick(ClickEvent event) 
		  {
			 try
			 {
			
				  log.debug("Now i'm getting values of fields in UI - Add new training into existing diary tab");
				  String choosedDiary = String.valueOf(selectSecondTab.getValue());
				  String description = String.valueOf(textfieldSecondTab.getValue());
				  
				  log.debug("Now i try to save date ");
				  Training.SaveTraining(choosedDiary, description);
			 }
			 catch(Exception e)
			 {
				 
				 log.debug("Zapis dziennika nie powiod³ siê");
				 log.debug(e.getMessage());
				 e.printStackTrace();
			 }
		  
		  }
	  }
	  ); 
//******************************************************************END OF Action of 'Add new training' button ************************************************	 


//******************************************************************Action of 'Add new training program' button ************************************************	 

	  //buttonInSecondTab
	  buttonInThirdTab.addClickListener(new Button.ClickListener() 
	  {
		private static final long serialVersionUID = 1L;
		public void buttonClick(ClickEvent event) 
		  {
			 try
			 {
				  log.debug("Now i'm getting values of fields in UI - Add new training type");

				  String programName = "Test";
				  String programDescription = String.valueOf(textfieldThirdTab.getValue());
				  String trainingType = String.valueOf(selectThirdTab.getValue());
				  
				  log.debug("Now i try to save date ");
				  ProgramType.SaveProgram(programName, programDescription, trainingType);
			 }
			 catch(Exception e)
			 {
				 
				 log.debug("Zapis dziennika nie powiod³ siê");
				 log.debug(e.getMessage());
				 e.printStackTrace();
			 }
		  
		  }
	  }
	  ); 
	  
//******************************************************************END OF Action of 'Add new training program' button ************************************************	 

	  
	  
//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Button Actions	&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&  
}

    @Override
    public void enter(ViewChangeEvent event) 
    {
    	tabsheet.addTab(verticalViewCreateNewDiary).setCaption("Create new Diary");
    	tabsheet.addTab(verticalAddNewTrainingIntoExistingDiary).setCaption("Add new training session into existing diary");
    	tabsheet.addTab(verticalAddNewTrainingProgram).setCaption("Add new training program");
    	tabsheet.addTab(label4).setCaption("Edit existing diary");
    	tabsheet.addTab(label5).setCaption("Edit existing training");
    	tabsheet.addTab(logoutLayout).setCaption("Logout");
    	
        // Get the user name from the session
        //String username = String.valueOf(getSession().getAttribute("user"));

       // And show the username
      //  text.setValue("Hello " + username);
    //-----------------------------------------------------
    	
    }
}