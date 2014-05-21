package com.qmap.core.server.xml;

import java.util.ArrayList;
import java.util.ListIterator;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.qmap.core.server.jena.BuildModel;

public class ProcessLocationsXML {

	static final String uriPrefix = "http://www.semanticweb.org/ontologies/2012/1/Ontology1328444427428.owl#";

	public static LocationListXML getXML(String id) {
		OntModel m = BuildModel.newInstance();

		OntClass o = m.getOntClass(uriPrefix + "Location");

		// All subclasses of Location class.
		ExtendedIterator<OntClass> itR = o.listSubClasses(true);

		//
		LocationListXML listXml = new LocationListXML();

		while (itR.hasNext()) {
			OntClass soC = itR.next();

			String cls = soC.getLocalName();

			// Get individuals
			@SuppressWarnings("unchecked")
			ExtendedIterator<Individual> oIt = (ExtendedIterator<Individual>) soC
					.listInstances();// listInstances();

			while (oIt.hasNext()) {

				// JENA INDIVIDUAL
				Individual i = oIt.next();

				if (i.hasProperty(m.getProperty(uriPrefix + "ownerReference"))) {
					RDFNode idc = i.getPropertyValue(m.getProperty(uriPrefix
							+ "ownerReference"));
					String compString = idc.asLiteral().getString();

					// Filter objects according to user ID
					if (compString.equals(id)) {

						// XML NODE
						LocationXML locXml = new LocationXML();

						String chk = getPrimeClass(m.getOntClass(uriPrefix
								+ cls));

						locXml.setPType(chk);

						String title = i
								.getPropertyValue(
										m.getProperty(uriPrefix + "hasTitle"))
								.asLiteral().getString();

						locXml.setName(title);
						locXml.setType(i.getOntClass().getLocalName());

						String desc = i
								.getPropertyValue(
										m.getProperty(uriPrefix
												+ "hasDescription"))
								.asLiteral().getString();

						locXml.setDescription(desc);

						Resource r = i.getPropertyResourceValue(m
								.getProperty(uriPrefix + "hasShape"));

						StmtIterator sit = r.listProperties(m
								.getProperty(uriPrefix + "hasPoints"));

						GeometryXML geom = new GeometryXML();
						ArrayList<Resource> px = new ArrayList<Resource>();

						while (sit.hasNext()) {
							Statement s = sit.next();

							Resource rs = s.getResource();

							px.add(rs);
						}

						ListIterator<Resource> rLi = px.listIterator();
						PointXML[] pxml = new PointXML[px.size()];

						while (rLi.hasNext()) {

							Resource pr = rLi.next();
							String ln = pr.getLocalName();

							// Used to correct order of points for display
							// purposes.

							// Jena returns complex shape points in arbitrary
							// fashion when > 5 points.
							int f = ln.lastIndexOf("point_");

							// Adds appropriate suffix - used for ordering
							// points later on.
							f += 6;
							int order = Integer.valueOf(ln.substring(f));

							order--;

							PointXML point = new PointXML();

							Double lat = pr.getProperty(
									m.getProperty(uriPrefix + "latitude"))
									.getDouble();
							Double lng = pr.getProperty(
									m.getProperty(uriPrefix + "longitude"))
									.getDouble();

							point.setLatitude(lat);
							point.setLongitude(lng);

							pxml[order] = point;

						}

						for (int x = 0; x < pxml.length; x++) {
							geom.addPoint(pxml[x]);
						}

						locXml.setGeometry(geom);
						listXml.addLocation(locXml);

					}

				}

			}

		}

		return listXml;

	}

	private static String getPrimeClass(OntClass o) {

		if (o.getLocalName().equals("Area")) {
			return "Area";
		} else if (o.getLocalName().equals("Structure")) {
			return "Structure";

		} else if (o.getLocalName().equals("Path")) {
			return "Path";
		} else {

			ExtendedIterator<OntClass> iter = o.listSuperClasses(false);

			while (iter.hasNext()) {
				OntClass spC = iter.next();

				if (spC.getLocalName().equals("Structure")) {
					return "Structure";
				}
				if (spC.getLocalName().equals("Area")) {
					return "Area";
				} else {
					return "Path";
				}
			}
		}
		return "Unclassified";
	}

}
