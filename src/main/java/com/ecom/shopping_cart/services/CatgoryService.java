package com.ecom.shopping_cart.services;

import com.ecom.shopping_cart.entities.ItemCategory;
import com.ecom.shopping_cart.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatgoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	public List<ItemCategory> allCat(){
		return categoryRepository.findAll();
	}
	
	public ItemCategory additem(ItemCategory itemCategory) {
		if(categoryRepository.existsByCategory(itemCategory.getCategory())) {
			return new ItemCategory();
		}
		return categoryRepository.save(itemCategory);
	}
	
	public Optional<ItemCategory> searchByCat(String category){
		return categoryRepository.findByCategory(category);
	}
}
