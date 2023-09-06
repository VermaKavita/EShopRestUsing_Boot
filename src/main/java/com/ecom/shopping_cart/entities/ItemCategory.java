package com.ecom.shopping_cart.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class ItemCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cat_id;
	private String category;
	@OneToOne
	@JsonBackReference
	private Product product;

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
