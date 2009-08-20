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
