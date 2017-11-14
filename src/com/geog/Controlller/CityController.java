package com.geog.Controlller;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;

import com.geog.DAO.CityDAO;
import com.geog.Model.City;

@ManagedBean
@SessionScoped
public class CityController {
	// @EJB
	// The City data source Object
	private CityDAO dao;
	// The full list of countries
	private List<City> CityList;
	// Form message
	private String message;
	// Variable for City update
	private City City;

	@PostConstruct
	/**
	 * Initialisation of the City DOA and pre load the City list
	 */
	public void init() {
		// Initialise the City dao
		this.dao = new CityDAO();
		// Load the City list
		this.loadCityList();
	}

	/**
	 * Loads the entire City list from the database
	 */
	public void loadCityList() {
		// Load the City list
		this.CityList = this.dao.list();
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
	 * Function used to add a City to the database. It sets a message into the
	 * message instance variable
	 * 
	 * @param City-
	 *            City to add to database
	 * @return boolean
	 */
	public void addCity(City City) {
		// Delegate to dao
		try {
			// Try to insert the City
			if (this.dao.insert(City)) {
				// If it was successful
				this.message = "<span class='success'>The City was added.</span>";
			}

		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			// Set the fail message if the country does not exists
			this.message = "<span class='error'>Error -  " + City.getRegionCode() + " in " + City.getCountryCode()
					+ " does not exists.</span>";
		} catch (SQLException e) {
			// Set the fail message if this City already exists
			this.message = "<span class='error'>Error - " + City.getRegionCode() + "," + City.getCode() + " in "
					+ City.getCountryCode() + " already exists.</span>";
			return;
		}

	}

	/**
	 * Function used to delete a City. It sets a message into the message instance
	 * variable
	 * 
	 * @param City
	 *            - City object to delete
	 * @return boolean - Whether the City was deleted or not
	 */
	public boolean deleteCity(City City) {
		boolean del = this.dao.delete(City);
		// Delegate to dao
		if (del) {
			this.message = "<span class='success'>The City was deleted.</span>";
			// Remove from the current list as well. No need for reloading the whole list
			// from the db
			this.CityList.remove(City);
		} else {
			this.message = "<span class='error'>Error - Counld not delete.</span>";
		}
		return del;
	}

	/**
	 * Function used to update a City. It sets a message into the message instance
	 * variable
	 * 
	 * @param City
	 *            - City object to update
	 * @return boolean - Whether the City was deleted or not
	 */
	public boolean updateCity(City City) {
		boolean upd = this.dao.update(City);
		// Delegate to dao
		if (upd) {
			this.message = "<span class='success'>The City was updated.</span>";
			// Remove from the current list as well. No need for reloading the whole list
		} else {
			this.message = "<span class='error'>Error - Counld not update.</span>";
		}
		return upd;
	}

	/**
	 * Function used to find a City
	 * 
	 * @param String
	 *            - the City code
	 * @return City - the found City or a new instance
	 */
	public City findCity(String code) {
		// Get the City
		City City = this.dao.find(code);
		// Return the City if it is not null
		if (City != null)
			return City;
		this.message = "<span class='error'>Error - Counld not find " + code + ".</span>";
		// Return an empty City if it was not found
		return new City("", "", "", "", 0, false, (float) 0);
	}

	/**
	 * Loads a City to the instance City variable. It either loads the City or
	 * creates an empty City
	 * 
	 * @param String
	 *            - the City code
	 */
	public void loadCity(String code) {
		this.City = this.findCity(code);
	}

	public City getCity() {
		return City;
	}

	public String getMessage() {
		return message;
	}

	public void clearMessage() {
		this.message = "";
	}

}
