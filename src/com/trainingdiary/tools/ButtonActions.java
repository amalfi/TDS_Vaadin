package com.trainingdiary.tools;

import java.util.Date;

import org.apache.log4j.Logger;

import com.trainingdiary.DiaryBean;
import com.trainingdiary.ProgramType;
import com.trainingdiary.Training;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class ButtonActions extends CustomComponent
{

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(ButtonActions.class);
	
	public static void SaveNewDiaryAction(Button button,  final ComboBox programType, final DateField date, final TextField textfield, final RichTextArea area )
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
	
	public static void SaveNewTrainingIntoExistingDiaryAction(final ComboBox selectSecondTab, final TextArea textfieldSecondTab)
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
	
	

	public static void SaveNewTrainingType(TextArea textfieldThirdTab, ComboBox selectThirdTab)
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
                
                 log.debug("Zapis dziennika nie powiodl sie");
                 log.debug(e.getMessage());
                 e.printStackTrace();
         }
	}
	
	

	
}
