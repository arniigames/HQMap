package com.qmap.core.client.eventHandlers;

import com.google.gwt.maps.client.event.MapDoubleClickHandler;
import com.google.gwt.user.client.Window;
import com.qmap.core.client.Main;
import com.qmap.core.client.UI.BaseUI;
import com.qmap.core.client.UI.DescriptionDialog;

public class DClickHandler implements MapDoubleClickHandler {

	@Override
	public void onDoubleClick(MapDoubleClickEvent event) {

		

		if (Main.bui.getLoginID().equals("")) {

			Window.alert("You must be logged in to add objects to the map.");
		} else if(Window.confirm("Enter input mode?")){

			// Enable clear canvas for object.
			Main.map.clearOverlays();
			Main.bui.disableButtons();

			Main.dd = new DescriptionDialog();

			
			
			
			// New info display to show input mode message
			BaseUI.id
					.setLabelText("<p><div style='font-weight:bold;'>*** INPUT MODE ACTIVE***</div>");

			BaseUI.p.show();

			event.getSender().removeMapDoubleClickHandler(this);

			SClickHandler sHandler = new SClickHandler();
			event.getSender().addMapClickHandler(sHandler);

			event.getSender().getElement().setClassName("inputMode");

		}

		
	}

}
