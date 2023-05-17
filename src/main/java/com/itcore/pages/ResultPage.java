package com.itcore.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.itcore.base.TestBase;

public class ResultPage extends TestBase {
	String locationCss = "button[data-index='0'] div";
	String datesCss = "button[data-index='1'] div";
	String numberOfGuestsCss = "button[data-index='2'] :nth-child(2)";
	String resultCardsCss ="div[data-testid='card-container']";
	WebDriverWait wait = new WebDriverWait(driver, 10);
	
	public ResultPage() {
		PageFactory.initElements(driver, this);
	}

	public String getLocation() {
		wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.cssSelector(resultCardsCss))));
		WebElement location = driver.findElement(By.cssSelector(locationCss));
		System.out.println("Selected Location: " + location.getAttribute("textContent")); //innerText
		return location.getAttribute("textContent");
	}

	public String getDates() {
		WebElement dates = driver.findElement(By.cssSelector(datesCss));
		System.out.println("Selected Dates: " + dates.getAttribute("textContent"));
		return dates.getAttribute("textContent");
	}

	public String getNumberOfGuests() {
		WebElement numberOfGuests = driver.findElement(By.cssSelector(numberOfGuestsCss));
		System.out.println("Selected NumberOfGuests: " + numberOfGuests.getAttribute("textContent"));
		return numberOfGuests.getAttribute("textContent");
	}
}
