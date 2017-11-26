package com.malwinas.employee.model.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.malwinas.employee.controller.object.DegreeObject;
import com.malwinas.employee.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

	@Query("SELECT new com.malwinas.employee.controller.object.DegreeObject(em.degree, count(em)) FROM Employee em GROUP BY em.degree")
	Collection<DegreeObject> selectDegreeAndCountGroupByDegree();
}
