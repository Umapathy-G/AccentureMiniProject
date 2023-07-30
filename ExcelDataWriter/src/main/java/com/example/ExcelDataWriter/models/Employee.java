package com.example.ExcelDataWriter.models;

public class Employee {
	private String empid;
	private String email;
		
	public Employee(String empid, String email) {
		super();
		this.empid = empid;
		this.email = email;
	}
	
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Employee [empid=" + empid + ", email=" + email + "]";
	}		
	
}
