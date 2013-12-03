package com.trainingdiary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.trainingdiary.database.HibernateUtil;

@javax.persistence.Entity
@ManagedBean
@ViewScoped
public class EditTraining implements Serializable {

	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(EditTraining.class);

	
	public ArrayList<Training> getTrainings() {
		return trainings;
	}	

	public void setTrainings(ArrayList<Training> trainings) {
		this.trainings = trainings;
	}

	private ArrayList<Training> trainings = LoadTrainings();
		

	


	public EditTraining() 
	{
		//trainings = new ArrayList<Training>();
		log.debug("Trainings loaded succesfully");
	}
	//getters and setters
	
	public ArrayList<Training> LoadTrainings()
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        ArrayList<Training> listOfTrainings = new ArrayList<Training>();
        try 
        { 
            transaction = session.beginTransaction();
            List programtypes = session.createQuery("from Training").list(); //is worth to remember (common mistake) - when you use want to select from table, use bean name, not table name
            for (Iterator iterator = programtypes.iterator(); iterator.hasNext();)
            {
                Training training = (Training) iterator.next();
                training.setDescription(training.getDescription());
                listOfTrainings.add(training);
             
                log.debug("Currently loaded training"+ training.getDescription().toString());		
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
		return listOfTrainings;
	}
	
}
				