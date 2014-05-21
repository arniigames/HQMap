package com.qmap.core.client.obj;

import com.google.gwt.maps.client.event.PolylineClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Polyline;
import com.qmap.core.client.eventHandlers.InfoPolylineClickHandler;

public class InfoPolyline extends Polyline {
	private String desc = "";
	private String nm = "";

	private boolean selected = false;

	private int primeClass;

	public InfoPolyline(LatLng[] points, String strokeColor, int strokeWeight,
			double strokeOpacity) {
		super(points, strokeColor, strokeWeight, strokeOpacity);
		// TODO Auto-generated constructor stub
		this.addPolylineClickHandler((PolylineClickHandler) new InfoPolylineClickHandler());
	}

	public void setDescription(String desc) {
		this.desc = desc;
	}

	public void setName(String nm) {
		this.nm = nm;
	}

	public String getName() {
		return this.nm;
	}

	public String getDescription() {
		return this.desc;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean s) {
		selected = s;
	}

	public int getPrimeClass() {
		return primeClass;
	}

	public void setPrimeClass(int p) {
		primeClass = p;

	}
}
