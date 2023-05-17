package com.itcore.testcases;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.itcore.base.TestBase;
import com.itcore.pages.HomePage;
import com.itcore.pages.ResultPage;
import com.itcore.utils.TestUtil;

public class ITCore_Test extends TestBase {

	String location;
	int daysFromCheckInDateAfterWeek, adults, children;
	int startRowNumber, endRowNumber;
	HomePage homePage;
	ResultPage resultPage;

	public ITCore_Test() {
		super();
	}

	@BeforeSuite()
	public void setExtent() {

		extent = new ExtentReports("AutoReport/index.html", true);
		extent.addSystemInfo("Framework", "Page Object");
		extent.addSystemInfo("Author", "Ahmed Ashour");
		extent.addSystemInfo("Enviroment", "Win 11");
		extent.addSystemInfo("Test", "ITCore Tech Test");

	}

	@BeforeMethod
	public void start(Method method) throws InterruptedException, Throwable {
		logger = extent.startTest(method.getName());
		initialization(prop.getProperty("url")); // From config.properties
		// The following for multiple data lines, but for a single data line row number = end row number = 1.
		startRowNumber = Integer.parseInt(prop.getProperty("startRowDataNumber"));
		endRowNumber = Integer.parseInt(prop.getProperty("endRowDataNumber"));
		pagesInitialization();
	}

	@Test(priority = 1)
	public void TC_VerifyResultsMatchSearchCriteria() throws Exception {
		for (int rowNumber = startRowNumber; rowNumber <= endRowNumber; rowNumber++) {
			// read test data
			readTestData(rowNumber);
			homePage.closePopUp();
			homePage.selectSearchLocation (location); //"Rome, Italy" 
			homePage.pickCheckInDateAfterWeek();
			homePage.pickCheckOutDateWithWeekOption(daysFromCheckInDateAfterWeek); //7
			homePage.selectNumberOfGuests(adults,children); //2 adults and 1 children. 
			homePage.searchForProperties();

			String[] city=location.split(",");
			System.out.println("*******************************");
			System.out.println("The city: " + city[0]);
			System.out.println("The Selected city: " + resultPage.getLocation());
			System.out.println("CheckIn Day: " + homePage.getCheckInDay());
			System.out.println("CheckOut Day: " + homePage.getCheckOutDay());
			System.out.println("The Dates: " + resultPage.getDates());
			System.out.println("Number Of Guests: " + resultPage.getNumberOfGuests());
			System.out.println("*******************************");
			
			Assert.assertTrue(resultPage.getLocation().contains(city[0]));
			Assert.assertTrue(resultPage.getDates().contains(homePage.getCheckInDay()));
			Assert.assertTrue(resultPage.getDates().contains(homePage.getCheckOutDay()));
			Assert.assertTrue(resultPage.getNumberOfGuests().contains(String.valueOf(adults+children)));
		}
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws Throwable {

		if (result.getStatus() == ITestResult.FAILURE) {

			logger.log(LogStatus.FAIL, "Test Failed " + result.getThrowable());
			String picturePath = TestUtil.TakeSnapshot(driver, result.getName());
			logger.log(LogStatus.FAIL, logger.addScreenCapture(picturePath));

		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test case Skipped is " + result.getName());

		} else {
			logger.log(LogStatus.PASS, "Test passed");
			String picturePath = TestUtil.TakeSnapshot(driver, result.getName());
			logger.log(LogStatus.PASS, logger.addScreenCapture(picturePath));

		}

		extent.endTest(logger);
		driver.close();
	}

	@AfterSuite()
	public void endReport() {

		extent.flush();
		extent.close();
	}

	private void pagesInitialization() {
		homePage = new HomePage();
		resultPage = new ResultPage();
	}

	private void readTestData(int rowNumber) throws IOException {
		String inputFileName = prop.getProperty("testDataFolderPath");
		String sheetName = prop.getProperty("sheetName");
		location = TestUtil.readFromExcelFile(inputFileName, sheetName, "location", rowNumber);
		daysFromCheckInDateAfterWeek = Integer.parseInt(TestUtil.readFromExcelFile(inputFileName, sheetName, "daysFromCheckInDateAfterWeek", rowNumber));
		adults = Integer.parseInt(TestUtil.readFromExcelFile(inputFileName, sheetName, "adults", rowNumber));
		children = Integer.parseInt(TestUtil.readFromExcelFile(inputFileName, sheetName, "children", rowNumber));
		System.out.println("Data has been loaded");
		return;
	}
}
