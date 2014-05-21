package com.qmap.core.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapType;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.control.MapTypeControl;
import com.google.gwt.maps.client.control.ScaleControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.user.client.ui.RootPanel;
import com.qmap.core.client.UI.BaseUI;
import com.qmap.core.client.UI.DescriptionDialog;


public class Main implements EntryPoint {
	
	//CONSTANTS
	public final String MAP_WIDTH = "814px";
	public final String MAP_HEIGHT = "570px";
	public final static double MAP_LAT = 53.40644;
	public final static double MAP_LNG = -2.96625; //Centre on Ashton Building, U. of Liverpool
	public final static int MAP_ZOOM = 17;
	
	//Global Objects.	
	//private static DClickHandler dHandler;
	
	//Base User Frame (Contains map, control panel, message box etcc...
	public static BaseUI bui = new BaseUI();
	
	//MapWidget and associated initialization options.
	
	public static MapWidget map;
	public static DescriptionDialog dd; 
	
	//Entry-point. Loads googlemaps and then calls startUp method.
	public void onModuleLoad() {
		final String gKey = "ABQIAAAABIkCwObU-NAVGiw9SErKqRQkHMw2vVdDlVYf0tLJikxoUd96QhQXYFrIR3BosyzXQjHcAyoUFQ2jGQ";
		Maps.loadMapsApi(gKey, "2", false, new Runnable() { public void run() {startUp();}});	
		
	}
	
	public void startUp(){
		
		MapOptions options = MapOptions.newInstance().setDraggableCursor("crosshair");
		map = new MapWidget(LatLng.newInstance(MAP_LAT, MAP_LNG), MAP_ZOOM,options);
		
		bui.getMapHolder().add(map);
		
		RootPanel.get("mapPane").add(bui);
		
		//Set height/width after appending to web page (rootpanel)
		map.setHeight(MAP_HEIGHT);
		map.setWidth(MAP_WIDTH);
		
		map.setDoubleClickZoom(false);
		map.addControl(new LargeMapControl());
		map.addControl(new MapTypeControl());
		map.addControl(new ScaleControl());
		
		map.setCurrentMapType(MapType.getNormalMap());
		//dHandler = new DClickHandler();
		//map.addMapDoubleClickHandler((MapDoubleClickHandler) dHandler);

	}

//	public static DClickHandler getdHandler() {
//		return dHandler;
//	}


}




