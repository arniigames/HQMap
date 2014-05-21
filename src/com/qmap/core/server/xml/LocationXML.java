package com.qmap.core.server.xml;

import javax.xml.bind.annotation.XmlElement;

//@XmlRootElement(name="Locations")
//@XmlType(propOrder = {"Name", "Type", "Description" , "Geometry"})
public class LocationXML {
	
	private String name;
	private String type;
	private String desc;
	
	private String ptype;
	
	private GeometryXML geom;
	
	//@XmlElementWrapper( name = "Location")
	@XmlElement(name="Name")
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	@XmlElement(name="Type")
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	@XmlElement(name="pType")
	public String getPType(){
		return ptype;
	}
	
	public void setPType(String ptype){
		this.ptype = ptype;
	}
	
	@XmlElement(name="Description")
	public String getDescription(){
		return desc;
	}
	
	public void setDescription(String desc){
		this.desc = desc;
	}
	
	@XmlElement(name="Geometry")
	public GeometryXML getGeometry(){
		return geom;
	}
	
	public void setGeometry(GeometryXML geom){
		this.geom = geom;
	}

	
	
	
	

}
