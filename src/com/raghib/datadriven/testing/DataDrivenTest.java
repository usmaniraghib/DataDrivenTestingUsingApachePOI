package com.raghib.datadriven.testing;

import java.io.IOException;

/**
 * Reference:-
 * https://www.youtube.com/watch?v=ipjl49Hgsg8&list=PLUDwpEzHYYLsN1kpIjOyYW6j_GLgOyA07&index=2
 * https://www.youtube.com/watch?v=1nP9UlwzpgU&list=PLUDwpEzHYYLsN1kpIjOyYW6j_GLgOyA07&index=11
 */

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.raghib.selenium.BaseClass;
import com.raghib.selenium.XLUtility;

//Reading the data from Excel sheet and Providing the data to Login Page

public class DataDrivenTest extends BaseClass {
	
	WebDriver driver;
	String browserName = "chrome";
	String browserVersion = "116";
	
	String url = "https://admin-demo.nopcommerce.com/login";
	
	@BeforeClass()
	public void browserOpen() {
		// Chrome Browser
		driver = BaseClass.getDriver(browserName, browserVersion);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();
	}

	@AfterClass()
	public void browserclose() {
		try {
			if (driver != null) {
				System.out.println("Driver Need to Close");
				BaseClass.quitDriver();
			} else {
				System.out.println("Driver Still Open");
			}
		} catch (Exception e) {
			System.out.println("Nothing to do with it");
		} finally {
			System.out.println("Finally Block - To close the driver");
			BaseClass.quitDriver();
		}
	}
	
	@Test(dataProvider="LoginData")
	public void loginTest(String user,String pwd,String exp)
	{
		driver.get(url);
		
		WebElement txtEmail=driver.findElement(By.id("Email"));
		txtEmail.clear();
		txtEmail.sendKeys(user);
		
		
		WebElement txtPassword=driver.findElement(By.id("Password"));
		txtPassword.clear();
		txtPassword.sendKeys(pwd);
		
		driver.findElement(By.xpath("//button[normalize-space()='Log in']")).click(); //Login  button
		
		String exp_title="Dashboard / nopCommerce administration";
		String act_title=driver.getTitle();
		
		if(exp.equals("Valid"))
		{
			if(exp_title.equals(act_title))
			{
				driver.findElement(By.linkText("Logout")).click();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		else if(exp.equals("Invalid"))
		{
			if(exp_title.equals(act_title))
			{
				driver.findElement(By.linkText("Logout")).click();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}		
	}
	
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		/*String loginData[][]= {
								{"admin@yourstore.com","admin","Valid"},
								{"admin@yourstore.com","adm","Invalid"},
								{"adm@yourstore.com","admin","Invalid"},
								{"adm@yourstore.com","adm","Invalid"}
							};*/
		
		//get the data from excel
		String excelFilePath = System.getProperty("user.dir");
		String path = excelFilePath + "\\ExcelFile\\loginData.xlsx";
		XLUtility xlutil = new XLUtility(path);
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);	
				
		String loginData[][]=new String[totalrows][totalcols];			
		
		for(int i=1;i<=totalrows;i++) //1
		{
			for(int j=0;j<totalcols;j++) //0
			{
				loginData[i-1][j]=xlutil.getCellData("Sheet1", i, j);
			}
				
		}		
		return loginData;
	}
}
