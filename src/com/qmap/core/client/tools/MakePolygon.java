package com.qmap.core.client.tools;

import com.google.gwt.maps.client.geom.LatLng;
import com.qmap.core.client.obj.InfoPolygon;

//Class to build custom polygons - needs to be expanded to cope with new classes being created.
public class MakePolygon {

	LatLng[] ll;
	int mode;

	public void setProperties(LatLng[] l, int m) {
		ll = l;
		mode = m;
	}

	public InfoPolygon getPolygon() {
		InfoPolygon p = null;
		String col;
		Double op;
		switch (mode) {
		case 1:
			// Structure
			col = "#00FF00";
			op = 0.6;
			p = new InfoPolygon(ll, col, 3, op, col, op - 0.3);
			p.setPrimeClass(mode);
			break;
		case 2:
			// Area
			col = "#0000FF";
			op = 0.4;
			p = new InfoPolygon(ll, col, 3, op, col, op - 0.3);
			p.setPrimeClass(mode);
			break;
		default:
			System.out.println("AN error has occured: type 3345 @ makePolygon");
		}
		return p;
	}

}
