package com.malwinas.employee.exception;

public class InvalidParameterException extends RuntimeException {

	private static final long serialVersionUID = -3524658519513303180L;
	private String attribute;
	private String value;
	
	public InvalidParameterException(String attribute, String value) {
		this.attribute = attribute;
		this.value = value;
	}

	@Override
	public String getMessage() {
		return "Invalid parameter exception, attribute: " + attribute + ", value: " + value;
	}
}
