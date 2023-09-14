package com.ecom.shopping_cart.repository;

import com.ecom.shopping_cart.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Integer>{

}
