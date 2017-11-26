package com.malwinas.employee;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.malwinas.employee.controller.DegreeController;
import com.malwinas.employee.controller.object.DegreeObject;
import com.malwinas.employee.model.repository.EmployeeRepository;
import com.malwinas.employee.service.DegreeService;

@RunWith(MockitoJUnitRunner.class)
public class DegreeControllerTest {
	
	@Mock
	private EmployeeRepository employeeRepository;
	
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	@Before
	public void init() {
		objectMapper = new ObjectMapper();
		
		DegreeService degreeService = new DegreeService(employeeRepository);
		mockMvc = MockMvcBuilders
					.standaloneSetup(new DegreeController(degreeService))
					.build();
	}
	
	@Test
	public void getDegreesTest() throws Exception {
		Collection<DegreeObject> degreeResults = getDegreeResults();
		when(employeeRepository.selectDegreeAndCountGroupByDegree()).thenReturn(degreeResults);
		
		String getDegreesResult = mockMvc.perform(get("/degree/get"))
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();
		
		CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, DegreeObject.class);
		List<DegreeObject> results = objectMapper.readValue(getDegreesResult, type);
		
		verify(employeeRepository).selectDegreeAndCountGroupByDegree();
		
		Assert.assertNotNull(results);
		Assert.assertEquals(degreeResults.size(), results.size());
	}
	
	private Collection<DegreeObject> getDegreeResults() {
		List<DegreeObject> degreeResults = new ArrayList<>();
		
		DegreeObject managerDegree = new DegreeObject("Manager", 1);
		DegreeObject salesmanDegree = new DegreeObject("Salesman", 4);
		DegreeObject driverDegree = new DegreeObject("Driver", 2);
		
		degreeResults.add(managerDegree);
		degreeResults.add(salesmanDegree);
		degreeResults.add(driverDegree);
		
		return degreeResults;
	}
}
