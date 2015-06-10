package ac.at.tuwien.s2015.wmpm.g13.beans;

import ac.at.tuwien.s2015.wmpm.g13.model.Invoice;
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

/**
 * Created by mattias on 6/10/2015.
 */
@Component
public class PaymentBean {

    private static final Logger LOGGER = Logger.getLogger(PaymentBean.class);
    private Mongo myDb;
    @Value("${mongo_db_name}")
    private String database;
    @Value("${mongo_db_collection_invoice}")
    private String stockCollection;

    @Autowired
    public PaymentBean(Mongo myDb) {
        this.myDb = myDb;
    }

    @Handler
    public void process(@Body Invoice invoice) throws Exception {
        DBCollection dbCollection = myDb.getDB(database).getCollection(stockCollection);
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("creationDate", invoice.getCreationDate());
        BasicDBObject dbObject = (BasicDBObject) dbCollection.findOne(searchQuery);
        if(dbObject != null) {
            BasicDBObject updatedBasicDBObject = new BasicDBObject();
            updatedBasicDBObject.put("payed", true);
            BasicDBObject update = new BasicDBObject();
            update.put("$set", updatedBasicDBObject);
            dbCollection.update(searchQuery, update);
        } else {
            LOGGER.warn("Could not find any invoice with this given parameter");
        }
    }
}
