package com.revature.controllers;

import java.util.List;

import javax.validation.Valid;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/admins")
@CrossOrigin
@Api(tags= {"Admin"})
public class AdminController {
	
	@Autowired
	private AdminService as;
	
	@ApiOperation(value="Returns all admins", tags= {"Admin"})
	@GetMapping
	public List<Admin> getAdmins() {
		
		return as.getAdmins();
	}
	
	@ApiOperation(value="Returns admin by id", tags= {"Admin"})
	@GetMapping("/{id}")
	public Admin getAdminById(@PathVariable("id")int id) {
		
		return as.getAdminById(id);
	}
		
	@ApiOperation(value="Adds a new admin", tags= {"Admin"})
	@PostMapping
	public ResponseEntity<Admin> createAdmin(@Valid @RequestBody Admin admin) {
		
		return new ResponseEntity<>(as.createAdmin(admin), HttpStatus.CREATED);
	}
	
	@ApiOperation(value="Updates admin by id", tags= {"Admin"})
	@PutMapping("/{id}")
	public Admin updateAdmin(@Valid @RequestBody Admin admin) {
		
		return as.updateAdmin(admin);
	}
	
	@ApiOperation(value="Deletes an admin by id", tags= {"Admin"})
	@DeleteMapping("/{id}")
	public String deleteAdmin(@PathVariable("id")int id) {
		
		return as.deleteAdminById(id);
	}
}
