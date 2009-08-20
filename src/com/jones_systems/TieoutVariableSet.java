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

import java.util.Hashtable;

public class TieoutVariableSet {

	private Hashtable<String, String> variables;
	
	public TieoutVariableSet() {
		variables = new Hashtable<String, String>();
	}
	
	public void add(String key, String value) {
		variables.put(key, value);		
	}
	
	public String toString() {
		
		return variables.toString();
		
	}
	
	public String applyTo(String query) {
		
		for (String s: variables.keySet()) {
			query = query.replace("%" + s + "%", variables.get(s));
		}
		
		return query;
		
	}
	
}
