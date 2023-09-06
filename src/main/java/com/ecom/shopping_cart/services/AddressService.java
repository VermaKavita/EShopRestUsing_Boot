package com.ecom.shopping_cart.services;

import com.ecom.shopping_cart.entities.Address;
import com.ecom.shopping_cart.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	public Address save(Address address) {
		return addressRepository.save(address);
	}

	public List<Address> getAddr(){
		return addressRepository.findAll();
	}

	@SuppressWarnings("deprecation")
	public Address findSingle(int aid) {
		return addressRepository.getById(aid);
	}


	public Address updateAdr(Address address,int aid) {
		//address.setCustomer(customer);
		address.setaId ( aid );
		return addressRepository.save(address);
		}

	public void deleteAdr(int aid) {
		addressRepository.deleteById(aid);
	}
}
