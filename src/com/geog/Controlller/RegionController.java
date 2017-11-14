package com.geog.Controlller;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;

import com.geog.DAO.RegionDAO;
import com.geog.Model.Region;

@ManagedBean
@SessionScoped
public class RegionController {
	// @EJB
	// The Region data source Object
	private RegionDAO dao;
	// The full list of countries
	private List<Region> RegionList;
	// Form message
	private String message;
	// Variable for Region update
	private Region Region;

	@PostConstruct
	/**
	 * Initialisation of the Region DOA and pre load the Region list
	 */
	public void init() {
		// Initialise the Region dao
		this.dao = new RegionDAO();
		// Load the Region list
		this.loadRegionList();
	}

	/**
	 * Loads the entire Region list from the database
	 */
	public void loadRegionList() {
		// Load the Region list
		this.RegionList = this.dao.list();
	}

	/**
	 * Method used to acquire the full list of countries
	 * 
	 * @return List<Region> - Full list of countries
	 */
	public List<Region> getRegionList() {
		return this.RegionList;
	}

	/**
	 * Function used to add a Region to the database. It sets a message into the
	 * message instance variable
	 * 
	 * @param Region-
	 *            Region to add to database
	 * @return boolean
	 */
	public void addRegion(Region Region) {
		// Delegate to dao
		try {
			//Try to insert the region
			if (this.dao.insert(Region)) {
				//If it was successful
				this.message = "<span class='success'>The Region was added.</span>";
			}
			
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
			//Set the fail message if the country does not exists
			this.message = "<span class='error'>Error - Country "+Region.getCountryCode()+" does not exists.</span>";
		}catch (SQLException e) {
			//Set the fail message if this region already exists
			this.message = "<span class='error'>Error - "+Region.getCode()+" in "+Region.getCountryCode()+" already exists.</span>";
			return;
		}
		
	}

	/**
	 * Function used to delete a Region. It sets a message into the message
	 * instance variable
	 * 
	 * @param Region
	 *            - Region object to delete
	 * @return boolean - Whether the Region was deleted or not
	 */
	public boolean deleteRegion(Region Region) {
		boolean del = this.dao.delete(Region);
		// Delegate to dao
		if (del) {
			this.message = "<span class='success'>The Region was deleted.</span>";
			// Remove from the current list as well. No need for reloading the whole list
			// from the db
			this.RegionList.remove(Region);
		} else {
			this.message = "<span class='error'>Error - Counld not delete.</span>";
		}
		return del;
	}

	/**
	 * Function used to update a Region. It sets a message into the message
	 * instance variable
	 * 
	 * @param Region
	 *            - Region object to update
	 * @return boolean - Whether the Region was deleted or not
	 */
	public boolean updateRegion(Region Region) {
		boolean upd = this.dao.update(Region);
		// Delegate to dao
		if (upd) {
			this.message = "<span class='success'>The Region was updated.</span>";
			// Remove from the current list as well. No need for reloading the whole list
		} else {
			this.message = "<span class='error'>Error - Counld not update.</span>";
		}
		return upd;
	}

	/**
	 * Function used to find a Region
	 * 
	 * @param String
	 *            - the Region code
	 * @return Region - the found Region or a new instance
	 */
	public Region findRegion(String code) {
		// Get the Region
		Region Region = this.dao.find(code);
		// Return the Region if it is not null
		if (Region != null)
			return Region;
		this.message = "<span class='error'>Error - Counld not find " + code + ".</span>";
		// Return an empty Region if it was not found
		return new Region("","", "", "");
	}

	/**
	 * Loads a Region to the instance Region variable. It either loads the Region
	 * or creates an empty Region
	 * 
	 * @param String
	 *            - the Region code
	 */
	public void loadRegion(String code) {
		this.Region = this.findRegion(code);
	}

	public Region getRegion() {
		return Region;
	}

	public String getMessage() {
		return message;
	}

	public void clearMessage() {
		this.message = "";
	}

}
