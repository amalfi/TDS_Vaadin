package com.trainingdiary.vaadin.ui;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.trainingdiary.DiaryBean;
import com.trainingdiary.ProgramType;
import com.trainingdiary.Training;
import com.trainingdiary.tools.ButtonActions;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
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
//--------------------------------------------------------------------------------------  Split panels to each tab (UserAccount)
	
	HorizontalSplitPanel verticalViewCreateNewDiarySplitPanel = new HorizontalSplitPanel();
	HorizontalSplitPanel verticalAddNewTrainingIntoExistingDiarySplitPanel = new HorizontalSplitPanel();
	HorizontalSplitPanel verticalAddNewTrainingProgramSplitPanel = new HorizontalSplitPanel();
	HorizontalSplitPanel verticalEditExistingTrainingSplitPanel = new HorizontalSplitPanel();
	HorizontalSplitPanel editExistingDiarySplitPanel = new HorizontalSplitPanel();
	
//------------------------------------------- ------------------------------------------- Components of FirstTab

  TextField textfield = new TextField("Name");
  ComboBox select = new ComboBox("Training Programs list");
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
  final CheckBox switchEditable = new CheckBox("Editable");
  Button saveChangesInEditedDiary = new Button("Save changes");
 
//------------------------------------------- -------------------------------------------Components of FifthTab
  	
  final Table tableEditTraining = new Table();
  final CheckBox switchEditableTrainingTable = new CheckBox("Editable");
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
  
    @SuppressWarnings("static-access")
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
	  	verticalViewCreateNewDiary.setComponentAlignment(textfield, Alignment.MIDDLE_CENTER);
	 
	  verticalViewCreateNewDiary.addComponent(datelabel);
	  	verticalViewCreateNewDiary.setComponentAlignment(datelabel, Alignment.MIDDLE_CENTER);
  	 
	  verticalViewCreateNewDiary.addComponent(date);
	  	verticalViewCreateNewDiary.setComponentAlignment(date, Alignment.MIDDLE_CENTER);
	  	
  	  verticalViewCreateNewDiary.addComponent(programType);
  	  	verticalViewCreateNewDiary.setComponentAlignment(programType, Alignment.MIDDLE_CENTER);
  	
  	  verticalViewCreateNewDiary.addComponent(select);
  	  	verticalViewCreateNewDiary.setComponentAlignment(select, Alignment.MIDDLE_CENTER);
	  
	  verticalViewCreateNewDiary.addComponent(descriptionlabel);
	  	verticalViewCreateNewDiary.setComponentAlignment(descriptionlabel, Alignment.MIDDLE_CENTER);
	 
	  verticalViewCreateNewDiary.addComponent(area);
	  	verticalViewCreateNewDiary.setComponentAlignment(area, Alignment.MIDDLE_CENTER);
		 
	  verticalViewCreateNewDiary.addComponent(button);
	  	verticalViewCreateNewDiary.setComponentAlignment(button, Alignment.MIDDLE_CENTER);
		 
	  //---------------------------------------------------------
	  
//******************************************************************END OF CREATE NEW DIARY TAB******************************************************************   	

