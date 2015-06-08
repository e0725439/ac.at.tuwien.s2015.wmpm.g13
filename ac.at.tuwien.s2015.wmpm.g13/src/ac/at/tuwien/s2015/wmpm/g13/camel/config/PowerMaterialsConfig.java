package ac.at.tuwien.s2015.wmpm.g13.camel.config;

import com.mongodb.Mongo;
import org.apache.camel.CamelContext;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.UnknownHostException;
import java.util.Properties;

@Configuration
@ComponentScan("ac.at.tuwien.s2015.wmpm.g13")
@ImportResource({"classpath:META-INF/cxf/cxf.xml"})
@PropertySource({"classpath:mongo_db.properties", "mail.properties", "facebook.properties"})
@EnableScheduling
public class PowerMaterialsConfig extends CamelConfiguration {

    private static final Logger LOGGER = Logger
            .getLogger(PowerMaterialsConfig.class);

    @Override
    protected CamelContext createCamelContext() throws Exception {
        return new SpringCamelContext(getApplicationContext());
    }

    @Override
    protected void setupCamelContext(CamelContext camelContext) throws Exception {

    }

    @Bean
    public Mongo myDb(@Value("${mongo_db_user_name}") String username, @Value("${mongo_db_user_password}") String password,
                      @Value("${mongo_db_name}") String database, @Value("${mongo_db_host}") String host, @Value("${mongo_db_port}") int port) {
        Mongo mongo = null;
        try {
            mongo = new Mongo(host, port);
            mongo.getDB(database).authenticate(username, password.toCharArray());
        } catch (UnknownHostException e) {
            LOGGER.error("Something went wrong during initialization of the mongo bean, cause of " + e.getMessage());
        }
        return mongo;
    }

    @Bean
    public PropertiesComponent properties() {
        String[] locations = new String[]{"classpath:mongo_db.properties", "classpath:mail.properties", "classpath:webservices.properties"};
        PropertiesComponent propertiesComponent = new PropertiesComponent();
        propertiesComponent.setLocations(locations);
        return propertiesComponent;
    }

    @Bean
    public JavaMailSenderImpl mailSender(@Value("${mail.server.host}") String host, @Value("${mail.server.port}") String port,
                                         @Value("${mail.server.protocol}") String protocol, @Value("${mail.server.user.name}") String username,
                                         @Value("${mail.server.user.password}") String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.smtp.starttls.enable", false);
        javaMailProperties.put("mail.smtp.quitwait", false);
        javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.smtp.socketFactory.fallback", false);
        javaMailProperties.put("mail.debug", true);
        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }

    @Bean
    public SimpleMailMessage confirmationEmail(@Value("${mail.server.user.name}") String to, @Value("${mail.server.user.name}") String from,
                                               @Value("${mail.subject.confirmation}") String subject) {
        return getSimpleMailMessage(to, from, subject);
    }

    @Bean
    public SimpleMailMessage businessConfirmationEmail(@Value("${mail.server.user.name}") String to, @Value("${mail.from.business}") String from,
                                                       @Value("${mail.subject.confirmation.business}") String subject) {
        return getSimpleMailMessage(to, from, subject);
    }

    //To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    private SimpleMailMessage getSimpleMailMessage(String to, String from, String subject) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(subject);
        return simpleMailMessage;
    }

}
