package ac.at.tuwien.s2015.wmpm.g13.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.beans.OrderProcessBean;

/**
 * Route for the daily supplier process for providing the missing orderItems
 * Created by mattias on 5/25/2015.
 */
@Component
public class OrderRoute extends RouteBuilder {

	private OrderProcessBean orderProcessBean;

	@Autowired
	public OrderRoute(OrderProcessBean orderProcessBean) {
		this.orderProcessBean = orderProcessBean;
	}

	@Override
	public void configure() throws Exception {

		from("direct:order_processing").routeId("OrderProcessing")
			.log("Processing order now...")
				.bean(orderProcessBean).choice()
				.when(header("shipped").isEqualTo(true))
				.inOnly("seda:shipment-email.queue") // USE SEDA QUEUE
				.otherwise().end();
	}
}
