/**
 * Copyright (c) 2015. All rights reserved.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 */
package ac.at.tuwien.s2015.wmpm.g13.model;

import ac.at.tuwien.s2015.wmpm.g13.model.person.LegalPerson;
import ac.at.tuwien.s2015.wmpm.g13.model.person.Person;

import java.util.Date;
import java.util.List;

public class Shippment {

    private String shippmentId;
    private String orderId;
    private List<OrderItem> orderItems;
    private Boolean ready;
    private Boolean paid;

    public Shippment() {
        // empty constructor
    }

    public Shippment(String shippmentId, String orderId, List<OrderItem> orderItems, Boolean ready, Boolean paid) {
        this.shippmentId = shippmentId;
        this.orderId = orderId;
        this.orderItems = orderItems;
        this.ready = ready;
        this.paid = paid;
    }

    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the orderItems
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * @param orderItems the orderItems to set
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getShippmentId() {
        return shippmentId;
    }

    public void setShippmentId(String shippmentId) {
        this.shippmentId = shippmentId;
    }

    public Boolean getReady() {
        return ready;
    }

    public void setReady(Boolean ready) {
        this.ready = ready;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */

    @Override
    public String toString() {
        return "Shippment{" +
                "shippmentId='" + shippmentId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderItems=" + orderItems +
                ", ready=" + ready +
                ", paid=" + paid +
                '}';
    }

}
