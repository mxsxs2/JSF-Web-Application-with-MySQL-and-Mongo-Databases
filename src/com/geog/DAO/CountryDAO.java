package com.geog.DAO;

import java.sql.*;
import java.util.*;


import com.geog.Model.Country;

public class CountryDAO extends DAOFactory{
	  
	  /**
	   * Method used to retrieve list of countries from the database
	   * @return
	   */
	  public List<Country> list(){
		  List<Country> list = new ArrayList<Country>();
	    
		  PreparedStatement ps;
			try {
			    ps = getConnetion().prepareStatement("SELECT * FROM `country`");
				ResultSet result =  ps.executeQuery();
				while(result.next()){
			    	Country country = new Country();
			    	country.setCode(result.getString("co_code"));
			    	country.setName(result.getString("co_name"));
			    	country.setDetails(result.getString("co_details"));
			    	list.add(country);
			    }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    return list;
	  }
}
