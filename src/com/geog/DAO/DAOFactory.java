package com.geog.DAO;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class DAOFactory<E>  implements DAOInterface<E>{
	// resource injection
	// @Resource(name="jdbc/geography")
	private static DataSource ds;

	/**
	 * Constructor used to initialise the DAO factory. It loads the jdbc data source.
	 */	
	public DAOFactory() {
		super();
		System.out.println("initcalled");
		// dependency/property injection).
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/geography");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * Method used to return a database connection
	 * @return Connection - mysql database connection
	 */
	protected static Connection getConnetion() {
		  try {
				return ds.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		  return null;
	}

}
