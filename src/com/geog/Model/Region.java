package com.geog.Model;

public class Region {
	private String countryCode;
	private String code;
	private String name;
	private String description;
	
	
	public Region() {
		super();
	}

	public Region(String countryCode, String code, String name, String description) {
		super();
		this.countryCode = countryCode;
		this.code = code;
		this.name = name;
		this.description = description;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
