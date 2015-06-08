/**
 * Copyright (c) 2015. All rights reserved.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 */
package ac.at.tuwien.s2015.wmpm.g13.model.person;

public class LegalPerson extends Person {

    private String name;

    private String registrationNumber;

    private String countryCourt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getCountryCourt() {
        return countryCourt;
    }

    public void setCountryCourt(String countryCourt) {
        this.countryCourt = countryCourt;
    }

    @Override
    public String toString() {
        return "LegalPerson [name=" + name + ", registrationNumber="
                + registrationNumber + ", countryCourt=" + countryCourt
                + ", getPersonId()=" + getPersonId() + ", getEmail()="
                + getEmail() + ", getAddress()=" + getAddress() + "]";
    }
}
