package com.qmap.core.client.process;

import java.util.ArrayList;

import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.qmap.core.client.Main;
import com.qmap.core.client.UI.ObjectPropertiesPanel;
import com.qmap.core.client.eventHandlers.LocationObjectMouseOutHandler;
import com.qmap.core.client.eventHandlers.LocationObjectMouseOverHandler;
import com.qmap.core.client.obj.InfoPolygon;
import com.qmap.core.client.obj.InfoPolyline;
import com.qmap.core.client.tools.MakePolygon;
import com.qmap.core.client.tools.MakePolyline;

public class XMLtoMapObjects {

	String result;

	public XMLtoMapObjects(String result) {
		this.result = result;
	}

	public void process() {

		
		// TODO Auto-generated method stub
		// System.out.println("FINAL: " +result);
		Main.map.clearOverlays();
		Document doc = XMLParser.parse(result);
		
		

		//System.out.println(doc.toString());

		NodeList nl = doc.getElementsByTagName("Location");

		// Arraylists to ensure
		ArrayList<InfoPolygon> bldgs = new ArrayList<InfoPolygon>();
		ArrayList<InfoPolygon> areas = new ArrayList<InfoPolygon>();
		ArrayList<InfoPolyline> paths = new ArrayList<InfoPolyline>();

		for (int x = 0; x < nl.getLength(); x++) {

			Node n = nl.item(x);
			
			

			NodeList locs = n.getChildNodes();

			InfoPolygon poly = null;
			InfoPolyline polyline = null;

			// System.out.println("LL: "+locs.toString());

			String d = locs.item(1).getFirstChild().getNodeValue();

			String t = locs.item(7).getFirstChild().getNodeValue();
			
			String subt = locs.item(9).getFirstChild().getNodeValue();
			
			//System.out.println("SUBT: "+subt);

			String nm = locs.item(5).getFirstChild().getNodeValue();

			NodeList g = locs.item(3).getChildNodes();

			LatLng[] ll = new LatLng[g.getLength()];

			String desc = "Description: " + d + "\nn";

			// Point counter (increments on odd numbers.
			int chk = 1;

			for (int z = 0; z < g.getLength(); z++) {
				int mod = z % 2;
				if (mod == 1) {

					NodeList pnt = g.item(z).getChildNodes();
					Double lat = Double.valueOf(pnt.item(1).getFirstChild()
							.getNodeValue());
					Double lng = Double.valueOf(pnt.item(3).getFirstChild()
							.getNodeValue());
					ll[chk] = LatLng.newInstance(lat, lng);

					// Increment Point counter
					chk++;
				}
			}

			

			int sw = 0;

			if (t.equals("Structure")) {
				sw = 1;
				LatLng temp = ll[1];
				ll[chk++] = temp;
			} else if (t.equals("Area")) {
				sw = 2;
				LatLng temp = ll[1];
				ll[chk++] = temp;
			} else if (t.equals("Path")) {
				sw = 3;
			} else {
				System.out.println("ERROR: "+t.toString()+" Not Classified");
			}

			switch (sw) {
			case 1:
				MakePolygon mk = new MakePolygon();
				mk.setProperties(ll, sw);
				poly = mk.getPolygon();
				poly.setDescription(desc);
				poly.setName(nm);
				bldgs.add(poly);
				poly.setOntClass(subt);
				//Add handlers
				poly.addPolygonMouseOverHandler(new LocationObjectMouseOverHandler());
				poly.addPolygonMouseOutHandler(new LocationObjectMouseOutHandler());
				break;
			case 2:
				MakePolygon mk1 = new MakePolygon();
				mk1.setProperties(ll, sw);
				poly = mk1.getPolygon();
				poly.setDescription(desc);
				poly.setName(nm);
				poly.setOntClass(subt);
				areas.add(poly);
				//AddHandlers
				poly.addPolygonMouseOverHandler(new LocationObjectMouseOverHandler());
				poly.addPolygonMouseOutHandler(new LocationObjectMouseOutHandler());
				break;
			case 3:
				
				MakePolyline mpl = new MakePolyline();
				mpl.setProperties(ll);
				polyline = mpl.getPolyline();
				polyline.setDescription(desc);
				polyline.setName(nm);
				paths.add(polyline);

				break;
			}
			// Handlers
			

		}
		// Add items to map object
		PolygonAddEnMass(areas);
		PolygonAddEnMass(bldgs);
		PolylineAddEnMass(paths);
		
		//Gets Modeliing tools
		Main.bui.getAttachPanel().add(new ObjectPropertiesPanel());

	}

	/******* HELPERS *******/
	private static void PolygonAddEnMass(ArrayList<InfoPolygon> polygons) {

		// Adds overlays in layers.
		for (int x = 0; x < polygons.size(); x++) {
			Main.map.addOverlay(polygons.get(x));
		}
	}
	
	private static void PolylineAddEnMass(ArrayList<InfoPolyline> polylines) {

		// Adds overlays in layers.
		for (int x = 0; x < polylines.size(); x++) {
			Main.map.addOverlay(polylines.get(x));
		}
	}

}