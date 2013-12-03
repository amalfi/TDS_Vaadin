package com.trainingdiary.database;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.trainingdiary.DiaryBean;


@SuppressWarnings("deprecation")
public class HibernateUtil
{
	static Logger log = Logger.getLogger(DiaryBean.class);
	private static final SessionFactory sessionFactory = buildSessionFactory();
	 
    private static SessionFactory buildSessionFactory() {
        try 
        {
            // Create the SessionFactory from hibernate.cfg.xml
        	//log.debug("Create the SessionFactory from hibernate.cfg.xml");
            return new AnnotationConfiguration().configure().buildSessionFactory();
 
        }
        catch (Throwable ex) 
        {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            log.debug(ex.getCause());
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
 
    public static void shutdown() {
    	// Close caches and connection pools
    	//log.debug("Close caches and connection pools");
    	getSessionFactory().close();
    }
 
}
