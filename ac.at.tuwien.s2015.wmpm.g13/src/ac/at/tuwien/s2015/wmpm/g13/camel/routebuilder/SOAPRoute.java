package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.beans.BusinessOrderProcessBean;

@Component
public class SOAPRoute extends RouteBuilder {

	private static final Logger LOGGER = Logger.getLogger(SOAPRoute.class);

	private BusinessOrderProcessBean businessOrderProcessBean;
	
	// CXF webservice using code first approach
	private String uri = "cxf:http://localhost:8282/businessorder?serviceClass=ac.at.tuwien.s2015.wmpm.g13.services.soap.BusinessOrderService";
	
	@Autowired
	public SOAPRoute(BusinessOrderProcessBean businessOrderProcessBean) {
		this.businessOrderProcessBean = businessOrderProcessBean;
	}
	
	public void configure() throws Exception {
		LOGGER.debug("Configuring soap endpoint...");
		from(uri).to("direct:businessorder_soap");
		
		from("direct:businessorder_soap").process(businessOrderProcessBean)
		.wireTap("mongodb:myDb?database=wmpm_master&collection=wmpm.businessorders.received&operation=insert");
	}

}
