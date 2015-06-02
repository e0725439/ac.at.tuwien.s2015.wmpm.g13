package ac.at.tuwien.s2015.wmpm.g13.beans;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.model.BusinessOrder;

@Component
public class BusinessOrderProcessBean{

    private static final Logger LOGGER = Logger.getLogger(BusinessOrderProcessBean.class);

    @Handler
    public void process(@Body BusinessOrder order) throws Exception {
        LOGGER.debug("Business Order was received: ");
        LOGGER.debug(order);
    }

}
