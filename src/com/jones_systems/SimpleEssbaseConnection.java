package com.jones_systems;

import com.essbase.api.base.*;
import com.essbase.api.session.*;
import com.essbase.api.dataquery.*;
import com.essbase.api.domain.*;

public class SimpleEssbaseConnection {
	
	// assumes that the domain is essbase since I've never seen it different
	private static String domainName = "essbase";

	private String analyticServer;
	private String app = null;
	private String cube = null;
	
	private IEssCubeView cv = null;
	private IEssbase ess = null;
	private IEssDomain dom = null;
	private boolean connected = false;
	
	public SimpleEssbaseConnection(String username, String password, String edsServer, String analyticServer, String app, String cube) {
	
		this.analyticServer = analyticServer;
		this.app = app;
		this.cube = cube;
		
		try {

			// Create JAPI instance.
			ess = IEssbase.Home.create(IEssbase.JAPI_VERSION);
	
			// Sign on to the domain, and perform MDX query operation.
			dom = ess.signOn(username, password, domainName, edsServer);
	
//			try{
//				cv = dom.openCubeView("Mdx Query Example", analyticServer, s_appName, s_cubeName, true, true, true, true);
//
//	
//				// NOTE: If you want to open a cube view using a connection pool
//				// name instead of specifying olapSvrName, appName and cubeName
//				// you can uncomment the following statement and comment the
//				// previous dom.openCubeView statement. Just make sure a connection
//				// pool with the specified name exists and is enabled in the
//				// enterprise server configuration.
//				// cv = dom.openCubeView("Mdx Query Example", "demoBasicPool", true);
//
//			} catch (Exception x) {
//				
//				System.out.println("Error: " + x.getMessage());
//                x.printStackTrace();
//                
//			} finally {
//				// Close cube view.
//				try {
//					if (cv != null) cv.close();
//				} catch (EssException x) {
//					System.err.println("Error: " + x.getMessage());
//				}
//			}
	
			//if (ess != null && ess.isSignedOn() == true) ess.signOff();
		
			connected = true;
			
		} catch (EssException x) {
			
			System.err.println("Error: " + x.getMessage());
			connected = false;
			
		}
		
		
		
	}
	
	protected void finalize() throws Throwable {
		
		System.err.println("blah");
		if (ess != null && ess.isSignedOn() == true) ess.signOff();
		
	}

	
	public boolean isConnected() {
		return connected;
	}
	
