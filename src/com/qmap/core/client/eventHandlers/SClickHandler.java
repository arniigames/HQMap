package com.qmap.core.client.eventHandlers;

import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.qmap.core.client.Main;

public class SClickHandler implements MapClickHandler {

	private boolean check = false;

	@Override
	public void onClick(MapClickEvent event) {

		// Adds exit event double click handler
		if (!check) {
			Main.map.addMapDoubleClickHandler(
					new ExitInputModeDCHandler(this));
			check = true;
		}

		LatLng ll = event.getLatLng();
		MarkerOptions opt = MarkerOptions.newInstance();

		Icon icon = Icon.newInstance("client_resources/MarkerIconSmallPoint.png");
		icon.setShadowURL("client_resources/MarkerIconSmallPointShadow.png");

		icon.setIconSize(Size.newInstance(10, 10));
		icon.setShadowSize(Size.newInstance(10, 10));
		icon.setDragCrossImageURL("client_resources/InputMarkerIconShadow.png");
		icon.setIconAnchor(Point.newInstance(5, 10));
		icon.setInfoWindowAnchor(Point.newInstance(10, 6));
		opt.setIcon(icon);

		Marker mk = new Marker(ll, opt);

		Main.map.addOverlay(mk);

		Main.dd.addMarker(mk);
	}

}