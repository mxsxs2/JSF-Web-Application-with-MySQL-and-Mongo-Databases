package com.geog.DAO;

import java.sql.*;
import java.util.*;
import javax.annotation.PreDestroy;
import com.geog.Model.Region;

public class RegionDAO extends DAOFactory<Region> {
	// SQL Statements
	private static final String SELECT_ALL = "SELECT `co_code`,`reg_code`,`reg_name`,`reg_desc` FROM `region`";
	private static final String SELECT_ONE = "SELECT `co_code`,`reg_name`,`reg_desc` FROM `Region` WHERE `reg_code` = ? LIMIT 1";
	private static final String DELETE_ONE = "DELETE FROM `Region` WHERE  `reg_code` = ? LIMIT 1";
	private static final String UPDATE_ONE = "UPDATE `Region` SET `reg_name`=?, `reg_desc`=? WHERE `reg_code` = ? LIMIT 1";
	private static final String INSERT_ONE = "INSERT `Region` (`co_code`,`reg_code`,`reg_name`,`reg_desc`) VALUES (?,?,?,?)";

	@Override
	public List<Region> list() {
		// Create an empty list
		List<Region> list = new ArrayList<Region>();

		// Try to Prepare the statement
		try (PreparedStatement ps = getConnetion().prepareStatement(SELECT_ALL);) {
			// Execute the query
			ResultSet result = ps.executeQuery();
			// Loop the result set
			while (result.next()) {
				// Create a new Region
				Region Region = new Region();
				// Map the Region details
				Region.setCountryCode(result.getString("co_code"));
				Region.setCode(result.getString("reg_code"));
				Region.setName(result.getString("reg_name"));
				Region.setDescription(result.getString("reg_desc"));
				// Add the Region to the list
				list.add(Region);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Return the list
		return list;
	}

	@Override
	public Region find(String id) {
		// Try to Prepare the statement
		try (PreparedStatement ps = getConnetion().prepareStatement(SELECT_ONE)) {
			// Set the id in the statement
			ps.setString(1, id);
			// Get the result
			ResultSet result = ps.executeQuery();
			// Check if there is any result
			if (result.next()) {
				// Create a new Region
				Region Region = new Region();
				// Map the details
				Region.setCode(result.getString("reg_code"));
				Region.setName(result.getString("reg_name"));
				Region.setDescription(result.getString("reg_desc"));
				// Return the Region
				return Region;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Return null if there was no result
		return null;
	}

	@Override
	public boolean update(Region object) {
		// Check if the Object is the right type
		if (object != null && object instanceof Region) {
			// Cast the object
			Region Region = (Region) object;
			// Try to Prepare the statement
			try (PreparedStatement ps = getConnetion().prepareStatement(UPDATE_ONE)) {
				// Set the details
				ps.setString(1, Region.getName());
				ps.setString(2, Region.getDescription() == null ? "" : Region.getDescription()); // Make sure the
																									// details is not
																									// null
				ps.setString(3, Region.getCode());

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
	public boolean delete(Region object) {
		// Check if the Object is the right type
		if (object != null && object instanceof Region) {
			// Cast the object
			Region Region = (Region) object;
			// Try to Prepare the statement
			try (PreparedStatement ps = getConnetion().prepareStatement(DELETE_ONE)) {
				// Set the details
				ps.setString(1, Region.getCode());

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
	public boolean insert(Region object) throws SQLException {
		// Check if the Object is the right type
		if (object != null && object instanceof Region) {
			// Cast the object
			Region Region = (Region) object;
			// Try to Prepare the statement
			PreparedStatement ps = getConnetion().prepareStatement(INSERT_ONE);
			// Set the details
			ps.setString(1, Region.getCountryCode());
			ps.setString(2, Region.getCode());
			ps.setString(3, Region.getName());
			ps.setString(4, Region.getDescription() == null ? "" : Region.getDescription()); // Make sure the details is
																								// not null
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
