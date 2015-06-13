/**
 * Copyright (c) 2015. All rights reserved.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 */
package ac.at.tuwien.s2015.wmpm.g13.model.order;

import ac.at.tuwien.s2015.wmpm.g13.model.person.LegalPerson;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="BusinessOrder")
public class BusinessOrder extends Order{

    private LegalPerson customer;

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
     * @param customer the customer to set
     */
    public void setCustomer(LegalPerson customer) {
        this.customer = customer;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BusinessOrder [orderId=" + orderId + ", orderDate=" + orderDate
                + ", sendDate=" + sendDate + ", orderItems=" + orderItems
                + ", customer=" + customer + ", supplier=" + supplier + "]";
    }

}
