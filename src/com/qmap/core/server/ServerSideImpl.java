package com.qmap.core.server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.qmap.core.client.ServerSide;
import com.qmap.core.server.jena.AddClassToModel;
import com.qmap.core.server.jena.AddNewPropertyToModel;
import com.qmap.core.server.jena.BuildLocationObjects;
import com.qmap.core.server.jena.BuildModel;
import com.qmap.core.server.jena.BuildPoints;
import com.qmap.core.server.jena.GetCandidateProperties;
import com.qmap.core.server.jena.GetLocationSubclasses;
import com.qmap.core.server.xml.LocationListXML;
import com.qmap.core.server.xml.ProcessLocationsXML;

public class ServerSideImpl extends RemoteServiceServlet implements ServerSide {

	// Two individual needed.

	private static final long serialVersionUID = 1L;

	static final String uriPrefix = "http://www.semanticweb.org/ontologies/2012/1/Ontology1328444427428.owl#";

	public String reply(String id, int type, String name, String coords, String desc,
			int area, String cls) {

		OntModel m = BuildModel.newInstance();

		String[] cArr = coords.split(" ");
		ArrayList<Individual> pointArray = new BuildPoints().getPoints(m, cArr,
				name);

		// Augment Model
		m = new BuildLocationObjects(id, m, pointArray, name, desc, area, type, cls)
				.getModel();

		try {
			// Write model to file.
			FileOutputStream fout = new FileOutputStream("owl/MO1-out.owl");
			m.write(fout);
			fout.close();
			m.close();
		} catch (IOException e) {
			System.out.println("Exception caught" + e.getMessage());
		}
		m.close();
		return null;

	}

	@Override
	public String loadExistingMapObjects(String id) {

		// OntModel m = BuildModel.newInstance();

		LocationListXML listXml = new LocationListXML();

		listXml = ProcessLocationsXML.getXML(id);

		JAXBContext context;

		StringWriter strW = new StringWriter();
		try {
			context = JAXBContext.newInstance(LocationListXML.class);

			Marshaller ms = context.createMarshaller();

			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			ms.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			ms.marshal(listXml, strW);

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strW.toString();

	}

	@Override
	public String[] getCandidateRelationships() {

		// BuildCorpusFromOWL p = new BuildCorpusFromOWL();

		return null;
	}

	@Override
	public String getClassStructure() {
		// TODO Auto-generated method stub

		GetLocationSubclasses structure = new GetLocationSubclasses();
		return structure.getTree();
	}

	@Override
	public String getValidObjectProperties(String sub, String obj) {
		// TODO Auto-generated method stub
		GetCandidateProperties gp = new GetCandidateProperties(sub, obj);

		return gp.getXml();
	}

	@Override
	public String augmentClassStructure(String id, String nm, String ref) {
		// TODO Auto-generated method stub

		AddClassToModel.process(id, nm, ref);

		return nm;
	}

	@Override
	public boolean assignObjectProperties(String[] rs) {
		OntModel m = BuildModel.newInstance();

		for (int x = 0; x < rs.length; x++) {

			String c = rs[x];

			String[] cv = c.split("%%%");

			System.out.println("@1: " + cv[0]);
			System.out.println("@2: " + cv[1]);
			System.out.println("@3: " + cv[2]);

			Individual Si = m.getIndividual(uriPrefix + cv[0]);
			OntProperty Op = m.getOntProperty(uriPrefix + cv[1]);

			Si.addProperty(Op, uriPrefix + cv[2]);

		}
		try {
			// Write model to file.
			FileOutputStream fout = new FileOutputStream("owl/MO1-out.owl");
			m.write(fout);
			fout.close();
			m.close();
			
		} catch (IOException e) {
			System.out.println("Exception caught" + e.getMessage());
		}
		m.close();

		return true;
	}

	@Override
	public boolean createNewProperty(String sub, String pred, String obj) {
		
		return AddNewPropertyToModel.process(sub, pred, obj);

	}
}
