package com.ecom.shopping_cart.controllers;

import com.ecom.shopping_cart.emailService.EmailRequest;
import com.ecom.shopping_cart.emailService.EmailService;
import com.ecom.shopping_cart.entities.Orders;
import com.ecom.shopping_cart.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private EmailService emailService;
    @Autowired
    OrderService orderService;
    @PostMapping("/")
    public Orders placeAnOrder(@RequestBody Orders order){
       return orderService.placeOrder ( order );
    }
    @GetMapping("/")
    public List<Orders> getOrders(){
        return orderService.findAll ();
    }
    @GetMapping("/{oid}")
    public Orders findMyOrder(@PathVariable int oid){
        return orderService.findById ( oid );
    }
    @DeleteMapping("/{oId}")
    public Orders deleteById(@PathVariable int oId){
    	return  new ResponseEntity<Orders>(HttpStatus.OK).getBody();
    }
    
    @PostMapping("/sendemail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        System.out.println(request + " is this");
        String to="kaviver1234@outlook.com";
        String subject="Ordered successfull";
        String message="thank you for your Order";
        emailService.sendEmail(subject,message,to);
        boolean result=emailService.sendEmail(subject, message, to);
        if (result) {
            return ResponseEntity.ok("Email sent successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }
}
