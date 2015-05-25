package ac.at.tuwien.s2015.wmpm.g13.main;

import ac.at.tuwien.s2015.wmpm.g13.camel.config.PowerMaterialsConfig;
import org.apache.camel.spring.Main;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PowerMaterials extends Main {
	
	private static final Logger LOGGER = Logger.getLogger(PowerMaterials.class);
	
	public static void main(String... args) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PowerMaterialsConfig.class);
		PowerMaterials powerMaterials = new PowerMaterials();
		powerMaterials.setApplicationContext(context);
		powerMaterials.run();
	}
	
	public void run() {
		super.enableHangupSupport();
		try {
			LOGGER.info("Running server!");
			super.run();
		} catch (Exception e) {
			LOGGER.error("Cannot start app with Spring, caused by " + e.getMessage());
		}
	}
}
