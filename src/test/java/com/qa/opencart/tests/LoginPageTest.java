package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.listeners.TestAllureListener;
import com.qa.opencart.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("EpicID 1004: Design Open Cart App - Login Page")
@Story("US 1004: Open Cart login design with multiple features")
@Listeners(TestAllureListener.class)
public class LoginPageTest extends BaseTest {
	
	
	
	@Description("Login page Title Test")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 1)
	public void loginPageTitleTest() {

		String actTitle = loginPage.getLoginPageTitle();
		System.out.println("Page title : " + actTitle);
		// to avoid hard coded value we have to create Constants.java class
		Assert.assertEquals(actTitle, Constants.LOGIN_PAGE_TITLE);

	}
	@Description("Login page URL Test")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 2)
	public void loginPageUrlTest() {
		Assert.assertTrue(loginPage.getLoginPageUrl());

	}
	
	@Description("forgot pwd link Test")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3)
	public void forgotPwdLinkTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Description("register link Test")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 4, enabled = false)
	public void registerLinkTest() {
		Assert.assertTrue(loginPage.isRegisterLinkExist());
	}
	

	@Description("login Test with credentials")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 5)
	public void loginTest() {
		accountsPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim(),
				prop.getProperty("pin"));
		Assert.assertEquals(accountsPage.getAccountPageTitle(), Constants.ACCOUNTS_PAGE_TITLE);	
	}

}
