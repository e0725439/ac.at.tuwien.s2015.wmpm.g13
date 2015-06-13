package ac.at.tuwien.s2015.wmpm.g13.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SOAPRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(SOAPRoute.class);

    // CXF webservice using code first approach
    private String uri = "cxf:http://{{soap.jetty.host}}:{{soap.jetty.port}}/businessorder?serviceClass=ac.at.tuwien.s2015.wmpm.g13.services.soap.BusinessOrderService";

    public void configure() throws Exception {
        LOGGER.debug("Configuring soap endpoint...");
        from(uri)
        	.to("direct:businessorder_soap");

        from("direct:businessorder_soap")
            .log("Received SOAP message with a business order")
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
    		.inOnly("seda:confirmation-email.queue")
    		.to("direct:order_processing");
    }

}
