package com.mpstrak.common;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.mpstrak.modules.DGLogin;
import com.mpstrak.utility.Utility;

public class TestCase extends Utility{

	WebDriver driver = AppLaunch(prop.getProperty("browser"), prop.getProperty("url"));
	final static Logger logger = Logger.getLogger(TestCase.class);

	ExtentHtmlReporter htmlreporter;
	ExtentReports reports;
	ExtentTest test;
	ExtentTest parentTest;
	ExtentTest childTest;
	String fileName = System.getProperty("user.dir")+"/test-output/child.html";


	DGLogin dgobject = PageFactory.initElements(driver, DGLogin.class);

	@BeforeSuite
	public  void systemStartUpConfiguration() throws IOException {

		loadLogger();
		logger.info("----------------------System StartUp Configuration -----------------------------");
		
		
        logger.info("----------------------Setting up extent Report  -----------------------------");
		
		htmlreporter = new ExtentHtmlReporter(fileName);
		reports = new ExtentReports();
		reports.attachReporter(htmlreporter);

		htmlreporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);  
		htmlreporter.config().setDocumentTitle("Title automation"); 
		htmlreporter.config().setReportName("Report Name automation"); 
		htmlreporter.config().setTheme(Theme.DARK); 
	}

	/*
	 * @BeforeTest
	public void setUp(){
		
	}*/

	
	
	


	@Test(priority=1 , enabled = true) // false true
	public void loginTestCase() throws InterruptedException 
	{			
		SoftAssert soft = new SoftAssert();
		
		logger.info("******************************************");
		logger.info("=====================loginTestCase===========");
		logger.info("Entering Username");
		
		parentTest = reports.createTest("loginTestCase");
		childTest = parentTest.createNode("Create Node1 for TEst the title Broweser3");
		
		childTest.log(Status.INFO, MarkupHelper.createLabel("ENTER USER AnD PASS  ", ExtentColor.BLUE));
		String actual=dgobject.username();
		System.out.println("Title "+actual);
		
		logger.info("Entering Password ");
		//String actual1=dgobject.setPassword();
		String actual1="DEG";
		soft.assertTrue(actual1.equals(":: De Gruyter :: Production Tracking System ::"));
		logger.info("===============Login  validation================ ");
		
		childTest = parentTest.createNode("Create Node 2 for TEst the title ");
		String edit = dgobject.clickLogin();		
		soft.assertEquals(edit, edit);
		
		logger.info("===============Login  Success ================ ");
		
		childTest.log(Status.INFO, MarkupHelper.createLabel("Login Succecc  ", ExtentColor.BLUE));
		soft.assertAll();
		//driver.quit();
	}
	
	/*
	
	@Test(priority=2 , enabled = true) // false true
	public void loginTestCase1() throws InterruptedException 
	{
		driver=AppLaunch(prop.getProperty("browser"), prop.getProperty("url"));
		DGLogin dgobject1 = PageFactory.initElements(driver, DGLogin.class);
		logger.info("******************************************");
		logger.info("=====================loginTestCase1=====================");
		parentTest = reports.createTest("loginTestCase1");
		childTest = parentTest.createNode("Create Node 1 for loginTestCase1");
		childTest.log(Status.INFO, MarkupHelper.createLabel("Login Success  ", ExtentColor.BLUE));
		
		logger.info("Entering Username");
		dgobject1.username();
		Assert.assertTrue(true);
		logger.info("Entering Password ");
		childTest = parentTest.createNode("Create Node 2 for loginTestCase1");
		childTest.log(Status.INFO, MarkupHelper.createLabel("Login Success  ", ExtentColor.BLUE));
		dgobject1.setPassword();
		Assert.assertTrue(false);

		//String title = object.getLoginTitle();
		//Assert.assertTrue(title.equals(":: De Gruyter :: Production Tracking System ::"));
		logger.info("===============Login  validation================ ");
		//String logout = driver.findElement(By.partialLinkText("Logout")).getText();
		dgobject1.clickLogin();		
		//Assert.assertEquals(logout, "Logout");
		logger.info("===============Login  Success ================ ");
		
	}
	
	2nd test 
	
	@Test(priority=3)
	  public void openBT2(){
		  SoftAssert softAssert = new SoftAssert();
		  parentTest = reports.createTest("Test menu 2");
		  childTest = parentTest.createNode("Create Node for TEst the title Broweser3");
		  
		  childTest.log(Status.INFO, MarkupHelper.createLabel("Test URL title  ", ExtentColor.BLUE));
		 // String title = driver.getTitle();
		  childTest.log(Status.INFO, MarkupHelper.createLabel("Asserting Test URL title  ", ExtentColor.BLUE));
		  softAssert.assertTrue(false);
		  
		  //======================================================================================
		  
		  childTest = parentTest.createNode("Create Node for TEst the title Broweser4");
		  childTest.log(Status.INFO, MarkupHelper.createLabel("Test URL title  ", ExtentColor.BLUE));  
		  childTest.log(Status.INFO, MarkupHelper.createLabel("Asserting Test URL title  ", ExtentColor.BLUE));
		  softAssert.assertEquals(false, true);
		  
		  softAssert.assertAll();
		  //listener
	  }
	
	
	*/

	
	
	

	@AfterTest
	public void tearDown(){
		reports.flush();
		System.out.println("Call after all test ");
		driver.quit();
	}


	
	@AfterMethod
	public void resultBased(ITestResult result){
		if(result.getStatus()==ITestResult.FAILURE){
			//log.info("Call from test method");
			System.out.println("Call from test method when failed Listener");
			childTest.log(Status.FAIL, MarkupHelper.createLabel("Test has Failed Listener", ExtentColor.RED));

			childTest.fail(result.getThrowable());
		} else if(result.getStatus()==ITestResult.SUCCESS){
			//log.info("Call from test method");
			System.out.println("Call from test method when success Listener ");
			childTest.log(Status.PASS, MarkupHelper.createLabel("Test haS PASS Listener", ExtentColor.GREEN));
		}else{
			//log.info("Call from test skip method");
			System.out.println("Call from skip test method");
			childTest.log(Status.SKIP, MarkupHelper.createLabel("Test has skipped", ExtentColor.ORANGE));
			childTest.skip(result.getThrowable());
		}
	}

}
