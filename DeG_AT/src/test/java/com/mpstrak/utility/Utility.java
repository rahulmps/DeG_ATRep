package com.mpstrak.utility;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Utility {


	public static WebDriver driver = null;
	final public static Properties prop = loadProperties("src/main/resources/conf/config.properties");
	final public static Logger logger = Logger.getLogger(Utility.class);
	public static FileInputStream fis = null;

	private static Properties loadProperties(String filePath) {
		// TODO Auto-generated method stub

		Properties propobj = new Properties();

		try{

			fis = new FileInputStream(filePath);
			propobj.load(fis);
		}
		catch(Exception e){
			logger.info("Configuration file is not loading"+e.getMessage());
			return null;
		}
		return propobj;
	} 

	// Method to get browser and URL 

	public static WebDriver AppLaunch(String browserName , String url){

		WebDriver driver = browser(browserName);
		urlFind(driver, url);

		return driver;


	}

	private static WebDriver urlFind(WebDriver driver, String url) {

		// TODO Auto-generated method stub
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(url);
		return driver;
	}

	// Method to instantiate selected browser 	

	public static WebDriver browser(String browsername){
		if(browsername.equalsIgnoreCase("FireFox")){
			System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver.exe");
			driver=new FirefoxDriver();
		}else if(browsername.equalsIgnoreCase("ch")){ 
			//System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
			driver=new ChromeDriver();
		}else if(browsername.equalsIgnoreCase("ie")){
			System.setProperty("webdriver.ie.driver", "src/main/resources/drivers/MicrosoftDriver.exe");
			driver=new InternetExplorerDriver();	
		}
		return driver;

	}

	// Method to get the defined log properties 
	public static void loadLogger(){
		PropertyConfigurator.configure("src/main/resources/conf/Log4j.properties");

	}

	public static void waitMethod(WebElement element){
		WebDriverWait wait = new WebDriverWait(driver,5);
		wait.until(ExpectedConditions.visibilityOf(element));


	}


	//		WebDriverWait wait = new WebDriverWait(driver,5)
	//				wait.until(ExpectedConditions.visibilityOf(element));
	public static void waitGridLoad(WebElement element , String attribute , String expected) throws InterruptedException{
		try {
			String exp = element.getAttribute(attribute);
			System.out.println("pre loop Expected attri -->"+exp);
			System.out.println("Expected attri, argument -->"+expected);
			while(element.getAttribute(attribute).equals(expected)){
				System.out.println("Not expected yet");
				Thread.sleep(1000);

			}
			System.out.println(" expected ");

		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();

		}

	}

	public static boolean gridValidationColDirect(WebElement element , String expected ,int GridCol, int random ){


		List<WebElement> rowslist = element.findElements(By.tagName("tr")); 
		//System.out.println("Inside grid validation");

		Iterator<WebElement> iterateR = rowslist.iterator();
		int incre = 1;
		Boolean task = false;
		outerloop:
			while(iterateR.hasNext()) {
				WebElement row=iterateR.next(); 
				System.out.println("Inside grid validation for row");
				List<WebElement> colslist = row.findElements(By.tagName("td")); 
				System.out.println("increment-->"+incre);
				//WebElement colslist1 = row.findElement(By.cssSelector(" #GD"+incre+" > td:nth-child(16) > a:nth-child(1)"));
				WebElement colslist1;
				if(random<15){
				colslist1 = row.findElement(By.cssSelector(" #GD"+incre+" > td:nth-child("+GridCol+")"));
				}
				else{
				colslist1 = row.findElement(By.xpath(" //tr[@id='"+incre+"']/td[12]"));
					////tr[@id='10']/td[12]  xpath=//tr[@id='1']/td[12]
				}

				incre++;

				String actual1 = colslist1.getText().trim();
				System.out.println("Actual task -->"+actual1);

				if( !actual1.equalsIgnoreCase(expected)) {   
					System.out.println("Inside IF-->Fail Current task is "+colslist1.getText().trim()+" and not "+expected);
					task=false;
					break outerloop;

				}
				task=true;
			}
		System.out.println();
		return task;



	}

	//tranversing the whole table 

	public static boolean gridValidationColALL(WebElement element , String expected ,int GridCol ){

		List<WebElement> rowslist = element.findElements(By.tagName("tr")); 
		System.out.println("Inside grid validation");

		Iterator<WebElement> iterateR = rowslist.iterator();
		int incre = 1;
		boolean task = false;
		outerloop:
			while(iterateR.hasNext()) {
				WebElement row=iterateR.next();

				System.out.println("Inside grid validation for row");
				List<WebElement> colslist = row.findElements(By.tagName("td"));  
				Iterator<WebElement> iterateC = colslist.iterator();
				int counter = 1 ;
				while(iterateC.hasNext()) {  
					WebElement col=iterateC.next(); 
					String cTask = col.getText().trim(); 
					if( counter==GridCol && !(cTask.equalsIgnoreCase(expected)) ) {  
						System.out.println("Inside IF-->Fail Current task is "+col.getText().trim()+" and not "+expected);
						task=false;
						break outerloop; 
					}
					++counter; 
				}
				task=true;
				System.out.println("Task current --> "+task); 
			} 
		return task;

	}

	// Pagination last page 

	public static void paginationlast(WebElement paginationTable){
		List<WebElement> rowlist = paginationTable.findElements(By.tagName("tr"));

		Iterator<WebElement> iterator = rowlist.iterator();
		while(iterator.hasNext()){
			System.out.println("last is CLICKED size  "+rowlist.size());
			WebElement row = iterator.next();
			List<WebElement> collist = row.findElements(By.tagName("td"));
			System.out.println("last is CLICKED col size  "+collist.size());
			//row.findElement(By.id("//*[@id='last']")).click();
			Iterator<WebElement> iteratorc=collist.iterator();
			int i =1;
			System.out.println("column list size "+collist.size());
			while(i<=collist.size())
			{
				if(i==7){
					driver.findElement(By.xpath("//*[@id='last']")).click();
					driver.findElement(By.id("last")).click();
					driver.findElement(By.cssSelector("#userGrid_pager_center #last > .ui-icon")).click();
					driver.findElement(By.xpath("//td[7]/span")).click();//td[7]/span
					driver.findElement(By.xpath("//td[@id='last']/span")).click();

					System.out.println("col if 7 "+driver.findElement(By.xpath("//*[@id='last']")).isEnabled());
				}
				if(i==8){
					driver.findElement(By.xpath("//*[@id='last']")).click();
					driver.findElement(By.cssSelector("#userGrid_pager_center #last > .ui-icon")).click();
					driver.findElement(By.xpath("//td[7]/span")).click();//td[7]/span

					System.out.println("col if 8 "+driver.findElement(By.xpath("//*[@id='last']")).isEnabled());
				}
				++i;
				System.out.println("col iteration  ");
			}
		}









		/*


        Iterator<WebElement> iterator = rowlist.iterator();
		while(iterator.hasNext()){
			System.out.println("last is CLICKED size  "+rowlist.size());
			WebElement row = iterator.next();
			List<WebElement> collist = row.findElements(By.tagName("td"));
			System.out.println("last is CLICKED col size  "+collist.size());
			//row.findElement(By.id("//*[@id='last']")).click();
			Iterator<WebElement> iteratorc=collist.iterator();
			int i =1;
			while(i<=collist.size())
			{
				if(i==7){
					driver.findElement(By.xpath("//*[@id='last']")).click();
					//driver.findElement(By.cssSelector("#userGrid_pager_center #last > .ui-icon")).click();

					System.out.println("col if  "+driver.findElement(By.xpath("//*[@id='last']")).isEnabled());
				}
				++i;
				System.out.println("col iteration  ");
			}
		}



===========================================================
for(WebElement row : rowlist){
			List<WebElement> clist = row.findElements(By.tagName("td"));
			int i =1;
			for(WebElement col : clist){
				if(i==7){
					driver.findElement(By.xpath("//*[@id='last']")).click();
					//driver.findElement(By.cssSelector("#userGrid_pager_center #last > .ui-icon")).click();

					System.out.println("col if  "+driver.findElement(By.xpath("//*[@id='last']")).isEnabled());
				}
				++i;
				System.out.println("col iteration  ");

			}


		}

		 */	




	}





}
