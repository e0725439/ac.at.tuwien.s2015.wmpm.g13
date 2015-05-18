package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.Invoice;
import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;
import ac.at.tuwien.s2015.wmpm.g13.model.Product;
import ac.at.tuwien.s2015.wmpm.g13.model.SimpleOrder;
import com.mongodb.Mongo;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mattias on 5/13/2015.
 */
@Component
public class SupplierProcessBean implements Processor {

    private static final Logger LOGGER = Logger.getLogger(SupplierProcessBean.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        LOGGER.debug("Got a new simpleOrder, now creating the invoice and sending it back again");
        SimpleOrder simpleOrder = exchange.getIn().getBody(SimpleOrder.class);
        exchange.getOut().setBody(getInvoice(simpleOrder));
    }

    private Invoice getInvoice(SimpleOrder simpleOrder) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal tempPrice;
        Invoice invoice = new Invoice();
        invoice.setOrder(simpleOrder);
        invoice.setCreationDate(new Date());
        for (OrderItem orderItem : simpleOrder.getOrderItems()) {
            tempPrice = orderItem.getProduct().getPrice().multiply(new BigDecimal(orderItem.getQuantity()));
            totalPrice.add(tempPrice);
        }
        invoice.setTotalPrice(totalPrice);
        return invoice;
    }
}
