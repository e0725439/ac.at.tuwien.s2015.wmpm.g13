package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.services.soap.BusinessOrderServiceImpl;

@Component
public class SOAPRoute extends RouteBuilder {

	private static final Logger LOGGER = Logger.getLogger(SOAPRoute.class);

	// CXF webservice using code first approach
	private String uri = "cxf:businessorder?serviceClass=" + BusinessOrderServiceImpl.class.getName();
	
	@Override
	public void configure() throws Exception {
		System.err.println("\n\n\nCONFIGURING SOAP ENDPOINT\n\n\n");
		
		from(uri).process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				LOGGER.debug("Received something...");
			}
		});

	}

}
