package com.qa.opencart.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {

	// defining webDriver to use in the util method:
	// driver should not be static because we can not execute test cases parallel,
	// other test case will be waiting for the driver
	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	// initializing webDriver through constructor:
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	// for 7th approach we only have string locator value so writing by method
	public static By getBy(String locatorType, String locatorValue) {
		By locator = null;

		switch (locatorType.toLowerCase()) {
		case "id":
			locator = By.id(locatorValue);
			break;
		case "name":
			locator = By.name(locatorValue);
			break;
		case "classname":
			locator = By.className(locatorValue);
			break;
		case "xpath":
			locator = By.xpath(locatorValue);
			break;
		case "cssselector":
			locator = By.cssSelector(locatorValue);
			break;
		case "linktext":
			locator = By.linkText(locatorValue);
			break;
		case "partiallinktext":
			locator = By.partialLinkText(locatorValue);
			break;
		case "tagname":
			locator = By.tagName(locatorValue);
			break;

		default:
			System.out.println("Please provide correct locator type...");
			break;
		}
		return locator;
	}

	public WebElement getElement(By locator) {
		// generic wrapper method
		WebElement element = driver.findElement(locator);
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
	}

	// overloaded getElement method for String locator values
	public WebElement getElement(String locatorType, String locatorValue) {
		WebElement element = driver.findElement(getBy(locatorType, locatorValue));
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
		return element;
				
	}

	// overloaded method with wait
	public WebElement getElement(By locator, int timeOut) {
		return doPresenceOfElementLocated(locator, timeOut);
	}
	
	public void doClear(By locator) {
		getElement(locator).clear();
	}

	public void doSendKeys(By locator, String value) {
		doClear(locator);
		getElement(locator).sendKeys(value);
	}

	// overloaded doSendKeys method for String locator values
	public void doSendKeys(String locatorType, String locatorValue, String value) {
		getElement(locatorType, locatorValue).sendKeys(value);
	}

	public void doSendKeys(By locator, String value, int timeOut) {
		doPresenceOfElementLocated(locator, timeOut).sendKeys(value);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	// overloaded doClick method for String locator values
	public void doClick(String locatorType, String locatorValue) {
		getElement(locatorType, locatorValue).click();
	}

	// overloaded method with timeout(wait)
	public void doClick(By locator, int timeOut) {
		doPresenceOfElementLocated(locator, timeOut).click();
	}

	public String doGetText(By locator) {
		return getElement(locator).getText();
	}

	// overloaded doGetText method for String locator values
	public String doGetText(String locatorType, String locatorValue) {
		return getElement(locatorType, locatorValue).getText();
	}

	// to get different attribute value
	public String getAttributeValue(By locator, String attrName) {
		String attrVal = getElement(locator).getAttribute(attrName);
		System.out.println(attrVal);
		return attrVal;
	}

	public boolean doIsDisplayed(By locator) {
		return getElement(locator).isDisplayed();

	}

	// To verify whether element is present or not:
	public boolean isElementExist(By locator) {
		// int elementCount = driver.findElements(locator).size();// instead of this use
		// the below line for more clean code
		int elementCount = getElementsCount(locator);
		System.out.println("Total elements found: " + elementCount);
		if (elementCount >= 1) {
			System.out.println("Element is found : " + locator);
			return true;
		} else {
			System.out.println("Element is Not found : " + locator);
			return false;
		}
	}

	// for storing the link text in list<String>
	public List<String> getElementsTextList(By locator) {
		List<WebElement> eleList = getElements(locator);
		List<String> eleTextList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String eleText = e.getText();
			if (!eleText.isEmpty()) {
				eleTextList.add(eleText);
			}
		}
		return eleTextList;
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	// to print values from the list
	public void printElementValues(List<String> eleList) {
		for (String e : eleList) {
			System.out.println(e);
		}
	}

	public List<String> getAttributeList(By locator, String attributeName) {
		List<WebElement> eleList = getElements(locator);
		List<String> attrList = new ArrayList<String>();

		for (WebElement e : eleList) {
			String attrValue = e.getAttribute(attributeName);
			attrList.add(attrValue);
		}
		return attrList;

	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	/******************************
	 * Drop Down Utils
	 *************************************/
	public void doDropDownSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doDropDownSelectByVisibleText(By locator, String text) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(text);
	}

	public void doDropDownSelectByAttributeValue(By locator, String AttributeValue) {
		Select select = new Select(getElement(locator));
		select.selectByValue(AttributeValue);
	}

	public void doSelectDropDownValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.equals(value)) {
				e.click();
				break;
			}
		}

	}

	public void doSelectDropDownValueWithoutSelectClass(By locator, String value) {
		List<WebElement> optionsList = getElements(locator);

		System.out.println(optionsList.size());

		for (WebElement e : optionsList) {
			String text = e.getText();
			if (text.equals(value)) {
				e.click();
				break;
			}
		}
	}

	/*
	 * *******************************************Links UTIL
	 **********************************/

	// generic function to return list of link text
	public List<String> getLinksTextList(By locator) {
		List<WebElement> langList = getElements(locator);
		List<String> linksTextList = new ArrayList<String>();
		System.out.println(langList.size());

		for (WebElement e : langList) {
			String text = e.getText().trim();
			System.out.println(text);
			linksTextList.add(text);
		}
		return linksTextList;
	}

	// generic function to click on specific link:
	public void clickOnElementFromSection(By locator, String linkText) {

		List<WebElement> langList = getElements(locator);
		System.out.println(langList.size());

		for (WebElement e : langList) {
			String text = e.getText().trim();
			System.out.println(text);
			if (text.equals(linkText)) {
				e.click();
				break;
			}
		}
	}

	/*
	 * **********************************WEBTABLE
	 * UTIL****************************************
	 */
	// Generic way to iterate/ handle static webTable:
	public void printTable(By rowLocator, By colLocator, String beforeXpath, String afterXpath) {
		int rowCount = getElements(rowLocator).size();
		int colCount = getElements(colLocator).size();
		for (int row = 2; row <= rowCount; row++) {
			for (int col = 1; col <= colCount; col++) {
				String xpath = beforeXpath + row + afterXpath + col + "]";
				String text = doGetText(By.xpath(xpath));
				System.out.print(text + " || ");
			}
			System.out.println();
		}
	}

	/*
	 * ********************************ACTIONS
	 * UTIL**************************************************
	 */
	public void doMoveToElement(By locator) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(locator)).perform();
	}

	public void doClickOnChildMenu(By parentMenuLocator, By childMenuLocator) throws InterruptedException {
		doMoveToElement(parentMenuLocator);
		Thread.sleep(3000);
		doClick(childMenuLocator);
	}

	public void doActionsSendKeys(By locator, String value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}

	public void doActionsSendKeysOnActiveElement(By locator, String value) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).sendKeys(value).build().perform();
	}

	/*
	 * Clicks in the middle of the given element. Equivalent to:
	 * Actions.moveToElement(onElement).click()
	 * 
	 * webElement click may click anywhere on the element actions class click clicks
	 * on the middle of the element
	 * 
	 */
	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}

	public void doActionsMoveToElementClick(By locator) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(locator)).click().build().perform();
	}

	/*
	 * *********************************WAIT
	 * UTILS*******************************************
	 */

	/*
	 * presenceOfElementLocated(locator): An expectation for checking that an
	 * element is present on the DOM of a page. This does not necessarily mean that
	 * the element is visible there are chances that the element is present on the
	 * DOM but not visible on the page so we have to use other method
	 */

	// generic function for WebDriverWait
	public WebElement doPresenceOfElementLocated(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

	}

	// generic method to give custom interval time/polling time
	public WebElement doPresenceOfElementLocated(By locator, int timeOut, long intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(intervalTime));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * visibilityOfElementLocated(locator): An expectation for checking that an
	 * element is present on the DOM of a page and visible.Visibility means that the
	 * element is not only displayed but also has a height and width that is greater
	 * than 0.
	 * 
	 * @param locator
	 * @param timeOut
	 * @return
	 */
	// generic method for webDriverWait with visibilityOfElementLocated method:
	public WebElement waitForElementToBeVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// generic method to give custom interval time/polling time with
	// visibilityOfElementLocated method:
	public WebElement waitForElementToBeVisible(By locator, int timeOut, long intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(intervalTime));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	// generic method with visibilityOf.webElement method
	// not recommended
	public WebElement waitForElementToBeVisibleWithWebElement(By locator, int timeOut, long intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(intervalTime));
		return wait.until(ExpectedConditions.visibilityOf(getElement(locator)));
	}

	// generic method to handle multiple elements without interval time
	public List<WebElement> waitForElementsToBeVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	// generic method to handle multiple elements
	public List<WebElement> waitForElementsToBeVisible(By locator, int timeOut, long intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofMillis(intervalTime));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	// generic method to get text list with wait and without interval time
	public List<String> getElementsTextListWithWait(By locator, int timeOut) {

		List<WebElement> eleList = waitForElementsToBeVisible(locator, timeOut);
		List<String> eleTextList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String text = e.getText();
			eleTextList.add(text);
		}
		return eleTextList;
	}
	
	/**
	 * ***********************************Wait utils for non web elements***********************************************
	 * @param urlFraction
	 * @param timeOut
	 * @return
	 */

	// generic method to validate URL with contain method and wait(non web element)
	public Boolean waitForURLToContain(String urlFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.urlContains(urlFraction));

	}

	// generic method to validate URL with To Be method and wait (non web element)
	public Boolean waitForURLToBe(String url, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.urlToBe(url));

	}

	// generic method to get title with wait below methods:
	public String doGetTitleWithFraction(String titleFraction, int timeOut) {
		if (waitForTitleContains(titleFraction, timeOut)) {
			return driver.getTitle();
		}
		return null;
	}

	//
	public String doGetTitle(String title, int timeOut) {
		if (waitForTitleToBe(title, timeOut)) {
			return driver.getTitle();
		}
		return null;
	}

	// contains method
	public boolean waitForTitleContains(String titleFraction, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.titleContains(titleFraction));
	}

	// exact title: title is method
	public boolean waitForTitleToBe(String title, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.titleIs(title));
	}

	public Alert waitForAlert(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public String getAlertText(int timeOut) {
		return waitForAlert(timeOut).getText();
	}

	public void getAlertAccept(int timeOut) {
		waitForAlert(timeOut).accept();
		;
	}

	public void getAlertDismiss(int timeOut) {
		waitForAlert(timeOut).dismiss();
		;
	}

	public void enterAlertText(String text, int timeOut) {
		waitForAlert(timeOut).sendKeys(text);
	}
	
	public void waitForFrameByNameOrId(String nameOrId, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
	}

	public void waitForFrameByIndex(int frameIndex, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void waitForFrameByLocator(By frameLocator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}

	public void waitForFrameByElement(WebElement frameElement, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}
	
	public void clickElementWhenReady(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		
	}
	
	public void clickElementWhenReady(By locator, int timeOut, long intervalTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut),Duration.ofMillis(intervalTime));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		
	}
	
	public WebElement waitForElementPresentUsingFluentWait(By locator, int timeOut,int pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeOut))
				.pollingEvery(Duration.ofSeconds(pollingTime))
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class)
				.withMessage(Errors.ELEMENT_NOT_FOUND_ERROR_MESSG);
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		
	}
	
	public WebElement waitForElementUsingWebDriverWait(By locator, int timeOut,int pollingTime) {
		WebDriverWait wait  = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait
		.pollingEvery(Duration.ofSeconds(pollingTime))
		.ignoring(NoSuchElementException.class)
		.ignoring(StaleElementReferenceException.class)
		.withMessage(Errors.ELEMENT_NOT_FOUND_ERROR_MESSG);
		
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	/*
	 * ************************CUSTOME WAIT****************************
	 */
	public WebElement reTryingElement(By locator, int timeOut) {

		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut) {
			try {
				element = getElement(locator);
				break;
			} catch (org.openqa.selenium.NoSuchElementException e) {
				System.out.println("Element is not found in attempt : " + attempts + ":" + locator);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempts++;
		}
		if (element == null) {
			try {
				throw new Exception("ELEMENTNOTFOUNDEXCEPTION");
			} catch (Exception e) {
				System.out.println("Element is not found exception...tried for : " + timeOut + "with the interval of : "
						+ 500 + "milliseconds");
			}
		}
		return element;

	}

	public WebElement reTryingElement(By locator, int timeOut, long pollingTime) {
		WebElement element = null;
		int attempts = 0;

		while (attempts < timeOut) {
			try {
				element = getElement(locator);
				break;
			} catch (org.openqa.selenium.NoSuchElementException e) {
				System.out.println("Element is not found in attempt : " + attempts + ":" + locator);
				try {
					Thread.sleep(pollingTime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			attempts++;
		}
		if (element == null) {
			try {
				throw new Exception("ELEMENTNOTFOUNDEXCEPTION");
			} catch (Exception e) {
				System.out.println("Element is not found exception...tried for : " + timeOut + "with the interval of : "
						+ pollingTime + "milliseconds");
			}
		}
		return element;

	}
}
