package ac.at.tuwien.s2015.wmpm.g13.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.beans.ShipmentCompletionBean;

@Component
public class ShipmentCompletionRoute extends RouteBuilder {

	private ShipmentCompletionBean shipmentCompletionBean;

	@Autowired
	public ShipmentCompletionRoute(ShipmentCompletionBean shipmentCompletionBean) {
		this.shipmentCompletionBean = shipmentCompletionBean;
	}

	@Override
	public void configure() throws Exception {
		// Check every 30s if there are any open orders that can be shipped
		from("quartz2://shipmentCompletionTimer?cron=0/10+*+*+*+*+?")
				.routeId("shipmentCompletionProcess")
				.bean(shipmentCompletionBean).choice()
				.when(header("shipped").isEqualTo(true))
				.inOnly("seda:shipment-email.queue") // USE SEDA QUEUE
				.otherwise().end();
	}
}
