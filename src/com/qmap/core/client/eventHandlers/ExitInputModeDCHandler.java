package com.qmap.core.client.eventHandlers;

import java.util.ArrayList;

import com.google.gwt.maps.client.event.MapDoubleClickHandler;
import com.google.gwt.maps.client.overlay.Marker;
import com.qmap.core.client.Main;
import com.qmap.core.client.UI.BaseUI;

public class ExitInputModeDCHandler implements MapDoubleClickHandler {

	SClickHandler sc;
	static ArrayList<Marker> mkArr;

	public ExitInputModeDCHandler(SClickHandler s) {
		sc = s;

	}

	// Method to be implemented (at some point) to prevent inverted polygons
	// being created.
	public boolean checkInvertedPolygon() {

		// LatLng[] ll = getLatLngArray(mkArr);
		return false;

	}

	@Override
	public void onDoubleClick(MapDoubleClickEvent event) {

		event.getSender().removeMapClickHandler(sc);
		
		Main.map.removeMapDoubleClickHandler(this);

		Main.bui.getAttachPanel().clear();
		Main.bui.getAttachPanel().add(Main.dd);
		Main.dd.process();

		BaseUI.p.hide();
	}

}