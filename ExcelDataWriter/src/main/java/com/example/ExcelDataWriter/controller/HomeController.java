package com.example.ExcelDataWriter.controller;

import java.io.IOException;
import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.ExcelDataWriter.utilities.Excel;

@Controller
public class HomeController {

	@Autowired
	Excel excel;

	@RequestMapping("/home")
	public String home() {
		return "demo.html";
	}

	@PostMapping("/upload")
	public ResponseEntity<?> mapReapExcelDatatoDB(@RequestParam(value = "file") MultipartFile reapExcelDataFile, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		try {
			if(reapExcelDataFile.getOriginalFilename() != "") {
				excel.readExcel(reapExcelDataFile, response);
			}
			else {
				return ResponseEntity.noContent().build();
			}			
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		System.out.println("Upload success!");
		return ResponseEntity.ok("File uploaded successfully.");
	}

}
