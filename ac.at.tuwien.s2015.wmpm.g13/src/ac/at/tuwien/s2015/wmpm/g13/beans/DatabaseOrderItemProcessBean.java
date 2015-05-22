package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;
import ac.at.tuwien.s2015.wmpm.g13.model.Product;
import com.mongodb.Mongo;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by e1025735 on 21.05.15.
 */
@Component
public class DatabaseOrderItemProcessBean implements Processor {
    private static final Logger LOGGER = Logger.getLogger(OrderProcessBean.class);

    private Mongo myDb;

    @Autowired
    public DatabaseOrderItemProcessBean(Mongo myDb) {
        this.myDb = myDb;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        ArrayList<OrderItem> orderItems = new ArrayList<>();

        List<Product> products = exchange.getIn().getBody(List.class);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProduct(products.get(0));
        orderItem1.setQuantity(400);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setProduct(products.get(1));
        orderItem2.setQuantity(175);

        OrderItem orderItem3 = new OrderItem();
        orderItem3.setProduct((products.get(2)));
        orderItem3.setQuantity(324);

        OrderItem orderItem4 = new OrderItem();
        orderItem4.setProduct(products.get(3));
        orderItem4.setQuantity(98);

        OrderItem orderItem5 = new OrderItem();
        orderItem5.setProduct(products.get(4));
        orderItem5.setQuantity(623);

        OrderItem orderItem6 = new OrderItem();
        orderItem6.setProduct(products.get(5));
        orderItem6.setQuantity(71);

        OrderItem orderItem7 = new OrderItem();
        orderItem7.setProduct(products.get(6));
        orderItem7.setQuantity(213);

        OrderItem orderItem8 = new OrderItem();
        orderItem8.setProduct(products.get(7));
        orderItem8.setQuantity(82);

        OrderItem orderItem9 = new OrderItem();
        orderItem9.setProduct(products.get(8));
        orderItem9.setQuantity(145);

        orderItems.add(orderItem1);
        orderItems.add(orderItem2);
        orderItems.add(orderItem3);
        orderItems.add(orderItem4);
        orderItems.add(orderItem5);
        orderItems.add(orderItem6);
        orderItems.add(orderItem7);
        orderItems.add(orderItem8);
        orderItems.add(orderItem9);
        exchange.getIn().setBody(orderItems);
    }
}
