/**
 * Copyright (c) 2015. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 * 
 */
package ac.at.tuwien.s2015.wmpm.g13.provider.db;

import java.util.ResourceBundle;

/**
 * Class returning the contents of the mondo_db configuration file.
 *
 */
public class JettyConfigProvider {

	private final static ResourceBundle BUNDLE = ResourceBundle.getBundle("jetty");

	/**
	 * Returns the value as a {@code String} representation of the given property.
	 * @param property
	 * @return String value of the property
	 */
	public static String getString(JettyProperty property) {
		return BUNDLE.getString(property.getProperty());
	}

	/**
	 * Returns the value as a {@code int} for the given property.
	 * 
	 * @param property
	 * @return String value of the property
	 * @throws NumberFormatException
	 *             if the value cannot be parsed to an {@code Integer}
	 */
	public static int getInt(JettyProperty property) {
		return Integer.parseInt(getString(property));
	}
}
