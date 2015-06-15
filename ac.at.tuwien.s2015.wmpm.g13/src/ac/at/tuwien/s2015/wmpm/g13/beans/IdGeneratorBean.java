package ac.at.tuwien.s2015.wmpm.g13.beans;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.model.order.Order;
import ac.at.tuwien.s2015.wmpm.g13.provider.db.IDProvider;

@Component
public class IdGeneratorBean {

	private static final Logger LOGGER = Logger.getLogger(IdGeneratorBean.class);

	@Handler
	public void process(@Body Order order) throws Exception {
		LOGGER.info("Generating ID for order.");
		order.setOrderId(IDProvider.generateID());
	}
	
}
