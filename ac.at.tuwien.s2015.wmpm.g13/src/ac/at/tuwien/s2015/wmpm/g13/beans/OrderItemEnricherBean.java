package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.DataModelException;
import ac.at.tuwien.s2015.wmpm.g13.model.Invoice;
import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;
import ac.at.tuwien.s2015.wmpm.g13.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mattias on 5/22/2015.
 */
@Component
public class OrderItemEnricherBean implements AggregationStrategy {

    private static final Logger LOGGER = Logger.getLogger(OrderItemEnricherBean.class);

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        ObjectMapper objectMapper = new ObjectMapper();
        Invoice invoice = oldExchange.getIn().getBody(Invoice.class);    // vom supplier
        List<OrderItem> basicDBObjects = newExchange.getIn().getBody(List.class); // aus der datebank
        List<OrderItem> newOrderItems= new ArrayList<>();

        LOGGER.info("Enriching orderItems from database and supplier, costs in total: " + invoice.getTotalPrice());

        for (OrderItem newOrderItem : invoice.getOrder().getOrderItems()) {
            boolean found = false;
            for (OrderItem basicDBObject : basicDBObjects) {
                if (basicDBObject.getProduct().getName().equals(newOrderItem.getProduct().getName())) {
                    try {
                        newOrderItem.setQuantity(newOrderItem.getQuantity()+basicDBObject.getQuantity());
                    } catch (DataModelException e) {
                        LOGGER.error("Validation expcetion, cause of " + e.getMessage());
                    }
                    newOrderItems.add(basicDBObject);
                    break;
                }
            }
            if (!found) {
                newOrderItems.add(newOrderItem);
            }
        }
        oldExchange.getIn().setBody(newOrderItems);
        return oldExchange;
    }
}
