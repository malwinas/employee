package com.malwinas.employee.controller.object;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.malwinas.employee.model.Employee;

public class EmployeeObject {
	
	@NotNull
	@Size(min = 1, max = 255)
	private String firstName;
	
	@NotNull
	@Size(min = 1, max = 255)
	private String lastName;
	
	@NotNull
	@Size(min = 1, max = 255)
	private String degree;
	
	@NotNull
	@Size(min = 1, max = 255)
	private String email;
	
	public EmployeeObject() {
		
	}
	
	public EmployeeObject(Employee employee) {
		this.firstName = employee.getFirstName();
		this.lastName = employee.getLastName();
		this.degree = employee.getDegree();
		this.email = employee.getEmail();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
