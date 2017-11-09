package com.geog.Model;

import javax.faces.bean.*;

@ManagedBean
@RequestScoped
public class Country implements java.io.Serializable{
	private String code;
	private String name;
	private String details;
	
	
	
	public Country() {
		super();
	}

	public Country(String code, String name, String varchar) {
		super();
		this.code = code;
		this.name = name;
		this.details = varchar;
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
