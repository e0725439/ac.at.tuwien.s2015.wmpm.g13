package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.Invoice;
import ac.at.tuwien.s2015.wmpm.g13.model.TestData;
import ac.at.tuwien.s2015.wmpm.g13.model.order.OrderItem;
import ac.at.tuwien.s2015.wmpm.g13.model.order.SimpleOrder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SupplierOrderItemsBean implements Processor {

    private static final Logger LOGGER = Logger.getLogger(SupplierOrderItemsBean.class);

    private List<OrderItem> parseOrderItems(List<BasicDBObject> objects) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<OrderItem> orderItems = new ArrayList<>();
        for (BasicDBObject object : objects) {
            try {
                orderItems.add(objectMapper.readValue(object.toString(), OrderItem.class));
            } catch (IOException e) {
                LOGGER.error("Error parsing orderItem, cause " + e.getMessage());
            }
        }
        return orderItems;
    }

    private Invoice getInvoice(List<OrderItem> orderItems) {
        Invoice invoice = null;
        if (orderItems.size() > 0) {
            double totalPrice = 0;
            invoice = new Invoice();
            invoice.setCreationDate(new Date());
            SimpleOrder simpleOrder = new SimpleOrder();
            simpleOrder.setCustomer(TestData.getCustomer());
            simpleOrder.setSendDate(new Date());
            simpleOrder.setOrderItems(orderItems);
            simpleOrder.setSupplier(TestData.getSupplier());
            for (OrderItem orderItem : orderItems) {
                totalPrice += orderItem.getProduct().getPrice() * orderItem.getQuantity();
            }
            invoice.setTotalPrice(totalPrice);
            invoice.setOrder(simpleOrder);
        }
        return invoice;
    }

    @SuppressWarnings("unchecked")
	@Handler
    public void process(Exchange exchange) throws Exception {
        LOGGER.info("Got a new order for missingOrders, now creating the invoice and sending it back again");
        exchange.getIn().setBody(getInvoice(parseOrderItems(exchange.getIn().getBody(List.class))));
    }
}
