package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import ac.at.tuwien.s2015.wmpm.g13.beans.FacebookProcessorBean;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Route for the daily facebook offering newsfeed post
 * Created by mattias on 5/25/2015.
 */
@Component
public class DailyFacebookRouteBuilder extends RouteBuilder {

    private static final Logger LOGGER = Logger
            .getLogger(DailyFacebookRouteBuilder.class);
    private FacebookProcessorBean facebookProcessorBean;

    @Autowired
    public DailyFacebookRouteBuilder(FacebookProcessorBean facebookProcessorBean) {
        this.facebookProcessorBean = facebookProcessorBean;
    }

    @Override
    public void configure() throws Exception {
        // Daily FacebookProcess
        from("quartz2://facebookTimer/cron=*+1+*+*+*+?").routeId("cronFacebookProcess")
                .to("mongodb:myDb?database=wmpm_mattias&collection=wmpm.item.stock&operation=findAll")
                .process(facebookProcessorBean)
                .recipientList(header("recipient"))
                .log("Special Order now sent to facebook wall");
    }
}
