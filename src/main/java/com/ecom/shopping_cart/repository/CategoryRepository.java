package com.ecom.shopping_cart.repository;

import com.ecom.shopping_cart.entities.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<ItemCategory, Integer> {
Optional<ItemCategory> findByCategory(String category);
	
boolean existsByCategory(String category);
}
