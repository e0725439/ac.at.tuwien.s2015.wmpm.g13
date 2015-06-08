package ac.at.tuwien.s2015.wmpm.g13.camel.route;


import ac.at.tuwien.s2015.wmpm.g13.beans.BusinessConfirmationEmailBean;
import ac.at.tuwien.s2015.wmpm.g13.beans.ConfirmationEmailBean;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(EmailRoute.class);

    private ConfirmationEmailBean confirmationEmailBean;

    private BusinessConfirmationEmailBean businessConfirmationEmailBean;

    @Autowired
    public EmailRoute(ConfirmationEmailBean confirmationEmailBean, BusinessConfirmationEmailBean businessConfirmationEmailBean) {
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
                .otherwise()
                .bean(businessConfirmationEmailBean);

    }

}
