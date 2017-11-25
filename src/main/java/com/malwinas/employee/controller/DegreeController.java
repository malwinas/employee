package com.malwinas.employee.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.malwinas.employee.controller.object.Degree;
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
	public ResponseEntity<Collection<Degree>> getDegrees() {
		Collection<Degree> degrees = degreeService.getDegrees();
		return new ResponseEntity<Collection<Degree>>(degrees, HttpStatus.OK);
	}
}
