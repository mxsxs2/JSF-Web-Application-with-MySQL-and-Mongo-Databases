package com.geog.Model;

import javax.faces.bean.*;


@ManagedBean
@RequestScoped
public class Country implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
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
	
	@Override
	public boolean equals(Object object) {
		//Check if object is null or instance of country.
		//isInstance checks if the object is null. 
		if(this.getClass().isInstance(object)){
			//Compare the codes of the country
			return this.code==this.getClass().cast(object).getCode();
		}
		//Return false it is not the same
		return false;
	}
}
