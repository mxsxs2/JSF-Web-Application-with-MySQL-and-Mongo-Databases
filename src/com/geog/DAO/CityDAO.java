package com.geog.DAO;

import java.sql.*;
import java.util.*;
import javax.annotation.PreDestroy;
import com.geog.Model.City;
import com.geog.Model.CitySearch;

public class CityDAO extends DAOFactory<City> {
	// SQL Statements
	private static final String SELECT_ALL = "SELECT `co_code`,`reg_code`,`cty_code`,`cty_name`,`population`,`isCoastal`,`areaKM` FROM `City`";
	private static final String SELECT_ONE = "SELECT `co_name` as `co_code`,`reg_name` as `reg_code`,`cty_code`,`cty_name`,`population`,`isCoastal`,`areaKM` "
										   + " FROM `city`,`country`,`region`"
                                           + " WHERE `city`.`reg_code`=`region`.`reg_code`"
										   + "   AND `region`.`co_code`=`country`.`co_code`"
                                           + "   AND `cty_code` = ? LIMIT 1";
	private static final String DELETE_ONE = "DELETE FROM `City` WHERE `cty_code` = ? LIMIT 1";
	// private static final String UPDATE_ONE = "UPDATE `City` SET `cty_name`=?,
	// `reg_desc`=? WHERE `cty_code` = ? LIMIT 1";
	private static final String INSERT_ONE = "INSERT `City` (`co_code`,`reg_code`,`cty_code`,`cty_name`,`population`,`isCoastal`,`areaKM`) VALUES (?,?,?,?,?,?,?)";

