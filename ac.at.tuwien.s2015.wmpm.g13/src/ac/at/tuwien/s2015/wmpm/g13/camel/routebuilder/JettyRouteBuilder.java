package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import ac.at.tuwien.s2015.wmpm.g13.beans.OrderItemEnricherBean;
import ac.at.tuwien.s2015.wmpm.g13.beans.SupplierOrderItemsBean;
import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.beans.OrderProcessBean;
import ac.at.tuwien.s2015.wmpm.g13.model.order.SimpleOrder;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@Component
public class JettyRouteBuilder extends RouteBuilder {

	private static final Logger LOGGER = Logger
			.getLogger(JettyRouteBuilder.class);
	
	private OrderProcessBean orderProcessBean;
	private SupplierOrderItemsBean supplierOrderItemsBean;
	private OrderItemEnricherBean orderItemEnricherBean;

	@Autowired
	public JettyRouteBuilder(OrderProcessBean orderProcessBean, SupplierOrderItemsBean supplierOrderItemsBean, OrderItemEnricherBean orderItemEnricherBean) {
		this.orderProcessBean = orderProcessBean;
		this.supplierOrderItemsBean = supplierOrderItemsBean;
		this.orderItemEnricherBean = orderItemEnricherBean;
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

		// Daily FacebookProcess
		from("quartz2://facebookTimer/cron=0/10+*+*+*+*+?").routeId("cronFacebookProcess")
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						LOGGER.info("Writing facebook special coupon on the powermaterials wall");
					}
				});

		// Daily SupplierProcess
		from("quartz2://supplierTimer/cron=0/10+*+*+*+*+?").routeId("cronSupplierProcess")
				.to("mongodb:myDb?database=wmpm_mattias&collection=wmpm.company.missingOrderItems&operation=findAll")
//						.convertBodyTo(OrderItem[].class)
//				.wireTap("mongodb:myDb?database=wmpm_mattias&collection=wmpm.company.missingOrderItems&operation=remove")
				.to("direct:supplier_missingOrderItems");

		from("direct:supplier_missingOrderItems")
				.delay(3000)
				.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
				.process(supplierOrderItemsBean)
				.to("direct:company_receiveMissingSimpleOrder");

		from("direct:company_receiveMissingSimpleOrder")
				.split(body()).parallelProcessing()
				.to("direct:supplier_receiveInvoice")
				.to("direct:company_putOrderItems")
				.end();

		from("direct:supplier_receiveInvoice")
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						LOGGER.info("Supplier got the paid invoice, done with the action");
					}
				});

		from("direct:company_putOrderItems")
				.enrich("mongodb:myDb?database=wmpm_mattias&collection=wmpm.company.orderItems&operation=findAll", orderItemEnricherBean)
				.wireTap("mongodb:myDb?database=wmpm_mattias&collection=wmpm.company.orderItems&operation=update");
	}
}
