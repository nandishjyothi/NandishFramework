package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By header = By.cssSelector(".container h1");
	private By accountSections = By.cssSelector(".media-heading");
	private By logoutLink = By.xpath("//div/a[text()='Logout']");
	private By searchField = By.name("search");
	private By searchButton = By.cssSelector(".btn.btn-light.btn-lg");
	private By opencartLogo = By.cssSelector("#logo .img-fluid");
	
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
		
	}
	
	public String getAccountPageTitle() {
		return eleUtil.doGetTitle(Constants.ACCOUNTS_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
	}
	public String getAccountsPageHeader() {
		return eleUtil.doGetText(header);
	}
	public boolean isLogoutLinkExist() {
		return eleUtil.doIsDisplayed(logoutLink);
	}
	public void logout() {
		if(isLogoutLinkExist()) {
			eleUtil.doClick(logoutLink);
		}
	}
	
	public List<String> getAccountSecList(){
		List<WebElement> accSecList = eleUtil.waitForElementsToBeVisible(accountSections, 10);
		List<String> accSecValList = new ArrayList<String>();
		for(WebElement e: accSecList) {
			String text = e.getText();
			accSecValList.add(text);
		}
		return accSecValList;
	}
	
	public boolean isSearchExist() {
		//writing the below line as the application is not working 
		driver.navigate().to("https://demo.opencart.com/");
		return eleUtil.doIsDisplayed(searchField);
	}
	
	public SearchResultsPage doSearch(String productName) {
		driver.navigate().to("https://demo.opencart.com/");
		System.out.println("Searching the product : "+productName);
		eleUtil.doSendKeys(searchField, productName);
		//eleUtil.doClick(searchButton);// because search is not working in application
		// since search not working navigating back main page and clicking on different items
		eleUtil.doClick(opencartLogo);
		return new SearchResultsPage(driver);
	}
	
	
	
	
	

}
