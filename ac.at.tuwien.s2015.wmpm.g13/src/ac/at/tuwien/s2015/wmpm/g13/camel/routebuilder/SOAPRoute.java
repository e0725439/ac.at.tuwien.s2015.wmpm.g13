package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.beans.BusinessOrderProcessBean;
import ac.at.tuwien.s2015.wmpm.g13.provider.db.DBProperty;
import ac.at.tuwien.s2015.wmpm.g13.provider.db.MongoConfigProvider;

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
		String wireTapRoute = "mongodb:myDb?database=" 
				+ MongoConfigProvider.getString(DBProperty.MONGO_DB_NAME) 
				+ "&collection=" 
				+ MongoConfigProvider.getString(DBProperty.MONGO_DB_COLLECTION_BUSINESSORDER)
				+ "&operation=insert";
		
		LOGGER.debug("Configuring soap endpoint...");
		from(uri).setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201)).to("direct:businessorder_soap");
		
		from("direct:businessorder_soap").process(businessOrderProcessBean)
		.wireTap(wireTapRoute);
	}

}
