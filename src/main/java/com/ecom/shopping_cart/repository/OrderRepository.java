package com.ecom.shopping_cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.shopping_cart.entities.Orders;

public interface OrderRepository extends JpaRepository<Orders, Integer>{

}
