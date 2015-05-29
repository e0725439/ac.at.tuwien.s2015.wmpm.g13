package ac.at.tuwien.s2015.wmpm.g13.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.model.BusinessOrder;

@Component
public class BusinessConfirmationEmailBean implements Processor {

    private static final Logger LOGGER = Logger
            .getLogger(BusinessConfirmationEmailBean.class);

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    @Qualifier("businessConfirmationEmail")
    private SimpleMailMessage confirmationMail;

    @Override
    public void process(Exchange exchange) throws Exception {
        BusinessOrder order = exchange.getIn().getBody(BusinessOrder.class);

        LOGGER.debug("WILL SEND BUSINESS CONFIRMATION MAIL FOR ORDER: " + order);
        LOGGER.debug("SENDING CONFIRMATION EMAIL TO: "
                + order.getCustomer().getEmail());

        this.confirmationMail.setText("We have received a very important buisness order with the ID "
                + order.getOrderId());
        this.mailSender.send(this.confirmationMail);

        LOGGER.debug("CONFIRMATION EMAIL SENT!");
    }
}