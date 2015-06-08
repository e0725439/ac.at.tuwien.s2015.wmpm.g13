/**
 * Copyright (c) 2015. All rights reserved.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 */
package ac.at.tuwien.s2015.wmpm.g13.provider.db;


import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

public class MongoDBConnectionProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBConnectionProvider.class);
    private static MongoDBConnectionProvider instance = null;
    private DB database = null;

    protected MongoDBConnectionProvider() {
        String host = MongoConfigProvider.getString(DBProperty.MONGO_DB_HOST);
        int port = MongoConfigProvider.getInt(DBProperty.MONGO_DB_PORT);
        String dbName = MongoConfigProvider.getString(DBProperty.MONGO_DB_NAME);
        String username = MongoConfigProvider.getString(DBProperty.MONGO_DB_USER_NAME);
        String password = MongoConfigProvider.getString(DBProperty.MONGO_DB_USER_PASSWORD);

        MongoClient mc;

        try {
            mc = new MongoClient(host, port);
            database = mc.getDB(dbName);

            LOGGER.debug("Authenticating user: " + username, " for database: " + dbName);

            boolean authenticate = database.authenticate(username, password.toCharArray());

            if (!authenticate) {
                LOGGER.error("User authentication failed.");
                mc.close();
                database = null;
            } else {
                LOGGER.debug("User authentication successfull.");
            }

        } catch (UnknownHostException e) {
            database = null;
        }
    }

    public static MongoDBConnectionProvider getInstance() {
        LOGGER.debug("Establishing connection to database.");
        if (instance == null) {
            instance = new MongoDBConnectionProvider();
        }
        return instance;
    }

    /**
     * @return Database pointer on server if connection and authentication could
     *         be established successfully, NULL otherwise.
     *
     */
    public DB getDB() {
        return database;
    }

    public boolean isDBavailable() {
        if (database == null) {
            return false;
        }

        if (!database.isAuthenticated()) {
            return false;
        }

        return true;
    }
}
