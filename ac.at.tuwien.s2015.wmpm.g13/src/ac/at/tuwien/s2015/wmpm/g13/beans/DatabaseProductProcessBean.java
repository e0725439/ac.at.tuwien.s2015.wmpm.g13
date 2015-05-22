package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.Product;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;


/**
 * Created by e1025735 on 21.05.15.
 */
@Component
public class DatabaseProductProcessBean implements Processor {
    private static final Logger LOGGER = Logger.getLogger(OrderProcessBean.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        ArrayList<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("1");
        product1.setName("Hammer");
        product1.setPrice(new BigDecimal("4.99"));

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setName("Schraubenzieher Set 6 teilig ");
        product2.setPrice(new BigDecimal("19.99"));

        Product product3 = new Product();
        product3.setProductId("3");
        product3.setName("Holzaxt");
        product3.setPrice(new BigDecimal("13.99"));

        Product product4 = new Product();
        product4.setProductId("4");
        product4.setName("Werkzeugkoffer");
        product4.setPrice(new BigDecimal("48.99"));

        Product product5 = new Product();
        product5.setProductId("5");
        product5.setName("Arbeitsgürtel");
        product5.setPrice(new BigDecimal("16.99"));

        Product product6 = new Product();
        product6.setProductId("6");
        product6.setName("Akkubohrer");
        product6.setPrice(new BigDecimal("49.99"));

        Product product7 = new Product();
        product7.setProductId("7");
        product7.setName("Flachzange");
        product7.setPrice(new BigDecimal("24.99"));

        Product product8 = new Product();
        product8.setProductId("8");
        product8.setName("Baumsäge");
        product8.setPrice(new BigDecimal("9.49"));

        Product product9 = new Product();
        product9.setProductId("9");
        product9.setName("Metallsäge");
        product9.setPrice(new BigDecimal("13.49"));

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);
        products.add(product8);
        products.add(product9);
        exchange.getIn().setBody(products);
    }
}
