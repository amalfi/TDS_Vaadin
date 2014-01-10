package com.trainingdiary.vaadin.ui;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.trainingdiary.DiaryBean;
import com.trainingdiary.ProgramType;
import com.trainingdiary.Training;
import com.trainingdiary.tools.ButtonActions;
import com.vaadin.data.Property;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Field.ValueChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SimpleLoginMainView extends CustomComponent implements View  //tutaj dodajesz elementy widoczne po zalogowaniu
{ 
	//Main view after dialog
	static Logger log = Logger.getLogger(DiaryBean.class);
	private static final long serialVersionUID = 1L;
	public static final String NAME = "";
	TabSheet tabsheet = new TabSheet();
	Window window = new Window();
	@SuppressWarnings("deprecation")
	Notification notification2 = new Notification("At least one of required fields is empty",Notification.TYPE_WARNING_MESSAGE);
	@SuppressWarnings("deprecation")
	Notification emptyTrainingTemplateNotification = new Notification("Training template list shouldnt be empty",Notification.TYPE_ERROR_MESSAGE);
	Notification successfullEditedTrainingNotification = new Notification("Training edited successfully",Notification.TYPE_HUMANIZED_MESSAGE);
	Notification successfullEditedDiaryNotification = new Notification("Training edited successfully",Notification.TYPE_HUMANIZED_MESSAGE);
	Notification successfullAddedTrainingToDiary = new Notification("Training added to diary successfully",Notification.TYPE_HUMANIZED_MESSAGE);	
	final Window window2 = new Window();
    Table table2= new Table();
//------------------------------------------- ------------------------------------------- Layouts in each tab 
	
  VerticalLayout verticalViewCreateNewDiary = new VerticalLayout(); //VerticalLayout of 'CreateNewDiary' tab
  VerticalLayout verticalAddNewTrainingIntoExistingDiary = new VerticalLayout(); //VerticalLayout of 'Add new training into existing diary' tab
  VerticalLayout verticalAddNewTrainingProgram = new VerticalLayout();
  VerticalLayout verticalEditExistingTraining = new VerticalLayout();
  VerticalLayout editExistingDiary = new VerticalLayout();
  VerticalLayout logoutLayout = new VerticalLayout();
//--------------------------------------------------------------------------------------  Split panels to each tab (UserAccount)
	
	HorizontalSplitPanel verticalViewCreateNewDiarySplitPanel = new HorizontalSplitPanel();
	HorizontalSplitPanel verticalAddNewTrainingIntoExistingDiarySplitPanel = new HorizontalSplitPanel();
	HorizontalSplitPanel verticalAddNewTrainingProgramSplitPanel = new HorizontalSplitPanel();
	HorizontalSplitPanel verticalEditExistingTrainingSplitPanel = new HorizontalSplitPanel();
	HorizontalSplitPanel editExistingDiarySplitPanel = new HorizontalSplitPanel();
	
//------------------------------------------- ------------------------------------------- Components of FirstTab

  TextField textfield = new TextField("Name");
  ComboBox select = new ComboBox("Training Programs templates list");
  DateField date = new DateField("Date");
  RichTextArea area = new RichTextArea("Description");
 // CustomTabSheet ctb = new CustomTabSheet();
  
  Label label = new Label();
  Label label2 = new Label();
  Label label3 = new Label();
  Label label4 = new Label();
  Label label5 = new Label();
  Label label6 = new Label();
  //--------------------------------------------------- -------------------------------------------Helping labels
  Label datelabel = new Label("");
  Label descriptionlabel = new Label("");
  Label programType = new Label("");
  //---------------------------------------------------
  Button button = new Button("Save new diary");
//-------------------------------------------
  String sProgramType;
  Date dDate;
  String sNameOfTrainingDiary;
  String sDescription;
  
//------------------------------------------- -------------------------------------------Components of SecondTab
  
  ComboBox selectTrainingProgramInSecondTab = new ComboBox("Training programs list");
  com.vaadin.ui.RichTextArea textfieldSecondTab = new com.vaadin.ui.RichTextArea("Training Description");
  ComboBox selectSecondTab = new ComboBox("Training diaries list");
  DateField dateOfTraining = new DateField("Date of Training");
  Button loadDiaryTemplate = new Button("Load diary template");
  Button buttonInSecondTab = new Button("Add new training");

//--------------------------------------------
  
//------------------------------------------- -------------------------------------------Components of ThirdTab

  TextField trainingProgramName = new TextField("Training program name");
  TextField numberOfExcersises = new TextField("Number of excersises");
  TextField numberOfSetsOfEachExcersise = new TextField("Number of sets");
  TextField breakBetweenSets = new TextField("Break between sets");
  com.vaadin.ui.TextArea textfieldThirdTab = new com.vaadin.ui.TextArea("Traning program description");
  Button buttonInThirdTab = new Button("Add new training program");

//--------------------------------------------
  

//------------------------------------------- -------------------------------------------Components of FourthTab
  final Table table = new Table();
  final CheckBox switchEditable = new CheckBox("Editable");
  Button saveChangesInEditedDiary = new Button("Save changes");
 
//------------------------------------------- -------------------------------------------Components of FifthTab
  	
  final Table tableEditTraining = new Table();
 // final CheckBox switchEditableTrainingTable = new CheckBox("Editable");
  Button saveChangesInEditedTraining = new Button("Save changes");
   

//------------------------------------------- -------------------------------------------Components of SixsthTab
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
  
    @SuppressWarnings({ "static-access", "deprecation" })
	public SimpleLoginMainView()  //here we can add action listeners etc
    {
//******************************************************************CREATE NEW DIARY TAB******************************************************************   	 
     // setCompositionRoot(new CssLayout(text, logout));  //tutaj w parametrachSetCompositionRoot ustawiamy komponenty jakie maja byc wstawione do widoku, 
    	/*ctb.setVisible(true);
    	ctb.setEnabled(true);*/

     Training training = new Training();
    	
      ArrayList<String> trainingsPrograms = new ArrayList<String>();
      trainingsPrograms = ProgramType.LoadProgramTypesToArrayList();
	  
      for(int i=0; i<trainingsPrograms.size(); i++)
      {
    	  select.addItem(trainingsPrograms.get(i));
      }

  //--------------------------------------------------------- -------------------------------------------Adding components to vertical layout 	
	  verticalViewCreateNewDiary.addComponent(textfield);
	  	verticalViewCreateNewDiary.setComponentAlignment(textfield, Alignment.MIDDLE_LEFT);
	 
	  verticalViewCreateNewDiary.addComponent(datelabel);
	  	verticalViewCreateNewDiary.setComponentAlignment(datelabel, Alignment.MIDDLE_LEFT);
  	 
	  verticalViewCreateNewDiary.addComponent(date);
	  	verticalViewCreateNewDiary.setComponentAlignment(date, Alignment.MIDDLE_LEFT);
	  	
  	  verticalViewCreateNewDiary.addComponent(programType);
  	  	verticalViewCreateNewDiary.setComponentAlignment(programType, Alignment.MIDDLE_LEFT);
  	
 /* 	  verticalViewCreateNewDiary.addComponent(select);
  	  	verticalViewCreateNewDiary.setComponentAlignment(select, Alignment.MIDDLE_LEFT);
	  */
	  verticalViewCreateNewDiary.addComponent(descriptionlabel);
	  	verticalViewCreateNewDiary.setComponentAlignment(descriptionlabel, Alignment.MIDDLE_LEFT);
	 
	  verticalViewCreateNewDiary.addComponent(area);
	  	verticalViewCreateNewDiary.setComponentAlignment(area, Alignment.MIDDLE_LEFT);
		 
	  verticalViewCreateNewDiary.addComponent(button);
	  	verticalViewCreateNewDiary.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
		 
	  //---------------------------------------------------------
	  
//******************************************************************END OF CREATE NEW DIARY TAB******************************************************************   	

//******************************************************************CREATE ADD NEW TRAINING TAB******************************************************************   	 
	  HorizontalLayout fittingLayout = new HorizontalLayout();
	  fittingLayout.addComponent(verticalAddNewTrainingIntoExistingDiary);
	  
	  //-----Here we add content to selectSecondTab
	  HashMap<String, DiaryBean> allDiaries =  DiaryBean.LoadDiaries();
	  
	  for(int i=0; i<allDiaries.size(); i++)
	  {
		  String currentDiary = String.valueOf(allDiaries.get(String.valueOf(i)).getNameOfDiary());
		  selectSecondTab.addItem(currentDiary);
	  }
	  //-------------------------------------------
	  
	  DiaryBean diarybean = new DiaryBean();  	
  	 verticalAddNewTrainingIntoExistingDiary.addComponent(select);
 	  verticalAddNewTrainingIntoExistingDiary.setComponentAlignment(select, Alignment.MIDDLE_LEFT);
	 
 	 verticalAddNewTrainingIntoExistingDiary.addComponent(loadDiaryTemplate);
	  verticalAddNewTrainingIntoExistingDiary.setComponentAlignment(loadDiaryTemplate, Alignment.MIDDLE_CENTER);
	
  	  
	  verticalAddNewTrainingIntoExistingDiary.addComponent(textfieldSecondTab);
	  verticalAddNewTrainingIntoExistingDiary.setComponentAlignment(textfieldSecondTab, Alignment.MIDDLE_LEFT);
	  //textfieldSecondTab.setRequired(true);
	  
	  verticalAddNewTrainingIntoExistingDiary.addComponent(selectSecondTab);
	  verticalAddNewTrainingIntoExistingDiary.setComponentAlignment(selectSecondTab, Alignment.MIDDLE_LEFT);
	  
	  verticalAddNewTrainingIntoExistingDiary.addComponent(dateOfTraining);
	  verticalAddNewTrainingIntoExistingDiary.setComponentAlignment(dateOfTraining, Alignment.MIDDLE_LEFT);
	  
	  verticalAddNewTrainingIntoExistingDiary.addComponent(buttonInSecondTab);
	  verticalAddNewTrainingIntoExistingDiary.setComponentAlignment(buttonInSecondTab, Alignment.MIDDLE_CENTER);
	  
//******************************************************************END OF CREATE NEW DIARY TAB******************************************************************   	 

//******************************************************************ADD NEW TRAINING PROGRAM*********************************************************************

	verticalAddNewTrainingProgram.addComponent(trainingProgramName);	
		verticalAddNewTrainingProgram.setComponentAlignment(trainingProgramName, Alignment.MIDDLE_LEFT);
			trainingProgramName.addValidator(new RegexpValidator("", "Input should be at least 5 characters long."));
				trainingProgramName.setRequired(true);
		
	verticalAddNewTrainingProgram.addComponent(numberOfExcersises);
		verticalAddNewTrainingProgram.setComponentAlignment(numberOfExcersises, Alignment.MIDDLE_LEFT);
				numberOfExcersises.addValidator(new IntegerRangeValidator("Number of excersises must be an Integer value",1,30));
				numberOfExcersises.setRequired(true);;
	
	verticalAddNewTrainingProgram.addComponent(numberOfSetsOfEachExcersise);
		verticalAddNewTrainingProgram.setComponentAlignment(numberOfSetsOfEachExcersise, Alignment.MIDDLE_LEFT);
				numberOfSetsOfEachExcersise.addValidator(new IntegerRangeValidator("Number of sets must be an Integer value", 1, 40 ));
				numberOfSetsOfEachExcersise.setRequired(true);
	
	verticalAddNewTrainingProgram.addComponent(breakBetweenSets);
		verticalAddNewTrainingProgram.setComponentAlignment(breakBetweenSets, Alignment.MIDDLE_LEFT);
			breakBetweenSets.addValidator(new IntegerRangeValidator("Rest time between sets should be and integer value (seconds) ", 10, 500));
			breakBetweenSets.setRequired(true);
			
	verticalAddNewTrainingProgram.addComponent(textfieldThirdTab);
		verticalAddNewTrainingProgram.setComponentAlignment(textfieldThirdTab, Alignment.MIDDLE_LEFT);	
			textfieldThirdTab.setRequired(true);;
		
    verticalAddNewTrainingProgram.addComponent(buttonInThirdTab);
		verticalAddNewTrainingProgram.setComponentAlignment(buttonInThirdTab, Alignment.MIDDLE_CENTER);
	  
	  //   viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
	  
//******************************************************************END OF NEW TRAINING PROGRAM*********************************************************************
//******************************************************************EDIT EXSITING DIARY*********************************************************************
		/* Define the names and data types of columns.
		* The "default value" parameter is meaningless here. */
		 ArrayList<DiaryBean> dzienniki = diarybean.loadDiariesArray();
		
		table.addContainerProperty("Id", Integer.class, null);
		table.addContainerProperty("Name", String.class, null);
		table.addContainerProperty("Date", String.class, null);
		table.addContainerProperty("Training Program", String.class, null);
		table.addContainerProperty("Description", String.class, null);
		
		for(int j=0; j<dzienniki.size(); j++)	
		{
			Integer id = Integer.valueOf(String.valueOf(dzienniki.get(j).getId()));
			String nameOfDiary = String.valueOf(dzienniki.get(j).getNameOfDiary());
			String diaryCreationDate = String.valueOf(dzienniki.get(j).getDiaryCreationDate());
			String choosedDiary = String.valueOf(dzienniki.get(j).getChoosedTrainingPlan());
			String diaryDescription =  String.valueOf(dzienniki.get(j).getDiaryDescription());	
			
			table.addItem(new Object[] {id, nameOfDiary, diaryCreationDate , diaryDescription, choosedDiary ,},j);
		}
		/*End of adding rows to table*/

		final CheckBox switchEditable = new CheckBox("Editable");
		switchEditable.setImmediate(true);
		
		
		switchEditable.addValueChangeListener
		(
			new Property.ValueChangeListener()
			{
				private static final long serialVersionUID = 1L;
	
					@Override
					public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) 
					{
					table.setEditable(((Boolean)event.getProperty()
					.getValue()).booleanValue());
					}
			}
		);
		
		editExistingDiary.addComponent(switchEditable);
		editExistingDiary.addComponent(table);
		editExistingDiary.addComponent(saveChangesInEditedDiary);
    
//******************************************************************END OF EDIT EXSITING DIARY*********************************************************************
//******************************************************************EDIT EXSITING TRAINING*********************************************************************
		ArrayList<Training>treningi = Training.LoadTrainings();	
		
		tableEditTraining.addContainerProperty("Id", Integer.class, null);
		tableEditTraining.addContainerProperty("Description", String.class, null);
		tableEditTraining.addContainerProperty("Choosed Diary", String.class, null);
		//tableEditTraining.addContainerProperty("Training date" , Date.class, null);
		for(int j=0; j<treningi.size(); j++)
		{
			Integer id = treningi.get(j).getId();
			String description = String.valueOf(treningi.get(j).getDescription());
			String choosedDiary = String.valueOf(treningi.get(j).getChoosedDiary());
			
			tableEditTraining.addItem(new Object[] {id,description, choosedDiary},j);
		}
	 
		final CheckBox switchEditableTrainingTable = new CheckBox("Edit training");
		switchEditableTrainingTable.addValueChangeListener(
		new Property.ValueChangeListener() 
		{
			private static final long serialVersionUID = 1L;

		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) 
		{		
			tableEditTraining.setEditable(((Boolean)event.getProperty().getValue()).booleanValue());		
		}
		});
		
		
		 verticalEditExistingTraining.addComponent(switchEditableTrainingTable);
		 verticalEditExistingTraining.addComponent(tableEditTraining);
		 verticalEditExistingTraining.addComponent(saveChangesInEditedTraining);
		


		 saveChangesInEditedTraining.addClickListener(new Button.ClickListener() 
		  {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) 
			  {
				
				try
				{
					ButtonActions.saveChangesIntoEditedTrainings(tableEditTraining);
					successfullEditedTrainingNotification.setDelayMsec(600);
					successfullEditedTrainingNotification.setPosition(Position.MIDDLE_CENTER);
					successfullEditedTrainingNotification.show(Page.getCurrent());

				}
				catch (ParseException e) 
				{
					log.debug(e.getCause(),e);
					e.printStackTrace();
				}
			  }
		  }
		  ); 
		
