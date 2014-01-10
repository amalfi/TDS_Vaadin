package com.trainingdiary.vaadin.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.trainingdiary.DiaryBean;
import com.trainingdiary.Training;
import com.trainingdiary.tools.ButtonActions;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.calendar.event.BasicEvent;


public class UserProfile extends CustomComponent
{
	static Logger log = Logger.getLogger(UserProfile.class);
	private static final long serialVersionUID = 1L;

	public UserProfile()
	{
		String currentUserNameValue = SimpleLoginView.currentLoadedUser;
		String timeStamp = new SimpleDateFormat("yyyy MM dd").format(Calendar.getInstance().getTime());
		com.vaadin.ui.Calendar calendar = new com.vaadin.ui.Calendar("Current User Calendar");
		VerticalLayout vl = new VerticalLayout();
		//----------------------------------------------------------------------------------------------------
		
		Label label = new Label("Welcome : " + currentUserNameValue);
		Label currentDateLabel = new Label("Today is : " + timeStamp);
		ArrayList<String> currentDiary = DiaryBean.getDiariesNamesForCurrentUser();
		String sListOfDiaries = "";
		for(int i=0; i<currentDiary.size(); i++)
		{
			sListOfDiaries = sListOfDiaries + currentDiary.get(i).toString() + ",";
		}
	
		Label label2 = new Label ("Your currently training diaries are : " + sListOfDiaries); //test ! it wouldnt return proper value of diaries name, just full objects names
		
		calendar.setWidth("600px");
		calendar.setHeight("300px");
		//----------------------------------------------------------------------------------------------------
		// Loop which add Training Dates to the Calendar Object
		ArrayList<Training> listOfTrainings = Training.LoadTrainings();
		for(int j=0; j<listOfTrainings.size(); j++)
		{
			Date currentlyTrainingDate = listOfTrainings.get(j).getTrainingDate();
			String diaryName = listOfTrainings.get(j).getDescription();
			calendar.addEvent(new BasicEvent(diaryName,"Trainings", currentlyTrainingDate, currentlyTrainingDate));
		}
		//----------------------------------------------------------------------------------------------------
		
		vl.setMargin(true);
		vl.addComponent(label);
		vl.addComponent(currentDateLabel);
		vl.addComponent(label2);
		vl.addComponent(calendar);
		vl.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		
		setCompositionRoot(vl);
	}
}
