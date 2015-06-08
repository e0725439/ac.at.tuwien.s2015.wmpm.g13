package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by josef on 6/2/2015.
 */
@Component
public class FacebookBean {

    private static final Logger LOGGER = Logger.getLogger(FacebookBean.class);
    @Value("${facebook.token}")
    private String token;
    @Value("${facebook.id}")
    private String id;
    @Value("${facebook.secret}")
    private String secret;

    @Handler
    public void process(Exchange exchange) throws Exception {
        List<OrderItem> orderItems = parseOrderItems(exchange.getIn().getBody(List.class));
        BasicDBObject facebookProduct = null;
        int quantity = 0;
        String message = "No special order this week available";

        for (OrderItem orderItem : orderItems) {
            if (orderItem.getQuantity() > quantity) {
                facebookProduct = orderItem.getProduct();
                quantity = orderItem.getQuantity();
            }
        }

        if (facebookProduct != null) {
            message = "Special order for our customers, our Product: " + facebookProduct.get("name")
                    + " is now available with a special discount of 20 percent, with price per product "
                    + String.format("%.2f", (double) facebookProduct.get("price") * 0.8);
        }

        String facebookEndpoint = "facebook://postStatusMessage?"
                + "message=" + URLEncoder.encode(message, "UTF-8")
                + "&oAuthAccessToken=" + token
                + "&oAuthAppId=" + id
                + "&oAuthAppSecret=" + secret;
        exchange.getIn().setHeader("recipient", facebookEndpoint);
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
