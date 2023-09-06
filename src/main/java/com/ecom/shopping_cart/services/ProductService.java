package com.ecom.shopping_cart.services;

import com.ecom.shopping_cart.entities.ItemCategory;
import com.ecom.shopping_cart.entities.Product;
import com.ecom.shopping_cart.helper.ExcelHelper;
import com.ecom.shopping_cart.repository.CategoryRepository;
import com.ecom.shopping_cart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	RestTemplate restTemplate;
	public String getProList(){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept( Arrays.asList( MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String> ( ( headers ) );
		return restTemplate.exchange("http://localhost:8081/product/", HttpMethod.GET,entity,String.class).getBody ();
	}
	public Product saveProduct(Product product) {
		product.setThumbnail(product.getThumbnail());
		 int categoryId = product.getCategory().getCat_id();
	        ItemCategory category = categoryRepository.findById(categoryId)
	                .orElseThrow(() -> new RuntimeException("Invalid category"));

	        product.setCategory(category);
		return productRepository.save(product);

	}

	public List<Product> findAll() {

		return productRepository.findAll ( Sort.by("pName").ascending());
	}
	public List<Product> findCategory(String category){
		return productRepository.findByCategory(category);
	}

	public List<Product> findByPriceAndCat(String category,int price){
		return productRepository.findByCategoryWithPrice ( category,price );
	}
	
	public void save(MultipartFile file) {
	    try {
	      List<Product> products = ExcelHelper.excelToProducts(file.getInputStream());
	      productRepository.saveAll(products);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store excel data: " + e.getMessage());
	    }
	  }
	
	 public ByteArrayInputStream load() {
		    List<Product> products = productRepository.findAll();

		    ByteArrayInputStream in = ExcelHelper.productsToExcel(products);
		    return in;
		  }

}
