package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.Invoice;
import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;
import ac.at.tuwien.s2015.wmpm.g13.model.order.SimpleOrder;
import ac.at.tuwien.s2015.wmpm.g13.model.TestData;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

/**
 * Created by mattias on 5/13/2015.
 */
@Component
public class SupplierOrderItemsBean implements Processor {

    private static final Logger LOGGER = Logger.getLogger(SupplierOrderItemsBean.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        LOGGER.debug("Got a new order for missingOrders, now creating the invoice and sending it back again");
        exchange.getIn().setBody(getInvoice(exchange.getIn().getBody(List.class)));
    }

    private Invoice getInvoice(List<OrderItem> orderItems) {
        double totalPrice = 0;
        Invoice invoice = new Invoice();
        invoice.setCreationDate(new Date());
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getProduct().getPrice()*orderItem.getQuantity();
        }
        invoice.setTotalPrice(totalPrice);
        SimpleOrder simpleOrder = new SimpleOrder();
        simpleOrder.setCustomer(TestData.getCompany());
        simpleOrder.setSendDate(new Date());
        simpleOrder.setOrderItems(orderItems);
        simpleOrder.setSupplier(TestData.getSupplier());
        invoice.setOrder(simpleOrder);
        return invoice;
    }
}
