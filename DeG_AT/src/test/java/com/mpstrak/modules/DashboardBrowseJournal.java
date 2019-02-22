package com.mpstrak.modules;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.mpstrak.utility.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DashboardBrowseJournal extends Utility {
	
	WebDriver driver;
	
	final static Logger logger = Logger.getLogger(DashboardBrowseJournal.class);
	
	public DashboardBrowseJournal(WebDriver driver){
	this.driver= driver;
	PageFactory.initElements(driver, this);
	
	}
	
	@FindBy(id="searchLabel")
	WebElement taskFilter ; 
	
	@FindBy(name = "clear")
	WebElement clear_bt;
	
	@FindBy(css="span.pull-right:nth-child(1) > button:nth-child(3)")
	WebElement find ; 
	
	@FindBy(css="#load_userGrid")
	WebElement loadGrid ; 
	
	@FindBy(css = "#userGrid_pager_right > div:nth-child(1)") ////*[@id='userGrid_pager_right']  // //td[@id='userGrid_pager_right']/div
	WebElement gridCountArticle;
	
	
	//ISSUE Grid 
	
	@FindBy(css="#load_remoteIssueGridUrl")
	WebElement issueGrid;
	
	@FindBy(css = "#remoteIssueGridUrl_pager_right > div:nth-child(1)") ////*[@id='userGrid_pager_right']  // //td[@id='userGrid_pager_right']/div
	WebElement gridCountIssue;
	
	
	public boolean articleDashboard(ExtentTest extentTest) throws InterruptedException {
		
		logger.info("Hello i am in dashboardbrowse");
		taskFilter.click();
		Thread.sleep(5000);
		clear_bt.click();
		
		waitGridLoad(loadGrid, "style", "display: block;"); //display: none; display: block;
		String resultOnclear = gridCountArticle.getText() ;
		resultOnclear = resultOnclear.trim();
		System.out.println("Print-->"+resultOnclear);
		logger.info("Count after clear is "+resultOnclear);
		//find.click();
		waitGridLoad(loadGrid, "style", "display: block;");
		String resultfind = gridCountArticle.getText() ;
		resultfind = resultfind.trim();
		System.out.println("Print-->"+resultfind);
		logger.info("Count after clear is "+resultfind);
		
		Boolean article = resultfind.equals(resultOnclear) ; 
		logger.info("Article are equal --> "+article);
		extentTest.log(LogStatus.INFO, "Article in  the grid-->"+resultfind);
		return article;
		
//		Boolean article1 = resultfind.equals("View 1 - 10 of 2 074") ; 
//		logger.info("Article are equal --> "+article1);
		
		
		
	}
	
public boolean issueDashboard(ExtentTest extentTest) throws InterruptedException {
		
		 
		waitGridLoad(issueGrid, "style", "display: block;"); //display: none; display: block;
		String resultOnclear1 = gridCountIssue.getText() ;
		resultOnclear1 = resultOnclear1.trim();
		System.out.println("Print-->"+resultOnclear1);
		logger.info("Count after clear is "+resultOnclear1); 
		waitGridLoad(loadGrid, "style", "display: block;");
		String resultfind1 = gridCountIssue.getText() ;
		resultfind1 = resultfind1.trim();
		System.out.println("Print-->"+resultfind1);
		logger.info("Count after clear is "+resultfind1);
		
		Boolean issue = resultfind1.equals(resultOnclear1) ; 
		logger.info("Issue are equal --> "+issue);
		
		// information in extent report
		extentTest.log(LogStatus.INFO, "Issue in  the grid-->"+resultfind1);
		return issue;
		
//		Boolean article1 = resultfind.equals("View 1 - 10 of 2 074") ; 
//		logger.info("Article are equal --> "+article1);
		
		
		
	}
	
	
	
	
	
	
	

	
	
}
