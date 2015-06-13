/**
 * Copyright (c) 2015. All rights reserved.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 */
package ac.at.tuwien.s2015.wmpm.g13.model.order;

import ac.at.tuwien.s2015.wmpm.g13.model.person.NaturalPerson;
import ac.at.tuwien.s2015.wmpm.g13.model.person.Person;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="SimpleOrder")
public class SimpleOrder extends Order{

    private NaturalPerson customer;

    public SimpleOrder() {
        // empty constructor
    }

    /**
     * @return the customer
     */
    public NaturalPerson getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(NaturalPerson customer) {
        this.customer = customer;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SimpleOrder [orderId=" + orderId + ", orderDate=" + orderDate
                + ", sendDate=" + sendDate + ", customer=" + customer
                + ", supplier=" + supplier + ", orderItems=" + orderItems + "]";
    }

}
