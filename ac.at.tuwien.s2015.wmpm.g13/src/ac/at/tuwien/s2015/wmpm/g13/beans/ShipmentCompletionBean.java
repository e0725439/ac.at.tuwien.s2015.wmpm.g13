package ac.at.tuwien.s2015.wmpm.g13.beans;

import java.io.IOException;

import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ac.at.tuwien.s2015.wmpm.g13.model.DataModelException;
import ac.at.tuwien.s2015.wmpm.g13.model.Product;
import ac.at.tuwien.s2015.wmpm.g13.model.Shipment;
import ac.at.tuwien.s2015.wmpm.g13.model.order.BusinessOrder;
import ac.at.tuwien.s2015.wmpm.g13.model.order.Order;
import ac.at.tuwien.s2015.wmpm.g13.model.order.OrderItem;
import ac.at.tuwien.s2015.wmpm.g13.model.order.SimpleOrder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

@Component
public class ShipmentCompletionBean {

	private static final Logger LOGGER = Logger
			.getLogger(ShipmentCompletionBean.class);

	private Mongo myDb;
	@Value("${mongo_db_name}")
	private String database;
	@Value("${mongo_db_collection_shipping}")
	private String shippingCollection;
	@Value("${mongo_db_collection_businessorder}")
	private String businessOrderCollection;
	@Value("${mongo_db_collection_simpleorder}")
	private String simpleOrderCollection;
	@Value("${mongo_db_collection_itemstock}")
	private String stockCollection;

	@Autowired
	public ShipmentCompletionBean(Mongo myDb) {
		this.myDb = myDb;
	}

	@Handler
	public void process(Exchange exchange) throws Exception {
		LOGGER.debug("Checking if orders can be completed...");

		// get all open shippings
		LOGGER.debug("Checking for open shippings.");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("shipped", false);
		DBCursor allOpenShippings = myDb.getDB(database).getCollection(shippingCollection).find(searchQuery);
		LOGGER.debug("Open shippings found: " + allOpenShippings.count());

		// for every element found, try to see if it can be shipped
		while (allOpenShippings.hasNext()) {
			try {
				BasicDBObject obj = (BasicDBObject) allOpenShippings.next();
				Shipment openShipment = new ObjectMapper().readValue(obj.toString(), Shipment.class);
				finalizeShipping(openShipment, exchange);
			} catch (JsonParseException e) {
				LOGGER.error(
						"Error occured during database search (JsonParse).", e);
			} catch (JsonMappingException e) {
				LOGGER.error(
						"Error occured during database search (JsonMapping).",
						e);
			} catch (IOException e) {
				LOGGER.error("Error occured during database search (IO).", e);
			}
		}
	}

	private void finalizeShipping(Shipment openShipment, Exchange exchange) {
		LOGGER.debug("Trying to finalize the shipment for: " + openShipment);
		Order order = getOrderForShipment(openShipment);
		if (order == null) {
			return;
		}
		boolean canBeShipped = true;
		for (OrderItem orderItem: order.getOrderItems()) {
			// check if the item is in the shipment
			for (OrderItem shipmentItem: openShipment.getOrderItems()) {
				if (shipmentItem.getProduct().getProductId().equals(orderItem.getProduct().getProductId())) {
					// check the quantitites
					int qOrder = orderItem.getQuantity();
					int qShipment = shipmentItem.getQuantity();
					int qDif = qOrder - qShipment;
					if (qDif != 0) {
						// check with stock and try to ship
						boolean isOnStock = isProductQuantityOnStock(qDif, orderItem.getProduct());
						if (isOnStock) {
							// finalize for this product
							// Substract item quantity from stock
							BasicDBObject stockDB = new BasicDBObject();
							stockDB.put("product.productId", orderItem.getProduct().getProductId());
							BasicDBObject productOnStock = (BasicDBObject) myDb.getDB(database).getCollection(stockCollection).findOne(stockDB);
							// update 
							productOnStock.put("quantity", ((int) productOnStock.get("quantity")) - qDif);
							BasicDBObject update = new BasicDBObject();
		                    update.put("$set", productOnStock);
							myDb.getDB(database).getCollection(stockCollection).update(stockDB, update);
									
							// Add item quantity to shipment
							try {
								shipmentItem.setQuantity(shipmentItem.getQuantity() + qDif);
								
							} catch (DataModelException e) {
								e.printStackTrace();
							}
							
							
							
						} else {
							canBeShipped = false;
						}
					}
				}
			}
		}
		if (canBeShipped) {
			//	finalize shipping
			openShipment.setShipped(true);
			// send email confirmation
			exchange.getIn().setHeader("shipped", true);
			
		}
		try {
			String openShipmentAsString = (new ObjectMapper()).writeValueAsString(openShipment);
			BasicDBObject dbObject = (BasicDBObject) JSON.parse(openShipmentAsString);
			
			BasicDBObject query = new BasicDBObject();
			query.put("orderId", openShipment.getOrderId());
			
			myDb.getDB(database).getCollection(shippingCollection).findAndModify(query, dbObject);
		} catch (JsonProcessingException e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		exchange.getIn().setBody(order);
	}

	private boolean isProductQuantityOnStock(int qDif, Product product) {
		BasicDBObject stockDB = new BasicDBObject();
		stockDB.put("product.productId", product.getProductId());
		BasicDBObject productOnStock = (BasicDBObject) myDb.getDB(database).getCollection(stockCollection).findOne(stockDB);
		try {
			if (productOnStock != null) {
				OrderItem orderItem = new ObjectMapper().readValue(productOnStock.toString(), OrderItem.class);
				if (orderItem != null) {
					if (orderItem.getQuantity() >= qDif) {
						return true;
					}
				}
			} else {
				LOGGER.error("No product has been found for ID: " + product.getProductId());
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return false;
	}

	private Order getOrderForShipment(Shipment shipment) {
		// get the order of the shipment
		BasicDBObject searchQueryBusinessOrder = new BasicDBObject();
		searchQueryBusinessOrder.put("orderId", shipment.getOrderId());
		BasicDBObject businessOrder = (BasicDBObject) myDb.getDB(database)
				.getCollection(businessOrderCollection)
				.findOne(searchQueryBusinessOrder);
		if (businessOrder != null) {
			try {
				BusinessOrder order = new ObjectMapper().readValue(
						businessOrder.toString(), BusinessOrder.class);
				return order;
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}

		BasicDBObject searchQuerySimpleOrder = new BasicDBObject();
		searchQuerySimpleOrder.put("orderId", shipment.getOrderId());
		BasicDBObject simepleOrder = (BasicDBObject) myDb.getDB(database)
				.getCollection(simpleOrderCollection)
				.findOne(searchQuerySimpleOrder);
		if (simepleOrder != null) {
			try {
				SimpleOrder order = new ObjectMapper().readValue(simepleOrder.toString(), SimpleOrder.class);
				return order;
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		
		return null;
	}
}
