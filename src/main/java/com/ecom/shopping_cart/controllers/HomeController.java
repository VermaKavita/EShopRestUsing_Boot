package com.ecom.shopping_cart.controllers;

import com.ecom.shopping_cart.emailService.EmailService;
import com.ecom.shopping_cart.entities.Roles;
import com.ecom.shopping_cart.entities.User;
import com.ecom.shopping_cart.jwtAuth.JwtHelper;
import com.ecom.shopping_cart.jwtAuth.JwtRequest;
import com.ecom.shopping_cart.jwtAuth.JwtResponse;
import com.ecom.shopping_cart.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class HomeController {

	@Autowired
	private AuthenticationManager manager;
	@Autowired
	private JwtHelper jwtHelper;
	@Autowired
	PasswordEncoder bCryptPasswordEncoder;
	@Autowired
	CustomerService customerService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserDetailsService userDetailsService;
	@GetMapping("/hello")
	public String hello() {
		String subject = "Email cration successfull";
		String Email = "kaviver1234@outlook.com";
		String message = "Hi " + Email + "\n Your Account setup completed";
		System.out.println(emailService.sendEmail(subject, message, Email));
		return "hello this is a page";
	}

	@PostMapping("/register")
	public ResponseEntity<String> userSignUp(@Valid @RequestBody User customer) {
		if (customerService.exists (customer.getEmail())) {
			return ResponseEntity.badRequest().body("Email is already taken");
		}
		Set<Roles> roles = customer.getRoles();
		System.out.println("roles "+roles);
		if (roles==null) {
			roles = new HashSet<> ();
			roles.add(Roles.ROLE_USER);
		}
		User user = new User();
		user.setUsername(customer.getUsername());
		user.setEmail(customer.getEmail());
		user.setPhone(customer.getPhone());
//	    user.setPassword(customer.getPassword());
		user.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));

		user.setRoles( roles );
		customerService.addCustomer ( user );
		String subject = "Registered successfully";
		String Email = customer.getEmail();
		String message = "Hi " + customer.getUsername() + "\n Your Account setup completed" + customer.getEmail();
		emailService.sendEmail(subject, message, Email);
		return ResponseEntity.ok("User signed up successfully");
	}

	@PostMapping("/jwt")
	public ResponseEntity<JwtResponse> createToken(@RequestBody JwtRequest request) {
		System.out.println("request is " + request.getUsername());
//		Authentication auth=this.authenticate(request.getUsername (),request.getPassword ());
		Authentication auth=manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername (), request.getPassword ()));
		System.out.println("token generate krne gaya");
		UserDetails user= (UserDetails) auth.getPrincipal ();
//		UserDetails user=this.userDetailsService.loadUserByUsername ( request.getUsername () );
		String token = this.jwtHelper.generateToken( user );
		JwtResponse response=new JwtResponse( user.getUsername (),token);
		System.out.println("toekn is " + token);
		return new ResponseEntity( response, HttpStatus.OK );
	}
}