package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.Invoice;
import ac.at.tuwien.s2015.wmpm.g13.model.Order;
import ac.at.tuwien.s2015.wmpm.g13.model.OrderItem;
import ac.at.tuwien.s2015.wmpm.g13.model.Shippment;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AvailableOrderItemBean {

    private static final Logger LOGGER = Logger.getLogger(AvailableOrderItemBean.class);
    private Mongo myDb;
    @Value("${mongo_db_name}")
    private String database;
    @Value("${mongo_db_collection_itemstock}")
    private String stockCollection;
    @Value("${mongo_db_collection_shipping}")
    private String shippingCollection;
    @Value("${mongo_db_collection_itemmissing}")
    private String missingCollection;

    @Autowired
    public AvailableOrderItemBean(Mongo myDb) {
        this.myDb = myDb;
    }

    @Handler
    public void process(Exchange exchange) {
        Order order = exchange.getIn().getBody(Order.class);
        LOGGER.info("Checking Availability for Order: " + order.getOrderId());
        DBCollection dbCollectionStock = myDb.getDB(database).getCollection(stockCollection);
        DBCollection dbCollectionShipping = myDb.getDB(database).getCollection(shippingCollection);
        DBCollection dbCollectionMissing = myDb.getDB(database).getCollection(missingCollection);
        //Create Shipping
        Shippment shippment = new Shippment();
        shippment.setOrderId(order.getOrderId());
        List<OrderItem> shippingItemList = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("product.name", orderItem.getProduct().getName());
            BasicDBObject dbObject = (BasicDBObject) dbCollectionStock.findOne(searchQuery);
            if (dbObject != null) {
                if((int) dbObject.get("quantity") >= orderItem.getQuantity()){
                    dbObject.put("quantity", ((int) dbObject.get("quantity")) - orderItem.getQuantity());
                    BasicDBObject update = new BasicDBObject();
                    update.put("$set", dbObject);
                    dbCollectionStock.update(searchQuery, update);
                    shippingItemList.add(new OrderItem(orderItem.getProduct(),orderItem.getQuantity()));
                }else {
                    shippingItemList.add(new OrderItem(orderItem.getProduct(),(int) dbObject.get("quantity")));

                    dbObject.put("quantity", 0);
                    BasicDBObject update = new BasicDBObject();
                    update.put("$set", dbObject);
                    dbCollectionStock.update(searchQuery, update);
                }
            } else {
                LOGGER.info(orderItem.getProduct().getName() + " not found in stock, creating it");
                BasicDBObject newBasicDBObject = new BasicDBObject();
                newBasicDBObject.put("product", orderItem.getProduct());
                newBasicDBObject.put("quantity", orderItem.getQuantity());
                dbCollection.insert(newBasicDBObject);
            }
        }

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
