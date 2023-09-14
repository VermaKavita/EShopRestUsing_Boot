package com.ecom.shopping_cart.controllers;

import com.ecom.shopping_cart.entities.ItemCategory;
import com.ecom.shopping_cart.services.CatgoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CatgoryService catgoryService;
	@GetMapping("/")
	public List<ItemCategory> getAll() {
		return catgoryService.allCat();
	}
	@PostMapping("/")
	public ItemCategory addCategory(@RequestBody ItemCategory itemCategory) {
		return catgoryService.additem(itemCategory);
	}
	@GetMapping("/{category}")
	public Optional<ItemCategory> getByCat(@PathVariable String category) {
		return catgoryService.searchByCat(category);
	}
}
