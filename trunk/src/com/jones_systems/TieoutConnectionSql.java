package com.jones_systems;

import org.jdom.*;
import java.sql.*;

/**
 * TieoutConnection with a relational backend
 * The XML file may specify a particular class/jar to load 
 * to support the underlying functionality
 * 
 * @author Jason Jones
 *
 */

public class TieoutConnectionSql extends TieoutConnection {

	private Connection dbConnection;
	private String jdbcClass;
	private Statement statement;
	
	/**
	 * Constructor takes a jdom element
	 * 
	 * @param Element e
	 */
	
	public TieoutConnectionSql(Element e) {
		
		type = "sql";
    	dsn = e.getChildText("connectstring");
    	username = e.getChildText("user");
    	password = e.getChildText("password");
    	jdbcClass = e.getChildText("jdbcclass");

	}
	
	/**
	 * the actual implentation of connecting to this data source
	 * 
	 * @return true if able to successfully connect
	 */
	
	public boolean connect() {

		try {
			Class.forName(jdbcClass); 
		} catch(Exception x) {
			System.err.println("Unable to load the driver class!");
			return false;
		}

		try {
			dbConnection = DriverManager.getConnection(dsn, username, password);
		} catch (SQLException e) {
			System.out.println("Couldn't get conn");
			System.out.println("error3: " + e.getMessage());
			return false;
		}

		try {
			statement = dbConnection.createStatement();
		} catch (SQLException e) {
			System.out.println("error3: " + e.getMessage());
		}
		
		
		return true;
		
	}
	
	public double doQuery(String query) {
		
		double result;
		
		 try {
			 
			 ResultSet rs = statement.executeQuery(query);
			 rs.next();

			 result = rs.getDouble(1); 
			 
		 } catch (SQLException e) {
			 
			 result = 0;
			 
		 }
		
		return result;
		
	}
	
}
