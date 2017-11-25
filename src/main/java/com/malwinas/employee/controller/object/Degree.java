package com.malwinas.employee.controller.object;

public class Degree {
	
	private String degree;
	private long count;
	
	public Degree(String degree, long count) {
		this.degree = degree;
		this.count = count;
	}
	
	public String getDegree() {
		return degree;
	}

	public long getCount() {
		return count;
	}
}
