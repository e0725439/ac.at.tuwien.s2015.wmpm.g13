package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SoapRouteTest {

    String soapExampleRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.services.g13.wmpm.s2015.tuwien.at.ac/\"><soapenv:Header/><soapenv:Body><soap:order><arg0><customer><address>Mustergasse 22</address><email>test@test.com</email><personId>123654</personId><countryCourt>Vienna</countryCourt><name>Musterfirma GMBH</name><registrationNumber>AT123456</registrationNumber></customer><orderDate>2014-12-12</orderDate><orderId>123456</orderId><orderItems><product><name>Hammer</name><price>10</price><productId>123456</productId></product><quantity>12</quantity></orderItems><sendDate>2014-12-12</sendDate><supplier><address>Mustergasse 22</address><email>asd@asd.com</email><personId>1234</personId><countryCourt>Vienna</countryCourt><name>PowerMaterials GMBH</name><registrationNumber>AT32165</registrationNumber></supplier></arg0></soap:order></soapenv:Body></soapenv:Envelope>";

    private BasicCamelStarter camelstarter = new BasicCamelStarter();

    @Before
    public void setUp() throws Exception {
        camelstarter.start();
        Thread.sleep(10000);
    }

    @After
    public void tearDown() {
        camelstarter.cancel();
    }

    @Test
    public void testBusinessOrder() throws Exception {
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://localhost:8282/businessorder");
        post.setHeader("Content-Type", "application/xml");
        HttpEntity entity = new ByteArrayEntity(soapExampleRequest.getBytes("UTF-8"));
        post.setEntity(entity);

        HttpResponse response = client.execute(post);
        Assert.assertEquals(201, response.getStatusLine().getStatusCode());
    }

}
