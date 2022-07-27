package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	
	private static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/OpenCartAppTestData.xlsx";
	private static Workbook book;
	private static Sheet sheet;
	
	public static Object[][] getTestData(String sheetName) {
		
		Object data[][] = null;
		FileInputStream ip=null;
		
		String excelName = System.getProperty("excel");
		
		if(excelName==null) {
				try {
					ip = new FileInputStream(TEST_DATA_SHEET_PATH);
					book = WorkbookFactory.create(ip);
					sheet = book.getSheet(sheetName);
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				} catch (InvalidFormatException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			

		}else {
			System.out.println("Running on environment: "+excelName);
			try {
				switch(excelName.toLowerCase()) {
				case "qa":
					System.out.println("******************************From QA excel***************************");
					ip = new FileInputStream("./src/test/resources/testdata/QAOpenCartAppTestData.xlsx");
					book = WorkbookFactory.create(ip);
					sheet = book.getSheet(sheetName);
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/testdata/DEVOpenCartAppTestData.xlsx");
					book = WorkbookFactory.create(ip);
					sheet = book.getSheet(sheetName);
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/testdata/STAGEOpenCartAppTestData.xlsx");
					book = WorkbookFactory.create(ip);
					sheet = book.getSheet(sheetName);
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/testdata/UATOpenCartAppTestData.xlsx");
					book = WorkbookFactory.create(ip);
					sheet = book.getSheet(sheetName);
					break;
				default:
					System.out.println("Please pass the right environment for excel data...");
					book = WorkbookFactory.create(ip);
					sheet = book.getSheet(sheetName);
					break;
				}
				
			}
			catch(FileNotFoundException e) {
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for(int i=0;i<sheet.getLastRowNum();i++) {
			for(int j=0;j<sheet.getRow(0).getLastCellNum();j++) {
				data[i][j] = sheet.getRow(i+1).getCell(j).toString();
			}
		}
		
		return data;
		
		
	}

}
