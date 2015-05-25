package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.SimpleOrder;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessBean implements Processor{

	private static final Logger LOGGER = Logger.getLogger(OrderProcessBean.class);

	@Override
	public void process(Exchange exchange) throws Exception {
				SimpleOrder body = exchange.getIn().getBody(
						SimpleOrder.class);
				LOGGER.debug("Received message with order: " + body);		
	}
}
