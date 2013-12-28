package com.trainingdiary.vaadin.ui;

import java.util.HashMap;

import com.trainingdiary.DiaryBean;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class UserProfile extends CustomComponent
{

	private static final long serialVersionUID = 1L;

	public UserProfile()
	{
		String currentUserNameValue = SimpleLoginView.currentLoadedUser;
		
		VerticalLayout vl = new VerticalLayout();
		//----------------------------------------------------------------------------------------------------
		
		Label label = new Label("Welcome " + currentUserNameValue);
		HashMap<String, DiaryBean> currentDiary = DiaryBean.getDiariesForCurrentUser();
		

		Label label2 = new Label ("Your currently training diaries are : " + currentDiary.toString()); //test ! it wouldnt return proper value of diaries name, just full objects names

		//----------------------------------------------------------------------------------------------------
		
		vl.setMargin(true);
		vl.addComponent(label);
		vl.addComponent(label2);
		vl.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		
		setCompositionRoot(vl);
	}
}
