import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellAddress;

public class ExcelSheetMaker {

	CreationHelper helper;
	String filepath = "dab" ;
	InputStream inp;
	
	public static void addInformationArray(String season, ArrayList<String> titles, String date, ArrayList<String> members) throws FileNotFoundException, IOException {
		
		//Imports the workbook saved as MAL_Members.xlsx
		Workbook wb = WorkbookFactory.create(new FileInputStream("MAL_Members.xlsx"));
		Boolean exists = false;
		
		//Checks if the worksheet for the season already exists. Starts by checking if there are sheets in the workbook. If there are, for each sheet check if the name of the sheet
		//contains the season. If it does, set exists = true.
		
		if(wb.getNumberOfSheets() != 0) {
			for(Sheet s : wb) {
				//System.out.println(s.getSheetName());
				if(s.getSheetName().trim().contains(season)) {
					//System.out.println("found");
					exists = true;
				}
			}
		}
		
		//If it doesn't exists, it creates this worksheet.
		if(!exists) wb.createSheet(season); 
		
		if(wb.getSheet(season).getRow(0) == null) {
			wb.getSheet(season).createRow(0);
		}
		
		if(wb.getSheet(season).getRow(0).getCell(0) == null) {
			wb.getSheet(season).getRow(0).createCell(0);
		}
		
		//Start of the mass-write handling for loop. Performs the bottom for each title in the titles array.
		
		for(int i = 0; i < titles.size(); i++) {
			
			int column = 0;
			int row = 0;
			int numrows = 0;
			int numcolumns = 0;
			
			//Checks if the anime title exists in the worksheet. If it has, sets the row number to the row containing the title;
			exists = false;
			
			System.out.println("Updating data for title: " + titles.get(i));
			if(wb.getSheet(season).getRow(0).getCell(0) != null)
			for(Row r : wb.getSheet(season)) {
				numrows += 1;
				if(r.getCell(0).getStringCellValue().equals(titles.get(i))) {
					//System.out.println("Found title: " + titles.get(i) + " at row " + r.getRowNum());
					exists = true;
					row = r.getRowNum();
				}
			}
			
			//If this title hasn't been added yet, creates that row and adds the title.
			if(!exists) {
				System.out.println("New title added: " + titles.get(i));
				wb.getSheet(season).createRow(numrows+1);
				wb.getSheet(season).getRow(numrows+1).createCell(0);
				wb.getSheet(season).getRow(numrows+1).getCell(0).setCellValue(titles.get(i));
				row = numrows+1;
			}
			
			//Checks if the date has been added to the excel sheet.
			exists = false;
			if(wb.getSheet(season).getRow(0).getCell(0) != null)
			for(Cell c : wb.getSheet(season).getRow(0)) {
				numcolumns += 1;
				if(c.getStringCellValue().contains(date)) {
					//System.out.println("Found date");
					exists = true;
					column = c.getColumnIndex();
				}
			}
			
			//If this date has not been added, adds the date.
			if(!exists) {
				wb.getSheet(season).getRow(0).createCell(numcolumns+1);
				wb.getSheet(season).getRow(0).getCell(numcolumns+1).setCellValue(date);
				column = numcolumns+1;
			}
			
			//Checks if this members value has already been recorded. If not, records the number.
			exists = false;
			if(wb.getSheet(season).getRow(row).getCell(column) != null)
			if(wb.getSheet(season).getRow(row).getCell(column).getStringCellValue() == members.get(i)) {
				exists = true;
			}
			
			if(!exists) {
				wb.getSheet(season).getRow(row).createCell(column);
				wb.getSheet(season).getRow(row).getCell(column).setCellValue(members.get(i));
			}
		}
		
		wb.write(new FileOutputStream("MAL_Members.xlsx"));
	}
	
	public static void addInformation(String season, String title, String date, String members) throws IOException {
		
		int column = 0;
		int row = 0;
		int numrows = 0;
		int numcolumns = 0;
		
		//Imports the workbook saved as MAL_Members.xlsx
		Workbook wb = WorkbookFactory.create(new FileInputStream("MAL_Members.xlsx"));
		Boolean exists = false;
		
		//Checks if the worksheet for the season already exists.
		if(wb.getNumberOfSheets() != 0) {
			for(Sheet s : wb) {
				//System.out.println(s.getSheetName());
				if(s.getSheetName().trim().contains(season)) {
					//System.out.println("found");
					exists = true;
				}
			}
		}
		
		//If it doesn't exists, it creates this worksheet.
		if(!exists) wb.createSheet(season); 
		
		if(wb.getSheet(season).getRow(0) == null) {
			wb.getSheet(season).createRow(0);
		}
		
		if(wb.getSheet(season).getRow(0).getCell(0) == null) {
			wb.getSheet(season).getRow(0).createCell(0);
		}
		
		//Checks if the anime title exists in the worksheet. If it has, sets the row number to the row containing the title;
		exists = false;
		if(wb.getSheet(season).getRow(0).getCell(0) != null)
		for(Row r : wb.getSheet(season)) {
			numrows += 1;
			if(r.getCell(0).getStringCellValue().equals(title)) {
				System.out.println("Found title: " + title + " at row " + r.getRowNum());
				exists = true;
				row = r.getRowNum();
			}
		}
		
		//If this title hasn't been added yet, creates that row and adds the title.
		if(!exists) {
			wb.getSheet(season).createRow(numrows+1);
			wb.getSheet(season).getRow(numrows+1).createCell(0);
			wb.getSheet(season).getRow(numrows+1).getCell(0).setCellValue(title);
			row = numrows+1;
		}
		
		//Checks if the date has been added to the excel sheet.
		exists = false;
		if(wb.getSheet(season).getRow(0).getCell(0) != null)
		for(Cell c : wb.getSheet(season).getRow(0)) {
			numcolumns += 1;
			if(c.getStringCellValue().contains(date)) {
				System.out.println("Found date");
				exists = true;
				column = c.getColumnIndex();
			}
		}
		
		//If this date has not been added, adds the date.
		if(!exists) {
			wb.getSheet(season).getRow(0).createCell(numcolumns+1);
			wb.getSheet(season).getRow(0).getCell(numcolumns+1).setCellValue(date);
			column = numcolumns+1;
		}
		
		//Checks if this members value has already been recorded. If not, records the number.
		exists = false;
		if(wb.getSheet(season).getRow(row).getCell(column) != null)
		if(wb.getSheet(season).getRow(row).getCell(column).getStringCellValue() == members) {
			exists = true;
		}
		
		if(!exists) {
			wb.getSheet(season).getRow(row).createCell(column);
			wb.getSheet(season).getRow(row).getCell(column).setCellValue(members);
		}
		
		wb.write(new FileOutputStream("MAL_Members.xlsx"));
		
	}
}


