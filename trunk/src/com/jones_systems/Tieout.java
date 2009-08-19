package com.jones_systems;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.jdom.*;
import org.jdom.input.SAXBuilder;

/**
 * This is the main Tieout class. 
 * 
 * @author Jason JOnes
 * @version 0.10
 * 
 */

public class Tieout
{

	private Hashtable<String, TieoutConnection> connections;
	private Hashtable<String, TieoutVariableSet> varsets;
	private Vector<TieoutTest> tests;
	
	/**
	 * Creates a new Tieout object
	 * 
	 * @param tieoutFile	the file containing the XML for the tests
	 * 
	 */
	
    public Tieout(File tieoutFile)
    {

    	connections = new Hashtable<String, TieoutConnection>();
    	varsets     = new Hashtable<String, TieoutVariableSet>();
    	tests		= new Vector<TieoutTest>();
    	
        Document doc = null;

        SAXBuilder sb = new SAXBuilder();

        try {
            doc = sb.build(tieoutFile);
        }
        catch (JDOMException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Element root = doc.getRootElement();
        
        Element connections1 = root.getChild("connections");
    
        @SuppressWarnings("unchecked")
        List<Element> connections2 = connections1.getChildren();
       
        // Load all of the connections
        // each Element e will be a <connection> thing
        
        for (Element e: connections2) {
       	   	connections.put(e.getAttributeValue("id"), TieoutConnection.create(e));
        }
        
        Element readvarsets = root.getChild("variables");
        
        @SuppressWarnings("unchecked")
        List<Element> readvarsets2 = readvarsets.getChildren();
        
        for (Element e: readvarsets2) {
        	String varsetkey = e.getAttributeValue("id");
        	
        	@SuppressWarnings("unchecked")
        	List<Element> variables = e.getChildren();
        	TieoutVariableSet tvs = new TieoutVariableSet();
        	
        	for (Element var: variables) {
        		tvs.add(var.getAttributeValue("name"), var.getAttributeValue("value"));
        	}
        	
        	varsets.put(varsetkey, tvs);
        	
        }
        
        Element readTests = root.getChild("tests");
        
        @SuppressWarnings("unchecked")
        List<Element> readTests2 = readTests.getChildren();
        
        // load in the tests
        
        for (Element e: readTests2) {
       	
        	TieoutTest newtest = new TieoutTest(e.getAttributeValue("id"));
        	String[] varsetsToAdd = e.getChildText("varsets").split(","); // may need to split based on comma
        	
        	// cycle through the varsets and add to the new test as needed
        	for (String s : varsetsToAdd) {
        		if (varsets.containsKey(s)) {
        			newtest.addVarset(s, varsets.get(s));
        		} else {
        			// warning -- that variable set does not exist
        		}
        	}
       	
        	
            @SuppressWarnings("unchecked")
            List<Element> queries = e.getChildren("query");
            
            // read in the queries and add them to the test 
            // also give them their respective connection to use for executing
            for (Element query : queries) {
            	TieoutConnection connection = connections.get(query.getAttributeValue("connection"));
            	newtest.addQuery(connection, query.getValue());
            }
            
            tests.add(newtest);
            
        }
        
        
    }
    
    /**
     * Runs the tests
     */
    
    public void runTests() {

    	// for each test in my set of tests
    	for (TieoutTest test: tests) {
   			test.run();
    	}
    	
    }
    
    /**
     * Connects to all of the data sources that were loaded from the 
     * connection file
     * 
     * @return true if all connections were made
     */
    
    public boolean connectDatasources() {
    	
    	int failures = 0;
    	
    	for (String t: connections.keySet()) {
    		
    		System.out.print("Connecting " + t + "...");
    		boolean result = connections.get(t).connect(); 
    		if (result) {
    			System.out.println("success.");
    		} else {
    			System.out.println("fail.");
    			failures++;
    		}
    	}
    	
    	return (failures == 0);
    	
    }
    
	public static void main(String[] args) {

		String testFilename = null;
		
		Options options = new Options();
		
		//options.addOption("f", "filename", true, "the file to use for the Tieout check");
		Option filename = OptionBuilder.withArgName("filename").hasArg().withDescription("the file name of the XML test file").create("filename");
		options.addOption(filename);
		
		
		CommandLineParser parser = new GnuParser();
		 
		
		try {
			CommandLine line = parser.parse(options, args);
			
			if (line.hasOption("filename")) {
				testFilename = line.getOptionValue("filename");
			} else {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("tieout", options);
				System.exit(2);
			}
			
		} catch (ParseException exp) {
			
		}
		
		File testfile = new File(testFilename);

		if (!testfile.exists()) {
			System.err.println("Cannot find file!");
			System.exit(1);
		}
		
		System.out.println("Starting test with file: " + testFilename);
		
		Tieout ts = new Tieout(testfile);
		boolean connected = ts.connectDatasources();
		
		if (!connected) {
			System.err.print("Could not connect to all datasources");
			System.exit(1);
		}
		
		ts.runTests();
	
	}
    
}
