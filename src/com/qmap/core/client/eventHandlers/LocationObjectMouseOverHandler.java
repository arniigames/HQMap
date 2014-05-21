package com.qmap.core.client.eventHandlers;

import com.google.gwt.maps.client.event.PolygonMouseOverHandler;
import com.qmap.core.client.UI.BaseUI;
import com.qmap.core.client.obj.InfoPolygon;

public class LocationObjectMouseOverHandler implements PolygonMouseOverHandler{

	@Override
	public void onMouseOver(PolygonMouseOverEvent event) {
		// TODO Auto-generated method stub

		InfoPolygon i = ((InfoPolygon) event.getSource());
		
		

		
		
		BaseUI.id.setLabelText("<p><div style='font-weight:bold;'>"+i.getName()
				+ "</div>");
		// Display n
		BaseUI.p.show();

	}

}
