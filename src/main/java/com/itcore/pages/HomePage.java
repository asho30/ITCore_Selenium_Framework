package com.itcore.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.itcore.base.TestBase;

public class HomePage extends TestBase {
	String closeButtonCss = "button[aria-label='Close']";
	String locationCss = "button[data-index='0']";
	String locationInputCss = "input#bigsearch-query-location-input";
	String weekCheckXpath = "//div[text()='7 days']";
	String addGuestsXpath = "//div[text()='Who']";
	String increaseAdultsButtonCss = "div[id='stepper-adults'] button[aria-label='increase value']";
	String increaseChildrenButtonCss = "div[id='stepper-children'] button[aria-label='increase value']";
	String searchButtonCss = "button[data-testid='structured-search-input-search-button']";
	LocalDate myDateObj = LocalDate.now();
	LocalDate checkOutDateObj;
	WebDriverWait wait = new WebDriverWait(driver, 10);
	
	public HomePage() {

		PageFactory.initElements(driver, this);

	}

	public void closePopUp() {
		WebElement closeButton = driver.findElement(By.cssSelector(closeButtonCss));
		wait.until(ExpectedConditions.visibilityOf(closeButton));
		System.out.println("Page has been loaded");
		closeButton.click();
	}

	public void selectSearchLocation(String location) {
		String locationOptionXpath = "//div[text()='" + location +"']";
		WebElement locationBox = driver.findElement(By.cssSelector(locationCss));
		wait.until(ExpectedConditions.visibilityOf(locationBox));
		locationBox.click();
		WebElement locationInput = driver.findElement(By.cssSelector(locationInputCss));
		wait.until(ExpectedConditions.visibilityOf(locationInput));
		locationInput.sendKeys(location);
		WebElement locationOption = driver.findElement(By.xpath(locationOptionXpath));
		locationOption.click();
	}
	
	public void pickCheckInDateAfterWeek() {
		WebElement weekCheckIn = driver.findElement(By.xpath(weekCheckXpath));
		weekCheckIn.click();
	    System.out.println("Before formatting: " + myDateObj);
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	    String formattedDate = myDateObj.format(myFormatObj);
	    System.out.println("After formatting: " + formattedDate);
	    String currentDateCss = "div[data-testid='calendar-day-"+ formattedDate +"']";
	    WebElement currentDate = driver.findElement(By.cssSelector(currentDateCss));
	    currentDate.click();
	}
	
	public void pickCheckOutDateWithWeekOption(int daysFromCheckInDateAfterWeek) {
		checkOutDateObj = myDateObj.plusDays(daysFromCheckInDateAfterWeek);
		System.out.println("Before formatting: " + checkOutDateObj);
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	    String formattedDate = checkOutDateObj.format(myFormatObj);
	    System.out.println("After formatting: " + formattedDate);
	    String checkOutDateCss = "div[data-testid='calendar-day-"+ formattedDate +"']";
	    WebElement checkOutDate = driver.findElement(By.cssSelector(checkOutDateCss));
	    checkOutDate.click();
		
	}

	public void selectNumberOfGuests(int adults, int children) {
		WebElement addGuests = driver.findElement(By.xpath(addGuestsXpath));
		addGuests.click();
		WebElement increaseAdultsButton = driver.findElement(By.cssSelector(increaseAdultsButtonCss));
		WebElement increaseChildrenButton = driver.findElement(By.cssSelector(increaseChildrenButtonCss));
		for (int i = 0; i < adults; i++) {
			increaseAdultsButton.click();
		}
		for (int i = 0; i < children; i++) {
			increaseChildrenButton.click();
		}
	}

	public void searchForProperties() {
		WebElement searchButton = driver.findElement(By.cssSelector(searchButtonCss));
		searchButton.click();
		
	}

	public String getDates() {
		myDateObj.getDayOfMonth();
		checkOutDateObj.getDayOfMonth();
		return null;
	}

	public String getCheckInDay() {
		return String.valueOf(myDateObj.getDayOfMonth());
	}

	public String getCheckOutDay() {
		return String.valueOf(checkOutDateObj.getDayOfMonth());
	}




}
