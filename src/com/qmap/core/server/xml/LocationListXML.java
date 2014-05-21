package com.qmap.core.server.xml;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "com.qmap.core.server.xml")
public class LocationListXML {
	
	//@XmlElementWrapper( name = "Locations")
	
	private ArrayList<LocationXML> locList = new ArrayList<LocationXML>();
	
	@XmlElement(name="Location")
	public void setLocations(ArrayList<LocationXML> locList){
		this.locList = locList;
	}
	
	public void addLocation(LocationXML loc){
		locList.add(loc);
	}
	
	
	public ArrayList<LocationXML> getLocations(){
		return locList;
	}
}
