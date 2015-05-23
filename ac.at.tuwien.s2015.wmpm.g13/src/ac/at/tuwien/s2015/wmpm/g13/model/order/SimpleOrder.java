/**
 * Copyright (c) 2015. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 * 
 */
package ac.at.tuwien.s2015.wmpm.g13.model.order;

import java.util.Date;
import java.util.List;

import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;
import ac.at.tuwien.s2015.wmpm.g13.model.person.LegalPerson;
import ac.at.tuwien.s2015.wmpm.g13.model.person.NaturalPerson;
import ac.at.tuwien.s2015.wmpm.g13.model.person.Person;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SimpleOrder extends Order{

	// the person (either natural or legal) who receives the order
	private Person customer;

	// the person (either natural or legal) who sends the order
	private Person  supplier;

	public SimpleOrder() {
		// empty constructor
	}

	/**
	 * @return the customer
	 */
	public Person getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(Person customer) {
		this.customer = customer;
	}

	/**
	 * @return the supplier
	 */
	public Person getSupplier() {
		return supplier;
	}

	/**
	 * @param supplier
	 *            the supplier to set
	 */
	public void setSupplier(Person supplier) {
		this.supplier = supplier;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Order [orderId=" + getOrderId() + ", customer=" + customer
				+ ", supplier=" + getSupplier() + ", orderDate=" + getOrderDate()
				+ ", sendDate=" + getSendDate() + ", orderItems=" + getOrderItems() + "]";
	}
	

}
