package ac.at.tuwien.s2015.wmpm.g13.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.model.BusinessOrder;

@Component
public class BusinessOrderProcessBean implements Processor {

	private static final Logger LOGGER = Logger.getLogger(BusinessOrderProcessBean.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		LOGGER.debug("Business Order was received: ");
		BusinessOrder order = exchange.getIn().getBody(BusinessOrder.class);
		LOGGER.debug(order);	
	}

}
