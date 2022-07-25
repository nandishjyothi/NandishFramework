package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ExcelUtil;

public class RegistrationPageTest extends BaseTest {
	
	@BeforeClass
	public void setupRegistration() {
		registrationPage = loginPage.goToRegisterPage();	
	}
	
	public String getRandomEmail() {
		//once the email is used it cannot be used again so random email generated using Random class
		Random randomGenerator = new Random();
		String email = "TestAutomation"+randomGenerator.nextInt(1000)+"@gmail.com";
		return email;
	}
	
	@DataProvider
	public Object[][] getRegisterData() {
		return ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
	}
	
	@Test(dataProvider = "getRegisterData")
	public void userRegistrationTest(String firstName,String lastName,String
			password,String subscribe) {
		Assert.assertTrue(registrationPage.accountRegistration( firstName, lastName, getRandomEmail(),
				password, subscribe));
	}
	

}
