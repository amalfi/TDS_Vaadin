package com.trainingdiary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibm.icu.text.SimpleDateFormat;
import com.trainingdiary.database.HibernateUtil;
import com.trainingdiary.tools.OtherFunctions;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;


@javax.persistence.Entity
public class ProgramType implements Serializable 
{

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(ProgramType.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "MEDIUMINT NOT NULL AUTO_INCREMENT")
	private Integer id;
	private String programName;
	private String programDescription;
	private String trainingType;
	private HashMap<String,Object> pts = new HashMap<String,Object>();
	
	  private String trainingProgramName;
	  private String numberOfExcersises;
	  private String numberOfSets;
	  private String breakBetweenSets;
	  
	//----------------------------------------------------
  
	public Integer getId() {
		return id;
	}

	public String getTrainingProgramName() {
		return trainingProgramName;
	}

	public void setTrainingProgramName(String trainingProgramName) {
		this.trainingProgramName = trainingProgramName;
	}

	public String getNumberOfExcersises() {
		return numberOfExcersises;
	}

	public void setNumberOfExcersises(String numberOfExcersises) {
		this.numberOfExcersises = numberOfExcersises;
	}

	public String getNumberOfSets() {
		return numberOfSets;
	}

	public void setNumberOfSets(String numberOfSets) {
		this.numberOfSets = numberOfSets;
	}

	public String getBreakBetweenSets() {
		return breakBetweenSets;
	}

	public void setBreakBetweenSets(String breakBetweenSets) {
		this.breakBetweenSets = breakBetweenSets;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getProgramDescription() {
		return programDescription;
	}

	public void setProgramDescription(String programDescription) {
		this.programDescription = programDescription;
	}

	public String getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}

	public HashMap<String,Object> getPts() {
		return pts;
	}

