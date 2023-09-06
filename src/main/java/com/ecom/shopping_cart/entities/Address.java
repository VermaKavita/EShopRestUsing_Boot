package com.ecom.shopping_cart.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int aId;
	private String city;
	private String state;
	private String street;
	private String pin;

	@ManyToOne
	@JsonBackReference
    private User customer;

	@Override
	public String toString() {
		return "Address [aId=" + aId + ", city=" + city + ", state=" + state + ", street=" + street + ", pin=" + pin
				+ ", customer=" + customer.getCid()+ "]";
	}

	public int getaId() {
		return aId;
	}

	public void setaId(int aId) {
		this.aId = aId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}


}
