package com.qmap.core.server.jena;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class BuildModel {

	static String fileURI = "file:client_resources/MO1-out.owl";


	public static OntModel newInstance() {
		OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF);
		m.read(fileURI);
		return m;
	}

}
