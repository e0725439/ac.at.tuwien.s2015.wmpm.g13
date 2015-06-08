package ac.at.tuwien.s2015.wmpm.g13.camel.route;


import ac.at.tuwien.s2015.wmpm.g13.beans.BusinessConfirmationEmailBean;
import ac.at.tuwien.s2015.wmpm.g13.beans.ConfirmationEmailBean;
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

    @Autowired
    public OrderConfirmationRoute(ConfirmationEmailBean confirmationEmailBean, BusinessConfirmationEmailBean businessConfirmationEmailBean) {
        this.confirmationEmailBean = confirmationEmailBean;
        this.businessConfirmationEmailBean = businessConfirmationEmailBean;
    }

    @Override
    public void configure() throws Exception {
        LOGGER.debug("Processing email confirmation.");

        Predicate isSimpleOrder = (Predicate) simple("${in.body.getClass} == 'ac.at.tuwien.s2015.wmpm.g13.model.SimpleOrder'");

        from("seda:confirmation-email.queue")
    	.choice()	
    		.when(isSimpleOrder)
            	.bean(confirmationEmailBean)
                .wireTap("mongodb:myDb?database={{mongo_db_name}}&collection={{mongo_db_collection_loggedorder}}&operation=insert")
				.to("mongodb:myDb?database={{mongo_db_name}}&collection={{mongo_db_collection_simpleorder}}&operation=insert")
				.endChoice()
            .otherwise()
            	.bean(businessConfirmationEmailBean)
                .wireTap("mongodb:myDb?database={{mongo_db_name}}&collection={{mongo_db_collection_loggedorder}}&operation=insert")
                // NOTE: .to() is not working here, nobody knows why - conversion error
                .wireTap("mongodb:myDb?database={{mongo_db_name}}&collection={{mongo_db_collection_businessorder}}&operation=insert")
                .endChoice();

    }

}
