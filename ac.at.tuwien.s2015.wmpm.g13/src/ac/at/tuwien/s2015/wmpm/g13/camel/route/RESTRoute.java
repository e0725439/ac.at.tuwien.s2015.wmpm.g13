package ac.at.tuwien.s2015.wmpm.g13.camel.route;

import ac.at.tuwien.s2015.wmpm.g13.beans.OrderProcessBean;
import ac.at.tuwien.s2015.wmpm.g13.model.DataModelException;
import ac.at.tuwien.s2015.wmpm.g13.model.SimpleOrder;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RESTRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(RESTRoute.class);

    private OrderProcessBean orderProcessBean;

    @Autowired
    public RESTRoute(OrderProcessBean orderProcessBean) {
        this.orderProcessBean = orderProcessBean;
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
                .bean(orderProcessBean)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
                .inOnly("seda:confirmation-email.queue")
                .end();
    }
}
