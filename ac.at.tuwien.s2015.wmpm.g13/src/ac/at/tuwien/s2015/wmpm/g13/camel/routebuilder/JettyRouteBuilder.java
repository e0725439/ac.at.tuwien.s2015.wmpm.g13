package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.log4j.Logger;

import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;
import ac.at.tuwien.s2015.wmpm.g13.model.Product;
import ac.at.tuwien.s2015.wmpm.g13.model.SimpleOrder;
import ac.at.tuwien.s2015.wmpm.g13.model.person.LegalPerson;
import ac.at.tuwien.s2015.wmpm.g13.model.person.NaturalPerson;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

public class JettyRouteBuilder extends RouteBuilder {

	private static final Logger LOGGER = Logger
			.getLogger(JettyRouteBuilder.class);

	public void configure() {

		LOGGER.debug("Starting Jetty server...");

		// define and add the jetty component
		restConfiguration().component("jetty").host("localhost").port(8181)
				.bindingMode(RestBindingMode.auto);

		LOGGER.debug("Jetty server started succesfully.");

		// define error behaviour
		onException(UnrecognizedPropertyException.class).handled(true)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
				.setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
				.setBody().constant("Invalid json data");

		rest("/services/rest").put("/simpleorder").consumes("application/json")
				.type(SimpleOrder.class).produces("text/html")
				.to("direct:order_put");

		from("direct:order_put")
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						SimpleOrder body = exchange.getIn().getBody(
								SimpleOrder.class);
						LOGGER.debug("Received message with order: " + body);
					}
				})
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
				.wireTap("mongodb:myDb?database=wmpm_test1&collection=wmpm.orders.received&operation=insert");

		// TEST ORDER CREATION AND SERVICE
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
				product.setPrice(new BigDecimal("10"));
				product.setProductId("10023");

				OrderItem item = new OrderItem();
				item.setProduct(product);
				item.setQuantity(2);

				List<OrderItem> orderItems = new ArrayList<OrderItem>();
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
	}

}
