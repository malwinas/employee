package com.malwinas.employee.controller.object;

public class DegreeObject {
	
	private String degree;
	private long count;
	
	public DegreeObject() {

	}
	
	public DegreeObject(String degree, long count) {
		this.degree = degree;
		this.count = count;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
}
