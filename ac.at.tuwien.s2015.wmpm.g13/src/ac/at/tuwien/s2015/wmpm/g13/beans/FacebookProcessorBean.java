package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;
import ac.at.tuwien.s2015.wmpm.g13.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by josef on 6/2/2015.
 */
@Component
public class FacebookProcessorBean implements Processor{

    private static final Logger LOGGER = Logger.getLogger(FacebookProcessorBean.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        List<OrderItem> orderItems = parseOrderItems(exchange.getIn().getBody(List.class));
        Product facebookProduct = null;
        int quantity = 0;
        String message = "No special order this week available";

        for(OrderItem orderItem : orderItems) {
            if(orderItem.getQuantity() > quantity) {
                facebookProduct = orderItem.getProduct();
                quantity = orderItem.getQuantity();
            }
        }

        if(facebookProduct != null) {
            message = "Special order for our customers, our Product: " + facebookProduct.getName() +
                    " is now available with a special discount -20%- for " + (facebookProduct.getPrice()*0.8);

        }

        String facebookEndpoint = "facebook://postStatusMessage?"
                + "message="+ message
                + "&oAuthAccessToken={{FBAuthAccessToken}}"
                + "&oAuthAppId={{FBAuthAppId}}"
                + "&oAuthAppSecret={{FBAuthAppSecret}}";
        exchange.getIn().setHeader("recipient",facebookEndpoint);
    }

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
}
