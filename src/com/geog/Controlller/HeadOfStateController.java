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
public class HeadOfStateController {
	// @EJB
	// The HeadOfState data source Object
	private HeadOfStateDAO dao;
	// The full list of countries
	private List<HeadOfState> HeadOfStateList;
	// Form message
	private String message;
	// Variable for HeadOfState update
	private HeadOfState HeadOfState;

	@PostConstruct
	/**
	 * Initialisation of the HeadOfState DOA and pre load the HeadOfState list
	 */
	public void init() {
		// Initialise the HeadOfState dao
		this.dao = new HeadOfStateDAO();
		// Load the HeadOfState list
		this.loadHeadOfStateList();
	}

	/**
	 * Loads the entire HeadOfState list from the database
	 */
	public void loadHeadOfStateList() {
		// Load the HeadOfState list
		this.HeadOfStateList = this.dao.list();
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
				this.message = "<span class='error'>Error -  Country " + HeadOfState.getCode()+ " in  does not exists.</span>";
				//Dont go forward
				return;
			}

			// Try to insert the HeadOfState
			if (this.dao.insert(HeadOfState)) {
				// If it was successful
				this.message = "<span class='success'>The HeadOfState was added.</span>";
			}
		} catch (MongoException e) {
			e.printStackTrace();
			// Set the fail message if this HeadOfState already exists
			this.message = "<span class='error'>Error - Country " + HeadOfState.getCode() + " already has a head of state.</span>";
		}

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
		boolean del = this.dao.delete(HeadOfState);
		// Delegate to dao
		if (del) {
			this.message = "<span class='success'>The HeadOfState was deleted.</span>";
			// Remove from the current list as well. No need for reloading the whole list
			// from the db
			this.HeadOfStateList.remove(HeadOfState);
		} else {
			this.message = "<span class='error'>Error - Counld not delete.</span>";
		}
		return del;
	}

	public HeadOfState getHeadOfState() {
		return HeadOfState;
	}

	public String getMessage() {
		return message;
	}

	public void clearMessage() {
		this.message = "";
	}

}
