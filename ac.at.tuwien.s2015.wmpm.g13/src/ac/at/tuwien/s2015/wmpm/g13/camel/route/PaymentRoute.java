package ac.at.tuwien.s2015.wmpm.g13.camel.route;

import ac.at.tuwien.s2015.wmpm.g13.beans.PaymentBean;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by mattias on 6/10/2015.
 */
@Component
public class PaymentRoute extends RouteBuilder {

    private PaymentBean paymentBean;

    @Autowired
    public PaymentRoute(PaymentBean paymentBean) {
        this.paymentBean = paymentBean;
    }

    @Override
    public void configure() throws Exception {
        from("direct:company_receivePayment").routeId("ReceivePayment")
                .log("Receiving the payment of the order from the customer")
                .bean(paymentBean)
                .log("Set the invoice to paid, and finishing this workflow");
    }
}
