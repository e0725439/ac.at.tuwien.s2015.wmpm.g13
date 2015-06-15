package ac.at.tuwien.s2015.wmpm.g13.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.beans.IdGeneratorBean;
import ac.at.tuwien.s2015.wmpm.g13.model.DataModelException;
import ac.at.tuwien.s2015.wmpm.g13.model.order.SimpleOrder;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

@Component
public class RESTRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(RESTRoute.class);
    
    private IdGeneratorBean idGeneratorBean;
    
    @Autowired
    public RESTRoute(IdGeneratorBean idGeneratorBean) {
    	this.idGeneratorBean = idGeneratorBean;
    }
    

    public void configure() {

        LOGGER.debug("Starting Jetty server...");

        // define and add the jetty component
        restConfiguration().component("jetty")
                .host("{{rest.jetty.host}}")
                .port("{{rest.jetty.port}}")
                .bindingMode(RestBindingMode.auto);

        LOGGER.debug("Jetty server started succesfully.");

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
                .log("Received REST message with a simple order")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
                .bean(idGeneratorBean)
                .inOnly("seda:confirmation-email.queue")
                .to("direct:order_processing")
                .end();
    }
}
