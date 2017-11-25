package com.malwinas.employee.exception;

public class InvalidAttributeException extends RuntimeException {

	private static final long serialVersionUID = -3524658519513303180L;
	private String attribute;
	
	public InvalidAttributeException(String attribute) {
		this.attribute = attribute;
	}

	@Override
	public String getMessage() {
		return "Invalid attribute exception: " + attribute;
	}
}
