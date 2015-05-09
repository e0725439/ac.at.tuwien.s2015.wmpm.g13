/**
 * Copyright (c) 2015. All rights reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS".
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES.
 * 
 */
package ac.at.tuwien.s2015.wmpm.g13.model.person;

public class LegalPerson extends Person {

	private String name;
	
	private String registrationNumber;
	
	private String countryCourt;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the registrationNumber
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	/**
	 * @param registrationNumber the registrationNumber to set
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	/**
	 * @return the countryCourt
	 */
	public String getCountryCourt() {
		return countryCourt;
	}

	/**
	 * @param countryCourt the countryCourt to set
	 */
	public void setCountryCourt(String countryCourt) {
		this.countryCourt = countryCourt;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LegalPerson [name=" + name + ", registrationNumber="
				+ registrationNumber + ", countryCourt=" + countryCourt
				+ ", getPersonId()=" + getPersonId() + ", getEmail()="
				+ getEmail() + ", getAddress()=" + getAddress() + "]";
	}
	
}
