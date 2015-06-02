package ac.at.tuwien.s2015.wmpm.g13.beans;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.model.SimpleOrder;

@Component
public class OrderProcessBean{

	private static final Logger LOGGER = Logger.getLogger(OrderProcessBean.class);
	
	@Handler
	public void process(@Body SimpleOrder order) throws Exception {

		LOGGER.debug("Received message with order: " + order);		
	}
}
