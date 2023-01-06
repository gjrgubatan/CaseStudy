package com.example.cs.employees;

import java.util.List;

public interface EmployeeService {
	List<Employee> getAllEmployees();	
	Employee saveEmployee(Employee employee);
	Employee getEmployeeById(int id);
	Employee updateEmployee(Employee employee);
	void deleteEmployeeById(int id);
	List<Employee> listAll(String keyword);
	List<Employee> searchEmployee(String firstName, String lastName, String position);
}
