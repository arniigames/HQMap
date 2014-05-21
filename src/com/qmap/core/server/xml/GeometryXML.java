package com.qmap.core.server.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Geometry")
public class GeometryXML {
	
	private ArrayList<PointXML> pointList = new ArrayList<PointXML>();
	
	public ArrayList<PointXML> getPoints(){
		return pointList;
	}
	
	public void setPoints(ArrayList<PointXML> points){
		this.pointList = points;
	}
	
	public void addPoint(PointXML point){
		pointList.add(point);
	}
	

}
