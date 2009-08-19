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
