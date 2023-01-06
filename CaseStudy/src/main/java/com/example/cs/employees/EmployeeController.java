package com.example.cs.employees;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class EmployeeController {
	
	private EmployeeServiceImp employeeServiceImp;
	private EmployeeService employeeService;
	private EmployeeRepository employeeRepository;
	
	public EmployeeController(EmployeeService employeeService, EmployeeServiceImp employeeServiceImp, EmployeeRepository employeeRepository) {
		super();
		this.employeeService = employeeService;
		this.employeeServiceImp = employeeServiceImp;
		this.employeeRepository = employeeRepository;
	}
	
	//handler method to handle list students and return mode and view
	@GetMapping("/employees")
	public String listEmployees(Model model) {
		model.addAttribute("employees", employeeService.getAllEmployees());
		return "employees";
	}
	
	@GetMapping("/employees/new")
	public String createEmployeeForm(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "createEmployee";
	}
	
	@PostMapping("/employees")
	public String saveEmployee(@ModelAttribute("employee") Employee employee, RedirectAttributes redirAttrs) {
		//Next lines and first condition checks if there is a duplicate entry within the database.
		int check = duplicateEmployee(employee.getFirstName(), employee.getLastName(), employee.getMiddleName(), employee.getBirthDate());
		if (check > 0) {
			redirAttrs.addFlashAttribute("duplicate", "  Employee already exists!");
			return "redirect:/employees/new";
		}else {
			employeeService.saveEmployee(employee);
			redirAttrs.addFlashAttribute("added", "  Employee added successfully!");
			return "redirect:/employees";
		}
	}
	
	//After I click the Update Button on students.html, it will go here, hence the "@GetMapping". Just like a flag that says "you will direct here".
	@GetMapping("/employees/edit/{id}")
	public String editEmployeeForm(@PathVariable int id, Model model) {
		model.addAttribute("employee", employeeService.getEmployeeById(id));
		//Then after that it will return the "editEmployee.html"
		return "editEmployee";
	}
	
	//Then finally after I submitted the form from editEmployee.html, it will execute this @PostMapping, hence the name "handler" as it handles the what action the form will do.
	@PostMapping("/employees/{id}")
	public String updateEmployee(@PathVariable int id, 
			@ModelAttribute ("employee") Employee employee,
			Model model, RedirectAttributes redirAttrs) {
		int check = duplicateEmployee(employee.getFirstName(), employee.getLastName(), employee.getMiddleName(), employee.getBirthDate());
		if (check == employee.getId() || check == 0) {
			//Get employee from database by ID
			Employee existingEmployee = employeeService.getEmployeeById(id);
			existingEmployee.setFirstName(employee.getFirstName());
			existingEmployee.setMiddleName(employee.getMiddleName());
			existingEmployee.setLastName(employee.getLastName());
			existingEmployee.setBirthDate(employee.getBirthDate());
			existingEmployee.setPosition(employee.getPosition());
			//save updated employee object
			employeeService.updateEmployee(existingEmployee);
			redirAttrs.addFlashAttribute("updated", "  Employee updated successfully!");
			return "redirect:/employees";
		} else {
			redirAttrs.addFlashAttribute("duplicate", "  Employee already exists!");
			return "redirect:/employees/edit/{id}";
		}
	}
	
	//handler method to handle delete student request
	//The reason that it was this simple is because I don't have to create a new page when deleting.
	//Just click the button and then let the operations do its job, and show you the database, that's it.
	@GetMapping("/employees/{id}")
	public String deleteEmployee(@PathVariable int id) {
		employeeService.deleteEmployeeById(id);
		return "redirect:/employees";
	}
	
	//Search Method
//	@GetMapping("/employeeSearch")
//	public String getEmployees(Model model, String keyword) {
//		if (keyword != null) {
//			model.addAttribute("employees", employeeServiceImp.findByKeyword(keyword));
//		} else {
//			model.addAttribute("employees", employeeService.getAllEmployees());
//		}
//		return "employees";
//	}
	@RequestMapping("/employees")
	public String viewEmployeeList(Model model) {
		model.addAttribute("employees", employeeService.getAllEmployees());
 		return "employees";
	}
	
	
	@PostMapping("/employees/search")
	public String searchEmployee(Model model, @ModelAttribute("filter") Employee filter) {
		 model.addAttribute("employees", employeeService.searchEmployee(filter.getFirstName(), filter.getLastName(), filter.getPosition()));
		 model.addAttribute("firstName", filter.getFirstName());
		 model.addAttribute("lastName", filter.getLastName());
		 model.addAttribute("position", filter.getPosition());
		 return "employees";
	}
	
	
	
	//Method for searching duplicate entries
	public Integer duplicateEmployee(String firstName, String lastName, String middleName, Date birthDate) {
		Integer duplicate = employeeRepository.searchDuplicate(firstName, lastName, middleName, birthDate);
		if(duplicate == null) {
			duplicate = 0;
		}
		return duplicate;
	}
}
