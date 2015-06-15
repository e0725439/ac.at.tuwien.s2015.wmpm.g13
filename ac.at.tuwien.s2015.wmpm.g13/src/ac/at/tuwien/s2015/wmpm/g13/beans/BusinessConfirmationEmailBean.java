package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.order.BusinessOrder;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class BusinessConfirmationEmailBean {

    private static final Logger LOGGER = Logger.getLogger(BusinessConfirmationEmailBean.class);

    @Autowired
    private JavaMailSender mailSender;

    private SimpleMailMessage confirmationMail = new SimpleMailMessage();

    @Handler
    public void process(@Body BusinessOrder order) throws Exception {
        LOGGER.debug("Will send confirmation mail for business order: " + order);
        LOGGER.debug("Sending confirmation email to: " + order.getCustomer().getEmail());

        this.confirmationMail.setTo(order.getCustomer().getEmail());
        this.confirmationMail.setSubject("Confirmation: BusinessOrder received successfully.");
        this.confirmationMail.setText("We have received a very important buisness order with the ID " + order.getOrderId());
        this.mailSender.send(this.confirmationMail);

        LOGGER.debug("Confirmation email sent.");
    }
}
