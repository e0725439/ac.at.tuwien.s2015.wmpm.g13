package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.beans.OrderProcessBean;
import ac.at.tuwien.s2015.wmpm.g13.model.DataModelException;
import ac.at.tuwien.s2015.wmpm.g13.model.SimpleOrder;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@Component
public class JettyRouteBuilder extends RouteBuilder {

	private static final Logger LOGGER = Logger.getLogger(JettyRouteBuilder.class);

	private OrderProcessBean orderProcessBean;

	@Autowired
	public JettyRouteBuilder(OrderProcessBean orderProcessBean) {
		this.orderProcessBean = orderProcessBean;
	}

	public void configure() {
		
		LOGGER.debug("Configuring JettyRouteBuilder...");
		
		// DEFINE BEHAVIOR ON JSON SCHEMA PROBLEMS
		onException(UnrecognizedPropertyException.class).handled(true)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
				.setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
				.setBody().constant("Invalid json data");

		// DEFINE BEHAVIOR ON DATA MODEL PROBLEMS
		onException(DataModelException.class).handled(true)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
				.setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
				.setBody().simple("Invalid data values:\n${exception.message}");

		rest("/services/rest").put("/simpleorder").consumes("application/json")
				.type(SimpleOrder.class).produces("text/html")
				.to("direct:order_put");

		from("direct:order_put")
				.process(orderProcessBean)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
				.wireTap("mongodb:myDb?database={{mongo_db_name}}&collection={{mongo_db_collection_simpleorder}}&operation=insert")
				.inOnly("seda:confirmation-email.queue")
				.end();
	}
}
