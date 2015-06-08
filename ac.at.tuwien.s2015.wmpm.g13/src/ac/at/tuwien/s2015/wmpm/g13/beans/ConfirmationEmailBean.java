package ac.at.tuwien.s2015.wmpm.g13.beans;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.model.SimpleOrder;


@Component
public class ConfirmationEmailBean {

    private static final Logger LOGGER = Logger.getLogger(ConfirmationEmailBean.class);

    @Autowired
    private JavaMailSender mailSender;

    private SimpleMailMessage confirmationMail = new SimpleMailMessage();

    @Handler
    public void process(@Body SimpleOrder order) throws Exception {
        LOGGER.debug("Will send confirmation mail for simple order: " + order);
        LOGGER.debug("Sending confirmation email to: " + order.getCustomer().getEmail());

        this.confirmationMail.setTo(order.getCustomer().getEmail());
        // NOTE: {{}} is not working here - bug in camel?
        this.confirmationMail.setSubject("Confirmation: SimpleOrder received successfully.");
        this.confirmationMail.setText("We have received the simple order with the ID " + order.getOrderId());
        this.mailSender.send(this.confirmationMail);

        LOGGER.debug("Confirmation email sent.");
    }
}
