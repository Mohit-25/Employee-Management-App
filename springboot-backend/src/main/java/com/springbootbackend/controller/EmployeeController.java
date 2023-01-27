package com.springbootbackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbootbackend.exception.ResourceNotFoundException;
import com.springbootbackend.model.Employee;
import com.springbootbackend.repository.EmployeeRepository;

@CrossOrigin//(origins ="http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employee
	@GetMapping("/employees")
	public List<Employee> getAllEmployees()
	{
		return employeeRepository.findAll();
	}
	
	//to add employee
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee)
	{
		return employeeRepository.save(employee);
	}
	
	//get employee by id
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id)
	{
		
		Employee employee=employeeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee does not exist with id:"+id));
		return ResponseEntity.ok(employee);
		
	}
	
	//update employee
	@PutMapping("/employees/{id}")
		public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id, @RequestBody  Employee employeeDetails)
		{
			Employee employee=employeeRepository.findById(id)
					.orElseThrow(()-> new ResourceNotFoundException("Employee does not exist with id:"+id));
		   
			employee.setFirstName(employeeDetails.getFirstName());
		    employee.setLastName(employeeDetails.getLastName());
		    employee.setEmailId(employeeDetails.getEmailId());
		    
		    Employee updatedEmployee =employeeRepository.persist(employee);
	        return ResponseEntity.ok(updatedEmployee);	
		}
	
	//delete employee
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id)
	{
		Employee employee=employeeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee does not exist with id:"+id));
		
		employeeRepository.delete(employee);
		Map<String, Boolean> response= new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}


