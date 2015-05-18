package ac.at.tuwien.s2015.wmpm.g13.beans;

import com.mongodb.Mongo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by mattias on 5/18/2015.
 */
@Component
public class DailyCronProcessBean {

    private static final Logger LOGGER = Logger.getLogger(DailyCronProcessBean.class);
    private Mongo myDb;

    @Autowired
    public DailyCronProcessBean(Mongo myDb) {
        this.myDb = myDb;
    }

    @Scheduled(cron="0/5 * * * * *")
    public void supplierCronJob() {
        LOGGER.info("Starting daily job scheduler to send the missing product list to the supplier");
    }
}
