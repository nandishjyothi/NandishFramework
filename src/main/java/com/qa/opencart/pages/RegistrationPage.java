package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

public class RegistrationPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	
	private By subscribeYes = By.xpath("//input[@id='input-newsletter-yes' and @type='radio']");
	private By subscribeNo = By.xpath("//input[@id='input-newsletter-no and @type='radio']']");
	
	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//button[@type='submit']");
	private By successMesg = By.xpath("//div/h1");
	
	private By logoutLink = By.linkText("Logout");
	private By openCartregisterLink = By.linkText("Register");
	
	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public boolean accountRegistration(String firstName,String lastName,String email,String
			password,String subscribe) {
		
		eleUtil.doSendKeys(this.firstName, firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.emailId, email);
		eleUtil.doSendKeys(this.password, password);
		
		if(subscribe.equals("yes")) {
			eleUtil.doClick(subscribeYes);
		}
		else {
			eleUtil.doClick(subscribeNo);
		}
		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueButton);
		
		String mesg = eleUtil.waitForElementToBeVisible(successMesg, 10, 1000).getText();
		if(mesg.contains(Constants.REGISTER_SUCCESS_MESSG)) {
			eleUtil.doClick(logoutLink);
			eleUtil.doClick(openCartregisterLink);
			return true;
		}
		return false;
		
		
		
	}
	
	
}
