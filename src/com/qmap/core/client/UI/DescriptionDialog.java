package com.qmap.core.client.UI;

import java.util.ArrayList;
import java.util.ListIterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.Polygon;
import com.google.gwt.maps.client.overlay.Polyline;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.qmap.core.client.Main;
import com.qmap.core.client.ServerSide;
import com.qmap.core.client.ServerSideAsync;
import com.qmap.core.client.eventHandlers.CTreeItemSelectionHandler;
import com.qmap.core.client.eventHandlers.DClickHandler;
import com.qmap.core.client.obj.ClassTree;

public class DescriptionDialog extends Composite {

	private static DescriptionDialogUiBinder uiBinder = GWT
			.create(DescriptionDialogUiBinder.class);
	@UiField
	TextBox titleInput;
	@UiField
	TextArea descriptionInput;
	@UiField
	Button createLocationButton;
	@UiField
	Button cancelButton;
	@UiField
	FormPanel descriptionForm;

	@UiField
	ScrollPanel treePanel;
	@UiField
	Button newSubClassButton;

	Polygon polygonBuilding;
	Polygon polygonArea;
	Polyline polylineRoad;

	ArrayList<Marker> markers = new ArrayList<Marker>();
	// ArrayList<Marker> markers2;

	int shapeArea;

	String coordString = "";

	private PopupPanel p = new PopupPanel();

	public PopupPanel getPopup() {
		return p;
	}

	public void setPopup(PopupPanel p) {
		this.p = p;
	}

	private String nm;

	// Build Tree to allow input of class.
	// ClassTree cTree = new ClassTree();

	// Value of checkbox select
	public int locType;

	public String cls;

	ClassTree cTree = new ClassTree();


	interface DescriptionDialogUiBinder extends
			UiBinder<Widget, DescriptionDialog> {
	}

	public DescriptionDialog() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	public void process() {

		// Plus one for the complete polygon
		LatLng[] ll = new LatLng[(markers.size() + 1)];

		ListIterator<Marker> li = markers.listIterator();
		int cnt = 0;

		while (li.hasNext()) {

			Marker m = li.next();
			ll[cnt] = m.getLatLng();
			cnt++;
		}

		ll[markers.size()] = ((Marker) markers.get(0)).getLatLng();

		// Polygon objects for visualising builidngs and areas.
		polygonBuilding = new Polygon(ll, "#2C18DA", 3, 1, "#0D7DD9", 0.4, null);
		polygonArea = new Polygon(ll, "#925B53", 3, 1, "#925B53", 0.2, null);

		for (int x = 0; x < markers.size(); x++) {
			Main.map.removeOverlay(markers.get(x));
		}

		// Polyline object in case of road
		polylineRoad = new Polyline(ll, "#F0001B", 8);

		Main.map.addOverlay(polygonBuilding);
		Main.map.addOverlay(polygonArea);
		Main.map.addOverlay(polylineRoad);

		// Removes last vertoce from polygon to make it a path not a polygon.
		// Needs to be done after object is added to map.
		polylineRoad.deleteVertex((polylineRoad.getVertexCount() - 1));

		// Hide non-d.efault map objects.
		hidePolygonA();
		hidePolygonB();
		hidePolylineA();

		//shapeArea = getAreaFromObject(polygonArea);

		// ///////////////////////////////////
		// Default Values for testing
		descriptionInput
				.setValue("Please replace this text with a brief description of the location.");

		treePanel.add(this.buildClassTree());

	}

	public ClassTree getcTree() {
		return cTree;
	}

	public void setcTree(ClassTree cTree) {
		this.cTree = cTree;
	}

