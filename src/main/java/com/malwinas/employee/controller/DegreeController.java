package com.malwinas.employee.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.malwinas.employee.controller.object.DegreeObject;
import com.malwinas.employee.service.DegreeService;

@Controller
@RequestMapping("/degree")
public class DegreeController {
	
	private DegreeService degreeService;
	
	@Autowired
	public DegreeController(DegreeService degreeService) {
		this.degreeService = degreeService;
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseEntity<Collection<DegreeObject>> getDegrees() {
		Collection<DegreeObject> degrees = degreeService.getDegrees();
		return new ResponseEntity<Collection<DegreeObject>>(degrees, HttpStatus.OK);
	}
}
