package com.revature.services;

import java.util.List;

import com.revature.beans.User;

public interface UserService {
	
	public List<User> getUsers();
	public User getUserById(int id);
	public User addUser(User user);
	public User updateUser(User user);
	public String deleteUserById(int id);
	
}
