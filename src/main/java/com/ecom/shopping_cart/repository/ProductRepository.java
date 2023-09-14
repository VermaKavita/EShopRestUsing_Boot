package com.ecom.shopping_cart.repository;

import com.ecom.shopping_cart.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>{
	Product findBypId(int pId);
	@Query(value="select * from product inner join item_category on product.p_id= item_category.product_p_id and category=:category ",nativeQuery = true)
	public List<Product> findByCategory(String category);

	@Query(value="select * from product inner join item_category on product.p_id=item_category.product_p_id and category=:category and price<=:price",nativeQuery = true)
	public List<Product> findByCategoryWithPrice(String category,int price);
	@Query(value="SELECT p from product p WHERE p.p_name like %name% OR p.details LIKE %name%",nativeQuery = true)
	public List<Product> globalAPi(String name);
}
