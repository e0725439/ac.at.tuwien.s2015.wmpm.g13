package ac.at.tuwien.s2015.wmpm.g13.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.Mongo;

import ac.at.tuwien.s2015.wmpm.g13.model.SimpleOrder;

@Component
public class OrderProcessBean implements Processor{

	private static final Logger LOGGER = Logger.getLogger(OrderProcessBean.class);
	
	private Mongo myDb;
	
	@Autowired
	public OrderProcessBean(Mongo myDb) {
		this.myDb = myDb;
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
				SimpleOrder body = exchange.getIn().getBody(
						SimpleOrder.class);
				LOGGER.debug("Received message with order: " + body);		
	}
}
