package com.ecom.shopping_cart.services;

import com.ecom.shopping_cart.entities.Orders;
import com.ecom.shopping_cart.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;

	public Orders placeOrder(Orders order) {
		order.setoDate ( new Date () );
		Orders placeOrder=orderRepository.save(order);
		return placeOrder;
	}

	public List<Orders> findAll() {
		return orderRepository.findAll();
	}

	public Orders findById(int id) {
		Optional<Orders> ord=orderRepository.findById(id);
		Orders newOrder=ord.get();
		return newOrder;
	}
	
	public void deleteById(int oId) {
		orderRepository.deleteById(oId);
	}
	
}
