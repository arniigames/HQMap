package com.qmap.core.server.xml;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="Points")
//@XmlType(propOrder = {"Latitude", "Longitude"})
public class PointXML {
	
	
	private Double latitude;
	private Double longitude;
	
	
	public Double getLatitude(){
		return latitude;
	}
	
	public void setLatitude(Double lat){
		this.latitude = lat;
	}
	
	public Double getLongitude(){
		return longitude;
	}
	
	public void setLongitude(Double lng){
		this.longitude = lng;
	}

}
