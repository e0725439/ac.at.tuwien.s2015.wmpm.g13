/**
 * Copyright (c) 2015. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 * 
 */
package ac.at.tuwien.s2015.wmpm.g13.model;

import java.util.Date;
import java.util.List;

import ac.at.tuwien.s2015.wmpm.g13.model.person.LegalPerson;
import ac.at.tuwien.s2015.wmpm.g13.model.person.NaturalPerson;

public class SimpleOrder {

	private String orderId;

	// the person who receives the order
	private NaturalPerson customer;

	// the person who sends the order
	private LegalPerson supplier;

	// date when the order is received
	private Date orderDate;

	// date when the order is send to the customer
	private Date sendDate;

	// list of order items: i.e. product and product quantity
	private List<OrderItem> orderItems;

	public SimpleOrder() {
		// empty constructor
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the customer
	 */
	public NaturalPerson getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 *            the customer to set
	 */
	public void setCustomer(NaturalPerson customer) {
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

	/**
	 * @return the orderDate
	 */
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * @param orderDate
	 *            the orderDate to set
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	/**
	 * @return the sendDate
	 */
	public Date getSendDate() {
		return sendDate;
	}

	/**
	 * @param sendDate
	 *            the sendDate to set
	 */
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	/**
	 * @return the orderItems
	 */
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	/**
	 * @param orderItems
	 *            the orderItems to set
	 */
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", customer=" + customer
				+ ", supplier=" + supplier + ", orderDate=" + orderDate
				+ ", sendDate=" + sendDate + ", orderItems=" + orderItems + "]";
	}
	

}
