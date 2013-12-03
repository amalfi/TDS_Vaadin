package com.trainingdiary.database;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import org.apache.log4j.Logger;

public class SaveUser implements ActionListener //Action
{
	static Logger log = Logger.getLogger(SaveUser.class);

	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException
	{
	log.debug("**************************************Action Event is fired up **************************************");	  
	}
}
