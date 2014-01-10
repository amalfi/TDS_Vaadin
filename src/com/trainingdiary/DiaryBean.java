package com.trainingdiary;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.trainingdiary.database.HibernateUtil;
import com.trainingdiary.vaadin.ui.SimpleLoginView;
import com.vaadin.ui.Notification;


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
    public static HashMap<String, DiaryBean> allDiaries = new HashMap<String,DiaryBean>();
    public static HashMap<String, DiaryBean> currentDiaries = new HashMap<String,DiaryBean>();
    public String choosedDiary;
    boolean editable;
    private String currentDiaryUser;
    
    

	public static HashMap<String, DiaryBean> getCurrentDiaries() {
		return currentDiaries;
	}
	public static void setCurrentDiaries(HashMap<String, DiaryBean> currentDiaries) {
		DiaryBean.currentDiaries = currentDiaries;
	}

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
	
    
	public String getCurrentDiaryUser() {
		return currentDiaryUser;
	}
	public void setCurrentDiaryUser(String currentDiaryUser) {
		this.currentDiaryUser = currentDiaryUser;
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
	
	public HashMap<String, DiaryBean> getAllDiaries() {
		return allDiaries;
	}

	public void setAllDiaries(HashMap<String, DiaryBean> allDiaries) {
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
		
		return statement;

	}

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public static void UpdateDiary(Integer id, String programType, Date diaryCreationDate, String diaryDescription, String nameOfDiary)
	{
	    Session session = HibernateUtil.getSessionFactory().openSession();
	       Transaction transaction = null;
       try 
       {
	    	 
	      log.debug("Session.beginTransaction process started");
          transaction = session.beginTransaction();
          DiaryBean diaryBean = new DiaryBean();
       //log.debug("Setting properties of new diary : "+choosedTrainingPlan+" , " + diaryCreationDate + ", " + diaryDescription + ", " + nameOfDiary);
          diaryBean.setId(id);
          diaryBean.setChoosedTrainingPlan(programType);
          diaryBean.setDiaryCreationDate(diaryCreationDate);
          diaryBean.setDiaryDescription(diaryDescription);
          diaryBean.setNameOfDiary(nameOfDiary);
          diaryBean.setCurrentDiaryUser(String.valueOf(SimpleLoginView.currentLoadedUser));
          //-------------------------------------------------------------
          log.debug("Sprawdzam czy dziennik o danej nazwie juz istnieje");
          //-------------------------------------------------------------
           session.update(diaryBean);
           transaction.commit();
           log.debug("Diary updated succesfully");
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
	          diaryBean.setCurrentDiaryUser(String.valueOf(SimpleLoginView.currentLoadedUser));
	          //-------------------------------------------------------------
	          log.debug("Sprawdzam czy dziennik o danej nazwie juz istnieje");
	          //-------------------------------------------------------------
	          if(checkIfDiaryExistInDatabase(session, transaction, nameOfDiary)==false)
    		  { 
	           session.save(diaryBean);
	           transaction.commit();
	           log.debug("Diary created succesfully");
    		  }
	          else
	          {
	        	  Notification.show("Error","Diary with this name already exist",Notification.Type.WARNING_MESSAGE);	
	          }

			
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
		return "";		  
			
	   }

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		public HashMap<String,DiaryBean> getLoadDiaries()
		{
			allDiaries=LoadDiaries();
			return allDiaries;
		}
	  
	  
		public static HashMap<String, DiaryBean> LoadDiaries()
		{
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction transaction = null;
	        try 
	        { 
	            transaction = session.beginTransaction();
	            List programtypes = session.createQuery("from DiaryBean").list(); //is worth to remember (common mistake) - when you use want to select from table, use bean name, not table name
	            int i=0;
	            for (Iterator iterator = programtypes.iterator(); iterator.hasNext();)
	            {
	                DiaryBean diary = (DiaryBean) iterator.next();
	                allDiaries.put(String.valueOf(i), diary);
	              
	                log.debug("Currently loaded diary "+ diary.getNameOfDiary().toString());
	                i++;
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
//------------------------------------------------------------------------------------------------------------------
		public ArrayList<DiaryBean> loadDiariesArray()
		{
			ArrayList<DiaryBean> diariesArrayList =  new ArrayList<DiaryBean>();
			

			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction transaction = null;
	        try 
	        { 
	            transaction = session.beginTransaction();
	            List programtypes = session.createQuery("from DiaryBean").list(); //is worth to remember (common mistake) - when you use want to select from table, use bean name, not table name
	            int i=0;
	            for (Iterator iterator = programtypes.iterator(); iterator.hasNext();)
	            {
	                DiaryBean diary = (DiaryBean) iterator.next();
	                diariesArrayList.add(diary);
	              
	                log.debug("Currently loaded diary "+ diary.getNameOfDiary().toString());
	                i++;
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
			
			return diariesArrayList;
			
		}
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//getChoosedTrainingDiaryProperties
		public HashMap<String,DiaryBean> getChoosedTrainingDiaryProperties()
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
		
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		public static ArrayList<String> getDiariesNamesForCurrentUser()
		{
			ArrayList<String> currentDiariesNames = new ArrayList<String>();
	        String currentUser= SimpleLoginView.currentLoadedUser;
			
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction transaction = null;
	        try 
	        { 
	            transaction = session.beginTransaction();
	            List programtypes = session.createQuery("from DiaryBean where currentdiaryuser = :currentuser").setParameter("currentuser", currentUser).list(); //is worth to remember (common mistake) - when you use want to select from table, use bean name, not table name
	            for (Iterator iterator = programtypes.iterator(); iterator.hasNext();)
	            {
	                DiaryBean diary = (DiaryBean) iterator.next();
	                currentDiariesNames.add(diary.getNameOfDiary());
	             
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
			return currentDiariesNames;
		}
		
		
		public static HashMap<String, DiaryBean> getDiariesForCurrentUser()
		{
			Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction transaction = null;
	        String diaries="";
	        String currentUser= SimpleLoginView.currentLoadedUser;
	        try 
	        { 
	            transaction = session.beginTransaction();

	           /* Query query = session.createQuery("from DiaryBean where currentdiaryuser = :currentuser");
	            query.setParameter("currentuser", currentUser);
	            List list = query.list();
	            
	            diaries=String.valueOf(list.get(0));*/
	            
	            List currentdiaries = session.createQuery("from DiaryBean where currentdiaryuser = :currentuser").setParameter("currentuser", currentUser).list(); //is worth to remember (common mistake) - when you use want to select from table, use bean name, not table name
	            for (Iterator iterator = currentdiaries.iterator(); iterator.hasNext();)
	            {
	                DiaryBean diary = (DiaryBean) iterator.next();
	                currentDiaries.put(diary.getNameOfDiary().toString(), diary);
	                log.debug("Currently loaded diary "+ diary.getNameOfDiary().toString());
	            }          	
	            
	            
	            log.debug("Dzienniki dla aktualnie zalogowanego usera pobrano poprawnie");
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
	        
			return currentDiaries;
		}

}
                    