package ac.at.tuwien.s2015.wmpm.g13.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class BusinessOrderProcessBean implements Processor {

	private static final Logger LOGGER = Logger.getLogger(BusinessOrderProcessBean.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		LOGGER.debug("Received something...");
		System.err.println("\n\n\n#########\n\n\n#########\n\n\n#########");
	}

}
