package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class JettyComponent extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(JettyComponent.class);

    public void configure() {

        LOGGER.debug("Starting Jetty server...");

        // define and add the jetty component
        restConfiguration().component("jetty")
                .host("{{jetty_host}}")
                .port("{{jetty_port}}")
                .bindingMode(RestBindingMode.auto);

        LOGGER.debug("Jetty server started succesfully.");
    }

}
