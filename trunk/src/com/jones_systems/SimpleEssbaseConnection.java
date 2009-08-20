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

}
