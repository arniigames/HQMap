package com.qmap.core.server.jena;

import java.io.FileOutputStream;
import java.io.IOException;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class AddNewPropertyToModel {

	static final String uriPrefix = "http://www.semanticweb.org/ontologies/2012/1/Ontology1328444427428.owl#";
	static final String rdfTypeUri = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

	public static boolean process(String sub, String pred, String obj) {

		OntModel m = BuildModel.newInstance();

		Individual s = m.getIndividual(uriPrefix + sub);

		ExtendedIterator<OntClass> sit = s.listOntClasses(true);

		OntClass s1 = null;

		while (sit.hasNext()) {
			OntClass temp = sit.next();
			if (!temp
					.equals(m
							.getOntClass("http://www.w3.org/2002/07/owl#NamedIndividual"))) {
				s1 = temp;
			}
		}

		Individual o = m.getIndividual(uriPrefix + obj);

		ExtendedIterator<OntClass> oit = o.listOntClasses(true);
		
		

		OntClass o1 = null;

		while (oit.hasNext()) {
			OntClass temp = oit.next();
			if (!temp
					.equals(m
							.getOntClass("http://www.w3.org/2002/07/owl#NamedIndividual"))) {
				o1 = temp;
			}
		}

		OntProperty p = m.createObjectProperty(uriPrefix + pred);

		p.setDomain(s1);
		p.setRange(o1);

		s.addProperty(p, o);

		try {
			// Write model to file.

			FileOutputStream fout = new FileOutputStream("owl/MO1-out.owl");
			m.write(fout);

			System.out.println("Model Written");

			fout.close();

		} catch (IOException e) {
			System.out.println("Exception caught" + e.getMessage());
			m.close();
			return false;
		}
		m.close();

		return true;
	}

}
