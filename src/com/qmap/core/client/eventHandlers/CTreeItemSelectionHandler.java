package com.qmap.core.client.eventHandlers;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.TreeItem;
import com.qmap.core.client.Main;
import com.qmap.core.client.obj.ClassTreeItem;

public class CTreeItemSelectionHandler implements SelectionHandler<TreeItem> {

	@Override
	public void onSelection(SelectionEvent<TreeItem> event)
			throws ClassCastException {
		// TODO Auto-generated method stub

		

		try {
			ClassTreeItem t = (ClassTreeItem) event.getSelectedItem();

			Main.dd.setNm(t.name);

			// Switch to define displayed object.
			switch (t.shape) {
			case 1:
				Main.dd.showPolygonA();
				Main.dd.hidePolygonB();
				Main.dd.hidePolylineA();
				Main.dd.locType = t.shape;
				Main.dd.cls = t.name;

				break;
			case 2:
				Main.dd.hidePolygonA();
				Main.dd.hidePolygonB();
				Main.dd.showPolylineA();
				Main.dd.locType = t.shape;
				Main.dd.cls = t.name;

				break;
			case 3:
				Main.dd.hidePolygonA();
				Main.dd.showPolygonB();
				Main.dd.hidePolylineA();
				Main.dd.locType = t.shape;
				Main.dd.cls = t.name;

				break;
			default:
				System.out.println("OVERLAY ERROR 2");
			}
		} catch (Exception e) {
			System.out.println("YO:" + e.getLocalizedMessage());

		}
		;
	}

}
