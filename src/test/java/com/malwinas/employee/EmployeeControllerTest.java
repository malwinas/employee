package com.malwinas.employee;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.malwinas.employee.controller.EmployeeController;
import com.malwinas.employee.controller.object.EmployeeObject;
import com.malwinas.employee.model.Employee;
import com.malwinas.employee.model.repository.EmployeeRepository;
import com.malwinas.employee.search.EmployeeSpecification;
import com.malwinas.employee.service.EmployeeService;
import com.malwinas.employee.service.ValidationService;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

	@Mock
	private EmployeeRepository employeeRepository;
	
	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	
	@Before
	public void init() {
		objectMapper = new ObjectMapper();
		
		String[] acceptedValues = {"firstName", "lastName", "email"};
		ValidationService validationService = new ValidationService(acceptedValues);
		EmployeeService employeeService = new EmployeeService(employeeRepository, validationService);
		mockMvc = MockMvcBuilders
					.standaloneSetup(new EmployeeController(employeeService))
					.build();
	}
	
	@Test
	public void insertEmployeeTest() throws Exception {
		EmployeeObject parameter = getParameter();
		Employee employee = getInsertResult();
		
		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
		
		String insertResult = mockMvc.perform(post("/employee/insert")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(parameter)))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		Long employeeId = Long.valueOf(insertResult);
		
		verify(employeeRepository).save(any(Employee.class));
		Assert.assertEquals(employee.getId().longValue(), employeeId.longValue());
	}
	
	@Test
	public void getAllEmployeesTest() throws Exception {
		List<Employee> employeeResult = getAllResults();
		
		when(employeeRepository.findAll()).thenReturn(employeeResult);
		
		String getAllResult = mockMvc.perform(get("/employee/get"))
			.andExpect(status().isOk())
			.andReturn().getResponse().getContentAsString();
		
		CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, EmployeeObject.class);
		List<EmployeeObject> result = objectMapper.readValue(getAllResult, type);
		
		verify(employeeRepository).findAll();
		
		Assert.assertNotNull(result);
		Assert.assertEquals(employeeResult.size(), result.size());
	}
	
	@Test
	public void getFiltredEmployeesTest() throws Exception {
		List<Employee> employeeResults = getAllResults();
		
		when(employeeRepository.findAll(any(EmployeeSpecification.class))).thenReturn(employeeResults);
		
		String getFiltredResult = mockMvc.perform(get("/employee/get")
					.param("attribute", "lastName")
					.param("value", "Kowal"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		
		CollectionType type = objectMapper.getTypeFactory().constructCollectionType(List.class, EmployeeObject.class);
		List<EmployeeObject> results = objectMapper.readValue(getFiltredResult, type);
		
		verify(employeeRepository).findAll(any(EmployeeSpecification.class));
		
		Assert.assertNotNull(results);
		Assert.assertEquals(employeeResults.size(), results.size());
	}
	
	@Test
	public void deleteEmployeeTest() throws Exception {
		Long employeeId = Long.valueOf(1);
		
		when(employeeRepository.findOne(employeeId)).thenReturn(getInsertResult());
		
		mockMvc.perform(delete("/employee/delete/{id}", employeeId))
			.andExpect(status().isOk());
		
		verify(employeeRepository).findOne(employeeId);
	}
	
	private EmployeeObject getParameter() {
		EmployeeObject employeeParameter = new EmployeeObject();
		employeeParameter.setFirstName("Jan");
		employeeParameter.setLastName("Nowak");
		employeeParameter.setDegree("Manager");
		employeeParameter.setEmail("jan.nowak@gmail.com");
		
		return employeeParameter;
	}
	
	private List<Employee> getAllResults() {
		List<Employee> employees = new ArrayList<>();
		
		Employee firstEmployee = new Employee();
		firstEmployee.setId(Long.valueOf(1));
		firstEmployee.setFirstName("Adam");
		firstEmployee.setLastName("Kowalski");
		firstEmployee.setDegree("Salesman");
		firstEmployee.setEmail("adam.kowalski@gmail.com");
		employees.add(firstEmployee);
		
		Employee secondEmployee = new Employee();
		secondEmployee.setId(Long.valueOf(2));
		secondEmployee.setFirstName("Krzysztof");
		secondEmployee.setLastName("Kowalczyk");
		secondEmployee.setDegree("Salesman");
		secondEmployee.setEmail("krzysztof.kowalczyk@gmail.com");
		employees.add(secondEmployee);
		
		return employees;
	}
	
	private Employee getInsertResult() {
		Employee employee = new Employee();
		employee.setId(Long.valueOf(1));
		return employee;
	}
}
