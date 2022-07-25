package com.qa.opencart.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By productHeader = By.xpath("//div[@id='product-info']//h1");
	private By productImages = By.cssSelector("div#content img");
	private By productMetaData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(1) li");
	private By productPriceData = By.cssSelector("div#content ul.list-unstyled:nth-of-type(2) li");
	private By qty = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	
	
	private Map<String, String> productInfoMap;
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getProductHeader() {
	String productHeaderText = eleUtil.doGetText(productHeader);
	System.out.println("Product header is : "+productHeaderText);
	return productHeaderText;
	}
	
	public int getProductImagesCount() {
		return eleUtil.waitForElementsToBeVisible(productImages, 10).size();
	}
	
	public Map<String, String> getProductInfo() {
		
		//hashMap will not maintain the order so we can use LinkedHashMap
		// if we want is sorted order then we can use TreeMap
		
		productInfoMap = new LinkedHashMap<String, String>();
		productInfoMap.put("name", getProductHeader());
		getProductMetaData();
		getProductPriceData();
		return productInfoMap;
		
	}
	
	private void getProductMetaData() {
		List<WebElement> metaDataList= eleUtil.getElements(productMetaData);
//		Brand: Apple
//		Product Code: Product 16
//		Reward Points: 600
//		Availability: In Stock
		for(WebElement e: metaDataList) {
			String text = e.getText();
			String meta[] = text.split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			productInfoMap.put(metaKey, metaValue);
		}	
	}
	private void getProductPriceData() {
		List<WebElement> metaPriceList= eleUtil.getElements(productPriceData);
//		$602.00
//		Ex Tax: $500.00
		
		String price = metaPriceList.get(0).getText().trim();
		String exPrice = metaPriceList.get(1).getText().trim();
		productInfoMap.put("price", price);
		productInfoMap.put("exPrice", exPrice);

	}

}
