package com.qtstage.BusinessFlows;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qtstage.Locators.HomePage_Locators;
import com.qtstage.Locators.LogInPage_Locators;
import com.qtstage.Reporting.Extent_Reporting;
import com.qtstage.Utilities.Excel_Handling;
import com.qtstage.Utilities.WrapperMethods;

public class LoginFlow {

	WebDriver driver;
	WrapperMethods method = new WrapperMethods();

	public void logIn(WebDriver driver, String TC_ID) throws Throwable {
		try {
			if (method.isElementDisplay(driver, HomePage_Locators.clickOnSignIn)) {
				System.out.println("Close the pop up window...");
				method.clickButton(driver, HomePage_Locators.clickOnSignIn, "click for SignInPage");
			}
			Thread.sleep(2000);
			System.out.println("Enter email id");
			method.enterText(driver, LogInPage_Locators.inpEmailAddress, Excel_Handling.Get_Data(TC_ID, "Email_id"),
					"Email_id");
			Extent_Reporting.Log_with_Screenshot("Entered email Id", driver);

			System.out.println("Enter password: " + Excel_Handling.Get_Data(TC_ID, "Password"));
			method.enterText(driver, LogInPage_Locators.inpPassword, Excel_Handling.Get_Data(TC_ID, "Password"),
					"password");
			Extent_Reporting.Log_with_Screenshot("Entered password", driver);

			System.out.println("Click on Login button");
			method.Clickbtn(driver, LogInPage_Locators.btnLogin, "Click on Login button");
			
			Extent_Reporting.Log_with_Screenshot("Clicked on Login button", driver);

			List<WebElement> allOptions = driver.findElements(By.xpath(HomePage_Locators.menuList));
			for(int i=0;i<allOptions.size();i++)
			{
				System.out.println(allOptions.get(i).getText());
				if(allOptions.get(i).getText().contains("India"))
				{
					System.out.println("Given Menu item are present");
					Thread.sleep(2000);
					method.mouseHoverAction(driver, HomePage_Locators.india, "Clicking on india");
					Thread.sleep(2000);
					List<WebElement> list2=method.findElementsInList(driver, HomePage_Locators.submenu, "submenu");
					for (WebElement webElement : list2) {
						System.out.println(webElement.getText());
					}
				}
			}
			Extent_Reporting.Log_with_Screenshot("Menu lists are: ", driver);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			System.out.println("Login failed..");
		}
	}
}