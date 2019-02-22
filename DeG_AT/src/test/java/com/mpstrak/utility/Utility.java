package com.mpstrak.utility;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
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
	
	
}
