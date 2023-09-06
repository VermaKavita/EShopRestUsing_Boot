package com.ecom.shopping_cart.entities;

import java.util.Set;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class SignUpRequest {
	private String username;
	private String email;
	private String password;
	private long phone;
	@Enumerated(EnumType.STRING)
	private Set<Roles> roles;
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Roles> getRoles() {
		return roles;
	}
	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}
	
}
