package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Admin;
import com.revature.services.AdminService;

@RestController
@RequestMapping("/admins")
@CrossOrigin
public class AdminController {
	
	@Autowired
	private AdminService as;
	
	@GetMapping
	public List<Admin> getAdmins() {
		
		return as.getAdmins();
	}
	
	@GetMapping("/{id}")
	public Admin getAdminById(@PathVariable("id")int id) {
		
		return as.getAdminById(id);
	}
}
