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

import ac.at.tuwien.s2015.wmpm.g13.model.person.Person;

public class Order {

	private String orderId;

	// the person (either natural or legal) who receives the order
	private Person customer;

	// the person (either natural or legal) who sends the order
	private Person supplier;

	// date when the order is received
	private Date orderDate;

	// date when the order is send to the customer
	private Date sendDate;

	// list of order items: i.e. product and product quantity
	private List<OrderItem> orderItems;

	public Order() {
		// empty constructor
	}

	public Order(Person customer, Person supplier, Date orderDate,
			Date sendDate, List<OrderItem> orderItems) {
		this.customer = customer;
		this.supplier = supplier;
		this.orderDate = orderDate;
		this.sendDate = sendDate;
		this.orderItems = orderItems;
	}

	public Order(String orderId, Person customer, Person supplier,
			Date orderDate, Date sendDate, List<OrderItem> orderItems) {
		this.orderId = orderId;
		this.customer = customer;
		this.supplier = supplier;
		this.orderDate = orderDate;
		this.sendDate = sendDate;
		this.orderItems = orderItems;
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

}
