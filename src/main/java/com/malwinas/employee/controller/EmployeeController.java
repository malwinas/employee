package com.malwinas.employee.controller;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.malwinas.employee.controller.object.EmployeeObject;
import com.malwinas.employee.exception.EmployeeNotFoundException;
import com.malwinas.employee.exception.InvalidAttributeException;
import com.malwinas.employee.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	private final Logger logger = Logger.getLogger(EmployeeController.class.getName());
	
	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ResponseEntity<Long> insert(@Valid @RequestBody EmployeeObject employeeObject) {
		Long employeeId = employeeService.insert(employeeObject);
		return new ResponseEntity<Long>(employeeId, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseEntity<Collection<EmployeeObject>> get(
			@RequestParam(required = false) String attribute, 
			@RequestParam(required = false) String value) {
		Collection<EmployeeObject> employeeObjects = employeeService.get(attribute, value);
		return new ResponseEntity<Collection<EmployeeObject>>(employeeObjects, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable Long id) {
		employeeService.delete(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
		logger.log(Level.WARNING, ex.getMessage());
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidAttributeException.class)
	public ResponseEntity<String> handleInvalidAttributeException(InvalidAttributeException ex) {
		logger.log(Level.WARNING, ex.getMessage());
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
