package com.geog.Controlller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import com.mongodb.MongoException;
import com.geog.DAO.CountryDAO;
import com.geog.DAO.HeadOfStateDAO;
import com.geog.Model.HeadOfState;

@ManagedBean
@SessionScoped
public class HeadOfStateController extends BasicController<HeadOfStateDAO> {
	// The full list of countries
	private List<HeadOfState> HeadOfStateList;
	// Variable for HeadOfState update
	private HeadOfState HeadOfState;

	@PostConstruct
	/**
	 * Initialisation of the HeadOfState DOA and pre load the HeadOfState list
	 */
	public void init() {
		// Initialise the HeadOfState dao
		this.setDao(new HeadOfStateDAO());
		// Load the HeadOfState list
		this.loadHeadOfStateList();
	}

	/**
	 * Loads the entire HeadOfState list from the database
	 */
	public void loadHeadOfStateList() {
		// Load the HeadOfState list
		this.HeadOfStateList = this.getDao().list();
		//Check if there was any database connection error;
		this.checkDatabaseConnectionErrors();
	}

	/**
	 * Method used to acquire the full list of countries
	 * 
	 * @return List<HeadOfState> - Full list of countries
	 */
	public List<HeadOfState> getHeadOfStateList() {
		return this.HeadOfStateList;
	}

	/**
	 * Function used to add a HeadOfState to the database. It sets a message into
	 * the message instance variable
	 * 
	 * @param HeadOfState-
	 *            HeadOfState to add to database
	 * @return boolean
	 */
	public void addHeadOfState(HeadOfState HeadOfState) {
		// Delegate to dao
		try {
			// Get the country dao
			CountryDAO cdao = new CountryDAO();
			// Check if the country exists. 
			if (cdao.find(HeadOfState.getCode())==null) {
				// Set the fail message if the country does not exists
				this.setMessage( "<span class='error'>Error -  Country " + HeadOfState.getCode()+ " in  does not exists.</span>");
				//Dont go forward
				return;
			}

			// Try to insert the HeadOfState
			if (this.getDao().insert(HeadOfState)) {
				// If it was successful
				this.setMessage( "<span class='success'>The HeadOfState was added.</span>");
			}
		} catch (MongoException e) {
			e.printStackTrace();
			// Set the fail message if this HeadOfState already exists
			this.setMessage( "<span class='error'>Error - Country " + HeadOfState.getCode() + " already has a head of state.</span>");
			//Check if it was a connection lost error
			this.getDao().setErrormessageIfConnectionLost(e);
		}
		
		//Check if there was any database connection error;
		this.checkDatabaseConnectionErrors();

	}

	/**
	 * Function used to delete a HeadOfState. It sets a message into the message
	 * instance variable
	 * 
	 * @param HeadOfState
	 *            - HeadOfState object to delete
	 * @return boolean - Whether the HeadOfState was deleted or not
	 */
	public boolean deleteHeadOfState(HeadOfState HeadOfState) {
		boolean del = this.getDao().delete(HeadOfState);
		// Delegate to dao
		if (del) {
			this.setMessage( "<span class='success'>The HeadOfState was deleted.</span>");
			// Remove from the current list as well. No need for reloading the whole list
			// from the db
			this.HeadOfStateList.remove(HeadOfState);
		} else {
			this.setMessage( "<span class='error'>Error - Counld not delete.</span>");
		}
		//Check if there was any database connection error;
		this.checkDatabaseConnectionErrors();
		return del;
	}

	public HeadOfState getHeadOfState() {
		return HeadOfState;
	}

}
