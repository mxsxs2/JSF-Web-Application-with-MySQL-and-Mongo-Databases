package com.geog.Model;

public class Country {
	private String code;
	private String name;
	private String varchar;
	
	
	
	public Country() {
		super();
	}

	public Country(String code, String name, String varchar) {
		super();
		this.code = code;
		this.name = name;
		this.varchar = varchar;
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

	public String getVarchar() {
		return varchar;
	}

	public void setVarchar(String varchar) {
		this.varchar = varchar;
	}
}
