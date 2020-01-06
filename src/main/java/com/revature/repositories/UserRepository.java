package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.beans.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	@Query("select u from User u where u.isDriver = ?1")
	public List<User> getUserByRole(boolean isDriver);
	
	@Query("select u from User u where u.userName = ?1")
	public List<User> getUserByUsername(String username);
}
