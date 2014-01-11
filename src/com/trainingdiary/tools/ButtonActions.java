package com.trainingdiary.tools;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibm.icu.text.SimpleDateFormat;
import com.trainingdiary.DiaryBean;
import com.trainingdiary.ProgramType;
import com.trainingdiary.Training;
import com.trainingdiary.database.HibernateUtil;
import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DateField;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class ButtonActions extends CustomComponent
{

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(ButtonActions.class);
	static HashMap<String,ProgramType> selectedTrainingTemplates  = new HashMap<String,ProgramType>();
	
	public static boolean SaveNewDiaryAction(Button button,  final ComboBox programType, final DateField date, final TextField textfield, final RichTextArea area )
	{							//programType , date
		boolean diaryExist=false;
				try
				 {
					 
					 log.debug("Now i'm getting values of fields in UI");
					  String sProgramType = String.valueOf(programType.getValue()); //programType
					  Date dDate = date.getValue(); //diaryCreationDate
					  String sNameOfTrainingDiary = String.valueOf(textfield.getValue()); //diaryDescription
					  String sDescription = String.valueOf(area.getValue());
					  log.debug("Now i try to save date ");
					  diaryExist=DiaryBean.SaveDiary(sProgramType, dDate, sDescription, sNameOfTrainingDiary);  
				 }
				 catch(Exception e)
				 { 
					 log.debug("Zapis dziennika nie powiod³ siê");
					 log.debug(e.getMessage());
					 e.printStackTrace();
				 }
				 return diaryExist;
	}
	
	public static void SaveNewTrainingIntoExistingDiaryAction(final ComboBox selectSecondTab, final RichTextArea textfieldSecondTab, DateField dateOfTraining)
	{
		try
		 {
			  log.debug("Now i'm getting values of fields in UI - Add new training into existing diary tab");
			  String choosedDiary = String.valueOf(selectSecondTab.getValue());
			  String description = String.valueOf(textfieldSecondTab.getValue());
			  Date dDate = dateOfTraining.getValue();
			  log.debug("Now i try to save date ");
			  Training.SaveTraining(choosedDiary, description, dDate);
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
                 ProgramType.SaveProgram(programName, programDescription, trainingType, trainingType, trainingType);
         }
         catch(Exception e)
         {
                
                 log.debug("Zapis dziennika nie powiodl sie");
                 log.debug(e.getMessage());
                 e.printStackTrace();
         }
	}
	
	
	public static RichTextArea LoadDiaryTemplate(RichTextArea content, String nameOfTrainingProgram)
	{
		//Function which would be update content of RichTextArea field, with template date from database
		String sContentOfRichTextArea=generateStringWithTrainingTemplate(nameOfTrainingProgram);
		
		content.setValue(sContentOfRichTextArea);
		return content;
	}
	//List currentdiaries = session.createQuery("from DiaryBean where currentdiaryuser = :currentuser").setParameter("currentuser", currentUser).list(); //is worth to remember (common mistake) - when you use want to select from table, use bean name, not table name
    
	public static HashMap<String, ProgramType> LoadSelectedTrainingProgramTemplate(String nameOfTrainingProgram)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try 
        { 
        transaction = session.beginTransaction();
        //List programtypes = session.createQuery("from ProgramType where programname = nameOfProgram ").setParameter("nameOfProgram", nameOfTrainingProgram).list();
        String hql = "from ProgramType pt where pt.trainingProgramName= '"+ nameOfTrainingProgram +"'";
        List programtypes = session.createQuery(hql).list();
            for (Iterator iterator = programtypes.iterator(); iterator.hasNext();)
            {
                ProgramType programType = (ProgramType) iterator.next();
                selectedTrainingTemplates.put(programType.getTrainingProgramName().toString(), programType);
            }          	
            transaction.commit();
        } 
        catch (HibernateException e) 
        {
            transaction.rollback();
            e.printStackTrace();  
            log.debug(e.getMessage());
        } 
        finally 
        {
            session.close();
        }
		return selectedTrainingTemplates;
	}
	
	
	public static String generateStringWithTrainingTemplate (String nameOfTrainingProgram)
	{
		HashMap<String, ProgramType> selectedTrainingTemplate = LoadSelectedTrainingProgramTemplate(nameOfTrainingProgram);
		String sTrainingDescriptionFieldContent="";
		
		for(int i=0; i<selectedTrainingTemplate.size(); i++)
		{
			
			String sProgramName=String.valueOf(selectedTrainingTemplate.get(nameOfTrainingProgram).getProgramName());
			//sTrainingDescriptionFieldContent=sTrainingDescriptionFieldContent + sProgramName + "<br><br>";   //we add to sTrainingDescriptionField program name
			
			String sNumberOfSets=String.valueOf(selectedTrainingTemplate.get(nameOfTrainingProgram).getNumberOfSets());
			//sTrainingDescriptionFieldContent=sTrainingDescriptionFieldContent + sNumberOfSets + "<br><br>";  //we add to sTrainingDescriptionField number of sets
			
			String sSelectedTraining=String.valueOf(selectedTrainingTemplate.get(nameOfTrainingProgram).getProgramDescription());
			sTrainingDescriptionFieldContent=sTrainingDescriptionFieldContent + sSelectedTraining + "<br><br>";
			
			String sGetTrainingType=String.valueOf(selectedTrainingTemplate.get(nameOfTrainingProgram).getTrainingType());
			//sTrainingDescriptionFieldContent=sTrainingDescriptionFieldContent + sGetTrainingType + "<br><br>";
			
			
			sTrainingDescriptionFieldContent=sTrainingDescriptionFieldContent.replace("@programname@", sProgramName).replace("@trainingtype@", sGetTrainingType);
		}
		/*
		 	<div align="center">@programname@<br></div>
			<br>Training Type : @trainingtype@<br>
			<br>@excersises_name@ x @numberofsets@ 
			<br>@breakbetweensets@ second break between sets<br>
			<br>

		 */
		
		return sTrainingDescriptionFieldContent;
	}
	
	@SuppressWarnings("deprecation")
	public static void saveChangesIntoEditedDiaries(Table table) throws ParseException // function called from Button listener (Save changes in existed diary)
	{
	
		for (Object id : table.getItemIds()) 
		{
            // Get the Property representing a cell
			Property idProperty = table.getContainerProperty(id, "Id");
            Property nameProperty = table.getContainerProperty(id,"Name");
            Property dateProperty = table.getContainerProperty(id, "Date");
            Property trainingProgramProperty = table.getContainerProperty(id, "Training Program");
            Property descriptionProperty = table.getContainerProperty(id, "Description");
            
            // Get the value of the Property
            Object idValue = idProperty.getValue();
            Object nameValue = nameProperty.getValue();
            Object dateValue = dateProperty.getValue();
            Object trainingProgramValue = trainingProgramProperty.getValue();
            Object descriptionValue = descriptionProperty.getValue();
            
            //--
            String string = String.valueOf(dateValue);
            string=string.substring(0, 9);
            Date date = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH).parse(string);
            //--
          DiaryBean.UpdateDiary(Integer.valueOf(String.valueOf(idValue)), String.valueOf(trainingProgramValue), date , String.valueOf(descriptionValue), String.valueOf(nameValue));
        }
	}
	
	@SuppressWarnings("deprecation")
	public static void saveChangesIntoEditedTrainings(Table table) throws ParseException // function called from Button listener (Save changes in existed diary)
	{
	log.debug("SaveChangsIntoEditedTrainings function");
		for (Object id : table.getItemIds()) 
		{
            // Get the Property representing a cell
			Property idProperty = table.getContainerProperty(id,"Id");
            Property descriptionProperty = table.getContainerProperty(id,"Description");
            Property choosedDiaryProperty = table.getContainerProperty(id, "Choosed Diary");
    
            // Get the value of the Property
            Object idValue = idProperty.getValue();
            Object descriptionValue = descriptionProperty.getValue();
            Object choosedDiaryValue = choosedDiaryProperty.getValue();        
            //--
            Integer Id = Integer.valueOf(idValue.toString());
            String sDescription = String.valueOf(descriptionValue);
            String sChoosedDiary = String.valueOf(choosedDiaryValue);
            
            log.debug("Wartosci pobrane z tabeli 'Edit training " + sDescription);
            
          Training.UpdateTrainings(Id,sDescription, sChoosedDiary);
        
		}
	}
	
	
}
