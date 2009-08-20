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
