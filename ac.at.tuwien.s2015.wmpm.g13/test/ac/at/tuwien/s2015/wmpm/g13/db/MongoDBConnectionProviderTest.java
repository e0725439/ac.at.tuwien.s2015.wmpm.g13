/**
 * Copyright (c) 2015. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 * 
 */
package ac.at.tuwien.s2015.wmpm.g13.db;

import org.junit.Assert;
import org.junit.Test;

import ac.at.tuwien.s2015.wmpm.g13.provider.db.MongoDBConnectionProvider;

import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class MongoDBConnectionProviderTest {

	@Test
	public void test() {
		DB db = MongoDBConnectionProvider.getInstance().getDB();
		Assert.assertNotNull(db);
		Assert.assertTrue(db.isAuthenticated());
		DBObject t = (DBObject)JSON.parse("{'test':'test'}");
		db.getCollection("test").insert(t);
	}

}
