package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.provider.db.JettyConfigProvider;
import ac.at.tuwien.s2015.wmpm.g13.provider.db.JettyProperty;

@Component
public class JettyComponent extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(JettyComponent.class);

    public void configure() {

        LOGGER.debug("Starting Jetty server...");

        // define and add the jetty component
        restConfiguration().component("jetty")
                .host(JettyConfigProvider.getString(JettyProperty.JETTY_HOST))
                .port(JettyConfigProvider.getString(JettyProperty.JETTY_PORT))
                .bindingMode(RestBindingMode.auto);

        LOGGER.debug("Jetty server started succesfully.");
    }

}
