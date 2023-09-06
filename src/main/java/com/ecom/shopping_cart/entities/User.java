package com.ecom.shopping_cart.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "c_id")
	private int cid;
	@NotNull
	private String username;

	@Column(unique = true)
	@NotNull
	private String email;
	@NotNull
	private String password;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Address> address;
	@ElementCollection(targetClass = Roles.class,fetch = FetchType.EAGER)
	@CollectionTable(name="user_roles",joinColumns = @JoinColumn(name="user_id"))
	@Enumerated(EnumType.STRING)
	 private Set<Roles> roles ;
	private long phone;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date orderDate;
	@OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
	private List<Orders> orders;

	public Set<Roles> getRoles() {
		return roles;
	}

	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Customer [cid=" + cid + ", lastname=" + username + ", email=" + email
				+ ", password=" + password + ", address=" + address + ", phone=" + phone + ", orderDate=" + orderDate
				+ ", orders=" + orders + "]";
	}
}
