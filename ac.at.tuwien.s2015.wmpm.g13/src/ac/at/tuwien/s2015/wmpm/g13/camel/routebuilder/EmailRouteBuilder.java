package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.beans.ConfirmationEmailBean;

@Component
public class EmailRouteBuilder extends RouteBuilder {
	
	private static final Logger LOGGER = Logger.getLogger(EmailRouteBuilder.class);
	
	private ConfirmationEmailBean confirmationEmailBean;
	
	@Autowired
	public EmailRouteBuilder(ConfirmationEmailBean confirmationEmailBean) {
		this.confirmationEmailBean = confirmationEmailBean;
	}
	
	@Override
	public void configure() throws Exception {
		LOGGER.debug("Processing email confirmation.");
		from("seda:confirmation-email.queue").process(confirmationEmailBean);

	}

}
