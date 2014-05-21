package com.qmap.core.server.jena;

import java.io.FileOutputStream;
import java.io.IOException;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;

public class AddClassToModel {

	static final String uriPrefix = "http://www.semanticweb.org/ontologies/2012/1/Ontology1328444427428.owl#";
	static final String rdfTypeUri = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

	public static void process(String id, String nm, String ref) {

		OntModel m = BuildModel.newInstance();

		OntClass noc = m.createClass(uriPrefix + nm);
		
		noc.addLiteral(m.createProperty(uriPrefix+"ownerReference"), id);

		// noc.setRDFType(m.getResource(rdfTypeUri+"class"));

		OntClass o = m.getOntClass(uriPrefix + ref);
		o.addSubClass(noc);

		try {
			// Write model to file.
			
			FileOutputStream fout = new FileOutputStream("owl/MO1-out.owl");
			m.write(fout);
			
			System.out.println("Model Written");
			
			fout.close();
			m.close();
		} catch (IOException e) {
			System.out.println("Exception caught" + e.getMessage());
		}
		m.close();

	}

}
