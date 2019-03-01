package com.mpstrak.modules;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.mpstrak.utility.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

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

		logger.info("We are on dashboardbrowse Page ");
		extentTest.log(LogStatus.INFO, "We are on dashboardbrowse Page for article search");
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


	//Search from current task 
	@FindBy(id = "currentTask_idd")
	WebElement currentTask ; 

	@FindBy(css="#userGrid_pager_center > table:nth-child(1)")
	WebElement paginationTable;

	@FindBy(css="#userGrid_pager_center > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(7)")
	WebElement last;

	@FindBy(xpath="//*[@id='userGrid']") // //*[@id="userGrid"]
	WebElement tableID;


	public boolean verifyArticleCurrentTask(ExtentTest test) throws Exception {
		int GridCol = 16; //// article current task location in grid
		String exp = null;  
		List<WebElement> lists = currentTask.findElements(By.tagName("option")); 
		int size = 0;
		int random = 1 + (int) (Math.random() * (15-1)); //same list .. till from 1 to 14 is used for article and journal 
		System.out.println("random task index --> "+random);

		for(WebElement element: lists)  
		{
			if(size==random){
				exp=element.getText();
				element.click();
				System.out.println("expected current task--> "+exp);
				logger.info("expected current task--> "+exp);
				test.log(LogStatus.INFO, "expected current task--> "+exp);
				break;
			}
			size++;
		}
		find.click();
		waitGridLoad(loadGrid, "style", "display: block;"); 
		Thread.sleep(1000);

		//WebDriverWait wait=new WebDriverWait(driver, 20);
		//WebElement  lastIsClickable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='last']")));
		//lastIsClickable.click();
		while(!last.isEnabled()){

			System.out.println("last is not Enabled ");

		} 

		//boolean status = gridValidationColALL(tableID, exp);
		boolean status = gridValidationColDirect(tableID, exp,GridCol,random);


		//paginationlast(paginationTable); //  working randomly  
		return status;
	}
	
	// When issue task is selected 
	@FindBy(id="remoteIssueGridUrl")
	WebElement IssueGridUrl ;
	
	public boolean verifyIssueCurrentTask(ExtentTest test) throws Exception {
		clear_bt.click();
		int GridCol = 8; // issue current task location in grid
		String exp = null;  
		List<WebElement> lists = currentTask.findElements(By.tagName("option")); 
		int size = 0;
		int random = 23 + (int) (Math.random() * (16-23)); 
		System.out.println("random task index --> "+random);

		for(WebElement element: lists)  
		{
			if(size==random){
				exp=element.getText();
				element.click();
				System.out.println("expected current task--> "+exp);
				logger.info("expected current task--> "+exp);
				test.log(LogStatus.INFO, "expected current task--> "+exp);
				break;
			}
			size++;
		}
		find.click();
		waitGridLoad(issueGrid, "style", "display: block;"); 
		Thread.sleep(1000);

		//WebDriverWait wait=new WebDriverWait(driver, 20);
		//WebElement  lastIsClickable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='last']")));
		//lastIsClickable.click();
		while(!last.isEnabled()){
			System.out.println("last is not Enabled ");
		} 
		//boolean status = gridValidationColALL(tableID, exp);
		boolean status = gridValidationColDirect(IssueGridUrl, exp,GridCol,random);
		//paginationlast(paginationTable); //  working randomly  
		return status;
	}


	//When single journal is selected to browse on dashboard 

	@FindBy(id="journalAcronymID")
	WebElement journalSearch ;

	public boolean verifyJournalSearch(ExtentTest test) throws Exception {
		clear_bt.click();
		String exp = null;  
		int journalGridCol = 8;
		List<WebElement> lists = journalSearch.findElements(By.tagName("option"));
		int size = 0;
		int random = 1 + (int) (Math.random() * (15-1)); //same list .. till from 1 to 14 is used for article and journal
		System.out.println("random task index --> "+random);

		for(WebElement element: lists)  
		{
			if(size==random){
				exp=element.getText();
				element.click();
				System.out.println("expected Journal Acronym --> "+exp);
				logger.info("expected Journal Acronym --> "+exp);
				test.log(LogStatus.INFO, "expected Journal Acronym --> "+exp);
				break;
			}
			String var2 = element.getText();
			//System.out.println(var2);
			size++;
		}
		find.click();
		waitGridLoad(loadGrid, "style", "display: block;"); 
		Thread.sleep(1000);

		//WebDriverWait wait=new WebDriverWait(driver, 20);
		//WebElement  lastIsClickable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='last']")));
		//lastIsClickable.click();
		while(!last.isEnabled()){

			System.out.println("last is not Enabled ");

		} 

		//boolean status = gridValidationColALL(tableID, exp ,journalGridCol);
		boolean status = gridValidationColDirect(tableID, exp  ,journalGridCol,random);
		//paginationlast(paginationTable); //  working randomly  

		return status;

	}

	
	// Save Search 
	@FindBy(id="saveSearchFind")
	WebElement saveSearch ;
	
	public boolean verifySaveSearch(ExtentTest extentTest){
		clear_bt.click();
		saveSearch.click();
		return true;
		
	}


}
