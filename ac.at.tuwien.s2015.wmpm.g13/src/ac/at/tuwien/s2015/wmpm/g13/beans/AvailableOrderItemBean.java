package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.order.Order;
import ac.at.tuwien.s2015.wmpm.g13.model.order.OrderItem;
import ac.at.tuwien.s2015.wmpm.g13.model.Shipment;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import org.apache.camel.Body;
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
    public void process(@Body Order order) {
        LOGGER.info("Checking Availability for Order: " + order.getOrderId());
        DBCollection dbCollectionStock = myDb.getDB(database).getCollection(stockCollection);
        DBCollection dbCollectionShipping = myDb.getDB(database).getCollection(shippingCollection);
        DBCollection dbCollectionMissing = myDb.getDB(database).getCollection(missingCollection);
        //Create Shipping
        Shipment shipment = new Shipment();
        shipment.setOrderId(order.getOrderId());
        List<OrderItem> shippingItemList = new ArrayList<>();
        shipment.setOrderItems(shippingItemList);
        LOGGER.info("Checking stock for each order item");
        for (OrderItem orderItem : order.getOrderItems()) {
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("product.name", orderItem.getProduct().getName());
            BasicDBObject itemStock = (BasicDBObject) dbCollectionStock.findOne(searchQuery);
            BasicDBObject itemMissing = (BasicDBObject) dbCollectionMissing.findOne(searchQuery);
            //Is OrderItem in Stock Collection?
            if (itemStock != null) {
                //Is enough Quantity available?
                LOGGER.info(orderItem.getProduct().getName() + " found in stocks");
                if ((int) itemStock.get("quantity") >= orderItem.getQuantity()) {
                    LOGGER.info("Enough "+ orderItem.getProduct().getName() + "stocks found");
                    //Add Item to Shipment
                    shippingItemList.add(new OrderItem(orderItem.getProduct(), orderItem.getQuantity()));
                    //Reduce Stock
                    itemStock.put("quantity", ((int) itemStock.get("quantity")) - orderItem.getQuantity());
                    BasicDBObject update = new BasicDBObject();
                    update.put("$set", itemStock);
                    dbCollectionStock.update(searchQuery, update);
                } else {
                    LOGGER.info(orderItem.getProduct().getName() + " not enough stock available, add it to missing stock");
                    //Add Item to Shipment
                    shippingItemList.add(new OrderItem(orderItem.getProduct(), (int) itemStock.get("quantity")));
                    //Add Item to missing Stock
                    if (itemMissing != null) {
                        itemMissing.put("quantity", orderItem.getQuantity());
                        BasicDBObject update = new BasicDBObject();
                        update.put("$set", itemMissing);
                        dbCollectionMissing.update(searchQuery, update);
                    } else {
                        BasicDBObject product = new BasicDBObject();
                        product.put("productId", orderItem.getProduct().getProductId());
                        product.put("name", orderItem.getProduct().getName());
                        product.put("price", orderItem.getProduct().getPrice());
                        BasicDBObject insert = new BasicDBObject();
                        insert.put("product", product);
                        insert.put("quantity", orderItem.getQuantity());
                        dbCollectionMissing.insert(insert);
                    }
                    //Reduce Stock
                    itemStock.put("quantity", 0);
                    BasicDBObject update = new BasicDBObject();
                    update.put("$set", itemStock);
                    dbCollectionStock.update(searchQuery, update);
                }
            } else {
                //Add Item to Shipment
                shippingItemList.add(new OrderItem(orderItem.getProduct(), 0));
                //Add item to missing stock
                LOGGER.info(orderItem.getProduct().getName() + " not found in stock, add it to missing stock");
                if (itemMissing != null) {
                    itemMissing.put("quantity", orderItem.getQuantity());
                    BasicDBObject update = new BasicDBObject();
                    update.put("$set", itemMissing);
                    dbCollectionMissing.update(searchQuery, update);
                } else {
                    BasicDBObject product = new BasicDBObject();
                    product.put("productId", orderItem.getProduct().getProductId());
                    product.put("name", orderItem.getProduct().getName());
                    product.put("price", orderItem.getProduct().getPrice());
                    BasicDBObject insert = new BasicDBObject();
                    insert.put("product", product);
                    insert.put("quantity", orderItem.getQuantity());
                    dbCollectionMissing.insert(insert);
                }
            }
        }
        //Insert Shipment into DB
        LOGGER.info("Stocks checked inserting shipment");
        BasicDBObject insert = new BasicDBObject();
        insert.put("orderId", shipment.getOrderId());
        List<BasicDBObject> shippingItemsDB = new ArrayList<>();
        for (OrderItem item : shippingItemList){
            BasicDBObject product = new BasicDBObject();
            product.put("productId", item.getProduct().getProductId());
            product.put("name", item.getProduct().getName());
            product.put("price", item.getProduct().getPrice());
            BasicDBObject insertItem = new BasicDBObject();
            insertItem.put("product", product);
            insertItem.put("quantity", item.getQuantity());
            shippingItemsDB.add(insertItem);
        }
        insert.put("orderItems", shippingItemsDB);
        insert.put("ready", false);
        insert.put("paid", false);
        dbCollectionShipping.insert(insert);
    }
}
