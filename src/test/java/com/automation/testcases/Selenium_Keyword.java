package com.automation.testcases;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.automation.functionlibrary.FunctionLibrary;
import com.automation.objectrepository.ObjectRepository;
import com.automation.utils.ExcelUtils;

public class Selenium_Keyword extends FunctionLibrary {

	ExcelUtils excelUtils = new ExcelUtils();
	ObjectRepository OR = new ObjectRepository();

	@BeforeTest
	public void beforeTest() {
		setUp();
	}

	@Test
	@SuppressWarnings("static-access")
	public void TC0001(){
	if(driver != null){
		try{
			List<String> list = excelUtils.getCellData("TC0001");
		//	String name = list.get(0);
		//	String email = list.get(1);
		//	String phone = list.get(2);
			String keyword = list.get(3);
			String location = list.get(4);
			
			WebElement elementKeyword = findElementById(OR.Title_Keywors_CompanyName_Id);
			elementKeyword.sendKeys(keyword);
	//		selectValuesDynamicDropDown(elementKeyword, "selenium");
			sleep(2000);
			WebElement elementLocation = findElementById(OR.City_State_ZipCode_Id);
			clearFields(elementLocation);
			elementLocation.sendKeys(location);
			sleep(2000);
			WebElement elementFindJobsButton = findElementById(OR.Find_Jobs_Button_Id);
			if(isElementPresent(elementFindJobsButton)){
				elementFindJobsButton.click();
			}
			sleep(2000);
			if(isElementPresent(findElementById(OR.Learn_More_Button_Id))){
				actionsClass();
			}
			WebElement elementContract = findElementByLink(OR.Contract_Jobs_LinkText);
			elementContract.click();
			sleep(2000);
			linksOnPage();
			
			//   html/body/table[2]/tbody/tr/td/table/tbody/tr/td[2]/div[4]/div[1]/a
			//   html/body/table[2]/tbody/tr/td/table/tbody/tr/td[2]/div[4]/div[2]/a
			//   html/body/table[2]/tbody/tr/td/table/tbody/tr/td[2]/div[4]/div[3]/a
			
			//   html/body/table[2]/tbody/tr/td/table/tbody/tr/td[2]/div[4]/div[1]/a
			

		}catch(Exception E){
			System.out.println(E.getMessage());
		}
	}
	}

}
