package ac.at.tuwien.s2015.wmpm.g13.camel.route;

import ac.at.tuwien.s2015.wmpm.g13.beans.OrderProcessBean;
import ac.at.tuwien.s2015.wmpm.g13.beans.ShipmentEmailBean;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Route for the daily supplier process for providing the missing orderItems
 * Created by mattias on 5/25/2015.
 */
@Component
public class OrderRoute extends RouteBuilder {

    private OrderProcessBean orderProcessBean;
    private ShipmentEmailBean shipmentEmailBean;

    @Autowired
    public OrderRoute(OrderProcessBean orderProcessBean, ShipmentEmailBean shipmentEmailBean) {
        this.shipmentEmailBean = shipmentEmailBean;
        this.orderProcessBean = orderProcessBean;
    }

    @Override
    public void configure() throws Exception {

        from("direct:order_processing")
        .log("Processing order now...")
        .bean(orderProcessBean)
        .choice()
        .when(header("shipped").isEqualTo(true))
            .bean(shipmentEmailBean)
        .otherwise()
            .end();
    }
}
