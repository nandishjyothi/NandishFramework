package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.utils.Constants;

public class ProductInfoPageTest extends BaseTest {
	
	@BeforeClass
	public void productInfoSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim(),
				prop.getProperty("pin"));
	}
	
	@Test(priority = 1)
	public void productHeaderTest() {
		searchResultspage = accountsPage.doSearch("MacBook");
		productInfoPage = searchResultspage.selectProduct("MacBook");
		Assert.assertEquals(productInfoPage.getProductHeader(),"MacBook");
		
	}
	
	@Test(priority = 2)
	public void productImagesCountTest() {
		searchResultspage = accountsPage.doSearch("MacBook");
		productInfoPage = searchResultspage.selectProduct("MacBook");
		Assert.assertEquals(productInfoPage.getProductImagesCount(),Constants.MACBOOK_IMAGE_COUNT);
	}
	
	@Test(priority = 3)
	public void productInfoTest() {
		searchResultspage = accountsPage.doSearch("MacBook");
		productInfoPage = searchResultspage.selectProduct("MacBook");
		Map<String,String> actProductInfoMap = productInfoPage.getProductInfo();
		actProductInfoMap.forEach((k,v) -> System.out.println(k+" : "+v));
		softAssert.assertEquals(actProductInfoMap.get("name"), "MacBook");
		softAssert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(actProductInfoMap.get("price"), "$602.00");
		softAssert.assertAll();
	}
	
	
	
	
	

}
