package ac.at.tuwien.s2015.wmpm.g13.camel.route;

import ac.at.tuwien.s2015.wmpm.g13.beans.OrderItemEnricherBean;
import ac.at.tuwien.s2015.wmpm.g13.beans.SupplierOrderItemsBean;
import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Route for the daily supplier process for providing the missing orderItems
 * Created by mattias on 5/25/2015.
 */
@Component
public class SupplierRoute extends RouteBuilder {

    private static final Logger LOGGER = Logger
            .getLogger(SupplierRoute.class);

    private SupplierOrderItemsBean supplierOrderItemsBean;
    private OrderItemEnricherBean orderItemEnricherBean;

    @Autowired
    public SupplierRoute(SupplierOrderItemsBean supplierOrderItemsBean, OrderItemEnricherBean orderItemEnricherBean) {
        this.supplierOrderItemsBean = supplierOrderItemsBean;
        this.orderItemEnricherBean = orderItemEnricherBean;
    }

    @Override
    public void configure() throws Exception {

        // Daily SupplierProcess
        from("quartz2://supplierTimer/cron=*+1+*+*+*+?").routeId("cronSupplierProcess")
                .to("mongodb:myDb?database=wmpm_mattias&collection=wmpm.item.missing&operation=findAll")
                        //.wireTap("direct:company_removeMissingItems")
                .to("direct:supplier_missingOrderItems")
                .end();

        from("direct:company_removeMissingItems").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                DBObject commandBody = new BasicDBObject("drop", "wmpm.item.missing");
                exchange.getIn().setBody(commandBody);
            }
        }).to("mongodb:myDb?database=wmpm_mattias&operation=command");

        from("direct:supplier_missingOrderItems")
                .delay(3000)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
                .process(supplierOrderItemsBean)
                .to("direct:company_receiveMissingSimpleOrder");

        from("direct:company_receiveMissingSimpleOrder")
                .split(body()).parallelProcessing()
                .to("direct:supplier_receiveInvoice")
                .to("direct:company_putOrderItems")
                .end();

        from("direct:supplier_receiveInvoice")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        LOGGER.info("Supplier got the paid invoice, done with the action");
                    }
                });

        from("direct:company_putOrderItems")
                .enrich("mongodb:myDb?database=wmpm_mattias&collection=wmpm.item.stock&operation=findAll", orderItemEnricherBean)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        LOGGER.info(exchange.getIn().getBody());
                        ArrayList<OrderItem> list = exchange.getIn().getBody(ArrayList.class);
                    }
                })
                .wireTap("mongodb:myDb?database=wmpm_mattias&collection=wmpm_item_stock&operation=save");
    }
}
