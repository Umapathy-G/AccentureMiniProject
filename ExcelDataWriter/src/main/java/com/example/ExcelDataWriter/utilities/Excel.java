package com.example.ExcelDataWriter.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.ExcelDataWriter.models.Employee;

@Component
public class Excel {

	static final String FILE_SAVE_LOCATION = "C:\\reports\\";
	static final String FILE_TEMPLATE = "D:\\ExcelTemplate\\Update-Workday-Mismatch template 1.xlsx";
	static final String FILE_NAME = "EmplyoeeDetails.xlsx";
	static final String SYSTEM_ID = "WD-EMPLID";

	public void readExcel(MultipartFile file, HttpServletResponse response) {
		try {
//			FileInputStream file = new FileInputStream(new File("gfgcontribute.xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			int lastrow = sheet.getLastRowNum();
			List<Employee> ls = new ArrayList<>();

			for (int i = 1; i <= lastrow; i++) {
				Row row = sheet.getRow(i);
//				int id = (int) row.getCell(0).getNumericCellValue();
				String empid = row.getCell(2).getStringCellValue().trim();
				String email = row.getCell(12).getStringCellValue().trim();

				ls.add(new Employee(empid, email));
			}

			for (int i = 0; i < ls.size(); i++) {

				String email = ls.get(i).getEmail();
				String[] arr_email = email.split("@", 2);

				if (Character.toString(ls.get(i).getEmpid().charAt(0)).equalsIgnoreCase("N")) {
					ls.remove(i);
					i--;
				}
				if (arr_email.length == 1 || !arr_email[1].equalsIgnoreCase("accenture.com")) {
					ls.remove(i);
					i--;
				}

//				System.out.println(ls.get(i));
			}

			writeDataToExcel(ls, response);
			System.out.println("Data inserted to excel file");
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeDataToExcel(List<Employee> empList, HttpServletResponse response)
			throws FileNotFoundException, IOException, InvalidFormatException {

		FileInputStream inputStream = new FileInputStream(FILE_TEMPLATE);
		Workbook workbook = WorkbookFactory.create(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		XSSFSheet sheet = workbook.createSheet("Report");
//		Row headerRow = sheet.createRow(0);
//		headerRow.createCell(0).setCellValue("User ID");
//		headerRow.createCell(1).setCellValue("First name");
//		headerRow.createCell(2).setCellValue("Last name");
//		headerRow.createCell(3).setCellValue("Email");

		int rowCount = 4;
		int count = 1;
		for (Employee emp : empList) {
			Row row = sheet.createRow(++rowCount);
			row.createCell(1).setCellValue(count++);
			row.createCell(2).setCellValue(SYSTEM_ID);
			row.createCell(3).setCellValue(emp.getEmpid());
			row.createCell(7).setCellValue(emp.getEmail());
		}

		response.setHeader("Content-Disposition", "attachment; filename=" + FILE_NAME);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "inline;filename=" + FILE_NAME);

		FileOutputStream fileOut0 = new FileOutputStream(FILE_NAME);
		workbook.write(fileOut0);
		workbook.write(response.getOutputStream()); // Write workbook to response.
		response.getOutputStream().flush();
		fileOut0.close();
		response.getOutputStream().close();
	}

}
