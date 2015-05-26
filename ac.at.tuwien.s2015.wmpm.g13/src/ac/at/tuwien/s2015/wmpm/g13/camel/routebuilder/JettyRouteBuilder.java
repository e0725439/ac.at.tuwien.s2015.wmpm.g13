package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.log4j.Logger;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.beans.OrderProcessBean;
import ac.at.tuwien.s2015.wmpm.g13.model.DataModelException;
import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;
import ac.at.tuwien.s2015.wmpm.g13.model.Product;
import ac.at.tuwien.s2015.wmpm.g13.model.SimpleOrder;
import ac.at.tuwien.s2015.wmpm.g13.model.person.LegalPerson;
import ac.at.tuwien.s2015.wmpm.g13.model.person.NaturalPerson;
import ac.at.tuwien.s2015.wmpm.g13.provider.db.DBProperty;
import ac.at.tuwien.s2015.wmpm.g13.provider.db.MongoConfigProvider;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@Component
public class JettyRouteBuilder extends RouteBuilder {

//	private static final Logger LOGGER = Logger.getLogger(JettyRouteBuilder.class);

	private OrderProcessBean orderProcessBean;

	@Autowired
	public JettyRouteBuilder(OrderProcessBean orderProcessBean) {
		this.orderProcessBean = orderProcessBean;
	}

	public void configure() {

		String wireTapRoute = "mongodb:myDb?database="
				+ MongoConfigProvider.getString(DBProperty.MONGO_DB_NAME)
				+ "&collection="
				+ MongoConfigProvider.getString(DBProperty.MONGO_DB_COLLECTION_PRODUCT)
				+ "&operation=insert";

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
				.wireTap(wireTapRoute)
				.inOnly("seda:confirmation-email.queue")
				.end();
	}
}
