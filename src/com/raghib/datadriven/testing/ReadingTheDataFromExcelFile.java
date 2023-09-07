package com.raghib.datadriven.testing;

/**
 * Reference:-
 * https://www.youtube.com/watch?v=ipjl49Hgsg8&list=PLUDwpEzHYYLsN1kpIjOyYW6j_GLgOyA07&index=2
 * https://www.youtube.com/watch?v=1nP9UlwzpgU&list=PLUDwpEzHYYLsN1kpIjOyYW6j_GLgOyA07&index=11
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.raghib.selenium.BaseClass;

/*
 * JXL library doesnot support .csv and .xslx formats, which is the format used by Excel-2010. 
 * Hence, use Excel 97-2003 which is .xls foramatted and is supported by JXL library. 
 * or else if you want to use excel-2010, use APACHE POI(XSSFWorkbooks) instead of JXL. 
 * For using .csv format, google for CSVReader libraries.
 */

//Here we are just reading the data from excel sheet and printing into console.

public class ReadingTheDataFromExcelFile extends BaseClass {

	XSSFRow row;
	String excelFilePath = System.getProperty("user.dir");
	FileInputStream fis = null;

	public static void main(String[] args) throws IOException {
		ReadingTheDataFromExcelFile dataDrivenProgOneobj = new ReadingTheDataFromExcelFile();
		dataDrivenProgOneobj.ReadInputDataFromExcel();
	}

	@SuppressWarnings("incomplete-switch")
	public void ReadInputDataFromExcel() {
		try {
			if (fis == null) {
				fis = new FileInputStream(excelFilePath + "\\ExcelFile\\AutomationTestingInput.xlsx");
			}
			
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			int rowCounts = sheet.getLastRowNum();
			int columnCounts = sheet.getRow(0).getLastCellNum();
			
			// Using For Loop
			for (int r = 0; r <= rowCounts; r++) {
				XSSFRow row = sheet.getRow(r);

				for (int c = 0; c < columnCounts; c++) {
					XSSFCell cell = row.getCell(c);

					switch (cell.getCellType()) {
					case NUMERIC:
						System.out.print(cell.getNumericCellValue() + " \t\t ");
						break;

					case STRING:
						System.out.print(cell.getStringCellValue() + " \t\t ");
						break;

					case BOOLEAN:
						System.out.print(cell.getBooleanCellValue() + " \t\t ");
						break;
					} // Switch Closed.
				} // Inner For Loop Closed.
				System.out.println();
			} // Outter For Loop Closed.
			
			//Using Iterator
			@SuppressWarnings("rawtypes")
			Iterator itr = sheet.iterator();
			while(itr.hasNext()) {
				XSSFRow row = (XSSFRow) itr.next(); 
				@SuppressWarnings("rawtypes")
				Iterator cellIterator = row.cellIterator();
				while(cellIterator.hasNext()) {
					XSSFCell cell = (XSSFCell) cellIterator.next();
					switch (cell.getCellType()) {
					case NUMERIC:
						System.out.print(cell.getNumericCellValue() + " \t\t ");
						break;

					case STRING:
						System.out.print(cell.getStringCellValue() + " \t\t ");
						break;
						
					case BOOLEAN:
						System.out.print(cell.getBooleanCellValue() + " \t\t ");
						break;
					} //Switch Closed.
				} //Inner While Loop Closed.
			} //Outter While Loop Closed.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	//ReadInputDataFromExcel() Method Closed.
}