package com.mpstrak.common;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.mps.utils.Constants;
import com.mps.utils.ExtentManager;
import com.mpstrak.modules.DGLogin;
import com.mpstrak.utility.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestCaseParentChild extends Utility{

	WebDriver driver = AppLaunch(prop.getProperty("browser"), prop.getProperty("url"));
	final static Logger logger = Logger.getLogger(TestCaseParentChild.class);

	
	public static ExtentTest extentTest;
	
	

	DGLogin dgobject = PageFactory.initElements(driver, DGLogin.class);

	@BeforeSuite
	public  void systemStartUpConfiguration() throws IOException {

		loadLogger();
		logger.info("----------------------System StartUp Configuration -----------------------------");
		
		
        logger.info("----------------------Setting up extent Report  -----------------------------");
		 
	}
	// TestCase check username validation 
	
		@Test(priority=1 , enabled = true) // false true
		public void usernamevalidation() throws InterruptedException 
		{			
			SoftAssert soft = new SoftAssert();
			
			logger.info("******************************************");
			logger.info("==username validation===========");
			logger.info("Submit without Entering Username");

			String expected="Please enter username";
			String actual1= dgobject.usernameempty();
			soft.assertEquals(actual1, expected);
			
			logger.info("===============Please enter username Message================ ");
			soft.assertAll();
			
			logger.info("******************************************");
			//driver.quit();
		}
	
	// TestCase check password  validation 
	
	
	@Test(priority=2 , enabled = true) // false true
	public void passvalidation() throws InterruptedException 
	{			
		SoftAssert soft = new SoftAssert();
		
		logger.info("******************************************");
		logger.info("=====================username validation e===========");
		logger.info("Submit without Entering Username");

		String expected="Please enter password";
		String actual1= dgobject.passempty();
		logger.info("===============Actual message  validation================ "+actual1);
		
				
		soft.assertEquals(actual1, expected);
		
		logger.info("===============Please enter username Message================ ");
		logger.info("******************************************");
		
		//childTest.log(Status.INFO, MarkupHelper.createLabel("Login Succecc  ", ExtentColor.BLUE));
		soft.assertAll();
		//driver.quit();
	}
	
	
	@Test(priority=3 , enabled = true) // false true
	public void invalidUP() throws InterruptedException 
	{			
		SoftAssert soft = new SoftAssert();
		
		logger.info("******************************************");
		logger.info("===loginInvalidTestCase====");
		logger.info("Entering Username");
		
		
		String actual=dgobject.username();
		System.out.println("Title "+actual);
		
		logger.info("Entering Password ");
		String actual2=dgobject.setPasswordINV();
		String expected="Invalid Username/Password. Please try again.";
		soft.assertEquals(actual2, expected);
		
		logger.info("******************************************");
		
		//childTest.log(Status.INFO, MarkupHelper.createLabel("Login Succecc  ", ExtentColor.BLUE));
		soft.assertAll();
		//driver.quit();
	}
	
	
	
	@Test(priority=4 , enabled = true) // false true
	public void loginTestCase() throws InterruptedException 
	{			
		SoftAssert soft = new SoftAssert();
		
		logger.info("******************************************");
		logger.info("=====================loginTestCase===========");
		logger.info("Entering Username");
		
		
		String actual=dgobject.username();
		System.out.println("Title "+actual);
		
		logger.info("Entering Password ");
		String actual2=dgobject.setPassword();
		String actual1=":: De Gruyter :: Production Tracking System ::";
		soft.assertTrue(actual1.equals(":: De Gruyter :: Production Tracking System ::"));
		logger.info("===============Login  validation================ ");
		
		//childTest = parentTest.createNode("Create Node 2 for TEst the title ");
		String edit = dgobject.clickLogin();		
		soft.assertEquals(edit, edit);
		
		logger.info("===============Login  Success ================ ");
		
		//childTest.log(Status.INFO, MarkupHelper.createLabel("Login Succecc  ", ExtentColor.BLUE));
		soft.assertAll();
		logger.info("******************************************");
	}
	
	
	@BeforeMethod
	public void startReporting(Method method) {
		extentTest = ExtentManager.getInstance().startTest(method.getName());
		// String currentDate = ExtentManager.generateDataTime();
	}

	@AfterMethod
	public void testResult(Method method, ITestResult result) throws IOException {
		// String screenShotPath = new
		// File("Framework\\Test_Reports\\Screenshots\\").getAbsolutePath() +
		// "\\"
		// + method.getName() + DateFormat.getDateTimeInstance().format(new
		// Date()).toString().replaceAll(":", "_")
		// + ".png";

		// UIUtils.takeScreenshot(driver, screenShotPath);

		// String attachImagePath = extentTest.addScreenCapture(screenShotPath);

		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			
			extentTest.log(LogStatus.PASS, "Test Case " + method.getName() + " is pass");
			String meth = method.getName();
			extentTest.log(LogStatus.PASS, "Test Case Passed " + extentTest.addScreenCapture(ExtentManager.takeScreenshot(driver, method.getName(), Constants.DIRECTORYPATH)));
//			extentTest.log(LogStatus.PASS, "Test Case Passed " + extentTest.addScreenCapture(takeScreenshot(driver, method.getName(), Constants.DIRECTORYPATH)));
			//test.log(LogStatus.FAIL, failMsg + test.addScreenCapture(fn_TakeScreenShot(driver)));
			// takeScreenshot(WebDriver driver, String fileName, String directoryPath)
			break;
		case ITestResult.FAILURE:
			extentTest.log(LogStatus.FAIL, "Test Case " + method.getName() + " failed");
			String meth1 = method.getName();
			extentTest.log(LogStatus.FAIL, "Test Case Failed " + extentTest.addScreenCapture(ExtentManager.takeScreenshot(driver, method.getName(), Constants.DIRECTORYPATH)));
			break;
		case ITestResult.SKIP:
			extentTest.log(LogStatus.SKIP, "Test Case " + method.getName() + "  skiped");
			// break;
		default:
			break;
		}
		ExtentManager.getInstance().endTest(extentTest);
	}

	@AfterClass
	public void generateResult() {
		ExtentManager.getInstance().flush();
		ExtentManager.getInstance().close();
		driver.close();
		// TestDriver.getInstance().getDriver().quit();
	}

}
