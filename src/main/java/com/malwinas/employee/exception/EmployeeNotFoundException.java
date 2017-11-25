package com.malwinas.employee.exception;

public class EmployeeNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -2547828161068011528L;
	private Long id;
	
	public EmployeeNotFoundException(Long id) {
		this.id = id;
	}
	
	@Override
	public String getMessage() {
		return "Employee not found: " + id;
	}
}
