package com.example.cs.employees;

import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImp implements EmployeeService {

	private EmployeeRepository employeeRepository;
	private Employee employee;
	
	public EmployeeServiceImp(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public Employee getEmployeeById(int id) {
		return employeeRepository.findById(id).get();
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployeeById(int id) {
		employeeRepository.deleteById(id);
	}

	//Search Method
//	public List<Employee> findByKeyword(String keyword) {
//		return employeeRepository.findByKeyword(keyword);
//	}
	public List<Employee> listAll(String keyword){
			return employeeRepository.findAll();
		}
	@Override
	public List<Employee> searchEmployee(String firstName, String lastName, String position) {
		 if(firstName != null || lastName != null || position != null) {
		 return employeeRepository.searchEmployee( firstName, lastName, position);
		 }
		 return employeeRepository.findAll();
	}
}