//******************************************************************END OF EDIT EXSITING TRAINING********************************************************************

//******************************************************************LOGOUT*********************************************************************
		logoutLayout.addComponent(logoutButton);
		logoutLayout.setComponentAlignment(logoutButton, Alignment.MIDDLE_CENTER);
//******************************************************************END OF LOGOUT*********************************************************************
	  setCompositionRoot(tabsheet);
//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Button Actions	&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&  
	 
//******************************************************************Action of 'Save new diary' button ************************************************	 
	  button.addClickListener(new Button.ClickListener() 
	  {
		private static final long serialVersionUID = 1L;
		public void buttonClick(ClickEvent event) 
		  { 
			  
			ButtonActions.SaveNewDiaryAction(button, select, date, textfield, area);
		  	successfullEditedTrainingNotification.setDelayMsec(600);
		  	successfullEditedTrainingNotification.setPosition(Position.MIDDLE_CENTER);
		  	successfullEditedTrainingNotification.show(Page.getCurrent());
    	  
		  }
	  }
	  );

//******************************************************************END OF Action of 'Save new diary' button ************************************************	 
//******************************************************************Action of 'Add new training' button ************************************************	 
	  //buttonInSecondTab
	  
	  //textfieldSecondTab
	  
	  loadDiaryTemplate.addClickListener(new Button.ClickListener() 
	  {
		@Override
		public void buttonClick(ClickEvent event) 
		{
			final String sSelectedTrainingTemplate= String.valueOf(select.getValue()); //value from 'Training Programs templates list'
			
			if(sSelectedTrainingTemplate.equals("")|| sSelectedTrainingTemplate==null)
			{
				emptyTrainingTemplateNotification.setDelayMsec(2500);
				emptyTrainingTemplateNotification.setPosition(Position.MIDDLE_CENTER);
				emptyTrainingTemplateNotification.show(Page.getCurrent());
			}
			ButtonActions.LoadDiaryTemplate(textfieldSecondTab, sSelectedTrainingTemplate);
		}
	  });
	  
	  
	  buttonInSecondTab.addClickListener(new Button.ClickListener() 
	  {
		private static final long serialVersionUID = 1L;
		public void buttonClick(ClickEvent event) 
		  {
			  ButtonActions.SaveNewTrainingIntoExistingDiaryAction(selectSecondTab, textfieldSecondTab, dateOfTraining);
			    
			    successfullAddedTrainingToDiary.setDelayMsec(600);
			    successfullAddedTrainingToDiary.setPosition(Position.MIDDLE_CENTER);
			    successfullAddedTrainingToDiary.show(Page.getCurrent());
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
                 //ButtonActions.SaveNewTrainingType(textfieldThirdTab, selectTrainingProgramInSecondTab);    
        	  String sTrainingProgramName = trainingProgramName.getValue();
        	  String sNumberOfExcersises = numberOfExcersises.getValue();
        	  String sNumberOfSetsOfEachExcersise = numberOfSetsOfEachExcersise.getValue();
        	  String sBreakBetweenSets = breakBetweenSets.getValue();
        	  String sTextFieldThirdTab = textfieldThirdTab.getValue();
        	  
        	  if (AddNewTrainingTemplate.fieldValidator(sTrainingProgramName, sNumberOfExcersises, sNumberOfSetsOfEachExcersise, sBreakBetweenSets, sTextFieldThirdTab, table2, window2)==true)
        	  {
        	  //(String trainingProgramName, String numberOfExcersises, String numberOfSets, String breakBetweenSets, Table table, Window window)
	        	  AddNewTrainingTemplate.AddContentToEditableTrainingTemplate( sTrainingProgramName, sNumberOfExcersises, sNumberOfSetsOfEachExcersise, sBreakBetweenSets, sTextFieldThirdTab, table2, window2);
	      		  UI.getCurrent().addWindow(window2);
	      		  //successfullySavedTrainingTemplate,
        	  }
        	 else
        	  {
        		
        			notification2.setDelayMsec(600);
					notification2.setPosition(Position.MIDDLE_CENTER);
					notification2.show(Page.getCurrent());
        	  }
        	  
      }
      }
      ); 
	  
