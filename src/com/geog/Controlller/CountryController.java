package com.geog.Controlller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;

import com.geog.DAO.CountryDAO;
import com.geog.Model.Country;

@ManagedBean
@SessionScoped
public class CountryController {
	// @EJB
	// The country data source Object
	private CountryDAO dao;
	// The full list of countries
	private List<Country> countryList;
	// Form message
	private String message;
	// Variable for country update
	private Country country;

	@PostConstruct
	/**
	 * Initialisation of the Country DOA and pre load the country list
	 */
	public void init() {
		// Initialise the country dao
		this.dao = new CountryDAO();
		// Load the country list
		this.loadCountryList();
	}

	/**
	 * Loads the entire country list from the database
	 */
	public void loadCountryList() {
		// Load the country list
		this.countryList = this.dao.list();
	}

	/**
	 * Method used to acquire the full list of countries
	 * 
	 * @return List<Country> - Full list of countries
	 */
	public List<Country> getCountryList() {
		return this.countryList;
	}

	/**
	 * Function used to add a country to the database. It sets a message into the
	 * message instance variable
	 * 
	 * @param Country-
	 *            Country to add to database
	 * @return boolean
	 */
	public void addCountry(Country country) {
		// Delegate to dao
		if (this.dao.insert(country)) {
			this.message = "<span class='success'>The Country was added.</span>";
		} else {
			this.message = "<span class='error'>Error - Country code "+country.getCode()+" already exists.</span>";
		}
	}

	/**
	 * Function used to delete a country. It sets a message into the message
	 * instance variable
	 * 
	 * @param country
	 *            - Country object to delete
	 * @return boolean - Whether the country was deleted or not
	 */
	public boolean deleteCountry(Country country) {
		boolean del = this.dao.delete(country);
		// Delegate to dao
		if (del) {
			this.message = "<span class='success'>The Country was deleted.</span>";
			// Remove from the current list as well. No need for reloading the whole list
			// from the db
			this.countryList.remove(country);
		} else {
			this.message = "<span class='error'>Error - Counld not delete.</span>";
		}
		return del;
	}

	/**
	 * Function used to update a country. It sets a message into the message
	 * instance variable
	 * 
	 * @param country
	 *            - Country object to update
	 * @return boolean - Whether the country was deleted or not
	 */
	public boolean updateCountry(Country country) {
		boolean upd = this.dao.update(country);
		// Delegate to dao
		if (upd) {
			this.message = "<span class='success'>The Country was updated.</span>";
			// Remove from the current list as well. No need for reloading the whole list
		} else {
			this.message = "<span class='error'>Error - Counld not update.</span>";
		}
		return upd;
	}

	/**
	 * Function used to find a country
	 * 
	 * @param String
	 *            - the country code
	 * @return Country - the found country or a new instance
	 */
	public Country findCountry(String code) {
		// Get the country
		Country country = this.dao.find(code);
		// Return the country if it is not null
		if (country != null)
			return country;
		this.message = "<span class='error'>Error - Counld not find " + code + ".</span>";
		// Return an empty country if it was not found
		return new Country("", "", "");
	}

	/**
	 * Loads a country to the instance country variable. It either loads the country
	 * or creates an empty country
	 * 
	 * @param String
	 *            - the country code
	 */
	public void loadCountry(String code) {
		this.country = this.findCountry(code);
	}

	public Country getCountry() {
		return country;
	}

	public String getMessage() {
		return message;
	}

	public void clearMessage() {
		this.message = "";
	}

}
