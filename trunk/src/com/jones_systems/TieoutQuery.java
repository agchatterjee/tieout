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

public class TieoutQuery {

	private String query;
	private TieoutConnection connection;
	
	public TieoutQuery(TieoutConnection connection, String query) {
		
		this.connection = connection;
		this.query = query;
		
	}
	
	public double run(TieoutVariableSet tvs) {

		String queryToRun = tvs.applyTo(query);
		return connection.doQuery(queryToRun);
		
	}
	
}
