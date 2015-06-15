package ac.at.tuwien.s2015.wmpm.g13.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.beans.IdGeneratorBean;
import ac.at.tuwien.s2015.wmpm.g13.model.DataModelException;

@Component
public class SOAPRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(SOAPRoute.class);

    private IdGeneratorBean idGeneratorBean;
    
    // CXF webservice using code first approach
    private String uri = "cxf:http://{{soap.jetty.host}}:{{soap.jetty.port}}/businessorder?serviceClass=ac.at.tuwien.s2015.wmpm.g13.services.soap.BusinessOrderService";

    @Autowired
    public SOAPRoute(IdGeneratorBean idGeneratorBean) {
    	this.idGeneratorBean = idGeneratorBean;
    }
    
    public void configure() throws Exception {
        LOGGER.debug("Configuring soap endpoint...");
        
        // DEFINE BEHAVIOR ON DATA MODEL PROBLEMS
        onException(DataModelException.class).handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/xml"))
                .setBody().simple("Invalid data values:\n${exception.message}");
        
        from(uri)
        	.to("direct:businessorder_soap");

        from("direct:businessorder_soap")
            .log("Received SOAP message with a business order")
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
            .bean(idGeneratorBean)
    		.inOnly("seda:confirmation-email.queue")
    		.to("direct:order_processing");
    }

}
