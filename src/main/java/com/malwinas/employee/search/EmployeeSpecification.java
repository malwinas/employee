package com.malwinas.employee.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.malwinas.employee.model.Employee;

public class EmployeeSpecification implements Specification<Employee>{
	
	private String attribute;
	private String value;
	
	public EmployeeSpecification(String attribute, String value) {
		this.attribute = attribute;
		this.value = value;
	}

	@Override
	public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return cb.like(root.<String> get(attribute), "%" + value + "%");
	}
}
