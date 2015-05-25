package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import ac.at.tuwien.s2015.wmpm.g13.beans.OrderProcessBean;
import ac.at.tuwien.s2015.wmpm.g13.model.order.SimpleOrder;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JettyRouteBuilder extends RouteBuilder {

	private static final Logger LOGGER = Logger
			.getLogger(JettyRouteBuilder.class);
	
	private OrderProcessBean orderProcessBean;

	@Autowired
	public JettyRouteBuilder(OrderProcessBean orderProcessBean) {
		this.orderProcessBean = orderProcessBean;
	}
	
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
				.type(SimpleOrder.class).produces("application/json")
				.to("direct:order_put");

		from("direct:order_put")
				.process(orderProcessBean)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
				.wireTap("mongodb:myDb?database=wmpm_mattias&collection=wmpm.company.receivedOrders&operation=insert");
	}
}
