package com.qtstage.Utilities;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;



// Creating TestNg.xmlfile dynamically for fetching parameter from excel file.
public class Create_TestNGXML 
{

	public List<XmlInclude> constructIncludes (String... methodNames) {
		List<XmlInclude> includes = new ArrayList<XmlInclude> ();
		for (String eachMethod : methodNames) {
			includes.add (new XmlInclude (eachMethod));
		}
		return includes;
	}

	@SuppressWarnings("deprecation")
	@Test
	public void createXMLfile() throws InterruptedException, IOException
	{

		//calling out the excel datasheet instance to get all the "Yes" data for setting up the testng.xml file
		Excel_Handling excel_main=new Excel_Handling();		

		excel_main.copy(Constants.datasheetPath+"Datasheet.xlsx", Constants.datasheetPath+"Datasheet_Result.xlsx");

		Excel_Handling excel_Control = new Excel_Handling();

		Excel_Handling excel_Sheets = new Excel_Handling();


		// Excel Reader parameters: Datasheet File name, Sheet Name, Result datasheet file name, sheet name
		excel_Control.ExcelReader(Constants.datasheetPath+"Datasheet.xlsx","Control",Constants.datasheetPath+"Datasheet_Result.xlsx", "Control");
		try 
		{
			excel_Control.getExcelDataAll("Control", "Execute", "Yes", "Sr_No");

		} catch (Exception e) {

			System.out.println("getExcelDataAll : Failed");
			e.printStackTrace();
		}
		@SuppressWarnings({ "static-access", "rawtypes" })
		Map<String, HashMap> map_control = excel_Control.TestData;
                
	
        for(String key_control: map_control.keySet()){
        	
    		try {
    			
    			String TC_Name = map_control.get(key_control).toString();
    			//String flag = map_control.get(key_control)
    			String[] TC_Name_New = TC_Name.split("TC_Name=");
    			
    			String[] TC_Name_New1 = TC_Name_New[1].split(",");

    			//code added for separate sheets
            	excel_Sheets.ExcelReader(Constants.datasheetPath+"Datasheet.xlsx", TC_Name_New1[0].trim(), Constants.datasheetPath+"Datasheet_Result.xlsx", TC_Name_New1[0].trim());
                
             
    			excel_Sheets.getExcelDataAll(TC_Name_New1[0].trim(), "flag", "Yes", "tc_id");
    			
    			
    		} catch (Exception e) {
    			
        		System.out.println("ExcelReader : Failed");

    			e.printStackTrace();
    		}
    		
    		
    		@SuppressWarnings({ "static-access", "rawtypes" })
    		Map<String, HashMap> map_sheets = excel_Sheets.TestData;
        	
    		for(String key: map_sheets.keySet()){
        	

	        	//creation of the testng xml based on parameters/data
	        	TestNG testNG = new TestNG();
	        	
	        	XmlSuite suite = new XmlSuite();
	            suite.setName (new Common_Functions().GetXMLTagValue(Constants.configPath+"Config.xml", "Regression_Suite_Name"));
	               
		        if(Integer.parseInt(Excel_Handling.Get_Data(key, "browser_instance"))>0){
		        	
		        	suite.setParallel("tests");
	        		suite.setThreadCount(Integer.parseInt(Excel_Handling.Get_Data(key, "browser_instance")));
		        	
		        	for(int i=1;i<=Integer.parseInt(Excel_Handling.Get_Data(key, "browser_instance"));i++){
		    			//System.out.println("Key values: "+map_sheets.get(key).toString());

		        		XmlTest test = new XmlTest (suite);
		        		
		        		test.setName (key+"_Instance_"+i);
		    	        test.setPreserveOrder("false");
		    	        test.addParameter("tcID", key);
		    	        test.addParameter("browserType", Excel_Handling.Get_Data(key, "browser_type"));
		    	        test.addParameter("appURL", new Common_Functions().GetXMLTagValue(Constants.configPath+"Config.xml", "AppUrl")); 	        
		        		test.addParameter("temp", "temp"+i);
		        		
		        		XmlClass testClass = new XmlClass ();
		        		testClass.setName ("com.qtstage.Tests."+Excel_Handling.Get_Data(key, "class_name"));
		        	
		    	        test.setXmlClasses (Arrays.asList (new XmlClass[] { testClass}));
		        	}
	        		
	        	}else{
	        		System.out.println("XML: Else");
	        		XmlTest test = new XmlTest(suite);
	        		
	        		test.setName(key);
	    	        test.setPreserveOrder("true");
	    	        test.addParameter("tcID", key);
	    	        test.addParameter("browserType", Excel_Handling.Get_Data(key, "browser_type"));
	    	        
	    	        test.addParameter("appURL", new Common_Functions().GetXMLTagValue(Constants.configPath+"Config.xml", "AppUrl")); 	        
	    	        Thread.sleep(3000);        		
	        		XmlClass testClass = new XmlClass();
	        		testClass.setName ("com.qtstage.Tests."+Excel_Handling.Get_Data(key, "class_name"));
	    	        test.setXmlClasses (Arrays.asList (new XmlClass[] { testClass}));
	        			
	        	}		        
		        
		        List<String> suites = new ArrayList<String>();
		        final File f1 = new File(Create_TestNGXML.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		        System.out.println(f1.getAbsolutePath());
		        File f = new File(f1+"\\testNG.xml");
		        f.createNewFile();
		        
		        FileWriter fw = new FileWriter(f.getAbsoluteFile());
		        
		        BufferedWriter bw = new BufferedWriter(fw);
		        bw.write(suite.toXml());
		        
		        bw.close();
		        
		        suites.add(f.getPath());
		        
		        testNG.setTestSuites(suites);
		        
		        //Report_Setup.InitializeReport(key);
		        testNG.run();
		        //Report_Setup.extent.endTest(Report_Setup.test);
		        
		        f.delete();
    		}	
        }
        //Report_Setup.extent.flush();
    }

	}
