package com.mpstrak.utility;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;

public class TestListener implements ITestListener {
	static Logger log = Logger.getLogger(TestListener.class);
	

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		log.info("Test Case start");
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		log.info("Test Case Finish");
	}
	
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	
   
}
