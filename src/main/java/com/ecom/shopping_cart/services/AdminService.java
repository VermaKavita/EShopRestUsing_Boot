package com.ecom.shopping_cart.services;

import com.ecom.shopping_cart.entities.Roles;
import com.ecom.shopping_cart.entities.User;
import com.ecom.shopping_cart.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AdminService {
	@Autowired
	CustomerRepository customerRepository;
	

	public User updUser(User customer, int cid) {
		if (customerRepository.existsById(cid)) {
			User costom = customerRepository.findById(cid);
			Set<Roles> role = customer.getRoles();
			customer.setCid(cid);
			if (role != null) {
				for (Roles roles : role) {
//					roles.setCustomer(costom);
					costom.getRoles().add(roles);
				}
			}
			return customerRepository.save(costom);
		} else {
			return customer;
		}
	}
	
	public void delUser(int cid) {
		 customerRepository.deleteById(cid);
	}

	public User getSingle(int cid) {
		return customerRepository.findById(cid);
	}
	public List<User> getAllCustomer() {
		return customerRepository.findByRole("ROLE_USER");
	}

	public List<User> getAll() {
		return customerRepository.findAll();
	}
	public List<User> getAllManager() {
		return customerRepository.findByRole("ROLE_MANAGER");
	}

	public List<User> getAllAdmin() {
		return customerRepository.findByRole("ROLE_ADMIN");
	}
	public HttpStatus deleteRole(String role, int cid) {
		if (customerRepository.existsById(cid)) {
				customerRepository.deleteByRoles ( role,cid );
			return HttpStatus.OK;
		} else
			throw new RuntimeException("Customer not found");
	}

}