	public double performMdxQueryGetOne(String mdxquery) throws Exception {

		boolean bDataLess = false;
		boolean bNeedCellAttributes = false;

		cv = dom.openCubeView("Mdx Query Example", analyticServer, app, cube, true, true, true, true);
		
		IEssOpMdxQuery op = cv.createIEssOpMdxQuery();
	    op.setQuery(bDataLess, mdxquery, bNeedCellAttributes, IEssOpMdxQuery.EEssMemberIdentifierType.NAME);
	    cv.performOperation(op);
	    IEssMdDataSet mddata = cv.getMdDataSet();
	    return mddata.getCellValue(0);
		
	}
	
//	public void performMdxQuery(String mdxquery) throws Exception {
//
//		boolean bDataLess = false;
//		boolean bNeedCellAttributes = false;
//
//		cv = dom.openCubeView("Mdx Query Example", analyticServer, s_appName, s_cubeName, true, true, true, true);
//		
//		IEssOpMdxQuery op = cv.createIEssOpMdxQuery();
//	    op.setQuery(bDataLess, mdxquery, bNeedCellAttributes, IEssOpMdxQuery.EEssMemberIdentifierType.NAME);
//	    cv.performOperation(op);
//
//	    //IEssMdDataSet mddata = cv.getMdDataSet();
//	    //printMdDataSet(mddata);
//    
//	
//	}
	
//	private static void printMdDataSet(IEssMdDataSet mddata) throws Exception {
//	
//		//System.err.println("Num cells" + mddata.getCellCount());
//		//System.err.println("Cell: " + mddata.getCellValue(0));
//		
//		IEssMdAxis[] axes = mddata.getAllAxes();
//	
//		int nAxes = axes.length;
//	
//		System.out.println("Number of axes: " + nAxes);
//	
//	
//		//Get meta info about all the axes eg number of tuples in each axis,
//		//number of dimensions in each axis, and if the axis is a slicer axis.
//		for (int axisInd = 0; axisInd < nAxes; axisInd++) {
//			if(axes[axisInd].isSlicerAxis())
//				System.out.print("Slicer ");
//	
//			System.out.println("Axis " + axisInd + "  Number Of Tuples : " +
//							   axes[axisInd].getTupleCount()
//							   + " Number Of Dimensions : " +
//							   axes[axisInd].getDimensionCount());
//		}
//	
//	
//	
//		//Get the meta info about the dimensions represented in each axis.
//		//For each dimension in each axis, get dimension name and get
//		//property names and their data types that are fetched in the result
//		//set for each of the members of these dimensions.
//	
//		for (int axisInd = 0; axisInd < nAxes; axisInd++) {
//			IEssMdAxis axis = axes[axisInd];
//			int nTuples = axis.getTupleCount();
//	
//			//Get all the dimensions and their info in this axis
//			System.out.println("\nGetting dimensions in axis : " + axisInd);
//			IEssMdMember[] dims = axis.getAllDimensions();
//			for (int axisDimInd = 0; axisDimInd < dims.length; axisDimInd++) {
//				IEssMdMember dim = dims[axisDimInd];
//				int propscnt = dim.getCountProperties();
//				System.out.println("Dim " + axisDimInd + "  dim name : " +
//								   dim.getName() +
//								   " #props " + propscnt);
//	
//				for (int propInd = 0; propInd < propscnt; propInd++) {
//					System.out.println("Property " +  propInd + " Name : "  +
//									 dim.getPropertyName(propInd) + ",  Type : " +
//									 dim.getPropertyDataType(propInd));
//				}
//			}
//	
//	
//	
//			//Here is how you can get all the tuple members in an axis. Get all
//			// the members in each tuples, get member names and all their properties
//			//and property values.
//			System.out.println("\nGetting members in all the tuples of axis : " + axisInd);
//			for (int tupleInd = 0; tupleInd < nTuples; tupleInd++) {
//				System.out.println("\nTuple : " + tupleInd);
//				IEssMdMember[] mbrs = axis.getAllTupleMembers(tupleInd);
//				printMemberInfo(mbrs);
//			}
//		}
//	
//	
//	
//		//here is how you go through all the clusters to get all the tuples.
//		//First get all the clusters in each axis and then loop through them.
//		//Get all the members in each cluster. You can get the members for
//		//each of the dimension in the cluster or you can get the members
//		//in each of the tuple in the cluster. For all the members in each
//		//tuple, get member names and all their properties and property values.
//		System.out.println("\n\n\nPrinting results through clusters .....");
//		for (int axisInd = 0; axisInd < nAxes; axisInd++) {
//			IEssMdAxis axis = axes[axisInd];
//	
//			//Get all the clusters in this axis
//			IEssMdCluster[] clusters = axis.getAllClusters();
//			long nClusters = axis.getClusterCount();
//			System.out.println("\nAxis " + axisInd + "  Num clusters " + nClusters);
//	
//			for (int clusterInd = 0; clusterInd < nClusters; clusterInd++) {
//				IEssMdCluster cluster = clusters[clusterInd];
//				int clusterTupleCount = cluster.getTupleCount();
//	
//				//Get the members based on the dimension index
//				System.out.println("\nCluster " + clusterInd + " Size " + clusterTupleCount);
//				for (int dimInd = 0; dimInd < cluster.getDimensionCount();
//					 dimInd++) {
//					IEssMdMember[] mbrs = cluster.getAllDimensionMembers(dimInd);
//					System.out.println("Cluster Dim " + dimInd + " Number Of Members : " +
//									   mbrs.length);
//				}
//	
//				//Get the members based on tuple index
//				System.out.println("\nGetting members in all the tuples of this cluster...");
//				for (int tupleInd = 0; tupleInd < clusterTupleCount; tupleInd++) {
//					System.out.println("\nTuple : " + tupleInd);
//					printMemberInfo(cluster.getAllTupleMembers(tupleInd));
//				}
//			}
//		}
//	
//	
//		//Now get all the data cells in the result set. For each cell,
//		//check whether the cell is missing or no access cell and then
//		//get the double value of the cell.
//		System.out.println("\n\n\nGetting all the cells...");
//		System.out.println("Number of cells : " + mddata.getCellCount());
//	
//		for (int ulCellOrdinal = 0; ulCellOrdinal < mddata.getCellCount(); ulCellOrdinal++) {
//	
//			if (mddata.isMissingCell(ulCellOrdinal)) {
//				System.out.println("Cell[" + ulCellOrdinal + "] = #Missing");
//			}
//			else if (mddata.isNoAccessCell(ulCellOrdinal)) {
//				System.out.println("Cell[" + ulCellOrdinal + "] = #NoAccess");
//			}
//			else {
//				double val = mddata.getCellValue(ulCellOrdinal);
//				System.out.println("Cell[" + ulCellOrdinal + "] = " + val);
//			}
//		}
//	}
//	
//	private static void printMemberInfo(IEssMdMember[] members)
//		throws EssException {
//		for (int mbrInd = 0; mbrInd < members.length; mbrInd++) {
//			IEssMdMember member = members[mbrInd];
//	
//			System.out.println("Mbr " + mbrInd + " identifier " +
//							   member.getName());
//			int propcnt = member.getCountProperties();
//			for (int propInd = 0; propInd < propcnt; propInd++) {
//				IEssValueAny propval = member.getPropertyValueAny(propInd);
//				System.out.println("Property " +  propInd + " Name : "  +
//								   member.getPropertyName(propInd) + ",  Type : " +
//								   member.getPropertyDataType(propInd) +
//								   ",  Value : " + propval);
//			}
//		}
//	}

}
