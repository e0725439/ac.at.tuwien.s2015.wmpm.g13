/**
 * Copyright (c) 2015. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 * 
 */
package ac.at.tuwien.s2015.wmpm.g13.model;

public class OrderItem {

	private Product product;

	private int quantity;

	public OrderItem() {
		// empty constructor
	}

	public OrderItem(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	/**
	 * @return the product
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 * @throws DataModelException 
	 */
	public void setQuantity(int quantity) throws DataModelException {
		
		if(quantity < 1){
			throw new DataModelException("OrderItem quantity must be 1 or more.");
		}
		
		this.quantity = quantity;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderItem [product=" + product + ", quantity=" + quantity + "]";
	}
	
}
