package ac.at.tuwien.s2015.wmpm.g13.beans;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.model.Product;


/**
 * Created by e1025735 on 21.05.15.
 */
@Component
public class DatabaseProductProcessBean{
    private static final Logger LOGGER = Logger.getLogger(OrderProcessBean.class);

    public DatabaseProductProcessBean() {
    }

    @Handler
    public void process(Exchange exchange) throws Exception {
    	
    	LOGGER.debug("Processing DatabaseProductProcessBean");
    	
        ArrayList<Product> products = new ArrayList<>();
        Product product1 = new Product("1", "Hammer", 4.99);
        Product product2 = new Product("2", "Schraubenzieher Set 6 teilig ", 19.99);
        Product product3 = new Product("3", "Holzaxt", 13.99);
        Product product4 = new Product("4", "Werkzeugkoffer", 48.99);
        Product product5 = new Product("5", "Arbeitsguertel", 16.99);
        Product product6 = new Product("6", "Akkubohrer", 49.99);
        Product product7 = new Product("7", "Flachzange", 24.99);
        Product product8 = new Product("8", "Baumsaege", 9.49);
        Product product9 = new Product("9", "Metallsaege", 13.49);
        Product product10 = new Product("10", "Wandfarbe weiss, matt 10L", 49.99);
        Product product11 = new Product("11", "Rohrzange", 8.49);
        Product product12 = new Product("12", "Sackkarre", 39.99);
        Product product13 = new Product("13", "Vorhangschloss mit 2 Schluessel", 11.79);
        Product product14 = new Product("14", "Zahlenvorhangschloss", 14.39);
        Product product15 = new Product("15", "Gewebeband 50cm x 50m", 7.15);
        Product product16 = new Product("16", "Schmierspray 400ml", 9.99);

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);
        products.add(product8);
        products.add(product9);
        products.add(product10);
        products.add(product11);
        products.add(product12);
        products.add(product13);
        products.add(product14);
        products.add(product15);
        products.add(product16);
        exchange.getIn().setBody(products);
    }
}
