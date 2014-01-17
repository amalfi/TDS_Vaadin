package com.trainingdiary.tools;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

public class OtherFunctions 
{
	static Logger log = Logger.getLogger(OtherFunctions.class);
	

	public static String ReadHeaderTemplateFromFile()
	{
		String sFileContent="";
		File file = new File("D:\\workspace\\TDS_Vaadin\\src\\templateHeader.txt");
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
 
		try {
			fis = new FileInputStream(file);
 
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
 
			while (dis.available() != 0) 
			{
				sFileContent=sFileContent + String.valueOf(dis.readLine());
			}
 
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				fis.close();
				bis.close();
				dis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sFileContent;
	}
	
	public static String ReadExcersiseTemplateFromFile()
	{
		String sFileContent="";
		File file = new File("D:\\workspace\\TDS_Vaadin\\src\\templateForExcersise.txt");
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
 
		try {
			fis = new FileInputStream(file);
 
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
 
			while (dis.available() != 0) 
			{
				sFileContent=sFileContent + String.valueOf(dis.readLine());
			}
 
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				fis.close();
				bis.close();
				dis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sFileContent;
	}
 
}
