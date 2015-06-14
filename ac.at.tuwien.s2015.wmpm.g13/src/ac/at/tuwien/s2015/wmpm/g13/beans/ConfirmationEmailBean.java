package ac.at.tuwien.s2015.wmpm.g13.beans;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.model.order.SimpleOrder;


@Component
public class ConfirmationEmailBean {

    private static final Logger LOGGER = Logger.getLogger(ConfirmationEmailBean.class);

    @Autowired
    private JavaMailSender mailSender;

    private SimpleMailMessage confirmationMail = new SimpleMailMessage();

    @Handler
    public void process(@Body SimpleOrder order) throws Exception {
        LOGGER.info("Will send confirmation mail for order: " + order);
        LOGGER.debug("Sending confirmation email to: " + order.getCustomer().getEmail());

        this.confirmationMail.setTo(order.getCustomer().getEmail());
        this.confirmationMail.setSubject("Confirmation: Order received successfully.");
        this.confirmationMail.setText("We have received the order with the ID " + order.getOrderId());
        this.mailSender.send(this.confirmationMail);

        LOGGER.info("Confirmation email sent.");
    }
}
