package com.ecom.shopping_cart.controllers;

import com.ecom.shopping_cart.entities.User;
import com.ecom.shopping_cart.repository.CustomerRepository;
import com.ecom.shopping_cart.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AdminService adminService;

	@GetMapping("")
	public List<User> getCus() {
		return adminService.getAll();
	}
	@GetMapping("/")
	public List<User> getAdmin() {
		return adminService.getAllAdmin ();
	}
	@GetMapping("/users")
	public List<User> getCustomer() {
		return adminService.getAllCustomer();
	}

	@GetMapping("/managers")
	public List<User> getManager() {
		return adminService.getAllManager();
	}

	@GetMapping("/{cid}")
	public ResponseEntity<User> getUserById(@PathVariable int cid) {
		User customer=adminService.getSingle(cid);
		if(customer!=null) {
				return ResponseEntity.status(HttpStatus.OK).body(customer);}
		else {
			throw new RuntimeException("User not present");
		}
	}
	@PutMapping("/{cid}")
	public ResponseEntity<User> updatingUser(@PathVariable int cid, @RequestBody User customer) {
//		customerRepository.save(customer);
		
		return new ResponseEntity<User>(adminService.updUser(customer, cid),HttpStatus.CREATED);
	}

	@DeleteMapping("/{cid}/{role}")
	public ResponseEntity<User> deleteRole(@PathVariable String role, @PathVariable int cid) {
		HttpStatus status=adminService.deleteRole(role, cid);
		return ResponseEntity.status(status).body(customerRepository.findById(cid));

	}
	@DeleteMapping("/{cid}")
	public ResponseEntity<User> deleteUser(@PathVariable int  cid) {
		adminService.delUser(cid);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