//******************************************************************CREATE ADD NEW TRAINING TAB******************************************************************   	 
	  HorizontalLayout fittingLayout = new HorizontalLayout();
	  fittingLayout.addComponent(verticalAddNewTrainingIntoExistingDiary);
	  
	  //-----Here we add content to selectSecondTab
	  	DiaryBean diarybean = new DiaryBean();
	  	ArrayList<DiaryBean> dzienniki = diarybean.loadDiariesArray();
	  	
	  	for(int k=0; k<dzienniki.size(); k++)
	  	{
	  		selectSecondTab.addItem(String.valueOf(dzienniki.get(k).getNameOfDiary()));
	  	}
	  
	  verticalAddNewTrainingIntoExistingDiary.addComponent(textfieldSecondTab);
	  verticalAddNewTrainingIntoExistingDiary.setComponentAlignment(textfieldSecondTab, Alignment.MIDDLE_CENTER);
	  
	  verticalAddNewTrainingIntoExistingDiary.addComponent(selectSecondTab);
	  verticalAddNewTrainingIntoExistingDiary.setComponentAlignment(selectSecondTab, Alignment.MIDDLE_CENTER);
	  
	  verticalAddNewTrainingIntoExistingDiary.addComponent(buttonInSecondTab);
	  verticalAddNewTrainingIntoExistingDiary.setComponentAlignment(buttonInSecondTab, Alignment.MIDDLE_CENTER);
	  
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
		/* Define the names and data types of columns.
		* The "default value" parameter is meaningless here. */
		table.addContainerProperty("Name", String.class, null);
		table.addContainerProperty("Date", String.class, null);
		table.addContainerProperty("Training Program", String.class, null);
		table.addContainerProperty("Description", String.class, null);
		
		
		
		for(int j=0; j<dzienniki.size(); j++)
		{
			String nameOfDiary = String.valueOf(dzienniki.get(j).getNameOfDiary());
			String diaryCreationDate = String.valueOf(dzienniki.get(j).getDiaryCreationDate());
			String choosedDiary = String.valueOf(dzienniki.get(j).getChoosedTrainingPlan());
			String diaryDescription =  String.valueOf(dzienniki.get(j).getDiaryDescription());	
			
			table.addItem(new Object[] {nameOfDiary, diaryCreationDate , diaryDescription, choosedDiary ,},j);
		}
		/*End of adding rows to table*/

		final CheckBox switchEditable = new CheckBox("Editable");
		
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
		switchEditable.setImmediate(true);
		
		editExistingDiary.addComponent(switchEditable);
		editExistingDiary.addComponent(table);
		editExistingDiary.addComponent(saveChangesInEditedDiary);
    
//******************************************************************END OF EDIT EXSITING DIARY*********************************************************************
		tableEditTraining.addContainerProperty("Description", String.class, null);
		tableEditTraining.addContainerProperty("Choosed Diary", String.class, null);
		
		ArrayList<Training>treningi = Training.LoadTrainings();		
		
		for(int j=0; j<treningi.size(); j++)
		{
			String description = String.valueOf(treningi.get(j).getDescription());
			String choosedDiary = String.valueOf(treningi.get(j).getChoosedDiary());

			tableEditTraining.addItem(new Object[] {description, choosedDiary},j);
		}
		/*End of adding rows to table*/
		
	
		
		final CheckBox switchEditableTrainingTable = new CheckBox("Editable");
		this.switchEditableTrainingTable.addValueChangeListener(
		new Property.ValueChangeListener()
		{
			private static final long serialVersionUID = 1L;

		@Override
		public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
			tableEditTraining.setEditable(((Boolean)event.getProperty()
					.getValue()).booleanValue());
			
		}
		});
		switchEditable.setImmediate(true);
		
		 verticalEditExistingTraining.addComponent(switchEditableTrainingTable);
		 verticalEditExistingTraining.addComponent(tableEditTraining);
		 verticalEditExistingTraining.addComponent(saveChangesInEditedTraining);
		
//******************************************************************EDIT EXSITING TRAINING*********************************************************************

		
		
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
			  ButtonActions.SaveNewTrainingIntoExistingDiaryAction(selectSecondTab, textfieldSecondTab);
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
                 ButtonActions.SaveNewTrainingType(textfieldThirdTab, selectThirdTab);    
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
			JavaScript.getCurrent().execute("alert('Test')");
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
    	verticalViewCreateNewDiarySplitPanel.addComponent(verticalViewCreateNewDiary);
    	verticalViewCreateNewDiarySplitPanel.addComponent(usp);
    	
    	verticalAddNewTrainingIntoExistingDiarySplitPanel.setLocked(true);
    	verticalAddNewTrainingIntoExistingDiarySplitPanel.addComponent(verticalAddNewTrainingIntoExistingDiary);
    	verticalAddNewTrainingIntoExistingDiarySplitPanel.addComponent(usp2);
    	
    	verticalAddNewTrainingProgramSplitPanel.setLocked(true);
    	verticalAddNewTrainingProgramSplitPanel.addComponent(verticalAddNewTrainingProgram);
    	verticalAddNewTrainingProgramSplitPanel.addComponent(usp3);
    	
    	editExistingDiarySplitPanel.setLocked(true);
    	editExistingDiarySplitPanel.addComponent(editExistingDiary);
    	editExistingDiarySplitPanel.addComponent(usp4);
    	
    	
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