package com.qmap.core.server.jena;

import java.util.ArrayList;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;

public class BuildPoints {
	
	
	static final String uriPrefix = "http://www.semanticweb.org/ontologies/2012/1/Ontology1328444427428.owl#";
	ArrayList<Individual> pointArray = new ArrayList<Individual>();
	
	public ArrayList<Individual> getPoints(OntModel m, String[] cArr, String name){
		
		Individual pnt;
		Double latD = 0.0;
		Double lngD = 0.0;
		
		// Gives points unique reference
		int pntCount = 0;
		
		for (int x = 0; x < cArr.length; x++) {

			pntCount++;

			// Operator to distinguish odd and even (lat and lng)
			int mod = x % 2;

			// Initialize individuals
			if (mod == 0) {
				latD = Double.parseDouble(cArr[x]);

			} else {
				pntCount--;
				lngD = Double.parseDouble(cArr[x]);
				

				// Override point object
				pnt = m.createIndividual(uriPrefix + name + "_point_"
						+ pntCount, m.getResource(uriPrefix + "Point"));

				// Add literal values to point
				pnt.addLiteral(m.getProperty(uriPrefix + "latitude"), latD);
				pnt.addLiteral(m.getProperty(uriPrefix + "longitude"), lngD);
				
				

				// Add to point array
				pointArray.add(pnt);

			}
		}

		return pointArray;	
	}
}
