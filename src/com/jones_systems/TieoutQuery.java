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
		//System.out.println("Executing query: " + queryToRun);
		return connection.doQuery(queryToRun);
		
	}
	
}
