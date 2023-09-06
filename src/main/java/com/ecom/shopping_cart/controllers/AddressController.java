package com.ecom.shopping_cart.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.shopping_cart.entities.Address;
import com.ecom.shopping_cart.services.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@PostMapping("/")
	public Address saveAddr(@RequestBody Address address) {
		return addressService.save(address);
	}
	@GetMapping("/")
	public List<Address> getAddress(){
		return addressService.getAddr();
	}
	@PutMapping("/{aid}")
	public Address updateAddress(@RequestBody Address address,@PathVariable int aid) {
		return addressService.updateAdr(address, aid);
	}
	@DeleteMapping("/{aid}")
	public void delete(int aid) {
		addressService.deleteAdr(aid);
	}

}