	public void setPts(HashMap<String,Object> pts) {
		this.pts = pts;
	}

	
	//----------------------------------------------------
	public HashMap<String,Object> getLoadPrograms()
	{
		pts=LoadPrograms();
		return pts;
	}
	
	
	public static ArrayList<String> LoadProgramTypesToArrayList()
	{
		ArrayList<String> programTypesList = new ArrayList<String>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try 
        { 
            transaction = session.beginTransaction();
            List programtypes = session.createQuery("from ProgramType").list(); //is worth to remember (common mistake) - when you use want to select from table, use bean name, not table name
            for (Iterator iterator = programtypes.iterator(); iterator.hasNext();)
            {
                ProgramType programType = (ProgramType) iterator.next();
                programTypesList.add(programType.getTrainingProgramName());
             
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
		return programTypesList;

	}
	
	public static boolean checkIfProgramTemplateAlreadyExist(Session session, Transaction transaction, String programName)
	{
		boolean statement = false;
	
		try 
	    {
	     List checkingList = session.createQuery("from ProgramType").list(); //is worth to remember (common mistake) - when you use want to select from table, use bean name, not table name
	     for (Iterator iterator = checkingList.iterator(); iterator.hasNext();)
	     {
	       if(statement==false)	
	       	{
	     	ProgramType pt = (ProgramType) iterator.next();
	          		String currentProgramType=String.valueOf(pt.getTrainingProgramName());
	     			 if(currentProgramType.equals(programName))
	         		 {
	         	 		 log.debug("W bazie znaleziono dziennik o istniejacej juz nazwie " + currentProgramType);
	     				 statement=true;	
	         		 }
	
	       	}
	       
	      }          	
	    } 
		
		   catch (HibernateException e)
	    {
	        transaction.rollback();
	        e.printStackTrace();
	        log.debug(e.getMessage());
	    } 
	
	return statement;
	}
	
	
	
	public HashMap<String,Object> LoadPrograms()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try 
        { 
            transaction = session.beginTransaction();
            List programtypes = session.createQuery("from ProgramType").list();
            for (Iterator iterator = programtypes.iterator(); iterator.hasNext();)
            {
                ProgramType programType = (ProgramType) iterator.next();
                pts.put(programType.programName.toString(), programType);
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
		return pts;
	}


	@SuppressWarnings("rawtypes")
	public static boolean SaveProgram(String trainingProgramName, String numberOfExcersises, String numberOfSets, String breakBetweenSets, String programDescription, Table table) //method which save new training program which will be used by creating new training diary
	{
		   boolean successFullySaved=true;
		  
	       Session session = HibernateUtil.getSessionFactory().openSession();
	       Transaction transaction = null;
	       if(checkIfProgramTemplateAlreadyExist(session, transaction, trainingProgramName)==false)
	       { 
	    	       //Variables which will be used to generate DescriptionField
			       String sHeaderTemplateContent = OtherFunctions.ReadHeaderTemplateFromFile();
			       String sExcersiseTemplateContent = OtherFunctions.ReadExcersiseTemplateFromFile();
			       String sExcersiseTemplateContentOriginal=sExcersiseTemplateContent;
			       String sExcersisesDescription="";
			       String sHeaderString = sHeaderTemplateContent.replace("@programname@", trainingProgramName).replace("@trainingtype@", programDescription);
			     //--------------------------------------------------------------------------------
			       String sFullDescription = "";
			       
			       try 
			       {
			       log.debug("Session.beginTransaction process started");
			          transaction = session.beginTransaction();
			          
			           for (Object id : table.getItemIds()) 
				       {
			        	   
			            // Get the Property representing a cell
						Property nameOfExcersise = table.getContainerProperty(id, "Name of Excersise");
			            Property numberOfSetsForExcersise = table.getContainerProperty(id,"Number of sets");
			            Property breakBetweenSetsInExcersises = table.getContainerProperty(id, "Break between sets");
			
			            // Get the value of the Property
			            Object nameOfExcersiseValue = nameOfExcersise.getValue();
			            Object numberOfSetsForExcersiseValue = numberOfSetsForExcersise.getValue();
			            Object breakBetweenSetsInExcersisesValue = breakBetweenSetsInExcersises.getValue();
			            
			            String sNameOfExcersiseValue = String.valueOf(nameOfExcersiseValue);
			            String sNumberOfSetsForExcersiseValue=String.valueOf(numberOfSetsForExcersiseValue);
			            String sBreakBetweenSetsInExcersiseValue = String.valueOf(breakBetweenSetsInExcersisesValue);
			            
			            sExcersiseTemplateContent=sExcersiseTemplateContentOriginal.replace("@excersises_name@", sNameOfExcersiseValue)
			            		.replace("@numberofsets@", sNumberOfSetsForExcersiseValue)
			            		.replace("@breakbetweensets@", sBreakBetweenSetsInExcersiseValue);
			            
			            sExcersisesDescription=sExcersisesDescription+sExcersiseTemplateContent;
			            log.debug(sExcersisesDescription);
				       } 
			           sFullDescription=sHeaderString+"<br>"+sExcersisesDescription;
			          
			           
			          ProgramType pm = new ProgramType();
			          pm.setTrainingProgramName(trainingProgramName);
			          pm.setTrainingType(trainingProgramName);
			          pm.setProgramName(trainingProgramName);
			          pm.setNumberOfExcersises(numberOfExcersises);
			          pm.setNumberOfSets(numberOfSets);
			          pm.setBreakBetweenSets(breakBetweenSets);
			          pm.setProgramDescription(sFullDescription);
		
			          session.save(pm);
			          transaction.commit();
			        
			       log.debug("New Training Program Type saved succesfully");
			       
			       }
			       catch (HibernateException e) 
			       {
			    	   successFullySaved=false;
		
			    	   log.debug(e.getMessage());
			    	   transaction.rollback();
			           e.printStackTrace();
			       } 
			       
			       finally 
			       {
			           session.close();
			       }
	       }
	       else
	       {
	    	   successFullySaved=false;
	       }
    	   return successFullySaved;
	       
	}
}

					