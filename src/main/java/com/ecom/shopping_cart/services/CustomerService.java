package com.ecom.shopping_cart.services;

import com.ecom.shopping_cart.entities.*;
import com.ecom.shopping_cart.repository.AddressRepository;
import com.ecom.shopping_cart.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AddressRepository addressRepository;

//	@Autowired
//	private  PasswordEncoder bCryptPasswordEncoder;
	public User addCustomer(User customer) {
		Optional<User> user=customerRepository.findByEmail ( customer.getEmail () );
		if(user.isPresent ()){
			throw new RuntimeException("User already registered. Please use different username.");
		}
	return customerRepository.save(customer);
	}

	public List<User> getAll(){
		return customerRepository.findAll();
	}

	public User findById(int cid) {
		return customerRepository.findById(cid);
	}

	public boolean exists(String email) {
		return customerRepository.existsByEmail (email);
	}

	public Boolean existById(int cid){
		return customerRepository.existsById ( cid );
	}
	public Optional<User> findEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	public HttpStatus delCustomer(int cid) {
		 if(customerRepository.existsById ( cid )){
	            customerRepository.deleteById ( cid );
	            return HttpStatus.OK;
	        }
	        else return HttpStatus.BAD_REQUEST;
	  	}

	public User updateCustomer(User customer,int cid) {
		if(customerRepository.existsById ( cid )){
			User costom=customerRepository.findById(cid);
			 List<Address> addresses = customer.getAddress();
			 List<Address> addresses1=costom.getAddress ();
			 Set<Roles> role=customer.getRoles();
			 System.out.println(role);
	            if (addresses != null ) {
	                for (Address updatedAddress : addresses) {
	                    updatedAddress.setCustomer(costom);
	                    addressRepository.save(updatedAddress);
	                    addresses1.add(updatedAddress);
	                }
	            }
	            if(role!=null) {
	            	for(Roles roles:role) {
	                	costom.getRoles().add(roles);
	                }
	            }
				costom.setCid ( cid );
	            costom.setAddress(addresses1);
	            costom.setRoles(costom.getRoles());
				costom.setOrders ( customer.getOrders () );
	             return customerRepository.saveAndFlush(costom);
        }
		else {
			return null;
		}
	}

	public HashMap<String, Object> getOrders(int Cid) {
		User cust =  customerRepository.findById(Cid);
		List<Orders> list = cust.getOrders();
		List<Product> productList = new ArrayList<>();
		if (list != null) {
			for (Orders order : list) {
				productList.add(order.getProduct());
			}
		}
		HashMap<String, Object> ht = new HashMap<String, Object>();
		if (list != null) {
			ht.put("Orders", list);
			ht.put("products", productList);
		} else {
			ht.put("Orders", 0);
			ht.put("product", 0);
		}
		return ht;
	}

}
