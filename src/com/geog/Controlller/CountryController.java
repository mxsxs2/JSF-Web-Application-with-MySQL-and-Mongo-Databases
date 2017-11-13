package com.geog.Controlller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;

import com.geog.DAO.CountryDAO;
import com.geog.Model.Country;

@ManagedBean
@ViewScoped
public class CountryController {
	// @EJB
	// The country data source Object
	private CountryDAO dao;
	// The full list of countries
	private List<Country> countryList;
	// Form message
	private String message;

	@PostConstruct
	/**
	 * Initilalization of the Country DOA and pre load the country list
	 */
	public void init() {
		System.out.println("Loaded");
		// Initialize the country dao
		this.dao = new CountryDAO();
		// Load the country list
		this.countryList = this.dao.list();
	}

	/**
	 * Method used to aquire the full list of countries
	 * 
	 * @return List<Country> - Full list of countries
	 */
	public List<Country> getCountryList() {
		return this.countryList;
	}

	/**
	 * Function used to add a country to the database
	 * 
	 * @param Country
	 *            - Country to add to database
	 * @return boolean
	 */
	public void addCountry(Country country) {
		// Delegate to dao
		if (this.dao.insert(country)) {
			this.message = "<span class='success'>The Country was added.</span>";
		} else {
			this.message = "<span class='error'>Error - This id already exists.</span>";
		}
	}

	public boolean deleteCountry(Country country) {
		boolean del=this.dao.delete(country);
		// Delegate to dao
		if (del) {
			this.message = "<span class='success'>The Country was deleted.</span>";
			//Remove from the current list as well. No need for reloading the whole list from the db
			this.countryList.remove(country);
		} else {
			this.message = "<span class='error'>Error - Counld not delete.</span>";
		}
		return del;
	}

	public String getMessage() {
		return message;
	}

	public void clearMessage() {
		this.message = "";
	}

}
