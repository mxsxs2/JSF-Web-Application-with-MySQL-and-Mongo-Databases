package com.geog.DAO;

import java.sql.*;
import java.util.*;

import javax.annotation.PreDestroy;

import com.geog.Model.Country;

public class CountryDAO extends DAOFactory<Country> {
	// SQL Statements
	private static final String SELECT_ALL = "SELECT `co_code`,`co_name`,`co_details` FROM `country`";
	private static final String SELECT_ONE = "SELECT `co_code`,`co_name`,`co_details` FROM `country` WHERE `co_code` = ? LIMIT 1";
	private static final String DELETE_ONE = "DELETE FROM `country` WHERE  `co_code` = ? LIMIT 1";
	private static final String UPDATE_ONE = "UPDATE `country` SET `co_name`=?, `co_details`=? WHERE `co_code` = ? LIMIT 1";
	private static final String INSERT_ONE = "INSERT `country` (`co_code`,`co_name`,`co_details`) VALUES (?,?,?)";

	@Override
	public List<Country> list() {
		// Create an empty list
		List<Country> list = new ArrayList<Country>();

		// Try to Prepare the statement
		try (PreparedStatement ps = getMYSQLConnetion().prepareStatement(SELECT_ALL);) {
			// Execute the query
			ResultSet result = ps.executeQuery();
			// Loop the result set
			while (result.next()) {
				// Create a new country
				Country country = new Country();
				// Map the country details
				country.setCode(result.getString("co_code"));
				country.setName(result.getString("co_name"));
				country.setDetails(result.getString("co_details"));
				// Add the country to the list
				list.add(country);
			}
		} catch (SQLException e) {
			//Check if it was a connection lost error
			this.setErrormessageIfConnectionLost(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Return the list
		return list;
	}

	@Override
	public Country find(String id) {
		// Try to Prepare the statement
		try (PreparedStatement ps = getMYSQLConnetion().prepareStatement(SELECT_ONE)) {
			// Set the id in the statement
			ps.setString(1, id);
			// Get the result
			ResultSet result = ps.executeQuery();
			// Check if there is any result
			if (result.next()) {
				// Create a new country
				Country country = new Country();
				// Map the details
				country.setCode(result.getString("co_code"));
				country.setName(result.getString("co_name"));
				country.setDetails(result.getString("co_details"));
				// Return the country
				return country;
			}
		} catch (SQLException e) {
			//Check if it was a connection lost error
			this.setErrormessageIfConnectionLost(e);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Return null if there was no result
		return null;
	}

	@Override
	public boolean update(Country object) {
		// Check if the Object is the right type
		if (object != null && object instanceof Country) {
			// Cast the object
			Country country = (Country) object;
			// Try to Prepare the statement
			try (PreparedStatement ps = getMYSQLConnetion().prepareStatement(UPDATE_ONE)) {
				// Set the details
				ps.setString(1, country.getName());
				ps.setString(2, country.getDetails()==null ? "": country.getDetails()); //Make sure the details is not null
				ps.setString(3, country.getCode());

				// Run the update and return the result
				return 1 == ps.executeUpdate();
			} catch (SQLException e) {
				//Check if it was a connection lost error
				this.setErrormessageIfConnectionLost(e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Return null if there was no result
		return false;
	}

	@Override
	public boolean delete(Country object) {
		// Check if the Object is the right type
		if (object != null && object instanceof Country) {
			// Cast the object
			Country country = (Country) object;
			// Try to Prepare the statement
			try (PreparedStatement ps = getMYSQLConnetion().prepareStatement(DELETE_ONE)) {
				// Set the details
				ps.setString(1, country.getCode());

				// Run the update and return the result
				return 1 == ps.executeUpdate();
			} catch (SQLException e) {
				//Check if it was a connection lost error
				this.setErrormessageIfConnectionLost(e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Return null if there was no result
		return false;
	}

	@Override
	public boolean insert(Country object) {
		// Check if the Object is the right type
		if (object != null && object instanceof Country) {
			// Cast the object
			Country country = (Country) object;
			// Try to Prepare the statement
			try (PreparedStatement ps = getMYSQLConnetion().prepareStatement(INSERT_ONE)) {
				// Set the details
				ps.setString(1, country.getCode());
				ps.setString(2, country.getName());
				ps.setString(3, country.getDetails()==null ? "": country.getDetails()); //Make sure the details is not null
				// Run the update and return the result
				return 1 == ps.executeUpdate();

			} catch (SQLException e) {
				//Check if it was a connection lost error
				this.setErrormessageIfConnectionLost(e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Return null if there was no result
		return false;
	}

	@PreDestroy
	public void closeConnections() {
		super.closeMYSQLConnection();
	}
}
