package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;

/**
 * Route for the daily facebook offering newsfeed post
 * Created by mattias on 5/25/2015.
 */
public class DailyFacebookRouteBuilder extends RouteBuilder {

    private static final Logger LOGGER = Logger
            .getLogger(DailyFacebookRouteBuilder.class);

    @Override
    public void configure() throws Exception {
        // Daily FacebookProcess
        from("quartz2://facebookTimer/cron=0/10+*+*+*+*+?").routeId("cronFacebookProcess")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        LOGGER.info("Writing facebook special coupon on the powermaterials wall");
                    }
                });
    }
}
