//Button event version of DClickHandler

package com.qmap.core.client.eventHandlers;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.qmap.core.client.Main;
import com.qmap.core.client.UI.BaseUI;
import com.qmap.core.client.UI.DescriptionDialog;

public class RemoteClickHandler implements ClickHandler {

	boolean inputMode = false;
	
	SClickHandler sHandler = new SClickHandler();

	@Override
	public void onClick(ClickEvent event) {

		if (Main.bui.getLoginID().equals("")) {
			Window.alert("You must be logged in to add objects to the map.");
		} else if (inputMode) {
			// Exits input mode
			Main.dd.removeFromParent();
			
			BaseUI.p.hide();
			
			Main.map.removeMapClickHandler(sHandler);
			Main.map.clearOverlays();
			
			Main.bui.getInputModeBtn().setText("Enter Input Mode");			
			Main.bui.getPropertiesButton().setEnabled(true);
			
			Main.bui.setInputModeStyle(false);
			
			inputMode = false;

		} else {
			//Initiate INPUT MODE
			// Enable clear canvas for object.
			Main.map.clearOverlays();
			
			Main.dd = new DescriptionDialog();

			// New info display to show input mode message
			BaseUI.id
					.setLabelText("<p><div style='font-weight:bold;'>*** INPUT MODE ACTIVE***</div>");

			BaseUI.p.show();

			Main.map.addMapClickHandler(sHandler);

			Main.bui.getInputModeBtn().setText("Exit Input Mode");		
			Main.bui.getPropertiesButton().setEnabled(false);
			
			Main.bui.setInputModeStyle(true);
			
			inputMode = true;

		}

	}

}
