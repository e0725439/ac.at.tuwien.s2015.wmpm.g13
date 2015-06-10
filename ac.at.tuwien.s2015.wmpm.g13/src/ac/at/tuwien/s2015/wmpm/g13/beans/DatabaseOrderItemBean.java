package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;
import ac.at.tuwien.s2015.wmpm.g13.model.Product;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by e1025735 on 21.05.15.
 */
@Component
public class DatabaseOrderItemBean {
    private static final Logger LOGGER = Logger.getLogger(OrderProcessBean.class);

    public DatabaseOrderItemBean() {
    }

    @Handler
    public void process(Exchange exchange) throws Exception {
        ArrayList<OrderItem> orderItems = new ArrayList<>();

        List<Product> products = exchange.getIn().getBody(List.class);

        OrderItem orderItem1 = new OrderItem(products.get(0), 1);
        //orderItem 2 is missing on purpose, because it gets in the missing Item collection
        OrderItem orderItem3 = new OrderItem(products.get(2), 2);
        OrderItem orderItem4 = new OrderItem(products.get(3), 5);
        OrderItem orderItem5 = new OrderItem(products.get(4), 7);
        OrderItem orderItem6 = new OrderItem(products.get(5), 2);
        OrderItem orderItem7 = new OrderItem(products.get(6), 9);
        OrderItem orderItem8 = new OrderItem(products.get(7), 4);
        OrderItem orderItem9 = new OrderItem(products.get(8), 8);
        OrderItem orderItem10 = new OrderItem(products.get(9), 9);
        OrderItem orderItem11 = new OrderItem(products.get(10), 11);
        OrderItem orderItem12 = new OrderItem(products.get(11), 3);
        OrderItem orderItem13 = new OrderItem(products.get(12), 6);
        OrderItem orderItem14 = new OrderItem(products.get(13), 7);
        OrderItem orderItem15 = new OrderItem(products.get(14), 2);
        OrderItem orderItem16 = new OrderItem(products.get(15), 3);

        orderItems.add(orderItem1);
        orderItems.add(orderItem3);
        orderItems.add(orderItem4);
        orderItems.add(orderItem5);
        orderItems.add(orderItem6);
        orderItems.add(orderItem7);
        orderItems.add(orderItem8);
        orderItems.add(orderItem9);
        orderItems.add(orderItem10);
        orderItems.add(orderItem11);
        orderItems.add(orderItem12);
        orderItems.add(orderItem13);
        orderItems.add(orderItem14);
        orderItems.add(orderItem15);
        orderItems.add(orderItem16);
        exchange.getIn().setBody(orderItems);
    }
}
