package com.geog.DAO;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.client.MongoDatabase;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;

public abstract class DAOFactory<E> implements DAOInterface<E> {
	// The MYSQL database connection
	private Connection MYSQLconnection;
	// Get the mongoDB database
	private MongoDatabase mongodatabase;
	//Flag if the db connection was lost
	private boolean lostMysql=false;
	private boolean lostMongoDB=false;
	
	
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
			this.lostMysql=true;
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
			this.lostMongoDB=true;
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
	
	
	/**
	 * Checks if a given exceptions is about the database connection. If it is then it sets the appropriate flag
	 * @param Exception
	 */
	public void setErrormessageIfConnectionLost(Exception e) {
		//Check if the mysl connection is lost
		if(MySQLNonTransientConnectionException.class.isInstance(e) || CommunicationsException.class.isInstance(e)) {
			//Set the flag
			this.lostMysql=true;
		}
		//check if the mongodb connection is lost
		if(MongoSocketOpenException.class.isInstance(e)) {
			//Set the flag
			this.lostMongoDB=true;
		}
	}
	/**
	 * Check if the mongoDB connection is available
	 * @return boolean
	 */
	protected boolean checkMongoDBConnection() {
		//Reset the flag
		this.lostMongoDB=false;
		//Try to run a ping command to check if the db is alive
		try {
			this.mongodatabase.runCommand(new BasicDBObject("ping", "1"));
			return true;
		}catch(Exception e){
		}
		//Set the flag as the database connection is lost
		this.lostMongoDB=true;
		return false;
	}
	
	/**
	 * Resets the database connection flags
	 */
	public void resetDatabaseConnectionErrors() {
		this.lostMysql=false;
		this.lostMongoDB=false;
	}
	/**
	 * Returns the status of the mysql connection
	 * @return boolean
	 */
	public boolean isLostMysql() {
		return lostMysql;
	}

	/**
	 * Returns the status of the mongoDB connection
	 * @return boolean
	 */
	public boolean isLostMongoDB() {
		return lostMongoDB;
	}
}
