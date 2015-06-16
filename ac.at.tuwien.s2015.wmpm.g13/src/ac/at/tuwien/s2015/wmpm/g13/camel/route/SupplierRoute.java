package ac.at.tuwien.s2015.wmpm.g13.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.beans.MissingOrderItemBean;
import ac.at.tuwien.s2015.wmpm.g13.beans.SupplierOrderItemsBean;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Route for the daily supplier process for providing the missing orderItems
 * Created by mattias on 5/25/2015.
 */
@Component
public class SupplierRoute extends RouteBuilder {
	
    private SupplierOrderItemsBean supplierOrderItemsBean;
    private MissingOrderItemBean missingOrderItemBean;

    @Autowired
    public SupplierRoute(SupplierOrderItemsBean supplierOrderItemsBean, MissingOrderItemBean missingOrderItemBean) {
        this.supplierOrderItemsBean = supplierOrderItemsBean;
        this.missingOrderItemBean = missingOrderItemBean;
    }

    @Override
    public void configure() throws Exception {

        // Daily SupplierProcess
        from("quartz2://supplierTimer?trigger.repeatCount=0").routeId("CronSupplierJob")
                .to("mongodb:myDb?database={{mongo_db_name}}&collection={{mongo_db_collection_itemmissing}}&operation=findAll")
                .inOnly("seda:company_removeMissingItems.queue")
                .to("direct:supplier_missingOrderItems");

        from("seda:company_removeMissingItems.queue").routeId("RemoveMissingItems").process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {
                DBObject commandBody = new BasicDBObject("drop", "wmpm.item.missing");
                exchange.getIn().setBody(commandBody);
            }
        }).to("mongodb:myDb?database={{mongo_db_name}}&operation=command");

        from("direct:supplier_missingOrderItems").routeId("SupplierOrder")
                .delay(3000)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
                .bean(supplierOrderItemsBean)
                .to("direct:company_receiveMissingOrder");

        from("direct:company_receiveMissingOrder").routeId("ReceiveMissingOrder")
                .split(body()).parallelProcessing()
                .to("direct:supplier_receiveInvoice")
                .to("direct:company_putOrderItems")
                .end();

        from("direct:company_putOrderItems").routeId("ReplenishStock")
                .bean(missingOrderItemBean)
                .log("Refreshed the inventory of our company");
    }
}
