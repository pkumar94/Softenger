package com.qtstage.BusinessFlows;

import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qtstage.Locators.HomePage_Locators;

import com.qtstage.Reporting.Extent_Reporting;
import com.qtstage.Utilities.Excel_Handling;
import com.qtstage.Utilities.WrapperMethods;

public class SearchProductFlow {
	WebDriver driver;
	WrapperMethods method = new WrapperMethods();

	public void searchProduct(WebDriver driver, String TC_ID) throws Throwable {
		try {
			Thread.sleep(3000);
			System.out.println("Enter product name to search");
			method.waitForElementVisible(driver, HomePage_Locators.searchBox);
			method.Clickbtn(driver, HomePage_Locators.searchBox, "clicking on srrach box");
			method.inputText_Enter(driver, HomePage_Locators.Enteredvalue, Excel_Handling.Get_Data(TC_ID, "ToSearch"), "clicking on india");
			Extent_Reporting.Log_with_Screenshot("Entered product name", driver);
			
			List<WebElement> product_list=method.findElementsInList(driver, HomePage_Locators.productList, "gettinglist of product");
			
			for(int i=0;i<product_list.size();i++)
			{
				
			
				System.out.println(product_list.get(i).getText());
				if(product_list.get(i).getText().equals(Excel_Handling.Get_Data(TC_ID, "ProductName")))
				{
					
					System.out.println("given product is present");
				}
			

			
				}
				
			
			Extent_Reporting.Log_with_Screenshot("productlist", driver);
			


		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			System.out.println("Failed to add to cart product");
		}
	}
}
