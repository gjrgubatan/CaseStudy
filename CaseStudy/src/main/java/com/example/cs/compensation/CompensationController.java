package com.example.cs.compensation;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.cs.employees.EmployeeRepository;
import com.example.cs.employees.EmployeeService;
import com.example.cs.employees.EmployeeServiceImp;

@Controller
public class CompensationController {
	
	@Autowired
	private CompensationService compService;
	private EmployeeService employeeService;
	private CompensationRepository compRepo;
	
	
	public CompensationController(EmployeeService employeeService, CompensationService compService, CompensationRepository compRepo) {
		super();
		this.employeeService = employeeService;
		this.compService = compService;
		this.compRepo = compRepo;
	}
	

	@GetMapping("/addCompensation/{id}")
	public String addCompensationForm(Model model, @PathVariable(name="id") int id ) {
		Compensation comp = new Compensation();
		comp.setId_employee(id);
		model.addAttribute("compensation", comp);
		return "addCompensation";
	}
	
	@PostMapping("/saveCompensation")
	public String saveCompensation(@ModelAttribute("compensation") Compensation compensation, Model model, RedirectAttributes attribute) {		
		String type = compensation.getType();
		String desc = compensation.getDescription();
		
		Long check = compRepo.checkEmployeeSalary(compensation.getDatec(), compensation.getId_employee());
		
		if (check != null && type.equals("Salary")) {
			attribute.addFlashAttribute("duplicateSalary", "  Employee Salary already exists for this Month!");
			return "redirect:/employees";
		} else {
			if(type.equals("Salary")) {
				if(compService.isValidDateSalary(compensation)) {
					compService.save(compensation);
					attribute.addFlashAttribute("success", "Successfully saved");
					return "redirect:/employees";
				}
				attribute.addFlashAttribute("error", "Only one salary entry per employee per month can be added");
				return "redirect:/employees";
			}
		}
		if(!type.equals("Salary") && desc.isEmpty()) {
			attribute.addFlashAttribute("error", "The description is required");
			return "redirect:/employees";
		} else {
			if(compService.validateTypeAndAmount(compensation)) {
				compService.save(compensation);
				attribute.addFlashAttribute("success", "Successfully saved");
				return "redirect:/employees";
			}
			else {
				attribute.addFlashAttribute("error", "Amount must be different from zero or greater than zero");
				return "redirect:/employees";
			}
		}
	}
	
	@GetMapping("/compensation/{id}")
	public String viewCompensation(@PathVariable int id, Model model, RedirectAttributes attribute) {
		List<Compensation> compList = compService.findCompensationById(id); 
		
		if(compList.isEmpty()) {
			attribute.addFlashAttribute("warning", "Employee doesn't have compensations");
			return "redirect:/employees";
		}
		model.addAttribute("employee", employeeService.getEmployeeById(id));
		model.addAttribute("compList", compList);
		return "compensationHistory";
	}
	
	@GetMapping("/compensationHistory/{id}/range")
	public String viewCompensationHistory(Model model, @PathVariable int id, String startD, String endD, RedirectAttributes attribute) throws ParseException {
		
		//If the variables are empty there is no date range
		if(startD.isEmpty() && endD.isEmpty()) {
			attribute.addFlashAttribute("error", "To filter by date you must enter start date and end date");
			return "redirect:/employees";
		}
		List<Compensation> compList = compService.findCompensationByDateRange(startD, endD, id); //get all compensations
		if(compList == null) {//If the list returns null is because end date occurs before start date
			attribute.addFlashAttribute("error1", "End date that occurs before start date");
			return "redirect:/employees";
		}
		if(compList.isEmpty()) {//if the list is empty means that the employee doesn't have compensation in this date range
			attribute.addFlashAttribute("warning1", "Employee doesn't have compensations in this range");
			return "redirect:/employees";
		}
		model.addAttribute("employee", employeeService.getEmployeeById(id));
		model.addAttribute("compList", compList);
		return "compensationHistory";
	}
	
	@GetMapping("/compensationHistory/{id}/details/{month}/{year}")
	public String viewCompensationDetails(@PathVariable int id, @PathVariable String month, @PathVariable int year, Model model) {
		List <Compensation> compList = compService.findCompensationByMonth(id, month, year);
		model.addAttribute("employee", employeeService.getEmployeeById(id));
		model.addAttribute("compList", compList);
		return "compensationDetails";
	}
	
	@RequestMapping("/compensationEdit/{id}")
	public String formEditCompensation(@PathVariable(name = "id") int id, Model model) {
		Compensation compensation = compService.getInfoCompById(id);
		model.addAttribute("compensation", compensation);
		return "editCompensation";
	}
	
	//Edit compensation
	@PostMapping(value = "/updateCompensation/{id}")
	public String updateCompensation(@ModelAttribute("compensation") Compensation compensation, BindingResult result, RedirectAttributes attribute) {
		String type = compensation.getType();
		String desc = compensation.getDescription();
		
		if(!type.equals("Salary") && desc.isEmpty()) {//If compensation isn't salary, description is required
			attribute.addFlashAttribute("error", "The description is required");
			return "redirect:/employees";
		}else {
			Compensation actualComp = compService.getInfoCompById(compensation.getId());
			if(type.equals("Salary")) {//we update changed data and save them
				actualComp.setAmount(compensation.getAmount());
				actualComp.setDescription(compensation.getDescription());
				compService.save(actualComp);
				attribute.addFlashAttribute("success", "Successfully modified");
				return "redirect:/employees";
			}
			//If compensation isn't salary
			if(compService.validateTypeAndAmount(compensation)) {//validate amount for the compensation type
				//we update changed data and save them
				actualComp.setAmount(compensation.getAmount());
				actualComp.setDescription(compensation.getDescription());
				compService.save(actualComp);
				attribute.addFlashAttribute("success", "Successfully modified");
				return "redirect:/employees";
			}
			else {//If return false the amount is wrong for this compensation type
				attribute.addFlashAttribute("error", "Amount must be different from zero or greater than zero");
				return "redirect:/employees";
			}
		}
	}
}
