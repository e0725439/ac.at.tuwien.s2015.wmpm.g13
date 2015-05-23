package ac.at.tuwien.s2015.wmpm.g13.model.order;

import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;

import java.util.Date;
import java.util.List;

/**
 * Created by josef on 5/23/2015.
 */
public abstract class Order {

    private String orderId;

    private Date orderDate;

    private Date sendDate;

    private List<OrderItem> orderItems;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
