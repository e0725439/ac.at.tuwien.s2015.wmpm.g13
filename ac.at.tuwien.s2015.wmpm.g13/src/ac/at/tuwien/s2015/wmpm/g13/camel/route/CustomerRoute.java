package ac.at.tuwien.s2015.wmpm.g13.camel.route;

import ac.at.tuwien.s2015.wmpm.g13.model.Invoice;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by mattias on 6/10/2015.
 */
@Component
public class CustomerRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(CustomerRoute.class);

    @Override
    public void configure() throws Exception {
        from("direct:supplier_receiveInvoice")
                .routeId("supplierReceiveInvoice")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        Invoice invoice = exchange.getIn().getBody(Invoice.class);
                        LOGGER.info("Received invoice with total costs of " + invoice.getTotalPrice()
                                + " and our " + invoice.getOrder().getOrderItems().size() + " products.");
                        LOGGER.info("Now sending the money back to the company");
                    }
                })
                .delay(2000)
                .to("direct:company_receivePayment");
    }
}
