package com.qmap.core.server.jena;

import java.util.ArrayList;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;

public class BuildLocationObjects {

	Individual shape;
	Individual location;

	OntModel m;

	static final String uriPrefix = "http://www.semanticweb.org/ontologies/2012/1/Ontology1328444427428.owl#";

	public BuildLocationObjects(String id, OntModel m, ArrayList<Individual> pointArray,
			String name, String desc, int area, int type, String cls) {

		this.m = m;

		// Assigns to appropriate individual
		
		switch (type) {
		case 1:
			// create individuals
			location = m.createIndividual(uriPrefix + name,
					m.getResource(uriPrefix + cls));

			shape = m.createIndividual(uriPrefix + name + "_Shape",
					m.getResource(uriPrefix + "Polygon"));
			shape.addLiteral(m.getProperty(uriPrefix + "hasArea"), area);

			for (int x = 0; x < pointArray.size(); x++) {

				shape.addProperty(m.getProperty(uriPrefix + "hasPoints"),
						pointArray.get(x));
			}

			break;

		case 3:

			location = m.createIndividual(uriPrefix + name,
					m.getResource(uriPrefix + cls));

			shape = m.createIndividual(uriPrefix + name + "_Shape",
					m.getResource(uriPrefix + "Polygon"));
			shape.addLiteral(m.getProperty(uriPrefix + "hasArea"), area);

			for (int x = 0; x < pointArray.size(); x++) {
				System.out.println("Pnts: " + x);
				shape.addProperty(m.getProperty(uriPrefix + "hasPoints"),
						pointArray.get(x));
			}

			break;

		case 2:
			location = m.createIndividual(uriPrefix + name,
					m.getResource(uriPrefix + cls));

			shape = m.createIndividual(uriPrefix + name + "_Shape",
					m.getResource(uriPrefix + "Polyline"));

			for (int x = 0; x < pointArray.size(); x++) {
				shape.addProperty(m.getProperty(uriPrefix + "hasPoints"),
						pointArray.get(x));
			}

			break;

		default:
			System.out.println("Some error has occurred");
		}

		location.addLiteral(m.getProperty(uriPrefix + "hasDescription"), desc);
		location.addLiteral(m.getProperty(uriPrefix + "hasTitle"), name);

		// Coordinate properties
		location.addProperty(m.getProperty(uriPrefix + "hasShape"), shape);
		
		//Add owner reference
		location.addLiteral(m.createProperty(uriPrefix+"ownerReference"), id);
		
		
	}

	public OntModel getModel() {

		return m;

	}
}
