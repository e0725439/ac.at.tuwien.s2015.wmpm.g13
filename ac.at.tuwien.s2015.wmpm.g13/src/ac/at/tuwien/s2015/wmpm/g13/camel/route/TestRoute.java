package ac.at.tuwien.s2015.wmpm.g13.camel.route;

import ac.at.tuwien.s2015.wmpm.g13.beans.DatabaseMissingItemBean;
import ac.at.tuwien.s2015.wmpm.g13.beans.DatabaseOrderItemBean;
import ac.at.tuwien.s2015.wmpm.g13.beans.DatabaseProductBean;
import ac.at.tuwien.s2015.wmpm.g13.model.Product;
import ac.at.tuwien.s2015.wmpm.g13.model.order.OrderItem;
import ac.at.tuwien.s2015.wmpm.g13.model.order.SimpleOrder;
import ac.at.tuwien.s2015.wmpm.g13.model.person.LegalPerson;
import ac.at.tuwien.s2015.wmpm.g13.model.person.NaturalPerson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class TestRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger
            .getLogger(RESTRoute.class);
    private DatabaseProductBean dbProductBean;
    private DatabaseOrderItemBean dbOrderItemBean;
    private DatabaseMissingItemBean dbMissingItemBean;

    private String mongoCommandString = "mongodb:myDb?database={{mongo_db_name}}&operation=command";

    @Autowired
    public TestRoute(DatabaseProductBean dbProductBean, DatabaseOrderItemBean dbOrderItemBean, DatabaseMissingItemBean dbMissingItemBean) {
        this.dbProductBean = dbProductBean;
        this.dbOrderItemBean = dbOrderItemBean;
        this.dbMissingItemBean = dbMissingItemBean;
    }


    public void configure() {
        LOGGER.debug("Configuring TestRoute");

        rest("/services/rest").get("/test/simpleorder")
                .produces("application/json").to("direct:generate_restorder");

        from("direct:generate_restorder").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                SimpleOrder order = new SimpleOrder();

                NaturalPerson naturalPerson = new NaturalPerson();
                naturalPerson.setAddress("Street 23, Vienna 1050, Austria");
                naturalPerson.setEmail("max.muster@mail.com");
                naturalPerson.setFirstname("Max");
                naturalPerson.setLastname("Mustermann");
                naturalPerson.setPersonId("123");

                LegalPerson legalPerson = new LegalPerson();
                legalPerson.setAddress("Street 33, Linz 4040, Austria");
                legalPerson.setPersonId("999");
                legalPerson.setEmail("company@company.com");
                legalPerson.setName("mycompany");
                legalPerson.setCountryCourt("Linz");
                legalPerson.setRegistrationNumber("UAT123213");

                Product product = new Product();
                product.setName("Hammer");
                product.setPrice(4.99);
                product.setProductId("1");

                OrderItem item = new OrderItem();
                item.setProduct(product);
                item.setQuantity(2);

                List<OrderItem> orderItems = new ArrayList<>();
                orderItems.add(item);

                order.setCustomer(naturalPerson);
                order.setSupplier(legalPerson);
                order.setOrderDate(Calendar.getInstance().getTime());
                order.setSendDate(Calendar.getInstance().getTime());
                order.setOrderId("5464564");
                order.setOrderItems(orderItems);

                exchange.getOut().setBody(order);
            }

        }).marshal().json(JsonLibrary.Jackson);


        rest("/services/rest").get("/test/createdb")
                .produces("application/json").to("direct:generate_product");
        from("direct:generate_product")
                .bean(dbProductBean)
                .wireTap("mongodb:myDb?database={{mongo_db_name}}&collection={{mongo_db_collection_product}}&operation=insert")
                .to("direct:orderitem");
        from("direct:orderitem")
                .bean(dbOrderItemBean)
                .wireTap("mongodb:myDb?database={{mongo_db_name}}&collection={{mongo_db_collection_itemstock}}&operation=insert")
                .to("direct:missingitem");
        from("direct:missingitem")
                .bean(dbMissingItemBean)
                .wireTap("mongodb:myDb?database={{mongo_db_name}}&collection={{mongo_db_collection_itemmissing}}&operation=insert")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201));


        rest("/services/rest").get("/test/dropdb")
                .produces("application/json").to("direct:dropdb");
        from("direct:dropdb").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                DBObject commandBody = new BasicDBObject("drop", "wmpm.order.simple");
                exchange.getIn().setBody(commandBody);
            }
        }).to(mongoCommandString).process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                DBObject commandBody = new BasicDBObject("drop", "wmpm.order.business");
                exchange.getIn().setBody(commandBody);
            }
        }).to(mongoCommandString).process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                DBObject commandBody = new BasicDBObject("drop", "wmpm.order.logged");
                exchange.getIn().setBody(commandBody);
            }
        }).to(mongoCommandString).process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                DBObject commandBody = new BasicDBObject("drop", "wmpm.item.stock");
                exchange.getIn().setBody(commandBody);
            }
        }).to(mongoCommandString).process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                DBObject commandBody = new BasicDBObject("drop", "wmpm.item.missing");
                exchange.getIn().setBody(commandBody);
            }
        }).to(mongoCommandString).process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                DBObject commandBody = new BasicDBObject("drop", "wmpm.product");
                exchange.getIn().setBody(commandBody);
            }
        }).to(mongoCommandString).to(mongoCommandString).process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                DBObject commandBody = new BasicDBObject("drop", "wmpm.invoice");
                exchange.getIn().setBody(commandBody);
            }
        }).to(mongoCommandString).to(mongoCommandString).process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                DBObject commandBody = new BasicDBObject("drop", "wmpm.shipping");
                exchange.getIn().setBody(commandBody);
            }
        }).to(mongoCommandString);
    }

}
