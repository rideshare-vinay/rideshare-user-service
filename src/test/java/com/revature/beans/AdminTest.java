package com.revature.beans;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AdminTest {
	
	private Admin admin;
	private Admin admin2;

	@Before
	public void setDemo() {
		admin = new Admin(1, "userName");
		admin2 = new Admin(1, "userName");
	}

	@Test
	public void testToString() {
		String actual = admin.toString();
		String expected = "Admin [adminId=" + admin.getAdminId() + ", userName=" + admin.getUserName() + "]";
		assertEquals(expected, actual);
	}
	
	@Test
	public void testHashCode() {
		assertTrue(admin.hashCode() == admin2.hashCode());
		admin.setUserName(null);
		assertNotNull(admin.hashCode());
		assertNotNull(admin);
	}
	
	@Test
	public void testEqual() {
		assertTrue(admin.equals(admin2) && admin2.equals(admin));
	}

	@Test
	public void testEqualsNull() {
		admin2 = null;
		assertFalse(admin.equals(admin2));
	}
	
	@Test
	public void testEqualsGetClass() {
		User user = new User();
		assertFalse(admin.equals(user));
	}
	
	@Test
	public void testEqualsOtherAdminId() {
		Admin other = new Admin(2, "userName2");
		assertFalse(admin.equals(other));
	}
	
	@Test
	public void testEqualsUserNameNull() {
		Admin other = new Admin(1, "userName");
		admin.setUserName(null);
		assertFalse(admin.equals(other));
	}
	
	@Test
	public void testEqualsUserNameNotTheSameAsAdmin() {
		Admin other = new Admin(1, "otherUserName");
		assertFalse(admin.equals(other));
	}

}
