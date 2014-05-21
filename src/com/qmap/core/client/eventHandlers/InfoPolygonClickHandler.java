package com.qmap.core.client.eventHandlers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.event.PolygonClickHandler;
import com.google.gwt.maps.client.overlay.PolyStyleOptions;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.qmap.core.client.Main;
import com.qmap.core.client.ServerSide;
import com.qmap.core.client.ServerSideAsync;
import com.qmap.core.client.UI.ObjectPropertiesPanel;
import com.qmap.core.client.UI.PropertySuggestion;
import com.qmap.core.client.obj.InfoPolygon;

public class InfoPolygonClickHandler implements PolygonClickHandler {

	String col;
	double op;

	InfoPolygon i;

	ObjectPropertiesPanel p;

	final ServerSideAsync servletObject = GWT.create(ServerSide.class);

	@Override
	public void onClick(PolygonClickEvent event) {

		i = (InfoPolygon) event.getSender();
		p = (ObjectPropertiesPanel) Main.bui.getAttachPanel().getWidget();

		int status = p.getStatus();

		if (!i.isSelected()) {

			if (status == 0) {
				FillPanel(status);
				status++;
				p.setStatus(status);
				
				p.getCreateNewProperty().setEnabled(false);
				p.getCreateNewDataProperty().setEnabled(false);
				p.getConfirmBtn().setEnabled(false);
				

				p.getPropertiesVPanel().clear();

			} else if (status == 1) {

				FillPanel(status);
				status++;
				p.setStatus(status);

				// Assign label text as potential class names.
				String sub = p.getSubjectLabel();
				String obj = p.getObjectLabel();

				System.out.println("S: " + sub + "  O: " + obj);

				p.getCreateNewProperty().setEnabled(true);
				p.getCreateNewDataProperty().setEnabled(true);
				p.getConfirmBtn().setEnabled(true);

				
				servletObject.getValidObjectProperties(sub, obj,
						new AsyncCallback<String>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(String result) {
								// TODO Auto-generated method stub

								
								
								
								
								Document xml = XMLParser.parse(result);

								String xSub = xml.getElementsByTagName("subjectIndividual").item(0).getFirstChild().getNodeValue();
								String xObj = xml.getElementsByTagName("objectIndividual").item(0).getFirstChild().getNodeValue();
								
								
								NodeList matches = xml
										.getElementsByTagName("propertyMatch");

								for (int x = 0; x < matches.getLength(); x++) {
									
									Node n = matches.item(x);
									String sub = n.getChildNodes().item(0).getFirstChild().getNodeValue();
									String prp = n.getChildNodes().item(1).getFirstChild().getNodeValue();
									String obj = n.getChildNodes().item(2).getFirstChild().getNodeValue();
									
									PropertySuggestion ps = new PropertySuggestion(
											sub, prp, obj, xSub, xObj);
									
									p.getPropertiesVPanel().add(ps);
				
								}

							}

						});

			} else if (status == 2) {

				System.out.println("Cannot select 3 objects");
			}

		} else {

			i.setSelected(false);
			
			p.getCreateNewProperty().setEnabled(false);
			p.getCreateNewDataProperty().setEnabled(false);
			p.getConfirmBtn().setEnabled(false);
			
			ClearPanel(i, status);
			status--;
			p.getPropertiesVPanel().clear();
			p.setStatus(status);
		}
		System.out.println("Status: " + status);

	}

	private void ClearPanel(InfoPolygon i, int status) {

		if (status == 1) {
			p.setSubjectLabel("");
		} else {
			p.setObjectLabel("");
		}

		InfoPolygon ip = i;
		int sw = ip.getPrimeClass();
		switch (sw) {
		case 1:
			// Structure
			col = "#00FF00";
			op = 0.6;
			ip.setStrokeStyle(PolyStyleOptions.newInstance(col, 3, op));
			ip.setSelected(false);
			break;
		case 2:
			// Area
			col = "#0000FF";
			op = 0.4;
			i.setStrokeStyle(PolyStyleOptions.newInstance(col, 3, op));
			i.setSelected(false);
			break;
		default:
			System.out.println("AN error has occured: type 3345 @ makePolygon");
		}

	}

	private void FillPanel(int psw) {

		switch (psw) {
		case 0:

			i.setStrokeStyle(PolyStyleOptions.newInstance("#00FF39", 10, 1));
			i.setSelected(true);
			p.setSubjectLabel(i.getName());

			break;
		case 1:

			i.setStrokeStyle(PolyStyleOptions.newInstance("#00FF39", 10, 1));
			i.setSelected(true);
			p.setObjectLabel(i.getName());
			break;
		}

	}
}
