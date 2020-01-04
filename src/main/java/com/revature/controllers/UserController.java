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

import com.revature.beans.User;
import com.revature.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/users")
@CrossOrigin
@Api(tags= {"User"})
public class UserController {
	
	@Autowired
	private UserService us;
	
	@ApiOperation(value="Returns all users", tags= {"User"})
	@GetMapping
	public List<User> getUsers() {
		
		return us.getUsers();
	}
	
	@ApiOperation(value="Returns user by id", tags= {"User"})
	@GetMapping("/{id}")
	public User getUserById(@PathVariable("id")int id) {
		
		return us.getUserById(id);
	}
	
	@ApiOperation(value="Adds a new user", tags= {"User"})
	@PostMapping
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		
		return new ResponseEntity<>(us.addUser(user), HttpStatus.CREATED);
	}
	
	@ApiOperation(value="Updates user by id", tags= {"User"})
	@PutMapping("/{id}")
	public User updateUser(@Valid @RequestBody User user) {
		
		return us.updateUser(user);
	}
	
	@ApiOperation(value="Deletes user by id", tags= {"User"})
	@DeleteMapping("/{id}")
	public String deleteUserById(@PathVariable("id")int id) {
		
		return us.deleteUserById(id);
	}
	
}
