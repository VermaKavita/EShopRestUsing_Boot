package com.ecom.shopping_cart.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler({RuntimeException.class})
	public ResponseEntity<?> handleRuntimeExcetion(RuntimeException ex){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "error");
		map.put("error", ex.getMessage());
		return ResponseEntity.ok(map);
	}
}
