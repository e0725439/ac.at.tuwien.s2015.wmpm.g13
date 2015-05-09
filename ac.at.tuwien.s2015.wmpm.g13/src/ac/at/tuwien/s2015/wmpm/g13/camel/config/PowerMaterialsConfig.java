package ac.at.tuwien.s2015.wmpm.g13.camel.config;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("ac.at.tuwien.s2015.wmpm.g13")
@EnableTransactionManagement
@ImportResource({ "classpath:spring-config.xml" })
public class PowerMaterialsConfig extends CamelConfiguration {

	@Override
	protected CamelContext createCamelContext() throws Exception {
		return new SpringCamelContext(getApplicationContext());
	}

	@Override
	protected void setupCamelContext(CamelContext camelContext) throws Exception {
		// setup...
	}
	
}
