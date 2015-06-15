package ac.at.tuwien.s2015.wmpm.g13.beans;

import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.model.Product;
import ac.at.tuwien.s2015.wmpm.g13.model.order.OrderItem;


/**
 * Created by e1025735 on 21.05.15.
 */
@Component
public class DatabaseMissingItemBean {

    public DatabaseMissingItemBean() {
    }

    @Handler
    public void process(Exchange exchange) throws Exception {
        ArrayList<OrderItem> missingItems = new ArrayList<>();

        Product product = new Product("2", "Schraubenzieher", 19.99);
        OrderItem orderItem1 = new OrderItem(product, 3);

        missingItems.add(orderItem1);
        exchange.getIn().setBody(missingItems);
    }
}
