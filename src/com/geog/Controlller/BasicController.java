package com.geog.Controlller;

import com.geog.DAO.DAOFactory;

public abstract class BasicController<E> {
	// Form message
	private String message;
	// The City data source Object
	private E dao;

	/**
	 * Returns the controller message
	 * 
	 * @return String - message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the controller message
	 * 
	 * @param String
	 *            - message
	 */
	protected void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Clears the controller message
	 */
	public void clearMessage() {
		this.message = "";
	}

	/**
	 * Returns the Data Acess Object
	 * 
	 * @return E - Data Access Object
	 */
	protected E getDao() {
		return dao;
	}

	/**
	 * Sets the Data Acess Object
	 * 
	 * @return E - Data Access Object
	 */
	protected void setDao(E dao) {
		this.dao = dao;
	}

	/**
	 * Checks if there was any database connections. Sets the error message.
	 */
	@SuppressWarnings("unchecked")
	public void checkDatabaseConnectionErrors() {
		// Check if there is mysql connection
		if (((DAOFactory<E>) this.dao).isLostMysql()) {
			this.message = "<span class='error'>Error - Cannot connect to Database</span>";
		}
		// Check if there is mongoDB connection
		if (((DAOFactory<E>) this.dao).isLostMongoDB()) {
			this.message = "<span class='error'>Error - Cannot connect to Mongo Database</span>";
		}
	}
}
