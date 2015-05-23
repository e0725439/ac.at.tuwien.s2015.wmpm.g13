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

public class BusinessOrder extends Order{

	// the person (either natural or legal) who receives the order
	private LegalPerson customer;

	// the person (either natural or legal) who sends the order
	private LegalPerson supplier;


	public BusinessOrder() {
		// empty constructor
	}

	/**
	 * @return the customer
	 */
	public LegalPerson getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(LegalPerson customer) {
		this.customer = customer;
	}

	/**
	 * @return the supplier
	 */
	public LegalPerson getSupplier() {
		return supplier;
	}

	/**
	 * @param supplier
	 *            the supplier to set
	 */
	public void setSupplier(LegalPerson supplier) {
		this.supplier = supplier;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Order [orderId=" + getOrderId() + ", customer=" + getCustomer()
				+ ", supplier=" + getSupplier() + ", orderDate=" + getOrderDate()
				+ ", sendDate=" + getSendDate() + ", orderItems=" + getOrderItems() + "]";
	}
	

}
