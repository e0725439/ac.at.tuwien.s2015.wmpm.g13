package ac.at.tuwien.s2015.wmpm.g13.camel.route;

import ac.at.tuwien.s2015.wmpm.g13.beans.AvailableOrderItemBean;
import ac.at.tuwien.s2015.wmpm.g13.beans.SupplierOrderItemsBean;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Route for the daily supplier process for providing the missing orderItems
 * Created by mattias on 5/25/2015.
 */
@Component
public class OrderRoute extends RouteBuilder {

    private SupplierOrderItemsBean supplierOrderItemsBean;
    private AvailableOrderItemBean availableOrderItemBean;

    @Autowired
    public OrderRoute(SupplierOrderItemsBean supplierOrderItemsBean, AvailableOrderItemBean availableOrderItemBean) {
        this.supplierOrderItemsBean = supplierOrderItemsBean;
        this.availableOrderItemBean = availableOrderItemBean;
    }

    @Override
    public void configure() throws Exception {

        from("direct:order_processing").bean(availableOrderItemBean).end();
        //choice(isready?).whenready(sendShippingmail).otherwise(end);


    }
}
