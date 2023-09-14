package com.ecom.shopping_cart.controllers;

import com.ecom.shopping_cart.entities.Address;
import com.ecom.shopping_cart.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
