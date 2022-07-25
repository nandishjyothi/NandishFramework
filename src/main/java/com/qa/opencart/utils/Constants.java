package com.qa.opencart.utils;

import java.util.ArrayList;
import java.util.List;

public class Constants {
	
	public static final String LOGIN_PAGE_TITLE = "OpenCart - Account Login1";
	public static final String LOGIN_PAGE_URL_FRACTION = "route=account/login";
	public static final int DEFAULT_TIME_OUT = 5;
	public static final String ACCOUNTS_PAGE_TITLE = "OpenCart - Your Account";
	public static final String ACCOUNTS_PAGE_HEADER = "Account1";
	public static final int MACBOOK_IMAGE_COUNT = 5;
	public static final int IPHONE_IMAGE_COUNT = 6;
	public static final int CANON_IMAGE_COUNT = 3;
	
	public static final String LOGIN_ERROR_MESSG = "No match for E-Mail and/or Password.";
	public static final String REGISTER_SUCCESS_MESSG = "Your Account Has Been Created!";
	public static final String REGISTER_SHEET_NAME = "registration";
	
	public static List<String> getExpectedAccSecList(){
		List<String> expSecList = new ArrayList<String>();
		expSecList.add("Account Details");
		expSecList.add("Change Password");
		expSecList.add("Payment Methods");
		expSecList.add("Showcase");
		expSecList.add("Partner Up");
		expSecList.add("Your Orders");
		expSecList.add("Your Downloads");
		expSecList.add("Your Stores");
		expSecList.add("Rate your Downloads");
		expSecList.add("Manage your Extensions");
		expSecList.add("Apply to become a seller");
		return expSecList;
		
	}

}
