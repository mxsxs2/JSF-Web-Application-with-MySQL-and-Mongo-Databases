package com.geog.DAO;

import java.sql.*;
import java.util.*;
import javax.annotation.PreDestroy;
import com.geog.Model.City;

public class CityDAO extends DAOFactory<City> {
	// SQL Statements
	private static final String SELECT_ALL = "SELECT `co_code`,`reg_code`,`cty_code`,`cty_name`,`population`,`isCoastal`,`areaKM` FROM `City`";
	private static final String SELECT_ONE = "SELECT `co_code`,`reg_code`,`cty_code`,`cty_name`,`population`,`isCoastal`,`areaKM` FROM `City` WHERE `cty_code` = ? LIMIT 1";
	private static final String DELETE_ONE = "DELETE FROM `City` WHERE `cty_code` = ? LIMIT 1";
	//private static final String UPDATE_ONE = "UPDATE `City` SET `cty_name`=?, `reg_desc`=? WHERE `cty_code` = ? LIMIT 1";
	private static final String INSERT_ONE = "INSERT `City` (`co_code`,`reg_code`,`cty_code`,`cty_name`,`population`,`isCoastal`,`areaKM`) VALUES (?,?,?,?,?,?,?)";

	@Override
	public List<City> list() {
		// Create an empty list
		List<City> list = new ArrayList<City>();

		// Try to Prepare the statement
		try (PreparedStatement ps = getConnetion().prepareStatement(SELECT_ALL);) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Return the list
		return list;
	}

	@Override
	public City find(String id) {
		// Try to Prepare the statement
		try (PreparedStatement ps = getConnetion().prepareStatement(SELECT_ONE)) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Return null if there was no result
		return null;
	}

	@Override
	public boolean update(City object) {
		/*// Check if the Object is the right type
		if (object != null && object instanceof City) {
			// Cast the object
			City City = (City) object;
			// Try to Prepare the statement
			try (PreparedStatement ps = getConnetion().prepareStatement(UPDATE_ONE)) {
				// Set the details
				ps.setString(1, City.getName());
				ps.setString(2, City.getDescription() == null ? "" : City.getDescription()); // Make sure the
																									// details is not
																									// null
				ps.setString(3, City.getCode());

				// Run the update and return the result
				return 1 == ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
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
			try (PreparedStatement ps = getConnetion().prepareStatement(DELETE_ONE)) {
				// Set the details
				ps.setString(1, City.getCode());

				// Run the update and return the result
				return 1 == ps.executeUpdate();
			} catch (SQLException e) {
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
			PreparedStatement ps = getConnetion().prepareStatement(INSERT_ONE);
			// Set the details
			ps.setString(1, City.getCountryCode());
			ps.setString(2, City.getRegionCode());
			ps.setString(3, City.getCode());
			ps.setString(4, City.getName());
			ps.setInt(5, City.getPopulation()<0 ? 0 : City.getPopulation());
			ps.setBoolean(6, City.isCoastal());
			ps.setFloat(7, City.getAreaKM() <0 ? 0 : City.getAreaKM()); // Make sure the details is not null
			// Run the update and return the result
			return 1 == ps.executeUpdate();

		}
		// Return null if there was no result
		return false;
	}

	@PreDestroy
	public void closeConnections() {
		this.closeConnection();
	}
}
