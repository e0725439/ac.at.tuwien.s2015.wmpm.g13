package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ac.at.tuwien.s2015.wmpm.g13.provider.db.DBProperty;
import ac.at.tuwien.s2015.wmpm.g13.provider.db.MongoConfigProvider;
import ac.at.tuwien.s2015.wmpm.g13.provider.db.MongoDBConnectionProvider;

import com.mongodb.BasicDBObject;

public class RestRouteTest {
	
	String badOrder = "{\"customer\":{\"personId\":\"123\",\"email\":\"max.muster@mail.com\",\"address\":\"Street 23, Vienna 1050, Austria\",\"firstname\":\"Max\",\"lastname\":\"Mustermann\"},\"supplier\":{\"personId\":\"999\",\"email\":\"company@company.com\",\"address\":\"Street 33, Linz 4040, Austria\",\"name\":\"mycompany\",\"registrationNumber\":\"UAT123213\",\"countryCourt\":\"Linz\"},\"orderDate\":1431446781522,\"sendDate\":1431446781522,\"orderItems\":[{\"product\":{\"productId\":\"10023\",\"name\":\"Hammer\",\"price\":10},\"quantity\":-2}]}";
	String goodOrder = "{\"customer\":{\"personId\":\"123\",\"email\":\"max.muster@mail.com\",\"address\":\"Street 23, Vienna 1050, Austria\",\"firstname\":\"Max\",\"lastname\":\"Mustermann\"},\"supplier\":{\"personId\":\"999\",\"email\":\"company@company.com\",\"address\":\"Street 33, Linz 4040, Austria\",\"name\":\"mycompany\",\"registrationNumber\":\"UAT123213\",\"countryCourt\":\"Linz\"},\"orderDate\":1431446781522,\"sendDate\":1431446781522,\"orderItems\":[{\"product\":{\"productId\":\"10023\",\"name\":\"Hammer\",\"price\":10},\"quantity\":2}]}";

	private BasicCamelStarter camelstarter = new BasicCamelStarter();
	
	@Before
	public void setUp() throws Exception {
		camelstarter.start();
		Thread.sleep(10000);
	}
	
	@After
	public void tearDown() {
		camelstarter.cancel();
		
		BasicDBObject query = new BasicDBObject();
		query.put("customer.personId", "123");
		MongoDBConnectionProvider.getInstance().getDB().getCollection(MongoConfigProvider.getString(DBProperty.MONGO_DB_COLLECTION_SIMPLEORDER)).remove(query);
	}
	
	@Test
	public void testSimpleOrderBadRequest() throws Exception {
		// simple order - bad
		HttpClient client = HttpClientBuilder.create().build();
		HttpPut put = new HttpPut("http://localhost:8181/services/rest/simpleorder");
		put.setHeader("Content-Type", "application/json");
		HttpEntity entity = new ByteArrayEntity(badOrder.getBytes("UTF-8"));
		put.setEntity(entity);
        
        HttpResponse response = client.execute(put);
		Assert.assertEquals(400, response.getStatusLine().getStatusCode());
	
		// simple order - valid
		put.setHeader("Content-Type", "application/json");
		entity = new ByteArrayEntity(goodOrder.getBytes("UTF-8"));
		put.setEntity(entity);
        
        response = client.execute(put);
		Assert.assertEquals(201, response.getStatusLine().getStatusCode());
	}

}