//******************************************************************END OF Action of 'Add new training program' button ************************************************	 
//******************************************************************Action of 'Save changes' (Edit existing diary) button ************************************************	 

	  saveChangesInEditedDiary.addClickListener(new Button.ClickListener() 
	  {
		private static final long serialVersionUID = 1L;
		public void buttonClick(ClickEvent event) 
		  {
			
			try
			{
				ButtonActions.saveChangesIntoEditedDiaries(table);
				successfullEditedDiaryNotification.setDelayMsec(600);
				successfullEditedDiaryNotification.setPosition(Position.MIDDLE_CENTER);
				successfullEditedDiaryNotification.show(Page.getCurrent());
			}
			catch (ParseException e) 
			{
				log.debug(e.getCause(),e);
				e.printStackTrace();
			}
		  }
	  }
	  ); 
	  
//******************************************************************END OF Action of 'Save changes' (Edit existing diary) button ************************************************	 	  
//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& Button Actions	&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&  
}

    @Override
    public void enter(ViewChangeEvent event) 
    {
    	UserProfile usp = new UserProfile();
    	UserProfile usp2 = new UserProfile();
    	UserProfile usp3 = new UserProfile();
    	UserProfile usp4 = new UserProfile();
    	UserProfile usp5 = new UserProfile();
    	
    	
    	verticalViewCreateNewDiarySplitPanel.setLocked(true);
    	verticalViewCreateNewDiary.setMargin(true);
    	verticalViewCreateNewDiarySplitPanel.addComponent(verticalViewCreateNewDiary);
    	verticalViewCreateNewDiarySplitPanel.addComponent(usp);
    	
    	verticalAddNewTrainingIntoExistingDiarySplitPanel.setLocked(true);
    	verticalAddNewTrainingIntoExistingDiary.setMargin(true);
    	verticalAddNewTrainingIntoExistingDiarySplitPanel.addComponent(verticalAddNewTrainingIntoExistingDiary);
    	verticalAddNewTrainingIntoExistingDiarySplitPanel.addComponent(usp2);
    	
    	verticalAddNewTrainingProgramSplitPanel.setLocked(true);
    	verticalAddNewTrainingProgram.setMargin(true);
    	verticalAddNewTrainingProgramSplitPanel.addComponent(verticalAddNewTrainingProgram);
    	verticalAddNewTrainingProgramSplitPanel.addComponent(usp3);
    	
    	editExistingDiary.setMargin(true);
    	editExistingDiarySplitPanel.setLocked(true);
    	editExistingDiarySplitPanel.addComponent(editExistingDiary);
    	editExistingDiarySplitPanel.addComponent(usp4);
    	
    	
    	verticalEditExistingTraining.setMargin(true);
    	verticalEditExistingTrainingSplitPanel.setLocked(true);
    	verticalEditExistingTrainingSplitPanel.addComponent(verticalEditExistingTraining);
    	verticalEditExistingTrainingSplitPanel.addComponent(usp5);
    	
    	//--------------------------------------------------------------------------------------------------------
    	
    	
    	tabsheet.addTab(verticalViewCreateNewDiarySplitPanel).setCaption("Create new Diary");
    	tabsheet.addTab(verticalAddNewTrainingIntoExistingDiarySplitPanel).setCaption("Add new training session into existing diary");
    	tabsheet.addTab(verticalAddNewTrainingProgramSplitPanel).setCaption("Add new training program");
    	tabsheet.addTab(editExistingDiarySplitPanel).setCaption("Edit existing diary");
    	tabsheet.addTab(verticalEditExistingTrainingSplitPanel).setCaption("Edit existing training");
    	tabsheet.addTab(logoutLayout).setCaption("My account");
    	
        // Get the user name from the session
        //String username = String.valueOf(getSession().getAttribute("user"));

       // And show the username
      //  text.setValue("Hello " + username);
    //-----------------------------------------------------
    	
    }
}