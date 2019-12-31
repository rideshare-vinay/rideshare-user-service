package com.revature.services;

import java.util.List;

import com.revature.beans.Admin;

public interface AdminService {
	
	public List<Admin> getAdmins();
	public Admin getAdminById(int id);
}
