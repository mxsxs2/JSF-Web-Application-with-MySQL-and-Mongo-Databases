package com.geog.Model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class CitySearch implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String populationCondition;
	private String population; //It is String so it is easier to check if the field was empty
	private String countryCode;
	private boolean isCoastal;
	
	
	
	public String getPopulationCondition() {
		return populationCondition;
	}
	public void setPopulationCondition(String populationCondition) {
		this.populationCondition = populationCondition;
	}
	public String getPopulation() {
		return population;
	}
	public void setPopulation(String population) {
		this.population = population;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public boolean isCoastal() {
		return isCoastal;
	}
	public void setCoastal(boolean isCoastal) {
		this.isCoastal = isCoastal;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
