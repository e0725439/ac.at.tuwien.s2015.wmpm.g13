/**
 * Copyright (c) 2015. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 * 
 */
package ac.at.tuwien.s2015.wmpm.g13.model.order;

import ac.at.tuwien.s2015.wmpm.g13.model.person.Person;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SimpleOrder extends Order{

	private Person customer;

	private Person  supplier;

	public SimpleOrder() {
		// empty constructor
	}

	public Person getCustomer() {
		return customer;
	}

	public void setCustomer(Person customer) {
		this.customer = customer;
	}

	public Person getSupplier() {
		return supplier;
	}

	public void setSupplier(Person supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + getOrderId() + ", customer=" + getCustomer()
				+ ", supplier=" + getSupplier() + ", orderDate=" + getOrderDate()
				+ ", sendDate=" + getSendDate() + ", orderItems=" + getOrderItems() + "]";
	}
}
