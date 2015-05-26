/**
 * Copyright (c) 2015. All rights reserved.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 */
package ac.at.tuwien.s2015.wmpm.g13.provider.db;

/**
 * Enum providing all Jetty constants.
 */
public enum JettyProperty {

    JETTY_HOST("jetty_host"), //
    JETTY_PORT("jetty_port") //
    ;

    private String property;

    private JettyProperty(String property) {
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
