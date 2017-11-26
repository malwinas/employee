package com.malwinas.employee.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.malwinas.employee.controller.object.DegreeObject;
import com.malwinas.employee.model.repository.EmployeeRepository;

@Service
public class DegreeService {

	private EmployeeRepository employeeRepository;
	
	@Autowired
	public DegreeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}
	
	public Collection<DegreeObject> getDegrees() {
		return employeeRepository.selectDegreeAndCountGroupByDegree();
	}
}
