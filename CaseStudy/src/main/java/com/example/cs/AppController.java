package com.example.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AppController {

	@Autowired
	private AdminRepository adminRepo;
	
	@GetMapping("/admin")
	public String listAdmin(Model model) {
		List<Admin> listAdmin = adminRepo.findAll();
		model.addAttribute("listAdmin", listAdmin);
		
		return "admin";
	}
}


