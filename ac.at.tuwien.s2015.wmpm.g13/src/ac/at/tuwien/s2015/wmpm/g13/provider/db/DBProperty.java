/**
 * Copyright (c) 2015. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 * 
 */
package ac.at.tuwien.s2015.wmpm.g13.provider.db;

/**
 * Enum providing all DB constants.
 */
public enum DBProperty {

	MONGO_DB_COLLECTION_SIMPLEORDER("mongo_db_collection_simpleorder"), //
	MONGO_DB_COLLECTION_BUSINESSORDER("mongo_db_collection_businessorder"), //
	MONGO_DB_COLLECTION_LOGGEDORDER("mongo_db_collection_loggedorder"), //
	MONGO_DB_COLLECTION_ITEMSTOCK("mongo_db_collection_itemstock"), //
	
	MONGO_DB_HOST("mongo_db_host"), //
	MONGO_DB_PORT("mongo_db_port"), //
	MONGO_DB_NAME("mongo_db_name"), //
	MONGO_DB_USER_NAME("mongo_db_user_name"), //
	MONGO_DB_USER_PASSWORD("mongo_db_user_password") //
	;

	private String property;

	private DBProperty(String property) {
		this.property = property;
	}

	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	
}
