package com.ecom.shopping_cart.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.shopping_cart.entities.ItemCategory;
import com.ecom.shopping_cart.services.CatgoryService;

@RestController
public class CategoryController {
	@Autowired
	CatgoryService catgoryService;
	@GetMapping("/category")
	public List<ItemCategory> getAll() {
		return catgoryService.allCat();
	}
	@PostMapping("/category")
	public ItemCategory addCategory(@RequestBody ItemCategory itemCategory) {
		return catgoryService.additem(itemCategory);
	}
	@GetMapping("/category/{category}")
	public Optional<ItemCategory> getByCat(@PathVariable String category) {
		return catgoryService.searchByCat(category);
	}
}
