package com.geog.DAO;

import java.sql.SQLException;
import java.util.*;

/**
 * Interface which defines the basic behaviour of every Data Access Object 
 * @author user
 *
 */
public interface DAOInterface<E>{

	/**
	 * Method used to find an row in the database by its primary key
	 * @param String - id of the city
	 * @return E - The found object or null
	 */
	public E find(String id);
	
	/**
	 * Method used to return a list of Objects from the database
	 * @return List<E> - The list of objects
	 */
	public List<E> list();
	
	/**
	 * Method used to update a row in the database by an object
	 * @param Object - Object to update
	 * @return boolean - Whether the update was successful or not
	 */
	public boolean update(E object);
	/**
	 * Method used to delete a row from the database by an object
	 * @param Object - Object to delete
	 * @return boolean - Whether the delete was successful or not
	 */
	public boolean delete(E object);
	
	/**
	 * Method used to insert a row from the database by an object
	 * @param Object - Object to insert
	 * @return boolean - Whether the insert was successful or not
	 * @throws SQLException 
	 */
	public boolean insert(E object) throws SQLException;
}
