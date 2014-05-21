package com.qmap.core.server.jena;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class GetCandidateProperties {

	private OntClass object;
	private OntClass subject;

	OntModel m;

	String xml;

	static final String uriPrefix = "http://www.semanticweb.org/ontologies/2012/1/Ontology1328444427428.owl#";

	public GetCandidateProperties(String sub, String obj) {

		xml = "<?xml version='1.0'?><matches>";

		xml += "<individuals><subjectIndividual>" + sub
				+ "</subjectIndividual>";
		xml += "<objectIndividual>" + obj + "</objectIndividual></individuals>";
		m = BuildModel.newInstance();

		// Instantiates individual object for retrieval of information.
		Individual iSub = m.getIndividual(uriPrefix + sub);
		Individual iObj = m.getIndividual(uriPrefix + obj);

		// workaround to get class from individual.
		ExtendedIterator<OntClass> subClassList = iSub.listOntClasses(true);
		ExtendedIterator<OntClass> objClassList = iObj.listOntClasses(true);

		subject = GetPrimeClass(subClassList);
		object = GetPrimeClass(objClassList);

		GetPropertiesRecursive(subject);

		xml += "</matches>";

	}

	private OntClass GetPropertiesRecursive(OntClass s) {

		OntClass sup;

		ExtendedIterator<OntClass> supClassIterator = s.listSuperClasses(true);

		ExtendedIterator<OntProperty> pIterator = s
				.listDeclaredProperties(true);

		while (pIterator.hasNext()) {
			OntProperty op = pIterator.next();
			ExtendedIterator<? extends OntClass> itt = op
					.listDeclaringClasses();

			while (itt.hasNext()) {
				OntClass occ = itt.next();

				// Determines the valid properties and excludes those that
				// should be restricted
				// to use with geometry objects only.
				if (occ.equals(subject)
						&& !op.hasRange(m.getResource(uriPrefix + "Geometry"))
						&& !op.isDatatypeProperty()
						&& !op.isAnnotationProperty()
						&& !op.equals(m.getProperty(uriPrefix + "equal"))) {

					xml += "<propertyMatch>";
					xml += "<subject>" + subject.getLocalName() + "</subject>";
					xml += "<property>" + op.getLocalName() + "</property>";
					xml += "<object>" + object.getLocalName() + "</object>";
					xml += "</propertyMatch>";

				}

			}
		}

		while (supClassIterator.hasNext()) {

			sup = supClassIterator.next();

			if (!sup.equals(m.getOntClass(uriPrefix + "Thing"))) {
				GetPropertiesRecursive(sup);
			}

		}

		return s;
	}

	private OntClass GetPrimeClass(ExtendedIterator<OntClass> ex) {
		OntClass oc = null;

		while (ex.hasNext()) {
			oc = ex.next();

			if (!oc.equals(m
					.getOntClass("http://www.w3.org/2002/07/owl#NamedIndividual"))) {
				return oc;

			}
		}

		return null;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

}
