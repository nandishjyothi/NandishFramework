package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	// 1. declare private driver
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 2.page constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	//3. Private By locators
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.cssSelector(".btn.btn-primary.btn-lg.hidden-xs");
	private By registerLink = By.xpath("//div/a[text()='Register']");
	private By forgotPwdLink = By.linkText("Forgot your password?");
	private By fourDigitPin = By.id("input-pin");
	private By continueBtn = By.cssSelector(".btn.btn-primary.btn-lg");
	private By loginErrorMesg = By.cssSelector(".alert.alert-danger");
	private By openCartregisterLink = By.linkText("Register");
	
	//4. page actions
	@Step("getting login page title value")
	public String getLoginPageTitle() {
		return eleUtil.doGetTitleWithFraction(Constants.LOGIN_PAGE_TITLE, Constants.DEFAULT_TIME_OUT);
	}
	
	@Step("getting login page URL")
	public Boolean getLoginPageUrl() {
		return eleUtil.waitForURLToContain(Constants.LOGIN_PAGE_URL_FRACTION, Constants.DEFAULT_TIME_OUT);
	}
	
	@Step("checking forgot pwd link exits or not")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.doIsDisplayed(forgotPwdLink);
	}
	
	@Step("checking register link exists or not")
	public boolean isRegisterLinkExist() {
		return eleUtil.doIsDisplayed(registerLink);
	}
	
	@Step("do login with username: {0} and password:{1}")
	public AccountsPage doLogin(String un,String pwd,String pin) {
		System.out.println("Login with: "+un+":"+pwd);
		eleUtil.doSendKeys(emailId, un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		eleUtil.doSendKeys(fourDigitPin, pin);
		eleUtil.doClick(continueBtn);
		
		//sending the Account page object page chaining model 
		return new AccountsPage(driver);
	}
	
	@Step("do login with wrong username: {0} and password:{1}")
	public boolean doLoginWithWrongCredentials(String un, String pwd) {
		System.out.println("Login with: "+un+":"+pwd);
		eleUtil.doSendKeys(emailId, un);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		String errorMesg = eleUtil.doGetText(loginErrorMesg);
		System.out.println(errorMesg);
		if(errorMesg.contains(Constants.LOGIN_ERROR_MESSG)) {
			System.out.println("Login is not Done...");
			return false;
		}
		return true;
	}
	
	@Step("navigating to registration page ")
	public RegistrationPage goToRegisterPage() {
		driver.navigate().to("https://demo.opencart.com/index.php?route=account/login&language=en-gb");
		eleUtil.doClick(openCartregisterLink);
		return new RegistrationPage(driver);
	}
	
	

}
