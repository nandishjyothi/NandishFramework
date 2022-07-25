package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.Errors;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accPageSetup() {
		accountsPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim(),
				prop.getProperty("pin"));
	}

	@Test(priority = 1)
	public void accPageTitleTest() {
		String actTitle = accountsPage.getAccountPageTitle();
		System.out.println("acc page title : " + actTitle);
		Assert.assertEquals(actTitle, Constants.ACCOUNTS_PAGE_TITLE);
	}

	@Test(priority = 2)
	public void accPageHeaderTest() {
		String header = accountsPage.getAccountsPageHeader();
		System.out.println("acc page header is : " + header);
		Assert.assertEquals(header, Constants.ACCOUNTS_PAGE_HEADER,Errors.ACC_PAGE_HEADER_NOT_FOUND_ERROR_MESSG);

	}

	@Test(priority = 3)
	public void isLogoutExistTest() {
		Assert.assertTrue(accountsPage.isLogoutLinkExist());
	}

	@Test(priority = 4)
	public void accPageSecTest() {
		List<String> actAccSecList = accountsPage.getAccountSecList();
		Assert.assertEquals(actAccSecList, Constants.getExpectedAccSecList());

	}
	@DataProvider
	public Object[][] productData(){
		return new Object[][] {
			{"MacBook Pro"},
			{"Apple"},
			{"Samsung"},
		};
	}
	
	@Test(priority = 5, dataProvider = "productData")
	public void searchTest(String productName) {
		searchResultspage = accountsPage.doSearch(productName);
		Assert.assertTrue(searchResultspage.getProductListCount()>0);
	}
	
	@DataProvider
	public Object[][] productSelectData(){
		return new Object[][] {
			{"MacBook","MacBook"},
			{"iPhone","iPhone"},
			{"Apple","Apple Cinema 30\""},
			{"canon","Canon EOS 5D"}
		};
	}
	
	@Test(priority = 6, dataProvider = "productSelectData")
	public void selectProductTest(String productName, String mainProductName) {
		searchResultspage = accountsPage.doSearch(productName);
		productInfoPage = searchResultspage.selectProduct(mainProductName);
		Assert.assertEquals(productInfoPage.getProductHeader(),mainProductName);
		
	}

}
