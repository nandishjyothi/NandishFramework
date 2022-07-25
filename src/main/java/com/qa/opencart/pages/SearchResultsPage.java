package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By productResults = By.xpath("//div[@class='product-thumb']/div/a/img");
	//private By macBookLink = By.xpath("//div[@class='description']/h4/a[text()='MacBook']");

	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public int getProductListCount() {
		int resultCount = eleUtil.waitForElementsToBeVisible(productResults, 10, 2000).size();
		System.out.println("The search product count: " + resultCount);
		return resultCount;
	}

	public ProductInfoPage selectProduct(String mainProductName) {
		System.out.println("Main product name is: " + mainProductName);
		List<WebElement> searchList = eleUtil.waitForElementsToBeVisible(productResults, 15, 2000);
		for (WebElement e : searchList) {
			String text = e.getAttribute("alt");
			if (text.equals(mainProductName)) {
				e.click();
				break;
			}
		}
		
		return new ProductInfoPage(driver);

	}
}
