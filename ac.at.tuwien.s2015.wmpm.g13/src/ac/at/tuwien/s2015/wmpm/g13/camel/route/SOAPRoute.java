package ac.at.tuwien.s2015.wmpm.g13.camel.route;

import ac.at.tuwien.s2015.wmpm.g13.beans.BusinessOrderProcessBean;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SOAPRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(SOAPRoute.class);

    private BusinessOrderProcessBean businessOrderProcessBean;

    // CXF webservice using code first approach
    private String uri = "cxf:http://{{soap.jetty.host}}:{{soap.jetty.port}}/businessorder?serviceClass=ac.at.tuwien.s2015.wmpm.g13.services.soap.BusinessOrderService";

    @Autowired
    public SOAPRoute(BusinessOrderProcessBean businessOrderProcessBean) {
        this.businessOrderProcessBean = businessOrderProcessBean;
    }

    public void configure() throws Exception {
        LOGGER.debug("Configuring soap endpoint...");
        from(uri)
        	.to("direct:businessorder_soap");

        from("direct:businessorder_soap")
    		.bean(businessOrderProcessBean)
    		.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
    		.to("seda:confirmation-email.queue");
//    		.end();
    }

}
