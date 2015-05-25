package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.DataModelException;
import ac.at.tuwien.s2015.wmpm.g13.model.Invoice;
import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;

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
 * Created by josef on 5/22/2015.
 */
@Component
public class OrderItemEnricherBean implements AggregationStrategy{

    private static final Logger LOGGER = Logger.getLogger(OrderItemEnricherBean.class);

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Invoice invoice = oldExchange.getIn().getBody(Invoice.class);    // vom supplier
        List<BasicDBObject> basicDBObjects = newExchange.getIn().getBody(List.class);
        List<OrderItem> newOrderItems = parseOrderItems(basicDBObjects);    // aus der datenbank
        List<OrderItem> actualOrderItems = new ArrayList<>();

        LOGGER.info("Enriching orderItems from database and supplier, costs in total: " + invoice.getTotalPrice());

        for(OrderItem oldOrderItem : invoice.getOrder().getOrderItems()) {
            boolean found = false;
            for (OrderItem newOrderItem : newOrderItems) {
                if(oldOrderItem.getProduct().getProductId().equals(newOrderItem.getProduct().getProductId())) {
                    found = true;
                    try {
						newOrderItem.setQuantity(newOrderItem.getQuantity()+oldOrderItem.getQuantity());
					} catch (DataModelException e) {
						LOGGER.error(e.getMessage(), e);
					}
                    actualOrderItems.add(newOrderItem);
                    break;
                }
            }
            if(!found) {
                actualOrderItems.add(oldOrderItem);
            }
        }
        oldExchange.getIn().setBody(actualOrderItems);
        return oldExchange;
    }

    private List<OrderItem> parseOrderItems(List<BasicDBObject> objects) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (Object object : objects) {
            try {
                orderItems.add(new ObjectMapper().readValue(object.toString(), OrderItem.class));
            } catch (IOException e) {
                LOGGER.error("Error parsing orderItem, cause " + e.getMessage());
            }
        }
        return orderItems;
    }
}
