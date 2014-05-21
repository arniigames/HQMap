package com.qmap.core.client.tools;

import com.google.gwt.maps.client.geom.LatLng;
import com.qmap.core.client.obj.InfoPolyline;


//Makes Polyline for display.
public class MakePolyline {
	LatLng[] ll;
	int mode;

	public void setProperties(LatLng[] l) {
		ll = l;
	}

	public InfoPolyline getPolyline() {
		InfoPolyline p = null;
		String col;
		Double op;
		col = "#FF0014";
		op = 0.8;
		p = new InfoPolyline(ll, col, 6, op);
		return p;
	}
}
