package com.geog.Model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class City  implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String code;
	private String countryCode;
	private String regionCode;
	private String name;
	private int population;
	private boolean coastal;
	private float areaKM;
	
	public City() {
		super();
	}
	public City(String code, String countryCode, String regionCode, String name, int population, boolean coastal,
			float areaKM) {
		super();
		this.code = code;
		this.countryCode = countryCode;
		this.regionCode = regionCode;
		this.name = name;
		this.population = population;
		this.coastal = coastal;
		this.areaKM = areaKM;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public boolean isCoastal() {
		return coastal;
	}
	public void setCoastal(boolean coastal) {
		this.coastal = coastal;
	}
	public float getAreaKM() {
		return areaKM;
	}
	public void setAreaKM(float areaKM) {
		this.areaKM = areaKM;
	}
	

}
