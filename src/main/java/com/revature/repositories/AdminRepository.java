package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.beans.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

}
