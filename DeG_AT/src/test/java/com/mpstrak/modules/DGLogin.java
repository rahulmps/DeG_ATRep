package com.mpstrak.modules;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mpstrak.utility.Utility;

public class DGLogin extends Utility {
  
	final static Logger logger = Logger.getLogger(DGLogin.class);
	
	public DGLogin(WebDriver driver){
		this.driver= driver;
	}
	WebDriver driver;
	

	@FindBy(id="userId") 
	WebElement userId; 
	//#userId
	

	@FindBy(id="pwd") 
	WebElement pwd; 
	
	@FindBy(xpath=".//*[@id='lblUn']")
	WebElement messageU;

	@FindBy(xpath=".//*[@id='lblPwd']")
	WebElement messageP;

	@FindBy(css="#h1>center>b")
	WebElement invalid;
	
	 
	
	
	@FindBy(css=".btn-middle.btn.btn-inverse.pull-right") 
	WebElement login; 
	
	public String username() throws InterruptedException{
		Thread.sleep(6000);
		userId.clear();
		userId.sendKeys(prop.getProperty("username"));
		logger.info(" Username entered  ");
		String title= driver.getTitle();
		logger.info(" Username entered  ");
		logger.info(" Username entered  ");
		return title;
		
		//Assert.assertTrue(true);
	}
	
	public String usernameempty() throws InterruptedException{
		Thread.sleep(6000);
		
		login.click();	
		String message= messageU.getText();
		
		 
		logger.info(" Username is not entered  ");
		 
		return message;
		
		//Assert.assertTrue(true);
	}
	
	public String passempty() throws InterruptedException{
		Thread.sleep(6000);
		userId.sendKeys(prop.getProperty("username"));
		logger.info(" Username entered ");
		logger.info(" Password is not entered  ");
		login.click();	
		String message= messageP.getText();
		
		 
		logger.info(" Password is not entered  "+message);
		 
		return message;
		
		//Assert.assertTrue(true);
	}
	
	
	public String setPassword(){
		pwd.sendKeys(prop.getProperty("password"));
		logger.info(" password entered  ");
		//Assert.assertTrue(false);
		String title= driver.getTitle();
		logger.info(" Username entered  ");
		return title;
	}
	
	public String setPasswordINV(){
		pwd.sendKeys("ddd");
		logger.info(" password entered  ");
		
		login.click();
		String title = invalid.getText();
		return title;
	}
	
	public String getLoginTitle(){
		return driver.getTitle();
		
	}
	
	public String clickLogin(){
		
		try{
			if(prop.getProperty("username").equals("admin")){
				//bm_click(login);
				login.click();	
				
			}
			else if(prop.getProperty("username").equals("jayantpe"))
			login.click();
			else 
			login.click();
				
		}
		catch (Exception e){
			logger.info("Exception in login "+e.getMessage());
		}
		String log = driver.findElement(By.cssSelector("#trackit>span>a")).getText();
		System.out.println("Check login-->"+log);
		return log;
		
	}
	
	
}
