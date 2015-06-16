package ac.at.tuwien.s2015.wmpm.g13.camel.route;


import ac.at.tuwien.s2015.wmpm.g13.beans.BusinessConfirmationEmailBean;
import ac.at.tuwien.s2015.wmpm.g13.beans.ConfirmationEmailBean;
import ac.at.tuwien.s2015.wmpm.g13.beans.ShipmentEmailBean;

import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConfirmationRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(OrderConfirmationRoute.class);

    private ConfirmationEmailBean confirmationEmailBean;

    private BusinessConfirmationEmailBean businessConfirmationEmailBean;
    
    private ShipmentEmailBean shipmentEmailBean;

    @Autowired
    public OrderConfirmationRoute(ConfirmationEmailBean confirmationEmailBean, BusinessConfirmationEmailBean businessConfirmationEmailBean, ShipmentEmailBean shipmentEmailBean) {
        this.confirmationEmailBean = confirmationEmailBean;
        this.businessConfirmationEmailBean = businessConfirmationEmailBean;
        this.shipmentEmailBean = shipmentEmailBean;
    }

    @Override
    public void configure() throws Exception {
        LOGGER.debug("Processing email confirmation.");

        Predicate isSimpleOrder = (Predicate) simple("${in.body.getClass} == 'ac.at.tuwien.s2015.wmpm.g13.model.order.SimpleOrder'");

        // ORDER CONFIRMATIONS
        from("seda:confirmation-email.queue").routeId("EmailConfirmation")
    	.choice()	
    		.when(isSimpleOrder)
            	.bean(confirmationEmailBean)
                .wireTap("mongodb:myDb?database={{mongo_db_name}}&collection={{mongo_db_collection_loggedorder}}&operation=insert")
				.inOnly("mongodb:myDb?database={{mongo_db_name}}&collection={{mongo_db_collection_simpleorder}}&operation=insert")
				.endChoice()
            .otherwise()
            	.bean(businessConfirmationEmailBean)
                .wireTap("mongodb:myDb?database={{mongo_db_name}}&collection={{mongo_db_collection_loggedorder}}&operation=insert")
                .inOnly("mongodb:myDb?database={{mongo_db_name}}&collection={{mongo_db_collection_businessorder}}&operation=insert")
                .endChoice();

        // SHIPMENT CONFIRMATIONS
        from("seda:shipment-email.queue").routeId("EmailShipment")
        	.bean(shipmentEmailBean)
        	.wireTap("mongodb:myDb?database={{mongo_db_name}}&collection={{mongo_db_collection_loggedshipments}}&operation=insert");
    }

}
