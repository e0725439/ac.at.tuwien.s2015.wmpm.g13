/**
 * Copyright (c) 2015. All rights reserved.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 */
package ac.at.tuwien.s2015.wmpm.g13.model;

import ac.at.tuwien.s2015.wmpm.g13.model.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice {

    private Order order;

    private double totalPrice;

    private Date creationDate;

    private boolean payed;

    public Invoice() {
        // empty constructor
    }

    public boolean getPayed() {
        return this.payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
