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
	//The database connection
	private Connection connection;

	/**
	 * Constructor used to initialise the DAO factory. It loads the jdbc data source.
	 */	
	public DAOFactory() {
		super();
		// dependency/property injection).
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/geography");
			this.loadConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * Method used to return a database connection
	 * @return Connection - mysql database connection
	 */
	protected Connection getConnetion() {
		 return this.connection;
	}
	
	/**
	 * Method used to open a connection to the database
	 */
	private void loadConnection() {
		 try {	
			 	//Get the connection from the database source
				this.connection=ds.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	/**
	 * Method used to close the database connection when the class is done
	 */
	public void closeConnection() {
	    try {
			this.connection.close();
			System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
