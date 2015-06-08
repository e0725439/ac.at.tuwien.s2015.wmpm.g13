package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.Invoice;
import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by mattias on 5/22/2015.
 */
@Component
public class MissingOrderItemBean {

    private static final Logger LOGGER = Logger.getLogger(MissingOrderItemBean.class);
    private Mongo myDb;
    @Value("${mongo_db_name}")
    private String database;
    @Value("${mongo_db_collection_itemstock}")
    private String stockCollection;

    @Autowired
    public MissingOrderItemBean(Mongo myDb) {
        this.myDb = myDb;
    }

    @Handler
    public void process(Exchange exchange) {
        Invoice invoice = exchange.getIn().getBody(Invoice.class);    // vom supplier
        LOGGER.info("Refreshing orderItems from database and supplier, costs in total: " + invoice.getTotalPrice());
        DBCollection dbCollection = myDb.getDB(database).getCollection(stockCollection);
        for (OrderItem orderItem : invoice.getOrder().getOrderItems()) {
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("product.name", orderItem.getProduct().getName());
            BasicDBObject dbObject = (BasicDBObject) dbCollection.findOne(searchQuery);
            if (dbObject != null) {
                LOGGER.info("Updating amount in stock of: " + orderItem.getProduct().getName());
                BasicDBObject updatedBasicDBObject = new BasicDBObject();
                updatedBasicDBObject.put("quantity", ((int) dbObject.get("quantity")) + orderItem.getQuantity());
                BasicDBObject update = new BasicDBObject();
                update.put("$set", updatedBasicDBObject);
                dbCollection.update(searchQuery, update);
            } else {
                LOGGER.info(orderItem.getProduct().getName() + " not found in stock, creating it");
                BasicDBObject newBasicDBObject = new BasicDBObject();
                newBasicDBObject.put("product", orderItem.getProduct());
                newBasicDBObject.put("quantity", orderItem.getQuantity());
                dbCollection.insert(newBasicDBObject);
            }
        }
    }
}
