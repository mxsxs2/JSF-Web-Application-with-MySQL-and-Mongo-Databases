package com.geog.Controlller;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;

import com.geog.DAO.CityDAO;
import com.geog.Model.City;

@ManagedBean
@SessionScoped
public class CityController extends BasicController<CityDAO>{
	// @EJB
	
	// The full list of countries
	private List<City> CityList;
	
	// Variable for City update
	private City City;

	@PostConstruct
	/**
	 * Initialisation of the City DOA and pre load the City list
	 */
	public void init() {
		// Initialise the City dao
		this.setDao( new CityDAO());
		// Load the City list
		this.loadCityList();
	}

	/**
	 * Loads the entire City list from the database
	 */
	public void loadCityList() {
		// Load the City list
		this.CityList = this.getDao().list();
		//Check if there was any database connection error;
		this.checkDatabaseConnectionErrors();
		
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
			if (this.getDao().insert(City)) {
				// If it was successful
				this.setMessage( "<span class='success'>The City was added.</span>");
			}

		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			// Set the fail message if the country does not exists
			this.setMessage( "<span class='error'>Error -  " + City.getRegionCode() + " in " + City.getCountryCode()+ " does not exists.</span>");
		} catch (SQLException e) {
			e.printStackTrace();
			// Set the fail message if this City already exists
			this.setMessage( "<span class='error'>Error - " + City.getRegionCode() + "," + City.getCode() + " in "+ City.getCountryCode() + " already exists.</span>");
			//Check if there was any database connection error;
			this.getDao().setErrormessageIfConnectionLost(e);
			this.checkDatabaseConnectionErrors();
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
		boolean del = this.getDao().delete(City);
		// Delegate to dao
		if (del) {
			this.setMessage( "<span class='success'>The City was deleted.</span>");
			// Remove from the current list as well. No need for reloading the whole list
			// from the db
			this.CityList.remove(City);
		} else {
			this.setMessage( "<span class='error'>Error - Counld not delete.</span>");
		}
		//Check if there was any database connection error;
		this.checkDatabaseConnectionErrors();
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
		boolean upd = this.getDao().update(City);
		// Delegate to dao
		if (upd) {
			this.setMessage( "<span class='success'>The City was updated.</span>");
			// Remove from the current list as well. No need for reloading the whole list
		} else {
			this.setMessage( "<span class='error'>Error - Counld not update.</span>");
		}
		//Check if there was any database connection error;
		this.checkDatabaseConnectionErrors();
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
		City City = this.getDao().find(code);
		// Return the City if it is not null
		if (City != null) return City;
		this.setMessage( "<span class='error'>Error - Counld not find " + code + ".</span>");
		//Check if there was any database connection error;
		this.checkDatabaseConnectionErrors();
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
}