	@Override
	public List<City> list() {
		// Create an empty list
		List<City> list = new ArrayList<City>();

		// Try to Prepare the statement
		try (PreparedStatement ps = getMYSQLConnetion().prepareStatement(SELECT_ALL);) {
			// Execute the query
			ResultSet result = ps.executeQuery();
			// Loop the result set
			while (result.next()) {
				// Create a new City
				City City = new City();
				// Map the City details
				City.setCountryCode(result.getString("co_code"));
				City.setRegionCode(result.getString("reg_code"));
				City.setCode(result.getString("cty_code"));
				City.setName(result.getString("cty_name"));
				City.setPopulation(result.getInt("population"));
				City.setCoastal(result.getBoolean("isCoastal"));
				City.setAreaKM(result.getFloat("areaKM"));
				// Add the City to the list
				list.add(City);
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
	public City find(String id) {
		// Try to Prepare the statement
		try (PreparedStatement ps = getMYSQLConnetion().prepareStatement(SELECT_ONE)) {
			// Set the id in the statement
			ps.setString(1, id);
			// Get the result
			ResultSet result = ps.executeQuery();
			// Check if there is any result
			if (result.next()) {
				// Create a new City
				City City = new City();
				// Map the details
				City.setCountryCode(result.getString("co_code"));
				City.setRegionCode(result.getString("reg_code"));
				City.setCode(result.getString("cty_code"));
				City.setName(result.getString("cty_name"));
				City.setPopulation(result.getInt("population"));
				City.setCoastal(result.getBoolean("isCoastal"));
				City.setAreaKM(result.getFloat("areaKM"));
				// Return the City
				return City;
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
	public boolean update(City object) {
		/*
		 * // Check if the Object is the right type if (object != null && object
		 * instanceof City) { // Cast the object City City = (City) object; // Try to
		 * Prepare the statement try (PreparedStatement ps =
		 * getConnetion().prepareStatement(UPDATE_ONE)) { // Set the details
		 * ps.setString(1, City.getName()); ps.setString(2, City.getDescription() ==
		 * null ? "" : City.getDescription()); // Make sure the // details is not //
		 * null ps.setString(3, City.getCode());
		 * 
		 * // Run the update and return the result return 1 == ps.executeUpdate(); }
		 * catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */
		// Return null if there was no result
		return false;
	}

	@Override
	public boolean delete(City object) {
		// Check if the Object is the right type
		if (object != null && object instanceof City) {
			// Cast the object
			City City = (City) object;
			// Try to Prepare the statement
			try (PreparedStatement ps = getMYSQLConnetion().prepareStatement(DELETE_ONE)) {
				// Set the details
				ps.setString(1, City.getCode());

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
	public boolean insert(City object) throws SQLException {
		// Check if the Object is the right type
		if (object != null && object instanceof City) {
			// Cast the object
			City City = (City) object;
			// Try to Prepare the statement
			PreparedStatement ps = getMYSQLConnetion().prepareStatement(INSERT_ONE);
			// Set the details
			ps.setString(1, City.getCountryCode());
			ps.setString(2, City.getRegionCode());
			ps.setString(3, City.getCode());
			ps.setString(4, City.getName());
			ps.setInt(5, City.getPopulation() < 0 ? 0 : City.getPopulation());
			ps.setString(6, String.valueOf(City.isCoastal()));
			ps.setFloat(7, City.getAreaKM() < 0 ? 0 : City.getAreaKM()); // Make sure the details is not null
			// Run the update and return the result
			return 1 == ps.executeUpdate();

		}
		// Return null if there was no result
		return false;
	}

	/**
	 * Loads list of Cities by the defined search criteria from the database. Return
	 * the cities found or a empty list. If the CitySearch is null, it returns every
	 * city
	 * 
	 * @param CitySearch
	 *            - Object of the search criteria
	 * @return List<City> - List of the cities
	 */
	public List<City> listSearch(CitySearch object) {
		// Create an empty list
		List<City> list = new ArrayList<City>();
		// Flags whether a criteria was added to the sql string ot not
		boolean populationAdded = false;
		boolean countryCodeAdded = false;
		// Holder for the population value
		int population = 0;
		// If there is no criteria to search then return the whole list
		if (object == null) return this.list();

		// The simple select query
		StringBuilder sqlQueryBuilder = new StringBuilder();
		sqlQueryBuilder.append("SELECT `co_code`,`reg_code`,`cty_code`,`cty_name`,`population`,`isCoastal`,`areaKM` FROM `City` WHERE ");

		// Add the coastal condition. It is always set to either true or false
		sqlQueryBuilder.append("`isCoastal`= ?");

		// Check if the population is filled
		if (object.getPopulation() != null && !object.getPopulation().isEmpty()) {
			try {
				// Try to parse the population
				population = Integer.parseInt(object.getPopulation());

				// Add the appropriate population condition
				switch (object.getPopulationCondition()) {
					case "eq":
						sqlQueryBuilder.append(" AND `population`= ?");
						populationAdded = true;
						break;
					case "lt":
						sqlQueryBuilder.append(" AND `population`< ?");
						populationAdded = true;
						break;
					case "gt":
						sqlQueryBuilder.append(" AND `population`> ?");
						populationAdded = true;
						break;
				}
			} catch (java.lang.NumberFormatException e) {
				// Dont have to do anything
			}
		}

		// Check if the population is filled
		if (object.getCountryCode() != null && !object.getCountryCode().isEmpty()) {
			sqlQueryBuilder.append(" AND `co_code`=?");
			countryCodeAdded = true;
		}
		// Try to Prepare the statement
		try (PreparedStatement ps = getMYSQLConnetion().prepareStatement(sqlQueryBuilder.toString())) {

			// Set the details
			ps.setString(1, String.valueOf(object.isCoastal())); // Coastal value is always first
			// Index for the next details
			int index = 2;
			// If the population was added
			if (populationAdded) {
				ps.setInt(index, population);
				index++;
			}
			// If the population was added
			if (countryCodeAdded) {
				ps.setString(index, object.getCountryCode());
			}

			// Execute the query
			ResultSet result = ps.executeQuery();
			// Loop the result set
			while (result.next()) {
				// Create a new City
				City City = new City();
				// Map the City details
				City.setCountryCode(result.getString("co_code"));
				City.setRegionCode(result.getString("reg_code"));
				City.setCode(result.getString("cty_code"));
				City.setName(result.getString("cty_name"));
				City.setPopulation(result.getInt("population"));
				City.setCoastal(result.getBoolean("isCoastal"));
				City.setAreaKM(result.getFloat("areaKM"));
				// Add the City to the list
				list.add(City);
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

	@PreDestroy
	public void closeConnections() {
		super.closeMYSQLConnection();
	}
}
