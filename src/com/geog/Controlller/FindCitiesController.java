package com.geog.Controlller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.geog.DAO.CityDAO;
import com.geog.Model.City;
import com.geog.Model.CitySearch;

@ManagedBean
@SessionScoped
public class FindCitiesController {
	// The City data source Object
	private CityDAO dao;
	// The full list of countries
	private List<City> CityList;
	// Form message
	private String message;

	// Model for the search
	private CitySearch citySearch;


	@PostConstruct
	/**
	 * Initialisation of the City DOA and pre load the City list
	 */
	public void init() {
		// Initialise the City dao
		this.dao = new CityDAO();
		// Load the City list
		this.loadCityList();
		//Create the city search object if it is empty
		if(this.citySearch==null) this.citySearch=new CitySearch();
	}

	/**
	 * Loads the entire City list from the database
	 */
	public void loadCityList() {
		// Load the City list
		this.CityList = this.dao.list();
	}	
	
	/**
	 * Loads list Cities by the defined search criteria from the database
	 */
	public void loadSearchedCityList() {
		// Load the City list
		this.CityList = this.dao.listSearch(this.citySearch);
	}

	/**
	 * Method used to acquire the full list of countries
	 * 
	 * @return List<City> - Full list of countries
	 */
	public List<City> getCityList() {
		return this.CityList;
	}
	/**
	 * Returns a message from the controller or from the DAO
	 * @return String - message
	 */
	public String getMessage() {
		return message;
	}
	
	public void clearMessage() {
		this.message = "";
	}

	public CitySearch getCitySearch() {
		return citySearch;
	}
	

}
