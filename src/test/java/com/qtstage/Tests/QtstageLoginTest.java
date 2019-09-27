package com.qtstage.Tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qtstage.BusinessFlows.LoginFlow;
import com.qtstage.Reporting.Report_Setup;
import com.qtstage.Utilities.Common_Functions;
import com.qtstage.Utilities.Driver_Setup;


public class QtstageLoginTest {
public WebDriver driver;
	
	@Parameters({ "browserType", "appURL", "tcID" })
	@Test
	public void tc_DecathlonLoginTest(String browserType, String appURL, String TC_ID) throws Throwable{
		try
		{	
			Common_Functions objCommonFunctions = new Common_Functions();	
			Driver_Setup objDriver_Setup = new Driver_Setup();
			LoginFlow objLoginFlow=new LoginFlow();
			
			Report_Setup.InitializeReport(TC_ID);
			driver = objDriver_Setup.initializeTestBaseSetup(browserType, appURL, TC_ID);
			objCommonFunctions.startRecording();
			
			//calling login method
			objLoginFlow.logIn(driver, TC_ID);

			Report_Setup.extent.endTest(Report_Setup.test);
			Report_Setup.extent.flush();
			objCommonFunctions.stopRecording();
		//	driver.close();
		}
		catch (Exception e) {
			e.getMessage();
			//driver.close();
			System.out.println("TC_Login_Error");
		}
		}
}