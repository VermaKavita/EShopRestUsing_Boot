package com.ecom.shopping_cart.security;

import com.ecom.shopping_cart.entities.User;
import com.ecom.shopping_cart.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService  implements UserDetailsService{
	@Autowired
	CustomerService customerService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = customerService.findEmail (username).orElseThrow (()->new RuntimeException (username+" not found"));
		return MyUserDetails.createInstance(user);
	}

}
