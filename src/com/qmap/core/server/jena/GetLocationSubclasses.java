package com.qmap.core.server.jena;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class GetLocationSubclasses {

	static final String uriPrefix = "http://www.semanticweb.org/ontologies/2012/1/Ontology1328444427428.owl#";
	//
	OntModel m;  

	String xml = "<?xml version='1.0' encoding='UTF-8' ?>\n<Thing>";

	int level;

	//Gets model from JENA and pulls out Location resource.
	public GetLocationSubclasses() {
		
		m = BuildModel.newInstance();
		
		level = 1;

		OntClass r = m.getOntClass(uriPrefix + "Location");

		ExtendedIterator<OntClass> exOnt = r.listSubClasses(true);
		while (exOnt.hasNext()) {
			OntClass o = exOnt.next();

			while (IterateClass(o)) {

			}
		}
		xml +="</Thing>";
		//System.out.println(xml);

	}

	// Recursive method for building XML document for serialized retunr to
	// clientside
	private Boolean IterateClass(OntClass o) {

		for (int x = 0; x < level; x++) {
			xml += "\t";
		}
		level++;

		if (o.hasSubClass()) {
			xml += "<" + o.getLocalName() + ">\n";
			ExtendedIterator<OntClass> ex = o.listSubClasses(true);
			while (ex.hasNext()) {
				IterateClass(ex.next());
			}
			level--;
			for (int x = 0; x < level; x++) {
				xml += "\t";
			}
			xml += "</" + o.getLocalName() + ">\n";
			return false;
		} else {

			return true;
		}

	}

	// Access method
	public String getTree() {
		return xml;
	}

}
