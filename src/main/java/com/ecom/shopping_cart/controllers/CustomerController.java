package com.ecom.shopping_cart.controllers;

import com.ecom.shopping_cart.entities.User;
import com.ecom.shopping_cart.repository.CustomerRepository;
import com.ecom.shopping_cart.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	CustomerRepository customerRepository;
//	@Autowired
//	BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @GetMapping("/home")
    public String home(){
        return "hello welcome to home page";
    }

//   
//    @PostMapping("/")
//    public ResponseEntity<User> addCust(@RequestBody User customer) {
//    	System.out.println(customer);
//    	if(customer==null){
//            return ResponseEntity.status( HttpStatus.BAD_REQUEST).build();
//        }
//    	try{
//			User cust=customerService.addCustomer(customer);
//			return ResponseEntity.status(HttpStatus.OK).body(cust);
//		}catch(Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}
//
//    }

    @GetMapping("/{cid}")
    public ResponseEntity<User> findId(@PathVariable int cid){
    	User customer = customerService.findById(cid);
		if(customer == null) throw new RuntimeException("Customer not found");
		return ResponseEntity.status(HttpStatus.OK).body(customer);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllCustomer(){
    	List<User> list=customerService.getAll();
    	return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @DeleteMapping("/{cid}")
    public ResponseEntity<User> delete(@PathVariable int cid){
    	HttpStatus result=customerService.delCustomer(cid);
    	return ResponseEntity.status(result).build();
    }

    @PutMapping("/{cid}")
    public ResponseEntity<User> update(@RequestBody User customer,@PathVariable int cid){
        if(customerService.existById ( cid )){
            User cust=customerService.updateCustomer(customer, cid);
            return ResponseEntity.status(HttpStatus.OK).body(cust);
        }
       else{
            throw new RuntimeException("Customer not found");
        }

    }
}
