package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
		
	@PostMapping
	public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
		
		return new ResponseEntity<>(as.createAdmin(admin), HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public Admin updateAdmin(@RequestBody Admin admin) {
		
		return as.updateAdmin(admin);
	}
	
	@DeleteMapping("/{id}")
	public String deleteAdmin(@PathVariable("id")int id) {
		
		return as.deleteAdminById(id);
	}
}
