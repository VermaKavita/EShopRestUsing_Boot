package com.ecom.shopping_cart.entities;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
public class Orders {

	@Id
	@GeneratedValue
	private int oId;
	@Column(name="c_id")
	private int cId;
	private int qty;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date oDate;
	@ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	public int getoId() {
		return oId;
	}
	public void setoId(int oId) {
		this.oId = oId;
	}
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public Date getoDate() {
		return oDate;
	}
	public void setoDate(Date oDate) {
		this.oDate = oDate;
	}
	public User getCustomer() {
		return customer;
	}
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	

}
