package ac.at.tuwien.s2015.wmpm.g13.camel.route;

import ac.at.tuwien.s2015.wmpm.g13.beans.FacebookBean;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Route for the daily facebook offering newsfeed post
 * Created by mattias on 5/25/2015.
 */
@Component
public class FacebookRoute extends RouteBuilder {

    private FacebookBean facebookBean;

    @Autowired
    public FacebookRoute(FacebookBean facebookBean) {
        this.facebookBean = facebookBean;
    }

    @Override
    public void configure() throws Exception {
        // Daily FacebookProcess
        from("quartz2://facebookTimer?cron=*+0/5+*+*+*+?").routeId("CronFacebookProcess")
                .to("mongodb:myDb?database={{mongo_db_name}}&collection={{mongo_db_collection_itemstock}}&operation=findAll")
                .bean(facebookBean)
                .recipientList(header("recipient"))
                .log("Special Order now sent to facebook wall");
    }
}
