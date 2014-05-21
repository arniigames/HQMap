package com.qmap.core.client.eventHandlers;

import com.google.gwt.maps.client.event.PolygonMouseOutHandler;
import com.qmap.core.client.UI.BaseUI;

public class LocationObjectMouseOutHandler implements  PolygonMouseOutHandler{
	//Mouse out behavior for map objects
	@Override
	public void onMouseOut(PolygonMouseOutEvent event) {
		BaseUI.p.hide();
	}

}
