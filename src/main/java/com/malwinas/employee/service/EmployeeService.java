package com.malwinas.employee.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.malwinas.employee.controller.object.EmployeeObject;
import com.malwinas.employee.exception.EmployeeNotFoundException;
import com.malwinas.employee.exception.InvalidParameterException;
import com.malwinas.employee.model.Employee;
import com.malwinas.employee.model.repository.EmployeeRepository;
import com.malwinas.employee.search.EmployeeSpecification;

@Service
public class EmployeeService {
	
	private EmployeeRepository employeeRepository;
	private ValidationService validationService;
	
	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository, ValidationService validationService) {
		this.employeeRepository = employeeRepository;
		this.validationService = validationService;
	}
	
	@Transactional
	public Long insert(EmployeeObject employeeObject) {
		Employee employee = new Employee(employeeObject.getFirstName(), employeeObject.getLastName(), 
				employeeObject.getDegree(), employeeObject.getEmail());
		
		employee = employeeRepository.save(employee);
		return employee.getId();
	}
	
	@Transactional
	public void delete(Long id) {
		Employee employee = employeeRepository.findOne(id);
		
		if (employee == null)
			throw new EmployeeNotFoundException(id);
		
		employeeRepository.delete(employee);
	}
	
	@Transactional
	public Collection<EmployeeObject> get(String attribute, String value) {
		
		if (!validationService.areParametersValid(attribute, value))
			throw new InvalidParameterException(attribute, value);
		
		return attribute != null ? getFiltred(attribute, value) : getAll();
	}
	
	private Collection<EmployeeObject> getAll() {
		List<EmployeeObject> employeeObjects = new ArrayList<>();
		
		List<Employee> employees = employeeRepository.findAll();
		
		for (Employee employee : employees)
			employeeObjects.add(new EmployeeObject(employee));
		
		return employeeObjects;
	}
	
	private Collection<EmployeeObject> getFiltred(String attribute, String value) {
		List<EmployeeObject> employeeObjects = new ArrayList<>();
		
		EmployeeSpecification specification = new EmployeeSpecification(attribute, value);
		List<Employee> employees = employeeRepository.findAll(specification);
		
		for (Employee employee : employees)
			employeeObjects.add(new EmployeeObject(employee));
		
		return employeeObjects;
	}
}
