/**
 * Copyright (c) 2015. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 * 
 */
package ac.at.tuwien.s2015.wmpm.g13.model;

import java.math.BigDecimal;
import java.util.Date;

public class Invoice {

	private SimpleOrder order;

	private BigDecimal totalPrice;

	// the date the invoice was issued on
	private Date creationDate;

	public Invoice() {
		// empty constructor
	}
	
	/**
	 * @return the order
	 */
	public SimpleOrder getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(SimpleOrder order) {
		this.order = order;
	}

	/**
	 * @return the totalPrice
	 */
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice
	 *            the totalPrice to set
	 */
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
