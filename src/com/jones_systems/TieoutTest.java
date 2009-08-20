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
import java.util.Vector;
import java.text.*;

public class TieoutTest {

	// since everything is a double and some weird float/rounding issues can appear,
	// use a penny as the max variance between the two 
	
	private static double MAX_VARIANCE = .01;
	
	private Vector<TieoutQuery> queries;
	private Hashtable<String, TieoutVariableSet> varsets;
	private String name;
	
	public TieoutTest(String testName) {

		name = testName;
		queries = new Vector<TieoutQuery>();
		varsets = new Hashtable<String, TieoutVariableSet>();
		
	}
	
	public void addVarset(String addTvsName, TieoutVariableSet addTvs) {

		varsets.put(addTvsName, addTvs);
		
	}
	
	public void addQuery(TieoutConnection connection, String queryText) {
		
		queries.add(new TieoutQuery(connection, queryText));
		
	}
	
	// running a test cycles through all of the variable sets
	// all varset combinations represent one whole test (true or false for success)
	public boolean run() {

		boolean result = true;
		
		// for each varset
		for (String s: varsets.keySet()) {
			
			TieoutVariableSet tvs = varsets.get(s);
			Vector<Double> testResults = new Vector<Double>();
			
			// for each query
			for (TieoutQuery query : queries) {
				testResults.add(query.run(tvs));
			}
		
			System.out.print(name + "\t" + varsets.get(s).toString() + "\t");

			if (allSame(testResults)) {
				
				System.out.print("good   ");
				
			} else {
				
				System.out.print("FAILURE");
				result = false;
				
			}
			
			/* TODO: handle how to show more than two entries */
			
			int max_show = 2;
			for (double d : testResults) {
				
				DecimalFormat myFormatter = new DecimalFormat("###,###,###,###.##");
			    String output = myFormatter.format(d);

			    System.out.print(String.format("%20s", output));
			    
				if (--max_show == 0) break;
			}
		
			System.out.println();
			
		}
		
		return result;
		
	}

	// scans the array and tells you if everything is the same
	private boolean allSame(Vector<Double> array) {
		
		Double val = array.get(0);
		
		for (Double d : array) {
			if (Math.abs(d - val) > MAX_VARIANCE) {
				return false;
			}
		}
		
		return true;
		
	}
	
	public String toString() {
		
		String result = "";
		
		result += "Varsets: ";
		
//		for (int i = 0; i < varsetsToUse.length; i++) {
//			result += varsetsToUse[i] + " ";
//		}
		
		result += "\n";
		
//		for (String s: queries.keySet()) {
//			result += "Query " + s + ": " + queries.get(s).getQuery() + "\n";
//		}
		
		return result;
		
	}
	
}
