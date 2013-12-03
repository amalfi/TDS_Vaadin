package com.trainingdiary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.trainingdiary.database.HibernateUtil;

@javax.persistence.Entity
@ManagedBean
@RequestScoped
public class EditDiary implements Serializable {

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(EditDiary.class);
	
    private List<String> diaryDescriptions = new ArrayList<String>();
    private List<DiaryBean> allDiaries = new ArrayList<DiaryBean>();
    static int counter=0;

//-----------------------------------------------------------------------------------

	public List<String> getDiaryDescriptions() {
		return diaryDescriptions;
	}

	
	public List<DiaryBean> getAllDiaries() {
		return allDiaries;
	}

	public void setAllDiaries(List<DiaryBean> allDiaries) {
		this.allDiaries = allDiaries;
	}
//
	public void setDiaryDescriptions(List<String> diaryDescriptions) {
		this.diaryDescriptions = diaryDescriptions;
	}

	public List<String> getdiaryDescriptions()
	{
		return diaryDescriptions;
		
	}
//-----------------------------------------------------------------------------------
	public List<DiaryBean> LoadDiaries()
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
                allDiaries.add(diary);
                diaryDescriptions.add(String.valueOf(diary.getDiaryDescription().toString()));
                log.debug("Currently loaded diary "+ diary.getNameOfDiary().toString());
                counter++;
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


	public EditDiary() 
	{
		allDiaries = LoadDiaries();	
	}


}
				