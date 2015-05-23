/**
 * Copyright (c) 2015. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 * 
 */
package ac.at.tuwien.s2015.wmpm.g13.model.order;

import ac.at.tuwien.s2015.wmpm.g13.model.person.LegalPerson;

public class BusinessOrder extends Order{

	private LegalPerson customer;

	private LegalPerson supplier;


	public BusinessOrder() {
		// empty constructor
	}

	public LegalPerson getCustomer() {
		return customer;
	}

	public void setCustomer(LegalPerson customer) {
		this.customer = customer;
	}

	public LegalPerson getSupplier() {
		return supplier;
	}

	public void setSupplier(LegalPerson supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + getOrderId() + ", customer=" + getCustomer()
				+ ", supplier=" + getSupplier() + ", orderDate=" + getOrderDate()
				+ ", sendDate=" + getSendDate() + ", orderItems=" + getOrderItems() + "]";
	}
}
