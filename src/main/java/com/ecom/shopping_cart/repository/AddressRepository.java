package com.ecom.shopping_cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.shopping_cart.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{

}
