package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.order.Order;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class ShipmentEmailBean {

    private static final Logger LOGGER = Logger.getLogger(ShipmentEmailBean.class);

    @Autowired
    private JavaMailSender mailSender;

    private SimpleMailMessage shipmentMail = new SimpleMailMessage();

    @Handler
    public void process(@Body Order order) throws Exception {
        LOGGER.info("Send shipment mail for order: " + order);
        LOGGER.debug("Sending shipment email to: " + order.getCustomer().getEmail());

        this.shipmentMail.setTo(order.getCustomer().getEmail());
        this.shipmentMail.setSubject("Shipping Confirmation: Order successfully shipped");
        this.shipmentMail.setText("We have shipped your order with the ID " + order.getOrderId());
        this.mailSender.send(this.shipmentMail);

        LOGGER.info("Shipment email sent");
    }
}
