package com.malwinas.employee.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

	private List<String> acceptedAttributes;
	
	public ValidationService(@Value("${malwinas.employee.acceptedAttributes}") String[] acceptedAttributes) {
		this.acceptedAttributes = Arrays.asList(acceptedAttributes);
	}
	
	public Boolean areParametersValid(String attribute, String value) {
		if (attribute == null)
			return true;
		
		if (acceptedAttributes.contains(attribute) && value != null)
			return true;
		
		return false;
	}
}
