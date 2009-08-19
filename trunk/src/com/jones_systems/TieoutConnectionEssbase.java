package com.jones_systems;

import org.jdom.*;

/**
 * A TieoutConnection with an Essbase backend
 * 
 */

public class TieoutConnectionEssbase extends TieoutConnection {

	private SimpleEssbaseConnection conn;
	private String analyticServer;
	private String app;
	private String cube;
	
	public TieoutConnectionEssbase(Element e) {

		type = "essbase";

    	dsn = e.getChildText("connectstring");
    	username = e.getChildText("user");
    	password = e.getChildText("password");
    	analyticServer = e.getChildText("analyticserver");
    	app = e.getChildText("essbaseapp");
    	cube = e.getChildText("essbasedb");
    	
	}
	
	public boolean connect() {
	
		conn = new SimpleEssbaseConnection(username, password, dsn, analyticServer, app, cube);
		
		if (!conn.isConnected()) {
			return false;
		}
		
		return true;
		
	}
	
	public double doQuery(String query) {

		double result;
		
		try {
			result = conn.performMdxQueryGetOne(query);
		} catch (Exception e) {
			result = 0;
		}
		
		return result;		
		
	}
	
}
