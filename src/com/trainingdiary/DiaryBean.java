package com.trainingdiary;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.trainingdiary.database.HibernateUtil;


@javax.persistence.Entity
public class DiaryBean implements Serializable
{
//--------------------------------------------------------Managed Property	
	
	static Logger log = Logger.getLogger(DiaryBean.class);	

	private static final long serialVersionUID = 1L;
	private String nameOfDiary;
	private Date diaryCreationDate;
	private String diaryDescription;
	private String choosedTrainingPlan;
    public HashMap<String,Object> allDiaries = new HashMap<String,Object>();
    public String choosedDiary;
    boolean editable;
    
  //---------------------------------------------------------
//Lists of diaries properties 
    private List<DiaryBean> diaryDescriptions;

    public List<DiaryBean> getDiaryDescriptions() {
		return diaryDescriptions;
	}
	public void setDiaryDescriptions(List<DiaryBean> diaryDescriptions) {
		this.diaryDescriptions = diaryDescriptions;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "MEDIUMINT NOT NULL AUTO_INCREMENT")
	private Integer id;
	
   
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getChoosedDiary() {
		return choosedDiary;
	}

	public void setChoosedDiary(String choosedDiary) {
		this.choosedDiary = choosedDiary;
	}
	
	public Date getDiaryCreationDate() {
		return diaryCreationDate;
	}
	public void setDiaryCreationDate(Date diaryCreationDate) {
		this.diaryCreationDate = diaryCreationDate;
	}
	public String getNameOfDiary() {
		return nameOfDiary;
	}
	public void setNameOfDiary(String nameOfDiary) {
		this.nameOfDiary = nameOfDiary;
	}
	public String getDiaryDescription() {
		return diaryDescription;
	}
	public void setDiaryDescription(String diaryDescription) {
		this.diaryDescription = diaryDescription;
	}
	public String getChoosedTrainingPlan() {
		return choosedTrainingPlan;
	}
	public void setChoosedTrainingPlan(String choosedTrainingPlan) {
		this.choosedTrainingPlan = choosedTrainingPlan;
	}
	
	public HashMap<String, Object> getAllDiaries() {
		return allDiaries;
	}

	public void setAllDiaries(HashMap<String, Object> allDiaries) {
		this.allDiaries = allDiaries;
	}
	
//------------------------------------------------------------------	
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static boolean checkIfDiaryExistInDatabase(Session session, Transaction transaction, String DiaryName) //universal method which check before saving somthing, if element which you want to save doesn't exist yet in database
	{
	boolean statement = false;
		try 
	       {
            List checkingList = session.createQuery("from DiaryBean").list(); //is worth to remember (common mistake) - when you use want to select from table, use bean name, not table name
            for (Iterator iterator = checkingList.iterator(); iterator.hasNext();)
            {
              if(statement==false)
              	{
            	DiaryBean diary = (DiaryBean) iterator.next();
                 		String currentDiaryName=String.valueOf(diary.getNameOfDiary());
            			 if(currentDiaryName.equals(DiaryName))
                		 {
                	 		 log.debug("W bazie znaleziono dziennik o istniejacej juz nazwie " + DiaryName);
            				 statement=true;	
                		 }
                log.debug("Currently loaded diary "+ diary.getNameOfDiary().toString());
              	}
              
             }          	
	       } 
		
		   catch (HibernateException e)
	       {
	           transaction.rollback();
	           e.printStackTrace();
	           log.debug(e.getMessage());
	       } 
		
	 	if (statement==true)
	 	{		
		FacesContext context = FacesContext.getCurrentInstance();  
	 	context.addMessage(null, new FacesMessage("Diary with this name exist actually in database ! Choose another diary name !")); 
	 	}
		return statement;

	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static String SaveDiary(String programType, Date diaryCreationDate, String diaryDescription, String nameOfDiary) //method which save diary0++
	   {
	       Session session = HibernateUtil.getSessionFactory().openSession();
	       Transaction transaction = null;
	       try 
	       {
	    	 
	       log.debug("Session.beginTransaction process started");
	          transaction = session.beginTransaction();
	          DiaryBean diaryBean = new DiaryBean();
	       //log.debug("Setting properties of new diary : "+choosedTrainingPlan+" , " + diaryCreationDate + ", " + diaryDescription + ", " + nameOfDiary);
	          diaryBean.setChoosedTrainingPlan(programType/*programType.getProgramName()*/);
	          diaryBean.setDiaryCreationDate(diaryCreationDate);
	          diaryBean.setDiaryDescription(diaryDescription);
	          diaryBean.setNameOfDiary(nameOfDiary);
	         
	          //-------------------------------------------------------------
	          log.debug("Sprawdzam czy dziennik o danej nazwie juz istnieje");
	          //-------------------------------------------------------------
	          if(checkIfDiaryExistInDatabase(session, transaction, nameOfDiary)==false)
    		  { 
	           session.save(diaryBean);
	           transaction.commit();
    		  }
	       log.debug("Diary created succesfully");

    	   FacesContext context = FacesContext.getCurrentInstance();  
    	   context.addMessage(null, new FacesMessage("Diary saved successfully")); 
			
	       } catch (HibernateException e) 
	       
	       {
	           transaction.rollback();
	           e.printStackTrace();
	           log.debug(e.getMessage());
	       } 
	       
	       finally 
	       {
	           session.close();
	       }
		return "";		  
			
	   }

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		public HashMap<String,Object> getLoadDiaries()
		{
			allDiaries=LoadDiaries();
			return allDiaries;
		}
	  
	  
		public HashMap<String,Object> LoadDiaries()
		{
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction transaction = null;
	        try 
	        { 
	            transaction = session.beginTransaction();
	            List programtypes = session.createQuery("from DiaryBean").list(); //is worth to remember (common mistake) - when you use want to select from table, use bean name, not table name
	            for (Iterator iterator = programtypes.iterator(); iterator.hasNext();)
	            {
	                DiaryBean diary = (DiaryBean) iterator.next();
	                allDiaries.put(diary.getNameOfDiary().toString(), diary);
	              
	                log.debug("Currently loaded diary "+ diary.getNameOfDiary().toString());
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
			return allDiaries;
		}

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//getChoosedTrainingDiaryProperties
		public HashMap<String,Object> getChoosedTrainingDiaryProperties()
		{
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction transaction = null;
	        try 
	        { 
	            transaction = session.beginTransaction();
	            List programtypes = session.createQuery("from DiaryBean ").list(); //is worth to remember (common mistake) - when you use want to select from table, use bean name, not table name
	            for (Iterator iterator = programtypes.iterator(); iterator.hasNext();)
	            {
	                DiaryBean diary = (DiaryBean) iterator.next();
	                allDiaries.put(diary.getNameOfDiary().toString(), diary);
	                log.debug("Currently loaded diary "+ diary.getNameOfDiary().toString());
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
			return allDiaries;
		}
		
	
	    
}
                    