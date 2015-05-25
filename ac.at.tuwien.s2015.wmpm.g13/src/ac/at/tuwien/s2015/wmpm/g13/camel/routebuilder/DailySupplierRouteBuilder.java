package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import ac.at.tuwien.s2015.wmpm.g13.beans.OrderItemEnricherBean;
import ac.at.tuwien.s2015.wmpm.g13.beans.SupplierOrderItemsBean;
import ac.at.tuwien.s2015.wmpm.g13.provider.db.DBProperty;
import ac.at.tuwien.s2015.wmpm.g13.provider.db.MongoConfigProvider;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Route for the daily supplier process for providing the missing orderItems
 * Created by mattias on 5/25/2015.
 */
@Component
public class DailySupplierRouteBuilder extends RouteBuilder {

    private static final Logger LOGGER = Logger
            .getLogger(DailySupplierRouteBuilder.class);

    private SupplierOrderItemsBean supplierOrderItemsBean;
    private OrderItemEnricherBean orderItemEnricherBean;

    @Autowired
    public DailySupplierRouteBuilder(SupplierOrderItemsBean supplierOrderItemsBean, OrderItemEnricherBean orderItemEnricherBean) {
        this.supplierOrderItemsBean = supplierOrderItemsBean;
        this.orderItemEnricherBean = orderItemEnricherBean;
    }

    @Override
    public void configure() throws Exception {
    	
    	String wireTapRoute = "mongodb:myDb?database=" 
				+ MongoConfigProvider.getString(DBProperty.MONGO_DB_NAME) 
				+ "&collection=" 
				+ MongoConfigProvider.getString(DBProperty.MONGO_DB_COLLECTION_MISSINGORDERITEMS);
    	
    	String wireTapRouteStockItems = "mongodb:myDb?database=" 
				+ MongoConfigProvider.getString(DBProperty.MONGO_DB_NAME) 
				+ "&collection=" 
				+ MongoConfigProvider.getString(DBProperty.MONGO_DB_COLLECTION_ITEMSTOCK);
    	
        // Daily SupplierProcess
        from("quartz2://supplierTimer/cron=30+*+*+*+*+?").routeId("cronSupplierProcess")
                .to(wireTapRoute + "&operation=findAll")
//				.wireTap(wireTapRoute + "&operation=remove")
                .to("direct:supplier_missingOrderItems");

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
                .enrich(wireTapRouteStockItems + "&operation=findAll", orderItemEnricherBean);
//                .wireTap(wireTapRouteStockItems + "&operation=update");

    }
}
