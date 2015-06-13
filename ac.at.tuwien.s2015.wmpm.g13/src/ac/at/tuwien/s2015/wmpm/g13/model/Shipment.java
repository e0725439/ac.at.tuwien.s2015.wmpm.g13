/**
 * Copyright (c) 2015. All rights reserved.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 */
package ac.at.tuwien.s2015.wmpm.g13.model;

import ac.at.tuwien.s2015.wmpm.g13.model.order.OrderItem;

import java.util.List;

public class Shipment {

    private String shipmentId;
    private String orderId;
    private List<OrderItem> orderItems;
    private Boolean shipped;

    public Shipment() {
        // empty constructor
    }

    public Shipment(String shipmentId, String orderId, List<OrderItem> orderItems, Boolean shipped) {
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.orderItems = orderItems;
        this.shipped = shipped;
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

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Boolean isShipped() {
        return shipped;
    }

    public void setShipped(Boolean shipped) {
        this.shipped = shipped;
    }

    /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */

    @Override
    public String toString() {
        return "Shipment{" +
                "shippmentId='" + shipmentId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderItems=" + orderItems +
                ", shipped=" + shipped +
                '}';
    }

}
