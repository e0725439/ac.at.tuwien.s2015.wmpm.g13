/**
 * Copyright (c) 2015. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 * 
 */
package ac.at.tuwien.s2015.wmpm.g13.db;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import ac.at.tuwien.s2015.wmpm.g13.provider.db.MongoDBConnectionProvider;

import com.mongodb.DB;

public class MongoDBConnectionProviderTest {

	@Test
	public void resetDatabase() {
		DB db = MongoDBConnectionProvider.getInstance().getDB();
		Assert.assertNotNull(db);
		Assert.assertTrue(db.isAuthenticated());
		
		Set<String> collectionNames = db.getCollectionNames();
		for (String collection: collectionNames) {
			if (collection.contains("wmpm")) {
				System.out.println("Removing collection: " + collection);
				db.getCollection(collection).drop();
			}
		}
	}

}
