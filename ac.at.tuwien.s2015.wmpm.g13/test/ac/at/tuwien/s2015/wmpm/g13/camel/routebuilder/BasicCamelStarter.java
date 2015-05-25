package ac.at.tuwien.s2015.wmpm.g13.camel.routebuilder;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ac.at.tuwien.s2015.wmpm.g13.camel.config.PowerMaterialsConfig;
import ac.at.tuwien.s2015.wmpm.g13.main.PowerMaterials;

public class BasicCamelStarter extends Thread {

	private PowerMaterials powerMaterials;

	public void run() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				PowerMaterialsConfig.class);
		powerMaterials = new PowerMaterials();
		powerMaterials.setApplicationContext(context);
		powerMaterials.run();
	}

	public void cancel() {
		try {
			powerMaterials.stop();
		} catch (Exception e) {
			// nothing to do here
		}
		interrupt();
	}

}
