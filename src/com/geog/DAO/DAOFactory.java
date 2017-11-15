package com.geog.DAO;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public abstract class DAOFactory<E> implements DAOInterface<E> {
	// The MYSQL database connection
	private Connection MYSQLconnection;
	// Get the mongoDB database
	private MongoDatabase mongodatabase;
	
	
	/**
	 * Constructor which loads the mysql connection
	 */
	public DAOFactory() {
		super();
		//Load the mysql connection
		this.loadMYSQLConnection();
	}
	
	
	/**
	 * Method used to return a mysql database connection
	 * 
	 * @return Connection - mysql database connection
	 */
	protected Connection getMYSQLConnetion() {
		return this.MYSQLconnection;
	}
	/**
	 * Method used to return a mongodb database connection
	 * 
	 * @return MongoDatabase - mongodb database connection
	 */
	public MongoDatabase getMongodatabase() {
		return mongodatabase;
	}


	/**
	 * Method used to open a connection to the database
	 */
	private void loadMYSQLConnection() {
		try {
			// dependency/property injection).
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/geography");
			// Get the connection from the database source
			this.MYSQLconnection = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Method used to load the mongodb database from datasource
	 */
	protected void loadMongoDBDatabase() {
		try {
			// dependency/property injection).
			Context ctx = new InitialContext();
			MongoClient ds = (MongoClient) ctx.lookup("java:comp/env/mongodb/headsOfStateDB");
			// Get the connection from the database source
			this.mongodatabase = ds.getDatabase("headsOfStateDB");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method used to close the database connection when the class is done
	 */
	public void closeMYSQLConnection() {
		try {
			this.MYSQLconnection.close();
			System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
