package com.mps.utils;



import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
//import ru.yandex.qatools.ashot.screentaker.ShootingStrategy;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class ExtentManager {
	private static ExtentReports extent;
	//
	ExtentReports reports;
	ExtentTest test;

	

	public static String generateDataTime() {
		Date now = new Date();
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		String currentDate = df.format(now).toString().replace("/", "_");
		currentDate = currentDate.toString().replace(" ", "_");
		currentDate = currentDate.toString().replace(",", "_");

		return currentDate;
	}

	public static ExtentReports getInstance() {
		if (extent == null) {
			Date d = new Date();
			String fileName = d.toString().replace(":", "_").replace(" ", "_") + ".html";
			try {
				extent = new ExtentReports(new File(Constants.REPORT_PATH + fileName).getCanonicalPath(), true,
						DisplayOrder.NEWEST_FIRST);
			} catch (IOException e) {
				e.printStackTrace();
			}

			extent.loadConfig(new File(System.getProperty("user.dir") + "//ReportConfig.xml"));
			extent.addSystemInfo("Selenium Version", "3.4.0").addSystemInfo("Environment", "QA");
		}
		return extent;
	}

	public static String takeScreenshot(WebDriver driver, String fileName, String directoryPath) throws IOException {
		String fileName1 = fileName + ".png";
		File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		FileUtils.copyFile(screenshotFile, new File(directoryPath + fileName1));
		String destination = directoryPath + fileName1;
		return destination;
	}
	
	// Full page screenshot 
	
	public static String takeScreenshot1(WebDriver driver, String fileName, String directoryPath) throws IOException {
		String fileName1 = fileName + ".png";
		
		
	    Screenshot screenshot = new AShot().shootingStrategy( ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
	    ImageIO.write(screenshot.getImage(),"PNG",new File(directoryPath + fileName1));
		
		String destination = directoryPath + fileName1;
		return destination;
	}
	
	
	
    

	
	
}