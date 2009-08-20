/*

	Copyright 2009 Jason Jones

	This file is part of Tieout.

    Tieout is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Tieout is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Tieout.  If not, see <http://www.gnu.org/licenses/>.

*/

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
