package com.jones_systems;

import org.jdom.*;

/**
 * Represents a connection to an underlying data source such as Essbase or
 * a relational database.
 * 
 * @author Jason Jones
 *
 */

public class TieoutConnection {

	protected String type;
	protected String dsn;
	protected String username;
	protected String password;
	
	protected boolean connected;
	
	public static TieoutConnection create(Element e) {
		
		String type = e.getChildText("type");
		
		if (type.equalsIgnoreCase("essbase")) {
			return new TieoutConnectionEssbase(e); 
		}
		
		return new TieoutConnectionSql(e);
		
	}

	public boolean connect() {
		System.out.println ("generic connecting!");
		return false;
	}

	public String toString() {
		return String.format("type: %s, dsn: %s, user: %s, pw: %s", type, dsn, username, password);
	}

	public double doQuery(String query) {
		return 0;
	}
	
}
