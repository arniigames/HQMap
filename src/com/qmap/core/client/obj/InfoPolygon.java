package com.qmap.core.client.obj;

import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Polygon;
import com.qmap.core.client.eventHandlers.InfoPolygonClickHandler;

public class InfoPolygon extends Polygon{

	private String desc = "";
	private String nm = "";
	private String cls = "";
	
	
	private boolean subject = false;
	private boolean object = false;
	
	private int primeClass;
	
	private boolean selected = false;
	
	public boolean isObject() {
		return object;
	}

	public void setObject(boolean object) {
		this.object = object;
	}


	
	public InfoPolygon(LatLng[] points, String strokeColor, int strokeWeight,
			double strokeOpacity, String fillColor, double fillOpacity) {
		super(points, strokeColor, strokeWeight, strokeOpacity, fillColor, fillOpacity);
		// TODO Auto-generated constructor stub
		
		this.addPolygonClickHandler(new InfoPolygonClickHandler());
	}
	
	public void setDescription(String desc){
		this.desc = desc;
	}
	
	public void setName(String nm){
		this.nm = nm;
	}
	
	public void setOntClass(String cls){
		this.cls = cls;
	}
	
	public String getName(){
		return this.nm;
	}
	
	public String getOntClass(){
		return this.cls;
	}
	
	public String getDescription(){
		return this.desc;
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void setSelected(boolean s){
		selected = s;
	}
	
	public int getPrimeClass(){
		return primeClass;
	}
	
	public void setPrimeClass(int p){
		primeClass = p;
		
	}

	public boolean isSubject() {
		return subject;
	}

	public void setSubject(boolean subject) {
		this.subject = subject;
	}
	

}