	public ClassTree buildClassTree() {

		cTree.addSelectionHandler(new CTreeItemSelectionHandler());
		return cTree;
	}

//	private int getAreaFromObject(Polygon polygonArea2) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	// Handler for successfull addition of description - goes serverside to
	// process ontology using
	// ServerSideImpl & servlet mechanism.
	@UiHandler("createLocationButton")
	void onCreateLocationButtonClick(ClickEvent event) {

		// Form validation
		TreeItem t = cTree.getSelectedItem();
		if (t == null) {
			Window.alert("Please assign a class for your item");

		} else {

			// Server object used for communicating with ServerSide
			final ServerSideAsync servletObject = GWT.create(ServerSide.class);

			String title = titleInput.getValue();

			title = title.replace(' ', '_');
			
			// Query servlet and return result
			servletObject.reply(Main.bui.getLoginID(), locType, title, coordString,
					descriptionInput.getValue(), shapeArea, cls,
					new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							// Show the RPC error message to the user
							System.out.println("rpc Message: "
									+ caught.getMessage());

						}

						public void onSuccess(String result) {
							

							// Remove input dialog box
							Main.bui.attachPanel.clear();

							// resets mapping envoronment to default.
							//Main.map.addMapDoubleClickHandler(new DClickHandler());
							Main.map.clearOverlays();
							
							//Resets GUI
							Main.bui.propertiesButton.setEnabled(true);
							Main.bui.inputModeBtn.setEnabled(true);
							Main.bui.setInputModeStyle(false);
							
							
							//Notofy user
							Window.alert(titleInput.getValue() + " successfully added to model.");

						}
					});
		}
	}

	// Cancels Object creation,
	@UiHandler("cancelButton")
	void onCancelButtonClick(ClickEvent event) {

		// removes dialogue panel
		this.removeFromParent();

		// Restire buttons on main panel.
		Main.bui.enableButtons();

		// Removes shape and markers from map.
		Main.map.removeOverlay(polygonBuilding);
		Main.map.removeOverlay(polygonArea);
		Main.map.removeOverlay(polylineRoad);

		// Re-instates Browse mode.
		Main.bui.attachPanel.clear();
		Main.bui.getInputModeBtn().setText("Enter Input Mode");
		
		Main.bui.setInputModeStyle(false);

	}

	// Ensures adequate description is added
	@UiHandler("descriptionInput")
	void onDescriptionInputValueChange(KeyPressEvent event) {

		// Basic form validation
		String s = descriptionInput.getValue();
		String t = titleInput.getValue();
		

		// Set minimum description 100 chars
		if ((s.length() > 50) && (t.length() > 1)) {
			createLocationButton.setEnabled(true);
		}

	}

	public void showPolygonA() {
		polygonBuilding.setVisible(true);
	}

	public void showPolygonB() {
		polygonArea.setVisible(true);
	}

	public void showPolylineA() {
		polylineRoad.setVisible(true);
	}

	public void hidePolygonA() {
		polygonBuilding.setVisible(false);
	}

	public void hidePolygonB() {
		polygonArea.setVisible(false);
	}

	public void hidePolylineA() {
		polylineRoad.setVisible(false);
	}

	public String getNm() {
		return nm;
	}

	public void setNm(String nm) {
		this.nm = nm;
		
		if (!nm.equals("")) {
			newSubClassButton.setEnabled(true);
		} else {
			newSubClassButton.setEnabled(false);

		}

	}

	//Crete a new subclass
	@UiHandler("newSubClassButton")
	void onNewSubClassButtonClick(ClickEvent event) {
		NewClassDialog ncd = new NewClassDialog(nm);

		ncd.DBLabel.setText("Add a child class of " + nm);

		p.add(ncd);
		p.setGlassEnabled(true);
		p.center();
		p.show();
	}

	// Add Marker Object to Description Dialog.
	public void addMarker(Marker mk) {
		String lat = String.valueOf(mk.getLatLng().getLatitude());
		String lng = String.valueOf(mk.getLatLng().getLongitude());
		
		// Augment coord String
		coordString += lat + " " + lng + " ";
		markers.add(mk);
		

	}

}
