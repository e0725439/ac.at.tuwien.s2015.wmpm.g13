/**
 * Copyright (c) 2015. All rights reserved.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 */
package ac.at.tuwien.s2015.wmpm.g13.model.order;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ac.at.tuwien.s2015.wmpm.g13.model.person.LegalPerson;

@XmlType(name="Order")
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Order {

    protected String orderId;
    protected Date orderDate;
    protected Date sendDate;
    protected List<OrderItem> orderItems;
    protected LegalPerson supplier;

    public Order() {
        // empty constructor
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
     * @return the orderDate
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
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
     * @param sendDate the sendDate to set
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * @return the supplier
     */
    public LegalPerson getSupplier() {
        return supplier;
    }

    /**
     * @param supplier the supplier to set
     */
    public void setSupplier(LegalPerson supplier) {
        this.supplier = supplier;
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SimpleOrder [orderId=" + orderId + ", orderDate=" + orderDate
                + ", sendDate=" + sendDate + ", supplier=" + supplier
                + ", orderItems=" + orderItems + "]";
    }

}
