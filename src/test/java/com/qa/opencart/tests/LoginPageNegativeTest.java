package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginPageNegativeTest extends BaseTest {
	
	
	@DataProvider
	public Object[][] loginWrongTestData() {
		return new Object[][] {
			{"test11@gmail.com","test@123"},
			{"nandishjyothi1@gmail.com","test@123"},
			{" ","test@123"},
			{"@#@#@gmail.com","test@123"},
			{" "," "}
		};
	}
	
	@Test(dataProvider = "loginWrongTestData")
	public void loginNegativeTest(String un,String pwd) {
		
		Assert.assertFalse(loginPage.doLoginWithWrongCredentials(un, pwd));
	}
}
